const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encrypt (text, key) {
    // key = key || '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e'
    key = key || '04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) {
    // key = key || '2cf4e55142ef69a391af5f03fda100533fb0f1fcdc43052b6d701624efd2cb44'
    key = key || '00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  }
}
