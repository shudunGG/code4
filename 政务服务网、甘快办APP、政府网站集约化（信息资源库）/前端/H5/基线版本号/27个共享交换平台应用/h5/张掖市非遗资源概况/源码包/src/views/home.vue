<template>
 <div class="app">
    <div class="top">
        <div class="topLeft">
            <img src="../assets/img/2.png" alt="">
            <span>非遗项目总数</span>
        </div>
        <div class="topRight">{{cockpitData.countryNum + cockpitData.provinceNum + cockpitData.cityNum + cockpitData.countyNum}}个</div>
    </div>
    <div class="numBox">
        <div class="numBoxItem one" @click="detail('国家级')">
            <div>
              <span>国家级</span>
              <span>{{cockpitData.countryNum}}个</span>
            </div>
            <img  src="../assets/img/7.png" alt="">
        </div>
        <div class="numBoxItem two" @click="detail('省级')">
           <div>
             <span>省级</span>
             <span>{{cockpitData.provinceNum}}个</span>
           </div>
           <img src="../assets/img/8.png" alt="">
        </div>
        <div class="numBoxItem three">
            <span>市级</span>
            <span>{{cockpitData.cityNum}}个</span>
        </div>
        <div class="numBoxItem four">
            <span>县级</span>
            <span>{{cockpitData.countyNum}}个</span>
        </div>
    </div>
     <div class="tops">
        <div class="topLeft">
            <img src="../assets/img/9.png" alt="">
            <span>非遗代表性传承人数</span>
        </div>
        <div class="topRight">{{fydbxccrsl}}人</div>
    </div>
    <div class="chart" ref="chartRef">

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
 import { useRouter } from "vue-router";
const router = useRouter();
 const {sm2Encrypt,sm2Decrypt} = tools
const index = ref(0)
const chartRef = ref(null);
const cockpitData = reactive({
    countryNum:0,
    provinceNum:0,
    cityNum:0,
    countyNum:0
})
const cockpitDataList = reactive([])
const fydbxccrsl = ref(0)
const fydbxccrslList = reactive([])
const fydbxccrsNameList = reactive([])
const getList1 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"33c91899-a7d3-41fd-8262-99158e4ed568",
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
           cockpitData.countryNum = result1.custom.result.cockpitData[0].xmsl
           cockpitData.provinceNum = result1.custom.result.cockpitData[1].xmsl
           cockpitData.cityNum = result1.custom.result.cockpitData[2].xmsl
           cockpitData.countyNum = result1.custom.result.cockpitData[3].xmsl
        }
    }
}
const getList2 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"b0bb789a-2535-4d19-8689-bfd33d469f37",
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
            fydbxccrsl.value = result1.custom.result.cockpitData.reduce((accumulator,currentValue)=>{
              return accumulator + currentValue.fydbxccrsl;
            },0)
            
            result1.custom.result.cockpitData.forEach(item => {
              fydbxccrslList.push({
                value:item.fydbxccrsl,
                name:item.dj,
                itemStyle: { color: handleColor(item.dj) }
              })
              fydbxccrsNameList.push({
                name:item.dj
              })
            });
        }
       
    }
}
const handleColor = (name)=>{
  if(name == '省级'){
    return '#2166E4'
  }else if(name == '市级'){
    return '#FD9237'
  }else if(name == '县级'){
    return '#07ADA2'
  }else{
    return '#2166E4'
  }
}
const detail = (name)=>{
     router.push({
        name:'details',
        query:{
            title:name
        }
     });
}
onMounted( async() => {
   await getList1()
   await getList2()
    console.log(fydbxccrsNameList)
      if (chartRef.value) {
        const chart = echarts.init(chartRef.value);
        const options = {
        legend: {
          selectedMode: false,
          itemShape:'circle',
          orient: 'vertical',
          data: fydbxccrsNameList,
          top:'center',
          itemWidth:12,
          itemHeight:12,
          right:'0',
          textStyle:{
            color:'#000',
            fontSize:16,
            lineHeight:24
          },
          formatter:function(name){
            if(name == '省级'){
              return name + `：    ${fydbxccrslList[0].value}个`
            }else if(name == '市级'){
              return name + `：    ${fydbxccrslList[1].value}个`
            }else if(name == '县级'){
               return name + `：    ${fydbxccrslList[2].value}个`
            }
          }
        },
        series: [
          {
            type: 'pie',
            radius: ['50%', '62%'],
            avoidLabelOverlap: false,
            center: ['30%', '45%'], 
            label: {
              show: false,
              position: 'center'
            },
            labelLine: {
              show: false
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            data: fydbxccrslList,
             silent: true
          }
        ]
     };
        chart.setOption(options);
      }
    });
</script>
<style scoped>
 .app{
    background: #fff;
    margin-top: 12px;
    padding: 12px;
 }
 .top{
    height: 51px;
    background: url('../assets/img/1.png');
    background-size: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 12px;
 }
 .tops{
     height: 51px;
    background: url('../assets/img/10.png');
    background-size: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 12px;
    margin-top: 16px;
 }
 .topLeft{
    display: flex;
    align-items: center;

 }
 .topLeft>img{
    width: 25px;
    height: 25px;
 }
 .topLeft>span{
    margin-left: 12px;
    font-weight: 500;
    font-size: 15px;
    color: #fff;
 }
.topRight{
    font-weight: 500;
    font-size: 15px;
    color: #fff;
}
.numBox{
    margin-top: 16px;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
}
.numBoxItem{
    position: relative;
    box-sizing: border-box;
    width: 48%;
    height: 66px;
    padding: 8px 12px;
}
.numBoxItem span:first-child{
    font-weight: 500;
    font-size: 15px;
    color: #000;
}
.numBoxItem span:last-child{
    margin-top: 8px;
    font-weight: 500;
    font-size: 15px;
    color: #000;
    display: block;
}
.one{
    background: url('../assets/img/3.png');
    background-size: 100%;
}
.two{
    background: url('../assets/img/4.png');
    background-size: 100%;
}
.three{
  background: url('../assets/img/5.png');
    background-size: 100%;  
    margin-top: 16px;
}
.four{
    background: url('../assets/img/6.png');
    background-size: 100%;
    margin-top: 16px;
}
.numBoxItem img{
    position: absolute;
    width: 6px;
    height: 10px;
    top:12px;
    right: 24px;
}
.chart{
    width: 100%;
    height: 300px;
}
.footer{
    position: fixed;
    bottom: 0;
    left:0;
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