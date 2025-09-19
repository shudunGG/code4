<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div class="card">
        <div style="display: flex">
          <div class="box2" style="margin-right: 20px">
            <img src="../assets/img/icon2.png" alt="" />
            <div class="box2_right">
              <div>流域面积1000平方公里以上河道数量</div>
              <div class="box2_child">
                <div>{{ info.lymj50ys ? info.lymj50ys + "条" : "--" }}</div>
              </div>
            </div>
          </div>
          <div class="box3">
            <img src="../assets/img/icon1.png" alt="" />
            <div class="box3_right">
              <div>流域面积1000平方公里以下河道数量</div>
              <div class="box3_child">
                <div>{{ info.lymj50yx ? info.lymj50yx + "条" : "--" }}</div>
              </div>
            </div>
          </div>
        </div>

        <div class="box1">
          <div class="box1_left">
            <div class="tag"></div>
            <div style="padding-top: 20px">河长人数</div>
            <div style="padding-top: 20px">
              {{ info.hezrs ? info.hezrs + "人" : "--" }}
            </div>
          </div>
          <div class="box1_right">
            <div class="tag"></div>
            <div style="padding-top: 20px">湖长人数</div>
            <div style="padding-top: 20px">
              {{ info.huzrs ? info.huzrs + "人" : "--" }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { toRefs, onBeforeMount, reactive, onMounted, watch } from "vue";
import * as echarts from "echarts";
// export default {
//   setup() {
const state = reactive({
  dataList: {},
});
const info = ref({});

onMounted(() => {
  state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
  info.value = state.dataList[0];
});
</script>
<style scoped lang="less">
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
  padding: 12px;
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
  width: 67%;
  color: #fff;
  font-size: 18px;
  display: flex;
  justify-content: space-between;
}
.box1_left {
  padding: 12px 15px;
  border-radius: 4px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 148px;
  background: url("../assets/img/img2.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box1_right {
  border-radius: 4px;
  padding: 12px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 148px;
  background: url("../assets/img/img1.png") no-repeat center center / 100%;
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
}
.box2 {
  border-radius: 4px;
  color: #fff;
  font-size: 18px;
  display: flex;
  // justify-content: space-between;
  align-items: center;
  padding: 12px 12px;
  position: relative;
  margin-bottom: 12px;
  width: 100%;
  height: 148px;
  background: url("../assets/img/img5.png") no-repeat  100%;
  img {
    width: 62px;
    height: 62px;
    margin-right: 21px;
  }
  .box2_child {
    display: flex;
    color: #fff;
  }
}
.box2_right {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  height: 100%;
  > div:first-child {
    font-weight: 500;
    font-size: 18px;
    text-align: left;
  }
}
.box3 {
  color: #fff;
  font-size: 18px;
  border-radius: 4px;
  display: flex;
  // justify-content: space-between;
  align-items: center;
  padding: 12px 12px;
  position: relative;
  margin-bottom: 12px;
  width: 100%;
  height: 148px;
  background: url("../assets/img/img4.png") no-repeat  100%;
  img {
    width: 62px;
    height: 62px;
    margin-right: 21px;
  }
  .box3_child {
    display: flex;
    color: #fff;
  }
}
.box3_right {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  height: 100%;
  > div:first-child {
    font-weight: 500;
    font-size: 18px;
    text-align: left;
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
  color: #fff;
  margin-bottom: 12px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 45px;
  background: url("../assets/img/img3.png") no-repeat center center / 100%;
}
.box4_child {
  display: flex;
  align-items: center;
  img {
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.box5 {
  color: #000;
  margin-bottom: 12px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 45px;
  background: url("../assets/img/img4.png") no-repeat center center / 100%;
}
.box5_child {
  display: flex;
  align-items: center;
  img {
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.box6 {
  color: #fff;
  margin-bottom: 12px;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 13.5px;
  position: relative;
  width: 100%;
  height: 45px;
  background: url("../assets/img/img6.png") no-repeat center center / 100%;
}
.box6_child {
  display: flex;
  align-items: center;
  img {
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.content {
  flex: 1;
}
</style>