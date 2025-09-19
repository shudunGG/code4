/**
 * 导入 axios 和 Qs 库
 */
import axios from "axios"
import Qs from "qs"

/**
 * 创建一个 axios 实例
 * @returns 返回配置好的 axios 实例
 */
const service = axios.create({
  baseURL: "/jpaas-yyjrzzxt-ds-server", // 设置请求的基础 URL
  timeout: 60000, // 设置请求超时时间为 60 秒
  ignoreError: false, // 是否忽略 200 服务器返回失败的情况，默认不忽略
  // 请求发送前的处理函数，可以修改请求数据
  transformRequest: [
    function (data, headers) {
      // 根据请求头的 Content-Type 来决定数据的处理方式
      if (headers["Content-Type"]) {
        if (headers["Content-Type"] === "multipart/form-data") {
          // 如果是 multipart/form-data 类型，则不处理直接返回
          return data
        } else if (headers["Content-Type"].includes("application/json")) {
          // 如果是 JSON 类型，则转换为 JSON 字符串
          return JSON.stringify(data)
        }
      }
      // 默认使用 Qs 库来序列化数据
      return Qs.stringify(data, { indices: false })
    }
  ],
  // 响应返回后的处理函数，可以修改响应数据
  transformResponse: [
    function (data) {
      // 处理 JSON 劫持问题
      return JSON.parse(data.startsWith("while") ? data.substr(9) : data)
    }
  ]
})

// 配置响应拦截器，统一处理响应数据
service.interceptors.response.use((res) => {
  // 返回响应的 data 部分
  return res.data
})

/**
 * 导出配置好的 axios 实例
 */
export { service as axios }