import md5 from "js-md5";
const servicesObj = {
    //获取省数据
    async getProvice(regionCode) {
        let resultTime = new Date().getTime();
        let sign = md5("jkm" + resultTime);
        let obj = {
            param: {
                from: "2",
                key: "3d62a19e42dc4189aa18fcf722cdd0c8",
                requestTime: resultTime,
                sign: sign,
                regionCode: regionCode,
            },
            from: "2",
            key: "3d62a19e42dc4189aa18fcf722cdd0c8",
            appid: "jkm",
            requestTime: resultTime,
            sign: sign,
            regionCode: regionCode,
        };
        let param = {
            param: JSON.stringify(obj),
        };
        return new Promise((resolve, reject) => {
            lightAppJssdk.request.request({
                url: "http://60.211.225.66:8050/jimp/link.do",
                data: param,
                methods: "POST",
                header: "",
                dataType: "json", //返回数据格式，默认json
                async: false, //同步设置
                success: (data) => {
                    resolve(data);
                },
                fail: (error) => {
                    reject(error)
                },
            });
        })
    },
}
export default servicesObj;