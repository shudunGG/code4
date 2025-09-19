<template>
  <div class="container-wrap">
    <div class="container">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">新型经营主体县区分布</div>
        <!-- <img class="containertitleimg" src="../assets/images/xy.png" alt=""> -->
      </div>
      <dir class="chart_title" v-if="type == 0">
        市级以上龙头企业数量
      </dir>
      <dir class="chart_title" v-if="type == 1">
        农民专业合作社数量
      </dir>
      <dir class="chart_title" v-if="type == 2">
        家庭农场数量
      </dir>
      <div class="dw">单位（家）</div>
      <div class="chart_box">
        <div
          ref="chart"
          style="width: 100%; height: 282px;margin-top: -40px;padding-left: 12px;box-sizing: border-box;"
        ></div>
        <!-- <div ref="chart" style="width: 100%; height: 480px;"></div> -->
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
      type: 0,
      chartData: [],
      xmData: {},
      areaList: [],
    };
  },
  created() {
    const type = this.$route.query.type;
    console.log("=====", type);
    this.type = type;
  },
  mounted() {
    this.initData();
  },
  methods: {
    initChart() {
      const chartDom = this.$refs.chart;
      const myChart = echarts.init(chartDom);
      const demandOptions4 = {
        xAxis: {
          data: this.areaList,
          axisLabel: {
            show: true,
            interval: 0, //使x轴上的文字显示完全,
            //设置一行显示几个字,自己设置
            formatter: function(params) {
              var newParamsName = "";
              var paramsNameNumber = params.length;
              var provideNumber = 5;
              var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
              if (paramsNameNumber > provideNumber) {
                for (var p = 0; p < rowNumber; p++) {
                  var tempStr = "";
                  var start = p * provideNumber;
                  var end = start + provideNumber;
                  if (p == rowNumber - 1) {
                    tempStr = params.substring(start, paramsNameNumber);
                  } else {
                    tempStr = params.substring(start, end) + "\n";
                  }
                  newParamsName += tempStr;
                }
              } else {
                newParamsName = params;
              }
              return newParamsName;
            },
          },
        },
        yAxis: {
          axisLabel: {
            // width: 30,
            fontSize: 10
          },
        },
        series: [
          {
            type: "bar",
            data: this.chartData,
            itemStyle: {
              color: "#247CFA",
            },
            barWidth: "25%",
            label: {
              show: true,
              position: "top",
            },
          },
        ],
      };
      myChart.setOption(demandOptions4);
    },
    initData() {
      let detailData = JSON.parse(sessionStorage.getItem("detailData"));
      console.log("aaaaaaaaa", detailData);
      let chartData = [];
      let areaList = [];
      for (let i = 0; i < detailData.length; i++) {
        const item = detailData[i];
        if (this.type == 0) {
          chartData.push(item.ltqysl);
        } else if (this.type == 1) {
          chartData.push(item.nmzyhzssl);
        } else if (this.type == 2) {
          chartData.push(item.jtncsl);
        }
        areaList.push(item.qxm);
      }
      this.areaList = areaList;
      this.chartData = chartData;
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
  width: 375px;
  margin: 0 auto;
  box-sizing: border-box;
  /* padding: 0 12px; */
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
  background-color: #f6f7f9;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: #999;
  font-weight: 600;
  margin: 16px auto 0;
}

.dw {
  margin-top: 12px;
  font-size: 15px;
  line-height: 21px;
  color: #000;
  padding-left: 12px;
  box-sizing: border-box;
  width: 375px;
  margin: 12px auto 0;
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
