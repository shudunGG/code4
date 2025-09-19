/*
 * @Author: your name
 * @Date: 2020-05-31 11:27:15
 * @LastEditTime: 2020-05-31 11:50:57
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /health-code 3/src/service/search.js
 */
const search = {
  //获取省数据
  async search() {
    let params = {
      applyertype: "20",
      areacode: "370800",
      currentpage: 0,
      dictid: "",
      onlinehandle: 0,
      ouguid: "3f5f63ae-400e-4373-8966-1099719bf09a",
      pagesize: 10,
      searchcondition: "市",
      splb: ""
    }
    let obj = {
      params: JSON.stringify(params),
      token: "Epoint_WebSerivce_**##0601"
    };
    let param = {
      param: JSON.stringify(obj),
    };
    return new Promise((resolve, reject) => {
      lightAppJssdk.request.request({
        url: "http://jizwfw.sd.gov.cn/jnzwdt/rest/jnzwdtTask/getTaskListByCondition1?foreSessionClusterIntercept=true",
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
export default search;