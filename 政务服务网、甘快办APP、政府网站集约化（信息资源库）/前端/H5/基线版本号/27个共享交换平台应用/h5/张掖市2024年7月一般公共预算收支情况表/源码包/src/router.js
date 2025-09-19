import Vue from "vue";
import Router from "vue-router";
import result from "./views/result";

Vue.use(Router);

export default new Router({
  // mode: "history",
  routes: [
    {
      path: "/",
      name: "result",
      component: result,
      meta: {
        title: "张掖市7月居民价格消费指数市发展改革委",
        keepAlive: true, // 此组件不需要被缓存
        isBack: false, // 用于判断上一个页面是哪个
      },
    },
  ],
});
