import JSSM4 from 'jssm4'
// sm4加密
const encrypt = (text) => {
  const sm4 = new JSSM4('d53acbf1cc9ed11a')
  return sm4.encryptData_ECB(text)
}

// sm4解密
const decrypt = (text) => {
  const sm4 = new JSSM4('d53acbf1cc9ed11a')
  return sm4.decryptData_ECB(text)
}

export default {
  encrypt,
  decrypt
}
