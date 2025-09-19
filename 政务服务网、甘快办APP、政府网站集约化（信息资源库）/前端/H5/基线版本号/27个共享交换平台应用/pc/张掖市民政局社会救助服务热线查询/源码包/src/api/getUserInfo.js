/**
 * 作者：zp
 * 创建时间：2023/6/1
 * 描述：getUserInfo 封装
 */
import axios from "axios";
// 将API URL定义为常量，提高可读性和可维护性
const USER_INFO_URL = "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/getCurrentUserInfoNew1";
const TOKEN_URL = "https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust";
const URL = "https://zwfw.gansu.gov.cn";
// 创建一个公共的axios请求处理函数
function Request (requestConfig) {
    return new Promise((resolve, reject) => {
        axios(requestConfig)
            .then((response) => {
                resolve(response.data);
            })
            .catch((error) => {
                // 更细致的错误处理，比如记录错误日志等
                console.error("请求失败:", error);
                reject(error);
            });
    });
}

/**
 * 异步获取用户信息的函数。
 * 该函数首先发送一个POST请求到指定的URL以获取用户信息。
 * 如果请求成功，函数将返回响应数据；如果请求失败，将重定向到登录页面。
 * 
 * @returns {Object} 如果请求成功，返回包含用户信息的响应对象；如果请求失败，会在控制台抛出错误。
 */
async function getUserInfo () {
    try {
        // 发送POST请求获取用户信息
        const response = await Request({
            method: "POST",
            url: USER_INFO_URL,
            headers: { 'Content-Type': 'application/json' }, // 明确设置请求头为JSON格式
        });

        // 判断响应是否表示成功
        if (response.success === true || response.code === "200") {
            return response;
        } else {
            // 如果响应表示失败，重定向到登录页面
            location.href =
                URL + "/szgs/member/wwLogin/againLgin.do?againUrl=" +
                encodeURIComponent(location.href);
        }
    } catch (error) {
        // 请求过程中出现的错误将在此处处理，并抛出错误
        throw error;
    }
}

/**
 * 异步获取token的方法。
 * 该方法通过发送GET请求到指定的URL来获取token，并且会设置请求的Content-Type为application/json。
 * 
 * @returns {Promise} 如果请求成功并且响应码为200，返回响应对象；否则抛出获取token失败的错误。
 */
async function getToken () {
    try {
        // 发送GET请求来获取token
        const response = await Request({
            method: "GET",
            url: TOKEN_URL,
            headers: { 'Content-Type': 'application/json' }, // 明确设置请求的Content-Type为application/json
        });
        // 检查响应是否成功
        if (response.code == 200 && response.success) {
            return response;
        } else {
            // 如果响应不成功，抛出错误
            throw new Error("获取token失败。");
        }
    } catch (error) {
        // 捕获并处理任何在请求过程中发生的错误
        throw error;
    }
}

export { getUserInfo, getToken };