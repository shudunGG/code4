<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div id="myChart"></div>
      <!-- <table border="1" class="card-wrap">
          <tr class="title">
            <td align="center">项目</td>
            <td align="center">价格变化(环比)</td>
            <td align="center">价格变化(同比)</td>
            <td align="center">单价/斤</td>
          </tr>
          <tr v-for="(item, index) in dataList" :key="index">
            <td align="center">{{ item.xm }}</td>
            <td align="center">{{ item.jgbh_hb }}</td>
            <td align="center">{{ item.jgbh_tb }}</td>
            <td align="center">{{ item.dj }}</td>
          </tr>
        </table> -->
      <div class="bottom">
        <div class="group">
          <div class="group_l">合计</div>
          <div class="group_r">
            {{ `${Year ? Year[0].nf : ""}年： ${Year ? Year[0].hj : ""}` }}
            <span> | </span>
            {{ `${Year ? Year[1].nf : ""}年： ${Year ? Year[1].hj : ""}` }}
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { toRefs, onBeforeMount, reactive, watch, onMounted } from "vue";
import tools, { changFormate } from "@/tools/tool";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  dataList: {},
});
const aYear = ref([]); //A列表
const aYearName = ref(""); //年份
const bYear = ref([]); //B列表
const bYearName = ref(""); //年份
const Year = ref([{ nf: "0" }, { nf: "0" }]); //数据
onMounted(() => {
  state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.dataList);
  data.myChart = echarts.init(document.getElementById("myChart"), null, {
    height: "480px",
  });
  aYear.value = changFormate(state.dataList)[0];
  aYearName.value = state.dataList[0].nf;
  bYear.value = changFormate(state.dataList)[1];
  bYearName.value = state.dataList[1].nf;
  Year.value = state.dataList;
  console.log(aYear.value, "Year.value");
  setEchartsOptions();
});
// 创建响应式数据对象，用于存储用户信息
const data = reactive({
  option: {
    tooltip: {},
    legend: {},
    grid: {
      left: "1",
      containLabel: true,
    },
    xAxis: {
      data: [
        "视力残疾",
        "听力残疾",
        "智力残疾",
        "肢体残疾",
        "精神残疾",
        "综合残疾",
      ],
      axisLabel: {
        interval: 0,
        width: 100,
        overflow: "break", // 防止标签过长时显示不全
        fontSize: 12, // 减小文字大小
        fontWeight: "400",
        color: "#999999",
      },
    },
    yAxis: {
      interval: 2000,
      autoScale: true, // 开启自适应
    },
    series: [
      {
        name: aYearName,
        data: aYear,
        type: "bar",

        label: {
          show: true,
          color: "#000", // 设置柱条尾部数据的字体颜色为白色
          position: "top",
        },
        itemStyle: {
          // color: "#247CF9", // 设置柱条的颜色为蓝色
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: "#41BE94", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#247CFA", // 100% 处的颜色
              },
            ],
          },
        },
      },
      {
        name: bYearName,
        data: bYear,
        type: "bar",
        label: {
          show: true,
          color: "#000", // 设置柱条尾部数据的字体颜色为白色
          position: "top",
        },
        itemStyle: {
          // color: "#FF5803", // 设置柱条的颜色为红色
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: "#F59045", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#FF5500", // 100% 处的颜色
              },
            ],
          },
        },
      },
    ],
  },
  myChart: null,
});
// const stopWatch = watch(
//   state.dataList,
//   (newValue, oldValue) => {
//     console.log(newValue, oldValue);
//   },
//   { deep: true }
// );
// 设置Echarts的配置项
const setEchartsOptions = () => {
  data.myChart.setOption(data.option);
};
// return { ...toRefs(state), setEchartsOptions };
//   },
// };
</script>
<style scoped>
.result-container table {
  margin-bottom: 0.2rem;
}

.result-container table td {
  background: #ffffff;
  text-align: center;
}

.result-container table thead td {
  font-size: 0.16rem;
  font-weight: bold;
}

.result-container table td,
.result-container table th {
  border-right: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
  font-size: 0.14rem;
  padding: 0 0.14rem;
}

.tips {
  margin-top: 0.3rem;
  color: #999999;
  text-align: center;
}

.block {
  text-align: center;
}
.container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
.container .content {
  width: 100%;
  margin-top: 12px;
  min-height: 457px;
  background-color: #fff;
}
.container #myChart {
  padding: 26px 0 0 12px;
}
.result-container .bottom {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: left;
  align-items: center;
  align-content: center;
  width: 100%;
}
.result-container .group {
  width: 1120px;
  height: 95px;
  display: flex;
  align-items: center;
  padding: 12px;
  background: url('../assets/images/bgi2.png') no-repeat center center;
  margin-bottom: 25px;
  background-size: cover;
}
.result-container .group .group_l {
  width: 104px;
  height: 55px;
  border-radius: 4px;
  background: #2375ea;
  line-height: 55px;
  text-align: center;
  font-weight: 700;
  font-size: 15px;
  color: #fff;
  margin-right: 10px;
}
.result-container .group .group_r {
  padding: 12px;
  font-size: 15px;
  font-weight: 500;
}
.result-container .group .group_r span {
  color: #2375ea;
  font-size: 15px;
  font-weight: 500;
  margin: 0 10px;
}
</style>