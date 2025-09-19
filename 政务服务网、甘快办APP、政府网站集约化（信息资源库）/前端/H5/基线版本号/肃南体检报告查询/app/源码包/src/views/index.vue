<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div>肃南裕固族自治县体检报告查询</div>
    </div>
    <div class="card-wrap" style="margin-top: 110px">
      <div class="card-title">
        <span
          style="
            color: #999;
            font-family: 'Microsoft YaHei';
            font-weight: 400;
            font-size: 12px;
          "
          >填写信息</span
        >
      </div>
      <div class="card-list">
        <div class="card-list-title">
          <span style="color: red; padding-right: 3px">*</span>身份证号码
        </div>
        <div class="card-list-picker">
          <!--          <van-field v-model="cartNo" placeholder="身份证号码" @blur="ruleHosipatil"/>-->
          <van-field
            v-model="jmCartNo"
            placeholder="身份证号码"
            @blur="ruleHosipatil"
            disabled
          />
        </div>
      </div>
      <div
        class="card-list"
        style="border-top: 0.2px solid #e8e8e8"
        v-show="showPhone"
      >
        <div class="card-list-title">
          <span style="color: red; padding-right: 3px">*</span>电话号码
        </div>
        <div class="card-list-picker">
          <!--          <van-field v-model="phone" placeholder="电话号码" @blur="ruleHosipatil"/>-->
          <van-field
            v-model="jmPhone"
            placeholder="电话号码"
            @blur="ruleHosipatil"
            disabled
          />
        </div>
      </div>
    </div>
    <van-button class="queryButton" type="info" @click="queryList"
      >查询</van-button
    >
    <div class="footer">
      <div class="footer-content">本服务由肃南裕固族自治县提供</div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { serverApi } from "../api/serverApi.js";
import { IdNumberDesensitization, TmPhone } from "../utils/desensitization";
import { closeToast, showLoadingToast, showToast } from "vant";
import { useRouter } from "vue-router";
import { useStore } from "../store/index.js";
import sm2 from "../utils/tool";
import { getUserInfo } from "../api/getUserInfo";

const router = useRouter();
const store = useStore();

// const cartNo = ref('622201198301102723')
// const token = ref('622224198910196512')
// const authCode = ref('19993688858')
const showPhone = ref(true);
const cartNo = ref("");
const token = ref("");
const authCode = ref("");
const hospital = ref(""); // 医院
const queryFlage = ref("");
const phone = ref();
const hospitalColumns = ref([
  // 医院列表
  { text: "张掖市中医医院", value: "zyy" },
  { text: "河西学院附属张掖人民医院", value: "hxfy" },
  { text: "张掖市第二人民医院", value: "dermyy" },
]);
const showPicker = ref(false);
const publicKey =
  "04e18f52110e0e1bc1b1dbe10b3d20607b436fd6952b3391c27ab0fcad690f2ef07a115a5e18012e63fd7a46e94b4d9ff9d71e78a4ea9f1b376b1954b865f8b59e";
const privateKey =
  "00ad1ecc86390c0439d3235d1b1a19a19297f365a0d8d436710800f565f61675d2";
const userCertNum = ref("");
const userMobile = ref("");

const jmCartNo = ref("");
const jmPhone = ref("");

onMounted(() => {
  queryUserInfo();
});

// 获取用户信息
const queryUserInfo = () => {
  showLoadingToast({
    message: "加载中...",
    forbidClick: true,
  });

  getUserInfo().then((res) => {
    if (res.data == "{}" || !res.data) {
      lightAppJssdk.navigation.close();
    } else {
      closeToast();
      const userInfo = res.data;
      if (userInfo.userType == 10) {
        // cartNo.value = IdNumberDesensitization(userInfo.userCertNum)

        jmCartNo.value = IdNumberDesensitization(userInfo.userCertNum);
        jmPhone.value = TmPhone(userInfo.userMobile);

        cartNo.value = userInfo.userCertNum;
        phone.value = userInfo.userMobile;

        userCertNum.value = userInfo.userCertNum;
        userMobile.value = userInfo.userMobile;

        token.value = userInfo.tokenNo;
        authCode.value = userInfo.tokenNo;
        console.log("---", userInfo);
      } else {
        showToast({
          confirmButtonColor: "#3f87f3",
          message: "该业务仅限个人用户办理",
        });
      }
    }
  });
};

const ruleHosipatil = () => {
  if (!hospital.value) {
    showToast({
      message: "请先选择医院",
    });
    cartNo.value = "";
    phone.value = "";
  }
};

// 查询
const queryList = async () => {
  const specialKey =
    /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
  const specialKeyPhone = /^1[3456789]\d{9}$/;
  if (cartNo.value === "") {
    showToast({
      message: "证件号码不能为空",
      duration: 2000,
    });
    return;
  } else {
    if (!specialKey.test(cartNo.value)) {
      showToast({
        message: "证件号码格式有误",
        duration: 2000,
      });
      return;
    }
  }

  if (phone.value === "") {
    showToast({
      message: "电话号码不能为空",
      duration: 2000,
    });
    return;
  } else {
    if (!specialKeyPhone.test(phone.value)) {
      showToast({
        message: "电话号码格式有误",
        duration: 2000,
      });
      return;
    }
  }

  // 重置pinia数据
  store.$reset();
  showLoadingToast({
    message: "加载中...",
    forbidClick: true,
  });
  console.log("---", queryFlage.value);
  await dermyyQuery();
};
// 张掖市第二人民医院
const dermyyQuery = async () => {
  console.log('cartNo.value', token.value);
  
  const param = {
    // 622201198301102723
    // sfzh: '62220119881112632X'
    // sfzh: '150205198302061338'
    tokenNo: sm2.encryptSM2(token.value, publicKey),
  };
  try {
    const result = await serverApi.queryList(
      "zyyyjkrzmpgab",
      "zysnPhysical",
      param
    );
    console.log('data', sm2.decryptSM2(result.data, privateKey));
    const data = JSON.parse(sm2.decryptSM2(result.data, privateKey))
    
    if (result.code === "200" && result.success && data?.reports?.length > 0) {
      closeToast();
      console.log("-----", result);
      data.reports = data.reports.sort((a, b) => {
        return new Date(b.reportTime) - new Date(a.reportTime);
      });
      console.log(data);
      
      store.changeListDetail(data);
      router.push("/deymDetail");
    } else {
      showToast({
        message: "暂无报告",
        duration: 2000,
      });
    }
  } catch (e) {
    console.log(e);
    
    showToast({
      message: "网络异常，请稍后再试",
      duration: 2000,
    });
  }
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
  // picker默认样式修改
  /deep/ .van-picker__toolbar {
    border-bottom: 1px solid #e5e5e5;
  }

  /deep/ .van-picker__cancel {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker__confirm {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker-column__item {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }

  /deep/ .van-picker-column__item--selected {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }

  /deep/ .van-cell {
    padding: 0;
  }

  .top-bg {
    height: 140px;
    background: url(../assets/images/bg.png);
    background-size: 100% 100%;
    overflow: hidden;
    position: absolute;
    left: 0;
    right: 0;

    div {
      margin-top: 33.5px;
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
      border-bottom: 0.5px solid #e8e8e8;

      img {
        width: 14.3px;
        margin-right: 7px;
      }
    }

    .card-list {
      padding: 12px 0;

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
        font-size: 14px;
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
  }

  .queryButton {
    width: 100%;
    margin-top: 20px;
    border-radius: 4px;
    font-size: 18px;
    background: #1677ff;
    border-color: #1677ff;
    color: #ffffff;
  }

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
}
</style>
