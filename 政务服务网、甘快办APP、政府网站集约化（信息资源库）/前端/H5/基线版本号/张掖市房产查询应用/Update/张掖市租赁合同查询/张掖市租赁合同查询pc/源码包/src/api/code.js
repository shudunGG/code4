import axios from 'axios'
/* eslint-disable */
const httpRequestJpaas = 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/'
// 获取校验码
import Hex from './hex.js'
import SM3Digest from './sm3-1.0.js'
let time = new Date().getTime() // 获取当前时间戳

export const requestJpaas = {
  // Jpaas签名
  createSignJpaas: (params) => {
    var inputtext = 'app_id=' + params.get('app_id') + '&biz_content=' + params.get('biz_content') + '&charset=' + 'UTF-8' + '&interface_id=' + params.get('interface_id') + '&origin=' + '1' + '&timestamp=' + time + '&version=' + params.get('version')
    // console.log('生成验签的内容:', inputtext)
    var dataBy = Hex.utf8StrToBytes(inputtext)
    var sm3 = new SM3Digest()
    sm3.update(dataBy, 0, dataBy.length)
    var sm3Hash = sm3.doFinal()
    var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length)
    // console.log('生成的验签:', sm3HashHex.toLowerCase())
    return sm3HashHex.toLowerCase()
  },
  getDataFromJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: 'post',
        url: `${httpRequestJpaas}gateway`,
        data: params,
      })
        .then(function (data) {
          if (data.data.code == 200 && data.data.success == true) {
            const objData = JSON.parse(data.data.data)
            if (objData.code == 200) {
              resolved(objData)
            } else {
              const obj = JSON.parse(JSON.parse(data.data.data).data)
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
  getData: function ({ columnId = '', params = {} } = {}) {
    const baseParams = new URLSearchParams()
    baseParams.append('header', '{}')
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', time)
    baseParams.append('interface_id', 'showVerifyCode')
    baseParams.append('app_id', 'yzm')
    baseParams.append('biz_content', JSON.stringify(Object.assign({})))
    return new Promise((resolve, reject) => {
      const sign = requestJpaas.createSignJpaas(baseParams)
      baseParams.append('sign', sign)
      const response = requestJpaas.getDataFromJpaas(baseParams)
      setTimeout(() => {
        time = new Date().getTime() // 1秒后重新赋值
      }, 1000)
      try {
        var obj = JSON.parse(response)
        if (typeof obj === 'object' && obj) {
          resolve(JSON.parse(response))
        } else {
          resolve(response)
        }
      } catch (e) {
        resolve(response)
      }
    })
  }
}

// 验证校验码
export const requestJpaas1 = {
  // Jpaas签名
  createSignJpaas: (params) => {
    var inputtext = 'app_id=' + params.get('app_id') + '&biz_content=' + params.get('biz_content') + '&charset=' + 'UTF-8' + '&interface_id=' + params.get('interface_id') + '&origin=' + '1' + '&timestamp=' + time + '&version=' + params.get('version')
    // console.log('生成验签的内容:', inputtext)
    var dataBy = Hex.utf8StrToBytes(inputtext)
    var sm3 = new SM3Digest()
    sm3.update(dataBy, 0, dataBy.length)
    var sm3Hash = sm3.doFinal()
    var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length)
    // console.log('生成的验签:', sm3HashHex.toLowerCase())
    return sm3HashHex.toLowerCase()
  },
  getDataFromJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: 'post',
        url: `${httpRequestJpaas}gateway`,
        data: params,
      })
        .then(function (data) {
          const objData = JSON.parse(data.data.data)
          resolved(objData)
        })
        .catch(function (error) {
          rejected(error)
        })
    })
  },
  getData: function (data) {
    const baseParams = new URLSearchParams()
    baseParams.append('header', '{}')
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', time)
    baseParams.append('interface_id', 'cheackCaptcha')
    baseParams.append('app_id', 'yzm')
    baseParams.append('biz_content', JSON.stringify(Object.assign(data)))
    return new Promise((resolve, reject) => {
      const sign = requestJpaas1.createSignJpaas(baseParams)
      baseParams.append('sign', sign)
      const response = requestJpaas1.getDataFromJpaas(baseParams)
      setTimeout(() => {
        time = new Date().getTime() // 1秒后重新赋值
      }, 1000)
      try {
        var obj = JSON.parse(response)
        if (typeof obj === 'object' && obj) {
          resolve(JSON.parse(response))
        } else {
          resolve(response)
        }
      } catch (e) {
        resolve(response)
      }
    })
  }
}
