/*
* @Author: ZP
* @Description: 工具类
* @LastEditTime: 2024-03-08 15:05:04
*/
import { sm2, sm3 } from 'sm-crypto'
import JSSM4 from "jssm4"
import CryptoJS from "crypto-js"

export default {
    // sm2加密
    encryptSM2 (text, key) {
        key =
            key ||
            "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b"
        const enText = sm2.doEncrypt(text, key, 1)
        return "04" + enText
    },
    // sm2解密
    decryptSM2 (text, key) {
        key =
            key || "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b"
        const deText = sm2.doDecrypt(text.slice(2), key, 1)
        return deText
    },
    // sm3加密
    encryptSM3 (key) {
        return sm3(key)
    },
    // sm4加密
    encrypt (text, key) {
        key = key || "11fa0b98b4974c0d"
        let sm4 = new JSSM4(key)
        return sm4.encryptData_ECB(text)
    },
    // sm4解密
    decrypt (text, key) {
        key = key || "11fa0b98b4974c0d"
        let sm4 = new JSSM4(key)
        return sm4.decryptData_ECB(text)
    },
    //加密
    encryptAES (word, keyStr) {
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
    decryptAES (word, keyStr) {
        keyStr = keyStr ? keyStr : "ad555e777edc4c34a99d546926221d1f"
        let key = CryptoJS.enc.Utf8.parse(keyStr)
        let decrypt = CryptoJS.AES.decrypt(word, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        })
        return CryptoJS.enc.Utf8.stringify(decrypt).toString()
    },
    // 获取地址栏参数
    GetQueryString (name) {
        // 获取URL参数
        return (new RegExp("[?|&]" + name + "=" + "([^&;]+?)(&|#|;|$)").exec(
            location.href
        ) || [""])[1]
    },
    // 根据数据办理状态动态改变表格数据的颜色
    changeColor (state) {
        const stateColor = {
            办理中: "blueClass",
            已办结: "greenClass",
            已受理: "yellowClass",
            未受理: "greyClass"
        }
        return stateColor[state]
    },
    // js封装根据输入时间计算XX年后的时间并格式化
    calculateFutureDate (date, years) {
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
    downloadBase64File (files, downloadName) {
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
            const blob = new Blob([byteData], { type: "application/octet-stream" })

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
    downloadBase64Files (files, downloadName) {
        // 创建一个用于下载的隐式的 a 标签元素
        const downloadLink = document.createElement("a")
        downloadLink.style.display = "none"

        const blob = new Blob([files], { type: "application/octet-stream" })

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
/**
 * 将时间戳转换为 "yyyy-mm-dd" 格式的日期字符串
 * @param timestamp 
 * @param type
 * @returns {string}
 */
function changDateFormate (timestamp, type) {
    if (!timestamp) return "无"
    // 创建日期对象
    const date = new Date(timestamp);

    // 获取年、月、日，并确保其为两位数格式
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    // 拼接日期字符串
    if (type === 'yyyy-mm-dd') {
        const formattedDate = `${year}-${month}-${day}`;
        return formattedDate
    } else {
        const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        return formattedDate
    }

}
/**
 * 获取当前日期和时间，格式为 "yyyy-mm-dd hh:mm:ss"
 * @returns {string}
 */
function getNowDate () {
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
 * 对字符串进行脱敏处理
 * @param str 要脱敏的字符串
 * @param begin 起始保留字符长度
 * @param end 结尾保留字符长度
 * @returns {string}
 */
function desensitizedCommon (str, begin, end) {
    if (!str && begin + end >= str.length) {
        return ""
    }

    let leftStr = str.substring(0, begin) // 保留起始部分
    let rightStr = str.substring(str.length - end, str.length) // 保留结束部分

    let strCon = ""
    for (let i = 0; i < str.length - end - begin; i++) {
        strCon += "*" // 用星号代替中间部分
    }
    return leftStr + strCon + rightStr
}

/**
 * 对姓名进行脱敏处理
 * @param name 要脱敏的姓名
 * @returns {string}
 */
function name (name) {
    if (!name) {
        return "无"
    }
    // 根据姓名长度进行不同方式的脱敏
    if (name.length === 2) {
        return name.replace(/^./, "*")
    }
    else if (name.length === 3) {
        return name.replace(/^./, "*")
    }
    else if (name.length <= 6 && name.length >= 3) {
        return name.replace(name.substr(0, name.length - 2), "***")
    }
    else if (name.length > 6) {
        return name.replace(/^(.).*(.)$/, "$1*****$2")
    }
}

/**
 * 对证件号进行脱敏处理（适用于身份证、军官证、护照号）
 * @param str 要脱敏的证件号
 * @returns {string}
 */
function IdNumberDesensitization (IdNumber) {
    if (!IdNumber) {
        return "无"
    }
    // 根据证件号长度进行不同方式的脱敏
    if (IdNumber.length === 18) {
        return IdNumber.replace(/^(.).*(.)$/, "$1****************$2")
    }
    else if (IdNumber.length === 10) {
        return IdNumber.replace(/^(.).*(.)$/, "$1********$2")
    }
    else if (IdNumber.length === 9) {
        return IdNumber.replace(/^(.).*(.)$/, "$1********$2")
    }
}

/**
 * 对手机号进行脱敏处理
 * @param str 要脱敏的手机号
 * @returns {string}
 */
function phone (phone) {
    if (!phone) {
        return "无"
    }
    // 根据不同地区的手机号长度进行脱敏
    if (phone.length === 11) {
        return phone.substring(0, 3) + "******" + phone.substring(9, 11)
    }
    else if (phone.length === 8) {
        return phone.substring(0, 2) + "****" + phone.substring(6, 8)
    }
    else if (phone.length === 9) {
        return phone.substring(0, 2) + "****" + phone.substring(6, 9)
    }
    else {
        return phone.substring(0, 3) + "****" + phone.substring(8, 11)
    }
}

/**
 * 对地址进行脱敏处理
 * @param address 要脱敏的地址
 * @returns {string}
 */
function address (address) {
    if (!address) {
        return "无"
    }
    // 保留到区级，后面用星号代替
    return address.substr(0, address.indexOf("区") + 1) + "****"
}

/**
 * 将余额信息全部脱敏为星号
 * @param str 要脱敏的字符串
 */
function replaceAll (str) {
    return str.replace(/./g, '*');
}

/**
 * 获取给定时间所在月份的最后一天
 * @param time
 * @returns {string}
 */
function getLastDayOfMonth (time) {
    let year = time.slice(0, 4)
    let month = time.slice(5, 7)
    let lastDay = year + "-" + month + "-" + new Date(year, month, 0).getDate()
    return lastDay
}

/**
 * 将日期字符串 "20221122" 格式化为 "2022-11-22"
 * @param {String} dateString 日期字符串
 * @returns {String}
 */
function formatDate (dateString) {
    return dateString.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
}

/**
 * 从身份证号中截取出生年月日并返回，格式为 "19990826"
 * @param {String} idCard 身份证号
 * @returns {String} 生日
 * @example
 */
function idCard (idCard) {
    let pattern = /^\d{6}(\d{4})(\d{2})(\d{2})\d{4}$/;
    let match = idCard.match(pattern);
    if (match) {
        let year = match[1];
        let month = match[2];
        let day = match[3];
        let birthday = year + month + day;
        return birthday
    }
}

/**
 * 处理接口返回的数据，使其总是以数组的形式返回
 * @param response
 * @returns {*}
 */
function handleResponse (response) {
    if (Array.isArray(response)) {
        return response;
    } else if (response && typeof response === 'object') {
        return [response];
    } else {
        return [];
    }
}

/**
 * 解析URL中的查询参数，返回一个键值对的对象
 * @param data URL字符串
 * @returns 解析后的对象
 */
function getRequest (data) {
    let url = data;
    let theRequest = new Object();
    if (url.indexOf("?") != -1) {
        let str = url.substr(1);
        let n = str.indexOf("?");
        let str2 = str.substr(n + 1)
        let strs = str2.split("&");
        for (let i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
/**
 * 判断 URL 是否有域名并拼接前缀的逻辑
 * @param url URL字符串
 * @param prependUrl URL字符串
 * @returns 解析后的对象
 */
function prependUrl (url, prependUrl) {
    if (!url) {
        return '';
    }
    if (!/^https?:\/\//.test(url)) {
        return `${prependUrl}/${url}`;
    }
    return url;
}

/**
 * 生成 32 位 UUID 的函数
 */
function generateUUID () {
    let d = new Date().getTime();
    let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}
/**
 * 格式化字典值
 * @param {*} value
 * 0：即将发车
 * 1：即将进站
 * 负数为已过该站点
 * 正数为还未到站
 */

function formatDictionaryValue (value, type) {
    value = Number(value);
    if (value === 0) {
        return '已到站';
    } else if (value === 1) {
        return '即将进站';
    } else if (value < 0) {
        if (type) {
            return `已过站`;
        } else {
            return `已过${Math.abs(Number(value))}站`;
        }

    } else if (value > 0) {
        return value + '站';
    }
    return '未知状态';
}
// 当前时间在首末时间范围内返回1，否则2
function checkTimeInRange (startTimeStr, endTimeStr, currentTimestamp) {
    if (!startTimeStr || !endTimeStr || !currentTimestamp) {
        return 2;
    }
    // 将时间字符串转换为分钟数
    const startMinutes = parseInt(startTimeStr.split(':').map(Number).reduce((acc, cur, index) => acc + cur * (60 ** (1 - index)), 0));
    const endMinutes = parseInt(endTimeStr.split(':').map(Number).reduce((acc, cur, index) => acc + cur * (60 ** (1 - index)), 0));

    // 将时间戳转换为分钟数
    const currentMinutes = Math.floor(new Date(currentTimestamp).getHours() * 60 + new Date(currentTimestamp).getMinutes());

    if (currentMinutes >= startMinutes && currentMinutes <= endMinutes) {
        return 1;
    } else {
        return 2;
    }
}
// js按照数组['视力残疾', '听力残疾', '智力残疾', '肢体残疾', '精神残疾', '综合残疾']来映射
function changFormate (date) {
    let a = [];
    let b = [];
    const typeMap = {
        slcj: 0,
        tlcj: 1,
        zlcj: 2,
        ztcj: 3,
        jscj: 4,
        zhcj: 5
    };
    date = [
        { "xh": "1", "zlcj": "2376", "jscj": "2694", "hj": "34019", "slcj": "4027", "nf": "2023", "ztcj": "17900", "tlcj": "4368", "zhcj": "2654" },
        { "xh": "2", "zlcj": "2484", "jscj": "2706", "hj": "35373", "slcj": "4263", "nf": "2022", "ztcj": "18760", "tlcj": "4417", "zhcj": "2743" }
    ];

    date.forEach(item => {
        let tempA = [];
        for (let [key] of Object.entries(typeMap)) {
            let typeValue = parseInt(item[key]);
            tempA.push(typeValue);
        }
        a.push(tempA);
    });
    console.log(a);
    return a;
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
    idCard,
    handleResponse,
    getRequest,
    prependUrl,
    generateUUID,
    formatDictionaryValue,
    checkTimeInRange,
    changFormate
}
