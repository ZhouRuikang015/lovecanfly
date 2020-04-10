<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">lovecanfly</div>
            <el-form :model="user" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="user.username" placeholder="username">
                        <el-button slot="prepend" icon="fa fa-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="user.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="fa fa-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import { getRequest, postRequest } from '@/axios';

export default {
    data: function() {
        return {
            user: {
                username: 'zrk',
                password: '123456',
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
        };
    },
    methods: {
        submitForm() {
            this.$refs.login.validate(valid => {
                if (valid) {
                    postRequest('/login', {
                        username: this.user.username,
                        password: this.user.password
                    }).then(resp => {
                      if (resp.status == 200) {
                          getRequest('/currentUser').then( resp => {
                              if (resp.status == 200){
                                  var user = resp.data;
                                  this.$store.commit('login',user);
                                  var path = this.$route.query.redirect;
                                  this.$router.replace({path: path == '/' || path == undefined ? '/manage' : path});

                              }
                          }).catch( (err)=>{
                              this.$message.error('操作失败')
                          });
                      }
                    })

                }
            })
        },



    }
}
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/login_bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 21px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
