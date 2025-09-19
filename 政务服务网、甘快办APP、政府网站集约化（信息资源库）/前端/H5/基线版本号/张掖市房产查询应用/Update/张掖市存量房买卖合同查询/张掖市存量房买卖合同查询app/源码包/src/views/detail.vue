<template>
  <div class="app">
    <div class="top-bg">
      <div>张掖市存量房买卖合同查询</div>
    </div>
    <div>
      <div
        style="
          background: linear-gradient(180deg, #cfe2ff 0%, #fff 100%);
          padding-top: 12px;
        "
      ></div>
      <div class="card-content">
        <div class="card">
          <div class="card-left">合同编号</div>
          <div class="card-right">
            {{ dataObj.CLFHTBH || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">卖方姓名</div>
          <div class="card-right">
            {{ desensitizedCommon(dataObj.SELLERXM,'姓名') || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">卖方证件号码</div>
          <div class="card-right">
            {{ hyposensitization(dataObj.SELLERZJHM) || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">卖方联系方式</div>
          <div class="card-right">
            {{ phone(dataObj.SELLERLXFS) || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">卖方联系地址</div>
          <div class="card-right">
            {{ desensitizedCommon(dataObj.SELLERLXDZ) || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">房屋性质</div>
          <div class="card-right">
            {{ dataObj.FWXZ || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">房屋坐落</div>
          <div class="card-right">
            {{ dataObj.FWZL || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">房屋面积（㎡）</div>
          <div class="card-right">
            {{ dataObj.FWMJ || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">成交价格（元）</div>
          <div class="card-right">
            {{ dataObj.CJJG || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">抵押情况</div>
          <div class="card-right">
            {{ dataObj.DYQK=='1'?`未抵押`:`已抵押` || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">抵押权人</div>
          <div class="card-right">
            {{ dataObj.DYQR || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">抵押期限</div>
          <div class="card-right">
            {{ dataObj.DYQX || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">租赁情况</div>
          <div class="card-right">
            {{ dataObj.ZLQK=='1'?`未出租`:`已出租` || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">房屋定金</div>
          <div class="card-right">
            {{ dataObj.FWDJ || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">交房日期</div>
          <div class="card-right">
            {{ dataObj.JFRQ || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">购房人姓名</div>
          <div class="card-right">
            {{ hyposensitization(dataObj.MSRLIST[0].MSRXM,'姓名') || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">购房人证件类型</div>
          <div class="card-right">
            {{ dataObj.MSRLIST[0].MSRZJLX || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">购房人证件号码</div>
          <div class="card-right">
            {{ hyposensitization(dataObj.MSRLIST[0].MSRZJHM) || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">共有份额</div>
          <div class="card-right">
            {{ dataObj.MSRLIST[0].GYFE || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">联系方式</div>
          <div class="card-right">
            {{ phone(dataObj.MSRLIST[0].MSRLXFS) || "暂无数据" }}
          </div>
        </div>
        <div class="card">
          <div class="card-left">联系地址</div>
          <div class="card-right">
            {{ desensitizedCommon(dataObj.MSRLIST[0].MSRLXDZ) || "暂无数据" }}
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="footer" v-if="shouResult"> -->
    <div style="margin: 0 12px; box-sizing: border-box">
      <van-button type="primary" block @click="$router.push('/')">返回首页</van-button>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
import { useStore } from "../store/index.js";
import { toRefs, reactive } from "vue";
import { name, desensitizedCommon ,phone} from "../tools/tool.js";
export default {
  name: "indexName",
  setup() {
    const obj = reactive({
      value: "",
      dataObj: {},
      bizOrderNo: "", // 交易流水号
      // userInfo: {
      //   userId: null,
      //   tokenNo: "",
      // },
      formDataParams: {},
      shouResult: false,
      shouResult1: false,
      text: "",
      cashierUrl: "",
      key: "042a455299593c3a7ac95171d5147273238aa4d144bc0b2b7cad45aff042f2008090ee95dfe86a741044acbb517db86969527ca7d9ba122a030deac8844d050f8e",
    });
    const router = useRouter();
    const store = useStore();
    const convertTimestamp = (timestamp) => {
      let date = new Date(parseInt(timestamp));
      let year = date.getFullYear();
      let month = String(date.getMonth() + 1).padStart(2, "0");
      let day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    };
    // obj.dataObj = JSON.parse(store.meterNum)
    // obj.value = obj.dataObj.jf;
    obj.value = JSON.parse(sessionStorage.getItem("resultData")).jf;
    obj.dataObj = JSON.parse(sessionStorage.getItem("resultData"))[0];
    // const header = {
    //   token: JSON.parse(store.userInfo).tokenNo,
    //   "auth-code": JSON.parse(store.userInfo).tokenNo,
    // };
    obj.formDataParams = JSON.parse(sessionStorage.getItem("formDataParams"));
    const hyposensitization = (str, value) => {
      console.log(str);
      let arr = str.split(",");
      let argument = [];
      for (let i = 0; i < arr.length; i++) {
        console.log(i);
        if (value == "姓名") {
          if (!arr[i]) {
            argument.push("无");
          }
          // 两个字的姓名，隐藏第一个字
          if (arr[i].length === 2) {
            argument.push(arr[i].replace(/^./, "*"));
          }
          // 三个字的姓名，隐藏第一个字
          else if (arr[i].length === 3) {
            argument.push(arr[i].replace(/^./, "*"));
          }
          // 六个字以下，三个字以上，只显示最后两个字
          else if (arr[i].length <= 6 && arr[i].length >= 3) {
            argument.push(
              arr[i].replace(arr[i].substr(0, arr[i].length - 2), "***")
            );
          }
          // 六个字以上，只显示第一个字和最后一个字
          else if (arr[i].length > 6) {
            argument.push(arr[i].replace(/^(.).*(.)$/, "$1*****$2"));
          }
        } else {
          argument.push(arr[i].replace(/^(.).*(.)$/, "$1****************$2"));
        }
      }
      return argument.join();
    };
    return {
      ...toRefs(obj),
      name,
      desensitizedCommon,
      convertTimestamp,
      phone,
      hyposensitization
    };
  },
};
</script>
<style scoped lang="less">
.app {
  .top-bg {
    height: 140px;
    background: url("../assets/images/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-shrink: 0;
    div {
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 22.5px;
      text-align: center;
      color: #fff;
    }
  }
  .card-warp {
    border-radius: 4px;
    background: #fff;
    box-shadow: 0 0 6px #00000029;
    padding: 12px;
    margin: 0px 12px 16px;
    .card-title {
      font-family: "PingFang SC Bold";
      font-weight: 700;
      font-size: 13px;
      color: #333;
      padding-bottom: 0px;
      // border-bottom: 1px solid #e8e8e8;
      padding-top: 12px;
    }
    .card-top-title {
      font-size: 15px;
      color: #3f87f3;
      font-family: "PingFang SC Bold";
      font-weight: 700;
      border-bottom: 1px solid #e8e8e8;
      padding-bottom: 12px;
    }

    .text-top {
      font-family: "PingFang SC Bold";
      font-weight: 700;
      font-size: 13px;
      color: #333;
      padding-bottom: 12px;
    }
    :deep(.van-cell) {
      padding: 8px 0 0;
    }
    :deep(.van-field__value) {
      padding-left: 10px;
    }
  }
  .card-content {
    border-radius: 4px;
    background: #fff;
    box-shadow: 0 0 6px #00000012;
    padding: 12px 12px 0 12px;
    margin: 24px 12px 16px;
    .card-title {
      font-family: "PingFang SC Bold";
      font-weight: 700;
      font-size: 15px;
      color: #3f87f3;
      padding-bottom: 12px;
    }
    .card {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 12px 0;
      border-top: 1px solid #eee;
      .card-left {
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 13px;
        color: #333;
      }
      .card-right {
        font-family: "PingFang SC";
        font-weight: 400;
        font-size: 13px;
        color: #999;
        width: 180px;
        text-align: right;
      }
    }
  }
  .footer {
    width: 100%;
    height: 147.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    bottom: 0;
    left: 0;
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
