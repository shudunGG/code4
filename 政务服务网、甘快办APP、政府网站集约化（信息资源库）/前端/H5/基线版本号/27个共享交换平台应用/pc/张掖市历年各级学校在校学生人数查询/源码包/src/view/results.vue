<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div class="switch-tab">
        <div
          class="tab-item"
          @click="tabClick(item, index)"
          :class="state.currentTabIndex == item.nf ? 'tab-item-active' : ''"
          v-for="(item, index) in state.tabList"
          :key="index"
        >
          {{ item.nf }}
        </div>
      </div>
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
  options: {
    // tooltip: {
    //   trigger: 'item'
    // },
    xAxis: {
      data:['高等学校','中职学校','普通中学','小学','幼儿园'],
      // data:['高等学校','中','普','小学','幼'],
      axisLine: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        color: '#999',
        // show: true,
        interval: 0
      }
    },
    yAxis: {
      // show: false
      // min: function(value){

      // },
      name: '单位（所）',
      min:0,
      max: function(value) {
        console.log('value',value,typeof value.max)
        let interger = Math.ceil(value.max)
        let length = String(interger).length
        let Bit = parseInt((value.max / Math.pow(10,length-1))) + 1
        return Bit * Math.pow(10,length - 1)
      },
      scale: true,
      interval: 10000,
      axisLabel:{
        fontSize: 12,
        color: '#999999',
        margin: 0,
      }
    },
    grid: {
      left: '12%',
      right: 0,
      top: "15%",
      bottom: "15%",
    },
    series: [
      {
        type: 'bar',
        data: [],
        barWidth: 35,
        itemStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: '#247CFA' 
              },
              {
                offset: 1,
                color: '#41BE94'  
              },
            ]
          }
        },
        label: {
          show: true,
          position: 'top',
          textStyle: {
            fontSize: 12
          }
        },
      },
    ]
  },
  myChart: null,
  tabList: [
  ],
  currentTabIndex: 0,
});
const pigPrices = ref([]); // 生猪价格
const cornPrices = ref([]); // 玉米价格
const yAxisDate = ref([]); //y轴数据
const pigcorn = ref([]); //猪粮比价
onMounted(() => {
  state.tabList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.tabList);
  // state.tabList = resultData.custom.result.cockpitData;
  state.currentTabIndex = state.tabList[0].nf;
  state.myChart = echarts.init(document.getElementById("myChart"), null, {
    height: "550px",
  });
  // yAxisDate.value = state.tabList.map((item) => item.rq);
  // pigPrices.value = state.tabList.map((item) => item.szccjg);
  // cornPrices.value = state.tabList.map((item) => item.ympfjg);
  // pigcorn.value = state.tabList.map((item) => item.zlbj);
  setEchartsOptions();
});
const tabClick = (i, index) => {
  console.log('i',i)
  state.currentTabIndex = i.nf
  setEchartsOptions()
}
const setEchartsOptions = () => {
  // state.options.series[0].data[0] = {
  //   name: state.tabList[state.currentTabIndex].zzrkbz,
  //   // name: '占人口比重（%）',
  //   // value: state.tabList[state.currentTabIndex].zzrkbz
  //   value: state.tabList[state.currentTabIndex].zzrkbz
  // }
  // state.options.series[0].data[1] = {
  //   name: (100 - state.tabList[state.currentTabIndex].zzrkbz).toFixed(2),
  //   value: (100 - state.tabList[state.currentTabIndex].zzrkbz).toFixed(2),
  //   barWidth: 10,
  //   label: {
  //     show: false,
  //     // textStyle: {
  //     //   fontSize: 9
  //     // }
  //   },
  // }
  const item = state.tabList.find((el) => el.nf == state.currentTabIndex);
  state.options.series[0].data = [
    item.gdxx,
    item.zzxx,
    item.ptzx,
    item.xx,
    item.yey,
  ];
  // state.options.series[0].data = item
  // {
  //   '高等学校': item.gdxx,
  //   '中职学校': item.zzxx,
  //   '普通中学': item.ptzx,
  //   '小学': item.xx,
  //   '幼儿园': item.yey,
  // }
  console.log("state.options", state.options);
  console.log("state.myChart", state.myChart);
  state.myChart.setOption(state.options);
};
</script>
<style scoped lang="less">
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
    width: 150px;
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