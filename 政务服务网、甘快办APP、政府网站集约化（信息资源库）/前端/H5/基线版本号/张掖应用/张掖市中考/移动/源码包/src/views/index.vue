<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div style="">张掖市2023年中考成绩查询平台</div>
    </div>
    <!-- 为谁参保登记 -->
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
            v-model.trim="name"
            placeholder="请输入姓名（必填）"
            @keyup="keyUp"
            maxlength="18"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">统编学号</div>
        <div class="card-list-picker">
          <input
            v-model.trim="value"
            placeholder="请输入统编学号（必填）"
            @keyup="keyUp"
            maxlength="18"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">身份证号</div>
        <div class="card-list-picker">
          <input
            v-model.trim="card"
            placeholder="请输入统编学号（必填）"
            @keyup="keyUp"
            maxlength="18"
          />
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title">验证码</div>
        <div class="yzm">
          <input
            id="put"
            type="text"
            placeholder="请输入验证码"
            v-model="verCode"
          />
          <canvas
            id="canvas"
            width="100"
            height="43"
            class="formCodeImg"
            @click="show_num()"
          ></canvas>
        </div>
      </div>
      <van-button class="queryButton" type="info" @click="queryList"
        >查询</van-button
      >
      <van-button class="resetButton" plain type="info" @click="resetInfo"
        >重置</van-button
      >
    </div>
    <div class="tips">
      可查询成绩为张掖市县区（即：甘州区、临泽县、高台县、山丹县、民乐县、肃南裕固族自治县）所有中考科目。为保护考生隐私，每个账号每日仅限5次查询。考生
      还可以通过“张掖市政务服务网https:/zwfw.gansu.gov.cn/zhangye/”、张掖市智慧教育云平台查询成绩。
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市教育局提供</div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import IndexApi from "@/api/index";
import requestJapss from "../utils/request";
import { Toast } from "vant";
import sm2 from "../utils/sm2";
import { getUserInfo } from "../utils/getUserInfo";
import aes from "../utils/aes";
export default {
  data() {
    return {
      code: "",
      cityShow: false,
      selctIndex: 0,
      cityArr: [{}, { values: [] }],
      selectName: "",
      selectArr: [],
      queryArr: "",
      pageNum: "1",
      name: "",
      value: "",
      card: "",
      verCode: "",
      usrInfo: {},
      requestCount: 0,
      num: 0,
      time: "",
    };
  },
  async created() {
    this.usrInfo = await getUserInfo();
    let date = new Date();
    let y = date.getFullYear();
    let m = date.getMonth() + 1;
    let d = date.getDate();
    let h = date.getHours();
    let M = date.getMinutes();
    this.time = y + "-" + m + "-" + d;
    // 判断分钟 如果小于30 就传 00 如果大于30 就传 30
    if (M >= 30) {
      M = 30;
    } else {
      M = "00";
    }
    let year = y + "/" + m + "/" + d;
    let time = h + ":" + M;
    const params = {
      type: "张掖市", // 市州名称
      appName: "张掖市中考查询", // 应用名称
      time: year + " " + time, // 2022/6/23 10：00 时间入参格式
    };
    requestJapss.jpass2("sjmd", "sjmdjk", params).then((res) => {
      console.log(res, "埋点");
    });
  },
  mounted() {
    this.show_num();
    this.getRequestCount();
  },
  methods: {
    show_num() {
      var show_num = [];
      // var CODE = ''
      var canvas_width = $("#canvas").width();
      var canvas_height = $("#canvas").height();
      var canvas = document.getElementById("canvas");
      var context = canvas.getContext("2d");
      canvas.width = canvas_width;
      canvas.height = canvas_height;
      var sCode =
        "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
      var aCode = sCode.split(",");
      var aLength = aCode.length;

      for (var i = 0; i <= 3; i++) {
        var j = Math.floor(Math.random() * aLength);
        var deg = (Math.random() * 30 * Math.PI) / 180;
        var txt = aCode[j];
        show_num[i] = txt.toLowerCase();
        var x = 10 + i * 20;
        var y = 20 + Math.random() * 8;
        context.font = "bold 23px 楷体";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = this.randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
      }
      for (var i = 0; i <= 5; i++) {
        context.strokeStyle = this.randomColor();
        context.beginPath();
        context.moveTo(
          Math.random() * canvas_width,
          Math.random() * canvas_height
        );
        context.lineTo(
          Math.random() * canvas_width,
          Math.random() * canvas_height
        );
        context.stroke();
      }
      for (var i = 0; i <= 30; i++) {
        context.strokeStyle = this.randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
      }
      this.CODE = show_num[0] + show_num[1] + show_num[2] + show_num[3];
    },
    randomColor() {
      var r = Math.floor(Math.random() * 256);
      var g = Math.floor(Math.random() * 256);
      var b = Math.floor(Math.random() * 256);
      return "rgb(" + r + "," + g + "," + b + ")";
    },
    ...mapMutations(["setDetail"]),
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern =
        /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.name = this.name.replace(pattern, "");
      this.value = this.value.replace(pattern, "");
    },
    //限制查询时间
    getCurrent() {
      let time = null;
      let now = new Date().valueOf();
      let goal = new Date("2022/6/27 00:00:00").getTime();
      if (now > goal) {
        time = true;
      } else {
        time = false;
      }
      return time;
    },
    // 查询
    async queryList() {
      let val = this.verCode.toLowerCase();
      Toast({
        message: "查询中",
        duration: 0,
      });
      if (!this.getCurrent()) {
        Toast({
          message: "成绩查询未开放，请于2023年6月26日查询",
        });
        return;
      }
      if (this.name === "") {
        Toast({
          message: "请输入姓名",
        });
        return;
      }
      const regName = /^[a-zA-Z\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
      if (!regName.test(this.name)) {
        Toast({
          message: "姓名格式不正确",
        });
        return;
      }
      if (this.value === "") {
        this.$toast("请输入准考证号");
        return;
      }
      const regValue = /^\d{10,}$/;
      if (!regValue.test(this.value)) {
        this.$toast("准考证号格式不正确");
        return;
      }
      if (!this.verCode) {
        this.$toast("请输入验证码");
        return;
      } else if (val !== this.CODE) {
        this.$toast("验证码错误");
        this.verCode = "";
        return;
      }
      console.log(this.num, "this.num");
      if (this.num > 5) {
        Toast({
          message: "今日查询次数已到上限",
        });
        return;
      }
      let params = {
        pageNum: sm2.encrypt(this.pageNum),
        pageSize: sm2.encrypt("10"),
        map: sm2.encrypt(
          JSON.stringify({
            xm: JSON.stringify({
              type: 2,
              content: this.name,
              left: "",
              right: "",
            }),
            xh: JSON.stringify({
              type: 2,
              content: this.value,
              left: "",
              right: "",
            }),
            sfzh: JSON.stringify({
              type: 2,
              content: this.card,
              left: "",
              right: "",
            }),
          })
        ),
      };
      this.show_num();
      await IndexApi.queryList(params)
        .then((res) => {
          Toast.clear();
          res = JSON.parse(sm2.decrypt(res.data.dataResult));
          console.log("res", res);
          this.writeRequestCount();
          if (res.dataList.length > 0) {
            this.setDetail(res.dataList);
            this.$store.state.total = res.totalCount;
            this.$router.push({
              path: "/listDetail",
              query: {
                name: JSON.stringify(this.name),
                value: JSON.stringify(this.value),
              },
            });
            this.num = "";
          } else {
            Toast({
              message: "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          Toast({
            message: "查询失败",
          });
        });
    },
    //写入查询次数
    writeRequestCount() {
      let keys = this.usrInfo.userName + this.time + "_6204";
      const paramss = {
        key: aes.encrypt(keys),
        value: this.requestCount,
      };
      requestJapss.jpass2("zkcx", "zkcxxrcxcs", paramss).then((res) => {
        console.log(res, "写入次数");
        this.requestCount += 1;
        this.getRequestCount();
      });
    },
    //获取查询次数
    getRequestCount() {
      let key = this.usrInfo.userName + this.time + "_6204";
      const params = {
        key: aes.encrypt(key),
      };
      requestJapss.jpass2("zkcx", "zkcxhcxcs", params).then((res) => {
        console.log(res, "次数");
        this.num = JSON.parse(res.data).data;
      });
    },
    // 重置信息
    resetInfo() {
      window.location.reload();
    },
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
  // picker默认样式修改
  /deep/.van-picker__toolbar {
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
  /deep/.van-picker-column__item--selected {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }
  .top-bg {
    height: 140px;
    background: url("~@/assets/images/bg.png");
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
      padding: 4px 0;
      border-bottom: 1px solid #e8e8e8;
      input {
        width: 100%;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #999;
        // margin: 12px 0;
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
      .yzm {
        display: flex;
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
  .tips {
    margin-top: 10px;
    margin-bottom: 32px;
    font-family: PingFangSC-Regular;
    font-size: 13px;
    color: #333;
    letter-spacing: 0;
    text-align: justify;
    line-height: 20px;
    font-weight: 400;
    span {
      color: #3f87f3;
      a {
        color: #3f87f3 !important;
      }
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
