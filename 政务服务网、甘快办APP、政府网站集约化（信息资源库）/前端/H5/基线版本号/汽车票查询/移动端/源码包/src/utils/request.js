import axios from 'axios'
import { jpassConfig } from './http'
import { Toast } from 'vant'
/* eslint-disable */
const { httpRequestJpaas } = jpassConfig
// const interface_id = 'showcomponent'
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
          const objData = JSON.parse(data.data.data)
          if (data.data.code == 200) {
            resolved(objData)
          } else {
            const obj = JSON.parse(JSON.parse(data.data.data).data)
            Toast({
              message: obj.data
            })
            rejected(obj)
          }
        } else {
          rejected(data)
        }
      })
        .catch(function (error) {
          rejected(error)
        })
    })
  },

  getData: function ({interface_id, app_id, params = {}}) {
    // console.log(id);
    const baseParams = new URLSearchParams()
   // baseParams.append('header', '{}')
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', new Date().getTime())
    baseParams.append('interface_id', interface_id)
    baseParams.append('app_id', app_id)
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
