
import { createRouter, createWebHashHistory } from 'vue-router'
// createWebHashHistory 哈希模式
// createWebHistory 历史模式
let routes = [
  {
    path: '/',
    name: 'index',
    //使用import可以路由懒加载，如果不使用，太多组件一起加载会造成白屏
    component: () => import('@/view/index.vue'),
    meta: {
      title: '张掖市猪粮比价',
      keepAlive: false
    }
  }
]

/**
 * 创建并配置路由器实例
 * 使用哈希模式的路由历史记录
 * 配置了自定义的滚动行为
 * @returns {Object} 返回配置好的路由器实例
 */
const router = createRouter({
  history: createWebHashHistory(), // 使用哈希历史记录模式
  routes, // 路由配置
  // 自定义滚动行为
  scrollBehavior (to, from, savedPosition) {
    // 如果有保存的滚动位置，则返回该位置
    if (savedPosition) {
      return savedPosition
    } else {
      // 判断从哪个页面跳转来，并处理滚动位置保存
      if (from.meta.saveSrollTop) {
        from.meta.savedPosition =
          document.documentElement.scrollTop || document.body.scrollTop
      } else {
        // 如果来源页面没有保存滚动位置，则目标页面滚动位置设为0
        to.meta.savedPosition = 0
      }
      // 返回滚动位置配置
      return {
        x: 0,
        y: to.meta.savedPosition || 0
      }
    }
  }
})
// 导出路由器实例
export default router
