<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div id="myChart"></div>
    </div>
  </section>
</template>
<script setup>
import { toRefs, onBeforeMount, reactive, onMounted, watch } from "vue";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  dataList: {},
});
const pigPrices = ref([]); // 生猪价格
const cornPrices = ref([]); // 玉米价格
const yAxisDate = ref([]); //y轴数据
const pigcorn = ref([]); //猪粮比价
const data = reactive({
  option: {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    legend: {
      // orient: "vertical",
      itemGap: 195,
      left: 195,
    },
    grid: {
      left: "2.5%",
      right: "2.5%",
      containLabel: true,
    },
    yAxis: {
      // show: false,
      // type: "value",
      name: "单位 (人)", // Y 轴的单位显示在这里
      nameLocation: "end", // 使单位显示在最上方
      nameGap: 20, // 控制单位与 Y 轴的距离
      nameRotate: 0, // 使单位与 Y 轴垂直
      // left:50
    },
    xAxis: {
      data: yAxisDate,
      // show: false,
      axisLabel: {
        width: 68,
        overflow: "break", // 防止标签过长时显示不全
        fontSize: 12, // 减小文字大小
        color: "#999999",
      },
    },
    series: [
      {
        name: "注册执业医师、助理医师",
        type: "bar",
        data: pigPrices,
        label: {
          show: true,
          color: "#000", // 设置柱条尾部数据的字体颜色为白色
          position: "top",
        },
        // 设置柱状图的样式
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
        name: "注册护士",
        type: "bar",
        data: cornPrices,
        label: {
          show: true,
          color: "#000", // 设置柱条尾部数据的字体颜色为白色
          position: "top",
        },
        itemStyle: {
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: "#F59045",
              },
              {
                offset: 1,
                color: "#FF5500",
              },
            ],
          },
        },
      },
    ],
    // orient: "horizontal", // 新增这一行，设置图表方向为横向
  },
  myChart: null,
});
onMounted(() => {
  state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.dataList);
  data.myChart = echarts.init(document.getElementById("myChart"), null, {
    height: "550px",
  });
  yAxisDate.value = state.dataList.map((item) => item.nf);
  pigPrices.value = state.dataList.map((item) => item.zczyyszlys);
  cornPrices.value = state.dataList.map((item) => item.zchs);
  // pigcorn.value = state.dataList.map((item) => item.zlbj);
  setEchartsOptions();
});
// 设置Echarts的配置项
const setEchartsOptions = () => {
  data.myChart.setOption(data.option);
};
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
</style>