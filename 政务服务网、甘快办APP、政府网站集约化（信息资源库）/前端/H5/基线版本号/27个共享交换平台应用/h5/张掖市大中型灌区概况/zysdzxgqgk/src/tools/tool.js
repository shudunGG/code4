import CryptoJS from "crypto-js/crypto-js";
import { sm2 } from "sm-crypto";
// 默认的 KEY 与 iv 如果没有给

const publicKey =
  "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b";
const privateKey =
  "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b";
const tools = {
  // sm2 加密
  encryptSm2: function(str) {
    return "04" + sm2.doEncrypt(str, publicKey, 1);
  },
  // sm2 解密
  decryptSm2: function(str) {
    return sm2.doDecrypt(str.slice(2), privateKey, 1);
  },
};
export { tools }
