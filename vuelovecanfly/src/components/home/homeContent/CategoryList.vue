<template>
  <div class="list-card">
    <div class="category">
      <div class="category-title">
        <i class="fa fa-bookmark" style="margin-right: 10px"></i>分类
      </div>
      <div class="category-body" >
        <el-row v-for="item in categoriesUsed" class="item-body" :key="item.categoryName">
          <div @click="changeCondition(item.categoryName)">
            <el-col :span="12">
              {{item.categoryName}}
            </el-col>
            <el-col :span="12">
              <el-tag style="float: right" type="info">
                {{item.articleNums}}
              </el-tag>
            </el-col>
          </div>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  import { getRequest } from '@/axios';

  export default {
    name: 'CategoryList',

    data(){
      return{

        categoriesUsed:[],
        categoryName:'在今年房价',
        articleNums:12
      }
    },

    created(){
      this.getCategoriesUsed();
    },

    methods:{
      getCategoriesUsed(){
        getRequest('home/all/category').then(resp => {
          if (resp.status == 200){
            this.categoriesUsed = resp.data;
          }
        })
      },


      changeCondition(categoryName){
        this.$store.commit('changeCondition',{
          condition:'category',
          name:categoryName
        });
        this.$router.push('/home/homeContent/briefArticles');
      }
    }
  };
</script>

<style scoped>
  .list-card {
    box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.18);
    background-color: #fff;
    width: 100%;
    border-radius: 18px;
    margin-bottom: 20px;

  }

  .category {
    padding: 20px;
  }

  .ul-text > li{
    display: inline;
  }

  .category-title {
    font-size: 12px;
    font-weight: 400;
    color: #7a7a7a ;
    margin-bottom: 15px;
  }

  .category-body {
    font-size: 14px;
    padding: 3px 0px;
  }

  .item-body {
    cursor: pointer;
    padding: 6px 27px;
  }

  .item-body:hover{
    background-color: rgba(234, 241, 251, 0.6);
    color: #f09c7a;
  }

</style>
