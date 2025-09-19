import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "detail",
      component: () => import("../views/detailView.vue"),
      meta: {
        title: "体检报告查询",
        keepAlive: false
      },
    },

    {
      path: "/info",
      name: "info",
      component: () => import("../views/infoView.vue"),
      meta: {
        title: "体检报告查询",
        keepAlive: false
      },
    },
    {
      path: "/infoDetail",
      name: "infoDetail",
      component: () => import("../views/infoDetailView.vue"),
      meta: {
        title: "体检报告查询",
        keepAlive: false
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
