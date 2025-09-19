<template>
  <div class="page">
    <div v-if="infoData && infoData.length " class="container-wrap">
      <!-- 主内容 -->
      <div class="concent" v-for="(item,index) in infoData" :key='index'>
        <div class="title">提取原因：{{item.tqyynr || '暂无数据'}}</div>
        <div class="text">
          <div class="text-content">申请时间：{{item.sqsjstr || '暂无数据'}}</div>
          <div class="text-content">提取编号：{{item.tqbh || '暂无数据'}}</div>
          <div class="text-content">审批状态：{{getflag(item.spzt) || '暂无数据'}}</div>
        </div>
        <div class="btnwapper">
          <button class="btn1" @click="getDetail(item.tqbh)">详情</button>
          <button  @click="deletelist(item.spzt,item.tqbh)">取消申请</button>
        </div>
      </div>
      <!-- 底部 -->
      <div class="footer">
        <div class="footer-content">本服务由张掖市住房公积金管理中心提供</div>
      </div>
    </div>
    <div v-if="infoData.length == 0" style="width:80%;height:100vh;margin:0 auto">
      <img style="width:100%;height:300px;" src="../assets/images/qs.png" alt="">
      <p class="nodata">暂无查询数据</p>
    </div>
  </div>
</template>
<script>
import { getUserInfo } from "../api/getUserInfo";
import tools, {
  name,
  IdNumberDesensitization,
  formatDate,
  replaceAll,
} from "../tools/tool";
import { serveAPI } from "../api/serve";
import { Toast } from "vant";

export default {
  name: "index",
  data() {
    return {
      show: true,
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
      publicKey1:
        "044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e",
      privateKey1:
        "3fce91c669c4769381384edcd8d63515362f222e9025f7f8b52f4f7ef2c38c69",
      infoData :{}
    };
  },
  created() {
    this.$store.commit("userInfo", {}); //第一次进入先清空一下缓存
    this.getUserInfo();
  },
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    // 获取用户信息
    getUserInfo() {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });
      getUserInfo().then((res) => {
        console.log(res);
        if (res.data == "{}" || !res.data) {
          this.$Dialog
            .alert({
              message: "当前未登录，请先登录",
            })
            .then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close();
            });
        } else {
          let userInfo = res.data;
          this.$store.commit("userInfo", userInfo);
          console.log(userInfo, "用户信息");
          this.userInfo = userInfo;
          this.getInfo(this.userInfo);
        }
      });
    },
    getInfo(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: userInfo.userCertNum,
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "grxxDetail.action",
      };
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "123");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          console.log(res, "公积金信息");
          if (res.code == "00" && res.data) {
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.basicInfo.grzh = res.data.grzh;
            this.basicInfo.zjhm = res.data.zjhm;
            this.$store.commit("accountInfo", res.data);
            this.getInfolist()
            this.$toast.clear();
          } else {
            Toast({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          Toast({
            message: "网络异常，请稍后重试！",
          });
        });
    },
    getInfolist() {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "gjjtqjl.action",
      };
      serveAPI.queryList("tygjj", "commonGjj", param).then((res) => {
        res = JSON.parse(tools.decryptSM2(res.data.data, this.privateKey1)).result;
        console.log(res,'mnv');
        if ((res.code == "01" || res.code == "00") && res.data) {
          this.$toast.clear();
          this.infoData = res.data
        } else {
          Toast({
            message: res.msg || "未查询到相关信息",
          });
        }
      });
      this.$store.commit("basicInfo", this.basicInfo);
      this.show = false
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
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            xxly: "G",
            tqbh:bh
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "tqsqDetail.action",
      };
      serveAPI.queryList("tygjj", "commonGjj", param).then((res) => {
        console.log(res, "列表详情");
        res = JSON.parse(tools.decryptSM2(res.data.data, this.privateKey1)).result;
        if ((res.code == "01" || res.code == "00") && res.data) {
          this.$toast.clear();
          sessionStorage.setItem('infoDetail',JSON.stringify(res.data))
          this.$router.push({path: "/form"})
        } else {
          Toast({
            message: res.msg || "未查询到相关信息",
          });
        }
      })
    },
    deletelist(num1,num2){
      if(num1 !== '0'){
        Toast({ message: "办件已审核，无法取消！"})
        return
      }
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            grzh: this.basicInfo.grzh,
            tqbh:num2,
            xxly:'G'
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "tqspDelete.action",
      };
      serveAPI.queryList("tygjj", "commonGjj", param).then((res) => {
        res = JSON.parse(tools.decryptSM2(res.data.data, this.privateKey1)).result;
        if (res.code == "00") {
          this.$toast.clear()
          Toast("删除成功！")
          this.getInfolist()
          location.reload()
        } else {
          Toast({
            message: res.msg || "删除失败！",
          });
        }
      })
    }
  },
};
</script>

<style scoped lang="less">
.page {
  width: 100%;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  .container-wrap {
    width: 100%;
    .concent{
      box-sizing: border-box;
      width: 95%;
      margin: 20px auto;
      border-radius: 8px;
      background: #fff;
      box-shadow: 0 0 12px #00000012;
      padding: 5px 10px 0px;
      .title{
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 15px;
        line-height: 28px;
        text-align: left;
        color: #333;
      }
      .text-content{
        margin: 5px 0;
        font-family: "PingFang SC";
        font-weight: 400;
        font-size: 13px;
        line-height: 26px;
        text-align: left;
        color: #999;
      }
      .btnwapper{
        width: 100%;
        height: 45px;
        display: flex;
        justify-content: space-around;
        border-top: 1px solid rgb(244, 244, 244);
        margin-top: 10px;
        button{
          width: 50%;
          height: 100%;
          font-family: "PingFang SC";
          font-weight: 400;
          font-size: 15px;
          line-height: 28px;
          text-align: center;
          color: #3f87f3;
        }
        .btn1{
          border-right: 1px solid rgb(244, 244, 244);
        }
      }
    }
  }
  // 底部
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;
    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
  /deep/ .van-cell {
    padding: 8px 15px 12px;
    font-size: 13px;
  }
  /deep/ .van-field__control {
    -webkit-text-fill-color: #999;
  }
  /deep/ .van-field__control:disabled {
    -webkit-text-fill-color: #999;
  }
}
.nodata{
  font-family: "PingFang SC Bold";
  font-weight: 600;
  font-size: 20px;
  line-height: 28px;
  text-align: center;
  color: #333;
  margin-top: 30px;
}
</style>
