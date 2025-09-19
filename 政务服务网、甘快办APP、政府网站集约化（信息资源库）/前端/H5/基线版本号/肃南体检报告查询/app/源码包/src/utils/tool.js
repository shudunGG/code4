import JSSM4 from 'jssm4'
import CryptoJS from 'crypto-js'
import { sm2 } from 'sm-crypto'
// const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encryptSM2 (text, key) {
    key = key || '04f776b67b25293281842ffe76ceef897f0222a8153d469e4a279b9f0afece6d5ca535f45cb40c196af3eeb41dad8c741df46a369d4bb9c655c0fec9fef3f54ba8'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decryptSM2 (text, key) {
    key = key || '12b34bec92fabd43a2240930650c92c6585761bddd036184b95d6e5e171450e0'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  // sm4加密
  encrypt (text, key) {
    key = key || '11fa0b98b4974c0d'
    const sm4 = new JSSM4(key)
    return sm4.encryptData_ECB(text)
  },
  // sm4解密
  decrypt (text, key) {
    key = key || '11fa0b98b4974c0d'
    const sm4 = new JSSM4(key)
    return sm4.decryptData_ECB(text)
  },
  // 加密
  encryptAES (word, keyStr) {
    keyStr = keyStr || 'ad555e777edc4c34a99d546926221d1f' // 判断是否存在ksy，不存在就用定义好的key
    const key = CryptoJS.enc.Utf8.parse(keyStr)
    const srcs = CryptoJS.enc.Utf8.parse(word)
    const encrypted = CryptoJS.AES.encrypt(srcs, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    })
    return encrypted.toString()
  },
  // 解密
  decryptAES (word, keyStr) {
    keyStr = keyStr || 'ad555e777edc4c34a99d546926221d1f'
    const key = CryptoJS.enc.Utf8.parse(keyStr)
    const decrypt = CryptoJS.AES.decrypt(word, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7
    })
    return CryptoJS.enc.Utf8.stringify(decrypt).toString()
  }
}
