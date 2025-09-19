import axios from 'axios'
import { jpassConfig } from '../api/HTTP'
import { Toast } from 'vant'
/* eslint-disable */
const { httpRequestJpaas } = jpassConfig
const interface_id = 'listInfoEnnzjk'
const requestJpaasCms = {

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
          // console.log(objData.code);
          if (objData.code == 200) {
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
  getDataCms: function ({ params = {} ,columnId = '' } = {}) {
    const baseParams = new URLSearchParams()
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', new Date().getTime())
    baseParams.append('interface_id', interface_id)
    baseParams.append('app_id', 'cmsnzjk')
    baseParams.append('biz_content', JSON.stringify(Object.assign({ columnId: columnId }, params)))
    return new Promise((resolved, rejected) => {
      requestJpaasCms.createSignJpaas(baseParams).then(sign => {
        baseParams.append('sign', sign)
        return requestJpaasCms.getDataFromJpaas(baseParams)
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
export default requestJpaasCms
