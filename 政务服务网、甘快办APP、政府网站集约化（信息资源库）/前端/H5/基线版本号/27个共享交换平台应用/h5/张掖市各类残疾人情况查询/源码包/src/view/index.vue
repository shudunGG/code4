<template>
  <div class="container">
    <!-- 页面主要内容 -->
    <section class="content">
      <div id="myChart"></div>
      <div class="bottom">
        <div class="group">
          <div class="group_l">合计</div>
          <div class="group_r">
            {{ `${Year ? Year[0].nf : ''}年： ${Year ? Year[0].hj : ''}` }}
            <span> | </span>
            {{ `${Year ? Year[1].nf : ''}年： ${Year ? Year[1].hj : ''}` }}
          </div>
        </div>
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
import tools, { changFormate } from '@/tools/tool'
import * as echarts from 'echarts';

const aYear = ref([]); //A列表
const aYearName = ref('');//年份
const bYear = ref([]); //B列表
const bYearName = ref('');//年份
const Year = ref([{ nf: '0' }, { nf: '0' }]);   //数据

// 创建响应式数据对象，用于存储用户信息
const data = reactive({
  option: {
    tooltip: {},
    legend: {},
    grid: {
      left: '1',
      containLabel: true
    },
    xAxis: {
      data: ['视力残疾', '听力残疾', '智力残疾', '肢体残疾', '精神残疾', '综合残疾'],
      axisLabel: {
        interval: 0,
        width: 25,
        overflow: 'break', // 防止标签过长时显示不全
        fontSize: 12,  // 减小文字大小
        fontWeight: '700',
        color: '#000',
      }
    },
    yAxis: {
      interval: 2000,
      autoScale: true  // 开启自适应
    },
    series: [
      {
        name: aYearName,
        data: aYear,
        type: 'line',

        label: {
          show: true,
          color: '#247CF9'  // 设置柱条尾部数据的字体颜色为白色
        },
        itemStyle: {
          color: '#247CF9'  // 设置柱条的颜色为蓝色
        }
      },
      {
        name: bYearName,
        data: bYear,
        type: 'line',
        label: {
          show: true,
          color: '#FF5803'  // 设置柱条尾部数据的字体颜色为白色
        },
        itemStyle: {
          color: '#FF5803'  // 设置柱条的颜色为红色
        }
      }
    ]
  },
  myChart: null,

})

onMounted(() => {
  data.myChart = echarts.init(document.getElementById('myChart'), null, {
    height: '480px'
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
      normGuid: "11209d5a-3471-4fd8-8c7b-cae28392e9f7",
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
      console.log(JSON.stringify(result.custom.result.cockpitData), '解密后的数据')
      aYear.value = changFormate(result.custom.result.cockpitData)[0]
      aYearName.value = result.custom.result.cockpitData[0].nf
      bYear.value = changFormate(result.custom.result.cockpitData)[1]
      bYearName.value = result.custom.result.cockpitData[1].nf
      Year.value = result.custom.result.cockpitData
      console.log(aYear.value, 'Year.value')
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
