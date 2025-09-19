import Vue from "vue";
import VueRouter from "vue-router";

const originalPush = VueRouter.prototype.push;
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

Vue.use(VueRouter);
const routes = [
  {
    path: "/",
    redirect: { name: "Index" },
  },
  {
    path: "/index",
    name: "Index",
    component: () => import("../views/IndexView"),
  },
  {
    path: "/payfee",
    name: "Payfee",
    component: () => import("../views/PayFee"),
  },
];

const router = new VueRouter({
  routes,
  scrollBehavior() {
    // 解决vue页面之间跳转，页面不是在顶部的问题
    return { x: 0, y: 0 };
  },
});

export default router;
