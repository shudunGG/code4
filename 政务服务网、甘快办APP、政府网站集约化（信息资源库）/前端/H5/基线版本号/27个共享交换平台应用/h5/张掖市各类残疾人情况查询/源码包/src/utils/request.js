/*
 * @Author: zhangpeng
 * @Description: 请求封装
 * @LastEditTime: 2024-03-08 15:05:04
 */
import axios from 'axios'
const httpRequestJpaasUrl = '/jpaas-jags-server/interface/'
import creatsign from './creatsign.js'
let time = new Date().getTime() // 获取当前时间戳
import { showToast, closeToast } from 'vant'
const jpaasRequest = {
    utf8StrToBytes: (utf8Str) => {
        let ens = encodeURIComponent(utf8Str)
        let es = unescape(ens)
        let esLen = es.length
        let words = []
        for (let i = 0; i < esLen; i++) {
            words[i] = es.charCodeAt(i)
        }
        return words
    },
    encode: (b, pos, len) => {
        let hexCh = new Array(len * 2)
        let hexCode = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        for (let i = pos, j = 0; i < len + pos; i++, j++) {
            hexCh[j] = hexCode[(b[i] & 0xff) >> 4]
            hexCh[++j] = hexCode[b[i] & 0x0f]
        }
        return hexCh.join('')
    },
    createSign: (params) => {
        let inputtext = 'app_id=' + params.get('app_id') + '&biz_content=' + params.get('biz_content') + '&charset=' + 'UTF-8' + '&interface_id=' + params.get('interface_id') + '&origin=' + '1' + '&timestamp=' + time + '&version=' + params.get('version')
        let dataBy = jpaasRequest.utf8StrToBytes(inputtext)
        let sm3 = new creatsign()
        sm3.update(dataBy, 0, dataBy.length)
        let sm3Hash = sm3.doFinal()
        let sm3HashHex = jpaasRequest.encode(sm3Hash, 0, sm3Hash.length)
        return sm3HashHex.toLowerCase()
    },
    gateway: (params = {}) => {
        return axios.post(httpRequestJpaasUrl + 'gateway.do', params)
    },

    getData: ({ appid, interface_id, params = {}, columnId = '' }, headers) => {
        try {
            const baseParams = new FormData()
            baseParams.append('version', '1.0')
            baseParams.append('charset', 'UTF-8')
            baseParams.append('origin', '1')
            baseParams.append('timestamp', time)
            baseParams.append('app_id', appid)
            baseParams.append('interface_id', interface_id)
            baseParams.append('biz_content', columnId
                ? JSON.stringify(Object.assign({ columnId: columnId }, params))
                : params instanceof Object
                    ? JSON.stringify(params)
                    : params)
            // baseParams.append('file', file ? file : '')
            baseParams.append('header', JSON.stringify(headers) || {})
            return new Promise(async (resolved) => {
                const sign = jpaasRequest.createSign(baseParams)
                baseParams.append('sign', sign)
                const response = await jpaasRequest.gateway(baseParams)
                setTimeout(() => {
                    time = new Date().getTime() // 1秒后重新赋值
                }, 1000)
                try {
                    if (response.data.code && response.data.success) {
                        resolved(JSON.parse(response.data.data))
                    } else {
                        resolved(response)
                    }
                } catch (e) {
                    resolved(response)
                }
            },
                () => {
                    closeToast();
                    showToast({
                        message: '网络异常，请稍后重试！'
                    })
                }
            )
        } catch (error) {
            throw error
        }
    }
}

export default jpaasRequest