import axios from 'axios'
import { jpassConfig } from './http'
/* eslint-disable */
const { httpRequestJpaas } = jpassConfig
const interface_id = 'jsd-data-convert-cms'
const header = JSON.stringify({
    'res-key': "FU7kqt0v",
    'key-secret': "F412590B9F19A9FFA6703DBEA266458B5161E10C75CF95F89228247AAED5D78C"
})
const requestJpaas = {

    // Jpaas签名
    createSignJpaas: function (params = {}) {
        return new Promise((resolved, rejected) => {
            axios({
                method: 'post',
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
                url: `${httpRequestJpaas}gateway`,
                data: params
            }).then(function (data) {
                if (data.data.code == 200 && data.data.success == true) {
                    const objData = JSON.parse(data.data.data)
                    if (objData.code == 200) {
                        resolved(objData)
                    } else {
                        const obj = JSON.parse(JSON.parse(data.data.data).data)

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
    getData: function ({ columnId = '', params = {} } = {}) {
        const baseParams = new URLSearchParams()
        baseParams.append('version', '1.0')
        baseParams.append('charset', 'UTF-8')
        baseParams.append('origin', '1')
        baseParams.append('timestamp', new Date().getTime())
        baseParams.append('interface_id', interface_id)
        baseParams.append('header', header)
        baseParams.append('app_id', 'cmsnzjk')
        baseParams.append('biz_content', JSON.stringify(Object.assign({ columnId: columnId }, params)))
        return new Promise((resolved, rejected) => {
            requestJpaas.createSignJpaas(baseParams).then(sign => {
                baseParams.append('sign', sign)
                return requestJpaas.getDataFromJpaas(baseParams)
            }).then(response => {
                try {
                    var obj = JSON.parse(response)
                    if (typeof obj === 'object' && obj) {
                        resolved(JSON.parse(response))
                    } else {
                        resolved(response)
                    }
                } catch (e) {
                    resolved(response)
                }
            }).catch(error => {
                rejected(error)
            })
        })
    }
}
export default requestJpaas
