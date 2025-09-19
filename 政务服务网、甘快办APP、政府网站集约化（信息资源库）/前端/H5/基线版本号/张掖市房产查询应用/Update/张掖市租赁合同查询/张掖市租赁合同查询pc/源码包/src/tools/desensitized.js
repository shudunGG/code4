//通用脱敏方法 参数1：需脱敏字段  参数2：前面需要展示几个字符  参数3：后面需要展示几个字符（后面需要1个则为-1）
function desensitizedCommon(str, beginLen, endLen) {
  let len = str.length; 
  if (!beginLen && !endLen) {
    let a = Math.ceil(len * 0.5) / 2
    beginLen = a;
    endLen = -a;
  }
  let firstStr = str.substr(0, beginLen);
  let lastStr = endLen ? str.substr(endLen) : "";
  let middleStr = str
    .substring(beginLen, len - Math.abs(endLen))
    .replace(/[\s\S]/gi, "*");
  return firstStr + middleStr + lastStr;
}
//姓名脱敏
function desensitizedName(str) {
  if (!str) return ""
  let a = 0;
  str.length >= 3 ? (a = -2) : (a = -1);
  return desensitizedCommon(str, 0, a);
}
function desensitizedAddr(str) {
  if (!str) {
    return ''
  }
  if (this.addrReg.test(str)) {
    const text1 = RegExp.$1
    const text2 = RegExp.$2.replace(/./g, '*')
    return text1 + text2
  }
  return str
}
export {
  desensitizedName, desensitizedCommon, desensitizedAddr
}
