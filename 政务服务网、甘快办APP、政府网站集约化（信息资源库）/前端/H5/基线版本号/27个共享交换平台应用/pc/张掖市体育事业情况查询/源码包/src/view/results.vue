<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="content">
      <div class="switch-tab">
        <div
          class="tab-item"
          @click="tabClick(item, index)"
          :class="state.currentTabIndex == index ? 'tab-item-active' : ''"
          v-for="(item, index) in state.tabList"
          :key="index"
        >
          {{ item.nf }}
        </div>
      </div>
      <div id="myChart"></div>
      <div class="data-total">
        <div class="color-box"></div>
        <div class="data1">
          占人口比重{{ state.tabList[state.currentTabIndex]?.zzrkbz }}（%）
        </div>
        <div class="data1" style="margin-left: 10px">
          全市体育人口：{{ state.tabList[state.currentTabIndex]?.qstyrk }}万人
        </div>
      </div>
      <div
        style="
          display: flex;
          
          justify-content: space-between;
          margin-left: 12px;
          height: 50px;
        "
      >
        <div class="container bgi1">
          <img src="../assets/images/icon1.png" alt="" style="height: 66px;
              width: 66px" />
          <div
            style="
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              margin-left: 12px;
              height: 50px;
              width: 420px;
            "
          >
            <div class="text1 noraml-text" style="">
              市、县区全民健身体育比赛活动（次）
            </div>
            <div class="text2 noraml-text">
              {{ state.tabList[state.currentTabIndex]?.sxqqmjstybshd }}
            </div>
          </div>
        </div>
        <div class="container bgi2">
          <img src="../assets/images/icon2.png" alt="" style="height: 66px;
              width: 66px" />
          <div
            style="
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              margin-left: 12px;
              height: 50px;
              width: 420px;
            "
          >
            <div class="text3 noraml-text" style="">参加人数（万人次）</div>
            <div class="text2 noraml-text">
              {{ state.tabList[state.currentTabIndex]?.cjrs }}
            </div>
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
  currentTabIndex: 0,
  options: {
    // tooltip: {
    //   trigger: 'item'
    // },
    
    series: [
      {
        type: "pie",
        color: ["#FA8F42", "#2375EA"],
        labelLine: {
          normal: {
            length: 102,
          },
        },
        data: [
          // { value: 1048, name: 'Search Engine' },
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  },
  myChart: null,
});
const tabClick = (i, index) => {
  state.currentTabIndex = index;
  setEchartsOptions();
};
onMounted(() => {
  state.tabList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.tabList);
  state.myChart = echarts.init(document.getElementById("myChart"), null, {
    height: "350px",
  });
  // yAxisDate.value = state.tabList.map((item) => item.rq);
  // pigPrices.value = state.tabList.map((item) => item.szccjg);
  // cornPrices.value = state.tabList.map((item) => item.ympfjg);
  // pigcorn.value = state.tabList.map((item) => item.zlbj);
  setEchartsOptions();
});
const setEchartsOptions = () => {
  state.options.series[0].data[0] = {
    name: state.tabList[state.currentTabIndex].zzrkbz,
    // name: '占人口比重（%）',
    // value: state.tabList[state.currentTabIndex].zzrkbz
    value: state.tabList[state.currentTabIndex].zzrkbz,
  };
  state.options.series[0].data[1] = {
    name: (100 - state.tabList[state.currentTabIndex].zzrkbz).toFixed(2),
    value: (100 - state.tabList[state.currentTabIndex].zzrkbz).toFixed(2),
    barWidth: 10,
    label: {
      show: false,
      // textStyle: {
      //   fontSize: 9
      // }
    },
  };

  state.myChart.setOption(state.options);
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
}
</style>