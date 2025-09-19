import axios from 'axios'
import { jpassConfig } from '../api/HTTP'
const { httpRequestJpaas } = jpassConfig
const requestJpaas = {
  // Jpaas签名
  createSignJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: 'post',
        url: `${httpRequestJpaas}createsign`,
        data: params
      }).then(function (data) {
        if (data.data.success) {
          const { sign } = data.data.data
          resolved(sign)
        } else {
          rejected(data)
        }
      })
        .catch(function (error) {
          rejected(error)
        })
    })
  },
  getDataFromJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: 'post',
        url: `${httpRequestJpaas}gateway`,
        data: params
      }).then(function (data) {
        if (data.data.code == 200 && data.data.success == true) {
          resolved(data.data)
        } else {
          rejected(data)
        }
      })
        .catch(function (error) {
          rejected(error)
        })
    })
  },
  getDataBlob: function ({ appid, interface_id, params = {} } = {}) {
    const baseParams = new URLSearchParams()
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', new Date().getTime())
    baseParams.append('interface_id', interface_id)
    baseParams.append('app_id', appid)
    baseParams.append('biz_content', JSON.stringify(params))
    return new Promise((resolved, rejected) => {
      requestJpaas.createSignJpaas(baseParams).then(sign => {
        baseParams.append('sign', sign)
        return requestJpaas.getDataFromJpaas(baseParams)
      }).then(response => {
        try {
          var obj = JSON.parse(response)
          if (typeof obj === 'object' && obj) {
            resolved(JSON.parse(response))
          } else {
            resolved(response)
          }
        } catch (e) {
          resolved(response)
        }
      }).catch(error => {
        rejected(error)
      })
    })
  }
}
export default requestJpaas
