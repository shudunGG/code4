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
      $.ajax({
        type: "post",
        url:
          "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do",
        data: param,
        contentType: false,
        //     // 告诉jQuery不要去设置Content-Type请求头
        processData: false,
        success: (res)=>{
          param.append("sign", res.data.sign);
          $.ajax({
            type: "post",
            url:
              "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do",
            data: param,
            contentType: false,
            //     // 告诉jQuery不要去设置Content-Type请求头
            processData: false,
          }).then((res) => {
            // console.log(res);
            resolve(res);
          });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          // that.msg = "接口异常";
          // that.open = true;
        },
      });
    });
  }
}
export { HTTP };
