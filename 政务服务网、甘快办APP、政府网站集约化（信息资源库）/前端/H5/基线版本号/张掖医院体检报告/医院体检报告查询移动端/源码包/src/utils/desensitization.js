/**
 * 姓名脱敏
 * @param name 脱敏字符串
 * @returns {string}
 */
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
/**
 * 证件脱敏(身份证号,军官证号,护照号)
 * @param str 脱敏字符串
 * @returns {string}
 */
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
/**
 * 手机号脱敏
 * @param str 脱敏字符串
 * @returns {string}
 */
function TmPhone (phone) {
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
/**
 * 地址脱敏
 * @param address 脱敏字符串
 * @returns {string}
 */
function address (address) {
  if (!address) {
    return '无'
  }
  return address.substr(0, address.indexOf('区') + 1) + '****'
}

export {
  name,
  IdNumberDesensitization,
  TmPhone,
  address,
  phone
}
