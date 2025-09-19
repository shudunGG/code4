<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市民政局社会救助服务热线查询</div>
      </div>
     
        <div class="container-bottom">
          <div class="card-wrap" v-for="(item, index) in list " :key="index">
            <!-- <div class="list">
              <div class="list-left">序号</div>
              <div class="list-right">{{ item.xh || "暂无数据" }}</div>
            </div> -->

            <div class="list">
              <div class="list-left">部门</div>
              <div class="list-right">{{ item.bm || "暂无数据" }}</div>
            </div>
            <div class="list">
              <div class="list-left">电话</div>
              <div class="list-right"><a
                style="display: block; color: #1677ff"
                :href="'tel:' + item.dh"
                >{{ item.dh || "暂无数据" }}</a
              ></div>
            </div>
            <div class="list">
              <div class="list-left">接听时间</div>
              <div class="list-right">{{ list1[0].dh || "暂无数据" }}</div>
            </div>
            <!-- <div class="list">
              <div class="list-left">环比(%)</div>
              <div class="list-right">{{ item.jgbh_hb || "暂无数据" }}</div>
            </div>
            <div class="list">
              <div class="list-left">单价(元)</div>
              <div class="list-right">{{ item.dj || "暂无数据" }}</div>
            </div> -->
          </div>
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
      list1: [],
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
            normGuid: "c8a81fa6-4c43-4ea9-a44c-eed7958a35a6",
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
          this.list1= this.list.splice(7, 1)
          console.log(res.custom.result.cockpitData, "res0.0.0.0.0.");
          console.log(this.list1,'1');
          console.log(this.list,'list');
          
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
  .title {
    td {
      font-weight: bold;
      height: 30px;
    }
  }
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
    .card-wrap {
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
