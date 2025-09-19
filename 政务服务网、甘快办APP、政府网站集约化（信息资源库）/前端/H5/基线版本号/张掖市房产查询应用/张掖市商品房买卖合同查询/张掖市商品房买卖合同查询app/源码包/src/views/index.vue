<template>
  <div class="app">
    <div class="top-bg">
      <div>张掖市商品房买卖合同查询</div>
    </div>
    <div class="card-warp">
      <div class="text-top">
        <div class="left">
          <span style="color: #ee0a24; margin-right: 2px">*</span>合同编号
        </div>
        <!-- <div class="right" @click="showPop">如何查合同编号？</div> -->
      </div>
      <van-field
        v-model="spfhtbh"
        placeholder="请输入合同编号"
        clearable
        minlength="5"
        maxlength="20"
      />
      <div class="text-top" style="margin-top: 12px">
        <div class="left">
          <span style="color: #ee0a24; margin-right: 2px">*</span>房屋坐落
        </div>
      </div>
      <van-field
        v-model="fwzl"
        placeholder="请输入房屋坐落"
        clearable
        maxlength="50"
      />
      <div class="text-top" style="margin-top: 12px">
        <div class="left">购房人姓名</div>
      </div>
      <van-field
        v-model="buyerxm"
        placeholder="请输入购房人姓名"
        clearable
        maxlength="20"
      />
      <div class="text-top" style="margin-top: 12px">
        <div class="left">购房人身份证号</div>
      </div>
      <van-field
        v-model="buyerzjhm"
        placeholder="请输入购房人身份证号"
        clearable
        maxlength="50"
      />
    </div>
    <div style="margin: 0 12px; box-sizing: border-box">
      <van-button type="primary" block @click="getList">查询</van-button>
      <van-button block @click="reset" style="margin-top: 15px"
        >重置</van-button
      >
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
import { toRefs, reactive } from "vue";
import { useStore } from "../store/index.js";
import { getUserInfo } from "../tools/getUserInfo";
import { requestWithSM2 } from "../tools/request.js";
import { showToast, showDialog, showLoadingToast } from "vant";
import tools from "../tools/tool.js";
export default {
  name: "indexName",
  setup() {
    const obj = reactive({
      spfhtbh: "",
      buyerzjhm: "",
      buyerxm: "",
      fwzl: "",
      userInfo: {},
      key: "044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e",
    });
    const router = useRouter();
    const store = useStore();
    const getUser = async () => {
      const res = await getUserInfo();
      const userInfo = {
        userId: res.data.userId,
        tokenNo: res.data.tokenNo,
      };
      obj.userInfo = userInfo;
      store.userInfo = JSON.stringify(userInfo);
      if (res.data.userType != 10) {
        showDialog({
          title: "提示",
          message: "该业务仅限个人用户办理",
          confirmButtonText: "确认",
        }).then(() => {
          // on close
          lightAppJssdk.navigation.close({
            success: function (data) {
              return data;
            },
            fail: function (data) {
              return data;
            },
          });
        });
      }
    };
    getUser();
    const reset = () => {
      obj.spfhtbh = "";
      obj.buyerzjhm = "";
      obj.buyerxm = "";
      obj.fwzl = "";
    };
    const getList = async () => {
      if (obj.spfhtbh === "") {
        showToast("请输入合同编号");
        return;
      }
      if (obj.fwzl === "") {
        showToast("请输入房屋坐落");
        return;
      }
      if (obj.buyerxm!='' && obj.buyerzjhm == "") {
        showToast("请输入购买方证件号码");
        return;
      }
      if (obj.buyerzjhm!='' && obj.buyerxm == "") {
        showToast("请输入购买方姓名");
        return;
      }
      if (obj.buyerxm == "" && obj.buyerzjhm == "") {
        delete obj.buyerxm;
        delete obj.buyerzjhm;
      }
      let specialKey =
        "/[`-~!@#$^&*=|{}':;'\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/";
      for (let i = 0; i < obj.spfhtbh.length; i++) {
        if (specialKey.indexOf(obj.spfhtbh.substr(i, 1)) != -1) {
          showToast("输入不能含有特殊字符与空格");
          return;
        }
      }
      const regex = /\s/;
      if (regex.test(obj.spfhtbh)) {
        showToast("输入不能含有特殊字符与空格");
        return;
      }
      const params = {
        params: tools.sm2Encrypt1(
          JSON.stringify({
            // spfhtbh: "202009120152",
            spfhtbh: obj.spfhtbh,
            // fwzl: "民乐县生态宜居小镇36号住宅楼（36）幢八层3-802",
            fwzl: obj.fwzl,
            buyerxm:obj.buyerxm,
            buyerzjhm:obj.buyerzjhm,
            tokenNo:JSON.parse(store.userInfo).tokenNo,
            // tokenNo:
            //   "naturalToken0469818175423826794330974049935477633680458028342182314167083500",
          })
        ),
      };
      try {
        const toast1 = showLoadingToast({
          message: "加载中...",
          forbidClick: true,
          loadingType: "spinner",
          duration: 0,
        });
        const res = await requestWithSM2(
          "zysslhfwzqmiabu",
          "newHouseContract",
          params
        );
        toast1.close();
        if (res.code === "200" && res.success && res.data) {
          let data = tools.sm2Decrypt1(res.data);
          let resultData = JSON.parse(data);
          if (resultData.length == 0) {
            showToast({
              message: "当前合同编号暂未查询到数据，请检查后重新查询",
            });
            return;
          } else {
            toast1.close();
            sessionStorage.setItem("resultData", data);
            router.push({
              path: "/detail",
            });
          }
        } else {
          toast1.close();
          showToast(res.message);
        }
        obj.userInfo = JSON.parse(store.userInfo);
      } catch (err) {
        toast1.close();
        showToast({
          message: err,
          duration: 1000,
        });
        return;
      }

      obj.spfhtbh = "";
      // obj.dm = "";
    };
    // const showPop = () => {
    //   showDialog({
    //     title: "如何查合同编号？",
    //     message:
    //       "您可查看缴费票据、催缴短信、咨询小区物业，或拨打缴费单位客服电话查询",
    //     confirmButtonText: "我知道了",
    //   }).then(() => {
    //     // on close
    //   });
    // };
    // 生成支付交易流水号 (获取业务系统订单号唯一（bizOrderNo）)
    // const getBizOrderNo = (value) => {
    //   // showLoadingToast({
    //   //   message: '交易流水号生成中...',
    //   //   forbidClick: true,
    //   //   loadingType: 'spinner',
    //   //   duration: 0
    //   // })
    //   return new Promise((resolve, reject) => {
    //     const params = {
    //       serialNumber: value, // 商品订单号
    //     };
    //     requestWithSM2("tyzf", "tradenoZS", params).then(
    //       (res) => {
    //         if (res.code === "200" && res.success) {
    //           const bizOrderNo = res.data;
    //           resolve(bizOrderNo);
    //         } else {
    //           const err = { message: "生成支付交易流水号失败！" };
    //           reject(err);
    //         }
    //       },
    //       () => {
    //         const err = { message: "生成支付交易流水号失败！" };
    //         resolve(err);
    //       }
    //     );
    //   });
    // };
    return {
      // obj,
      ...toRefs(obj),
      getList,
      // showPop,
      reset,
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
    margin: 12px 12px 30px;

    .text-top {
      display: flex;
      justify-content: space-between;
      .left {
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 15px;
        color: #333;
      }
      .right {
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 12px;
        color: #3f87f3;
      }
    }
    :deep(.van-cell) {
      padding: 12px 0;
      border-bottom: 1px dashed #e8e8e8;
    }
  }
  .footer {
    width: 100%;
    height: 137.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    bottom: 0px;
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
