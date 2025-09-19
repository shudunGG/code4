const sm2 = require('sm-crypto').sm2

const cipherMode = 1
export default {
  // 加密
  encryptSM2 (text, key) {
    key = key || '0447c33b1ff9cbb2f15aae1b753c8a306e605a6b87b9441402f5e757dcbcfe4ab28bc340f8e6508918ca59799940d8b5f9fd556211f5803d2ae4aec580de48268d'
    const enText = sm2.doEncrypt(text, key, cipherMode)
    return '04' + enText
  },
  // 解密
  decryptSM2 (text, key) {
    key = key || '5506c31374d5d8e9d79ba5ecc7cd1a7d60df3f7ef5fbb6865ccd9ce489751ac7'
    const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
    return deText
  },
  // 获取地址栏参数
  GetQueryString (name) {
    // 获取URL参数
    return (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [''])[1]
  }
}
function desensitizedName (fullName) {
  if (!fullName) {
    return "";
  }
  let str = fullName.substr(0, 1);
  for (let i = 0; i < fullName.length - 1; i++) {
    str += '*';
  }
  return str;
}

function desensitizedCommon (str, begin, end) {
  if (!str && (begin + end) >= str.length) {
    return "";
  }
  let leftStr = str.substring(0, begin);
  let rightStr = str.substring(str.length - end, str.length);

  let strCon = ''
  for (let i = 0; i < str.length - end - begin; i++) {
    strCon += '*';
  }
  return leftStr + strCon + rightStr;
}

function desensitizedAddr (str) {
  if (!str) {
    return "";
  }
  if (this.addrReg.test(str)) {
    let text1 = RegExp.$1;
    let text2 = RegExp.$2.replace(/./g, "*");
    return text1 + text2;
  }
  return str;
}
export {
  desensitizedName, desensitizedCommon, desensitizedAddr
}

