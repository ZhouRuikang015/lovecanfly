<template>
  <div class="header">
    <div class="header-left" style="font-size: 28px;font-weight: bold;color: #565a5f;height: 66px;line-height: 66px">
      <p>lovecanfly</p>
    </div>
    <div class="header-left">
          <el-menu :default-active="this.$route.path" class="navbar"
                   mode="horizontal"
                   active-text-color="#ffd04b" router>
            <el-menu-item index="/home/homeContent/briefArticles">广场</el-menu-item>
            <el-menu-item index="/home/homeContent/timeLine">归档</el-menu-item>
<!--            <el-menu-item index="3">关于我</el-menu-item>-->
          </el-menu>
    </div>
    <div class="header-right">
      <el-menu :default-active="activeIndex" style="float: right" class="navbar"
               mode="horizontal">
        <el-menu-item style="border-bottom: 0px !important;" index="4">
          <el-input
                  placeholder="搜索"
                  v-model="keyword"
                  class="search-input"
          >
            <i slot="suffix" class="el-input__icon el-icon-search" @click="changeKeyword"></i>
          </el-input>
        </el-menu-item>

        <el-menu-item v-if="this.$store.getters.nickname" style="border-bottom: 0px !important;" index="5">
          <el-dropdown trigger="click" @command="handleCommand">
                    <span class="dropdown-behave">
                      {{this.$store.getters.nickname}}
                    </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="toManage">我的后台管理</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>

        <el-menu-item v-else style="border-bottom: 0px !important;" index="6">
          <el-button type="primary" @click="toLogin">登录</el-button>
          <el-button type="info" plain @click="dialogVisible = true">注册</el-button>
        </el-menu-item>
      </el-menu>
    </div>



      <el-dialog
              title="注册"
              :visible.sync="dialogVisible"
              width="30%"
              >
        <el-form :model="user" :rules="rules" ref="reg" label-width="0px" class="ms-content">
          <el-form-item prop="username">
            <el-input v-model="user.username" placeholder="用户名">
              <el-button slot="prepend" icon="fa fa-user"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                    type="password"
                    placeholder="密码"
                    v-model="user.password"
                    @keyup.enter.native="submitForm()"
            >
              <el-button slot="prepend" icon="fa fa-lock"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item prop="nickname">
            <el-input v-model="user.nickname" placeholder="昵称">
              <el-button slot="prepend" icon="fa fa-smile-o"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input v-model="user.email" placeholder="email">
              <el-button slot="prepend" icon="el-icon-message"></el-button>
            </el-input>
          </el-form-item>
          <el-switch
                  style="display: block; text-align: center"
                  v-model="user.gender"
                  active-color="#F064B7"
                  inactive-color="#2AC0F0"
                  active-text="♀"
                  inactive-text="♂">
          </el-switch>
        </el-form>
        <span slot="footer" class="dialog-footer" style="text-align: center">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="warning" @click="submitForm()">注 册</el-button>
          </span>
      </el-dialog>
  </div>

</template>

<script>
  import {postRequest} from "@/axios";

  export default {
    data() {
      return {
        keyword: '',
        activeIndex:'',


        dialogVisible:false,
        user: {
          username: '',
          password: '',
          email:'',
          nickname:'',
          gender: false
        },
        rules: {
          username: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, message: '姓名过短', trigger: 'blur' },
            { max: 20, message: '姓名过长', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 1, message: '姓名过短', trigger: 'blur' },
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
          ]
        }
      }
    },


    methods: {
      handleCommand(command) {
        if (command == 'logout') {
          postRequest('/logout').then( resp => {
            if (resp.status == 200) {
              this.$store.commit('logout');
              this.$router.push('/');
            }})
        } else if (command == 'toManage') {
          this.$router.push('/manage');
        }

      },

      changeKeyword(){
        this.$store.commit('changeKeyword',this.keyword);
      },

      toLogin() {
        this.$router.push('/login');
      },

      submitForm() {
        this.$refs.reg.validate(valid => {
          if (valid) {
            postRequest('/home/reg', {
              username: this.user.username,
              password: this.user.password,
              nickname: this.user.nickname,
              email: this.user.email,
              gender: this.user.gender? 0: 1,
            }).then(resp => {
              if (resp.status == 200) {
                this.dialogVisible = false;
                this.user.username = '',
                this.user.password = '',
                this.user.nickname = '',
                this.user.gender = false
              }
            })

          }
        })
      }
    }

  }
</script>

<style scoped>
  .header {
    /*position: relative;*/
    width: 100%;
    height: 66px;
    font-size: 22px;
    background: #ffffff;
    box-shadow: 0 2px 10px 5px rgba(0, 0, 0, 0.1);
    color: #fff;
    border: 0;
    margin: 0;

  }

  .header-left{
    float: left;
    padding-left: 60px;
  }

  .header-right{
    float: right;
    padding-right: 30px;
  }

  /deep/ .el-menu.el-menu--horizontal{
    border: 0;
  }

  /deep/ .el-menu-item{
     font-size: 20px;
    font-weight: bold;
  }


  .dropdown-behave {
    color: #2d8cf0;
    cursor: pointer;
  }


  .search-input {
    width: 150px;
  }
</style>
