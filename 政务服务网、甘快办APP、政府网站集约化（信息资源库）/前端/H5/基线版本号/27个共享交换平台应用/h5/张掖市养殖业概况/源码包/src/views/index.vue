<template>
  <div class="container-wrap">
    <div class="container">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">养殖业</div>
        <!-- <img class="containertitleimg" src="../assets/images/xy.png" alt=""> -->
      </div>
      <div class="wrap xm">
        <img src="../assets/images/icon1.png" class="icon" alt="" />
        <div class="wrap_right">
          <div class="right_title">畜牧产业综合产值</div>
          <div class="right_content">
            <div>{{ xmData.cz }}亿元</div>
            <div>
              同比{{ xmData.tb }}%
              <img src="../assets/images/up.png" class="up_arrow" alt="" />
            </div>
          </div>
        </div>
      </div>
      <div class="wrap cq">
        <img src="../assets/images/icon2.png" class="icon" alt="" />
        <div class="wrap_right">
          <div class="right_title">畜禽规模化养殖场数量</div>
          <div class="right_content">
            {{ allnum }}家
          </div>
        </div>
      </div>
      <dir class="chart_title">
        各类畜禽养殖场类型分布
      </dir>
      <div class="chart_box">
        <div
          ref="chart"
          style="width: 138px; height: 138px;margin-right: 21px;"
        ></div>
        <!-- <div ref="chart" style="width: 100%; height: 480px;"></div> -->
        <div class="tllist">
          <div class="tllistper" v-for="(item, index) in tullist" :key="index">
            <div class="tllistperin1" :style="item.itemStyle"></div>
            <div class="tllistperin2">{{ item.name }}</div>
            <div class="tllistperin3">{{ item.value }}家</div>
            <div class="tllistperin4">
              {{ ((item.value / allnum) * 100).toFixed() }}%
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="provider">
      本服务由甘肃省张掖市提供
    </div>
  </div>
</template>

<script>
// import IndexApi from "@/api/index";
// import { Toast } from "vant";
import * as echarts from "echarts";
import { color } from "echarts/core";
import requestJpaas from "../utils/request";
const { getData } = requestJpaas;
import { tools } from "../tools/tool";
export default {
  name: "MyChart",
  data() {
    return {
      allnum: 999,
      yhname: "养殖场数量",
      listdata: [
        { value: 0, name: "羊", itemStyle: { color: "#2375EA" } },
        {
          value: 0,
          name: "牛",
          itemStyle: { color: "#FA8F42" },
        },
        {
          value: 0,
          name: "生猪",
          itemStyle: { color: "#FC5634" },
        },
        { value: 0, name: "奶牛", itemStyle: { color: "#21C5A1" } },
        { value: 0, name: "肉鸡", itemStyle: { color: "#8265F1" } },
        {
          value: 0,
          name: "蛋鸡",
          itemStyle: { color: "#438C03" },
        },
      ],
      tullist: [
        {
          value: 0,
          name: "羊",
          itemStyle: { background: "#2375EA" },
        },
        {
          value: 0,
          name: "牛",
          itemStyle: { background: "#FA8F42" },
        },
        {
          value: 0,
          name: "生猪",
          itemStyle: { background: "#FC5634" },
        },
        {
          value: 0,
          name: "奶牛",
          itemStyle: { background: "#21C5A1" },
        },
        { value: 0, name: "肉鸡", itemStyle: { background: "#8265F1" } },
        {
          value: 0,
          name: "蛋鸡",
          itemStyle: { background: "#438C03" },
        },
      ],
      xmData: {},
    };
  },
  created() {},
  mounted() {
    this.getXMData();
    this.getlist();
  },
  methods: {
    initChart() {
      const chartDom = this.$refs.chart;
      const myChart = echarts.init(chartDom);
      const demandOptions4 = {
        tooltip: {
          trigger: "item",
          show: false
        },
        grid: {
          height: 400,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          splitLine: { show: false },
          axisLine: { show: false },
        },
        yAxis: {
          splitLine: { show: false },
        },
        series: [
          {
            hoverAnimation:false,
            name: "",
            type: "pie",
            radius: ["70%", "90%"], // 设置内半径和外半径，形成环形
            data: this.listdata,
            padAngle: 1,
            label: {
              normal: {
                show: true,
                formatter: [
                  `{textcss1|${this.allnum}个}`,
                  `{textcss2|${this.yhname}}`,
                ].join("\n"),
                position: "center",
                name: "111",
                rich: {
                  textcss1: {
                    color: "#000",
                    lineHeight: 20,
                    fontSize: 18.5,
                  },
                  textcss2: {
                    color: "#000",
                    lineHeight: 20,
                    fontSize: 15,
                  },
                },
              },
            },
            rich: {
              titlecss: {
                color: "red",
              },
            },
            labelLine: {
              show: false,
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      myChart.setOption(demandOptions4);
    },
    async getXMData() {
      const data = {
        normGuid: "00c2c553-5e9f-456b-b2c0-212bd79cda72",
        fieldList: [],
      };
      const res = await getData({
        params: tools.encryptSm2(JSON.stringify(data)),
      });
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result
        .cockpitData;
      this.xmData = newres[0];
    },
    async getlist() {
      const data = {
        normGuid: "9ec72bd8-92a3-4c75-96d5-3ad578405420",
        fieldList: [],
      };
      const res = await getData({
        params: tools.encryptSm2(JSON.stringify(data)),
      });
      console.log("127-----", JSON.parse(tools.decryptSm2(res.data)));
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result
        .cockpitData;
      // this.allnum = JSON.parse(
      //   tools.decryptSm2(res.data)
      // ).custom.result.sumCount;
      let allnum = 0
      newres.forEach((item, index) => {
        if (item.lx === "羊") {
          this.listdata[0].value = item.sl;
          this.tullist[0].value = item.sl;
        } else if (item.lx === "牛") {
          this.listdata[1].value = item.sl;
          this.tullist[1].value = item.sl;
        } else if (item.lx === "生猪") {
          this.listdata[2].value = item.sl;
          this.tullist[2].value = item.sl;
        } else if (item.lx === "奶牛") {
          this.listdata[3].value = item.sl;
          this.tullist[3].value = item.sl;
        } else if (item.lx === "肉鸡") {
          this.listdata[4].value = item.sl;
          this.tullist[4].value = item.sl;
        } else if (item.lx === "蛋鸡") {
          this.listdata[5].value = item.sl;
          this.tullist[5].value = item.sl;
        }
        allnum += item.sl
      });
      console.log('=============',allnum);
      this.allnum = allnum
      this.initChart();
    },
  },
};
</script>
<style scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f6f6f8;
  overflow: auto;
}
.container {
  width: 100%;
  /* border: 1px solid red; */
  margin-top: 12px;
  background: #fff;
  padding-bottom: 33px;
  padding-top: 16px;
}
.containertitle {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.containertitlebor {
  width: 3.5px;
  height: 15px;
  border-radius: 3.5px;
  background: #3266c2;
  margin-left: 12px;
}
.containertitletext {
  font-family: "PingFang SC Bold";
  font-weight: 700;
  line-height: 21px;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 8px;
}
.containertitleimg {
  width: 7px;
  height: 12px;
  margin-left: 8px;
}

.chart_box {
  display: flex;
  align-items: center;
  margin-top: 25px;
  width: 375px;
  box-sizing: border-box;
  padding: 0 15px;
  margin: 25px auto 0;
  justify-content: space-between;
}
.tllist {
  width: 181px;
  /* border: 1px solid red; */
}
.tllistper {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.tllistperin1 {
  width: 12px;
  height: 12px;
  background: red;
  border-radius: 50px;
  /* border: 1px solid red; */
}
.tllistperin2 {
  width: 60px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-align: left;
  color: #000;
  margin-left: 5px;
}
.tllistperin3 {
  width: 70px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 20px;
  text-align: right;
  color: #000;
}
.tllistperin4 {
  width: 54px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-align: right;
  color: #000;
}

.wrap {
  width: 351px;
  height: 74px;
  margin: 12px auto 0;
  background-size: 100% 100%;
  background-position: center center;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  padding: 0 12px 0 19px;
}

.xm {
  background-image: url(../assets/images/bg1.png);
}

.cq {
  background-image: url(../assets/images/bg2.png);
}

.icon {
  width: 42px;
  height: 42px;
  margin-right: 21px;
}

.wrap_right {
  flex: 1;
  font-size: 15px;
  line-height: 21px;
  color: #fff;
}

.right_title {
  margin-bottom: 8px;
}

.right_content {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.up_arrow {
  width: 10px;
  height: 14px;
  margin-left: 8px;
}

.chart_title {
  width: 351px;
  height: 46px;
  border-radius: 2px;
  background-color: #F6F7F9;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: #999;
  font-weight: 600;
  margin: 16px auto 0;
}

.provider {
  width: 100%;
  height: 58px;
  background-image: url(../assets/images/bottomBg.png);
  background-position: center center;
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 15px;
  color: #999;
  position: absolute;
  left: 0;
  bottom: 0;
}
</style>
