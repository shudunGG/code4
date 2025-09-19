import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/home.vue'),
    meta: {
      title: '张掖市非遗资源概况',
      keepAlive: false,
      savedPosition: true
    }
  },
  {
    path: '/details',
    name: 'details',
    component: () => import('../views/details.vue'),
    meta: {
      title: '张掖市非遗资源概况',
      keepAlive: false,
      savedPosition: true
    }
  }
]

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      if (from.meta.saveSrollTop) {
        from.meta.savedPosition =
          document.documentElement.scrollTop || document.body.scrollTop
      } else {
        to.meta.savedPosition = 0
      }
      return {
        x: 0,
        y: to.meta.savedPosition || 0
      }
    }
  }
})

export default router
