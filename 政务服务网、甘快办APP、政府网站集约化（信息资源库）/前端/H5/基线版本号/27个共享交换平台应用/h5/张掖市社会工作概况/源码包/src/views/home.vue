<template>
 <div>
  <div class="app">
    <div class="content">
      <div class="box">
      <div class="boxLeft">
        <div class="line"></div>
        <span>婚姻登记</span>
      </div>
    </div>
     <div class="hydj">
        <div class="hydjBox hydjBox1">
          <span class="jsText">本年累计全市结婚登记对数</span>
          <span class="nums">{{cockpitDatajh.jhdjrs}}对</span>
        </div>
        <div class="hydjBox hydjBox2">
          <span class="jsText">本市累计全市离婚登记对数</span>
          <span class="nums">{{cockpitDatajh.lhdjrs}}对</span>
        </div>
      </div>
      <div class="qxdj">
        <div class="circle"></div>
          <span>各县区婚姻登记情况</span>
        </div>
       <div class="djType">
        <div class="djTypeItem">
          <div style=" background: linear-gradient(180.00deg, #247cf8 0%, #239f83 100%);"></div>
          <div>结婚登记</div>
        </div>
        <div class="djTypeItem">
          <div style="background: linear-gradient(180.00deg, #f59045 0%, #f50 100%);"></div>
          <div>离婚登记</div>
        </div>
       </div>
       <div>
        <div class="dw">单位（对）</div>
        <div class="chart" ref="chartRef"></div>
       </div>
    </div>
    <div style="margin-top:12px" class="content">
      <div class="box">
       <div class="boxLeft">
        <div class="line"></div>
        <span>志愿服务</span>
       </div>
    </div>
    <div class="zyList one">
       <div class="zyItem">
        <img src="../assets/img/6.png" alt="">
        <span>志愿者服务组织数量</span>
       </div>
       <div class="zyItemNums">
        {{zyfwzzsl}}个
       </div>
    </div>
     <div class="zyList two">
       <div class="zyItem zyItems">
        <img src="../assets/img/7.png" alt="">
        <span>实名注册志愿者人数</span>
       </div>
       <div class="zyItemNums" style="color:#000">
        {{smzczyzrs}}个
       </div>
    </div>
     <div class="zyList three">
       <div class="zyItem zyItems">
        <img src="../assets/img/8.png" alt="">
        <span>志愿服务项目数量</span>
       </div>
       <div class="zyItemNums" style="color:#000">
        {{zyfwxmsl}}个
       </div>
    </div>
    </div>
    <div style="margin-top:12px" class="content">
      <div class="box">
       <div class="boxLeft">
        <div class="line"></div>
        <span>殡葬服务</span>
       </div>
      </div>
      <div class="zyList four" style="height:50px">
       <div class="zyItem">
        <img src="../assets/img/12.png" alt="">
        <span>殡葬服务中心数量</span>
       </div>
       <div class="zyItemNums">
        {{bzfwzxgs}}个
       </div>
    </div>
    <div class="zyList five" style="height:50px">
       <div class="zyItem">
        <img src="../assets/img/13.png" alt="">
        <span style="color:#3266C2">公益性骨灰堂个数</span>
       </div>
       <div class="zyItemNums" style="color:#000000">
        {{gyxghtgs}}个
       </div>
    </div>
    </div>
 </div>
     <div class="footer">
        <div class="footerIn">
            本服务由甘肃省张掖市提供
        </div>
    </div>
 </div>
</template>
<script setup>
 import {ref,onMounted, reactive} from 'vue'
 import {request} from '../tools/request'
 import tools from '../tools/tool'
 import * as echarts from 'echarts';
 const {sm2Encrypt,sm2Decrypt} = tools
const chartRef = ref(null);
const cockpitDatajh = reactive({
    jhdjrs:0,
    lhdjrs:0,
})
const zyfwzzsl = ref(0)
const smzczyzrs = ref(0)
const zyfwxmsl = ref(0)
const bzfwzxgs = ref(0)
const gyxghtgs = ref(0)
const cockpitData = reactive([])
const qxjhdjrs = reactive([])
const qxlhdjrs = reactive([])
//婚姻登记
const getList = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"e3a31ed2-bb9b-41b3-a323-375a1bdd9861",
            "fieldList":'[]'}),'0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b')
    }
    const params = {
        interface_id:'zyjtyyjk',
        app_id:'zyjtyyjkrzoeeih',
        biz_content:JSON.stringify(data)
    }
    let res = await request(params)
    if(res.code == 200){
        const result = sm2Decrypt(res.data,'3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b')
        const result1 = JSON.parse(result)
        if(result1.custom.result.cockpitData.length){
           cockpitDatajh.jhdjrs = result1.custom.result.cockpitData[0].jhdjrs
           cockpitDatajh.lhdjrs = result1.custom.result.cockpitData[0].lhdjrs
        }
    }
}
//区县婚姻登记情况
const getList1 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"ebe643ad-b682-447f-ac96-768e5dfc830e",
            "fieldList":'[]'}),'0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b')
    }
    const params = {
        interface_id:'zyjtyyjk',
        app_id:'zyjtyyjkrzoeeih',
        biz_content:JSON.stringify(data)
    }
    let res = await request(params)
    if(res.code == 200){
        const result = sm2Decrypt(res.data,'3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b')
        const result1 = JSON.parse(result)
        console.log(result1)
        if(result1.custom.result.cockpitData.length){
            result1.custom.result.cockpitData.forEach(item => {
              cockpitData.push(item.qxmc)
              qxjhdjrs.push(item.qxjhdjrs)
              qxlhdjrs.push(item.qxlhdjrs)
            });
        }
    }
}
//志愿服务
const getList2 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"bd1cac7a-d78a-48c5-9b3c-2b9171f02dde",
            "fieldList":'[]'}),'0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b')
    }
    const params = {
        interface_id:'zyjtyyjk',
        app_id:'zyjtyyjkrzoeeih',
        biz_content:JSON.stringify(data)
    }
    let res = await request(params)
    if(res.code == 200){
        const result = sm2Decrypt(res.data,'3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b')
        const result1 = JSON.parse(result)
        if(result1.custom.result.cockpitData.length){
          const list = result1.custom.result.cockpitData.filter(item=> item.tbbm == "张掖市民政局")
            zyfwzzsl.value = list.reduce((accumulator,currentValue)=>{
              return accumulator + Number(currentValue.zyfwzzsl.substring(0,currentValue.zyfwzzsl.length-1));
            },0)
          smzczyzrs.value = list.reduce((accumulator,currentValue)=>{
              return accumulator + Number(currentValue.smzczyzrs.substring(0,currentValue.smzczyzrs.length-1));
            },0)
            zyfwxmsl.value = list.reduce((accumulator,currentValue)=>{
              return accumulator + Number(currentValue.zyfwxmsl.substring(0,currentValue.zyfwxmsl.length-1));
            },0)
        }
       
    }
}
//殡葬服务
const getList3 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"7f9995b4-8860-4f39-a5c0-0f0ea8e28309",
            "fieldList":'[]'}),'0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b')
    }
    const params = {
        interface_id:'zyjtyyjk',
        app_id:'zyjtyyjkrzoeeih',
        biz_content:JSON.stringify(data)
    }
    let res = await request(params)
    if(res.code == 200){
        const result = sm2Decrypt(res.data,'3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b')
        const result1 = JSON.parse(result)
        if(result1.custom.result.cockpitData.length){
            bzfwzxgs.value = result1.custom.result.cockpitData[0].bzfwzxgs
            gyxghtgs.value = result1.custom.result.cockpitData[0].gyxghtgs
        }
       
    }
}


onMounted( async() => {
   await getList()
   await getList1()
   await getList2()
   await getList3()
   if (chartRef.value){
    const chart = echarts.init(chartRef.value);
    const options = {
     xAxis: {
       data: cockpitData,
       axisLabel:{
        fontWeight: '700',
        color:'#247CFA',
        fontSize: 12,
        fontFamily: "PingFang SC Bold"
       }
     },
     yAxis: {
      axisLabel:{
        fontWeight: '700',
        color:'#999',
        fontSize: 12,
        fontFamily: "PingFang SC Bold",
        width:20,
        rotate:45
      }
     },
     series: [
       {
         type: 'bar',
         barGap: '0%',
         data: qxjhdjrs,
         itemStyle: {
           normal: {
             color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
               { offset: 0, color: '#247cfa' },
               { offset: 1, color: '#41BE94' }
             ])
           }
         },
         label:{
           show: true, 
           position: 'top', 
           color: '#000',
           fontSize: 12,
           rotate: 45,
           fontWeight:'500',
           fontFamily:'PingFang SC Medium',
           offset:[15,10]
         },
       },
       {
         type: 'bar',
         barGap: '0%',
         data: qxlhdjrs,
         itemStyle: {
           normal: {
             color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
               { offset: 0, color: '#F59045' },
               { offset: 1, color: '#FF5500' }
             ])
           }
         },
         label:{
           show: true, 
           position: 'top', 
           color: '#000',
           fontSize: 12,
           rotate: 45,
           fontWeight:'500',
           fontFamily:'PingFang SC Medium',
            offset:[10,10]
         }
       }
     ],
     barCategoryGap: '30%',
   };
    chart.setOption(options);
   }

 });
</script>
<style scoped>
 .app{
    margin-top: 12px;
 }
 .content{
  background: #fff;
  padding: 12px;
 }
 .box{
   display: flex;
   align-items: center;
 }
 .boxLeft{
  display: flex;
  align-items: center;
 }
 .line{
  width: 4px;
  height: 18px;
  border-radius: 2px;
  background: #3266C2;
  margin-right: 8px;
  margin-top: 2px;
 }
 .boxLeft>span{
  font-weight: 700;
  font-size: 18px;
  color: #000;
 }
 .hydj{
  margin-top: 12px;
  display: flex;
  justify-content: space-between;
 }
 .hydjBox{
  box-sizing: border-box;
  height: 78px;
  padding: 12px 15px;
  width: 172px;
 }
 .hydjBox1{
    background: url('../assets/img/1.png') no-repeat;
  background-size: 100%;
 }
 .hydjBox2{
  background: url('../assets/img/2.png') no-repeat;
   background-size: 100%;
 }
 .jsText{
    font-weight: 500;
    font-size: 15px;
    color: #fff;
    letter-spacing: 0.88px;
    line-height: 29px;
 }
 .nums{
  display: inline-block;
  font-weight: 500;
  font-size: 15px;
  color: #fff;
  text-align: right;
  margin-left: 16px;
 }
 .qxdj{
   margin-top: 16px;
   display: flex;
   align-items: center;
 }
 .qxdj>span{
  font-weight: 700;
  font-size: 17px;
  color: #000;
 }
 .circle{
   background: #247CFA;
   width: 8px;
   height: 8px;
   border-radius: 50%;
   margin-right: 8px;
 }
 .djType{
   margin-top: 12px;
   display: flex;
   align-items: center;
   justify-content: space-between;
   padding: 0 64px;
 }
 .djTypeItem{
  display: flex;
  align-items: center;
 }
 .djTypeItem>div:first-child{
  width: 20px;
  height: 10px;
  border-radius: 2px;
 }
 .djTypeItem>div:last-child{
   margin-left: 8px;
   font-weight: 500;
   font-size: 15px;
   text-align: center;
   color: #000;
 }
.chart{
    width: 100%;
    height: 275px;
}
.zyList{
  box-sizing: border-box;
   margin-top: 12px;
   width: 100%;
   height: 48px;
   display: flex;
   align-items: center;
   justify-content: space-between;
   padding: 0 12px;
}
/deep/ canvas{
  height: 350px !important;
  position: relative !important;
  top:-25px !important
}
.listBg{
  width: 100%;
  height: 100%;
}
.one{
  background: url('../assets/img/3.png') no-repeat;
  background-size: 100%;
}
.two{
  background: url('../assets/img/4.png') no-repeat;
  background-size: 100%;
}
.three{
  background: url('../assets/img/5.png') no-repeat;
  background-size: 100%;
}
.four{
  background: url('../assets/img/10.png') no-repeat;
  background-size: 100%;
}
.five{
   background: url('../assets/img/11.png') no-repeat;
  background-size: 100%;
}
.zyItem{
  display: flex;
  align-items: center;
}
.zyItem>img{
  width: 25px;
  height: 25px;
  margin-right: 12px;
}
.zyItem>span{
  font-weight: 500;
  font-size: 15px;
  color: #fff;
}
.zyItems>span{
  color: #000;
}
.zyItemNums{
  font-weight: 500;
  font-size: 15px;
  color: #fff;
}
.dw{
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  color: #000;
  position: relative;
  top:20px
}
.footer{
    width: 100%;
}
.footerIn{
    background: url('../assets/img/footer.png') no-repeat;
    background-size: 100%;
    height: 58.5px;
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 15px;
    text-align: center;
    color: #999;
    line-height: 58.5px;
}
</style>