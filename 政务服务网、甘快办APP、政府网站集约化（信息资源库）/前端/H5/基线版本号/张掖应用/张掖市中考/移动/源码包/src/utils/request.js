import axios from 'axios'
import { jpassConfig } from './http'
import { Toast } from 'vant'
/* eslint-disable */
const { httpRequestJpaas } = jpassConfig
const interface_id = 'listInfoEnnzjk'
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
          // console.log(objData.code);
          if (objData.code == 200) {
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
  getData: function ({ params = {} ,columnId = '' } = {}) {
    const baseParams = new URLSearchParams()
    baseParams.append('version', '1.0')
    baseParams.append('charset', 'UTF-8')
    baseParams.append('origin', '1')
    baseParams.append('timestamp', new Date().getTime())
    baseParams.append('interface_id', interface_id)
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
  },
  jpass2(appid, interfaceid, data) {
    return new Promise((resolve, reject) => {
      let that = this;
      let formdata = new FormData();
      formdata.append("app_id", appid);
      formdata.append("interface_id", interfaceid);
      formdata.append("version", "1.0");
      formdata.append("header", `{}`);
      formdata.append("biz_content", JSON.stringify(data));
      formdata.append("charset", "UTF-8");
      formdata.append("origin", "1");
      formdata.append("timestamp", new Date().valueOf());

      // lightAppJssdk.request.request({
      $.ajax({
        type: "post",
        url:
          "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do",
        data: formdata,
        contentType: false,
        // 告诉jQuery不要去设置Content-Type请求头
        processData: false,
        success: function(res1) {
          formdata.append("sign", res1.data.sign);
          // lightAppJssdk.request
          //   .request({
          $.ajax({
            type: "post",
            url:
              "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do",
            data: formdata,
            contentType: false,
            // 告诉jQuery不要去设置Content-Type请求头
            processData: false,
          }).then((data) => {
            // resolve(JSON.parse(data.data));
            resolve(data);
          });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          that.msg = "接口异常";
          that.open = true;
        },
      });
    });
  }
}
export default requestJpaas
