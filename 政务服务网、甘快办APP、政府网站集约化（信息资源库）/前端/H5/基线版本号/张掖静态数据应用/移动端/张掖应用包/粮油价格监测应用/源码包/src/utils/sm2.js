const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encrypt (text, key) {
    key = key || '04f3de3a64bdc63c18d8bef95512835e69bedaf0e1089da91637790f45114a20cd1100dcb8da5207e4c1fe43e03a8c93033cac262ceb57ae2b86c6a4d7109df845'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) {
    key = key || '009b49c2685632742b544ce47212ec427920db9234d77f966f92e3cbddc855b016'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  }
}
