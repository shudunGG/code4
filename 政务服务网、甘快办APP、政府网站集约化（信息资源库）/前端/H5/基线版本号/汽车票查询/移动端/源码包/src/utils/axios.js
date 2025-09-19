import axios from 'axios'
import Qs from 'qs'
// import store from '../store'
// import notification from 'ant-design-vue/es/notification'
// import Modal from 'ant-design-vue/es/modal'
// const Vue = window.Vue
const VueAxios = {
  vm: {},
  // eslint-disable-next-line no-unused-vars
  install (Vue, instance) {
    if (this.installed) {
      return
    }
    this.installed = true

    if (!instance) {
      // eslint-disable-next-line no-console
      console.error('You have to install axios')
      return
    }

    Vue.axios = instance

    Object.defineProperties(Vue.prototype, {
      axios: {
        get: function get () {
          return instance
        }
      },
      $http: {
        get: function get () {
          return instance
        }
      }
    })
  }
}
// 创建 axios 实例
const service = axios.create({
  baseURL: 'https://zwfw.gansu.gov.cn', // api base_url
  timeout: 60000, // 请求超时时间 60s
  ignoreError: false, // 忽略200服务器返回失败，不提示
  transformRequest: [function (data, headers) {
    if (headers['Content-Type']) {
      if (headers['Content-Type'] === 'multipart/form-data') {
        return data
      } else if (headers['Content-Type'].includes('application/json')) {
        return JSON.stringify(data)
      }
    }
    // 对 data 进行任意转换处理
    return Qs.stringify(data, { indices: false })
  }],
  transformResponse: [function (data) {
    return JSON.parse(data.startsWith('while') ? data.substr(9) : data)// 处理JSON劫持
  }]
})

const err = (error) => {
  if (axios.isCancel(error)) {
    return new Promise(() => { })
  }

  if (error.response) {
    switch (error.response.status) {
      case 500:
        Notification.error({
          key: 'Error',
          message: '内部错误',
          description: '服务器内部错误，请稍后重新尝试'
        })
        break
      case 403:
        Notification.error({
          key: 'Forbidden',
          message: '无权限访问',
          description: ((error.response || {}).data || {}).message || '您无权访问该页面'
        })
        break
      case 404:
        Notification.error({
          key: 'NotFound',
          message: '无法访问',
          description: ((error.response || {}).data || {}).message || '网络异常或服务无法访问，请稍后重新尝试'
        })
        break
      case 401:
        break
      default:
        Notification.error({
          key: 'Unknown',
          message: '未知错误',
          description: ((error.response || {}).data || {}).message || '未知错误，请稍后重新尝试'
        })
    }
  }
  return Promise.reject(error)
}

// response interceptor
service.interceptors.response.use(
  (response) => {
    const responseData = response.data
    if (responseData) {
      responseData.headers = response.headers
      const { success, message } = responseData
      // if (success || response.config.ignoreError) {
      //   return responseData
      // } else
      if (!success && !response.config.ignoreError) {
        return (message)
      }
      return responseData
    }
  },
  err
)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

// 取消请求
// const cancelRequests = () => {
//   store.state.requestCancels.forEach(cancel => {
//     cancel && cancel()
//   })
//   store.state.requestCancels = []
// }

export {
  installer as VueAxios,
  service as axios
  // cancelRequests
}
