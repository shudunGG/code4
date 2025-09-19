import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "index",
      component: () => import("../views/index.vue"),
      meta: {
        title: "翻修、大修自住住房",
      }
    },
    // 申请材料提交页
    {
      path: "/detail",
      name: "detail",
      component: () => import("../views/detailView.vue"),
      meta: {
        title: "翻修、大修自住住房",
      },
    },
    // 申请成功
    {
      path: "/success",
      name: "success",
      component: () => import("../views/successView.vue"),
      meta: {
        title: "翻修、大修自住住房",
      },
    },
    {
      path: "/form",
      name: "form",
      component: () => import("../views/form.vue"),
      meta: {
        title: "翻修、大修自住住房",
      },
    },




  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      if (from.meta.saveSrollTop) {
        from.meta.savedPosition =
          document.documentElement.scrollTop || document.body.scrollTop;
      } else {
        to.meta.savedPosition = 0;
      }
      return {
        x: 0,
        y: to.meta.savedPosition || 0
      };
    }
  }
});
