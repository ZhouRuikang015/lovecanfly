<template>
  <div class="blog-home scrollbar">
    <home-header></home-header>
    <div class="home-body " style="padding: 30px 90px">
      <el-row :gutter="20" style="padding-bottom: 60px;">
        <el-col class="hidden-md-and-down" :md="5">
          <information></information>
        </el-col>
        <el-col :md="19">
          <transition name="move" mode="out-in">
              <router-view></router-view>
          </transition>
        </el-col>
      </el-row>
      <el-backtop :right="50" :bottom="80" style="z-index: 1000" target=".home-body"></el-backtop>
    </div>
    <home-footer></home-footer>
  </div>
</template>

<script>
  import HomeHeader from '@/components/home/Header'
  import HomeFooter from '@/components/home/Footer'
  import Information from '@/components/home/Information'



  export default {
    name: 'Home',

    components: {
      HomeHeader,
      HomeFooter,
      Information
    },

    data(){
      return{
        keepAlive:['articlePage']
      }
    }
  };
</script>

<style scoped>
  @import '~element-ui/lib/theme-chalk/display.css';
  .blog-home {
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    background-color: #f7f7f7;
  }

  .home-body {
    min-height: 100%;
    padding: 20px;
    box-sizing: border-box;
  }
</style>
