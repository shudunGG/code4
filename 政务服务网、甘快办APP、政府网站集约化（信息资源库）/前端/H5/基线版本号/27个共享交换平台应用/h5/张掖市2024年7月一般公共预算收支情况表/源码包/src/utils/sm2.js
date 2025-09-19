const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encrypt (text, key) {
    key = key || '04841e06ae8a7a2524deb84fb098b6d7cb41b6e33996619dbcd610edd4c5762be507ded5480f9a03a97750ae4bf2da2e3fa26f42ef4249bd9f66ef6eee15d05e1b'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) {
    key = key || '00e4bce5f10d8a3e96b7e62df284cda5078c8f263056a0f6305370a5e63438bd20'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  }
}