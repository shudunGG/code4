<template>
  <div class="container-wrap">
    <div style="background: #ffffff; padding-top: 16px">
      <div class="buttoms">
        <button
          v-for="year in years"
          :key="year"
          @click="changeYear(year)"
          :class="{ highlight: year === selectedYear }"
        >
          {{ year }}
        </button>
      </div>

      <!-- ECharts 饼图容器 -->
      <div
        ref="pieChart"
        style="
          width: 100%;
          height: 330px;
          background: #ffffff;
          padding-bottom: 15px;
        "
      ></div>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script>
import tools from "../tools/tool.js";
import { showToast, showLoadingToast } from "vant";
import { request } from "../tools/request.js";
import { ref, toRefs, reactive, onBeforeMount, onBeforeUnmount } from "vue";
import * as echarts from "echarts"; // 引入 ECharts
export default {
  setup() {
    const state = reactive({
      key: "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b",
      key2: "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b",
      rawData: [],
      selectedYear: "2019",
    });
    // 声明响应式数据
    const pieChart = ref(null); // ECharts 容器的引用
    const pieData = ref([]); // 存储饼图数据
    const total = ref(0); // 存储合计数据
    const chartInstance = ref(null); // ECharts 实例
    const years = ref([]); // 存储所有年份
    const dataByYear = ref({}); // 存储按年份分组的数据
    onBeforeMount(() => {
      state.toast = showLoadingToast({
        message: "加载中...",
        forbidClick: true,
        loadingType: "spinner",
        duration: 0,
      });
      const params = {
        params: tools.sm2Encrypt(
          JSON.stringify({
            normGuid: "4c4e85ac-115c-49f6-8d2c-b6d39f4f26ee",
            fieldList: [],
          }),
          state.key
        ),
      };
      request({
        app_id: "zyjtyyjkrzoeeih",
        interface_id: "zyjtyyjk",
        biz_content: JSON.stringify(params),
      }).then(
        (res) => {
          state.toast.close();
          let resStart;
          resStart = JSON.parse(res);
          res = JSON.parse(tools.sm2Decrypt(resStart.data, state.key2));
          if (res.status.text === "请求成功" && res.custom.result) {
            state.rawData = res.custom.result.cockpitData;
            console.log(state.rawData, "1");
            state.rawData.forEach((item) => {
              if (!dataByYear.value[item.nf]) {
                dataByYear.value[item.nf] = item;
                years.value.push(item.nf); // 记录所有年份
              }
            });
            changeYear("2019");
          } else {
            showToast("暂无数据");
          }
        },
        () => {
          state.toast.close();
          showToast("查询失败，请重试!");
        }
      );
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
    const changeYear = (year) => {
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

    // 组件卸载时清除 ECharts 实例
    onBeforeUnmount(() => {
      if (chartInstance.value) {
        chartInstance.value.dispose();
      }
    });

    return {
      ...toRefs(state),
      pieChart,
      pieData,
      total,
      years,
      changeYear,
    };
  },
};
</script>
<style lang="less" scoped>
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
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f6f6f8;
  overflow: auto;
  .card-list-title {
    position: relative;
    padding-left: 11px;
  }
  .card-list-picker {
    padding-left: 11px;
  }
  .card-list-title:before {
    content: "*";
    color: red;
    position: absolute;
    left: 2px;
    bottom: -2px;
  }
  ::v-deep(.van-picker__toolbar) {
    border-bottom: 1px solid #e5e5e5;
  }
  ::v-deep(.van-picker__cancel) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker__confirm) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker-column__item) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }
  ::v-deep(.van-picker-column__item--selected) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }
  .top-bg {
    height: 140px;
    background: url("../assets/img/bg.png");
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
      padding: 8px 0;
      border-bottom: 1px solid #e8e8e8;
      input {
        width: 100%;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #999;
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
      color: #fff;
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
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/img/bgc.png);
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
      bottom: 7px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>
