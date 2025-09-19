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
    name: 'HospitalHomePage',
    redirect: '/index',
    component: () => import("../views/HospitalHomePage"),
    children: [
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
        path: "/no-results",
        name: "NoResults",
        component: () => import("../views/NoResultsView"),
      },
    ]
  },
  
]

const router = new VueRouter({
  routes
})

export default router
