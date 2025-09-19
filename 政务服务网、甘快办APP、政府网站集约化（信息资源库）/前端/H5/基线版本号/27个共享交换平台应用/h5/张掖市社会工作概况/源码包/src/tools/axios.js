import axios from 'axios'
import router from '../router'
import Qs from 'qs'

// 创建 axios 实例
const service = axios.create({
  baseURL: '',
  timeout: 20000, // 请求超时时间
  transformRequest: [
    function (data, headers) {
      if (headers['Content-Type'] === 'multipart/form-data') {
        return data
      } else if (headers['Content-Type'] === 'application/json') {
        return JSON.stringify(data)
      } else if (headers['Content-Type'] === 'text/plain') {
        return JSON.stringify(data)
      } else {
        // 对 data 进行任意转换处理
        return Qs.stringify(data)
      }
    }
  ]
})

// 添加请求拦截器
service.interceptors.request.use(
  (config) => {
    // 在发送请求之前做些什么
    return config
  },
  (error) => {
    // 对请求错误做些什么
    return Promise.reject(error)
  }
)

// 添加响应拦截器
service.interceptors.response.use(
  (response) => {
    // 对响应数据做点什么
    return response
  },
  (err) => {
    const config = err.config
    // 设置重新请求次数
    config.retry = 2
    // 设置请求延时
    config.retryDelay = 5000
    // 如果配置不存在或未设置重试选项，则返回错误信息
    if (!config || !config.retry) return Promise.reject(err)
    // 设置变量即跟踪重试次数
    config.retryCount = config.retryCount || 0
    // 检查我们是否已经超过了总重试次数
    if (config.retryCount > config.retry) {
      // 重置
      config.retryCount = 0
      // 跳转error页面
      router.push({ name: 'error' })
      // 返回错误信息
      return Promise.reject(err)
    }

    // 重试次数加1
    config.retryCount++

    // 创建延时器等待发送重试请求
    const backoff = new Promise((resolve) => {
      setTimeout(() => {
        resolve()
      }, config.retryDelay || 1)
    })

    // 返回调用axios来重试请求
    return backoff.then(() => {
      return service(config)
    })
  }
)
export { service as axios }
