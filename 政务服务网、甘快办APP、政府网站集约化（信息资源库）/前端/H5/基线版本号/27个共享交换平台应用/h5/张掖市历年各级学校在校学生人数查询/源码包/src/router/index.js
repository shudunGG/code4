import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/index",
  },
  {
    path: "/index",
    name: "index",
    component: () => import("../views/index.vue"),
    meta: {
      title: "张掖市历年各级学校在校学生人数查询",
      keepAlive: true,
      savedPosition: true,
    },
  },
  {
    path: "/detail",
    name: "detail",
    component: () => import("../views/detail.vue"),
    meta: {
      title: "张掖市历年各级学校在校学生人数查询",
      keepAlive: false,
      savedPosition: true,
    },
  },
];

const router = createRouter({
  // history: createWebHashHistory(import.meta.env.BASE_URL), // hash模式
  history: createWebHashHistory(), // hash模式
  // history: createWebHistory(import.meta.env.BASE_URL), // 历史模式
  routes,
  scrollBehavior(to, from, savedPosition) {
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
        y: to.meta.savedPosition || 0,
      };
    }
  },
});

export default router;
