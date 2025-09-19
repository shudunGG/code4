/*
 * @Author: your name
 * @Date: 2020-05-28 10:32:47
 * @LastEditTime: 2021-11-24 16:54:04
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /pxckzy/src/tools/request.js
 */
import axios from "axios";
import { baseUrl } from "../HTTP";

class HTTP {
  static get(url, data) {
    return this.request(url, data, "GET");
  }

  static post(url, data) {
    return this.request(url, data, "POST");
  }
  /**
   * Http request
   */
  static request(url, data, method) {
    return new Promise((resolve, reject) => {
      axios({
        method: method,
        // url: requestUrl,
        url: url,
        data: data || {},
      })
        .then((res) => {
          resolve(res);
        })
        .catch((err) => {
          reject(err);
        });
    });
  }

  static jpass(param) {
    return new Promise((resolve, reject) => {
      // lightAppJssdk.request.request({
      //     url: `https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do`,
      //     data: param,
      //     header: "",
      //     success: function (res) {
      //         console.log(res);
      //         param.sign = res.data.sign;
      //         lightAppJssdk.request.request({
      //             url: `https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do`,
      //             data: param,
      //             header: "",
      //             success: function (res) {
      //                 console.log(res);
      //                 resolve(res)
      //             },
      //             fail: function (err) {
      //                 reject(err)
      //             },
      //         });
      //     },
      //     fail: function (err) {
      //         reject(err)
      //     },
      // });
      $.ajax({
        type: "post",
        url:
          "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do",
        data: param,
        success: function(res) {
          param.sign = res.data.sign
        console.log(res);;
          $.ajax({
            type: "post",
            url:
              "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do",
            data: param,
          }).then((res) => {
            resolve(res);
          });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {},
      });
    });
  }
}
export { HTTP };
