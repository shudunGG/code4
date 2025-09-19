/* eslint-disable no-sparse-arrays */
/* eslint-disable no-useless-escape */
import CryptoJS from 'crypto-js/crypto-js'
import { sm2 } from 'sm-crypto'
import JSSM4 from 'jssm4'
// aes     默认的 KEY 与 iv 如果没有给
const aesKey = '12345678900987654321'

// sm2----------------//
const cipherMode = 1 // 1 - C1C3C2，0 - C1C2C3，默认为1
const publicKey = '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e' // 自行替换
const privateKey = '2cf4e55142ef69a391af5f03fda100533fb0f1fcdc43052b6d701624efd2cb44' // 自行替换

// sm4-----------------//
const sm4Key = 'd53acbf1cc9ed11a' // 自行替换

export default {
  // sm2加密
  sm2Encrypt (str, publicKeys) {
    publicKeys = publicKeys || 'publicKey'
    return '04' + sm2.doEncrypt(str, publicKeys, cipherMode)
  },
  // sm2解密
  sm2Decrypt (str, privateKeys) {
    privateKeys = privateKeys || privateKey
    return sm2.doDecrypt(str.substring(2), privateKeys, cipherMode)
  },
  // sm2加密
  sm2Encrypt1 (str, publicKeys) {
    publicKeys = publicKeys || '04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b'
    return '04' + sm2.doEncrypt(str, publicKeys, cipherMode)
  },
  // sm2解密
  sm2Decrypt1 (str, privateKeys) {
    privateKeys = privateKeys || '00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa'
    return sm2.doDecrypt(str.substring(2), privateKeys, cipherMode)
  },
  // sm4加密
  sm4Encrypt (str, publicKeys) {
    publicKeys = publicKeys || sm4Key
    const sm4 = new JSSM4(publicKeys)
    return sm4.encryptData_ECB(str)
  },
  // sm解密
  sm4Decrypt (str, publicKeys) {
    publicKeys = publicKeys || sm4Key
    const sm4 = new JSSM4(publicKeys)
    return sm4.decryptData_ECB(str)
  },
  /**
   * AES加密 ：字符串 key iv  返回base64
   */
  aesEncrypt (word, keyStr = aesKey) {
    const key = CryptoJS.enc.Utf8.parse(keyStr)
    const srcs = CryptoJS.enc.Utf8.parse(word)
    const encrypted = CryptoJS.AES.encrypt(srcs, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    }) // 加密模式为ECB，补码方式为PKCS5Padding（也就是PKCS7）

    return encrypted.toString()
  },
  /**
   * AES 解密 ：字符串 key iv  返回base64
   *
   */
  aesDecrypt (word, keyStr = aesKey) {
    const key = CryptoJS.enc.Utf8.parse(keyStr)
    const decrypt = CryptoJS.AES.decrypt(word, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    })
    const ss = CryptoJS.enc.Utf8.stringify(decrypt).toString()
    return ss
  },
  // 获取传参
  getUrlKey (name) {
    return (
      decodeURIComponent(
        (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(
          location.href
        ) || [, ''])[1].replace(/\+/g, '%20')
      ) || null
    )
  },
  // 信息校验 start
  // 手机号校验
  isPhone: function (phoneStr) {
    const myreg =
      /^((13[0-9])|(14[1|4|5|6|7|8|9])|(15([0|1|2|3|5|6|7|8|9]))|(16[2|5|6|7])|(17[0|1|2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\d{8}$/
    if (!myreg.test(phoneStr)) {
      return false
    } else {
      return true
    }
  },
  // 身份证号校验
  isCard: function (cardStr) {
    const myreg =
      /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X]|[x])$/
    if (!myreg.test(cardStr)) {
      return false
    } else {
      return true
    }
  },
  // 统一社会信用代码
  validateUnifiedSocialCreditCode: function (code) {
    if (code.length !== 18) {
      return false
    }

    // 各位加权因子
    const weights = [
      1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28
    ]

    // 校验字符集
    const chars = '0123456789ABCDEFGHJKLMNPQRTUWXY'

    let sum = 0
    for (let i = 0; i < 17; i++) {
      const char = code[i]
      const charIndex = chars.indexOf(char)
      if (charIndex === -1) {
        return false
      }
      sum += charIndex * weights[i]
    }

    const checksumIndex = (31 - (sum % 31)) % 31
    const checksumChar = chars[checksumIndex]

    return code[17] === checksumChar
  },
  // 邮箱校验
  isEmail: function (emailStr) {
    const myreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
    if (!myreg.test(emailStr)) {
      return false
    } else {
      return true
    }
  },
  // 信息校验 end
  // 信息脱敏 start
  /**
   *
   * @param {身份号} cardStr
   * @param {手机号} phoneStr
   * @param {姓名} name
   * @param {邮箱} emailStr
   * @param {用户名} Accountname
   */
  idcardDesensitization: function (
    cardStr = '',
    phoneStr = '',
    name = '',
    emailStr = '',
    Accountname = ''
  ) {
    let star = '*'
    const len = cardStr.toString().length - 4
    for (let i = 1; i < len; i++) {
      star = star + '*'
    }
    return {
      userName: name.replace(/.(?=.)/g, '*'),
      mobile: phoneStr.substring(0, 3) + '****' + phoneStr.substring(7, 11),
      idCard: star + cardStr.substring(14, 18),
      Accountname: Accountname.replace(/.(?=.)/g, '*'),
      email:
        emailStr.substr(0, 2) + '****' + emailStr.substr(emailStr.indexOf('@'))
    }
  },
  // 信息脱敏 end
  // 格式化时间戳(
  /**
   *
   * @param {时间戳 可不传} date
   */
  formatTime: function (date = new Date()) {
    const o = {
      y: date.getFullYear(), // 年份
      M:
        date.getMonth() + 1 < 10
          ? '0' + (date.getMonth() + 1)
          : date.getMonth() + 1, // 月份
      d: date.getDate() < 10 ? '0' + date.getDate() : date.getDate(), // 日
      // "h": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时
      H: date.getHours() < 10 ? '0' + date.getHours() : date.getHours(), // 小时
      m: date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes(), // 分
      s: date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds() // 秒
    }
    return {
      o,
      type: o.y + '-' + o.M + '-' + o.d + ' ' + o.H + ':' + o.m + ':' + o.s,
      typeOne: o.y + '-' + o.M + '-' + o.d,
      typeTwo: o.y + '/' + o.M + '/' + o.d + ' ' + o.H + ':' + o.m + ':' + o.s,
      typeThree: o.y + '/' + o.M + '/' + o.d,
      typeFour: o.y + '年' + o.M + '月' + o.d + '日',
      typeFive:
        o.y +
        '年' +
        o.M +
        '月' +
        o.d +
        '日' +
        ' ' +
        o.H +
        ':' +
        o.m +
        ':' +
        o.s,
      typeSix: o.H + ':' + o.m,
      typeSeven: o.M + '月' + o.d + '日 ' + o.H + ':' + o.m,
      typeEight: o.M + '月' + o.d + '日 '
    }
  },

  // 防抖
  debounce: function (fun, wait) {
    // fun:事件处理函数， wait:延迟时间
    let timer
    return function () {
      if (timer) {
        // timer有值为真，这个事件已经触发了一次，重新开始计数
        clearTimeout(timer)
      }
      timer = setTimeout(function () {
        fun()
      }, wait)
    }
  },

  // 节流
  throttle: function (fun, wait) {
    let timer // 开   timer=undefined
    return function () {
      if (!timer) {
        // 判断timer是否有值（当前是否已经开启了定时器） ，
        timer = setTimeout(function () {
          // 关
          fun()
          timer = undefined // 开
        }, wait)
      }
    }
  }
}
/**
 * 姓名脱敏
 * @param name 脱敏字符串
 * @returns {string}
 */
function name (name) {
  if (!name) {
    return '暂无数据 '
  }
  // 两个字的姓名，隐藏第一个字
  if (name.length === 2) {
    return name.replace(/^./, '*')
  } else if (name.length === 3) {
    return name.replace(/^./, '*')
  } else if (name.length <= 6 && name.length >= 3) {
    return name.replace(name.substr(0, name.length - 2), '***')
  } else if (name.length > 6) {
    return name.replace(/^(.).*(.)$/, '$1*****$2')
  }
  // 三个字的姓名，隐藏第一个字
  // 六个字以下，三个字以上，只显示最后两个字
  // 六个字以上，只显示第一个字和最后一个字
}

/**
* 证件脱敏(身份证号,军官证号,护照号)
* @param str 脱敏字符串
* @returns {string}
*/
function IdNumberDesensitization (IdNumber) {
  if (!IdNumber) {
    return '无'
  }
  // 身份证号
  if (IdNumber.length === 18) {
    return IdNumber.replace(/^(.).*(.)$/, '$1****************$2')
  } else if (IdNumber.length === 10) {
    return IdNumber.replace(/^(.).*(.)$/, '$1********$2')
  } else if (IdNumber.length === 9) {
    return IdNumber.replace(/^(.).*(.)$/, '$1********$2')
  }
  // 军官证号
  // 护照号
}
/**
* 手机号脱敏
* @param str 脱敏字符串
* @returns {string}
*/
function phone (phone) {
  if (!phone) {
    return '无'
  }
  // 大陆：前三位，后两位，其他用*代替
  if (phone.length === 11) {
    return phone.substring(0, 3) + '******' + phone.substring(9, 11)
  } else if (phone.length === 8) {
    return phone.substring(0, 2) + '****' + phone.substring(6, 8)
  } else if (phone.length === 9) {
    return phone.substring(0, 2) + '****' + phone.substring(6, 9)
  } else {
    return phone.substring(0, 3) + '****' + phone.substring(8, 11)
  }
  // 香港，澳门：前2位，后2位，其他用*代替
  // 台湾：前2位，后3位，其他用*代替
  // 其他海外地区：前3位，后4位，其他用*代替
}
/**
* 地址脱敏
* @param desensitizedCommon 脱敏字符串
* @returns {string}
*/
function desensitizedCommon (str, beginLen, endLen) {
  if (str === null || str === '' || str === undefined) return
  const len = str.length
  if (!beginLen && !endLen) {
    const a = Math.ceil(len * 0.5) / 2
    beginLen = a
    endLen = -a
  }
  const firstStr = str.substr(0, beginLen)
  const lastStr = endLen ? str.substr(endLen) : ''
  const middleStr = str
    .substring(beginLen, len - Math.abs(endLen))
    .replace(/[\s\S]/gi, '*')
  return firstStr + middleStr + lastStr
}
// 余额脱敏
function replaceAll (str) {
  return str.replace(/./g, '*')
}

export {
  name,
  IdNumberDesensitization,
  phone,
  desensitizedCommon,
  replaceAll
}
