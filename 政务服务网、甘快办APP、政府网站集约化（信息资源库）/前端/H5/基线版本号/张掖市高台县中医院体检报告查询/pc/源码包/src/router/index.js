import Vue from 'vue'
import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)
const routes = [

  {
    path: '/',
    name: 'HospitalHomePage',
    redirect: '/department',
    component: () => import("../views/HospitalHomePage"),
    children: [
      {
        path: "/department",
        name: "department",
        component: () => import("../views/SelectDepartment")
      },
      {
        path: "/doctor",
        name: "doctor",
        component: () => import("../views/DoctorInfo")
      },
      {
        path: "/infoDetail",
        name: "infoDetail",
        component: () => import("../views/infoDetailView"),
      },
    ]
  },
  {
    path: "/no-results",
    name: "NoResults",
    component: () => import("../views/NoResultsView"),
  }

]

const router = new VueRouter({
  // mode: "history",
  // base: process.env.BASE_URL,
  routes
})

export default router
