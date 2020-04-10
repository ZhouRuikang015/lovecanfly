<template>
  <div class="card">
    <div class="card-pad">
      <div v-for="timeLineYear in timeLineYears" :key="timeLineYear.year" v-if="timeLineYear.year">
        <el-button type="primary" size="mini">{{timeLineYear.year}} | {{timeLineYear.count}} 篇</el-button>
        <div v-for="timeLineMonth in timeLineYear.timeLineMonths" :key="timeLineMonth.month" v-if="timeLineMonth.month"
             class="month-body">
          <div class="month-title">
            <i class="fa fa-subway" style="color:#b9a2ff;margin-right: 0.3em"></i>
            <el-tag type="danger"  effect="dark">{{timeLineMonth.month}} | {{timeLineMonth.articles.length}} 篇</el-tag>
          </div>
          <div v-for="article in timeLineMonth.articles" :key="article.articleId" class="article-body">
            <ul class="ul-text">
              <li>{{article.updateTime.split(' ')[0]}}</li>
              <li>{{article.categoryName}}</li>
              <li><i class="fa fa-hand-o-right"></i> {{article.nickname}}</li>
            </ul>
            <p class="p-title"   @click="toArticlePage(article.articleId)">{{article.title}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { getRequest } from '@/axios';

  export default {
    name: 'TimeLine',

    data() {
      return {
        timeLineYears: []
      };
    },


    created() {
      this.getTimeLineYears();
    },


    methods: {
      getTimeLineYears() {
        getRequest('/home/timeLine').then(resp => {
          if (resp.status == 200) {
            this.timeLineYears = resp.data;
          }
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
  .card {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    border-radius: 6px;
    margin-bottom: 20px;
  }

  .card-pad {
    padding: 25px 30px;
  }

  .month-body {
    margin-left: 14px;
    border-left: 1px solid #dbdbdb;
  }

  .article-body {
    position: relative;
    padding-top: 0.3em;
    padding-left: 1.5em;
    padding-bottom: 1.5em;
  }

  .article-body:before {
    content: '';
    width: 9px;
    height: 9px;
    left: -0.3em;
    top:0.8em;
    background: #dbdbdb;
    border-radius: 50%;
    position: absolute;
  }


  .ul-text > li {
    display: inline;
    margin-right: 20px;
    font-size: 6px;
    color: #7a7a7a;
  }


  .month-title {
    padding-left: 0.4em;
    padding-top: 1em;
  }

  /deep/ .el-tag--dark.el-tag--danger{
    background-color: #b9a2ff;
    border-color: #b9a2ff;
  }

  .p-title{
    font-size: 14px;
    margin-top: 3px
  }

  .p-title:hover{
    color: #ff8e00;
    cursor: pointer;
  }

</style>
