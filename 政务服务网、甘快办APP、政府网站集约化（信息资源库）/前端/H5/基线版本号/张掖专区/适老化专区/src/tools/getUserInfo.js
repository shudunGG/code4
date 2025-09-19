import JSSM4 from 'jssm4'
import { jpassConfig } from '../HTTP'

// sm4加密
const SM4 = (text) => {
  let sm4 = new JSSM4('wusiYTaMcArXhew8')
  return sm4.encryptData_ECB(text)
}

// sm4解密
const sm4Decrypt = (text) => {
  let sm4 = new JSSM4('g@EdevfJ3g@B*xeT')
  return sm4.decryptData_ECB(text)
}

// 判断用户是否登录，最终只返回用户getTokenSNO
const getTokenNO = () => {
  return new Promise((resolve, reject) => {
    //获取用户信息
    lightAppJssdk.user.getUserInfo({
      success: (data) => {
        if (data == '未登录') {
          lightAppJssdk.user.loginapp({
            success: (userResult) => {
              //登录后获取数据
              if (userResult == '取消登录' || userResult == '用户拒绝授权') {
                lightAppJssdk.navigation.close({})
              } else {
                resolve(JSON.parse(userResult))
              }
            },
            fail: (userError) => {
              reject(userError)
            },
          })
        } else {
          //已经登录
          if (data == '用户拒绝授权') {
            lightAppJssdk.navigation.close({})
          } else {
            resolve(JSON.parse(data))
          }
        }
      },
      fail: (error) => {
        reject(error)
      },
    })
  })
}
const getTokenNOer = () => {
  return new Promise((resolve, reject) => {
    //获取用户信息
    lightAppJssdk.user.getUserInfo({
      success: (data) => {
        if (data == '未登录') {
          // lightAppJssdk.user.loginapp({
          //     success: (userResult) => {
          //         //登录后获取数据
          //         if (userResult == "取消登录" || userResult == "用户拒绝授权") {
          //             lightAppJssdk.navigation.close({});
          //         } else {
          //             resolve(JSON.parse(userResult));
          //         }
          //     },
          //     fail: (userError) => {
          //         reject(userError);
          //     },
          // });
          resolve(data)
        } else {
          //已经登录
          if (data == '用户拒绝授权') {
            lightAppJssdk.navigation.close({})
          } else {
            resolve(JSON.parse(data))
          }
        }
      },
      fail: (error) => {
        reject(error)
      },
    })
  })
}
// 获取请求头Authorization的值
const getAuthorization = () => {
  let param = {
    app_id: 'tyyhww',
    version: '1.0',
    charset: 'UTF-8',
    origin: '1',
    header: JSON.stringify({
      nlkftoken:
        'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY',
    }),
    timestamp: new Date().valueOf(),
    interface_id: 'getAccessToken',
    biz_content: JSON.stringify({ appmark: 'zygkb' }),
  }
  return new Promise((resolve, reject) => {
    $.ajax({
      type: 'post',
      url: jpassConfig.httpRequestJpaas + 'createsign.do',
      data: param,
      timeout: 3000,
      success: (res) => {
        param.sign = res.data.sign
        $.ajax({
          type: 'post',
          url: jpassConfig.httpRequestJpaas + 'gateway.do',
          data: param,
          timeout: 3000,
          success: (data) => {
            resolve(JSON.parse(data.data))
          },
          error: (err) => {
            reject(err)
          },
        })
      },
      error: (err) => {
        reject(err)
      },
    })
  })
}

// 获取请个人(10)、法人(20)用户信息
const getUserInfo = async () => {
  let tokenNO = await getTokenNO(),
    headToken
  if (tokenNO.token) {
    //保证只有token获取成功才能调用下面接口；
    headToken = await getAuthorization()
  }
  let param = {
    app_id: 'tyyhww',
    interface_id: 'getInfoByToken',
    version: '1.0',
    charset: 'UTF-8',
    origin: '1',
    header: JSON.stringify({
      nlkftoken:
        'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJQaXB2cXlWcWNLUzFPTFZvZUtYUlNlVFhrYm9sbzFzMSIsImV4cCI6MTYyNzA0MTMwOSwiaWF0IjoxNTE2MjM5fQ.aCP7GR1lRmMw0uUuTMk80rzG-4a8QQQJ4O_pi1ulheY',
      Authorization: `Bearer ${headToken.data.token}`,
    }),
    timestamp: new Date().valueOf(),
    biz_content: JSON.stringify({
      tokenNO: SM4(tokenNO.token),
      method:
        tokenNO.userType == '10'
          ? 'getGsNaturalInfoByToken'
          : 'getGsCorporationInfoByToken',
    }),
  }
  return new Promise((resolve, reject) => {
    $.ajax({
      type: 'post',
      url: jpassConfig.httpRequestJpaas + 'createsign.do',
      data: param,
      timeout: 3000,
      success: (res) => {
        param.sign = res.data.sign
        $.ajax({
          type: 'post',
          url: jpassConfig.httpRequestJpaas + 'gateway.do',
          data: param,
          timeout: 3000,
          success: (data) => {
            if (data.success && data.data) {
              resolve(JSON.parse(sm4Decrypt(JSON.parse(data.data).data)))
            } else {
              reject(false)
            }
          },
          error: (err) => {
            reject(err)
          },
        })
      },
      error: (err) => {
        reject(err)
      },
    })
  })
}

export { getUserInfo, getTokenNOer }
