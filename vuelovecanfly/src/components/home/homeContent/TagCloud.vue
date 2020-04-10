<template>
  <div class="tag-card">
    <div class="tag-pad">
      <div class="tag-title">
        <i class="fa fa-tags" style="margin-right: 10px"></i>标签云
      </div>
      <wordcloud
              :data="tags"
              nameKey="name"
              valueKey="value"
              color="Category10"
              :fontSize="fontSize"
              :showTooltip="true"
              :wordClick="wordClickHandler">
      </wordcloud>
    </div>
  </div>
</template>

<script>

  //引用：https://github.com/feifang/vue-wordcloud
  import wordcloud from 'vue-wordcloud';
  import { getRequest } from '@/axios';

  export default {
    name: 'TagCloud',
    components: {
      wordcloud
    },

    data() {
      return {
        tags: [],
        fontSize:[40,80]
      };
    },

    created() {
      this.getTags();
    },

    methods: {
      getTags() {
        getRequest('home/all/tag').then(resp => {
          if (resp.status == 200) {
            const data = resp.data;
            this.tags = data.map(d => {return {'name': d.tagName,'value': d.articleNums}});
          }
        });
      },


      wordClickHandler(name, value, vm) {
        this.$store.commit('changeCondition',{
          condition:'tag',
          name:name
        });
        this.$router.push('/home/homeContent/briefArticles');
      }
    }
  };
</script>

<style scoped>

  .tag-card {
    box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.18);
    background-color: #fff;
    width: 100%;
    border-radius: 18px;
    margin-bottom: 20px;
  }


  .tag-pad {
    padding: 0px;
  }

  /deep/ .text {
    cursor: pointer;
  }


  .tag-title {
    padding: 20px;
    font-size: 12px;
    font-weight: 400;
    color: #7a7a7a;
  }
</style>


