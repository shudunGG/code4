import axios from "axios";

axios.defaults.headers['Content-Type'] = "multipart/form-data"

axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response.data;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default function getInfo(type,options,gatewayInfo,pageInfo){
    return new Promise((resolve)=>{
        const timestamp = new Date().valueOf()
        const obj = {
            type: type,
            list: options,
            pageNo: pageInfo.pageNo || 1,
            pageSize: pageInfo.pageSize || 5
        }
        const data = {
            app_id: gatewayInfo.appId,
            interface_id: gatewayInfo.interfaceId,
            version: "1.0",
            biz_content: JSON.stringify(obj),
            header: "{}",
            charset: "utf-8",
            timestamp: timestamp,
            origin: "0",
        }
        // response.setHeader("Access-Control-Allow-Origin", "*");

        function getSign(data){
        return axios({
            method: "POST",
            // headers: {'Access-Control-Allow-Origin': '*'},
            url: "https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign",
            data
        })
    }

    function getData(data,sign) {

        data.sign = sign
        return axios({
            method:"POST",
            url:"https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do",
            data,
        })
    }
    getSign(data).then((res)=>{
        if (res.success) {
            return getData(data,res.data.sign)
        }
    },).then((res)=>{
        resolve(res)
        })
    })
}
