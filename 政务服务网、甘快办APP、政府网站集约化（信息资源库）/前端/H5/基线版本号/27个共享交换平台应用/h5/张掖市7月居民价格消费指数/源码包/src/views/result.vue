<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div>张掖市居民价格消费指数</div>
    </div>
    <div style="padding: 0 12px">
      <table border="1" style="width: 100%; margin-top: 12px">
        <tr class="title">
          <td style="width: 35%" align="center">项目</td>
          <td style="width: 15%" align="center">环比(%)</td>
          <td style="width: 15%" align="center">同比(%)</td>
          <td style="width: 20%" align="center">累计同比(%)</td>
        </tr>
        <tr v-for="(item, index) in list" :key="index">
          <td align="center">{{ item.xm }}</td>
          <td align="center">{{ item.hb }}</td>
          <td align="center">{{ item.tb }}</td>
          <td align="center">{{ item.ljtb }}</td>
        </tr>
      </table>
    </div>
    <div class="footer" v-show="showBtn">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>

<script>
import { Toast } from "vant";
import IndexApi from "@/api/index";
import sm2 from "../utils/sm2";
export default {
  data() {
    return {
      list: [],
      showBtn: true,
      PublicKey:
        "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b",
      PrivateKey:
        "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b",
    };
  },

  created() {},
  mounted() {
    Toast({
      message: "查询中",
      duration: 0,
      forbidClick: true,
    });
    const params = {
      app_id: "zyjtyyjkrzoeeih",
      interface_id: "zyjtyyjk",
      biz_content: JSON.stringify({
        params: sm2.encrypt(
          JSON.stringify({
            normGuid: "4633ad80-e239-4f5e-8c4a-45b939983fb9",
            fieldList: [],
          }),
          this.PublicKey
        ),
      }),
    };

    IndexApi.queryList(params)
      .then((res) => {
        if (res.code == "200") {
          res = JSON.parse(sm2.decrypt(res.data, this.PrivateKey));
          Toast.clear();
          this.list = res.custom.result.cockpitData;
          console.log(res.custom.result.cockpitData, "res0.0.0.0.0.");
        } else {
          Toast({
            message: "暂无数据,请重试！",
          });
        }
      })
      .catch(() => {
        this.error = true;
        this.loading = false;
        Toast({
          message: "网络异常,请重试！",
        });
      });
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
  overflow: auto;

  .title {
    td {
      font-weight: bold;
      height: 30px;
    }
  }

  .top-bg {
    height: 140px;
    background: url("~@/assets/images/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    // position: absolute;
    // left: 0;
    // right: 0;
    // padding: 0px !important;
    div {
      margin-top: 33.5px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0px;
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
