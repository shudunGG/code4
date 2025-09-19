var newJisUrl = "http://zwfw.sd.gov.cn/JIS/sso/interface.do"; //接口地址
var userType = "1"; //用户类型  1个人   2法人
// window.onload = function() {

// }
import CryptoJS from "crypto-js";
/**
 * 通过jssdk方法获取APP本地用户票据
 */
export function getUserInfo() {
	return new Promise((resolve, reject) => {
		lightAppJssdk.user.getTicket({
			success: function (data) { //成功回调
				// console.log(JSON.stringify(data));
				if (data == '未登录') {
					onLoginApp(); //APP用户未登录，调用登录页面
				} else {
					var jsonData = JSON.parse(data);
					if (jsonData.retcode == "000000") {
						var dataValueObj = JSON.parse(jsonData.data);
						var ticket = dataValueObj.ticket; //解析出用户票据信息
						userType = dataValueObj.usertype; //解析保存用户类型（个人法人）
						let userinfo = getUserToken(ticket);
						resolve(userinfo);
					}
				}
			},
			fail: function (data) { //错误返回
				console.log(JSON.stringify(data));
			}
		});
	});
}

/**
 * 使用jssdk调用登录页面
 */
function onLoginApp() {
	lightAppJssdk.user.loginapp({
		success: function (data) {
			if (data == "未登录") {
				//取消登录或登录失败，关闭页面
				oncloseWindow();
			} else {
				var dataObj = JSON.parse(data);
				if (dataObj.retcode == '000000') { //登录成功，返回用户的票据和用户类型
					var dataValueObj = JSON.parse(dataObj.data);
					getUserToken(dataValueObj.ticket); //解析出用户票据信息
					userType = dataValueObj.usertype; //解析保存用户类型（个人法人）
				} else {
					//关闭页面
					oncloseWindow();
				}
			}

		},
		fail: function (data) {
			//关闭页面
			oncloseWindow();
		}
	});
}

/**
 * 通过用户票据获取用户的令牌数据，票据使用一次后失效，token时效性较长
 */
function getUserToken(ticket) {
	return new Promise((resolve, reject) => {
		var params = '{"ticket":"' + ticket + '"}';
		var data = {
			appmark: "sdzwapp",
			time: "20191215213400",
			sign: hex_md5("sdzwappaKd20dbGdFvmuwrt20191215213400"),
			servicename: "ticketValidate",
			params: params
		};
		lightAppJssdk.request.request({
			url: newJisUrl,
			data: data,
			header: '',
			dataType: 'json', //返回数据格式，默认json                
			async: true, //同步设置                
			success: function (data) {
				console.log("ticketValidate===================" + JSON.stringify(data));
				if (data.retcode == '000000') {
					var userToken = JSON.parse(data.data).token;
					if (userType == "1") { //个人类型获取用户信息
						let userinfo = getUserInfoByToken(userToken);
						resolve(userinfo);
					} else { //法人类型获取用户信息
						let userinfo = findCorUserByToken(userToken);
						resolve(userinfo);
					}
				} else { }
			},
			fail: function (data) {
				console.log(data);
			}
		});
	});
}

/**
 * 根据令牌获取个人完整信息
 */
function getUserInfoByToken(userToken) {
	return new Promise((resolve, reject) => {
		var params = '{"token":"' + userToken + '"}';
		var data = {
			appmark: "sdzwapp",
			time: "20191215213400",
			sign: hex_md5("sdzwappaKd20dbGdFvmuwrt20191215213400"),
			servicename: "findOutsideUserByToken",
			params: params
		};
		lightAppJssdk.request.request({
			url: newJisUrl,
			data: data,
			header: '',
			dataType: 'json', //返回数据格式，默认json
			async: true, //同步设置
			success: function (data) {
				console.log("getUserInfoByToken", data);
				if (data.retcode == '000000') {
					var userinfo = Decrypt(data.data, "aKd20dbGdFvmuwrt");
					var userObj = JSON.parse(userinfo);
					resolve(userObj)
				} else { }
			},
			fail: function (data) { }
		});
	});
}

/**
 * 根据令牌获取完整法人信息
 */
function findCorUserByToken(userToken) {
	return new Promise((resolve, reject) => {
		var params = '{"token":"' + userToken + '"}';
		var data = {
			appmark: "sdzwapp",
			time: "20191215213400",
			sign: hex_md5("sdzwappaKd20dbGdFvmuwrt20191215213400"),
			servicename: "findCorUserByToken",
			params: params
		};
		lightAppJssdk.request.request({
			url: newJisUrl,
			data: data,
			header: '',
			dataType: 'json', //返回数据格式，默认json
			async: true, //同步设置
			success: function (data) {
				console.log("findCorUserByToken" + JSON.stringify(data));
				if (data.retcode == '000000') {
					var userinfo = Decrypt(data.data, "aKd20dbGdFvmuwrt");
					var userObj = JSON.parse(userinfo);
					resolve(userObj)
				} else { }
			},
			fail: function (data) { }
		});
	})
}

/**
 * 关闭容器
 */
function oncloseWindow() {
	lightAppJssdk.navigation.close({
		success: function (data) { },
		fail: function (data) { }
	});
};

/**
 * AES解密
 */
function Decrypt(word, key) {
	var key = CryptoJS.enc.Utf8.parse(key); //转为128bit
	var srcs = CryptoJS.enc.Hex.parse(word); //转为16进制
	var str = CryptoJS.enc.Base64.stringify(srcs); //变为Base64编码的字符串
	var decrypt = CryptoJS.AES.decrypt(str, key, {
		mode: CryptoJS.mode.ECB,
		spadding: CryptoJS.pad.Pkcs7
	});
	return decrypt.toString(CryptoJS.enc.Utf8);
}