import JSSM4 from 'jssm4'
import axios from 'axios'
/* eslint-disable */
const SM4 = (text) => {
  const sm4 = new JSSM4('wusiYTaMcArXhew8')
  return sm4.encryptData_ECB(text)
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
  const baseParams = new URLSearchParams()
  baseParams.append('app_id', 'tyyhww')
  baseParams.append('version', '1.0')
  baseParams.append('charset', 'UTF-8')
  baseParams.append('origin', '1')
  baseParams.append('timestamp', new Date().valueOf())
  baseParams.append('interface_id', 'getAccessToken')
  baseParams.append('header', JSON.stringify({
    nlkftoken: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY'
  }))
  baseParams.append('biz_content', JSON.stringify({
    appmark: 'zygkb'
  }))

  return new Promise((resolve, reject) => {
    axios({
      method: 'post',
      url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do',
      data: baseParams
    }).then(res => {
      baseParams.append('sign', res.data.data.sign)
      axios({
        method: 'post',
        url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
        data: baseParams
      }).then(data => {
        resolve(JSON.parse(data.data.data))
      }).catch(error => {
        reject(error)
      })
    }).catch(error => {
      reject(error)
    })
  })
}

// 获取请个人、法人用户信息
const getUserInfo = async () => {
  const tokenNO = await getTokenNO(); let headToken
  if (tokenNO.token) { // 保证只有token获取成功才能调用下面接口；
    headToken = await getAuthorization()
  }
  const baseParams = new URLSearchParams()
  baseParams.append('app_id', 'tyyhww')
  baseParams.append('version', '1.0')
  baseParams.append('charset', 'UTF-8')
  baseParams.append('origin', '1')
  baseParams.append('timestamp', new Date().valueOf())
  baseParams.append('interface_id', tokenNO.userType == '10' ? 'getGsNaturalInfoByToken' : 'getGsCorporationInfoByToken')
  baseParams.append('header', JSON.stringify({
    nlkftoken: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY',
    Authorization: `Bearer ${headToken.data.token}`
  }))
  baseParams.append('biz_content', JSON.stringify({
    tokenNO: SM4(tokenNO.token)
  }))
  return new Promise((resolve, reject) => {
    axios({
      method: 'post',
      url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do',
      data: baseParams
    }).then(res => {
      baseParams.append('sign', res.data.data.sign)
      axios({
        method: 'post',
        url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
        data: baseParams
      }).then(data => {
        resolve(JSON.parse(data.data.data))
      }).catch(error => {
        reject(error)
      })
    }).catch(error => {
      reject(error)
    })
  })
}

export {
  getUserInfo
}
