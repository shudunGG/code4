<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container">
    <div class="card"  v-if="list  && list.length > 0">
      <div class="title">
        <div class="line"></div>
        <div class="dq">大气环境</div>
      </div>
      <div class="box1">
        <div class="box1_left">
          <div class="tag"></div>
          <div>PM2.5浓度均值：</div>
          <div>{{list[0].pm25}}ng/㎡</div>
        </div>
        <div class="box1_right">
          <div class="tag"></div>
          <div>PM10浓缩均值：</div>
          <div>{{list[0].pm10}}ng/㎡</div>
        </div>
      </div>
      <div class="box2">
        <div class="tag"></div>
        <div class="box2_child">
          <div>优良天数</div>
          <div>{{list[0].gooddays}}天</div>
        </div>
        <div class="box2_child">
          <div>占比</div>
          <div>{{list[0].gooddaysrate}}%</div>
        </div>
      </div>
    </div>

    <div class="card" v-if="listw && listw.cockpitData && listw.cockpitData.length > 0">
      <div class="title">
        <div class="line"></div>
        <div class="aq">{{ listw ? listw.normName : '暂无数据' }}</div>
      </div>
    <div class="box3" >
        <div class="box3_left">
          <div class="tag2"></div>
          <div class="water">水质达标比例（国控）</div>
      
          <div>{{ listw.cockpitData[0].szdbbl_gk }}％</div>
        </div>
   <div class="box3_right">
          <div class="tag2"></div>
          <div class="water">水质达标比例（省级）</div>
          <div>{{ listw.cockpitData[0].szdbbl_sj }}％</div>
        </div>
      </div> 
      <div class="box4">
        <div class="tag3"></div>
        <div class="box4_child">
          <div class="water">县级集中式饮用水水源地水质达标率</div>
          <div>{{ listw.cockpitData[0].xjjzsyyssydszdbl }}％</div>
        </div>
      </div>
      <div class="box4">
        <div class="tag3"></div>
        <div class="box4_child">
          <div class="water">地级集中式饮用水水源地水质达标率</div>
          <div>{{ listw.cockpitData[0].djjzsyyssydszdbl }}％</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, ref, nextTick, onUnmounted } from 'vue';
import appApi from '../api/index';
import hex_md5 from '../tools/md5';
import { showLoadingToast, closeToast, showDialog, showToast, showConfirmDialog } from 'vant';
import { useRoute, useRouter } from 'vue-router';
import sm2 from '../tools/tool';
const router = useRouter();
const route = useRoute();
const list = ref([]);
const listv = ref([]);
const listw = ref([]);
const loading = ref(false);

onMounted(() => {
  getData();
  getDataw();
});
const getData = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: '36ca7aaf-73c5-464e-b63c-295429d20e9a',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    list.value =JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData.filter(item => item.district === "张掖市")
console.log(  list.value );

    // filteredData.value = data.value.filter(item => item.district === "张掖市");



    
  });
};
const getDataw = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: '4dae6c87-ef93-4074-abaa-896e1a480df1',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    let wat=JSON.parse(sm2.sm2Decrypt(res.data)).custom.result;
    listw.value = wat
    
  });
};
</script>

<style lang="less" scoped>
* {
  // font-size: 16px;
  box-sizing: border-box;
}
.aq {
  font-family: 'PingFang SC Bold';
  font-weight: 700;
  font-size: 18px !important;
  text-align: left;
  color: #000;
}
.container {
  background-color: #f6f6f8;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 12px 0;
}
.card {
  background: #fff;
  padding: 12px 12px 0;
  margin-bottom: 12px;
}
.title {
  display: flex;
  align-items: center;
  font-weight: 700;
  font-size: 18px;
  text-align: left;
  color: #000;
  margin-bottom: 12px;
}
.line {
  width: 3.5px;
  height: 15px;
  border-radius: 1.75px;
  background: #3266c2;
  margin-right: 8px;
}
.box1 {
  color: #fff;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}
.box1_left {
  padding: 12px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 74px;
  background: url('../assets/img/img2.png') no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box1_right {
  padding: 12px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 74px;
  background: url('../assets/img/img1.png') no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.tag {
  position: absolute;
  width: 3px;
  height: 12px;
  background: #fff;
  opacity: 0.5;
  left: 0;
  top: 12px;

  font-family: 'PingFang SC Medium';
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #fff;
}
.box2 {
  color: #fff;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 15px;
  position: relative;
  margin-bottom: 12px;
  width: 100%;
  height: 74px;
  background: url('../assets/img/img5.png') no-repeat center center / 100%;
  .box2_child div:first-child {
    margin-bottom: 8px;
  }
}
.box3 {
  height: 66px;
  color: #000;
  font-size: 15px;
  display: flex;
  font-weight: 500;
  justify-content: space-between;
  margin-bottom: 12px;
}
.box3_left {
  padding: 8px 13.5px;
  padding-right: 0;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 66px;
  background: url('../assets/img/img4.png') no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box3_right {
  padding: 8px 13.5px;
  padding-right: 0;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 66px;
  background: url('../assets/img/img4.png') no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.tag2 {
  position: absolute;
  left: 0;
  top: 8px;
  width: 1.5px;
  height: 25px;
  background: #ee911a;
}
.tag3 {
  position: absolute;
  left: 0;
  top: 8px;
  width: 1.5px;
  height: 25px;
  background: #2375f2;
}
.box4 {
  color: #000;
  margin-bottom: 12px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 66px;
  background: url('../assets/img/img3.png') no-repeat center center / 100%;
  .box4_child div:first-child {
    font-family: 'PingFang SC Medium';
    font-weight: 500;
    font-size: 15px;
    text-align: left;
    color: #000;
  }
}
.water {
  font-family: 'PingFang SC Medium';
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #000;
}
</style>
