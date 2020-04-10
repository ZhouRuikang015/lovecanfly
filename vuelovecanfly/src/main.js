import Vue from 'vue'
import App from './App.vue'
import store from './store';
import 'font-awesome/css/font-awesome.min.css'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI, {
  size: 'small'
});

import VeHistogram from 'v-charts/lib/histogram.common';
Vue.component(VeHistogram.name, VeHistogram);

import router from './router';
router.beforeEach((to, from, next) => {
      if (to.name == 'Login') {
        next();
        return;
      }
      var username = store.state.user.username;
      var userRoles = store.state.user.roles.map(role => role.roleName);
      if (username == 'NotLogin') {
        if (to.name == null) {
          Vue.prototype.$message.error('访问地址有误噢！');
          next(from.path);
        } else if ( to.meta.requireAuth) {
          Vue.prototype.$message.error('尚未登录，需登录哦~');
          next({ path: '/login', query: { redirect: to.path } });
        } else {
          next();
        }
      } else {
        var isAllowed = to.meta.roles == null ? true : to.meta.roles.filter(role => userRoles.indexOf(role) != -1).length > 0;
        if (to.name == null) {
          Vue.prototype.$message.error('访问地址有误噢！');
          next(from.path);
        } else if (!isAllowed) {
          Vue.prototype.$message.error('无访问权限呀！');
          next(false);
        } else {
          next();
        }

      }
    }
);

Vue.config.productionTip = false;

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');
