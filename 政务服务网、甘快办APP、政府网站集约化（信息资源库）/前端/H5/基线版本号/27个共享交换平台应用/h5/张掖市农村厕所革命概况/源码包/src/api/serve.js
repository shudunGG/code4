/*
* @Author: ZP
* @Description: ：api接口 网关接口
* @LastEditTime: 2024-03-08 15:05:04
* 此文件定义了与API通信的网关接口，包括生成交易订单、获取历史开奖结果等功能。
* 使用了axios工具库和jpaasRequest工具库进行HTTP请求。
*/

// 导入axios工具库和jpaasRequest工具库
import { axios } from "./axios"
import jpaasRequest from '../utils/request'
const { getData } = jpaasRequest
import Hex from './hex.js'
import SM3Digest from './sm3-1.0.js'

// 定义接口URL前缀
const prefix = "/hanweb-payment-server"
let time = new Date().getTime() // 获取当前时间戳

// 定义API接口地址
const api = {
    getPay: prefix + '/open-api/wechat/pay/trade-order/app', // 生成交易订单的API地址
}

/**
 * 封装了API请求的模块
 * 提供了生成交易订单、获取历史开奖结果等接口的调用方法。
 */
export const serveAPI = {
    /**
     * 生成交易订单
     * @param {Object} data - 请求数据
     * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
     */
    getPay(data) {
        return axios({
            url: api.getPay,
            method: 'post',
            data,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })
    },
    /**
     * 获取cms栏目信息
     * @param {string} appid - 应用ID
     * @param {string} interface_id - 接口ID
     * @param {Object} params - 请求参数
     * @param {string} columnId - 栏目ID
     * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
     */
    getCmsInfo(appid, interface_id, params, columnId) {
        return getData({
            appid, interface_id,
            params,
            columnId: columnId
        })
    },

    /**
     * 上传文件网关接口
     * @param {string} appid - 应用ID
     * @param {string} interface_id - 接口ID
     * @param {Object} params - 请求参数
     * @param {File} file - 文件对象
     * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
     */
    // queryListFile(appid, interface_id, params, file) {
    //     return getData({
    //         appid, interface_id,
    //         params
    //     },
    //         file)
    // },

    /**
     * 网关接口
     * @param {string} appid - 应用ID
     * @param {string} interface_id - 接口ID
     * @param {Object} params - 请求参数
     * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
     */
    queryList(appid, interface_id, params) {
        return getData({
            appid, interface_id,
            params
        })
    },

    // 获取签名
    createSign: (appid,params,interface_id) => {
        var inputtext = 'app_id=' + appid + '&biz_content=' + JSON.stringify(params) + '&charset=' + 'UTF-8' + '&interface_id=' + interface_id + '&origin=' + '1' + '&timestamp=' + time + '&version=1.0'
        console.log('生成验签的内容:', inputtext)
        var dataBy = Hex.utf8StrToBytes(inputtext)
        var sm3 = new SM3Digest()
        sm3.update(dataBy, 0, dataBy.length)
        var sm3Hash = sm3.doFinal()
        var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length)
        console.log('生成的验签:', sm3HashHex.toLowerCase())
        return sm3HashHex.toLowerCase()
    },

    /**
     * 加请求头网关接口
     * @param {string} appid - 应用ID
     * @param {string} interface_id - 接口ID
     * @param {Object} params - 请求参数
     * @param {Object} headers - 请求头
     * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
     */
    queryListHeaders(appid, interface_id, params, headers)
    {
        return getData({
            appid, interface_id,
            params,
        }, headers)
    },
    jpass2(appid, interface_id, data) {
        return new Promise((resolve, reject) => {
            let that = this
            let formdata = new FormData()
            // const sign = serveAPI.createSign(appid,data,interface_id)
            formdata.append('app_id', appid)
            formdata.append('interface_id', interface_id)
            formdata.append('version', '1.0')
            formdata.append('header', `{}`)
            formdata.append('biz_content', JSON.stringify(data))
            formdata.append('charset', 'UTF-8')
            formdata.append('origin', '1')
            formdata.append('timestamp', new Date().valueOf())
            // formdata.append('sign', sign)

            // $.ajax({
            //     type: 'post',
            //     url:
            //         'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
            //     data: formdata,
            //     contentType: false,
            //     // 告诉jQuery不要去设置Content-Type请求头
            //     processData: false,
            // }).then((data) => {
            //     // resolve(JSON.parse(data.data))
            //     resolve(data)
            // })

            // lightAppJssdk.request.request({
            $.ajax({
                type: 'post',
                url:
                    'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do',
                data: formdata,
                contentType: false,
                // 告诉jQuery不要去设置Content-Type请求头
                processData: false,
                success: function (res1) {
                    formdata.append('sign', res1.data.sign)
                    // lightAppJssdk.request
                    //   .request({
                    $.ajax({
                        type: 'post',
                        url:
                            'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
                        data: formdata,
                        contentType: false,
                        // 告诉jQuery不要去设置Content-Type请求头
                        processData: false,
                    }).then((data) => {
                        // resolve(JSON.parse(data.data))
                        resolve(data)
                    })
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    that.msg = '接口异常'
                    that.open = true
                }
            })
        })
    }
}
