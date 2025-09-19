import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)
const routes = [{
    path: '/',
    redirect: {
      name: "Index"
    }
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import("../views/IndexView"),
    meta: {
        keepAlive: true
      }
  },
  {
    path: "/results",
    name: "Results",
    component: () => import("../views/ResultsView"),
  },
  {
    path: "/no-results",
    name: "NoResults",
    component: () => import("../views/NoResultsView"),
  }


]

const router = new VueRouter({
  routes
})

export default router