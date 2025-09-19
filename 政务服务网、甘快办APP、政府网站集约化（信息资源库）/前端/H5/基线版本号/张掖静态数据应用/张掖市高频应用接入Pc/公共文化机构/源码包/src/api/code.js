
import axios from "axios";
/* eslint-disable */
const httpRequestJpaas = "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/";
// 获取校验码
export const requestJpaas = {
  // Jpaas签名
  createSignJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: "post",
        url: `${httpRequestJpaas}createsign`,
        data: params,
      })
        .then(function (data) {
          if (data.data.success) {
            const {sign} = data.data.data;
            resolved(sign);
          } else {
            rejected(data);
          }
        })
        .catch(function (error) {
          rejected(error);
        });
    });
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
              const obj = JSON.parse(JSON.parse(data.data.data).data);
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
  getData: function ({columnId = "", params = {}} = {}) {
    const baseParams = new URLSearchParams();
    baseParams.append("header", "{}");
    baseParams.append("version", "1.0");
    baseParams.append("charset", "UTF-8");
    baseParams.append("origin", "1");
    baseParams.append("timestamp", new Date().getTime());
    baseParams.append("interface_id", "showVerifyCode");
    baseParams.append("app_id", "yzm");
    baseParams.append("biz_content", JSON.stringify(Object.assign({})));
    return new Promise((resolve, reject) => {
      requestJpaas
        .createSignJpaas(baseParams)
        .then((sign) => {
          baseParams.append("sign", sign);
          return requestJpaas.getDataFromJpaas(baseParams);
        })
        .then((response) => {
          try {
            var obj = JSON.parse(response);
            if (typeof obj === "object" && obj) {
              resolve(JSON.parse(response));
            } else {
              resolve(response);
            }
          } catch (e) {
            resolve(response);
          }
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
};

// 验证校验码
export const requestJpaas1 = {
  // Jpaas签名
  createSignJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: "post",
        url: `${httpRequestJpaas}createsign`,
        data: params,
      })
        .then(function (data) {
          if (data.data.success) {
            const {sign} = data.data.data;
            resolved(sign);
          } else {
            rejected(data);
          }
        })
        .catch(function (error) {
          rejected(error);
        });
    });
  },
  getDataFromJpaas: function (params = {}) {
    return new Promise((resolved, rejected) => {
      axios({
        method: "post",
        url: `${httpRequestJpaas}gateway`,
        data: params,
      })
        .then(function (data) {
          // if (data.data.code == 200 && data.data.success == true) {
          const objData = JSON.parse(data.data.data);
          //     if (objData.code == 200) {
          //         resolved(objData)
          //     } else {
          //         const obj = JSON.parse(JSON.parse(data.data.data).data)
          //         rejected(obj)
          //     }
          // } else {
          //     rejected(data)
          // }
          resolved(objData);
        })
        .catch(function (error) {
          rejected(error);
        });
    });
  },
  getData: function (data) {
    const baseParams = new URLSearchParams();
    baseParams.append("header", "{}");
    baseParams.append("version", "1.0");
    baseParams.append("charset", "UTF-8");
    baseParams.append("origin", "1");
    baseParams.append("timestamp", new Date().getTime());
    baseParams.append("interface_id", "cheackCaptcha");
    baseParams.append("app_id", "yzm");
    baseParams.append("biz_content", JSON.stringify(Object.assign(data)));
    return new Promise((resolve, reject) => {
      requestJpaas1
        .createSignJpaas(baseParams)
        .then((sign) => {
          baseParams.append("sign", sign);
          return requestJpaas1.getDataFromJpaas(baseParams);
        })
        .then((response) => {
          try {
            var obj = JSON.parse(response);
            if (typeof obj === "object" && obj) {
              resolve(JSON.parse(response));
            } else {
              resolve(response);
            }
          } catch (e) {
            resolve(response);
          }
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
};
