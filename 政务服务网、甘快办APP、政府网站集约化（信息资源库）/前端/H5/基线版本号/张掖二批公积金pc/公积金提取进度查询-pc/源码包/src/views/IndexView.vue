<template>
  <div>
    <section class="query-container">
        <div class="wapper" v-for="(item,index) in infoData" :key='index'>
          <div class="wapper-title">提取原因：{{item.tqyynr || '暂无数据'}}</div>
          <div class="wapper-text">
            <div class="text-title">申请时间：{{item.sqsjstr || '暂无数据'}}</div>
            <div class="text-title">提取编号：{{item.tqbh || '暂无数据'}}</div>
            <div class="text-title">审批状态：{{getflag(item.spzt) || '暂无数据'}}</div>
          </div>
          <div class="wapper-btn">
             <button class="btn1" @click="getDetail(item.tqbh)">详情</button>
          <button  @click="deletelist(item.spzt,item.tqbh)">取消申请</button>
          </div>
        </div>
    </section>
  </div>
</template>
<script>
import IndexApi from "@/api/index";
import sm2 from "@/utils/sm2";
import tools, {
  name,
  IdNumberDesensitization,
  changDateFormate,
  formatDate,
  replaceAll,
} from "../tools/tool";
import getUserInfo from "@/api/userInfo";
import $ from "jquery";
export default {
  name: "IndexView",
  created() {
    this.getToken();
  },
  mounted() {},
  data() {
    return {
      show: true,
      userType1: false,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "",
        grzhyetm: "",
        dwmc: "",
      },
      basicInfo: {
        grzh: "",
        sqtqjexx: "",
        zjhm: "",
        gfrsfzh: "",
        sqrskhm: "",
        sqrskzh: "",
        tqlx: "11",
        xxly: "G",
        tqyydm: "01",
        sqrkhyhdm: "",
        sqrkhyhmc: "",
        gfrxm: "",
      },
      userInfo: {}, //用户信息
      token: "",
      authCode: "",
      userid:'',
      infoData:{}
    }
  },
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    changDateFormate,
    getToken() {
      let tokenData = null;
      $.ajax({
        type: "get",
        async: false,
        headers: {},
        url: "https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust",
        success: function (res) {
          tokenData = res.data;
          console.log(res.data,'111111');
          return tokenData;
        },
      });
      this.token = tokenData.token;
      this.$store.commit("token", tokenData.token);
      // this.authCode = sm2.decrypt3(tokenData.authCode);
      this.authCode = tokenData.authCode;
      this.$store.commit("authCode", tokenData.authCode);
      console.log(this.authCode, "this.authCode");
      this.getLoginUser();
    },
    getLoginUser() {
      getUserInfo().then((res) => {
        console.log(res);
        if (res.code === "200") {
          this.userid = sm2.decrypt2(res.params.userId);
          let userInfo = res.params;
          this.userInfo = userInfo;
          this.$store.commit("userInfo", userInfo);
          this.getInfo(this.userInfo);
        } else {
          location.href =
            "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
            encodeURIComponent(location.href);
        }
      });
    },
    getInfo() {
      const loading = this.$loading({
        fullscreen: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: this.userid,
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "grxxDetail.action",
      }
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          if (res.code == 200 && res.success && res.data.data) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            // console.log(res,'12345667778');
            if(res.data){
              this.accountInfo = res.data;
              this.basicInfo.grzh = res.data.grzh;
              this.basicInfo.zjhm = res.data.zjhm;
              this.$store.commit("accountInfo", res.data);
              this.getInfoList()
            }else{
              loading.close();
              this.$message.info(res.message || "未查询到相关信息");
              this.$router.push({path: "/no-results"})
            }
          } else {
            loading.close();
            if (res.code == 502 || res.code == 506) {
              this.$message.info("请进行开户操作");
              this.$router.push({path: "/no-results"})
            }
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    getInfoList() {
      const loading = this.$loading({
        fullscreen: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "gjjtqjl.action",
      }
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          if (res.code == 200 && res.success && res.data.data) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            if ((res.code == "01" || res.code == "00") && res.data) {
              loading.close()
              this.infoData = res.data
              if(this.infoData.length == 0){
                this.$router.push({path: "/no-results"})
              }
            } else {
             this.$message.info("未查询到相关信息");
            }
            loading.close();
          } else {
            loading.close();
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    getflag(num){
      let message = '暂无数据'
      if(num == '0'){
        message = '待审核'
      }
      if(num == '1'){
        message = '审核通过'
      }
      if(num == '2'){
        message = '审核未通过'
      }
      return message
    },
    getDetail(bh){
      const loading = this.$loading({
        fullscreen: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            xxly: "G",
            tqbh:bh
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "tqsqDetail.action",
      }
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          if (res.code == 200 && res.success && res.data.data) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            if ((res.code == "01" || res.code == "00") && res.data) {
              loading.close()
              sessionStorage.setItem('infoDetail',JSON.stringify(res.data))
              this.$router.push({path: "/results"})
            } else {
             this.$message.info("未查询到相关信息");
            }
            loading.close();
          } else {
            loading.close();
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    deletelist(num1,num2){
      if(num1 !== '0'){
        this.$message.info('办件已审核，无法取消！')
        return
      }
      const loading = this.$loading({
        fullscreen: true,
        text: "正在删除信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            xxly: "G",
            tqbh:num2
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "tqspDelete.action",
      }
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          if (res.code == 200 && res.success && res.data.data) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            if(res.code == '00'){
              this.$message.info("删除成功！");
              this.getInfoList()
              location.reload()
            }
          } else {
            this.$message.info("删除失败！");
          }
          loading.close();
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    }
  },
};
</script>

<style scoped lang="less">
.query-container{
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  .wapper{
    width: 47%;
    border-radius: 8px;
    border: 1px solid rgba(235, 235, 235, .8);
    box-shadow: 0 0 6px #00000012;
    margin-bottom: 30px;
    margin-left: 30px;
    .wapper-title{
      font-family: "PingFang SC Bold";
      font-weight: 700;
      font-size: 17px;
      line-height: 28px;
      text-align: left;
      color: #333;
      padding: 10px 15px 0;
    }
    .wapper-text{
      padding: 10px 15px 0;
      display: flex;
      flex-wrap: wrap;
      .text-title{
        font-family: "PingFang SC";
        font-weight: 400;
        font-size: 15px;
        line-height: 30px;
        text-align: left;
        color: #999;
        width: 50%;
      }
    }
    .wapper-btn{
      margin-top: 15px;
      height: 40px;
      border-top: 1px solid rgb(245, 245, 245);
      button{
        width: 50%;
        height: 100%;
        background: none;
        border: none;
        font-size: 15px;
        font-family: "PingFang SC";
        font-weight: 400;
        font-size: 15px;
        line-height: 28px;
        text-align: center;
        color: #3f87f3;
      }
      .btn1{
        border-right: 1px solid rgb(245, 245, 245);
      }
    }
  }
}
</style>
