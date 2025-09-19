import CryptoJS from 'crypto-js/crypto-js'
import {
	sm2
} from 'sm-crypto'

// 默认的 KEY 与 iv 如果没有给
const KEY = CryptoJS.enc.Utf8.parse("12345678900987654321"); //""中与后台一样  密码
const IV = CryptoJS.enc.Utf8.parse(); //""中与后台一样
let s = "12345678900987654321"
let codeStr = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

const tools = {
	// sm2 加密
	encrypt: function (str, publicKey) {
		return '04' + sm2.doEncrypt(str, publicKey, 1)
	},
	// sm2 解密
	decrypt: function (str, privateKey) {
		return sm2.doDecrypt(str.slice(2), privateKey, 1)
	},
	// rsa解密
	RSAdecryption: function (str) { // 注册方法
		let pubKey = `-----BEGIN PRIVATE KEY-----
        MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJvxDGJfx7WHC/NRUM27B9gPbE6EO63nGhLBdajx1NHF0R9YWegRcxX7KpVM1s/LLqts/2wAoxmwncjTlseOBqYrscXf2mrMU/R279dbOmUEW3upP201YlwXQ0barcNLkChtxlSsq7M1+bM+Q8vpiES3CIxlYbI19OC8d3TRuq0zAgMBAAECgYA/qN3HYxZiEqlcQTvdYJbJKzKpsu2yBn/sXDGuMFsS79B5HVZGmKkEti4iGOYwmd+Jd8ulyXTLzf/7tfT9rgskMwi1/6XdkDHxPqGSmZKPBUO/dxmHij75IyuacqzKzG0hQboqMcOdaa35QAfZVTPyR3vUxJK6KTJfKf03GuG3IQJBANz0zZUzccB0h+SNh57faCknuMUI7I6Ex2D8JF5EtksUFceEcTMA4HuXyPgOPdDnjVGstUl9qMF98ilxvFVXCTsCQQC0rIpJbcKBIcJqlvcyDtqQWUhY2GrWYO0gBg1k3A1QyRqGnBpJBaM8aEdqYypXp2Z4OiMUi/+K42/moTNxOWxpAkBOCGKmY6GzrzPi4pzX3dxiVHXrmAg7GOd09/c8F5Ytk61RZhO4SIH/BA7kk9r+8Re2lYD8sFUt6HXibxltJizLAkAgwmQ0mjU5StDAmOzrY6wXnkiy61K72klElDRbhFbZvg0UOTu+38nQSywfyylDSXQX+pqfwB6upTyF+dalAf95AkEAqYoBBfABXhd0+cJBlubh4VV4T2yUwyz6YuoHcmX9wVezKkKox8RmQUYRWJ+qIj/+s60kVEmXzPd9NKegVKe7Lw==
        -----END PRIVATE KEY-----
        `; // ES6 模板字符串 引用 rsa 私钥
		let encryptStr = new JSEncrypt();
		encryptStr.setPrivateKey(pubKey); // 设置 私钥
		let data = encryptStr.decrypt(str.toString()); // 进行解密
		return data;
	},
	// rsa加密
	RSAencryption: function (str) { // 注册方法
		let pubKey = `-----BEGIN PUBLIC KEY-----
        MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCb8QxiX8e1hwvzUVDNuwfYD2xOhDut5xoSwXWo8dTRxdEfWFnoEXMV+yqVTNbPyy6rbP9sAKMZsJ3I05bHjgamK7HF39pqzFP0du/XWzplBFt7qT9tNWJcF0NG2q3DS5AobcZUrKuzNfmzPkPL6YhEtwiMZWGyNfTgvHd00bqtMwIDAQAB
        -----END PUBLIC KEY-----`; // ES6 模板字符串 引用 rsa 公钥
		let encryptStr = new JSEncrypt();
		encryptStr.setPublicKey(pubKey); // 设置 加密公钥
		let data = encryptStr.encrypt(str.toString()); // 进行加密
		return data;
	},
	// 信息校验 start
	// 手机号校验
	isPhone: function (phoneStr) {
		let myreg =
			/^((13[0-9])|(14[1|4|5|6|7|8|9])|(15([0|1|2|3|5|6|7|8|9]))|(16[2|5|6|7])|(17[0|1|2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\d{8}$/;
		if (!myreg.test(phoneStr)) {
			return false;
		} else {
			return true;
		}
	},
	// 身份证号校验
	isCard: function (cardStr) {
		let myreg =
			/^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X]|[x])$/;
		if (!myreg.test(cardStr)) {
			return false;
		} else {
			return true;
		}
	},
	encodeCJZH: (card) => {
		return card.replace(/^(.).*(.)$/, '$1***********$2')
	},
	// 邮箱校验
	isEmail: function (emailStr) {
		let myreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if (!myreg.test(emailStr)) {
			return false;
		} else {
			return true;
		}
	},
	// 信息校验 end

	// 信息脱敏 start
	encodeName: function (str) {
		if (null != str && str != undefined) {
			if (str.length <= 2) {
				return "*" + str.substr(1, str.length);
			} else if (str.length <= 3) {
				return "**" + str.substr(2, str.length);
			} else if (str.length > 3 && str.length <= 6) {
				return "**" + str.substr(2, str.length);
			} else if (str.length > 6) {
				return str.substr(0, 2) + "****" + str.substr(6, str.length);
			}
		} else {
			return "";
		}
	},

	encodeSFZ: function (str) {
		if (str != null && str != undefined && str.length > 8) {
			return "*********" + str.substr(14, 18);
		} else {
			return str;
		}
	},
	// 信息脱敏 end

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
		for (let i = 1; i < len; i++) {
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
		let encrypted = CryptoJS.AES.encrypt(srcs, key, {
			iv: iv,
			mode: CryptoJS.mode.ECB,
			padding: CryptoJS.pad.Pkcs7
		});
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

		let decrypt = CryptoJS.AES.decrypt(src, key, {
			iv: iv,
			mode: CryptoJS.mode.ECB,
			padding: CryptoJS.pad.Pkcs7
		});

		let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
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
		let o = {
			"y": date.getFullYear(), //年份
			"M": date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1, //月份
			"d": date.getDate() < 10 ? '0' + date.getDate() : date.getDate(), //日
			// "h": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时
			"H": date.getHours() < 10 ? '0' + date.getHours() : date.getHours(), //小时
			"m": date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes(), //分
			"s": date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds(), //秒
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
			let temp = n;
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
		let str = '';
		// 验证码有几位就循环几次
		for (let i = 0; i < 4; i++) {
			let ran = this.getRandom(0, 62);
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
	base64Compression: function (ele, bili = 3, vue) {
		let file = document.getElementById(ele).files[0]; //取传入的第一个文件
		if (undefined == file) {
			//如果未找到文件，结束函数，跳出
			return;
		}
		return new Promise((resolve) => {
			let r = new FileReader();
			r.readAsDataURL(file);
			r.onload = function (e) {
				let base64 = e.target.result;
				vue.cont2 = base64.length;
				let _img = new Image();
				_img.src = base64;
				_img.onload = function () {
					let _canvas = document.createElement("canvas");
					let w = this.width / bili;
					let h = this.height / bili;
					_canvas.setAttribute("width", w);
					_canvas.setAttribute("height", h);
					_canvas.getContext("2d").drawImage(this, 0, 0, w, h);
					let base64 = _canvas.toDataURL("image/jpeg");
					_canvas.toBlob(function () {
						resolve(base64);
					}, "image/jpeg");
				};
			};
		})
	},
	saveImg: function (id) {
		html2canvas(document.body).then(function (canvas) {
			document.body.appendChild(canvas);
		});
		setTimeout(function () {
			let url = document.getElementById(id).src;
			let a = document.createElement("a"); // 创建一个a节点插入的document
			let event = new MouseEvent("click"); // 模拟鼠标click点击事件
			a.download = "未命名"; // 设置a节点的download属性值
			a.href = url; // 将图片的src赋值给a节点的href
			a.dispatchEvent(event);
		}, 1000);
	},
	//通用工具类 end
}
export {
	tools
}
