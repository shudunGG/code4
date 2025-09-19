const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encrypt (text, key) {
    key = key || '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) {
    key = key || '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e'
    const deText = sm2.doDecrypt(text, key, cipherMode)
    return deText
  }
}
