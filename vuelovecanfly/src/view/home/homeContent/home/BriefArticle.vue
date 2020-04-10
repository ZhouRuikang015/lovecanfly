<template>
  <div v-loading="loading">
    <div v-if="this.listSearchCondition.condition" class="brief-article-card">
      <div style="padding: 22px 30px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>
            <i class="fa fa-caret-right" style="margin-right: 6px"></i>
            {{this.listSearchCondition.condition == 'tag'? '标签':this.listSearchCondition.condition=='category'?'分类':'用户'}}
          </el-breadcrumb-item>
          <el-breadcrumb-item> {{this.listSearchCondition.name}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>

    <div v-for="item in briefArticles" class="brief-article-card">
      <div :index="item.index" :key="item.index" class="card-body">
        <p style="font-size: 12px;color: #7a7a7a ;">
          {{(item.createTime)}}
          <el-button  type="text" style="margin:0px 10px;color: #7a7a7a;"
                      @click="changeCondition('category',item.categoryName)">
            {{item.categoryName}}
          </el-button>
          {{Math.round(item.content.length/160 + 1)}}分钟 读完(大约{{item.content.length}}字)
        </p>
        <el-link   :underline="false" style="font-size: 21px;font-weight: 400;" @click="toArticlePage(item.articleId)">
          {{item.title}}
        </el-link>
        <p style="font-size: 16px;color: #4a4a4a ;margin: 20px 0px">
          {{item.summary}}
        </p>
        <el-button type="info" size="mini" @click="toArticlePage(item.articleId)">
          阅读更多
        </el-button>
        <el-button  type="text" style="margin:0px 10px;color: #7a7a7a;"
                    @click="changeCondition(item.userId,item.nickname)">
          <i class="fa fa-user"></i>
            {{item.nickname}}
        </el-button>

      </div>
    </div>
    <div class="pagination">
      <el-pagination
              background
              layout="total, prev, pager, next"
              :current-page="curPage"
              :page-size="pageSize"
              :total="pageTotal"
              @current-change="handlePageChange"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
  import { getFormatTime } from '@/utils/timeFormat';
  import { getRequest } from '@/axios';

  export default {
    name: 'BriefArticle',
    data(){
      return{
        briefArticles:[],
        content:'我是谁',

        curPage:1,
        pageSize:10,
        pageTotal:0,
        userId:'',

        loading:false
      }
    },



    computed: {
      listSearchCondition() {
        return this.$store.state.listSearchCondition;
      },

      hasChanged(){
        return this.listSearchCondition.condition+this.listSearchCondition.name;
      },

      keyword(){
        return this.$store.state.keyword;
      }


    },

    created() {
      this.getBriefArticles();
    },

    watch: {
      hasChanged(){
        this.getBriefArticles();
      },

      keyword(){
        this.getBriefArticles();
      }
    },


    methods:{
      getBriefArticles(){
        let condition = this.listSearchCondition.condition;
        let name = this.listSearchCondition.name;
        let url ='';
        if (condition == 'category'){
          url = '/home/category/all?categoryName='+name+'&curPage='+ this.curPage+ '&pageSize='+ this.pageSize;
        }else if (condition == 'tag'){
          url = '/home/tag/all?tagName='+name+'&curPage='+ this.curPage+ '&pageSize='+ this.pageSize;
        }else{
          url = '/home/all/article?status=1&curPage='+ this.curPage+ '&pageSize='+ this.pageSize+'&keyword='+ this.keyword+'&userId='+ condition ;
        }
        this.loading = true;
        getRequest(url).then( resp => {
          if (resp.status == 200){
            this.loading = false;
            this.briefArticles = resp.data.list;
            this.briefArticles.forEach((item,index) => {
              item.createTime = getFormatTime(item.createTime);
            });
            this.pageTotal = resp.data.total;
          }
        }).catch( err =>{
          this.loading = false;
        })
      },

      handlePageChange(val) {
        this.curPage = val;
        this.getBriefArticles();
      },


      changeCondition(condition,name){
        this.$store.commit('changeCondition',{
          condition:condition,
          name:name
        });
      },

      toArticlePage(articleId){
        this.$store.commit('lookArticle',articleId);
        this.$router.push('/home/articlePage');
      }

    }
  };
</script>

<style scoped>
  .brief-article-card {
    box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.18);
    background-color: #fff;
    width: 100%;
    border-radius: 18px;
    margin-bottom: 20px;
  }

  .card-body{
    padding: 25px 30px;

  }


  .pagination {
    margin: 20px 0;
    text-align: center;
  }


</style>
