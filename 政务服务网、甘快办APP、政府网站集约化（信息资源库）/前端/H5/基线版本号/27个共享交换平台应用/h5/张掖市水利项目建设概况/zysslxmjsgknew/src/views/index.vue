<template>
  <div class="container-wrap">
    <div class="container">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">年度实施水利项目数</div>
        <img class="containertitleimg" src="../assets/images/xy.png" alt="">
      </div>
      <div ref="chart" style="width: 100%; height: 220px;margin-top:20px;margin-bottom:5px;"></div>
      <!-- <div ref="chart" style="width: 100%; height: 480px;"></div> -->
      <div class="tllist">
        <div class="tllistper" v-for="(item,index) in tullist" :key="index">
          <div class="tllistper1">
            <div class="tllistperin1" :style="item.itemStyle"></div>
            <div class="tllistperin2">{{ item.name }}</div>
          </div>
          <div class="tllistper2">
            <div class="tllistperin3">{{ item.value }}个</div>
            <div class="tllistperin4">{{ ((item.value/allnum)*100).toFixed(2) }}%</div>
          </div>
        </div>
      </div>
    </div>
    <div class="bottomtitle">
      <img class="bottomtitleimg" src="../assets/images/bottomimg.png" alt="">
      <div class="bottomtitletext">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>

<script>
// import IndexApi from "@/api/index";
import { Toast } from 'vant'
import * as echarts from 'echarts'
import requestJpaas from '../utils/request'
import { tools } from '../tools/tool'
const { getData } = requestJpaas
export default {
  name: 'MyChart',
  data () {
    return {
      allnum: 999,
      yhname: '水利项目数',
      listdata: [
        {value: 0, name: '防洪治理工程', itemStyle: { color: '#1F66E4' }},
        {value: 0, name: '大中型灌区续建配套与节水改造项目', itemStyle: { color: '#FD9137' }},
        {value: 0, name: '山洪灾害防治及维修养护项目', itemStyle: { color: '#F25424' }},
        {value: 0, name: '水利工程维修养护', itemStyle: { color: '#09A321' }},
        {value: 0, name: '水资源管理', itemStyle: { color: '#9398FF' }},
        {value: 0, name: '水土保持综合治理工程', itemStyle: { color: '#1297CB' }},
        {value: 0, name: '库区移民项目', itemStyle: { color: '#01B0A6' }},
        {value: 0, name: '水源工程', itemStyle: { color: '#2E22DC' }},
      ],
      tullist: [
        {value: 0, name: '防洪治理工程', itemStyle: { background: '#1F66E4' }},
        {value: 0, name: '大中型灌区续建配套与节水改造项目', itemStyle: { background: '#FD9137' }},
        {value: 0, name: '山洪灾害防治及维修养护项目', itemStyle: { background: '#F25424' }},
        {value: 0, name: '水利工程维修养护', itemStyle: { background: '#09A321' }},
        {value: 0, name: '水资源管理', itemStyle: { background: '#9398FF' }},
        {value: 0, name: '水土保持综合治理工程', itemStyle: { background: '#1297CB' }},
        {value: 0, name: '库区移民项目', itemStyle: { background: '#01B0A6' }},
        {value: 0, name: '水源工程', itemStyle: { background: '#2E22DC' }},
      ]
    }
  },
  created () {},
  mounted () {
    // console.log('sm2----:',tools.encryptSm2('222334'))
    this.getlist()
  },
  methods: {
    initChart () {
      const chartDom = this.$refs.chart
      const myChart = echarts.init(chartDom)
      const demandOptions4 = {
        tooltip: {
          trigger: 'item'
        },
        grid: {
          height: 400,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          splitLine: { show: false },
          axisLine: { show: false }
        },
        yAxis: {
          splitLine: { show: false }
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: ['70%', '90%'], // 设置内半径和外半径，形成环形
            data: this.listdata,
            padAngle: 1,
            label: {
              normal: {
                show: true,
                formatter: [
                  `{textcss1|${this.allnum}个}`,
                  `{textcss2|${this.yhname}}`
                ].join('\n'),
                position: 'center',
                name: '111',
                rich: {
                  textcss1: {
                    color: '#000',
                    lineHeight: 20,
                    fontSize: 18.5
                  },
                  textcss2: {
                    color: '#000',
                    lineHeight: 20,
                    fontSize: 15
                  }
                }
              }
            },
            rich: {
              titlecss: {
                color: 'red'
              }
            },
            labelLine: {
              show: false
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      myChart.setOption(demandOptions4);
    },
    async getlist () {
      const data = {
        normGuid: 'c49d4bce-2bec-4a49-8aa4-bfd8ef8133e4',
        fieldList: []
      }
      const toast1 = Toast.loading({
        message: '加载中...',
        forbidClick: true,
        duration: '0',
        transition: 'slide-fade'
      })
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      toast1.clear()
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result.cockpitData
      this.allnum = JSON.parse(tools.decryptSm2(res.data)).custom.result.sumCount

      newres.forEach((item, index) => {
        if (item.xmlx === '防洪治理工程') {
          this.listdata[0].value += 1
          this.tullist[0].value += 1
        } else if (item.xmlx === '大中型灌区续建配套与节水改造项目') {
          this.listdata[1].value += 1
          this.tullist[1].value += 1
        } else if (item.xmlx === '山洪灾害防治及维修养护项目') {
          this.listdata[2].value += 1
          this.tullist[2].value += 1
        } else if (item.xmlx === '水利工程维修养护') {
          this.listdata[3].value += 1
          this.tullist[3].value += 1
        } else if (item.xmlx === '水资源管理') {
          this.listdata[4].value += 1
          this.tullist[4].value += 1
        } else if (item.xmlx === '水土保持综合治理工程') {
          this.listdata[5].value += 1
          this.tullist[5].value += 1
        } else if (item.xmlx === '库区移民项目') {
          this.listdata[6].value += 1
          this.tullist[6].value += 1
        } else if (item.xmlx === '水源工程') {
          this.listdata[7].value += 1
          this.tullist[7].value += 1
        }
      })
      this.initChart()
    }
  }
}
</script>
<style scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f6f6f8;
  overflow: auto;
}
.container{
  width: 93.6%;
  /* border: 1px solid red; */
  margin-top: 12px;
  background: #fff;
  padding-bottom: 33px;
  padding-top: 16px;
  margin: 0 auto;
  margin-top: 12px;
}
.containertitle {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.containertitlebor {
  width: 3.5px;
  height: 15px;
  border-radius: 3.5px;
  background: #3266c2;
  margin-left: 12px;
}
.containertitletext {
  font-family: "PingFang SC Bold";
  font-weight: 700;
  line-height: 21px;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 8px;
}
.containertitleimg {
  width: 7px;
  height: 12px;
  margin-left: 8px;
}
.tllist{
  width: 100%;
  /* border: 1px solid red; */
}
.tllistper{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding-top: 7.5px;
  padding-bottom: 7.5px;
}
.tllistper1 {
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
}
.tllistper2 {
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
  margin-right: 20px;
}
.tllistperin1{
  width: 12px;
  height: 12px;
  /* background: red; */
  border-radius: 50px;
  margin-left: 20px;
  /* border: 1px solid red; */
}
.tllistperin2{
  width: 172px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 16px;
  text-align: left;
  color: #000;
  margin-left: 5px;
  /* border: 1px solid red; */
}
.tllistperin3{
  width: 34px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 15px;
  text-align: left;
  color: #000;
  margin-left: 5px;
  /* border: 1px solid red; */
}
.tllistperin4{
  width: 54px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 15px;
  text-align: left;
  color: #000;
  margin-left: 30px;
  /* border: 1px solid red; */
}
.bottomtitle {
  width: 100%;
  height: 58px;
  /* border: 1px solid red; */
  position: fixed;
  bottom: 0px;
}
.bottomtitleimg {
  width: 100%;
  height: 58px;
  /* border: 1px solid red; */
  position: absolute;
}
.bottomtitletext{
  width: 100%;
  line-height: 58px;
  text-align: center;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  color: #999;
}
</style>
