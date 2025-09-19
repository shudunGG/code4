import axios from 'axios'
import { jpassConfig } from './http'
/* eslint-disable */
const { httpRequestJpaas } = jpassConfig

const requestJpaas = {
    // jags签名
    createSignJpaas: function (params = {}) {

        return new Promise((resolved, rejected) => {

            axios({
                method: 'post',
                // url: process.env.NODE_ENV === 'development'
                //     ? `${httpRequestJpaas}createsign`
                //     : 'http://111.42.127.101:42000//jpaas-jags-server/interface/createsign',
                url: `${httpRequestJpaas}createsign`,
                data: params
            }).then(function (data) {
                if (data.data.success) {
                    const { sign } = data.data.data
                    resolved(sign)
                } else {
                    rejected(data)
                }
            })
                .catch(function (error) {
                    rejected(error)
                })
        })
    },
    getDataFromJpaas: function (params = {}) {
        return new Promise((resolved, rejected) => {
            axios({
                method: 'post',
                // url: process.env.NODE_ENV === 'development'
                //     ? `${httpRequestJpaas}gateway`
                //     : 'http://111.42.127.101:42000/jpaas-jags-server/interface/gateway',
                url: `${httpRequestJpaas}gateway`,
                data: params
            }).then(function (data) {
                if (data.data.code == 200 && data.data.success) {
                    const objData = JSON.parse(data.data.data)
                    if (objData.code == 200) {
                        resolved(objData)
                    } else {
                        const obj = JSON.parse(data.data.data)
                        resolved(objData)
                        rejected(obj)
                    }
                } else {
                    rejected(data)
                }
            })
                .catch(function (error) {
                    rejected(error)
                })
        })
    },
    getData: function ({ appid, interface_id, params = {} } = {}) {
        const baseParams = new URLSearchParams()
        // baseParams.append('header', '{}')
        baseParams.append('version', '1.0')
        baseParams.append('charset', 'UTF-8')
        baseParams.append('origin', '1')
        baseParams.append('timestamp', new Date().getTime())
        baseParams.append('interface_id', interface_id)
        baseParams.append('app_id', appid)
        baseParams.append('biz_content', JSON.stringify(Object.assign(params)))
        return new Promise((resolved, rejected) => {
            requestJpaas.createSignJpaas(baseParams).then(sign => {
                baseParams.append('sign', sign)
                return requestJpaas.getDataFromJpaas(baseParams)
            }).then(response => {
                resolved(response)
            }).catch(error => {
                rejected(error)
            })
        })
    }
}

export default requestJpaas
