import axios from "axios";
import { jpassConfig } from "./http";
import { Toast } from "vant";
/* eslint-disable */
import Hex from "../api/hex.js";
import SM3Digest from "../api/sm3-1.0.js";
let time = new Date().getTime(); // 获取当前时间戳
const { httpRequestJpaas } = jpassConfig;
const header = JSON.stringify({
  client_key: 1731373215,
});

const requestJpaas = {
  // Jpaas签名
  createSign: (params) => {
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
    console.log("生成验签的内容:", inputtext);
    var dataBy = Hex.utf8StrToBytes(inputtext);
    var sm3 = new SM3Digest();
    sm3.update(dataBy, 0, dataBy.length);
    var sm3Hash = sm3.doFinal();
    var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length);
    console.log("生成的验签:", sm3HashHex.toLowerCase());
    return sm3HashHex.toLowerCase();
  },
  getDataFromJpaas: function(params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: "post",
        url: `${httpRequestJpaas}gateway.do`,
        data: params,
      })
        .then(function(data) {
          if (data.data.code == 200 && data.data.success == true) {
            const objData = JSON.parse(data.data.data);
            if (objData.code == 200) {
              resolved(objData);
            } else {
              const obj = JSON.parse(data.data.data);
              resolved(objData);
              rejected(obj);
            }
          } else {
            rejected(data);
          }
        })
        .catch(function(error) {
          rejected(error);
        });
    });
  },
  getData: function(params) {
    const baseParams = new URLSearchParams();
    baseParams.append("version", "1.0");
    baseParams.append("charset", "UTF-8");
    baseParams.append("origin", "1");
    baseParams.append("header", params.header || header);
    baseParams.append("timestamp", time);
    baseParams.append("interface_id", params.interface_id);
    baseParams.append("app_id", params.app_id ? params.app_id : app_id);
    baseParams.append("biz_content", params.biz_content);
    return new Promise((resolved, rejected) => {
      const sign = requestJpaas.createSign(baseParams);
      baseParams.append("sign", sign);
      const response = requestJpaas.getDataFromJpaas(baseParams);
      setTimeout(() => {
        time = new Date().getTime(); // 1秒后重新赋值
      }, 1000);
      try {
        var obj = JSON.parse(response);
        if (typeof obj === "object" && obj) {
          resolved(JSON.parse(response));
        } else {
          resolved(response);
        }
      } catch (e) {
        resolved(response);
      }
    });
  },
};

export default requestJpaas;
