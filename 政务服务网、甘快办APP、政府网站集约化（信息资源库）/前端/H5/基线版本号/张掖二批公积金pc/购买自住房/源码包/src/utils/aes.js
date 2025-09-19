import CryptoJS from "crypto-js";
export default {
  //加密
  encrypt(word, keyStr) {
      keyStr = keyStr ? keyStr : 'ad555e777edc4c34a99d546926221d1f'; //判断是否存在ksy，不存在就用定义好的key
      var key = CryptoJS.enc.Utf8.parse(keyStr);
      var srcs = CryptoJS.enc.Utf8.parse(word);
      var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
      return encrypted.toString();
  },
  //解密
  decrypt(word, keyStr) {
      keyStr = keyStr ? keyStr : 'ad555e777edc4c34a99d546926221d1f';
      var key = CryptoJS.enc.Utf8.parse(keyStr);
      var decrypt = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
      return CryptoJS.enc.Utf8.stringify(decrypt).toString();
  }
}