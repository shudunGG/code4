import Vue from 'vue'
import Router from 'vue-router'
import Index from './views/index.vue'
import ListDetail from './views/detail'
Vue.use(Router)

export default new Router({
  // mode: "history",
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      meta: {
        title: '张掖市律师事务所查询',
        keepAlive: true, // 此组件不需要被缓存
        isBack: false, // 用于判断上一个页面是哪个
      },
    },
    {
      path: '/listDetail',
      name: 'listDetail',
      component: ListDetail,
      meta: {
        title: '张掖市律师事务所查询',
        keepAlive: false,
        isBack: false,
      },
    },
  ],
})
