<template>
  <div class="container-wrap">
    <div class="container">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">万亩以上中大型灌区处数</div>
      </div>
      <div ref="chart" style="width: 100%; height: 220px;margin-top:20px;margin-bottom:5px;"></div>
      <div class="tllist">
        <div class="tllistper" v-for="(item,index) in tullist" :key="index">
          <div class="tllistper1">
            <div class="tllistperin1" :style="item.itemStyle"></div>
            <div class="tllistperin2">{{ item.name }}</div>
          </div>
          <div class="tllistper2">
            <div class="tllistperin3">{{ item.value }}个</div>
            <div class="tllistperin4" v-if="item.value != 0">{{ (item.value / allnum * 100).toFixed(2)}}%</div>
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
const { getData } = requestJpaas
import { tools } from '../tools/tool'
export default {
  name: 'MyChart',
  data () {
    return {
      allnum: 0,
      yhname: '水利项目数',
      listdata: [
        {value: 0, name: '大型灌区处数', itemStyle: { color: '#1F66E4' }},
        {value: 0, name: '重点中型灌区处数', itemStyle: { color: '#FD9137' }},
        {value: 0, name: '一般中型灌区处数', itemStyle: { color: '#F25424' }},
      ],
      tullist: [
        {value: 0, name: '大型灌区处数', itemStyle: { background: '#1F66E4' }},
        {value: 0, name: '重点中型灌区处数', itemStyle: { background: '#FD9137' }},
        {value: 0, name: '一般中型灌区处数', itemStyle: { background: '#F25424' }},
      ],
      xydata: {}
    }
  },
  created () {
    this.getlist()
  },
  mounted () {},
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
            radius: ['75%', '90%'], // 设置内半径和外半径，形成环形
            data: this.listdata,
            padAngle: 1,
            zoom: 1.29,
            label: {
              normal: {
                show: true,
                formatter: [
                  `{textcss1|${this.allnum}处}`,
                  `{textcss2|万亩以上}`,
                  '{textcss2|中大型灌溉区处数}'
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
        ],
      }
      myChart.setOption(demandOptions4)
    },
    async getlist () {
      const data = {
        normGuid: 'bfeceec3-e0f9-4628-a6b0-8cfa8f481656',
        fieldList: []
      }
      const toast1 = Toast.loading({
        message: '加载中...',
        forbidClick: true,
        duration: '0',
        transition: 'slide-fade'
      })
      const res = await getData ({
        // columnId: "bfeceec3-e0f9-4628-a6b0-8cfa8f481656",
        params: tools.encryptSm2(JSON.stringify(data))
      })
      toast1.clear()
      // console.log('127-----', res)
      // console.log('127-----', tools.decryptSm2(res.data))
      const res2 = JSON.parse(tools.decryptSm2(res.data))
      let xydata = res2.custom.result.cockpitData[0]
      // console.log(xydata)
      this.xydata = xydata
      this.allnum = xydata.wmysdzxgqcs
      this.listdata[0].value = xydata.dxgqcs
      this.tullist[0].value = xydata.dxgqcs
      this.listdata[1].value = xydata.zdzxgqcs
      this.tullist[1].value = xydata.zdzxgqcs
      this.listdata[2].value = xydata.ybzxgqcs
      this.tullist[2].value = xydata.ybzxgqcs

      this.initChart()
    }
  }
}
</script>
<style scoped>
.container-wrap {
  width: 100%;
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
  background: #fff;
  /* margin-left: 12px; */
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
  /* border: 1px solid red; */
  padding-right: 20px;
}
.tllistper1{
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
}
.tllistper2{
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
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
  line-height: 30px;
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
  line-height: 20px;
  text-align: left;
  color: #000;
  /* border: 1px solid red; */
  margin-right: 30px;
}
.tllistperin4{
  width: 54px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-align: left;
  color: #000;
  /* margin-left: 35px; */
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
