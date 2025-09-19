import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('../views/index.vue'),
    meta: {
      title: '首页',
      keepAlive: true,
      savedPosition: true
    }
  },
  {
    path: '/deymDetail',
    name: 'deymDetail',
    component: () => import('../views/DeymDetail.vue'),
    meta: {
      title: '详情页',
      keepAlive: true,
      savedPosition: true
    }
  },
  {
    path: '/pdfBrow',
    name: 'pdfBrow',
    component: () => import('../views/pdfBrow.vue'),
    meta: {
      title: '预览页',
      keepAlive: true,
      savedPosition: true
    }
  },
  {
    path: '/zyyy',
    name: 'detail',
    component: () => import('../views/ZyyyDetail.vue'),
    meta: {
      title: '详情页',
      keepAlive: true,
      savedPosition: true
    }
  },
  {
    path: '/fsyy_detail',
    name: 'FsyyDetail',
    component: () => import('../views/FsyyDetail.vue'),
    meta: {
      title: '详情页',
      keepAlive: true,
      savedPosition: true
    }
  },
  {
    path: '/report',
    name: 'report',
    component: () => import('../views/ZyyyReport.vue'),
    meta: {
      title: '详情页',
      keepAlive: true,
      savedPosition: true
    }
  }
]

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior (to, from, savedPosition) {
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
