/* eslint-disable no-sparse-arrays */
/* eslint-disable no-useless-escape */
import CryptoJS from 'crypto-js/crypto-js'
import { sm2, sm4 } from 'sm-crypto'

// aes     默认的 KEY 与 iv 如果没有给
const aesKey = '12345678900987654321'

// sm2----------------//
const cipherMode = 1 // 1 - C1C3C2，0 - C1C2C3，默认为1
const publicKey = '0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b' // 自行替换
const privateKey = '3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b' // 自行替换

// sm4-----------------//
const sm4Key = '12345678900987654321' // 自行替换

export default {
  // sm2加密
  sm2Encrypt (str, publicKeys = publicKey) {
    return '04' + sm2.doEncrypt(str, publicKeys, cipherMode)
  },
  // sm2解密
  sm2Decrypt (str, privateKeys = privateKey) {
    return sm2.doDecrypt(str.substring(2), privateKeys, cipherMode)
  },
  // sm4加密
  sm4Encrypt (str, publicKeys = sm4Key) {
    return sm4.encrypt(str, publicKeys)
  },
  // sm解密
  sm4Decrypt (str, publicKeys = sm4Key) {
    return sm4.decrypt(str, publicKeys)
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
