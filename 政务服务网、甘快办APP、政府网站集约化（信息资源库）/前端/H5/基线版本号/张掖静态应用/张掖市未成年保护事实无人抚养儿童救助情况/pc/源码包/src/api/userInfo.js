import axios from "axios";
// const axios = require("axios")
export default function () {
    return new Promise((resolve, reject)=>{
        axios({
            method: "POST",
            url: "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/getCurrentUserInfoNew",
            async: true,
        }).then((res)=>{
            console.log(res)
            if (res.data.success === true || res.data.code!=="200") {
                resolve(res.data)
            } else {
                reject("未登录")
            }
        }).catch((err)=>{
            reject(err)
            console.log(err)
        })
    })
}


