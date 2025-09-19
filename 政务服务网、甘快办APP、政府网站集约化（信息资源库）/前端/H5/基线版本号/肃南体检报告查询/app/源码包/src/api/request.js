import axios from 'axios'
import Qs from 'qs'
// 创建 axios 实例
const service = axios.create({
  baseURL: '/jpaas-yyjrzzxt-ds-server', // api base_url
  timeout: 60000, // 请求超时时间 60s
  ignoreError: false, // 忽略200服务器返回失败，不提示
  transformRequest: [
    function (data, headers) {
      // 表示允许在向服务器发送前，修改请求数据
      if (headers['Content-Type']) {
        if (headers['Content-Type'] === 'multipart/form-data') {
          return data
        } else if (headers['Content-Type'].includes('application/json')) {
          return JSON.stringify(data)
        }
      }
      // 对 data 进行任意转换处理
      return Qs.stringify(data, { indices: false })
    }
  ],
  transformResponse: [
    function (data) {
      return JSON.parse(data.startsWith('while') ? data.substr(9) : data) // 处理JSON劫持
    }
  ]
})
service.interceptors.response.use((res) => {
  return res.data
})
export { service as axios }
