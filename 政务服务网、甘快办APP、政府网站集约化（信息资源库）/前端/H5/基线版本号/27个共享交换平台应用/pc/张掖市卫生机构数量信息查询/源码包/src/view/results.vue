<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div>
      <div class="switch-tab">
        <div
          class="tab-item"
          @click="changeYear(item.nf, index)"
          :class="state.currentTabIndex == index ? 'tab-item-active' : ''"
          v-for="(item, index) in state.rawData"
          :key="index"
        >
          {{ item.nf }}
        </div>
      </div>
      <!-- ECharts 饼图容器 -->
      <div
        ref="pieChart"
        style="
          width: 100%;
          height: 450px;
          background: #ffffff;
          padding-bottom: 15px;
        "
      ></div>
    </div>
  </section>
</template>
<script setup>
import {
  toRefs,
  onBeforeMount,
  reactive,
  onMounted,
  watch,
  onBeforeUnmount,
} from "vue";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  dataList: {},
  rawData: [],
  selectedYear: "2019",
  currentTabIndex: 0,
});
const pieChart = ref(null); // ECharts 容器的引用
const pieData = ref([]); // 存储饼图数据
const total = ref(0); // 存储合计数据
const chartInstance = ref(null); // ECharts 实例
const years = ref([]); // 存储所有年份
const dataByYear = ref({}); // 存储按年份分组的数据
// const data = reactive({
//   option: {
//     tooltip: {
//       trigger: "axis",
//       axisPointer: {
//         type: "shadow",
//       },
//     },
//     legend: {
//       // orient: "vertical",
//       itemGap: 155,
//       left: 95,
//     },
//     grid: {
//       left: "2.5%",
//       right: "2.5%",
//       containLabel: true,
//     },
//     yAxis: {
//       // show: false,
//     },
//     xAxis: {
//       data: yAxisDate,
//       // show: false,
//       axisLabel: {
//         width: 68,
//         overflow: "break", // 防止标签过长时显示不全
//         fontSize: 12, // 减小文字大小
//         color: "#999999",
//       },
//     },
//     series: [
//       {
//         name: "生猪（出场价格）单位：元／50公斤",
//         type: "bar",
//         data: pigPrices,
//         label: {
//           show: true,
//           color: "#000", // 设置柱条尾部数据的字体颜色为白色
//           position: "top",
//         },
//         // 设置柱状图的样式
//         itemStyle: {
//           // color: "#247CF9", // 设置柱条的颜色为蓝色
//           color: {
//             type: "linear",
//             x: 0,
//             y: 0,
//             x2: 0,
//             y2: 1,
//             colorStops: [
//               {
//                 offset: 0,
//                 color: "#41BE94", // 0% 处的颜色
//               },
//               {
//                 offset: 1,
//                 color: "#247CFA", // 100% 处的颜色
//               },
//             ],
//           },
//         },
//       },
//       {
//         name: "玉米（批发价格）单位：元／50公斤",
//         type: "bar",
//         data: cornPrices,
//         label: {
//           show: true,
//           color: "#000", // 设置柱条尾部数据的字体颜色为白色
//           position: "top",
//         },
//         itemStyle: {
//           color: {
//             type: "linear",
//             x: 0,
//             y: 0,
//             x2: 0,
//             y2: 1,
//             colorStops: [
//               {
//                 offset: 0,
//                 color: "#F59045",
//               },
//               {
//                 offset: 1,
//                 color: "#FF5500",
//               },
//             ],
//           },
//         },
//       },
//     ],
//     // orient: "horizontal", // 新增这一行，设置图表方向为横向
//   },
//   myChart: null,
// });
onMounted(() => {
  state.rawData = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.rawData);
  state.rawData.forEach((item) => {
    if (!dataByYear.value[item.nf]) {
      dataByYear.value[item.nf] = item;
      years.value.push(item.nf); // 记录所有年份
    }
  });
  // state.pieChart = echarts.init(document.getElementById("pieChart"), null, {
  //   height: "450px",
  // });
  changeYear("2019", 0);
});
// 初始化图表
const initChart = () => {
  chartInstance.value = echarts.init(pieChart.value);
  updateChart();
};

// 更新图表
const updateChart = () => {
  const option = {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {d}%", // 显示占比
    },
    legend: {
      orient: "horizontal", // 使图例水平排列
      bottom: "0", // 将图例放置在页面底部
      left: "center", // 图例居中显示
      data: pieData.value.map((item) => item.name), // 显示图例
      selectedMode: false, // 禁用图例的点击交互功能
      itemGap: 75,
    },
    series: [
      {
        name: "占比",
        type: "pie",
        radius: ["50%", "70%"], // 调整外环半径范围// 设置外半径和内半径，实现环形饼图
        avoidLabelOverlap: false,
        label: {
          show: true,
          position: "outside", // 标签显示在外部
          formatter: "{c}", // 显示每个部分的名称和占比
        },
        data: pieData.value, // 使用当前数据
        silent: true,
      },
      {
        name: "",
        type: "pie",
        radius: ["40%", "50%"], // 设置内部圆形的半径
        center: ["50%", "50%"], // 饼图的中心位置
        label: {
          show: true,
          position: "center", // 将标签显示在中心
          formatter: `{b}\n {b|${total.value}}`, // 显示合计文本
          fontSize: 16, // 中心文本字体大小
          // fontWeight: "bold", // 中心文本字体加粗
          padding: [10, 0], // 控制文字上下间距
          rich: {
            b: {
              fontSize: 18,
              color: "#3B82F6", // 中心文本颜色
              lineHeight: 30, // 设置行高
            },
          },
        },
        silent: true,
        itemStyle: {
          // 取消背景色，保持透明
          color: "transparent",
          borderColor: "transparent",
          borderWidth: 0,
        },
        data: [{ name: "卫生机构合计", value: total.value }],
      },
    ],
  };

  chartInstance.value.setOption(option); // 更新图表
};

// 切换年份数据
const changeYear = (year,index) => {
  state.currentTabIndex = index;
  state.selectedYear = year;
  const selectedYearData = dataByYear.value[year];
  pieData.value = [
    {
      name: "医院",
      value: selectedYearData.yy,
      itemStyle: {
        color: "#1F66E4", // 红色
      },
    },
    {
      name: "卫生院",
      value: selectedYearData.wsy,
      itemStyle: {
        color: "#FD9137", // 红色
      },
    },
    {
      name: "社区卫生服务中心（站）",
      value: selectedYearData.sqwsfwzx,
      itemStyle: {
        color: "#F25424", // 红色
      },
    },
    {
      name: "村卫生室",
      value: selectedYearData.cwss,

      itemStyle: {
        color: "#09A321", // 红色
      },
    },
    {
      name: "疾控中心机构",
      value: selectedYearData.jkzxjg,
      itemStyle: {
        color: "#9398FF", // 红色
      },
    },
    {
      name: "妇女卫生机构",
      value: selectedYearData.fnwsjg,
      itemStyle: {
        color: "#1297cb", // 红色
      },
    },
  ];

  // 计算合计
  total.value = pieData.value.reduce(
    (sum, item) => sum + parseInt(item.value),
    0
  );
  initChart();
  updateChart(); // 更新图表
};
// // 设置Echarts的配置项
// const setEchartsOptions = () => {
//   data.myChart.setOption(data.option);
// };
</script>
<style scoped lang='less'>
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
.buttoms {
  height: 45px;
  border-radius: 4px;
  background: #f6f7f9;
  display: flex;
  justify-content: space-evenly;
  margin: 0 16px;

  button {
    background: #f6f7f9;
    font-family: "PingFang SC Bold";
    font-weight: 600;
    font-size: 15px;
    text-align: center;
    color: #000;
    margin: 8px 0px;
    padding: 0px 5px;
  }
  .highlight {
    background: #fff !important;
    border: 1px solid #cbcbcb;
    color: #3266c2 !important;
    border-radius: 5px;
  }
}
.switch-tab {
  display: flex;
  align-items: center;
  justify-content: space-around;
  height: 50px;
  border-radius: 4px;
  background: #f6f7f9;
  padding: 0 5px;
  box-sizing: border-box;

  .tab-item {
    width: 100px;
    height: 45px;
    line-height: 45px;
    position: relative;
    font-size: 15px;
    color: #000000;
    text-align: center;
    font-weight: 700;
    cursor: pointer;
  }

  .tab-item-active {
    background: #fff;
    color: #3266c2;
    border: 0.5px solid #cbcbcb;
  }

  // .tab-item::after {
  //   position: absolute;
  //   content: "";
  //   width: 200%;
  //   height: 200%;
  //   border: 1px solid #cbcbcb;
  //   transform: scale(0.5);
  //   transform-origin: 0 0;
  // }
}
</style>