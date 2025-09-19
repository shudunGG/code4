import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    redirect: { name: "Index" }
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import("../views/IndexView"),
  },
  {
    path: "/drResult",
    name: "DrResult",
    component: () => import("../views/DrResult.vue"),
  },
  {
    path: "/zyy_result",
    name: "ZyyResult",
    component: () => import("../views/ZyyResult.vue"),
  },
  {
    path: '/zy_report',
    name: 'ZyReport',
    component: () => import('../views/ZyReport.vue')
  },
  {
    path: "/no-results",
    name: "NoResults",
    component: () => import("../views/NoResultsView"),
  },
  {
    path: '/fsyy_result',
    name: 'FsyyResult',
    component: () => import('../views/FsyyResult.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
