<template>
  <div class="app">
    <div class="content">
      <div class="switch-tab">
        <div class="tab-item" @click="tabClick(item, index)"
          :class="state.currentTabIndex == item.nf ? 'tab-item-active' : ''" v-for="(item, index) in state.tabList"
          :key="index">
          {{ item.nf }}
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
  ],
  currentTabIndex: 0,
  publicKey: '04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b',
  privateKey: '00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa'
});
const router = useRouter();
const store = useStore();

const tabClick = (i, index) => {
  console.log('i',i)
  state.currentTabIndex = i.nf
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
  const item = state.tabList.find(el => el.nf == state.currentTabIndex)
  state.options.series[0].data  = [
     item.gdxx,item.zzxx,item.ptzx,item.xx,item.yey
  ]
  // state.options.series[0].data = item
  // {
  //   '高等学校': item.gdxx,
  //   '中职学校': item.zzxx,
  //   '普通中学': item.ptzx,
  //   '小学': item.xx,
  //   '幼儿园': item.yey,
  // }
  console.log('state.options',state.options)
  console.log('state.myChart',state.myChart)
  state.myChart.setOption(state.options)
}

const getList = async () => {
  const params = {
    params: tools.sm2Encrypt1(
      JSON.stringify({
        normGuid: "5b0ae4ac-23c7-4343-9bb4-1e4be4ecefd2",
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
      state.tabList = resultData.custom.result.cockpitData
      state.currentTabIndex = state.tabList[0].nf
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
      align-items: center;
      justify-content: space-between;
      height: 46px;
      border-radius: 4px;
      background: #f6f7f9;
      padding: 0 5px;
      box-sizing: border-box;

      .tab-item {
        width: 60px;
        height: 35px;
        line-height: 35px;
        position: relative;
        font-size: 15px;
        color: #000000;
        text-align: center;
        font-weight: 700;
      }

      .tab-item-active {
        background: #fff;
        color: #3266C2;
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
