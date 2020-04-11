<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if=!this.$store.state.collapse class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="logo">lovecanfly for {{this.$store.getters.nickname}}
        </div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>

                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        <!-- 用户头像 -->
                        <div class="user-avator">
                            <img v-if="this.$store.state.user.gender === 1"  src= "@//assets/img/avatar_man.jpg"/>
                            <img v-else src= "@/assets/img/avatar_woman.png"/>
                        </div>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="home">广场</el-dropdown-item>
                        <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            fullscreen: false,
        };
    },

    methods: {
        // 用户名下拉菜单选择事件
        handleCommand(command) {
            if (command == 'logout') {
                postRequest('/logout').then( resp => {
                    if (resp.status == 200) {
                        this.$store.commit('logout');
                        this.$router.replace('/login');
                    }})
            }else if (command == 'home'){
                this.$router.push('/');
            }
        },
        // 侧边栏折叠
        collapseChage() {
            this.$store.commit('change')
        },
        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        }
    },
    mounted() {
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    }
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 54px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 54px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 54px;
}
.header-right {
    float: right;
    padding-right: 40px;
}
.header-user-con {
    display: flex;
    height: 54px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 0px;
}
.user-avator img {
    display: block;
    width: 36px;
    height: 36px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
</style>
