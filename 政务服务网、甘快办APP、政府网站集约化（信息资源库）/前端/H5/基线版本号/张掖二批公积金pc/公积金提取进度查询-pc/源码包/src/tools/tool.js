const sm2 = require("sm-crypto").sm2
import JSSM4 from "jssm4"
import CryptoJS from "crypto-js"

const cipherMode = 1
export default {
    // 加密
    encryptSM2(text, key) {
        key = key ||"044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e"
        const enText = sm2.doEncrypt(text, key, cipherMode)
        return "04" + enText
    },
    // 解密
    decryptSM2(text, key) {
        key =
            key || "3fce91c669c4769381384edcd8d63515362f222e9025f7f8b52f4f7ef2c38c69"
        const deText = sm2.doDecrypt(text.slice(2), key, cipherMode)
        return deText
    },
    // sm4加密
    encrypt(text, key) {
        key = key || "11fa0b98b4974c0d"
        let sm4 = new JSSM4(key)
        return sm4.encryptData_ECB(text)
    },
    // sm4解密
    decrypt(text, key) {
        key = key || "11fa0b98b4974c0d"
        let sm4 = new JSSM4(key)
        return sm4.decryptData_ECB(text)
    },
    //加密
    encryptAES(word, keyStr) {
        keyStr = keyStr ? keyStr : "ad555e777edc4c34a99d546926221d1f" //判断是否存在ksy，不存在就用定义好的key
        let key = CryptoJS.enc.Utf8.parse(keyStr)
        let srcs = CryptoJS.enc.Utf8.parse(word)
        let encrypted = CryptoJS.AES.encrypt(srcs, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        })
        return encrypted.toString()
    },
    //解密
    decryptAES(word, keyStr) {
        keyStr = keyStr ? keyStr : "ad555e777edc4c34a99d546926221d1f"
        let key = CryptoJS.enc.Utf8.parse(keyStr)
        let decrypt = CryptoJS.AES.decrypt(word, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        })
        return CryptoJS.enc.Utf8.stringify(decrypt).toString()
    },
    // 获取地址栏参数
    GetQueryString(name) {
        // 获取URL参数
        return (new RegExp("[?|&]" + name + "=" + "([^&;]+?)(&|#|;|$)").exec(
            location.href
        ) || [""])[1]
    },
    // 根据数据办理状态动态改变表格数据的颜色
    changeColor(state) {
        const stateColor = {
            办理中: "blueClass",
            已办结: "greenClass",
            已受理: "yellowClass",
            未受理: "greyClass"
        }
        return stateColor[state]
    },
    // js封装根据输入时间计算XX年后的时间并格式化
    calculateFutureDate(date, years) {
        // 判断输入的 date 是否是 Date 类型的对象
        if (!(date instanceof Date)) {
            throw new TypeError("Parameter date must be a Date object.")
        }
        // 判断输入的 years 是否为有效的数字
        if (typeof years !== "number" || isNaN(years)) {
            throw new TypeError("Parameter years must be a valid number.")
        }
        // 计算未来的年份并创建一个新的 Date 对象
        const futureYear = date.getFullYear() + years
        const Month = date.getMonth() + 1
        const futureMonth = Month < 10 ? "0" + Month : Month
        const futureDate = date.getDate()
        const futureDates = futureDate < 10 ? "0" + futureDate : futureDate
        const newDate = futureYear + "-" + futureMonth + "-" + futureDates
        return newDate
    },
    // base64转URL
    downloadBase64File(files, downloadName) {
        // 创建一个用于下载的隐式的 a 标签元素
        const downloadLink = document.createElement("a")
        downloadLink.style.display = "none"

        // 循环处理每个 base64 文件并下载
        files.forEach((base64Data, index) => {
            // 将 base64 字符串转为二进制数据
            const byteCharacters = atob(base64Data)
            const byteNumbers = new Array(byteCharacters.length)
            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i)
            }
            const byteData = new Uint8Array(byteNumbers)

            // 创建一个 Blob 对象
            const blob = new Blob([byteData], {
                type: "application/octet-stream"
            })

            // 创建文件 URL
            const url = URL.createObjectURL(blob)

            // 下载文件
            downloadLink.href = url
            if (files.length === 1) {
                // 如果只有一个文件，直接使用传入的下载文件名
                downloadLink.download = downloadName
            } else {
                // 如果有多个文件，为每个文件生成一个带序号的下载文件名
                downloadLink.download = `${index + 1}_${downloadName}`
            }
            document.body.appendChild(downloadLink)
            downloadLink.click()
            document.body.removeChild(downloadLink)
            URL.revokeObjectURL(url)
        })
    },
    downloadBase64Files(files, downloadName) {
        // 创建一个用于下载的隐式的 a 标签元素
        const downloadLink = document.createElement("a")
        downloadLink.style.display = "none"

        const blob = new Blob([files], {
            type: "application/octet-stream"
        })

        // 创建文件 URL
        const url = URL.createObjectURL(blob)

        // 下载文件
        downloadLink.href = url
        if (files.length === 1) {
            // 如果只有一个文件，直接使用传入的下载文件名
            downloadLink.download = downloadName
        } else {
            // 如果有多个文件，为每个文件生成一个带序号的下载文件名
            downloadLink.download = `${downloadName}`
        }
        document.body.appendChild(downloadLink)
        downloadLink.click()
        document.body.removeChild(downloadLink)
        URL.revokeObjectURL(url)
    }

}
//时间戳转换
function changDateFormate(timestamp) {
    if (!timestamp) return "无"
    // 假设以下是您的日期对象
    const date = new Date(timestamp);

    // 将日期格式化为 "yyyy-mm-dd" 格式
    const year = date.getFullYear(); // 获取年份
    const month = String(date.getMonth() + 1).padStart(2, "0"); // 获取月份，并将一位数的月份补零成两位数
    const day = String(date.getDate()).padStart(2, "0"); // 获取日期，并将一位数的日期补零成两位数
    // const formattedDate = `${year}-${month}-${day}`; // 拼接成 "yyyy-mm-dd" 格式的字符串
    const formattedDate = `${year}${month}${day}`; // 拼接成 "yyyymmdd" 格式的字符串
    return formattedDate
}

function getNowDate() {
    let myDate = new Date()
    let year = myDate.getFullYear() //获取当前年
    let mon = myDate.getMonth() + 1 //获取当前月
    if (mon < 10) {
        mon = "0" + mon
    }
    let date = myDate.getDate() //获取当前日
    if (date < 10) {
        date = "0" + date
    }
    let hours = myDate.getHours() //获取当前小时
    if (hours < 10) {
        hours = "0" + hours
    }
    let minutes = myDate.getMinutes() //获取当前分钟
    if (minutes < 10) {
        minutes = "0" + minutes
    }
    let seconds = myDate.getSeconds() //获取当前秒
    if (seconds < 10) {
        seconds = "0" + seconds
    }
    let now =
        year + "-" + mon + "-" + date + " " + hours + ":" + minutes + ":" + seconds
    return now
}

/**
 * 脱敏公用
 * @param str 脱敏字符串
 * @param begin 起始保留长度，从0开始
 * @param end 结束保留长度，到str.length结束
 * @returns {string}
 */

function desensitizedCommon(str, begin, end) {
    if (!str && begin + end >= str.length) {
        return ""
    }

    let leftStr = str.substring(0, begin)
    let rightStr = str.substring(str.length - end, str.length)

    let strCon = ""
    for (let i = 0; i < str.length - end - begin; i++) {
        strCon += "*"
    }
    return leftStr + strCon + rightStr
}
/**
 * 姓名脱敏
 * @param name 脱敏字符串
 * @returns {string}
 */
function name(name) {
    if (!name) {
        return "无"
    }
    // 两个字的姓名，隐藏第一个字
    if (name.length === 2) {
        return name.replace(/^./, "*")
    }
    // 三个字的姓名，隐藏第一个字
    else if (name.length === 3) {
        return name.replace(/^./, "*")
    }
    // 六个字以下，三个字以上，只显示最后两个字
    else if (name.length <= 6 && name.length >= 3) {
        return name.replace(name.substr(0, name.length - 2), "***")
    }
    // 六个字以上，只显示第一个字和最后一个字
    else if (name.length > 6) {
        return name.replace(/^(.).*(.)$/, "$1*****$2")
    }
}

/**
 * 证件脱敏(身份证号,军官证号,护照号)
 * @param str 脱敏字符串
 * @returns {string}
 */
function IdNumberDesensitization(IdNumber) {
    if (!IdNumber) {
        return "无"
    }
    // 身份证号
    if (IdNumber.length === 18) {
        return IdNumber.replace(/^(.).*(.)$/, "$1****************$2")
    }
    // 军官证号
    else if (IdNumber.length === 10) {
        return IdNumber.replace(/^(.).*(.)$/, "$1********$2")
    }
    // 护照号
    else if (IdNumber.length === 9) {
        return IdNumber.replace(/^(.).*(.)$/, "$1********$2")
    }
}
/**
 * 手机号脱敏
 * @param str 脱敏字符串
 * @returns {string}
 */
function phone(phone) {
    if (!phone) {
        return "无"
    }
    // 大陆：前三位，后两位，其他用*代替
    if (phone.length === 11) {
        return phone.substring(0, 3) + "****" + phone.substring(7, 11)
    }
    // 香港，澳门：前2位，后2位，其他用*代替
    else if (phone.length === 8) {
        return phone.substring(0, 2) + "****" + phone.substring(6, 8)
    }
    // 台湾：前2位，后3位，其他用*代替
    else if (phone.length === 9) {
        return phone.substring(0, 2) + "****" + phone.substring(6, 9)
    }
    // 其他海外地区：前3位，后4位，其他用*代替
    else {
        return phone.substring(0, 3) + "****" + phone.substring(8, 11)
    }
}
/**
 * 地址脱敏
 * @param address 脱敏字符串
 * @returns {string}
 */
function address(address) {
    if (!address) {
        return "无"
    }
    return address.substr(0, address.indexOf("区") + 1) + "****"
}
// 余额脱敏
function replaceAll(str) {
    return str.replace(/./g, '*');
}


function getLastDayOfMonth(time) {
    let year = time.slice(0, 4)
    let month = time.slice(5, 7)
    let lastDay = year + "-" + month + "-" + new Date(year, month, 0).getDate()
    return lastDay
}
// 将20221122格式化为2022-11-22
function formatDate(dateString) {
    return dateString.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
}
// 处理接口返回的数据
function handleResponse (response) {
    console.log(response, '5656')
    if (Array.isArray(response)) {
        console.log(response, '1')
        // 如果接口返回的是数组，直接使用
        return response;
    } else if (response && typeof response === 'object') {
        // 如果接口返回的是单个对象，将其放入数组中
        console.log([response], '2')

        return [response];
    } else {
        // 其他情况，返回一个空数组或根据具体需求处理
        return [];
    }
}
export {
    name,
    IdNumberDesensitization,
    desensitizedCommon,
    phone,
    address,
    getLastDayOfMonth,
    changDateFormate,
    getNowDate,
    formatDate,
    replaceAll,
    handleResponse
}