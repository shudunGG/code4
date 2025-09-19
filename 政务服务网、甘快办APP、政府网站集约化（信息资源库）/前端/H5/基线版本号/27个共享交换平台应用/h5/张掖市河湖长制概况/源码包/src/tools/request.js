import axios from 'axios'
import {
  Toast
} from 'vant'
import Hex from "./hex.js";
import SM3Digest from "./sm3-1.0.js";
let time = new Date().getTime(); // 获取当前时间戳
/* eslint-disable */
const httpRequestJpaas = 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/'
// const interface_id = 'showcomponent'
const requestJpaas = {
  // Jpaas签名
  createSignJpaas: function (params = {}) {
    try {
      var inputtext =
        "app_id=" +
        params.get("app_id") +
        "&biz_content=" +
        params.get("biz_content") +
        "&charset=" +
        "UTF-8" +
        "&interface_id=" +
        params.get("interface_id") +
        "&origin=" +
        "1" +
        "&timestamp=" +
        time +
        "&version=" +
        params.get("version");

      var dataBy = Hex.utf8StrToBytes(inputtext);
      var sm3 = new SM3Digest();
      sm3.update(dataBy, 0, dataBy.length);
      var sm3Hash = sm3.doFinal();
      var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length);

      return sm3HashHex.toLowerCase();
    } catch (error) {
      console.log(error);
    }
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
            if (data.data.code == 200) {
              resolved(objData)
            } else {
              const obj = JSON.parse(JSON.parse(data.data.data).data)
              Toast({
                message: obj.data
              })
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

  getData: function ({
    interface_id,
    app_id,
    params = {}
  }) {
    // console.log(id);
    const baseParams = new URLSearchParams()
    // baseParams.append('header', '{}')
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', time)
    baseParams.append('interface_id', interface_id)
    baseParams.append('app_id', app_id)
    baseParams.append('biz_content', JSON.stringify(params))

    return new Promise((resolved, rejected) => {
      const sign = requestJpaas.createSignJpaas(baseParams)
      baseParams.append("sign", sign);
      const response = requestJpaas.getDataFromJpaas(baseParams);
      setTimeout(() => {
        time = new Date().getTime(); // 1秒后重新赋值
      }, 1000);
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
    })
  }
}
export default requestJpaas