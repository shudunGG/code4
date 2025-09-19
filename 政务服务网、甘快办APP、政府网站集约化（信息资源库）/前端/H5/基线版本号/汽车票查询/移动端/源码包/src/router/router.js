import Vue from 'vue'
import Router from 'vue-router'
import Index from '../views/index.vue'
Vue.use(Router)

export default new Router({
  // mode: "history",
  routes: [
    {
      path: '/',
      name: 'ticket',
      component: Index,
      mate: {
        title: '甘肃省内汽车票查询',
        keepAlive: true
      }
    },
    {
      path: '/ticketlist',
      name: 'ticketList',
      component: () => import('../views/ticketlist.vue'),
      mate: {
        title: '车票详情',
        keepAlive: false
      }
    },
    {
      path: '/demo',
      name: 'demo',
      component: () => import('../views/calendar/calendat.vue'),
      mate: {
        title: '车票详情'
      }
    },
    {
      path: '/demo12',
      name: 'demo12',
      component: () => import('../views/inputSelect.vue'),
      mate: {
        title: '车票详情'
      }
    }
  ],

  // 页面置顶
  scrollBehavior(to, from, savedPossition) {
    return { x: 0, y: 0 }
  }
})
