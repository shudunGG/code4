const sm2 = require('sm-crypto').sm2
const cipherMode = 1
export default {
  // 加密
  encrypt (text, key) {
    key = key || '04f776b67b25293281842ffe76ceef897f0222a8153d469e4a279b9f0afece6d5ca535f45cb40c196af3eeb41dad8c741df46a369d4bb9c655c0fec9fef3f54ba8'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 加密
  encrypt1 (text, key) {
    key = key || '04f40d97e9c997ee436047255acfbd68c993bcac96460da53f61e60e74808f521098e27917ec331805f9dcba11404a0f811786d5fa940f5eaa86c32c475ef01700'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decrypt (text, key) { 
    key = key || '3fce91c669c4769381384edcd8d63515362f222e9025f7f8b52f4f7ef2c38c69'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  decrypt2 (text, key) { 
    key = key || '6c1b4b513b5384363860714e4179f7d7bd201df733d8c0681d46bc3c4a73f983'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  decrypt3 (text, key) { 
    key = key || '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  }
}
