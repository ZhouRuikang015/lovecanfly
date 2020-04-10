<template>
  <div class="link-card">
    <div class="link-padding">
      <div class="link-title">
        <i class="fa fa-bookmark" style="margin-right: 10px"></i>友链
      </div>
      <div class="link-body">
        <el-row class="row-body">
          <a v-for="item in links" class="a-link"
             :href="item.linkAddress" target="_blank">
            {{item.linkName}}
          </a>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'BlogLink',

    data() {
      return{
        links:[{
          linkName:'江南一点雨',
          linkAddress:'https://javaboy.org'
        }],
      }
    }
  };
</script>

<style scoped>
  .link-card {
    box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.18);
    background-color: #fff;
    width: 100%;
    border-radius: 18px;
    margin-bottom: 20px;
  }

  .link-padding {
    padding: 20px;
  }

  .link-title{
    font-size: 12px;
    font-weight: 400;
    color: #7a7a7a ;
    margin-bottom: 15px;
  }

  .link-body {
    font-size: 14px;
    padding: 0px 12px;
  }

  .a-link {
    color: #101323;
  }

  .row-body{
    width: 100%;
    padding: 8px 15px;
  }

  .row-body:hover{
    background-color: #d5dce6;
  }
</style>
