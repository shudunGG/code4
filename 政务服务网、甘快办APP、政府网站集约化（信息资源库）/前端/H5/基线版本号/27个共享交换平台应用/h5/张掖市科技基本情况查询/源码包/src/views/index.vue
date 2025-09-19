<template>
  <div class="app">
    <div class="content">
      <div class="switch-tab">
        <div class="tab-item" @click="tabClick(item, index)"
          :class="state.currentTabIndex == item.value ? 'tab-item-active' : ''" v-for="(item, index) in state.tabList"
          :key="index">
          {{ item.text }}
        </div>
      </div>
      <div id="myChart"></div>
      <!-- <div class="data-total">
        <div class="color-box"></div>
        <div class="data1">占人口比重{{ state.tabList[state.currentTabIndex]?.zzrkbz }}（%）</div>
        <div class="data1" style="margin-left: 10px;">全市体育人口：{{ state.tabList[state.currentTabIndex]?.qstyrk }}万人</div>
      </div>
      <div class="container bgi1">
        <img src="../assets/images/icon1.png" alt="">
        <div
          style="display: flex; flex-direction: column;justify-content: space-between;margin-left: 12px;height: 50px;">
          <div class="text1 noraml-text" style="">市、县区全民健身体育比赛活动（次）</div>
          <div class="text2 noraml-text">{{ state.tabList[state.currentTabIndex]?.sxqqmjstybshd }}</div>
        </div>
      </div>
      <div class="container bgi2">
        <img src="../assets/images/icon2.png" alt="">
        <div
          style="display: flex; flex-direction: column;justify-content: space-between;margin-left: 12px;height: 50px;">
          <div class="text3 noraml-text" style="">参加人数（万人次）</div>
          <div class="text2 noraml-text">{{ state.tabList[state.currentTabIndex]?.cjrs }}</div>
        </div>
      </div> -->
    </div>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script setup>
import * as echarts from 'echarts';
import { useRouter } from "vue-router";
import { reactive, onMounted } from "vue";
import { useStore } from "../store/index.js";
import { getUserInfo } from "../tools/getUserInfo";
import { requestWithSM2 } from "../tools/request.js";
import { showToast, showDialog, showLoadingToast } from "vant";
import tools from "../tools/tool.js";

onMounted(() => {
  state.myChart = echarts.init(document.getElementById('myChart'), null, {
    height: 300
  });
  getList()
  window.addEventListener('resize', ()=>{
    console.log('resize');
    location.reload()
 });
})


const state = reactive({
  options: {
    // tooltip: {
    //   trigger: 'item'
    // },
    xAxis: {
      // data:['高等学校','中职学校','普通中学','小学','幼儿园'],
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
      // name: '单位（人）',
      min:0,
      max: function(value) {
        console.log('value',value,typeof value.max)
        let interger = Math.ceil(value.max)
        let length = String(interger).length
        let Bit = parseInt((value.max / Math.pow(10,length-1))) + 1
        return Bit * Math.pow(10,length - 1)
      },
      scale: true,
      maxInterval: 10000,
      minInterval: 1,
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
        barWidth: 15,
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
    {text: '各类专业技术人员',value: 'glzyjsry'},
    {text: '科研机构',value: 'kyjg'},
    {text: '科学技术支出',value: 'kxjszc'},
    {text: '省、市级以上科研成果',value: 'ssjyskjcg'},
  ],
  dataList: [],
  currentTabIndex: 'glzyjsry',
  publicKey: '04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b',
  privateKey: '00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa'
});
const router = useRouter();
const store = useStore();

const tabClick = (i, index) => {
  console.log('i',i)
  state.currentTabIndex = i.value
  setEchartsOptions()
}
const getUser = async () => {
  const res = await getUserInfo();
  console.log(res, 'getUserInfo');

  const userInfo = {
    userId: res.data.userId,
    tokenNo: res.data.tokenNo,
  };
  obj.userInfo = userInfo;
  store.userInfo = JSON.stringify(userInfo);
  if (res.data.userType != 10) {
    showDialog({
      title: "提示",
      message: "该业务仅限个人用户办理",
      confirmButtonText: "确认",
    }).then(() => {
      // on close
      lightAppJssdk.navigation.close({
        success: function (data) {
          return data;
        },
        fail: function (data) {
          return data;
        },
      });
    });
  }
};

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
  let arr = [],arr1 = []
  state.dataList.forEach(item => {
    arr.push(item[state.currentTabIndex])
    arr1.push(item.nf)
  })
  // const item = state.tabList.find(el => el.nf == state.currentTabIndex)
  const obj = {
    'glzyjsry' : '人',
    'kyjg' : '个',
    'kxjszc' : '万元',
    'ssjyskjcg' : '个',
  }
  state.options.series[0].data  = arr
  state.options.xAxis.data  = arr1
  state.options.yAxis.name = `单位（${obj[state.currentTabIndex]}）`
  console.log('state.options',state.options)
  console.log('state.myChart',state.myChart)
  state.myChart.setOption(state.options)
}

const getList = async () => {
  const params = {
    params: tools.sm2Encrypt1(
      JSON.stringify({
        normGuid: "3317f9aa-5895-42c4-96d9-ff2bc635b1cf",
        fieldList: []
      }), '0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b'
    ),
  };
  const header = {
    'client_key': 1731373215
  }
  const toast1 = showLoadingToast({
    message: "加载中...",
    forbidClick: true,
    loadingType: "spinner",
    duration: 0,
  });
  const res = await requestWithSM2(
    "zyjtyyjkrzoeeih",
    "zyjtyyjk",
    params,
    header
  );
  toast1.close();
  if (res.code === "200" && res.success && res.data) {
    let data = tools.sm2Decrypt1(res.data, '3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b');
    let resultData = JSON.parse(data);
    console.log(resultData);
    if (resultData.status.code == 1) {
      state.dataList = resultData.custom.result.cockpitData
      setEchartsOptions()
    }
  } else {
    toast1.close();
    showToast(res.message);
  }
};


</script>
<style scoped lang="less">
.app {
  min-height: 100vh;
  overflow-x: hidden;
  background: #f6f6f8;

  .content {
    margin-top: 12px;
    background-color: #fff;
    box-sizing: border-box;
    padding: 16px 12px 12px;
    // height: 500px;

    .switch-tab {
      display: flex;
      // align-items: ;
      justify-content: space-between;
      flex-wrap: wrap;
      // height: 101px;
      border-radius: 4px;
      background: #f6f7f9;
      padding: 8px;
      box-sizing: border-box;
      :nth-child(1){
        margin-bottom: 12px;
      }
      :nth-child(2){
        margin-bottom: 12px;
      }
      .tab-item {
        // width: 161.5px;
        width: calc((100% - 12px) / 2);
        height: 36.5px;
        line-height: 35px;
        position: relative;
        font-size: 15px;
        color: #000000;
        text-align: center;
        font-weight: 700;
        background: #fff;
        border: 0.5px solid #cbcbcb;
        box-sizing: border-box;
      }

      .tab-item-active {
        // background: #fff;
        color: #3266C2;
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
      height: 300px;
      width: 100%;
      // width: 375px;
      // width: 400px;
      // height: 180px;
      // margin: 20px auto 12px;
    }

    .data-total {
      display: flex;
      align-items: center;
      height: 22px;
      margin-bottom: 25px;

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
    .bgi1{
      background-image: url('../assets/images/bgi2.png');
    }
    .bgi2{
      background-image: url('../assets/images/bgi1.png');
    }
    .container {
      height: 74px;
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
        color: #2375EA;
      }

      .text2 {
        color: #000000;
      }

      .text3 {
        color: #FA8F42;
      }
    }
  }

  .footer{
    width: 100%;
    height: 147.5px;
    background-image: url(../assets/images/footer.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: fixed;
    bottom: 0;
    left: 0;
    .footer-content{
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>
