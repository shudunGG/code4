// import CryptoJS from '../node_modules/crypto-js/crypto-js.js'
const CryptoJS = require('crypto-js')
const sm2 = require('sm-crypto').sm2
const sm3 = require('sm-crypto').sm3
const cipherMode = 1
const defaultKey = 'M8j56LQ4052IH11'
export default {
  // 加密
  encrypt (text, key) {
    key = key || '04f35d6bd42286dd152273e71fb76e48657790763631e95d09601ebf3b247e53757c8dfcaabaf5a3d5a1d7efe73e2d772ed9cff9765d9169181c65b40e66d55ec4'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) {
    key = key || '008ddf25695b7c988bdc5a97237827db393fbcf13e0ad31d10ae7687bb0c7b3114'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  decrypt3 (text, key) { 
    key = key || '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  // DES加密
  encryptDes (message) {
    const keyHex = CryptoJS.enc.Utf8.parse(defaultKey)
    const encrypted = CryptoJS.DES.encrypt(message, keyHex, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    })
    return encrypted.toString()
  },

  // DES解密
  decryptedDES (data) {
    const keyHex = CryptoJS.enc.Utf8.parse(defaultKey)
    const decrypted = CryptoJS.DES.decrypt(
      { ciphertext: CryptoJS.enc.Hex.parse(data) },
      keyHex,
      { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 })// 以utf-8的形式输出解密过后内容
    return decrypted.toString(CryptoJS.enc.Utf8)
  },
  // sm3加密
  getSm3 (text) {
    return sm3(text)
  },
  uuidGenerator () {
    const originStr = 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx'
    const originChar = '0123456789abcdef'
    const len = originChar.length
    return originStr.replace(/x/g, function () {
      return originChar.charAt(Math.floor(Math.random() * len))
    })
  },
  desensitization (str, beginLen, endLen) {
    const len = str.length
    const firstStr = str.substr(0, beginLen)
    const lastStr = endLen ? str.substr(endLen) : ''
    const middleStr = str
      .substring(beginLen, len - Math.abs(endLen))
      .replace(/[\s\S]/gi, '*')
    return firstStr + middleStr + lastStr
  },
  // 姓名脱敏
  desensitizationName (str) {
    const len = str > 2 ? -2 : -1
    return this.desensitization(str, 0, len)
  }
}
