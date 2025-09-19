/*
 * @Author: your name
 * @Date: 2020-05-30 17:59:24
 * @LastEditTime: 2021-11-26 16:12:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /health-code/src/router.js
 */
import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  // mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("./views/home.vue"),
      mate: {
        title: "营商环境专区",
        keepAlive: true,
      },
    },
    {
      path: "/notify",
      name: "notify",
      component: () => import("./views/notify.vue"),
      mate: {
        title: "营商环境专区",
        keepAlive: false,
      },
    },
    {
      path: "/more",
      name: "more",
      component: () => import("./views/more.vue"),
      mate: {
        title: "营商环境专区",
        keepAlive: false,
      },
    },
  ],
});
