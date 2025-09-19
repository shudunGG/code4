import CryptoJS from "crypto-js";
// 1：引入crypto-js.js
// 2：调用decryptByAES（"密文","73961011"）;// 其中，73961011写死

//AES 解密
const DES = {
    decryptByDES(ciphertext, key = "73961011") {
        var keyHex = CryptoJS.enc.Utf8.parse(key);
        var decrypted = CryptoJS.DES.decrypt({
            ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
        }, keyHex, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    },



    // 针对queryHealthy、AssignCode接口进行AES加密方法
    Decrypt(word) {
        let key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
        let decrypt = CryptoJS.AES.decrypt(word, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();

    }
}
export default DES
