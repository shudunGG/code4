<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div style="">张掖市中考成绩查询</div>
    </div>
    <div class="card-wrap" style="margin-top: 90px">
      <div class="card-title">
        <span
          style="
            color: #999;
            font-family: 'Microsoft YaHei';
            font-weight: 400;
            font-size: 12px;
          "
          >查询信息</span
        >
      </div>
      <div class="card-list">
        <div class="card-list-title">姓名</div>
        <div class="card-list-picker">
          <input
            v-model="name"
            placeholder="请输入姓名"
            @keyup="keyUp"
            maxlength="20"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">统编学号</div>
        <div class="card-list-picker">
          <input
            v-model="value"
            placeholder="请输入统编学号"
            @keyup="keyUp"
            maxlength="10"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">身份证号</div>
        <div class="card-list-picker">
          <input
            v-model="card"
            placeholder="请输入身份证号"
            @keyup="keyUp"
            maxlength="18"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">验证码</div>
        <div class="card-list-picker">
          <input
            v-model="yzm"
            placeholder="请输入验证码"
            @keyup="keyUp"
            maxlength="4"
          />
          <div class="code" @click="updateCode">
            <canvas id="canvas" width="100" height="38"></canvas>
          </div>
        </div>
      </div>
      <van-button class="queryButton" type="info" @click="queryList"
        >查询</van-button
      >
      <van-button class="resetButton" plain type="info" @click="resetInfo"
        >重置</van-button
      >
    </div>
    <div class="tips" style="margin: 10px; 0">
      可查询成绩为张掖市县区（即：甘州区、临泽县、高台县、山丹县、民乐县、肃南裕固族自治县）所有中考科目。为保护考生隐私，每个账号每日仅限5次查询。考生
      还可以通过“张掖市政务服务网https:/zwfw.gansu.gov.cn/zhangye/”、张掖市智慧教育云平台查询成绩。
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市教育局提供</div>
    </div>
  </div>
</template>
<script>
import tools from "../tools/tool.js";
import aes from "../tools/aes";
import { showToast, showLoadingToast } from "vant";
import { request } from "../tools/request.js";
import { toRefs, reactive, onMounted, nextTick } from "vue";
import { useRouter } from "vue-router";
import { createCode } from "../utils/checkCode";
import { requestJpaas, requestJpaas1 } from "../api/code";
import { getUserInfo } from "../tools/getUserInfo";
import { serveAPI } from "../api/serve";
export default {
  setup() {
    const state = reactive({
      token: "",
      name: "",
      value: "",
      card: "",
      yzm: "",
      userInfo: "",
      time: "",
      num: 0,
      toast: "",
      pageNum: "1",
      requestCount: 0,
      key: "",
      secretKey: "00e4bce5f10d8a3e96b7e62df284cda5078c8f263056a0f6305370a5e63438bd20",
      startTime: null,
      endTime: null,
      captcha: {
        uuid: "",
        sandCode: "",
      },
      codeFlag: false,
    });
    const router = useRouter();
    // 身份证号码校验
    function validateIDCard(idCard) {
      var idCardReg =
        /^[1-9]\d{5}(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dX]$/;
      if (!idCardReg.test(idCard)) {
        return false;
      }
      return true;
    }
    onMounted(async () => {
      await queryCode();
      nextTick(() => {
        createCode(state.captcha.sandCode);
      });
      await getUser();
      const data = {
        type: "fe7e0f2d42b6898bd321e172e9dbb0ce6247a0e5923079f8cebb249e4049693b",
      };
      // 获取密钥
      await request({
        app_id: "jsdnzjk",
        interface_id: "findSecretKey",
        biz_content: JSON.stringify(data),
      }).then((res) => {
        state.key = JSON.parse(res).data;
        console.log(state.key);
      });
      // 获取埋点
      let date = new Date();
      let y = date.getFullYear();
      let m = date.getMonth() + 1;
      let d = date.getDate();
      let h = date.getHours();
      let M = date.getMinutes();
      state.time = y + "-" + m + "-" + d;
      // 判断分钟 如果小于30 就传 00 如果大于30 就传 30
      if (M >= 30) {
        M = 30;
      } else {
        M = "00";
      }
      let year = y + "/" + m + "/" + d;
      let time = h + ":" + M;
      await getRequestCount();
      const params = {
        type: "zys", // 市州名称
        appName: "zyszkcx", // 应用名称
        time: year + " " + time, // 2022/6/23 10：00 时间入参格式
      };
      request({
        app_id: "sjmd",
        interface_id: "sjmdjk",
        biz_content: JSON.stringify(params),
      }).then((res) => {
        console.log(res, "埋点");
      });
    });

    // 获取验证码
    const queryCode = async () => {
      const result = await requestJpaas.getData();
      if (result.code === "200") {
        state.captcha.uuid = result.data.captcha.uuid;
        state.captcha.sandCode = result.data.captcha.capStr;
      } else {
        ElMessage.error("验证码获取失败，请重试或联系管理员");
      }
    };
    // 验证码获取更新
    const updateCode = async () => {
      state.captcha.uuid = "";
      state.captcha.sandCode = "";
      await queryCode();
      createCode(state.captcha.sandCode);
    };
    // 验证码校验yzm
    const verifyCode = async () => {
      try {
        let formDatas = new FormData();
        formDatas.append("uuid", state.captcha.uuid);
        formDatas.append("sandCode", state.yzm);
        state.captcha.sandCode = state.yzm;
        const result = await requestJpaas1.getData(state.captcha);
        console.log(result, "result");
        if (result.code == "200") {
          state.codeFlag = true;
        } else {
          state.codeFlag = false;
        }
      } catch (e) {
        ElMessage.error("验证码获取失败，请重试或联系管理员");
      }
    };
    const getUser = async () => {
      const res = await getUserInfo();
      if (res.data.userType === "10") {
        state.userInfo = res.data;
        console.log(state.userInfo, "USERinFO");
      } else {
        showToast("该业务仅限个人用户办理");
        setTimeout(() => {
          lightAppJssdk.navigation.close();
        }, 1000);
      }
    };
    // 获取查询次数
    const getRequestCount = async () => {
      let key = state.userInfo.userName + state.time + "_6204";
      console.log(state.userInfo.userName, "userName");
      const params = {
        key: aes.encrypt(key),
      };
      await serveAPI.jpass2("zkcx", "zkcxhcxcs", params).then((res) => {
        console.log(res, "次数");
        if (
          JSON.parse(res.data).code == "200" &&
          JSON.parse(res.data).success
        ) {
          state.requestCount = Number(JSON.parse(res.data).data);
        } else {
          state.requestCount = 0;
        }
        console.log(state.requestCount, "次数");
      });
    };
    const writeRequestCount = async () => {
      let keys = state.userInfo.userName + state.time + "_6204";
      state.requestCount += 1;
      const paramss = {
        key: aes.encrypt(keys),
        value: state.requestCount,
      };
      await serveAPI.jpass2("zkcx", "zkcxxrcxcs", paramss).then((res) => {
        console.log(res, "写入次数");
        getRequestCount();
      });
    };
    const queryList = async () => {
      await verifyCode();
      await updateCode();
      // state.startTime = new Date("2024-6-25 15:15:00").getTime();
      // state.endTime = new Date().getTime();
      // if (state.startTime > state.endTime) {
      //   showToast("未到查询时间，请于2024年6月25日15:15:00以后查询");
      //   return;
      // }
      if (state.name === "") {
        showToast("请输入姓名");
        return;
      }
      const regName = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
      if (!regName.test(state.name) || state.name.includes(" ")) {
        showToast("姓名格式不正确");
        return;
      }
      if (state.value === "") {
        showToast("请输入统编学号");
        return;
      }
      if (
        /[`~!@#$^&*=|{}':',\\\[\]\.<>\/?~！@#￥……&%*——|{}【】'；：''。，、？]/g.test(
          state.value
        ) ||
        state.value.includes(" ")
      ) {
        showToast("统编学号不能含有特殊字符");
        return;
      }
      const regStudentId = /^[0-9]{10}$/;
      if (!regStudentId.test(state.value)) {
        showToast("统编学号格式不正确");
        return;
      }

      if (state.card === "") {
        showToast("请输入身份证号");
        return;
      }
      if (state.card.length != 18) {
        showToast("身份证号长度为18个字符");
        return;
      }
      if (!validateIDCard(state.card) || state.card.includes(" ")) {
        showToast("身份证号格式不正确");
        return;
      }
      if (state.yzm == "") {
        showToast("请输入验证码");
        return;
      }
      if (!state.codeFlag) {
        showToast("验证码错误");
        return;
      }
      if (state.requestCount > 4) {
        showToast("今日查询次数已到上限");
        return;
      }
      state.toast = showLoadingToast({
        message: "加载中...",
        forbidClick: true,
        loadingType: "spinner",
        duration: 0,
      });
      console.log(state.key, "state.key");
      const param = {
        param: tools.sm2Encrypt(
          JSON.stringify({
            pageNum: state.pageNum,
            pageSize: "10",
            params: {
              xm: state.name,
              tbxh: state.value,
              id: state.card,
            },
          }),
          state.key
        ),
      };
      request({
        app_id: "jsdnzjk",
        interface_id: "findDataInfo",
        biz_content: JSON.stringify(param),
        header: JSON.stringify({
          "res-key": "qIygTRtz",
          "key-secret":
            "C6D55C747EB308ACE0BC791F4317D99CA8A99C744649AC740A08F2E444671FAD",
        }),
      })
        .then((res) => {
          let resStart;
          resStart = JSON.parse(res);
          console.log(res);
          res = JSON.parse(tools.sm2Decrypt(resStart.data, state.secretKey));
          if (resStart.message == "您当前查询的信息暂未对外开放！") {
            showToast("暂未到查询时间");
          } else {
            if (res.rows && res.total > 0) {
              writeRequestCount();
              sessionStorage.setItem("dataList", JSON.stringify(res.rows));
              setTimeout(function () {
                state.toast.close();
                router.push("/details");
              }, 500);
            } else {
              showToast("暂无数据");
            }
          }
        },
        ()=>{
          showToast("查询失败，请重试!");
        })
    };
    const resetInfo = async () => {
      state.name = "";
      state.value = "";
      state.card = "";
      state.yzm = "";
      updateCode();
    };
    return {
      ...toRefs(state),
      queryList,
      resetInfo,
      verifyCode,
      queryCode,
      updateCode,
      getUser,
    };
  },
};
</script>
<style lang="less" scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: white;
  padding: 0 16px;
  overflow: auto;
  .card-list-title {
    position: relative;
    padding-left: 11px;
  }
  .card-list-picker {
    padding-left: 11px;
  }
  .card-list-title:before {
    content: "*";
    color: red;
    position: absolute;
    left: 2px;
    bottom: -2px;
  }
  ::v-deep(.van-picker__toolbar) {
    border-bottom: 1px solid #e5e5e5;
  }
  ::v-deep(.van-picker__cancel) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker__confirm) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker-column__item) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }
  ::v-deep(.van-picker-column__item--selected) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }
  .top-bg {
    height: 140px;
    background: url("../assets/img/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    position: absolute;
    left: 0;
    right: 0;
    div {
      margin-top: 47px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }
  .card-wrap {
    border-radius: 4px;
    background: #fff;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
    padding: 0 12px;
    position: relative;
    z-index: 3;
    margin-top: 12.5px;
    .upload-wrap {
      display: flex;
      justify-content: space-between;
    }
    &.card-wrap-last {
      margin-bottom: 69px;
    }
    .card-title {
      height: 37.5px;
      display: flex;
      align-items: center;
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 18px;
      text-align: left;
      color: #333;
      border-bottom: 1px solid #e8e8e8;
      img {
        width: 14.3px;
        margin-right: 7px;
      }
    }
    .card-list {
      padding: 8px 0;
      border-bottom: 1px solid #e8e8e8;
      input {
        width: 100%;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #999;
      }
      input::-webkit-input-placeholder {
        color: #999;
      }
      input::-moz-input-placeholder {
        color: #999;
      }
      input::-ms-input-placeholder {
        color: #999;
      }
      .card-list-title {
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #333;
        margin-bottom: 8px;
      }
      .card-list-picker {
        display: flex;
        justify-content: space-between;
        align-items: center;
        > span {
          font-family: "Microsoft YaHei";
          font-weight: 400;
          font-size: 15px;
          text-align: left;
          color: #999;
        }
      }
    }
    .queryButton {
      width: 100%;
      margin-top: 30px;
      border-radius: 4px;
      font-size: 18px;
      background: #1677ff;
      border-color: #1677ff;
      color: #fff;
    }
    .resetButton {
      width: 100%;
      margin-top: 12px;
      margin-bottom: 18px;
      border-radius: 4px;
      font-size: 18px;
      border: 0.37px solid #bcbcbc;
      color: #333;
    }
  }
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/img/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    // bottom: 0;
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
}
</style>
