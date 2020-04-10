<template>
  <div class="card">
    <div class="card-body" >
      <div class="myself">
        <img src="@/assets/img/avatar_man.jpg" />
        <p>开发者专属占位</p>
        <p style="margin-top: 3px"><i class="el-icon-location-information location">   China· Guangzhou</i></p>
      </div>
    </div>
    <div class="data-body">
      <el-row class="row-body">
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <p class="data-title" >文章</p>
          <p class="data" v-loading="loading" id="article" @click="changeCondition">{{articlesCount}}</p>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <p class="data-title">分类</p>
          <p class="data" v-loading="loading">{{categoriesCount}}</p>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <p class="data-title">标签</p>
          <p class="data" v-loading="loading">{{tagsCount}}</p>
        </el-col>
      </el-row>
      <div class="icon-link">
          <a style="margin-right: 10px; font-size: 16px"  href="https://github.com/ZhouRuikang015" target="_blank">
            <el-button type="success" icon="fa fa-github" circle size="mini"></el-button>
          </a>
          <a target="_blank" href="mailto:zhouruikang015@163.com">
            <el-button type="primary" icon="el-icon-message" circle size="mini"></el-button>
          </a>
          <a  style="margin-left: 10px;" href="http://zhouruikang.me" target="_blank">
            <el-button type="danger" icon="el-icon-connection" circle size="mini"></el-button>
          </a>
      </div>
    </div>
  </div>
</template>


<script>

  import { getRequest } from '@/axios';

  export default {
    name: 'Information',
    data(){
      return{
        articlesCount: 0,
        categoriesCount: 0,
        tagsCount: 0,

        loading: false,
      }
    },

    created() {
      this.getInformation();
    },

    methods:{
      getInformation(){
        this.loading = true;
        getRequest('/home/count').then( resp => {
          if (resp.status == 200){
            this.loading = false;
            this.articlesCount = resp.data.articlesCount;
            this.categoriesCount = resp.data.categoriesCount;
            this.tagsCount = resp.data.tagsCount;
          }
        });



      },

      changeCondition(){
        this.$store.commit('changeCondition',{
          condition:'',
          name:''
        });
        if (this.$route.path != '/home/homeContent/briefArticles'){
          this.$router.push('/home/homeContent/briefArticles');
        }
      }
    }
  };
</script>

<style scoped>
  .card {
    display: block;
    box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.18);
    background-color: #fff;
    width: 100%;
    border-radius: 18px;
  }

  .card-body {
    padding: 35px 30px;
  }

  .myself{
    width: 100%;
  }

  .myself > img {
    width: 50%;
    height: 100%;
    /*border-radius: 50%;*/
    position: relative;
    left: 25%;
  }

  .myself > p {
    margin-top: 10px;
    font-size: 18px;
    font-weight: 600;
    text-align: center;
    color: #1f2f3d;
  }

  .location {
    font-size: 13px;
  }

  .data-body {
    margin-bottom: 10px;
    padding: 0px 30px 10px 30px;
    text-align: center;
  }


  .data-title {
    font-size: 14px;
  }

  .data {
    font-size: 26px;
    font-weight: 500;
    color: #363636;
  }

  .icon-link {
    color: black;
    font-size: 12px;
    font-weight: 500;
    margin-top: 10px;
  }

  #article:hover{
    font-size: 26px;
    font-weight: 600;
    color: #ff7781;
    cursor: pointer;
  }


</style>
