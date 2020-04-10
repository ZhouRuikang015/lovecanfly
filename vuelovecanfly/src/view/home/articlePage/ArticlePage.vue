<template>
  <div>
    <article-detail></article-detail>
    <article-comment v-if="enableComment!=0" ></article-comment>
  </div>
</template>

<script>

  import ArticleDetail from '@/components/home/articlePage/ArticleDetail';
  import ArticleComment from '@/components/home/articlePage/ArticleComment';
  import { getRequest } from '@/axios';
  export default {
    name: 'ArticlePage',
    components: {  ArticleComment, ArticleDetail },

    data() {
      return {
          enableComment: '',
      };
    },
    created(){
      this.getArticleDetail();
    },


    methods: {
      getArticleDetail() {
        getRequest('/home/' + this.$store.state.articleId).then(resp => {
          if (resp.status == 200) {
            this.enableComment = resp.data.enableComment;
          }
        });

      },


    }
  };
</script>

<style scoped>

</style>
