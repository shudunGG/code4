/*
* @Author: ZP
* @Description: 请求封装
* @LastEditTime: 2024-03-08 15:05:04
*/
import axios from 'axios'
const httpRequestJpaasUrl = '/jpaas-jags-server/interface/'
import Hex from '../api/hex.js'
import SM3Digest from '../api/sm3-1.0.js'

let time = new Date().getTime() // 获取当前时间戳

const jpaasRequest = {
    // 获取签名
    createSign: (params) => {
        var inputtext = 'app_id=' + params.get('app_id') + '&biz_content=' + params.get('biz_content') + '&charset=' + 'UTF-8' + '&interface_id=' + params.get('interface_id') + '&origin=' + '1' + '&timestamp=' + time + '&version=' + params.get('version')
        console.log('生成验签的内容:', inputtext)
        var dataBy = Hex.utf8StrToBytes(inputtext)
        var sm3 = new SM3Digest()
        sm3.update(dataBy, 0, dataBy.length)
        var sm3Hash = sm3.doFinal()
        var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length)
        console.log('生成的验签:', sm3HashHex.toLowerCase())
        return sm3HashHex.toLowerCase()
    },
    // 网关请求 
    gateway: (params = {}) => {
        return new Promise((resolved, rejected) => {
            axios({
                method: 'post',
                url: httpRequestJpaasUrl + 'gateway',
                data: params
            }).then((res) => {
                if (res.data.code == 200) {
                    resolved(res)
                } else {
                    rejected(res)
                }
            }).catch((error) => {
                rejected(error)
            })
        })
    },
    // 获取网关数据
    getData: ({ appid, interface_id, params = {}, columnId = '' }, header) => {
        const baseParams = new FormData()
        baseParams.append('version', '1.0')
        baseParams.append('charset', 'UTF-8')
        baseParams.append('origin', '1')
        baseParams.append('timestamp', time)
        baseParams.append('app_id', appid)
        baseParams.append('interface_id', interface_id)
        if (header) {
            baseParams.append('header', header)
        }
        const bizContent = columnId
            ? JSON.stringify(Object.assign({ columnId: columnId }, params))
            : JSON.stringify(params)
        baseParams.append('biz_content', bizContent)
        return new Promise((resolved, rejected) => {
            // 获取签名
            const sign = jpaasRequest.createSign(baseParams)
            // 签名
            baseParams.append('sign', sign)
            const response = jpaasRequest.gateway(baseParams)
            setTimeout(() => {
                time = new Date().getTime() // 1秒后重新赋值
            }, 1000)
            try {
                let obj = JSON.parse(response)
                if (typeof obj === 'object' && obj) {
                    resolved(obj)
                } else {
                    resolved(obj)
                }
            } catch (error) {
                resolved(response)
            }
        })
    }
}
export default jpaasRequest