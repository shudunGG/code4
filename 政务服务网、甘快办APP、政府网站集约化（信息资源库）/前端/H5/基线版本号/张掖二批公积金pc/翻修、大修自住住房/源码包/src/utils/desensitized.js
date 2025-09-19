function desensitizedName (fullName) {
    if (!fullName) {
      return ''
    }
    let str = fullName.substr(0, 1)
    for (let i = 0; i < fullName.length - 1; i++) {
      str += '*'
    }
    return str
  }
  
  function desensitizedCommon (str, begin, end) {
    if (!str && (begin + end) >= str.length) {
      return ''
    }
    const leftStr = str.substring(0, begin)
    const rightStr = str.substring(str.length - end, str.length)
  
    let strCon = ''
    for (let i = 0; i < str.length - end - begin; i++) {
      strCon += '*'
    }
    return leftStr + strCon + rightStr
  }
  
  function desensitizedAddr (str) {
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
  