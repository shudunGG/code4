<template>
  <div class="container-wrap">
    <div class="container1">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">农村供水工程</div>
      </div>
      <div class="topmk">
        <div class="topmk1">
          <img class="topmk1bg1" src="../assets/images/bg1.png" alt="">
          <img class="topmk1bg1in" src="../assets/images/bg1in.png" alt="">
          <div class="topmk1intext">
            <div class="topmk1intextin" style="margin-bottom:8px;">规模以上农村供水工程处数</div>
            <div class="topmk1intextin">{{ ncgsgcdata.gmysncgsgccs }}处</div>
          </div>
        </div>
        <div class="topmk2">
          <div class="topmk2in">
            <img class="topmk2inimg" src="../assets/images/bg2.png" alt="">
            <div class="topmk2intext">
              <div class="topmk2intextin" style="margin-bottom:8px;">
                <div class="topmk2intextinbor"></div>
                <div class="topmk2intextinwz">集中供水率</div>
              </div>
              <div class="topmk2intextin2">{{ ncgsgcdata.jzgss }}%</div>
            </div>
          </div>
          <div class="topmk2in">
            <img class="topmk2inimg" src="../assets/images/bg3.png" alt="">
            <div class="topmk2intext">
              <div class="topmk2intextin" style="margin-bottom:8px;">
                <div class="topmk2intextinbor"></div>
                <div class="topmk2intextinwz">自来水普及率</div>
              </div>
              <div class="topmk2intextin2">{{ ncgsgcdata.zlspjs }}%</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container2">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">水资源管理</div>
      </div>
      <div class="container2intitle">
        <div class="container2inbor"></div>
        <div class="container2intext">水资源现状</div>
        <!-- <img class="container2inimg" src="../assets/images/wh.png" alt=""> -->
      </div>
      <div class="topmk">
        <div class="topmk2" style="margin-top: 0px;">
          <div class="topmk2in">
            <img class="topmk2inimg" src="../assets/images/bg4.png" alt="">
            <div class="topmk2intext">
              <div class="topmk2intextin" style="margin-bottom:8px;">
                <div class="topmk2intextinbor"></div>
                <div class="topmk2intextinwz">平均降水量</div>
              </div>
              <div class="topmk2intextin2">{{ szyxzdata.pjjsl }}毫米</div>
            </div>
          </div>
          <div class="topmk2in">
            <img class="topmk2inimg" src="../assets/images/bg5.png" alt="">
            <div class="topmk2intext">
              <div class="topmk2intextin" style="margin-bottom:8px;">
                <div class="topmk2intextinbor"></div>
                <div class="topmk2intextinwz">平均蒸发量</div>
              </div>
              <div class="topmk2intextin2">{{ szyxzdata.pjzfl }}毫米</div>
            </div>
          </div>
        </div>
        <div class="topmk3">
          <div class="topmk3in">
            <img class="topmk3inimg" src="../assets/images/bg6.png" alt="">
            <div class="topmk3intext">
              <div class="topmk3intextin" >
                <div class="topmk3intextinbor2" style="background: #2375F2;"></div>
                <div class="topmk3intextinwz">人均占有水资源量</div>
              </div>
              <div class="topmk3intextin2">{{ szyxzdata.rjzyszyl }}多㎡</div>
            </div>
          </div>
          <div class="topmk3in">
            <img class="topmk3inimg" src="../assets/images/bg7.png" alt="">
            <div class="topmk3intext">
              <div class="topmk3intextin" style="margin-bottom:8px;">
                <div class="topmk3intextinbor2" style="background: #29AC8E"></div>
                <div class="topmk3intextinwz">人均占有水资源量与全国比值</div>
              </div>
              <div class="topmk3intextin2">{{ szyxzdata.rjzyszylyqgbz }}</div>
            </div>
          </div>
          <div class="topmk3in">
            <img class="topmk3inimg" src="../assets/images/bg8.png" alt="">
            <div class="topmk3intext">
              <div class="topmk3intextin" style="margin-bottom:8px;">
                <div class="topmk3intextinbor2" style="background: #CB8741;"></div>
                <div class="topmk3intextinwz">人均占有水资源量与全省比值</div>
              </div>
              <div class="topmk3intextin2">{{ szyxzdata.rjzyszylyqsbz }}</div>
            </div>
          </div>
        </div>
        <div class="topmk4">
          <div class="top4in">
            <img class="top4in1img" src="../assets/images/bh9.png" alt="">
            <div class="top4in1">
              <img class="top4in1img2" src="../assets/images/bg9in.png" alt="">
              <div class="top4in1text">年用水总量</div>
              <!-- <img class="top4in1img3" src="../assets/images/wh2.png" alt=""> -->
            </div>
            <div class="top4in2">{{ nysl }}亿吨</div>
          </div>
        </div>
      </div>
      <div class="container2intitle" style="margin-bottom:12px;">
        <div class="container2inbor"></div>
        <div class="container2intext">县区年用水量</div>
      </div>
      <div ref="chart" style="width:100%;height:320px;"></div>
    </div>
    <div style="width:100%;height:20px;"></div>
    <div class="bottomtitle">
      <img class="bottomtitleimg" src="../assets/images/bottomimg.png" alt="">
      <div class="bottomtitletext">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script>
// import IndexApi from "@/api/index";
// import { Toast } from "vant";
import * as echarts from 'echarts'
import requestJpaas from '../utils/request'
import { tools } from '../tools/tool'
import { indexOf } from 'lodash'
const { getData } = requestJpaas
export default {
  name: 'MyChart',
  data () {
    return {
      allnum: 0,
      yhname: '水利项目数',
      xqnysllist1: [],
      xqnysllist2: [],
      ncgsgcdata: {},
      szyxzdata: {},
      nysl: '',// 年用水总量
    }
  },
  created () {},
  mounted () {
    this.getncgsgc()
    this.getszygl()
    this.getqxnysl() // 县区年用水量
  },
  methods: {
    initChart () {
      const chartDom = this.$refs.chart
      const myChart = echarts.init(chartDom)
      const demandOptions4 = {
        title: {
          text: '单位（亿吨）',
          left: 15,
          textStyle: {
            color: '#000',
            fontSize: 15,
            fontWeight: 500
          }
        },
        tooltip: {},
        legend: {
          show: false
        },
        xAxis: {
          data: this.xqnysllist1,
          axisLabel: {
            show: true, // 显示标签
            textStyle: {
              color: '#247cfa', // 文字颜色
              fontSize: 10, // 文字字号
              fontWeight: 400
            },
            margin: 12 // 标签与轴线之间的距离
          }
        },
        yAxis: {},
        series: [
          {
            type: 'bar',
            barWidth: 15,
            data: this.xqnysllist2,
            label: {
              show: true,
              position: 'top',
              color: '#000',
              fontSize: '12px'
            },
            zoom: 1.29,
            itemStyle: {
              color: {
                type: 'linear',
                x: 0,
                x2: 0,
                y: 1,
                y2: 0,
                colorStops: [
                  {
                    offset: 0,
                    color: '#40BD95'
                  },
                  {
                    offset: 1,
                    color: '#247CFA'
                  }
                ]
              }
            }
          }
        ]
      }
      myChart.setOption(demandOptions4);
    },
    async getncgsgc () { // 农村供水工程
      const data = {
        normGuid: 'fca319ad-b7f2-4f75-99d5-b9934d643a34',
        fieldList: []
      }
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      this.ncgsgcdata = JSON.parse(tools.decryptSm2(res.data)).custom.result.cockpitData[0]
    },
    async getszygl () { // 水资源管理
      const data = {
        normGuid: '3aae540f-61fc-483d-a228-69fec380d94e',
        fieldList: []
      }
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      this.szyxzdata = JSON.parse(tools.decryptSm2(res.data)).custom.result.cockpitData[0]
    },
    async getqxnysl () { // 县区年用水量
      const data = {
        normGuid: '800f864a-1148-46af-8da0-1b3951ac17b5',
        fieldList: []
      }
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      console.log('274-----', JSON.parse(tools.decryptSm2(res.data)).custom.result.cockpitData)
      const arrlist = JSON.parse(tools.decryptSm2(res.data)).custom.result.cockpitData
      let that = this
      arrlist.forEach(item => {
        if (item.qxmc.indexOf('张掖市') > -1) {
          that.nysl = item.nyszl
        }
        if (item.qxmc !== '张掖市') {
          that.xqnysllist1.push({
            value: item.qxmc
          })
          that.xqnysllist2.push({
            value: item.nyszl
          })
        }
      })
      that.initChart()
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
.container1{
  width: 100%;
  /* border: 1px solid red; */
  margin-top: 12px;
  background: #fff;
  padding-bottom: 10px;
  padding-top: 12px;
}
.container2{
  width: 100%;
  /* border: 1px solid red; */
  margin-top: 12px;
  background: #fff;
  padding-bottom: 33px;
  padding-top: 12px;
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
  line-height: 25px;
  font-size: 18px;
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
}
.tllistperin4{
  width: 54px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-align: left;
  color: #000;
  margin-left: 35px;
  /* border: 1px solid red; */
}
.topmk{
  width: 93.3%;
  margin-left: 12px;
  /* border: 1px solid red; */
  margin-top: 12px;
}
.topmk1{
  width: 100%;
  height: 74px;
  border-radius: 4px;
  position: relative;
}
.topmk1bg1{
  width: 100%;
  height: 74px;
  position: absolute;
  top: 0px;
  left: 0px;
}
.topmk1bg1in{
  width: 42px;
  height: 42px;
  /* border: 1px solid red; */
  border-radius: 4px;
  background: #fff;
  position: absolute;
  top: 16px;
  left: 12px;
}
.topmk1intext{
  display: flex;
  flex-direction: column;
  /* border: 1px solid red; */
  position: absolute;
  top: 12px;
  left: 75px;
}
.topmk1intextin{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
}
.topmk2{
  width: 100%;
  height: 74px;
  /* border: 1px solid red; */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 16px;
}
.topmk2in{
  position: relative;
}
.topmk2inimg{
  width: 167px;
  height: 74px;
  border-radius: 4px;
}
.topmk2intext{
  display: flex;
  flex-direction: column;
  /* border: 1px solid red; */
  position: absolute;
  top: 12px;
}
.topmk2intextin{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
  display: flex;
  flex-direction: row;
}
.topmk2intextinbor{
  width: 3px;
  height: 12px;
  background: #fff;
  opacity: 0.5;
}
.topmk2intextinwz{
  margin-left: 12px;
}
.topmk2intextin2{
  margin-left: 15px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
}
.container2intitle {
  /* border: 1px solid red; */
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 16px;
}
.container2inbor{
  width: 8px;
  height: 8px;
  background: #247cfa;
  border-radius: 8px;
  margin-left: 12px;
}
.container2intext{
  font-family: "PingFang SC Bold";
  font-weight: 700;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 8px;
}
.container2inimg{
  width: 15px;
  height: 15px;
  margin-left: 15px;
}

.topmk3{
  width: 100%;
  /* border: 1px solid red; */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 16px;
}
.topmk3in{
  /* width: 106px; */
  height: 120px;
  position: relative;
}
.topmk3inimg{
  width: 106px;
  height: 100%;
  border-radius: 4px;
}
.topmk3intext{
  height: 104px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: absolute;
  top: 8px;
}
.topmk3intextin{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
  display: flex;
  flex-direction: row;
}
.topmk3intextinbor{
  width: 3px;
  height: 12px;
  background: #fff;
  opacity: 0.5;
}
.topmk3intextinbor2{
  width: 1.5px;
  height: 25px;
  background: #2375f2;
}
.topmk3intextinwz{
  width: 75px;
  margin-left: 12px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 25px;
  text-align: left;
  color: #000;
  /* border: 1px solid red; */

}
.topmk3intextin2{
  margin-left: 15px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  line-height: 21px;
  text-align: left;
  color: #000;
  bottom: 10px;
}
.topmk4{
  width: 100%;
  /* border: 1px solid red; */
}
.top4in{
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  position: relative;
  margin-top: 16px;
}
.top4in1{
  position: absolute;
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
  margin-left: 12px;
}
.top4in1img{
  width: 100%;
  height: 45px;
}
.top4in1img2{
  width: 25px;
  height: 25px;
  border-radius: 4px;
}
.top4in1text{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
  margin-left: 12px;
}
.top4in1img3{
  width: 15px;
  height: 15px;
  margin-left: 12px;
}
.top4in2{
  position: absolute;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
  right: 12px;
  /* border: 1px solid red; */
}
.bottomtitle {
  width: 100%;
  height: 58px;
  background: #fff;
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
