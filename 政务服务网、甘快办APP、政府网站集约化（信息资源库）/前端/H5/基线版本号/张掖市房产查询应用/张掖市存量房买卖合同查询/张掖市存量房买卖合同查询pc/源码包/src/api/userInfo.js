import axios from "axios";
function getUserInfo () {
    return new Promise((resolve, reject) => {
        axios({
            method: "POST",
            url: "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/getCurrentUserInfoNew1",
            async: true,
        }).then((res) => {
            console.log(res)
            if (res.data.success === true || res.data.code == "200") {
                resolve(res.data)
            } else {
                location.href =
                    "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
                    encodeURIComponent(location.href);
            }
        }).catch((err) => {
            reject(err)
        })
    })
}
function getToken () {
    return new Promise((resolve, reject) => {
        axios({
            method: "GET",
            async: false,
            headers: {},
            url: "https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust",
        }).then((res) => {
            if (res.data.code == 200 && res.data.success) {
                resolve(res.data)
            } else {
                reject(res.data)
            }
        }).catch((err) => {
            reject(err)
        });
    });
}
export { getUserInfo, getToken }