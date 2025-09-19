<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div class="card" v-if="list && list.length > 0">
        <div class="title">
          <div class="line"></div>
          <div class="dq">大气环境</div>
        </div>
        <div class="box1">
          <div class="box1_left">
            <div class="tag"></div>
            <div>PM2.5浓度均值：</div>
            <div>{{ list[0].pm25 }}ng/㎡</div>
          </div>
          <div class="box1_right">
            <div class="tag"></div>
            <div>PM10浓缩均值：</div>
            <div>{{ list[0].pm10 }}ng/㎡</div>
          </div>
        </div>
        <div class="box2">
          <div class="tag"></div>
          <div class="box2_child">
            <div>优良天数</div>
            <div>{{ list[0].gooddays }}天</div>
          </div>
          <div class="box2_child">
            <div>占比</div>
            <div>{{ list[0].gooddaysrate }}%</div>
          </div>
        </div>
      </div>

      <div
        class="card"
        v-if="listw && listw.cockpitData && listw.cockpitData.length > 0"
      >
        <div class="title">
          <div class="line"></div>
          <div class="aq">{{ listw ? listw.normName : "暂无数据" }}</div>
        </div>
        <div class="box3">
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
        <div class="box5">
          <div class="tag3"></div>
          <div class="box4_child">
            <div class="water">地级集中式饮用水水源地水质达标率</div>
            <div>{{ listw.cockpitData[0].djjzsyyssydszdbl }}％</div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { toRefs, onBeforeMount, reactive, onMounted, watch } from "vue";
import { serveAPI } from "@/api/serve";
import tools from "@/tools/tool";
import { ElMessage, ElLoading } from "element-plus";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  dataList: {},
  secretKey:
    "00e4bce5f10d8a3e96b7e62df284cda5078c8f263056a0f6305370a5e63438bd20",
  PublicKey:
    "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b",
  PrivateKey:
    "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b",
});
const list = ref([]);
const listv = ref([]);
const listw = ref([]);

onMounted(() => {
  state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.dataList);
  list.value = state.dataList.filter((item) => item.district === "张掖市");
  // list.value = state.dataList
  console.log(list.value);
  getDataw();
});
const getDataw = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "4dae6c87-ef93-4074-abaa-896e1a480df1",
        fieldList: [],
      }),
      state.PublicKey
    ),
  };
  serveAPI.queryListHeaders("zyjtyyjkrzoeeih", "zyjtyyjk", param).then(
    (res) => {
      let resStart;
      resStart = JSON.parse(res.data.data);
      resStart = JSON.parse(tools.decryptSM2(resStart.data, state.PrivateKey));
      loading.close();
      listw.value = resStart.custom.result;
    },
    () => {
      loading.close();
      ElMessage.error("网络繁忙，请稍后再试");
    }
  );
};
</script>
<style scoped lang='less'>
.result-container table {
  margin-bottom: 0.2rem;
}

.result-container table td {
  background: #ffffff;
  text-align: center;
}

.result-container table thead td {
  font-size: 0.16rem;
  font-weight: bold;
}

.result-container table td,
.result-container table th {
  border-right: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
  font-size: 0.14rem;
  padding: 0 0.14rem;
}

.tips {
  margin-top: 0.3rem;
  color: #999999;
  text-align: center;
}

.block {
  text-align: center;
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
  padding: 25px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 104px;
  background: url("../assets/img/img2.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box1_right {
  padding: 25px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 104px;
  background: url("../assets/img/img1.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.tag {
  position: absolute;
  width: 3px;
  height: 20px;
  background: #fff;
  opacity: 0.5;
  left: 0;
  top: 12px;
  font-family: "PingFang SC Medium";
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
  height: 104px;
  background: url("../assets/img/img5.png") no-repeat center center / 100%;
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
  height: 106px;
  background: url("../assets/img/img4.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box3_right {
  padding: 8px 13.5px;
  padding-right: 0;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 106px;
  background: url("../assets/img/img4.png") no-repeat center center / 100%;
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
  // margin-bottom: 12px;
  margin-top: 60px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 106px;
  background: url("../assets/img/img3.png") no-repeat center center / 100%;
  .box4_child div:first-child {
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 15px;
    text-align: left;
    color: #000;
  }
}
.box5 {
  color: #000;
  // margin-bottom: 12px;
  margin-top: 20px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 106px;
  background: url("../assets/img/img3.png") no-repeat center center / 100%;
  .box4_child div:first-child {
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 15px;
    text-align: left;
    color: #000;
  }
}
.water {
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: left;
  color: #000;
}
</style>