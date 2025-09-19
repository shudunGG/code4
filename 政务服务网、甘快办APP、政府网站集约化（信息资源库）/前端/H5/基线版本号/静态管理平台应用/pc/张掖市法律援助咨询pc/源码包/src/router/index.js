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
    redirect: { name: "Results" }
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import("../views/IndexView"),
    meta: {
      keepAlive: true
    },
    children: [
      {
        path: "/results",
        name: "Results",
        component: () => import("../views/ResultsView"),
        meta: {
          keepAlive: true
        },
        beforeEnter: (to, from, next) => {
          // console.log(from, to)
          if (from.name === "Index") {
            next()
          } else {
            next()
          }
        },
      },
      {
        path: "/no-results",
        name: "NoResults",
        component: () => import("../views/NoResultsView"),
        meta: {
          keepAlive: true
        },
      }
    ]
  },
  {
    path: '/zw',
    name: 'zw',
    component: () => import("../views/zwDetail"),
  }
]

const router = new VueRouter({
  routes,
  scrollBehavior(to, from, savedPosition) {
    console.log(to, from)
    // savedPosition当且仅当通过浏览器的前进/后退按钮触发时才可用
    if (to.path === '/zw') {
      if (savedPosition) {
        return savedPosition
      }
      return { x: 0, y: 180 }
    }

  }
})

export default router
