<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市“菜蓝子”以及农产品价格数据</div>
      </div>
      <div class="container-bottom">
        <table border="1" class="card-wrap">
          <tr class="title">
            <!-- <td align="center">序号</td> -->
            <td align="center">项目</td>
            <td align="center">价格变化(环比)</td>
            <td align="center">价格变化(同比)</td>
            <td align="center">单价/斤</td>
          </tr>
          <tr v-for="(item, index) in list" :key="index">
            <!-- <td align="center">{{ item.xh }}</td> -->
            <td align="center">{{ item.xm }}</td>
            <td align="center">{{ item.jgbh_hb }}</td>
            <td align="center">{{ item.jgbh_tb }}</td>
            <td align="center">{{ item.dj }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div class="message">
      本服务由甘肃省张掖市提供
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
            normGuid: "810049c7-aadc-4bbb-bf69-b5634a4dd1a6",
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
.container-wrap .container-bottom .list:last-child {
  border-bottom: none;
}
.container-wrap {
  min-height: 100vh;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: auto;
  .container-top {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    height: 140px;
    background: url("~@/assets/images/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    text-align: center;
    div {
      margin-top: 33.5px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }
  .container-bottom {
    margin-top: 110px;
    padding: 0 16px;
    // z-index: 3;
    .title {
      width: 100%;
    z-index: 3;
    td {
      font-weight: bold;
      height: 30px;
    }
  }
    .card-wrap {
      width:100%;
      border-radius: 4px;
      background: #fff;
      box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
      padding: 0 12px;
      position: relative;
      z-index: 3;
      margin-bottom: 12.5px;
    }
    .list {
      padding: 16px 0;
      border-bottom: 1px solid #dcdcdc;
      display: flex;
      justify-content: space-between;
      .list-left {
        width: 90px;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 17px;
        text-align: left;
        color: #333;
        word-break: break-all;
      }
      .list-right {
        width: 208px;
        word-break: break-all;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 16px;
        text-align: right;
        color: #999;
      }
    }
  }
  .message {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 14px;
    text-align: center;
    color: #ccc;
    margin: 16px auto;
  }
}
</style>
