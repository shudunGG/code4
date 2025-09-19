<template>
  <div class="container-wrap">
    <div class="containerout">
      <div class="container">
        <div class="containertitle">
          <div class="containertitlebor"></div>
          <div class="containertitletext">旅游资源</div>
        </div>
        <div style="width:100%;height:245px;margin-top:24px;margin-bottom:16px;" ref="chartsDOM"></div>
        <div class="tlmk">
          <div class="tlmkin">
            <div class="tlmkinbor" style="background:#3266C2;"></div>
            <div class="tlmkintext">3A:{{ arr3num }}</div>
          </div>
          <div class="tlmkin">
            <div class="tlmkinbor" style="background:#FF9237;"></div>
            <div class="tlmkintext">4A:{{ arr4num }}</div>
          </div>
          <div class="tlmkin">
            <div class="tlmkinbor" style="background:#F15323;"></div>
            <div class="tlmkintext">5A:{{ arr5num }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="containerout">
      <div class="container">
        <div class="top4in1" style="background:#207FFF;">
          <div class="top4in1left">
            <img class="top4in1img2" src="../assets/images/gimg1.png" alt="">
            <div class="top4in1text">旅行社数量</div>
          </div>
          <div class="top4in1right">{{ lxsdata.lhssl }}个</div>
        </div>
        <div class="top4in1" style="background:#23A285;">
          <div class="top4in1left">
            <img class="top4in1img2" src="../assets/images/gimg2.png" alt="">
            <div class="top4in1text">全市星级酒店数量</div>
          </div>
          <div class="top4in1right">{{ alllxsnum }}个</div>
        </div>
        <div class="threemk">
          <div class="threemkper" style="background:#F5F8FF;">
            <img class="threemkperimg" src="../assets/images/mkbj.png" alt="">
            <div class="threemkperin">
              <div class="threemkperin1">
                <div class="threemkperin1bor" style="background:#2375F2;"></div>
                <div class="threemkperin1text">4星级酒店</div>
              </div>
              <div class="threemkperin2">{{ xjarr4.sl }}个</div>
            </div>
          </div>
          <div class="threemkper" style="background:#FEFBF4;">
            <img class="threemkperimg" src="../assets/images/mkbj.png" alt="">
            <div class="threemkperin">
              <div class="threemkperin1">
                <div class="threemkperin1bor" style="background:#D5A066;"></div>
                <div class="threemkperin1text">3星级酒店</div>
              </div>
              <div class="threemkperin2">{{ xjarr3.sl }}个</div>
            </div>
          </div>
          <div class="threemkper" style="background:#F5F9F8;">
            <img class="threemkperimg" src="../assets/images/mkbj.png" alt="">
            <div class="threemkperin">
              <div class="threemkperin1">
                <div class="threemkperin1bor" style="background:#29AC8E;"></div>
                <div class="threemkperin1text">2星级酒店</div>
              </div>
              <div class="threemkperin2">{{ xjarr2.sl }}个</div>
            </div>
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
import { Toast } from "vant";
import * as echarts from 'echarts';
import mapJson from '../assets/zy.json'
import { color } from 'echarts/core';
import requestJpaas from '../utils/request'
const { getData } = requestJpaas
import {tools} from '../tools/tool'
export default {
  name: 'MyChart',
  data () {
    return {
      allnum: 0,
      yhname: '水利项目数',
      listdata: [
        { value: 9, name: "大型灌区处数", itemStyle: { color: "#1F66E4" } },
        {
          value: 35,
          name: "重点中型灌区处数",
          itemStyle: { color: "#FD9137" },
        },
        {
          value: 10,
          name: "一般中型灌区处数",
          itemStyle: { color: "#F25424" },
        },
      ],
      tullist: [
        {
          value: 9,
          name: "大型灌区处数",
          itemStyle: { background: "#1F66E4" },
        },
        {
          value: 35,
          name: "重点中型灌区处数",
          itemStyle: { background: "#FD9137" },
        },
        {
          value: 10,
          name: "一般中型灌区处数",
          itemStyle: { background: "#F25424" },
        },
      ],
      tklist: [
        // 自定义引导线的起点和终点
        [{ coord: [98.890262,39.078532], name: '肃南裕固族自治县' }, { coord: [98.604432,38.502051], name: '终点' }],
        [{ coord: [99.604433,39.531384], name: '高台县' }, { coord: [100.685931,39.770478], name: '终点' }],
        [{ coord: [100.151142,39.318594], name: '临泽县' }, { coord: [101.050657,39.521923], name: '终点' }],
        [{ coord: [100.517996,39.00345], name: '甘州区' }, { coord: [101.050657,39.221923], name: '终点' }],
        [{ coord: [101.180996,38.50345], name: '山丹县' }, { coord: [101.560657,38.921923], name: '终点' }],
        [{ coord: [100.807996,38.49345], name: '民乐县' }, { coord: [100.060657,37.921923], name: '终点' }]
      ],
      mapJson: {},
      arr3: [],
      arr4: [],
      arr5: [],
      arr3num: '',
      arr4num: '',
      arr5num: '',
      qxlist1: [],
      qxlist2: [],
      qxlist3: [],
      qxlist4: [],
      qxlist5: [],
      qxlist6: [],
      lxsnum: '',//旅行社数
      xjjdsnum: '',//星级酒店数
      xjarr2: {},
      xjarr3: {},
      xjarr4: {},
      xjarr2num: '',
      xjarr3num: '',
      xjarr4num: '',
      tkshowlist: [],
      lxsdata: {},
      alllxsnum: 0
    }
  },
  created () {
    this.mapJson = mapJson
    this.getlist()
  },
  mounted () {
    // this.getlist()
    this.getlxsgk()
    this.getlxsgk2()
    // this.initChart2()
  },
  methods: {
    async getlist () {//获取列表数据
      const data = {
        normGuid: "6a9c8978-fed4-4b74-9149-005c350c6947",
        fieldList: []
      }
      const toast1 = Toast.loading({
        message: '加载中...',
        forbidClick: true,
        duration: '0',
        transition: 'slide-fade',
      })
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      toast1.clear()
      let resnew = JSON.parse(tools.decryptSm2(res.data))

      let allarrlist = resnew.custom.result.cockpitData
      let arr3 = []
      let arr4 = []
      let arr5 = []
      allarrlist.forEach((item, index) => {
        if (item.jqdj == '3A') {
          arr3.push(item)
        } else if (item.jqdj == '4A') {
          arr4.push(item)
        } else if (item.jqdj == '5A') {
          arr5.push(item)
        }
      })
      this.arr3 = arr3
      this.arr4 = arr4
      this.arr5 = arr5
      this.arr3num = arr3.length
      this.arr4num = arr4.length
      this.arr5num = arr5.length
      // console.log('182----；', allarrlist)
      const qxlist1 = []
      const qxlist2 = []
      const qxlist3 = []
      const qxlist4 = []
      const qxlist5 = []
      const qxlist6 = []
      allarrlist.forEach((item, index) => {
        if (item.ssqx.indexOf('临泽') > -1) {
          qxlist1.push(item)
        } else if (item.ssqx.indexOf('高台') > -1) {
          qxlist2.push(item)
        } else if (item.ssqx.indexOf('甘州') > -1) {
          qxlist3.push(item)
        } else if (item.ssqx.indexOf('山丹') > -1) {
          qxlist4.push(item)
        } else if (item.ssqx.indexOf('民乐') > -1) {
          qxlist5.push(item)
        } else if (item.ssqx.indexOf('肃南') > -1) {
          qxlist6.push(item)
        }
      })
      this.qxlist1 = qxlist1
      this.qxlist2 = qxlist2
      this.qxlist3 = qxlist3
      this.qxlist4 = qxlist4
      this.qxlist5 = qxlist5
      this.qxlist6 = qxlist6
      let tkshowlist = [
        { name: '肃南裕固族自治县', value: '0' },
        { name: '临泽县', value: '0' },
        { name: '高台县', value: '0' },
        { name: '甘州区', value: '0' },
        { name: '山丹县', value: '0' },
        { name: '民乐县', value: '0' }
      ]
      // tklist: [
      //   // 自定义引导线的起点和终点
      //   [{ coord: [98.890262,39.078532], name: '肃南裕固族自治县' }, { coord: [98.104432,38.562051], name: '终点' }],
      //   [{ coord: [99.604433,39.531384], name: '高台县' }, { coord: [100.685931,39.770478], name: '终点' }],
      //   [{ coord: [100.151142,39.318594], name: '临泽县' }, { coord: [101.050657,39.521923], name: '终点' }],
      //   [{ coord: [100.517996,39.00345], name: '甘州区' }, { coord: [101.050657,39.221923], name: '终点' }],
      //   [{ coord: [101.180996,38.50345], name: '山丹县' }, { coord: [101.560657,38.921923], name: '终点' }],
      //   [{ coord: [100.807996,38.49345], name: '民乐县' }, { coord: [100.060657,37.921923], name: '终点' }]
      // ]
      tkshowlist[0].value = qxlist6.length
      this.tklist[0][0].name = this.tklist[0][0].name + '(' + qxlist6.length + ')'
      tkshowlist[1].value = qxlist1.length
      this.tklist[1][0].name = this.tklist[1][0].name + '(' + qxlist2.length + ')'
      tkshowlist[2].value = qxlist2.length
      this.tklist[2][0].name = this.tklist[2][0].name + '(' + qxlist1.length + ')'
      tkshowlist[3].value = qxlist3.length
      this.tklist[3][0].name = this.tklist[3][0].name + '(' + qxlist3.length + ')'
      tkshowlist[4].value = qxlist4.length
      this.tklist[4][0].name = this.tklist[4][0].name + '(' + qxlist4.length + ')'
      tkshowlist[5].value = qxlist5.length
      this.tklist[5][0].name = this.tklist[5][0].name + '(' + qxlist5.length + ')'
      this.tkshowlist = tkshowlist
      this.initChart2()
    },
    async getlxsgk () { // 旅行社概况
      const data = {
        normGuid: "f6840274-ab43-43aa-ae58-36c2fd6e6f92",
        fieldList: []
      }
      const toast2 = Toast.loading({
        message: '加载中...',
        forbidClick: true,
        duration: '0',
        transition: 'slide-fade',
      })
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      toast2.clear()
      let newres = JSON.parse(tools.decryptSm2(res.data))
      console.log('181----:', newres)
      let lxsdata = newres.custom.result.cockpitData[0]
      this.lxsdata = lxsdata
      // this.lxsnum = allarrlist2.length
    },
    async getlxsgk2 () { // 各星级酒店数量
      const data = {
        normGuid: 'e45dc109-bf51-4744-9e0c-2a7cfa6c20e3',
        fieldList: []
      }
      const toast3 = Toast.loading({
        message: '加载中...',
        forbidClick: true,
        duration: '0',
        transition: 'slide-fade'
      })
      const res = await getData ({
        params: tools.encryptSm2(JSON.stringify(data))
      })
      toast3.clear()
      let newres = JSON.parse(tools.decryptSm2(res.data))
      console.log('302----:', newres)
      let allarrlist3 = newres.custom.result.cockpitData
      console.log('304----:', allarrlist3)
      this.xjjdsnum = allarrlist3.length
      let alllxsnum = 0
      allarrlist3.forEach(item => {
        alllxsnum = alllxsnum + item.sl
        if (item.dj === '2星') {
          this.xjarr2 = item
        } else if (item.dj === '3星') {
          this.xjarr3 = item
        } else if (item.dj === '4星') {
          this.xjarr4 = item
        }
      })
      this.alllxsnum = alllxsnum
    },
    initChart2 () {
      // 初始化统计图对象
      const myChart = echarts.init(this.$refs['chartsDOM'])
      echarts.registerMap('ZY', this.mapJson)
      // myChart.resize({
      //   width: '400', // 或者可以使用 'auto'
      //   height: '400'
      // })
      const option = {
        tooltip: {
          show: false,
          trigger: 'item',
          formatter: function (params) {
            // 自定义弹框内容
            return `<div class="map-tooltip-list">${params.name}(${params.value || '无数据'})</div>`
          },
          backgroundColor: 'rgba(255, 255, 255, 0.9)', // 背景颜色
          borderColor: '#ccc', // 边框颜色
          borderWidth: 1, // 边框宽度
          textStyle: {
            color: '#3266c2', // 文字颜色
            fontSize: 12, // 字体大小
            textBorderColor: "rgba(222, 55, 55, 1)",
            textBorderType: [5, 10],
            textBorderDashOffset: 5
          },
          padding: [10, 15], // 内边距
          confine: true,
          className: 'map-tooltip'
        },
        legend: {
          show: false
        },
        series: [
          {
            name: '张掖市',
            type: 'map',
            map: 'ZY', // 这个是上面注册时的名字哦，registerMap（'这个名字保持一致'）
            zoom: 1.22,
            label: {
              show: false
            },
            itemStyle: {
              areaColor: '#AAC5F4', // 区块颜色
              borderColor: '#fff', // 描边颜色
              borderWidth: 1 // 描边宽度
            },
            select: {
              itemStyle: {
                borderColor: '#79A8FF',
                areaColor: '#79A8FF' // 区块颜色
              }
            },
            markLine: {
              symbol: ['none', 'none'], // 取消箭头样式
              label: {
                show: true,
                position: 'end',
                formatter: '{b}', // 显示引导线的文字
                fontSize: 12,
                color: '#3266C2'
              },
              lineStyle: {
                color: '#4177D7', // 引导线颜色
                width: 1, // 引导线宽度
                type: 'dashed' // 引导线样式
              },
              data: this.tklist
            },
            data: this.tkshowlist
          }
        ]
      }
      myChart.setOption(option)
      let that = this
      myChart.on('click', function (params) {
        // params 包含了点击事件的相关信息，比如点击的系列（series）和数据（data）
        console.log(params)
        // 在这里编写你的逻辑代码
        // 例如，可以弹出一个提示框显示点击的地区名称
        // alert('你点击了：' + params.name)
        let xzlistdata = []
        if (params.name.indexOf('临泽') > -1) {
          xzlistdata = that.qxlist1
        } else if (params.name.indexOf('高台') > -1) {
          xzlistdata = that.qxlist2
        } else if (params.name.indexOf('甘州') > -1) {
          xzlistdata = that.qxlist3
        } else if (params.name.indexOf('山丹') > -1) {
          xzlistdata = that.qxlist4
        } else if (params.name.indexOf('民乐') > -1) {
          xzlistdata = that.qxlist5
        } else if (params.name.indexOf('肃南') > -1) {
          xzlistdata = that.qxlist6
        }
        sessionStorage.setItem('xzlistdata', JSON.stringify(xzlistdata))
        that.$router.push({
          path: '/jqlist'
        })
      })
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
.containerout{
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.container{
  width: 93.3%;
  background: #fff;
  margin-top: 12px;
  /* border: 1px solid red; */
  display: flex;
  flex-direction: column;
  align-items: center;
}
.containertitle {
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid red; */
  margin-top: 12px;
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
.top4in1{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  /* border: 1px solid red; */
  /* margin-left: 12px; */
  width: 327px;
  height: 49px;
  border-radius: 4px;
  margin-top: 12px;
  background: linear-gradient(90.00deg, #207fff 0%, #2971ea 100%);
}
.top4in1text{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 18px;
  text-align: left;
  color: #fff;
  margin-left: 12px;
}
.top4in1img2{
  width: 25px;
  height: 25px;
  border-radius: 4px;
  background: #fff;
  margin-left: 12px;
}
.top4in1left{
  display: flex;
  flex-direction: row;
}
.top4in1right{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
  margin-right: 12px;
}
.threemk{
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  /* border: 1px solid red; */
  padding-bottom: 12px;
}
.threemkper{
  position: relative;
  width: 98px;
  height: 66px;
  /* border: 1px solid red; */
  margin-top: 16px;
}
.threemkperimg{
  position: absolute;
  width: 57px;
  height: 60px;
  /* border: 1px solid red; */
  right: 0px;
  bottom: 0px;
}
.threemkperin{
  width: 83px;
  /* border: 1px solid red; */
  margin-top: 6px;
}
.threemkperin1{
  display: flex;
  flex-direction: row;
  align-items: center;
}
.threemkperin1bor{
  width: 1.5px;
  height: 25px;
  background: #2375f2;
}
.threemkperin1text{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 12px;
  line-height: 21px;
}
.threemkperin2{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 13px;
  line-height: 21px;
  margin-top: 6px;
}
.tlmk {
  width: 93.3%;
  /* border: 1px solid red; */
  display: flex;
  flex-direction: row;
  padding-bottom: 12px;
}
.tlmkin {
  width: 60px;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-right: 40px;
}
.tlmkinbor {
  width: 12px;
  height: 12px;
  border-radius: 50px;
}
.tlmkintext {
  margin-left: 5px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: center;
  color: #000;
}
.maptitle{
  font-family: "PingFang SC Heavy";
  font-weight: 900;
  font-size: 12px;
  text-align: center;
  color: #3266c2;
}
.tooltipcla{
  font-family: "PingFang SC Heavy";
  font-weight: 900;
  font-size: 12px;
  text-align: center;
  color: #3266c2;
}
.map-tooltip-list{
  color: red;
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
