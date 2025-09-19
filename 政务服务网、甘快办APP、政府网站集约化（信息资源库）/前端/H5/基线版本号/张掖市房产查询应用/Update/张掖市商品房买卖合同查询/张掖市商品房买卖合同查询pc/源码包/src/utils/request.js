import axios from "axios";
import { jpassConfig } from "./http";

/* eslint-disable */
import Hex from "../api/hex.js";
import SM3Digest from "../api/sm3-1.0.js";
const { httpRequestJpaas } = jpassConfig;
let time = new Date().getTime(); // 获取当前时间戳
const requestJpaas = {
  // Jpaas签名
  createSignJpaas: (params) => {
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
    // console.log("生成验签的内容:", inputtext);
    var dataBy = Hex.utf8StrToBytes(inputtext);
    var sm3 = new SM3Digest();
    sm3.update(dataBy, 0, dataBy.length);
    var sm3Hash = sm3.doFinal();
    var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length);
    // console.log("生成的验签:", sm3HashHex.toLowerCase());
    return sm3HashHex.toLowerCase();
  },
  getDataFromJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: "post",
        url: `${httpRequestJpaas}gateway`,
        data: params,
      })
        .then(function (data) {
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
        .catch(function (error) {
          rejected(error);
        });
    });
  },
  getData: function ({ interface_id, params = {} } = {}) {
    const baseParams = new URLSearchParams();
    baseParams.append("header", "{}");
    baseParams.append("version", "1.0");
    baseParams.append("charset", "UTF-8");
    baseParams.append("origin", "1");
    baseParams.append("timestamp", time);
    baseParams.append("interface_id", interface_id);
    baseParams.append("app_id", "qazlswsyytufisz");
    baseParams.append("biz_content", JSON.stringify(Object.assign(params)));
    // baseParams.append("biz_content",JSON.stringify(params) );
    return new Promise((resolved, rejected) => {
      const sign = requestJpaas.createSignJpaas(baseParams);
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
  getData1: function (
    { appid, interface_id, params = {}, columnId = "" } = {},
    header
  ) {
    const baseParams = new FormData();
    baseParams.append("version", "1.0");
    if (header) {
      baseParams.append("header", JSON.stringify(header));
    } else {
      baseParams.append("header", "{}");
    }
    baseParams.append("charset", "UTF-8");
    baseParams.append("origin", "1");
    baseParams.append("timestamp", time);
    baseParams.append("interface_id", interface_id);
    baseParams.append("app_id", appid);
    if (columnId) {
      baseParams.append(
        "biz_content",
        JSON.stringify(Object.assign({ columnId: columnId }, params))
      );
    } else {
      baseParams.append("biz_content", JSON.stringify(params));
    }
    return new Promise((resolved, rejected) => {
      const sign = requestJpaas.createSignJpaas(baseParams);
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
