/* eslint-disable camelcase */
/* eslint-disable no-useless-catch */
import { axios } from './axios'
import { baseUrl, app_id } from './config'
import CryptoJS from 'crypto-js'
import { sm2, sm3, sm4 } from 'sm-crypto'
// 获取验签
import Hex from './hex.js'
import SM3Digest from './sm3-1.0.js'
let time = new Date().getTime() // 获取当前时间戳
const createSign = (params) => {
  var inputtext = 'app_id=' + params.app_id + '&biz_content=' + JSON.parse(JSON.stringify(params.biz_content)) + '&charset=' + 'UTF-8' + '&interface_id=' + params.interface_id + '&origin=' + '1' + '&timestamp=' + time + '&version=' + '1.0'
  // 这一步是先将输入数据转成utf-8编码的字节流，然后再转成16进制可见字符
  // inputtext 为加密内容
  console.log('生成验签的内容:', inputtext)
  var dataBy = Hex.utf8StrToBytes(inputtext)
  var sm3 = new SM3Digest()
  sm3.update(dataBy, 0, dataBy.length) // 数据很多的话，可以分多次update
  var sm3Hash = sm3.doFinal() // 得到的数据是个byte数组
  var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length) // 编码成16进制可见字符
  console.log('生成的验签:', sm3HashHex.toLowerCase())
  return sm3HashHex.toLowerCase()
}


const gateWay = async (data = {}) => {
  const res = await axios({
    method: 'post',
    url: baseUrl + 'gateway.do',
    data,
    headers: {
      'content-type': 'application/x-www-form-urlencoded'
    }
  })
  if (res.data.success) {
    const { data } = res.data
    return data
  }
}

/**
 * 网关请求
 * @param {*} loading 是否展示loading效果。默认开启
 * @param {*} app_id 应用id。
 * @param {*} interface_id 接口id
 * @param {Object} params 业务参数
 * @param {Object} header 业务接口的请求头
 * @returns
 */
const request = async (params) => {
  try {
    const baseParams = {
      version: '1.0',
      charset: 'UTF-8',
      origin: '1',
      timestamp: time,
      interface_id: params.interface_id,
      app_id: params.app_id ? params.app_id : app_id,
      biz_content: params.biz_content,
      header: params.header || '{}'
    }
    const sign = createSign(baseParams)
    baseParams.sign = sign
    const result = await gateWay(baseParams)
    setTimeout(() => {
      time = new Date().getTime(); // 1秒后重新赋值
    }, 1000);
    return result
  } catch (error) {
    throw error
  }
}

// sm2加密网关请求
const requestWithSM2 = async (params, needencry) => {
  if (needencry && needencry.need && params.biz_content) {
    params.biz_content =
      '04' + sm2.doEncrypt(params.biz_content, needencry.key, 1)
  }
  try {
    const baseParams = {
      version: '1.0',
      charset: 'UTF-8',
      origin: '1',
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id ? params.app_id : app_id,
      biz_content: params.biz_content,
      header: params.header || '{}'
    }
    const sign = await createSign(baseParams)
    baseParams.sign = sign
    const result = await gateWay(baseParams)
    return result
  } catch (error) {
    throw error
  }
}

// sm4加密网关请求
const requestWithSM4 = async (params, needencry) => {
  if (needencry && needencry.need && params.biz_content) {
    params.biz_content = sm4.encrypt(
      params.biz_content,
      needencry.key
    )
  }
  try {
    const baseParams = {
      version: '1.0',
      charset: 'UTF-8',
      origin: '1',
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id || app_id,
      biz_content: params.biz_content,
      header: params.header || '{}'
    }
    const sign = await createSign(baseParams)
    baseParams.sign = sign
    const result = await gateWay(baseParams)
    return result
  } catch (error) {
    throw error
  }
}

// aes加密网关请求
const requestWithAES = async (params, needencry) => {
  if (needencry && needencry.need && params.biz_content) {
    const key = CryptoJS.enc.Utf8.parse(needencry.key)
    const srcs = CryptoJS.enc.Utf8.parse(JSON.stringify(params.biz_content))
    const encrypted = CryptoJS.AES.encrypt(srcs, key, {
      iv: key,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7
    })
    params.biz_content = encrypted.ciphertext.toString()
  }
  try {
    const baseParams = {
      version: '1.0',
      charset: 'UTF-8',
      origin: '1',
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id || app_id,
      biz_content: params.biz_content,
      header: params.header || '{}'
    }
    const sign = await createSign(baseParams)
    baseParams.sign = sign
    const result = await gateWay(baseParams)
    return result
  } catch (error) {
    throw error
  }
}

// 文件上传用
function Jpaas(param) {
  return new Promise((resolve) => {
    const timestamp = new Date().valueOf()
    param.timestamp = timestamp
    const formData = new FormData()
    formData.append('app_id', app_id)
    formData.append('interface_id', param.interface_id)
    formData.append('biz_content', param.biz_content)
    formData.append('version', '1.0')
    formData.append('charset', 'UTF-8')
    formData.append('origin', '1')
    formData.append('header', param.header || '{}')
    formData.append('timestamp', timestamp)
    formData.append('file', param.file)
    axios({
      method: 'post',
      url: baseUrl + 'createsign.do',
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((res) => {
      if (res.data.success) {
        // param.sign = res.data.data.sign;
        formData.append('sign', res.data.data.sign)
        axios({
          method: 'post',
          url: baseUrl + 'gateway.do',
          data: formData,
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
          .then((res) => {
            if (res.data.success) {
              const resp = JSON.parse(res.data.data)
              if (resp.success) {
                resolve(resp)
              } else {
                resolve({})
              }
            } else {
              resolve({})
            }
          })
          .catch(() => {
          })
      } else {
        resolve({})
      }
    })
  })
}

function jpass2(appid, interfaceid, data) {
  return new Promise((resolve, reject) => {
    let that = this
    let formdata = new FormData()
    formdata.append('app_id', appid)
    formdata.append('interface_id', interfaceid)
    formdata.append('version', '1.0')
    formdata.append('header', `{}`)
    formdata.append('biz_content', JSON.stringify(data))
    formdata.append('charset', 'UTF-8')
    formdata.append('origin', '1')
    formdata.append('timestamp', new Date().valueOf())
    // lightAppJssdk.request.request({
    $.ajax({
      type: 'post',
      url:
        'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do',
      data: formdata,
      contentType: false,
      // 告诉jQuery不要去设置Content-Type请求头
      processData: false,
      success: function (res1) {
        formdata.append('sign', res1.data.sign)
        // lightAppJssdk.request
        //   .request({
        $.ajax({
          type: 'post',
          url:
            'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
          data: formdata,
          contentType: false,
          // 告诉jQuery不要去设置Content-Type请求头
          processData: false
        }).then((data) => {
          // resolve(JSON.parse(data.data));
          resolve(data)
        })
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        that.msg = '接口异常'
        that.open = true
      }
    })
  })
}

export { Jpaas, jpass2, request, requestWithSM2, requestWithSM4, requestWithAES }
