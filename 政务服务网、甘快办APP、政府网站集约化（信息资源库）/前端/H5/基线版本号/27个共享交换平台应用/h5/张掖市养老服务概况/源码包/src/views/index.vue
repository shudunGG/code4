<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container">
    <div class="content">
      <div class="card">
        <div class="title">
          <div class="line"></div>
          <div>养老机构</div>
        </div>
        <div class="box1">
          <div class="box1_left">
            <div class="tag"></div>
            <div>全市养老机构数量</div>
            <div>{{ info.qsyljgzs ? info.qsyljgzs + '个' : '--' }}</div>
          </div>
          <div class="box1_right">
            <div class="tag"></div>
            <div>设置床位数量</div>
            <div>{{ info.szcwzs ? info.szcwzs + '张' : '--' }}</div>
          </div>
        </div>
        <div class="box2">
          <div class="tag"></div>
          <img src="../assets/img/icon1.png" alt="" />
          <div class="box2_right">
            <div>护理型床位数量</div>
            <div class="box2_child">
              <div>{{ info.hlxcwsl ? info.hlxcwsl + '张' : '--' }}</div>
              <div>
                占比：<span>{{ info.hlxcwslzb || '--' }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="title">
          <div class="line"></div>
          <div>养老服务设施</div>
        </div>
        <div class="box4">
          <div class="box4_child">
            <img src="../assets/img/icon3.png" alt="">
            <div>综合养老服务中心数量</div>
          </div>
          <div>
            {{ info.zhylfwzxsl ? info.zhylfwzxsl + '个' : '--' }}
          </div>
        </div>
        <div class="box5">
          <div class="box5_child">
            <img src="../assets/img/icon2.png" alt="">
            <div>综合养老服务中心床位数量</div>
          </div>
          <div>{{ info.zhylfwzxcwsl ? info.zhylfwzxcwsl + '张' : '--' }}</div>
        </div>
      </div>
      <div class="card">
        <div class="title">
          <div class="line"></div>
          <div>居家养老</div>
        </div>
        <div class="box6">
          <div class="box6_child">
            <img src="../assets/img/icon4.png" alt="">
            <div>全市居家养老上门服务人次</div>
          </div>
          <div>
            {{ info.qsjjylsmfwrc || '--' }}
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      本服务由甘肃省张掖市提供
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref, nextTick, onUnmounted } from "vue";
import appApi from "../api/index";
import hex_md5 from "../tools/md5";
import {
  showLoadingToast,
  closeToast,
  showDialog,
  showToast,
  showConfirmDialog,
} from "vant";
import { useRoute, useRouter } from "vue-router";
import sm2 from "../tools/tool";
const router = useRouter();
const route = useRoute();
const info = ref({});
const loading = ref(false);

onMounted(() => {
  getData();
});
const getData = () => {
  const param = {
    params: sm2.sm2Encrypt(JSON.stringify({
      normGuid: "5566febb-4aab-4bba-b971-59bb4942ec38",
      fieldList: []
    })),
  };
  appApi.getList("zyjtyyjk", "zyjtyyjkrzoeeih", param).then((res) => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)));
    info.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0]
  });
};
</script>

<style lang="less" scoped>
* {
  // font-size: 16px;
  box-sizing: border-box;
}
.container {
  background-color: #f6f6f8;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 12px 0 0;
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
  color: #fff;
  font-size: 15px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}
.box1_left {
  border-radius: 4px;
  padding: 12px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 74px;
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
  height: 74px;
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
  color: #fff;
  border-radius: 4px;
  font-size: 15px;
  display: flex;
  // justify-content: space-between;
  align-items: center;
  padding: 12px 12px;
  position: relative;
  width: 100%;
  height: 74px;
  background: url("../assets/img/img5.png") no-repeat center center / 100%;
  img {
    width: 42px;
    height: 42px;
    margin-right: 21px;
  }
  .box2_child {
    display: flex;
    color: #000000;
    > div:last-child {
      margin-left: 56px;
    }
    span {
      color: #3266c2;
    }
  }
}
.box2_right {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  > div:first-child {
    font-weight: 500;
    font-size: 15px;
    text-align: left;
    color: #3266c2;
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
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 66px;
  background: url("../assets/img/img4.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box3_right {
  padding: 8px 13.5px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 66px;
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
  color: #fff;
  border-radius: 4px;
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
.box4_child{
  display: flex;
  align-items: center;
  img{
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.box5 {
  color: #000;
  border-radius: 4px;
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
.box5_child{
  display: flex;
  align-items: center;
  img{
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.box6 {
  color: #fff;
  border-radius: 4px;
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
.box6_child{
  display: flex;
  align-items: center;
  img{
    width: 25px;
    height: 25px;
    margin-right: 12px;
  }
}
.content{
  flex: 1;
}
.footer{
  font-family: "PingFang SC Medium";
  text-align: center;
  line-height: 58.5px;
  color: #999;
  // font-weight: 500;
  font-size: 15px;
  width: 100%;
  height: 58.5px;
  background: url('../assets/img/bg.png') no-repeat center center / 100%;
}
</style>
