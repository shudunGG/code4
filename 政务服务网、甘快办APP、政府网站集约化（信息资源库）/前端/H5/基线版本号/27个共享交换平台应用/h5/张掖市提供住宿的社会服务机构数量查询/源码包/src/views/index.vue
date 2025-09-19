<template>
  <div class="app">
    <div class="content">
      <div class="data-total j-sb">
        <div class="d-flex a-center">
          <div class="color-box color-box1"></div>
          <div class="data1">单位数（个）</div>
        </div>
        <div class="d-flex a-center">
          <div class="color-box color-box2"></div>
          <div class="data1">年末床位数（张）</div>
        </div>
        <div class="d-flex a-center">
          <div class="color-box color-box3"></div>
          <div class="data1">年末在院人数（人）</div>
        </div>
      </div>
      <div id="myChart"></div>
      <div class="footer-box">
        <div class="footer-title">合计</div>
        <div class="footer-content">
          <div class="footer-item">
            <div class="label">单位数(个)
            </div>
            <div class="value value1">{{ state.totalObj?.dws || 0 }}</div>
          </div>
          <div class="footer-item">
            <div class="label">年末床位数(张)
            </div>
            <div class="value value2">{{ state.totalObj?.nmcws || 0 }}</div>
          </div>
          <div class="footer-item">
            <div class="label">年末在院人数(人)
            </div>
            <div class="value value3">{{ state.totalObj?.nmzyrs || 0 }}</div>
          </div>
        </div>
      </div>

    </div>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script setup>
import * as echarts from 'echarts';
import { useRouter } from "vue-router";
import { reactive, onMounted, watch } from "vue";
import { useStore } from "../store/index.js";
import { getUserInfo } from "../tools/getUserInfo";
import { requestWithSM2 } from "../tools/request.js";
import { showToast, showDialog, showLoadingToast } from "vant";
import tools from "../tools/tool.js";

onMounted(() => {
  
  state.myChart = echarts.init(document.getElementById('myChart'), null, {
    height: 320
  });
  getList()
  window.addEventListener('resize', ()=>{
    console.log('resize');
    
    location.reload()
  });
})



const state = reactive({
  totalObj: {},
  myChart: null,
  tableData: [],
  options: {

    xAxis: {
      axisLine: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      data: [],
      axisLabel: {
        color: '#999'
      }
    },
    yAxis: {
      show: false
    },
    color: [
      '#fa8f42',
      '#3B82F6',
      '#6366F1',

    ],
    // label: {
    //   show: true,
    //   position: 'top',
    //   textStyle: {
    //     fontSize: 1
    //   }
    // },
    grid: {
      left: 0,
      right: 0,
      top: 0,
      bottom: 20

    },
    series: [
      {
        type: 'bar',
        data: [],
        barWidth: 10,
        label: {
          show: true,
          position: 'top',
          textStyle: {
            fontSize: 9
          }
        },

      },
      {
        type: 'bar',
        data: [],
        barWidth: 10,
        label: {
          show: true,
          position: 'top',
          textStyle: {
            fontSize: 9
          }
        },
      },
      {
        type: 'bar',
        data: [],
        barWidth: 10,
        label: {
          show: true,
          position: 'top',
          textStyle: {
            fontSize: 9
          }
        },
      }
    ]
  },
  publicKey: '04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b',
  privateKey: '00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa'
});
const router = useRouter();
const store = useStore();

const setEchartsOptions = () => {
  let arr1 = [], arr2 = [], arr3 = []

  state.tableData.forEach(element => {
    state.options.xAxis.data.push(element.dw)
    arr1.push(element.dws)
    arr2.push(element.nmcws)
    arr3.push(element.nmzyrs)
  });

  state.options.series[0].data = arr1
  state.options.series[1].data = arr2
  state.options.series[2].data = arr3


  state.myChart.setOption(state.options)
}

const getList = async () => {
  const params = {
    params: tools.sm2Encrypt1(
      JSON.stringify({
        normGuid: "2c83b04e-b95e-4de1-b0ec-e7f36c8c2b49",
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
      state.totalObj = resultData.custom.result.cockpitData[0]
      state.tableData = resultData.custom.result.cockpitData.slice(1)
      console.log(state.totalObj, 'state.totalObj');
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
overflow-x: hidden;

  .d-flex {
    display: flex;
  }

  .a-center {
    align-items: center;
  }

  .j-sb {
    justify-content: space-between;
  }

  min-height: 100vh;
  background: #f6f6f8;

  .content {
    margin-top: 12px;
    background-color: #fff;
    box-sizing: border-box;
    padding: 16px 12px 12px;
    #myChart{
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      canvas{
        width: 100%;
      }
    }

    .data-total {
      display: flex;
      align-items: center;
      height: 18px;
      margin-bottom: 12px;

      .color-box {
        width: 12.5px;
        height: 7px;

        border: 0 solid #fff;
        margin-right: 5px;
      }

      .color-box1 {
        background-color: #fa8f42;
      }

      .color-box2 {
        background-color: #3B82F6;
      }

      .color-box3 {
        background-color: #6366F1;
      }

      .data1 {
        font-size: 12px;
        color: #333;
        white-space: nowrap;
        // letter-spacing: 1px;
      }
    }

    .footer-box {
      margin-top: 16px;
      position: relative;
      height: 108px;

    }

    .footer-title {
      width: 54px;
      height: 37px;
      line-height: 37px;
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
      justify-content: space-between;
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
          color: #FA8F42
        }

        .value2 {
          color: #3B82F6
        }

        .value3 {
          color: #6366F1
        }
      }
    }
  }
  .footer{
    width: 100%;
    height: 147.5px;
    background-image: url(../assets/images/bgc.png);
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
