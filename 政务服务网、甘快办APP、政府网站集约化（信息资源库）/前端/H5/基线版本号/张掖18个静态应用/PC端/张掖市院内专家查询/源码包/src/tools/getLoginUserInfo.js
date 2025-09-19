import $ from 'jquery'
function getLoginUserInfo () {
  let uinfo = null
  $.ajax({
    type: 'post',
    async: false,
    dataType: 'json',
    data: {},
    url: 'https://zwfw.gansu.gov.cn/szgs/member/wwLogin/getCurrentUserInfoNew1.do',
    success: function (result) {
      console.log(result)
      uinfo = result
    }
  })
  return uinfo
}
function getToken () {
  let tokenData = null
  $.ajax({
    type: 'get',
    async: false,
    headers: {},
    url: 'https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust',
    success: function (result) {
      console.log(result)
      tokenData = result
    }
  })
  return tokenData
}

// async function getToken(){
//     let tokenData = null
//     await axios({
//         method: 'get',
//         headers: {},
//         url: 'https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust'
//     })
//     .then((res)=>{
//         console.log(res)
//         tokenData = res.data
//     })
// }
export {
  getLoginUserInfo, getToken
}
