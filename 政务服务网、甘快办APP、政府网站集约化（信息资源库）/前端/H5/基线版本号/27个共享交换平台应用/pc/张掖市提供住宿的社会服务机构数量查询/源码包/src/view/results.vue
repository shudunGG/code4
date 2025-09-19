<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="content">
      <div class="switch-tab">
        <div
          class="tab-item"
          @click="tabClick(item, index)"
          :class="state.currentTabIndex == index ? 'tab-item-active' : ''"
          v-for="(item, index) in state.tableData"
          :key="index"
        >
          {{ item.dw }}
        </div>
      </div>
      <div id="myChart"></div>
      <div class="footer-box">
        <div class="footer-title">合计</div>
        <div class="footer-content">
          <div class="footer-item">
            <div class="label">单位数(个)</div>
            <div class="value value1">{{ state.totalObj?.dws || 0 }}</div>
          </div>
          <div class="footer-item">
            <div class="label">年末床位数(张)</div>
            <div class="value value2">{{ state.totalObj?.nmcws || 0 }}</div>
          </div>
          <div class="footer-item">
            <div class="label">年末在院人数(人)</div>
            <div class="value value3">{{ state.totalObj?.nmzyrs || 0 }}</div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { toRefs, onBeforeMount, reactive, onMounted, watch } from "vue";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  tabList: {},
  totalObj: {},
  tableData: {},
  currentTabIndex: 0,
  // options: {
  //   // tooltip: {
  //   //   trigger: 'item'
  //   // },
  //   // legend: {
  //   //   data: ["Allocated Budget", "Actual Spending"],
  //   // },
  //   radar: {
  //     // shape: 'circle',
  //     indicator: [
  //       { name: "单位数（个）", max: 10 },
  //       { name: "年末在院人数（人）", max: 1200 },
  //       { name: "年末床位数（张）", max: 1000 },
  //     ],
  //   },
  //   series: [
  //     {
  //       type: "radar",
  //       data: [
  //         {
  //           value: [10, 1000, 800],
  //           name: "Allocated Budget",
  //         },
  //         // {
  //         //   value: [500, 1400, 2800],
  //         //   name: "Actual Spending",
  //         // },
  //       ],
  //     },
  //   ],
  // },
  myChart: null,
});
const tabClick = (i, index) => {
  state.currentTabIndex = index;
  setEchartsOptions();
};
const setOption = (value) => {
  const options = {
    radar: {
      // shape: 'circle',
      indicator: [
        { name: "单位数（个）", max: 10 },
        { name: "年末在院人数（人）", max: 1200 },
        { name: "年末床位数（张）", max: 1500 },
      ],
    },
    series: [
      {
        type: "radar",
        label: {
          show: true,
          color: "#000", // 设置柱条尾部数据的字体颜色为白色
          position: "top",
        },
        areaStyle: {
          color: new echarts.graphic.RadialGradient(0.1, 0.6, 1, [
            // {
            //   color: 'rgba(255, 145, 124, 0.1)',
            //   offset: 0
            // },
            {
              color: "#14B8A6",
              offset: 1,
            },
          ]),
        },
        data: [
          {
            value: value,
            name: "Allocated Budget",
          },
          // {
          //   value: [500, 1400, 2800],
          //   name: "Actual Spending",
          // },
        ],
      },
    ],
  };
  options && state.myChart.setOption(options);
};
onMounted(() => {
  state.tabList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.tabList);
  state.totalObj = state.tabList[0];
  state.tableData = state.tabList.slice(1);
  state.myChart = echarts.init(document.getElementById("myChart"), null, {
    height: "450px",
  });
  setEchartsOptions();
});
const setEchartsOptions = () => {
  let value = [
    state.tableData[state.currentTabIndex].dws,
    state.tableData[state.currentTabIndex].nmzyrs,
    state.tableData[state.currentTabIndex].nmcws,
  ];
  setOption(value);
};
</script>
<style scoped lang="less">
.result-container table {
  margin-bottom: 0.2rem;
}
.result-container {
  height: 750px;
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
.content {
  margin-top: 12px;
  background-color: #fff;
  box-sizing: border-box;
  padding: 16px 12px 12px;
  height: 500px;

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

  #myChart {
    // width: 400px;
    height: 380px;
    display: flex;
    justify-content: center;
    // margin: 20px auto 12px;
  }

  .data-total {
    display: flex;
    align-items: center;
    height: 22px;
    margin-bottom: 25px;
    justify-content: center;

    .color-box {
      width: 12.5px;
      height: 7px;
      background: #fa8f42;
      border: 0 solid #fff;
      margin: 0 8px;
    }

    .data1 {
      font-size: 14px;
      color: #333;
      white-space: nowrap;
      // letter-spacing: 1px;
    }
  }
  .bgi1 {
    background-image: url("../assets/images/bgi2.png");
  }
  .bgi2 {
    background-image: url("../assets/images/bgi1.png");
  }
  .container {
    height: 128px;
    border-radius: 4px;
    margin-bottom: 16px;
    background-repeat: no-repeat;
    background-size: cover;
    padding: 12px;
    box-sizing: border-box;
    display: flex;
    align-items: center;

    .noraml-text {
      font-size: 15px;
      font-weight: 500;
    }

    .text1 {
      color: #2375ea;
    }

    .text2 {
      color: #000000;
    }

    .text3 {
      color: #fa8f42;
    }
  }
  .footer-box {
      margin-top: 16px;
      position: relative;
      height: 108px;
    }

    .footer-title {
      width: 104px;
      height: 50px;
      line-height: 50px;
      border-radius: 4px;
      background: #2375ea;
      font-weight: 700;
      font-size: 15px;
      text-align: center;
      color: #fff;
      position: relative;
      z-index: 9;
    }

    .footer-content {
      position: absolute;
      width: 100%;
      top: 19px;
      height: 88px;
      border-radius: 4px;
      background: #eef5ff;
      display: flex;
      justify-content: space-around;
      box-sizing: border-box;
      padding: 0 12px;

      .footer-item {
        display: flex;
        flex-direction: column;
        justify-content: center;
        text-align: center;

        .label {
          font-size: 15px;
          color: #000;
          font-weight: 500;
          margin-bottom: 4px;
          margin-top: 12px;
        }

        .value {
          font-size: 18px;
          font-weight: 900;
        }

        .value1 {
          color: #fa8f42;
        }

        .value2 {
          color: #3b82f6;
        }

        .value3 {
          color: #6366f1;
        }
      }
    }
}
</style>