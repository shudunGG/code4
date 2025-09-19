<template>
 <div class="app">
    <div class="top">
        <div class="topLeft">
            <img src="../assets/img/2.png" alt="">
            <span>累计建成数量</span>
        </div>
        <div class="topRight">{{cockpitData.ljjssl}}个</div>
    </div>
    <div class="numBox">
        <div class="numBoxLeft">
            <span>本年已建成数量</span>
            <span>{{cockpitData.bnyjssl}}个</span>
        </div>
        <div class="numBoxRight">
            <span>本年任务完成率</span>
            <span>{{cockpitData.bnrwwcs}}%</span>
        </div>
    </div>
    <div class="tableBox">
        <div class="tableTop">
            <div class="line"></div>
            <span>本年县区完成情况</span>
        </div>
        <div class="tabbleList">
            <table>
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>县区</th>
                        <th>计划建成数量</th>
                        <th>实际建成数量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :class="[index % 2 !== 0 ? 'trbg' : '']" v-for="(item,index) in cockpitDataList" :key="index">
                        <td :class="[index == 0 ? 'tdOne' : (index == 1 ? 'tdTwo':(index == 2 ?'tdThree':'moreTd'))]">{{index+1}}</td>
                        <td class="tds">{{item.qxm}}</td>
                        <td class="tds">{{item.jhjssl}}个</td>
                        <td class="tds tdmargin">{{item.sjjssl}}个</td>
                    </tr>
                </tbody>
            </table>
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
 const {sm2Encrypt,sm2Decrypt} = tools
const index = ref(0)
const cockpitData = reactive({
    ljjssl:0,
    bnyjssl:0,
    bnrwwcs:0
})
const cockpitDataList = reactive([])
const getList1 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"f8894712-7818-43b2-bd4d-f958215df15e",
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
           cockpitData.ljjssl = result1.custom.result.cockpitData[0].ljjssl
           cockpitData.bnyjssl = result1.custom.result.cockpitData[0].bnyjssl
           cockpitData.bnrwwcs = result1.custom.result.cockpitData[0].bnrwwcs
        }
    }
}
const getList2 = async()=>{
    const data = {
        'params':sm2Encrypt(JSON.stringify({"normGuid":"7d7d2167-5a3e-4542-9bc7-2d9b5a78eb5e",
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
            cockpitDataList.push(...result1.custom.result.cockpitData)
        }
       
    }
}
onMounted(()=>{
    getList1()
    getList2()
})
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
}
.numBoxLeft{
    box-sizing: border-box;
    width: 48%;
    height: 66px;
    background: url('../assets/img/3.png');
    background-size: 100%;
    padding: 8px 12px;
}
.numBoxLeft>span:first-child{
    font-weight: 500;
    font-size: 15px;
    color: #000;
}
.numBoxLeft>span:last-child{
    margin-top: 8px;
    font-weight: 500;
    font-size: 15px;
    color: #000;
    display: block;
}
.numBoxRight{
    box-sizing: border-box;
    width: 48%;
    height: 66px;
    background: url('../assets/img/4.png');
    background-size: 100%;
    padding: 8px 12px;
}
.numBoxRight>span:first-child{
    font-weight: 500;
    font-size: 15px;
    color: #000;
}
.numBoxRight>span:last-child{
    margin-top: 8px;
    font-weight: 500;
    font-size: 15px;
    color: #000;
    display: block;
}
.tableBox{
    margin-top: 16px;
}
.tableTop{
    display: flex;
    align-items: center;
}
.line{
    width: 4px;
    height: 15px;
    border-radius: 2px;
    background: #3266C2;
    margin-right: 8px;
}
.tableTop>span{
    font-weight: 700;
    font-size: 18px;
    color: #000;
}
.tabbleList{
    margin-top: 12px;
}
table{
    width: 100%;
    border-collapse: collapse;
}
thead{
    border-bottom: 1px solid #eee;
}
thead th{
    padding: 0 4px 12px;
    text-align: center;
    font-weight: 700;
    font-size: 13px;
    color: #999;
}
tbody tr{
    height: 42.5px;
}
tbody .tds{
    padding: 12px 4px;
    text-align: center;
    font-weight: 700;
    font-size: 13px;
    color: #000;
}
.tdOne{
   display: inline-block;
   width: 17px;
   height: 17px;
   background: #e9383e;
   text-align: center;
   line-height: 17px;
   color: #fff;
   margin-left: 14px;
   margin-top: 12px;
}
.tdTwo{
     display: inline-block;
   width: 17px;
   height: 17px;
   background: #EE911A;
   text-align: center;
   line-height: 17px;
   color: #fff;
   margin-left: 14px;
   margin-top: 12px;
}
.tdThree{
     display: inline-block;
   width: 17px;
   height: 17px;
   background: #41BE94;
   text-align: center;
   line-height: 17px;
   color: #fff;
   margin-left: 14px;
   margin-top: 12px;
}
.moreTd{
   display: inline-block;
   width: 17px;
   height: 17px;
   background: #247CFA;
   text-align: center;
   line-height: 17px;
   color: #fff;
   margin-left: 14px;
   margin-top: 12px;
}
.trbg{
   background: #f5f8ff;
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