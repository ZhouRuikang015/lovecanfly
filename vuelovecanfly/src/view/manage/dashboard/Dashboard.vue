<template>
  <div class="container" style="padding: 0px 30px">
    <div>
      <el-row style="height: 40px;padding: 10px 0px">
        <el-switch
                v-if="this.$store.getters.isAdmin"
                v-model="enableAdmin"
                style="display: inline-block;font-size: 12px;float: right;"
                active-text="开启管理员模式"
                active-color="#13ce66"
                inactive-color="#d5dce6"
                @change="init"
        >
        </el-switch>
      </el-row>
      <div class="dashboard-box">
        <el-row :gutter="20">
          <el-col :xs="12" :sm="12" :md="12" :lg="6" :xl="6" class="col-box">
            <el-card>
              <div slot="header">
                <span>文章</span>
                <el-button style="float: right; padding: 3px 0;font-size: 15px" icon="el-icon-connection"
                           type="text" @click="toArticle"></el-button>
              </div>
              <div class="text" >
                <h5 v-loading="loading1">{{articlesCount}}</h5>
              </div>
            </el-card>
          </el-col>

          <el-col :xs="12" :sm="12" :md="12" :lg="6" :xl="6" class="col-box">
            <el-card>
              <div slot="header">
                <span>分类</span>
                <el-button style="float: right; padding: 3px 0;font-size: 15px" icon="el-icon-connection"
                           type="text" @click="toCategory"></el-button>
              </div>
              <div class="text" >
                <h5 v-loading="loading2">{{categoriesCount}}</h5>
              </div>
            </el-card>
          </el-col>

          <el-col :xs="12" :sm="12" :md="12" :lg="6" :xl="6" class="col-box">
            <el-card>
              <div slot="header">
                <span>评论</span>
                <el-badge :is-dot="dot" style="float: right;">
                  <el-button style="float: right; padding: 3px 0;font-size: 15px" icon="el-icon-connection"
                             type="text" @click="toComment"></el-button>
                </el-badge>
              </div>
              <div class="text" >
                <h5 v-loading="loading1">{{commentsCount}}</h5>
              </div>
            </el-card>
          </el-col>

          <el-col :xs="12" :sm="12" :md="12" :lg="6" :xl="6" class="col-box">
            <el-card>
              <div slot="header">
                <span>浏览量</span>
                <el-tooltip effect="light" placement="left">
                  <div slot="content" >
                    新增访问量:{{newViews}}噢~
                  </div>
                  <el-button style="float: right; padding: 3px 0;font-size: 15px" icon="el-icon-zoom-in"
                             type="text">
                  </el-button>
                </el-tooltip>
              </div>
              <div class="text" >
                <h5 v-loading="loading1">{{viewsCount}}</h5>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col v-if="!enableAdmin" :xs="24" :sm="24" :md="24" :lg="12" :xl="12" class="col-box">
            <el-tabs type="border-card">
              <el-tab-pane v-loading="loading2">
                <span slot="label"><i class="el-icon-date"></i> 新动态</span>
                <p style="margin-top: 10px">距离上一次登录： {{lastLoginTime}}</p>
                <p style="margin: 20px 30px">新增评论
                  <el-tag type="danger">
                    {{newComments}}
                  </el-tag>
                  条
                </p>
                <p style="margin: 20px 30px">新增访问量
                  <el-tag type="warning">
                    {{newViews}}
                  </el-tag>
                  位
                </p>

              </el-tab-pane>
            </el-tabs>
          </el-col>
          <el-col v-else :xs="24" :sm="24" :md="24" :lg="15" :xl="15" class="col-box">
            <div style="background-color: #ffffff;padding: 40px;border-radius: 5px;" v-loading="loading2">
              <p style="text-align: left">最近一周：</p>
              <ve-histogram :data="chartData" :settings="chartSettings" height="280px"
                       :before-config="beforeConfig"></ve-histogram>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
  import { getRequest } from '@/axios';

  export default {
    name: 'Dashboard',
    data() {
      return {
        articlesCount: 0,
        categoriesCount: 0,
        commentsCount: 0,
        viewsCount: 0,

        lastLoginTime:'',
        newComments: 0,
        newViews: 0,

        loading1: false,
        loading2: false,

        //https://v-charts.js.org/#/histogram
        chartSettings: {
          axisSite: { right: ['新浏览量'] },
          yAxisType: ['normal', 'normal'],
          // yAxisName: ['新评论', '新浏览量'],
          min: [0, 0]
        },
        chartData: {
          columns: ['recordCreateTime', 'newComments', 'newViews'],
          rows: []
        },

        enableAdmin: false,
      };
    },


    computed:{
      dot () {
        return (this.newComments>0)? true:false;
      }
    },

    created() {
      this.init();
    },


    methods: {
      init(){
        this.getDashboard();
        this.getRecords();
      },

      getDashboard() {
        this.loading1 = true;
        getRequest(this.enableAdmin ? '/admin/dashboardCount' :'/article/dashboardCount').then(resp => {
          this.loading1 = false;
          if (resp.status == 200) {
            const data = resp.data;
            this.articlesCount = data.articlesCount;
            this.categoriesCount = data.categoriesCount;
            this.commentsCount = data.commentsCount;
            this.viewsCount = data.viewsCount;
          }
        },err => {
          this.loading1 = false;
        }).catch(err => {
          this.loading1 = false;
          this.$message.error('操作失败');
        });
      },


      getRecords() {
        this.loading2 = true;
        getRequest(this.enableAdmin?'/admin/scheduleRecords':'/record').then(resp => {
          this.loading2 = false;
          if (resp.status == 200) {
            let data = resp.data;
            if (this.enableAdmin){
              this.chartData.rows = resp.data.reverse();
            }else{
              this.lastLoginTime = data.recordCreateTime;//后端把上次登入时间注入到record的recordCreateTime里
              this.newComments = data.newComments;
              this.newViews = data.newViews;
            }
          }
        },err => {
          this.loading2 = false;
        }).catch(err => {
          this.loading2 = false;
          this.$message.error('请求数据失败');
        });
      },

      beforeConfig(data) {
        data.rows.forEach((item, index) => {
          item.recordCreateTime = item.recordCreateTime.split(' ')[0];
        });
      },


      toCategory() {
        this.$router.push('/manage/category');
      },

      toArticle() {
        this.$router.push('/manage/article');
      },

      toComment() {
        this.$router.push('/manage/comment');
      }
    }
  };
</script>

<style scoped>

  .dashboard-box {
    /*padding: 15px 40px;*/
  }

  .col-box {
    margin-bottom: 20px;
  }

  .text {
    font-size: 30px;
  }


</style>
