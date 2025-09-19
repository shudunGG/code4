import Vue from "vue";
import Router from "vue-router";
import Index from "./src/index.vue";
Vue.use(Router);

export default new Router({
  mode: 'hash',
  // mode: "history",
  // base: '/dist/',
  routes: [
    {
      path: "/",
      name: "index",
      component: Index,
      meta: {
        title: '卫生许可证查询',
        keepAlive: false, //此组件不需要被缓存
        isBack: false, //用于判断上一个页面是哪个
      },
    },
  ]
});