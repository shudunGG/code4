import axios from "axios";
// const axios = require("axios")
function getUserInfo() {
    return new Promise((resolve, reject) => {
        axios({
            method: "POST",
            url: "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/getCurrentUserInfoNew1",
            async: true,
        }).then((res) => {
            console.log(res)
            if (res.data.success === true || res.data.code == "200") {
                console.log(123);
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
export default getUserInfo