import CryptoJS from 'crypto-js/crypto-js'
import html2canvas from "html2canvas";

// 默认的 KEY 与 iv 如果没有给
const KEY = CryptoJS.enc.Utf8.parse("12345678900987654321");//""中与后台一样  密码
const IV = CryptoJS.enc.Utf8.parse();//""中与后台一样
var s = "12345678900987654321"
let codeStr = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

const tools = {
    // 信息校验 start
    // 手机号校验
    isPhone: function (phoneStr) {
        console.log(phoneStr)
        var myreg = /^((13[0-9])|(14[1|4|5|6|7|8|9])|(15([0|1|2|3|5|6|7|8|9]))|(16[2|5|6|7])|(17[0|1|2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\d{8}$/;
        if (!myreg.test(phoneStr)) {
            return false;
        } else {
            return true;
        }
    },
    // 身份证号校验
    isCard: function (cardStr) {
        console.log(cardStr)
        var myreg = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X]|[x])$/;
        if (!myreg.test(cardStr)) {
            return false;
        } else {
            return true;
        }
    },
    // 邮箱校验
    isEmail: function (emailStr) {
        console.log(emailStr)
        var myreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (!myreg.test(emailStr)) {
            return false;
        } else {
            return true;
        }
    },
    // 信息校验 end

    // 信息脱敏 start
    /**
     * 
     * @param {身份号} cardStr 
     * @param {手机号} phoneStr 
     * @param {姓名} name 
     * @param {邮箱} emailStr 
     * @param {用户名} Accountname 
     */
    idcardDesensitization: function (cardStr = '', phoneStr = '', name = '', emailStr = '', Accountname = '') {
        let star = '*';
        const len = cardStr.toString().length - 4;
        for (var i = 1; i < len; i++) {
            star = star + '*';
        }
        return {
            userName: name.replace(/.(?=.)/g, '*'),
            mobile: phoneStr.substring(0, 3) + "****" + phoneStr.substring(7, 11),
            idCard: star + cardStr.substring(14, 18),
            Accountname: Accountname.replace(/.(?=.)/g, '*'),
            email: emailStr.substr(0, 2) + "****" + emailStr.substr(emailStr.indexOf('@'))
        };
    },
    // 信息脱敏 end

    //信息加密工具类 start
    // aes 加密


    /**
     * AES加密 ：字符串 key iv  返回base64
     */
    Encrypt: function (word, keyStr = s, ivStr) {
        let key = KEY
        let iv = IV
        if (keyStr) {
            key = CryptoJS.enc.Utf8.parse(keyStr);
            iv = CryptoJS.enc.Utf8.parse(ivStr);
        }

        let srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {
            iv: iv,
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        // console.log("-=-=-=-", encrypted.ciphertext)
        return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
    },
    /**
     * AES 解密 ：字符串 key iv  返回base64
     *
     */
    Decrypt: function (word, keyStr = s, ivStr) {
        let key = KEY
        let iv = IV
        if (keyStr) {
            key = CryptoJS.enc.Utf8.parse(keyStr);
            iv = CryptoJS.enc.Utf8.parse(ivStr);
        }

        let base64 = CryptoJS.enc.Base64.parse(word);
        let src = CryptoJS.enc.Base64.stringify(base64);

        var decrypt = CryptoJS.AES.decrypt(src, key, {
            iv: iv,
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });

        var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
        return decryptedStr.toString();
    },
    //信息加密工具类 end 
    //通用工具类 start
    // 格式化时间戳(
    /**
     * 
     * @param {时间戳 可不传} date 
     */
    formatTime: function (date = new Date()) {
        var o = {
            "y": date.getFullYear(), //年份
            "M": date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1, //月份
            "d": date.getDate() < 10 ? '0' + date.getDate() : date.getDate(), //日
            // "h": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时
            "H": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
        };
        return {
            o,
            type: o.y + "-" + o.M + "-" + o.d + " " + o.H + ":" + o.m + ":" + o.s,
            typeOne: o.y + "-" + o.M + "-" + o.d,
            typeTwo: o.y + "/" + o.M + "/" + o.d + " " + o.H + ":" + o.m + ":" + o.s,
            typeThree: o.y + "/" + o.M + "/" + o.d,
            typeFour: o.y + "年" + o.M + "月" + o.d + "日",
            typeFive: o.y + "年" + o.M + "月" + o.d + "日" + " " + o.H + ":" + o.m + ":" + o.s
        };
    },
    // 判断手机是否为iPhoneX
    isIphoneX: function () {
        return /iphone/gi.test(navigator.userAgent) && (screen.height == 812 && screen.width == 375)
    },
    // 获取当前时段
    getMoment: function () {
        const h = (new Date()).getHours()
        let greetingMsg = ""
        if (0 <= h && h < 6) {
            greetingMsg = "凌晨好"
        } else if (6 <= h && h <= 11) {
            greetingMsg = "早上好"
        } else if (11 < h && h <= 14) {
            greetingMsg = "中午好"
        } else if (14 < h && h <= 17) {
            greetingMsg = "下午好"
        } else if (17 < h && h <= 24) {
            greetingMsg = "晚上好"
        }
        return greetingMsg
    },
    // 获取随机验证码
    // 用来生成随机整数
    getRandom: function (n, m) { // param: (Number, Number)
        n = Number(n);
        m = Number(m);
        // 确保 m 始终大于 n
        if (n > m) {
            var temp = n;
            n = m;
            m = temp;
        }
        return Math.floor(Math.random() * (m - n) + n);
    },
    /**
     * 
     * @param {页面提示元素的id} element 
     */
    getCode: function (element) {
        var str = '';
        // 验证码有几位就循环几次
        for (var i = 0; i < 4; i++) {
            var ran = this.getRandom(0, 62);
            str += codeStr.charAt(ran);
        }
        if (element) {
            document.getElementById(element).innerText = str;
        }
        return str;
    },
    // 验证密码强度
    /**
     * 
     * @param {设置的密码} oValue 
     */
    passwordStrength: function (oValue) {
        oValue = oValue.replace(/[\u4E00-\u9FA5]/g, "");
        if (/\d/.test(oValue) && /[a-z]/.test(oValue) && /[A-Z]/.test(oValue)) {
            return {
                type: '2',
                text: "强"
            }
        } else if (
            /^\d+$/.test(oValue) ||
            /^[A-Z]+$/.test(oValue) ||
            /^[a-z]+$/.test(oValue)
        ) {
            return {
                type: '0',
                text: "弱"
            }
        } else {
            return {
                type: '1',
                text: "中"
            }
        }
    },
    // base64 压缩
    /**
     * 
     * @param {input type=file 的 id} ele 
     * @param {缩放比例} bili 
     */
    base64Compression: function (ele, bili = 3,vue) {
        var file = document.getElementById(ele).files[0]; //取传入的第一个文件
        if (undefined == file) {
            //如果未找到文件，结束函数，跳出
            return;
        }
        return new Promise((resolve, reject) => {
            var r = new FileReader();
            r.readAsDataURL(file);
            r.onload = function (e) {
                var base64 = e.target.result;
                vue.cont2 = base64.length;
                console.log("压缩前：", base64.length);
                console.log("压缩前：", base64);
                var _img = new Image();
                _img.src = base64;
                _img.onload = function () {
                    var _canvas = document.createElement("canvas");
                    var w = this.width / bili;
                    var h = this.height / bili;
                    _canvas.setAttribute("width", w);
                    _canvas.setAttribute("height", h);
                    _canvas.getContext("2d").drawImage(this, 0, 0, w, h);
                    var base64 = _canvas.toDataURL("image/jpeg");
                    _canvas.toBlob(function (blob) {
                        console.log("压缩后", base64.length);
                        resolve(base64);
                    }, "image/jpeg");
                };
            };
        })
    },
    saveImg: function (id) {
        // let url = document.getElementById(id).src;
        // html2canvas(document.body).then(function (canvas) {
        //     document.body.appendChild(canvas);
        //     let base64ImgSrc = canvas.toDataURL("image/png")
        //     console.log("html", base64ImgSrc);
        // });
        setTimeout(function () {
            let url = document.getElementById(id).src;
            var a = document.createElement("a"); // 创建一个a节点插入的document
            var event = new MouseEvent("click"); // 模拟鼠标click点击事件
            a.download = "未命名"; // 设置a节点的download属性值
            a.href = url; // 将图片的src赋值给a节点的href
            a.dispatchEvent(event);
            console.log(123)
        }, 1000);
    },
    //通用工具类 end
}
export {
    tools
}