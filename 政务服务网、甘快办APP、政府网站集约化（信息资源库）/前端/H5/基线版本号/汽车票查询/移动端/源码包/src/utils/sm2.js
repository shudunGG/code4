const sm2 = require('sm-crypto').sm2
const cipherMode = 1
import JSEncrypt from "jsencrypt"; // jsencrypt
export default {
  // 加密
  // encrypt (text, key) {
  //   // key = key || '04f3de3a64bdc63c18d8bef95512835e69bedaf0e1089da91637790f45114a20cd1100dcb8da5207e4c1fe43e03a8c93033cac262ceb57ae2b86c6a4d7109df845'
  //   key = key || '042a455299593c3a7ac95171d5147273238aa4d144bc0b2b7cad45aff042f2008090ee95dfe86a741044acbb517db86969527ca7d9ba122a030deac8844d050f8e'
  //   const enText = sm2.doEncrypt(text, key, cipherMode)
  //   return '04' + enText
  // },
  // 解密
  // decrypt (text, key) {
  //   // key = key || '009b49c2685632742b544ce47212ec427920db9234d77f966f92e3cbddc855b016'
  //   key = key || '514256c53bcb67c4f69ad6ae26b6dc40107006b8e53c5ed1df35fbec944cbff6'
  //   const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
  //   return deText
  // },
  // RSA解密
  rsaDecrypt(str) {
    const pubKey = `-----BEGIN PRIVATE KEY-----
    MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAItIaWXS2Loqxd8Dqr6GipVKZmyTXwxIBPuVdFAGeTiHtxzZlLu0PHBea2EjG0uC9lmSctWqhO/VPO0mi/ZSA+2TQDUIq6yzaeInl1oCRuAuJ4eTl9F+nigY2B1lVQ6L2w553KphrKEhcwqwbANmFoW9I9XftsCWXPjnF7/RX0W9AgMBAAECgYAe+kv2yZ5KSZkYsMgY9/N6oMGHBH/vS1VQXz0vT0FGwWiIkhvyY3PNyjI3uIcplEcWROwleXd3oAWc+SXH8xXMPRXUczR6VfmMDGwEBsXghuI+n8guNUufG4z9b1KiP+a+DJM95L6xTI2smIid81eCnRdhDJ3PlRjVZLtAfawgQQJBAMQLGP/9zlaoBJ1htowX7qGiklq1OcKJjTiqaz5CjmklU9GB2Af82QWcr3JWO/SHQDrWFabnjqURVDiZTtT/y9MCQQC14VdzBm4OSY7AlXR76R/48MTaWKb0HB1rWNBJEsEBHJ1f1OCKpG9kow1LM3PGDDdgsCMRNPzH1tcGcEyULX4vAkBOceJfDMA0DKdMEn1pMWpbYyINCddC8KeBtS+Uii1SBY20l7BKAfMVO804gykaf8RDxsaDMsTxaC27OB1DPdfhAkBKrnKX+oXqO+3vye6AiqLkNwi9lzVGetzhnSA5QQgbCsLb/K8OfqJ2sjTdMg1RvuZmPyzG/fjlnaPBMw9esKdlAkBLcgz4tjJ/S2qtLJt8zRaSminNKXV2BnhSAq45TMeqT+0QbFxXyZjdCns83rZ8ik36GdcZRc8f2WlxP2in9sLp
      -----END PRIVATE KEY-----` //私钥
    const encryptStr = new JSEncrypt()
    encryptStr.setPrivateKey(pubKey)
    const data = encryptStr.decrypt(str.toString())
    return data
  }
}
