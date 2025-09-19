<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container">
    <div class="wrap">
      <div class="title">
        <div class="line"></div>
        <div>全市生产总值趋势分析</div>
      </div>
      <div class="tab">
        <div v-for="(item, index) in resArr">
          <div
            :class="['tab-item', isSelect == index ? 'selected' : '']"
            @click="change(index)"
          >
            {{ item.name }}
          </div>
        </div>
      </div>
      <div
        ref="chartRef"
        style="width: 100%; height: 400px; background: #fff"
      ></div>
    </div>
    <div class="footer">
      <div>本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, onUnmounted } from "vue";
import appApi from "../api/index";
import sm2 from "../tools/tool";
import * as echarts from "echarts";
const chartRef = ref(null);
const chartInstance = ref(null);
const isSelect = ref("");
const resArr = ref([]);
const slArr = ref([]); //数量
const sjArr = ref([]); //时间
const unit = ref(""); //单位
onMounted(() => {
  getData();
  window.addEventListener("resize", resizeChart);
});
onUnmounted(() => {
  window.removeEventListener("resize", resizeChart);
  if (chartInstance.value) {
    chartInstance.value.dispose();
  }
});
// 监听窗口大小变化
const resizeChart = () => {
  if (chartInstance.value) {
    const initialData = {
      sjArr: sjArr.value, //时间
      slArr: slArr.value, //数量
      unit: unit.value,
    };
    updateChartData(initialData);
    chartInstance.value.resize();
  }
};
const change = (index) => {
  isSelect.value = index;
  const data = resArr.value[index].items;
  slArr.value = data.map((item) => Number(item.sl));
  sjArr.value = data.map((item) => item.sj);
  unit.value = data[0].unit;
  const initialData = {
    sjArr: sjArr.value, //时间
    slArr: slArr.value, //数量
    unit: unit.value,
  };
  updateChartData(initialData);
};
const getData = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: "a5f6cea6-02eb-4c47-b3e8-a8be19d40bef",
        fieldList: [],
      })
    ),
  };
  appApi.getList("zyjtyyjk", "zyjtyyjkrzoeeih", param).then((res) => {
    const data = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData;
    const groupedData = {};
    data.forEach((item) => {
      const { lx } = item;
      if (!groupedData[lx]) {
        groupedData[lx] = [];
      }
      groupedData[lx].push(item);
    });

    const result = Object.keys(groupedData).map((key) => ({
      name: key,
      items: groupedData[key],
    }));
    resArr.value = result;
    const data1 = resArr.value[0].items;
    slArr.value = data1.map((item) => Number(item.sl));
    sjArr.value = data1.map((item) => item.sj);
    unit.value = data1[0].unit;
    if (chartRef.value) {
      // 初始化图表，设置配置项
      chartInstance.value = echarts.init(chartRef.value);
      const initialData = {
        sjArr: sjArr.value, //时间
        slArr: slArr.value, //数量
        unit: unit.value,
      };
      updateChartData(initialData);
    }
  });
};
// 监听数据变化，更新图表
const updateChartData = (newData) => {
  if (chartInstance.value) {
    chartInstance.value.setOption({
      grid: {
        left: "52", // 增加左侧边距
      },
      xAxis: {
        type: "category",
        data: newData.sjArr,
        axisTick: {
          show: false,
        },
      },
      yAxis: {
        type: "value",
        name: `单位（${newData.unit}）`,
        position: "left",
      },
      series: [
        {
          name: "人数",
          type: "line",
          data: newData.slArr,
          label: {
            show: true, // 是否显示标签
            position: "top",
          },
          symbol: "circle", // 设置数据点为实心圆
          symbolSize: 6, // 调整点的大小
          areaStyle: {
            color: new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1, // 四个参数分别为渐变起点的x, y和渐变终点的x, y（0,0到0,1表示水平渐变）
              [
                { offset: 0, color: "#ECF1F9" }, // 起点颜色
                { offset: 1, color: "#fff" }, // 终点颜色
              ]
            ),
          },
        },
      ],
    });
  }
};
</script>

<style lang="less" scoped>
* {
  font-size: 16px;
  box-sizing: border-box;
}
.container {
  background-color: #f5f5f5;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .wrap {
    background: #fff;
    .title {
      display: flex;
      align-items: center;
      justify-content: flex-start;
      padding: 12px;
      .line {
        width: 3.5px;
        height: 15px;
        border-radius: 1.75px;
        background: #3266c2;
        margin-right: 8px;
      }
      div {
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 18px;
        text-align: left;
        color: #000;
      }
    }
    .tab {
      margin: 0px 12px;
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-radius: 2px;
      background: #f6f7f9;
      padding: 5px;
      .tab-item {
        background: #f6f7f9;
        border: 0.5px solid transparent;
        flex: 1;
        text-align: center;
        padding: 8px;
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 15px;
        text-align: center;
        color: #000;
      }
      .selected {
        background: #fff;
        border: 0.5px solid #cbcbcb;
        color: #3266c2;
      }
    }
  }
  .footer {
    width: 100%;
    height: 58.5px;
    background-image: url("../assets/img/bg.png");
    background-size: 100% 100%;
    div {
      margin-top: 21.5px;
      font-family: "PingFang SC Medium";
      font-weight: 500;
      font-size: 15px;
      text-align: center;
      color: #999;
    }
  }
}
</style>
