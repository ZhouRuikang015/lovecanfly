<template>
  <div class="comment-card">
    <div style="padding: 20px">

      <el-form :model="commentator" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item prop="content">
          <el-input type="textarea" v-model="commentator.content" placeholder="写下你的评论" :rows="5"></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item prop="name">
              <el-input v-model="commentator.name" placeholder="* 姓名" v-if="!this.$store.state.user.nickname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" >
            <el-form-item style="margin-left: 0 !important;" prop="email"  >
              <el-input v-model="commentator.email" placeholder="* 邮箱" v-if="!this.$store.state.user.email"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item style="float: right">
              <el-button type="primary" @click="submitForm('ruleForm')">发布</el-button>
              <el-button plain type="info" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div class="comment-divider">
        <i style="font-weight: 400;font-size: 21px" class="fa fa-sticky-note-o"> 全部评论 {{pageTotal}}</i>
      </div>

      <div v-for="(item,index) in articleComments"  class="comment-body">
        <ul class="ul-text">
          <li style="font-size: 20px;color: #101323">
            <i class="fa fa-user-o"> {{item.commentatorName}}</i>
          </li>
          <li>
            <i class="fa fa-calendar-o"> {{item.commentCreateTime}}</i>
          </li>
          <li>
            # {{pageTotal-index-(query.curPage-1)*query.pageSize}}楼
          </li>
        </ul>
        <p class="content-body">{{item.commentContent}}</p>

        <div v-if="item.replyContent" class="reply-body">
          <ul class="ul-text">
            <li >
              <i style="font-size: 15px" class="fa fa-reply">   作者回复</i>
            </li>
            <li>
              <i class="fa fa-calendar-check-o"> {{item.commentCreateTime}}</i>
            </li>
          </ul>
           <p class="content-body">{{item.replyContent}}</p>
        </div>
      </div>

      <div class="pagination">
        <el-pagination
                background
                layout="total, prev, pager, next"
                :current-page="query.curPage"
                :page-size="query.pageSize"
                :total="pageTotal"
                @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>
  </div>

</template>

<script>
  import { getRequest, postRequest } from '@/axios';

  export default {
    name: 'ArticleComment',

    // props: {
    //   articleId: {
    //     default: ''
    //   },
    // },


    data() {
      return {
        commentator: {
          name: this.$store.state.user ? this.$store.state.user.nickname:'',
          email: this.$store.state.user ? this.$store.state.user.email:'',
          content: ''
        },



        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, message: '姓名过短', trigger: 'blur' },
            { max: 20, message: '姓名过长', trigger: 'blur' }
          ],
          email: [
            {
              required: true,
              message: '请输入邮箱地址',
              trigger: 'blur'
            },
            {
              type: 'email',
              message: '邮箱格式不对',
              trigger: ['blur', 'change']
            }
          ],
          content: [
            { required: true, message: '评论不可为空哦！', trigger: 'blur' }
          ]
        },

        articleComments:[],

        pageTotal: 0,

        query:{
          curPage:1,
          pageSize:10,
          articleId:this.$store.state.articleId,
        }


      };
    },

    created(){
      this.getComments();
    },

    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            postRequest('/home/comment/',{
              articleId: this.query.articleId,
              commentatorName:this.commentator.name,
              commentatorEmail:this.commentator.email,
              commentContent:this.commentator.content,
            }).then(resp => {
              if (resp.status == 200){
                this.$message.warning('等待作者审核');
                this.commentator.content = '';
              }
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      handlePageChange(val) {
        this.curPage = val;
        this.getComments();
      },

      getComments(){
        getRequest('/home/comment/',this.query).then( resp => {
          if (resp.status == 200){
            this.articleComments = resp.data.list;
            this.pageTotal = resp.data.total;
          }
        })


      }
    }
  };
</script>

<style scoped>

  /deep/ .el-form-item__content {
    margin-left: 0 !important;
  }

  .ul-text > li {
    display: inline;
    margin-right: 20px;
  }


  .comment-card {

    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    border-radius: 6px;
    margin-bottom: 20px;
  }

  .comment-divider {
    margin: 30px 0px;
    border-top: 2px solid rgba(0, 0, 0, 0.18);
    padding: 15px 10px;
  }

  .content-body {
    font-weight: 500;
    font-size: 18px;
    color: #1b1f23;
    padding: 20px 0px 20px 10px;

  }

  .comment-body {
    font-size: 12px;
    color: #7a7a7a;
    margin: 20px;
    border-bottom: 1px solid #d5dce6;
  }

  .reply-body {
    margin: 10px 0px;
    padding-left: 40px;
  }

  .pagination {
    margin: 20px 0;
    text-align: center;
  }
</style>
