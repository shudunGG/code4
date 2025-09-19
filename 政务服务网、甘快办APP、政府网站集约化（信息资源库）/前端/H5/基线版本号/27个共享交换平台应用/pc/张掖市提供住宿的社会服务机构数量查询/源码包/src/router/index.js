import { createRouter, createWebHashHistory } from 'vue-router'
// createWebHashHistory 哈希模式
// createWebHistory 历史模式
let routes = [
  {
    path: '/',
    redirect: { name: "index" }
  },
  {
    path: '/index',
    name: 'index',
    //使用import可以路由懒加载，如果不使用，太多组件一起加载会造成白屏
    component: () => import('@/view/index.vue'),
    children: [
      {
        path: '/results',
        name: 'results',
        component: () => import('@/view/results.vue'),
        beforeEnter: (to, from, next) => {
          if (
            from.name == "index" ||
            from.name == "fail" ||
            from.name == "results"
          ) {
            next()
          } else {
            next({
              name: "index"
            })
          }
        }
      },
      {
        path: '/fail',
        name: 'fail',
        component: () => import('@/view/fail.vue'),
        beforeEnter: (to, from, next) => {
          if (
            from.name == "index" ||
            from.name == "fail" ||
            from.name == "results"
          ) {
            next()
          } else {
            next({
              name: "index"
            })
          }
        }
      }
    ]
  },
 
]
// 路由
const router = createRouter({
  history: createWebHashHistory(),
  routes
})
// 导出路由器实例
export default router
