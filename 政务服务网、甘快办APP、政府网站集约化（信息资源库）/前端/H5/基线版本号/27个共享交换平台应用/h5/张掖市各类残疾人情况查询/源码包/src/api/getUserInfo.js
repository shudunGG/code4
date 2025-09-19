/*
 * @Author: zhangpeng
 * @Description: 请求封装
 * @LastEditTime: 2024-03-08 15:05:04
 */
import JSSM4 from 'jssm4'
import axios from 'axios'
import creatsign from '@/utils/creatsign'
const httpRequestJpaasUrl = '/jpaas-jags-server/interface/'
let time = new Date().getTime() // 获取当前时间戳
/* eslint-disable */
const SM4 = (text) => {
      const sm4 = new JSSM4('wusiYTaMcArXhew8')
      return sm4.encryptData_ECB(text)
}

const jpaasRequest = {
      utf8StrToBytes: (utf8Str) => {
            let ens = encodeURIComponent(utf8Str)
            let es = unescape(ens)
            let esLen = es.length
            let words = []
            for (let i = 0; i < esLen; i++) {
                  words[i] = es.charCodeAt(i)
            }
            return words
      },
      encode: (b, pos, len) => {
            let hexCh = new Array(len * 2)
            let hexCode = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
            for (let i = pos, j = 0; i < len + pos; i++, j++) {
                  hexCh[j] = hexCode[(b[i] & 0xff) >> 4]
                  hexCh[++j] = hexCode[b[i] & 0x0f]
            }
            return hexCh.join('')
      },
      createSign: (params) => {
            let inputtext = 'app_id=' + params.get('app_id') + '&biz_content=' + params.get('biz_content') + '&charset=' + 'UTF-8' + '&interface_id=' + params.get('interface_id') + '&origin=' + '1' + '&timestamp=' + time + '&version=' + params.get('version')
            let dataBy = jpaasRequest.utf8StrToBytes(inputtext)
            let sm3 = new creatsign()
            sm3.update(dataBy, 0, dataBy.length)
            let sm3Hash = sm3.doFinal()
            let sm3HashHex = jpaasRequest.encode(sm3Hash, 0, sm3Hash.length)
            return sm3HashHex.toLowerCase()
      },
      gateway: (params = {}) => {
            return axios.post(httpRequestJpaasUrl + 'gateway.do', params)
      },
}
// 判断用户是否登录，最终只返回用户getTokenSNO
const getTokenNO = () => {
      return new Promise((resolve, reject) => {
            // 获取用户信息
            lightAppJssdk.user.getUserInfo({
                  success: data => {
                        if (data == '未登录') {
                              lightAppJssdk.user.loginapp({
                                    success: userResult => {
                                          // 登录后获取数据
                                          if (userResult == '取消登录' || userResult == '用户拒绝授权') {
                                                lightAppJssdk.navigation.close({})
                                          } else {
                                                resolve(JSON.parse(userResult))
                                          }
                                    },
                                    fail: userError => {
                                          reject(userError)
                                    }
                              })
                        } else { // 已经登录
                              if (data == '用户拒绝授权') {
                                    lightAppJssdk.navigation.close({})
                              } else {
                                    resolve(JSON.parse(data))
                              }
                        }
                  },
                  fail: (error) => {
                        reject(error)
                  }
            })
      })
}


// 获取请求头Authorization的值
const getAuthorization = () => {
      return new Promise((resolve, reject) => {
            try {
                  const baseParams = new URLSearchParams()
                  baseParams.append('app_id', 'tyyhww')
                  baseParams.append('version', '1.0')
                  baseParams.append('charset', 'UTF-8')
                  baseParams.append('origin', '1')
                  baseParams.append('timestamp', time)
                  baseParams.append('interface_id', 'getAccessToken')
                  baseParams.append('header', JSON.stringify({
                        nlkftoken: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY'
                  }))
                  baseParams.append('biz_content', JSON.stringify({
                        appmark: 'zygkb'
                  }))
                  const sign = jpaasRequest.createSign(baseParams)
                  baseParams.append('sign', sign)
                  jpaasRequest.gateway(baseParams).then(response => {
                        if (response.status == 200) {
                              resolve(JSON.parse(response.data.data))
                        } else {
                              reject(response)
                        }
                  }).catch(error => {
                        reject(error)
                  })
            } catch (error) {
                  reject(error)
            }
      })
}

// 获取请个人、法人用户信息
const getUserInfo = async () => {
      let tokenNO = await getTokenNO()
      let headToken;
      if (tokenNO.token) { // 保证只有token获取成功才能调用下面接口；
            headToken = await getAuthorization()
      }
      const baseParams = new URLSearchParams()
      baseParams.append('app_id', 'tyyhww')
      baseParams.append('version', '1.0')
      baseParams.append('charset', 'UTF-8')
      baseParams.append('origin', '1')
      baseParams.append('timestamp', time)
      baseParams.append('interface_id', tokenNO.userType == '10' ? 'getGsNaturalInfoByToken' : 'getGsCorporationInfoByToken')
      baseParams.append('header', JSON.stringify({
            nlkftoken: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY',
            Authorization: `Bearer ${headToken.data.token}`
      }))
      baseParams.append('biz_content', JSON.stringify({
            tokenNO: SM4(tokenNO.token)
      }))

      return new Promise((resolve, reject) => {
            try {
                  const sign = jpaasRequest.createSign(baseParams)
                  baseParams.append('sign', sign)
                  jpaasRequest.gateway(baseParams).then(response => {
                        if (response.status == 200) {
                              resolve(JSON.parse(response.data.data))
                        } else {
                              reject(response)
                        }
                  })
            } catch (error) {
                  reject(error)
            }
      })
}

export {
      getUserInfo
}
