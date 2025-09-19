import { axios } from "./request"
import requestJpaas from '../utils/request'
const { getData } = requestJpaas


const prefix = "/hanweb-payment-server"

const api = {
    getPay: prefix + '/open-api/wechat/pay/trade-order/app', // 生成交易订单
}

export const serveAPI = {
    getPay (data) {
        return axios({
            url: api.getPay,
            method: 'post',
            data,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })
    },
    getCmsInfo (appid, interface_id, params, columnId) {
        return getData({
            appid, interface_id,
            params,
            columnId: columnId
        })
    },
    queryList (appid, interface_id, params) {
        return getData({
            appid, interface_id,
            params
        },)
    },
}