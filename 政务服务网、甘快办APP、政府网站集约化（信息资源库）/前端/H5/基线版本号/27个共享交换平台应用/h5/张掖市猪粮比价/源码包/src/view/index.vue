<template>
  <div class="container">
    <!-- 页面主要内容 -->
    <section class="content">
      <div id="myChart"></div>
      <div class="box">
        <div class="item" v-for="(item, index) in pigcorn" :key="index">猪粮比价：{{ item }}</div>
      </div>
    </section>
    <!-- 页面底部内容 -->
    <footer class="footer">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </footer>
  </div>
</template>

<script setup>
import { getUserInfo } from '@/api/getUserInfo'
import { useUserStore } from '@/store/index'
import { onMounted, onUnmounted, reactive } from 'vue';
import { serveAPI } from '@/api/serve'
import { showLoadingToast, showToast, closeToast } from 'vant'
import tools from '@/tools/tool'
import * as echarts from 'echarts';
// 创建响应式数据对象，用于存储用户信息

const pigPrices = ref([]); // 生猪价格
const cornPrices = ref([]); // 玉米价格
const yAxisDate = ref([]);//y轴数据
const pigcorn = ref([]);//猪粮比价
const data = reactive({
  option: {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      orient: "vertical",
      itemGap: 12,
      left: 75
    },
    grid: {
      left: '2.5%',
      right: '2.5%',
      containLabel: true
    },
    yAxis: {
      data: yAxisDate,
      axisLabel: {
        width: 38,
        overflow: 'break', // 防止标签过长时显示不全
        fontSize: 12,  // 减小文字大小
        color: '#999999',
      },
    },
    xAxis: {
      show: false
    },
    series: [
      {
        name: '生猪（出场价格）单位：元／50公斤',
        type: "bar",
        data: pigPrices,
        label: {
          show: true,
          color: 'white'  // 设置柱条尾部数据的字体颜色为白色
        },
        // 设置柱状图的样式
        itemStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 1,
            colorStops: [
              {
                offset: 1,
                color: '#41be94'
              },
              {
                offset: 0,
                color: '#247cfa'
              }
            ]
          }
        },
      },
      {
        name: '玉米（批发价格）单位：元／50公斤',
        type: "bar",
        data: cornPrices,
        label: {
          show: true,
          color: 'white'  // 设置柱条尾部数据的字体颜色为白色
        },
        itemStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 1,
            colorStops: [
              {
                offset: 1,
                color: '#F59045'
              },
              {
                offset: 0,
                color: '#FF5500'
              }
            ]
          }
        },
      }],
    orient: 'horizontal' // 新增这一行，设置图表方向为横向
  },
  myChart: null,

})

onMounted(() => {
  data.myChart = echarts.init(document.getElementById('myChart'), null, {
    height: '550px'
  });
  setEchartsOptions()
})
// 设置Echarts的配置项
const setEchartsOptions = () => {
  data.myChart.setOption(data.option)
}

// 获取用户信息的函数
const getUserInformation = () => {
  showLoadingToast({
    duration: 0,
    message: '加载中...',
    forbidClick: true,
  });
  getUserInfo().then(async res => {
    if (res.code == '6000' && res.success) {
      let userInfo = res.data
      useUserStore().setUserInfo(userInfo)
      console.log('读取存储在pinia中的userInfo', useUserStore().userInfo)
    } else {
      showToast('当前未登录，请先登录')
      lightAppJssdk.navigation.close()
    }
  })
}
// getUserInformation()// 获取用户信息


// 查询数据
const queryList = () => {
  showLoadingToast({
    duration: 0,
    message: '加载中...',
    forbidClick: true,
  });
  let params = {
    params: tools.encryptSM2(JSON.stringify({
      normGuid: "82b311be-6c6d-4313-8461-2b20fe70c4ab",
      fieldList: []
    }))
  }
  let header = {
    'client_key': 1731373215
  }

  serveAPI.queryListHeaders("zyjtyyjkrzoeeih", "zyjtyyjk", params, header).then(res => {
    console.log(res, '查询数据')
    if (res.code == 200 && res.success && res.data) {
      closeToast();
      let result = JSON.parse(tools.decryptSM2(res.data))
      console.log(result.custom.result.cockpitData, '解密后的数据')
      // y轴数据
      yAxisDate.value = result.custom.result.cockpitData.map(item => item.rq);
      pigPrices.value = result.custom.result.cockpitData.map(item => item.szccjg);
      cornPrices.value = result.custom.result.cockpitData.map(item => item.ympfjg);
      pigcorn.value = result.custom.result.cockpitData.map(item => item.zlbj);
      setEchartsOptions()
    } else {
      closeToast();
      showToast({
        message: res.message || '查询失败！'
      });
    }
  }).catch(() => {
    closeToast();
    showToast({
      message: '网络异常，请稍后重试！'
    })
  })

}
queryList()



</script>
<style scoped lang="less">
@import '@/styles/index.css';
</style>
