<template>
  <div class="sidebar">
    <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse=this.$store.state.collapse
            background-color=rgba(0,0,0,0.8)
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
    >
      <template v-for="(item,index) in this.$router.options.routes" v-if="hasRole(item)">
        <template v-if="item.children.length>1">
          <!--                :index 为跳转path,需为字符串-->
          <el-submenu :index="index+''" :key="index">
            <template slot="title">
              <i :class="item.iconCls"></i>
              <span slot="title">{{item.name}}</span>
            </template>
            <el-menu-item v-for="child in item.children" v-if="!child.hidden" :index="child.path" :key="child.path">
              {{child.name}}
            </el-menu-item>
          </el-submenu>
        </template>

        <template v-else>
          <el-menu-item :index="item.children[0].path" :key="item.children[0].path">
            <i :class="item.children[0].iconCls"></i>
            <span slot="title">{{item.children[0].name}}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>

  export default {
    computed: {

      onRoutes() {
        return this.$route.path.replace('/', '');
      }

    },

    methods: {
      hasRole(item) {
        if (item.hidden) return false;

        let userRoles = JSON.parse(localStorage.getItem('user')).roles.map(role => role.roleName);
        let routerRoles = item.meta.roles;
        return (routerRoles == undefined) ? true : (routerRoles.find(routerRole => userRoles.indexOf(routerRole) != -1));
      },


    }

  };
</script>

<style scoped>

  /deep/ .el-menu{
    border-right: 0px;
  }

  .sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 54px;
    bottom: 0;
    overflow-y: scroll;
  }

  .sidebar::-webkit-scrollbar {
    width: 0;
  }

  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 200px;
  }

  .sidebar > ul {
    height: 100%;
  }
</style>
