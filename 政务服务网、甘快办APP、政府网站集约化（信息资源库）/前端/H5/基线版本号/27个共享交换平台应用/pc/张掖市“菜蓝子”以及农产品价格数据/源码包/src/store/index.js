// 导入createPinia和defineStore函数，用于创建和定义Pinia store
import { createPinia, defineStore } from 'pinia'
import piniaPersist from 'pinia-persist' 
// 创建Pinia实例
const pinia = createPinia();
pinia.use(piniaPersist)
/**
 * 定义一个名为'counter'的store，用于管理用户信息
 */

export const useUserStore = defineStore('counter', {
  // store的状态定义，初始化时userInfo为空对象
  state: () => ({
    userInfo: {}, // 用户信息的初始状态为空对象
  }),
  // 定义可以执行的动作
  actions: {
    /**
     * 更新用户信息
     * @param {Object} userInfo - 新的用户信息对象
     */
    setUserInfo (userInfo) {
      this.userInfo = userInfo // 更新用户信息
    }
  },
  // 在persist属性中配置要持久化的状态
  persist: {
    enabled: true,//开启存储
    strategies: [
      // 指定存储的位置以及存储的变量都有哪些，该属性可以不写，
      // 在不写的情况下，默认存储到sessionStorage 里面，默认存储state里面的所有数据。
      // { storage: sessionStorage, paths: ['userInfo', 'accountId', 'token'] }
      // { storage: localStorage, paths: ['token'] },
    ],

  }
})
// 导出Pinia实例供其他模块使用
export default pinia , piniaPersist