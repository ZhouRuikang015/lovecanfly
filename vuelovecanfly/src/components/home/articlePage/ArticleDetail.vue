<template>
  <div class="article-card">
    <div class="article-body">
      <div style="font-size: 12px;color: #7a7a7a ;">
        <ul class="ul-text">
          <li>
            <i class="fa fa-calendar-o"> 发表于 {{updateTime}}</i>
          </li>
          <li>
            <el-button type="text" class="fa fa-bookmark" style="color: #7a7a7a;"
                       @click="changeCondition('category',categoryName)">
              {{categoryName}}
            </el-button>
          </li>
                    <li>
                      <i class="fa fa-book">
                      {{Math.round(contentLength/160 + 1)}}分钟 读完(大约{{contentLength}}字)
                      </i>
                    </li>
          <li>
            <i class="fa fa-eye"> {{views}}</i>
          </li>
          <li v-if="updateTime!=createTime">
            <i class="fa fa-calendar-plus-o"> 修改于 {{updateTime}}</i>
          </li>
        </ul>
      </div>
      <div>
        <p style="font-weight: 500;font-size: 24px">
          {{title}}
        </p>
        <p style="margin: 20px 0px;font-size: 14px">
          {{summary}}
        </p>
      </div>
      <div class="markdown-body" v-html="html">

      </div>

      <div class="tag-body">
        <el-tag v-for="(item,index) in articleTags"
                :key="item.tagId"
                :type="tagType[index]"
                size="mini"
                class="fa fa-tag tag-click"
                style="margin: 10px 10px"
                @click="changeCondition('tag',item)"
        > {{item}}
        </el-tag>
      </div>
      <div v-if="enableComment == 0" style="margin-top:10px;text-align: center">
        <p style="color:#f0808c;font-size: 10px">本文不可评论喔~</p>
      </div>
    </div>
  </div>
</template>

<script>
  import { getRequest } from '@/axios';
  import { getFormatTime } from '@/utils/timeFormat';

  export default {
    name: 'ArticleDetail',
    // components: { Comment },


    data() {
      return {
        title: '',
        categoryName: '',
        summary: '',
        categoryId: 0,
        html: '',
        createTime: '',
        updateTime: '',
        views: '',
        enableComment: '',
        articleTags: [],
        tagType: ['success', 'warning', 'danger', 'primary'],

        contentLength: ''

      };
    },

    created() {
    this.getArticleDetail();
    },


    methods: {
      getArticleDetail() {
        getRequest('/home/'+ this.$store.state.articleId).then(resp => {
          if (resp.status == 200) {
            const data = resp.data;
            this.content = data.content;
            this.title = data.title;
            this.categoryId = data.categoryId;
            this.categoryName = data.categoryName;
            this.createTime = getFormatTime(data.createTime);
            this.updateTime = getFormatTime(data.updateTime);
            this.summary = data.summary;
            this.html = data.html;
            this.views = data.views;
            this.enableComment = data.enableComment;
            this.articleTags = data.articleTags.split(',');
            this.contentLength = data.content.length;
          }
        });


      },

      changeCondition(condition, name) {
        this.$store.commit('changeCondition', {
          condition: condition,
          name: name
        });
        this.$router.replace('/home/homeContent/briefArticles');
      }
    }

  };
</script>

<style scoped>
  @import "../../../assets/css/github-markdown.css";

  .markdown-body {
    box-sizing: border-box;
    margin: 0 auto;
    padding: 10px;
  }


  .article-card {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    border-radius: 6px;
    margin-bottom: 20px;
  }

  .article-body {
    padding: 20px;
  }

  .ul-text > li {
    display: inline;
    margin-right: 20px;
  }

  .tag-body {
    text-align: center;
  }


  .tag-click {
    cursor: pointer;
  }
</style>
