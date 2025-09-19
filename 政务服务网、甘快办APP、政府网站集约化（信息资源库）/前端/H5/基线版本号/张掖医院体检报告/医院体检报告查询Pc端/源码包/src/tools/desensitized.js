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

// 证件号码脱敏
function IdNumberDesensitization (IdNumber) {
  if (!IdNumber) {
    return '无'
  }
  // 身份证号
  if (IdNumber.length === 18) {
    return IdNumber.replace(/^(.).*(.)$/, '$1****************$2')
  }
  // 军官证号
  else if (IdNumber.length === 10) {
    return IdNumber.replace(/^(.).*(.)$/, '$1********$2')
  }
  // 护照号
  else if (IdNumber.length === 9) {
    return IdNumber.replace(/^(.).*(.)$/, '$1********$2')
  }
}
// 姓名脱敏
function name (name) {
  if (!name) {
    return '无'
  }
  // 两个字的姓名，隐藏第一个字
  if (name.length === 2) {
    return name.replace(/^./, '*')
  }
  // 三个字的姓名，隐藏第一个字
  else if (name.length === 3) {
    return name.replace(/^./, '*')
  }
  // 六个字以下，三个字以上，只显示最后两个字
  else if (name.length <= 6 && name.length >= 3) {
    return name.replace(name.substr(0, name.length - 2), '***')
  }
  // 六个字以上，只显示第一个字和最后一个字
  else if (name.length > 6) {
    return name.replace(/^(.).*(.)$/, '$1*****$2')
  }
}
// 电话号码脱敏
function phone (phone) {
  if (!phone) {
    return '无'
  }
  // 大陆：前三位，后两位，其他用*代替
  if (phone.length === 11) {
    return phone.substring(0, 3) + '****' + phone.substring(7, 11)
  }
  // 香港，澳门：前2位，后2位，其他用*代替
  else if (phone.length === 8) {
    return phone.substring(0, 2) + '****' + phone.substring(6, 8)
  }
  // 台湾：前2位，后3位，其他用*代替
  else if (phone.length === 9) {
    return phone.substring(0, 2) + '****' + phone.substring(6, 9)
  }
  // 其他海外地区：前3位，后4位，其他用*代替
  else {
    return phone.substring(0, 3) + '****' + phone.substring(8, 11)
  }
}


export {
  desensitizedName, desensitizedCommon, desensitizedAddr, phone, IdNumberDesensitization, name
}
