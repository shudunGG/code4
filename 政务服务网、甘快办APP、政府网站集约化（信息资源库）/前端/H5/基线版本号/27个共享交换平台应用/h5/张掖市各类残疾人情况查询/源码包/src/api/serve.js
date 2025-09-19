/*
* @Author: zhangpeng
* @Description: ：api接口 网关接口
* @LastEditTime: 2024-03-08 15:05:04
* 此文件定义了与API通信的网关接口，包括生成交易订单、获取历史开奖结果等功能。
* 使用了axios工具库和jpaasRequest工具库进行HTTP请求。
*/

// 导入axios工具库和jpaasRequest工具库
import { axios } from "./axios"
import jpaasRequest from '@/utils/request'
const { getData } = jpaasRequest

// 定义接口URL前缀
const prefix = "/hanweb-payment-server"


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
  getPay (data) {
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
  getCmsInfo (appid, interface_id, params, columnId) {
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
  queryListFile (appid, interface_id, params, file) {
    return getData({
      appid, interface_id,
      params
    }, file)
  },

  /**
   * 网关接口
   * @param {string} appid - 应用ID
   * @param {string} interface_id - 接口ID
   * @param {Object} params - 请求参数
   * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
   */
  queryList (appid, interface_id, params) {
    return getData({
      appid,
      interface_id,
      params
    },)
  },

  /**
   * 加请求头网关接口
   * @param {string} appid - 应用ID
   * @param {string} interface_id - 接口ID
   * @param {Object} params - 请求参数
   * @param {Object} headers - 请求头
   * @returns {Promise} 返回Promise对象，解析后包含API响应数据。
   */
  queryListHeaders (appid, interface_id, params, headers) {
    return getData({
      appid, interface_id,
      params,
    }, headers)
  }
}