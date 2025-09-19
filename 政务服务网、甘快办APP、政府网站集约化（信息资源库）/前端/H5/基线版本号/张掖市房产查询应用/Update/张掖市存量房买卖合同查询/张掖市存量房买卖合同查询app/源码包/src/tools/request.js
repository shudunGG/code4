/* eslint-disable camelcase */
/* eslint-disable no-useless-catch */
import { axios } from "./axios";
import { baseUrl, app_id } from "./config";
import CryptoJS from "crypto-js";
import { sm4 } from "sm-crypto";
import Hex from "./hex.js";
import SM3Digest from "./sm3-1.0.js";
let time = new Date().getTime(); // 获取当前时间戳
// import { Buffer } from 'buffer'
// 获取验签
const createSign = async (params) => {
  var inputtext =
    "app_id=" +
    params.app_id +
    "&biz_content=" +
    params.biz_content +
    "&charset=" +
    "UTF-8" +
    "&interface_id=" +
    params.interface_id +
    "&origin=" +
    "1" +
    "&timestamp=" +
    time +
    "&version=" +
    params.version;
  console.log("生成验签的内容:", inputtext);
  var dataBy = Hex.utf8StrToBytes(inputtext);
  var sm3 = new SM3Digest();
  sm3.update(dataBy, 0, dataBy.length);
  var sm3Hash = sm3.doFinal();
  var sm3HashHex = Hex.encode(sm3Hash, 0, sm3Hash.length);
  console.log("生成的验签:", sm3HashHex.toLowerCase());
  return sm3HashHex.toLowerCase();
};
// const gateWay = async (data = {}) => {
//   console.log(data,"dataaaaaaaaaaaaaaaa");
//   console.log(data.get("version"),"来维护");
//   const res = await axios({
//     method: "post",
//     url: baseUrl + "gateway",
//     data,
//     // headers: {
//     //   "content-type": "application/x-www-form-urlencoded",
//     // },
//   });
//   console.log(res, "res0.0.0.0.0.0.0.0.0.");
//   if (res.data.success) {
//     const { data } = res.data;
//     return data;
//   }
// };
let httpRequestJpaas = "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/";
const getDataFromJpaas = async (params) => {
  return new Promise((resolved, rejected) => {
    axios({
      method: "post",
      url: `${httpRequestJpaas}gateway.do`,
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
};
/**
 * 网关请求
 * @param {*} loading 是否展示loading效果。默认开启
 * @param {*} app_id 应用id。
 * @param {*} interface_id 接口id
 * @param {Object} params 业务参数
 * @param {Object} header 业务接口的请求头
 * @returns
 */
const request = async (params) => {
  try {
    const baseParams = {
      version: "1.0",
      charset: "UTF-8",
      origin: "1",
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id ? params.app_id : app_id,
      biz_content: params.biz_content,
      header: params.header || "{}",
    };
    const sign = await createSign(baseParams);
    baseParams.sign = sign;
    const result = await gateWay(baseParams);
    return result;
  } catch (error) {
    throw error;
  }
};

// sm2加密网关请求

const requestWithSM2 = async (app_id, interface_id, params, header) => {
  const baseParams = {
    version: "1.0",
    charset: "UTF-8",
    origin: "1",
    timestamp: time,
    interface_id: interface_id,
    app_id: app_id,
    biz_content: JSON.stringify(params),
    header: JSON.stringify(header) || "{}",
  };
  try {
    const sign = await createSign(baseParams);
    baseParams.sign = sign;
    const response = await getDataFromJpaas(baseParams);
    setTimeout(() => {
      time = new Date().getTime(); // 1秒后重新赋值
    }, 1000);
    try {
      var obj = JSON.parse(response);
      if (typeof obj === "object" && obj) {
        return JSON.parse(response);
      } else {
        return response;
      }
    } catch (e) {
      return response;
    }
  } catch (error) {
    throw error;
  }

  // try {
  //   const baseParams = {
  //     version: "1.0",
  //     charset: "UTF-8",
  //     origin: "1",
  //     timestamp: new Date().getTime(),
  //     interface_id,
  //     app_id,
  //     biz_content: JSON.stringify(params),
  //     header: JSON.stringify(header) || "{}",
  //   };
  //   const sign = await createSign(baseParams);
  //   baseParams.sign = sign;
  //   const result = await getDataFromJpaas(baseParams);
  //   return JSON.parse(result);
  // } catch (error) {
  //   throw error;
  // }
};

// sm4加密网关请求
const requestWithSM4 = async (params, needencry) => {
  if (needencry && needencry.need && params.biz_content) {
    params.biz_content = sm4.encrypt(params.biz_content, needencry.key);
  }
  try {
    const baseParams = {
      version: "1.0",
      charset: "UTF-8",
      origin: "1",
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id || app_id,
      biz_content: params.biz_content,
      header: params.header || "{}",
    };
    const sign = await createSign(baseParams);
    baseParams.sign = sign;
    const result = await gateWay(baseParams);
    return result;
  } catch (error) {
    throw error;
  }
};

// aes加密网关请求
const requestWithAES = async (params, needencry) => {
  if (needencry && needencry.need && params.biz_content) {
    const key = CryptoJS.enc.Utf8.parse(needencry.key);
    const srcs = CryptoJS.enc.Utf8.parse(JSON.stringify(params.biz_content));
    const encrypted = CryptoJS.AES.encrypt(srcs, key, {
      iv: key,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7,
    });
    params.biz_content = encrypted.ciphertext.toString();
  }
  try {
    const baseParams = {
      version: "1.0",
      charset: "UTF-8",
      origin: "1",
      timestamp: new Date().getTime(),
      interface_id: params.interface_id,
      app_id: params.app_id || app_id,
      biz_content: params.biz_content,
      header: params.header || "{}",
    };
    const sign = await createSign(baseParams);
    baseParams.sign = sign;
    const result = await gateWay(baseParams);
    return result;
  } catch (error) {
    throw error;
  }
};

// 文件上传用
function Jpaas(param) {
  return new Promise((resolve) => {
    const timestamp = new Date().valueOf();
    param.timestamp = timestamp;
    const formData = new FormData();
    formData.append("app_id", app_id);
    formData.append("interface_id", param.interface_id);
    formData.append("biz_content", param.biz_content);
    formData.append("version", "1.0");
    formData.append("charset", "UTF-8");
    formData.append("origin", "1");
    formData.append("header", param.header || "{}");
    formData.append("timestamp", timestamp);
    formData.append("file", param.file);
    axios({
      method: "post",
      url: baseUrl + "createsign.do",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }).then((res) => {
      if (res.data.success) {
        // param.sign = res.data.data.sign;
        formData.append("sign", res.data.data.sign);
        axios({
          method: "post",
          url: baseUrl + "gateway.do",
          data: formData,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
          .then((res) => {
            if (res.data.success) {
              const resp = JSON.parse(res.data.data);
              if (resp.success) {
                resolve(resp);
              } else {
                resolve({});
              }
            } else {
              resolve({});
            }
          })
          .catch(() => {});
      } else {
        resolve({});
      }
    });
  });
}

export { Jpaas, request, requestWithSM2, requestWithSM4, requestWithAES };
