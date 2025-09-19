<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container">
    <div class="card">
      <div class="title">
        <div class="line"></div>
        <div class="dq">种植业</div>
      </div>
      <div class="box1" v-if="list3">
        <div class="box1_left">
          <div class="tag"></div>
          <div>蔬菜面积、产量</div>
          <div class="ab" >{{ list2.scmj }}万亩、{{ list2.sccl }}万吨</div>
        </div>
        <div class="box1_right">
          <div class="tag"></div>
          <div>年末粮食面积、产量</div>
          <div class="ab">{{ list2.nmgdmj }}</div>
        </div>
      </div>
      <div class="box2">
        <div>
          <img
            style="width: 42px; height: 42px; margin-right: 20px"
            src="../assets/img/14.png"
            alt=""
          />
        </div>
        <div style="display: flex; flex-direction: column">
          <span style="color: #3266c2; text-align: left; margin-bottom: 8px"
            >粮食、油料、蔬菜播种面积</span
          >
          <span style="color: #000">{{ list4.ls }}、{{ list4.yl }}、{{ list4.sc }}万亩</span>
        </div>
      </div>
      <div class="box2">
        <div>
          <img
            style="width: 42px; height: 42px; margin-right: 20px"
            src="../assets/img/12.png"
            alt=""
          />
        </div>
        <div style="display: flex; flex-direction: column">
          <span style="color: #3266c2; text-align: left; margin-bottom: 8px"
            >粮食、油料、蔬菜播种产量</span
          >
          <span style="color: #000">{{ list3.ls }}、{{ list3.yl }}、{{ list3.sc }}万吨</span>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="title">
        <div class="line"></div>
        <div class="dq">农业自动化</div>
      </div>
      <div class="box1">
        <div class="box1_left">
          <div class="tag"></div>
          <div>农机拥有量</div>
          <div class="ab">{{ list.njyyl }}台</div>
        </div>
        <div class="box1_right">
          <div class="tag"></div>
          <div>农机总动力</div>
          <div class="ab">{{ list.njzdl }}万千瓦特</div>
        </div>
      </div>
      <div class="box2">
        <div>
          <img
            style="width: 42px; height: 42px; margin-right: 20px"
            src="../assets/img/13.png"
            alt=""
          />
        </div>
        <div style="display: flex; flex-direction: column">
          <span style="color: #3266c2; text-align: left; margin-bottom: 8px"
            >农业机械化综合水平</span
          >
          <span style="color: #000">{{ list.nyjxhzhsp }}%</span>
        </div>
      </div>
      <div
        style="
          height: 46.5px;
          border-radius: 2px;
          background: #f6f7f9;

          display: flex;
          align-items: center;
          justify-content: center;
        "
      >
        <div
          style="
            font-weight: 600;
            font-size: 16px;

            color: #999;
          "
        >
          各类农机类型数量
        </div>
      </div>

      <div
        v-if="list1 && list1.length > 0"
        style="
          margin-top: 12px;

          margin-bottom: 20px;
          background: #fff;
          height: 105px;
          display: flex;
          flex-direction: row;
          align-items: start;
          justify-content: space-around;
        "
      >
        <div
          style="
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 80px;
          "
        >
          <div style="display: flex; align-items: center; font-size: 15px">
            <p
              style="
                width: 12px;
                height: 12px;
                border-radius: 100%;
                background: #1f66e4;
                margin-right: 5px;
              "
            ></p>
            {{ list1[0].lx }}
          </div>
          <div style="display: flex; align-items: center; font-size: 15px">
            <p
              style="
                width: 12px;
                height: 12px;
                border-radius: 100%;
                background: #fd9137;
                margin-right: 5px;
              "
            ></p>
            {{ list1[1].lx }}
          </div>
          <div style="display: flex; align-items: center; font-size: 15px">
            <p
              style="
                width: 12px;
                height: 12px;
                border-radius: 100%;
                background: #f25424;
                margin-right: 5px;
              "
            ></p>
            {{ list1[2].lx }}
          </div>
          <!-- <div style="font-size: 15px">{{ list1[0].sl }} 台</div> -->
        </div>
        <div
          style="
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 80px;
          "
        >
          <div style="display: flex; align-items: center; font-size: 15px">{{ list1[0].sl }}台</div>
          <div style="display: flex; align-items: center; font-size: 15px">{{ list1[1].sl }}台</div>
          <div style="display: flex; align-items: center; font-size: 15px">{{ list1[2].sl }}台</div>
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
const list1 = ref([]);
const list2 = ref([]);
const list3 = ref([]);
const list4 = ref([]);

const loading = ref(false);

onMounted(() => {
  getData();
  getData1();
  getData2();
  getData3();
  getData4();
});
const getData = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: '98af86a2-01b1-4e20-b620-7dae85fb4486',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)).custom.result, '0');
    list.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0];
  });
};
const getData1 = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: '27241ec7-4222-47dd-8bf9-a2ffc3946b22',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)).custom.result, '1');
    list1.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData;
  });
};
const getData2 = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: 'abea1f65-594f-425e-af46-5ce2e83f39c2',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)).custom.result, '2');
    list2.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0];
  });
};
const getData3 = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: 'aa85957e-ffc2-4db3-8c48-a2693a86d8d5',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0], '3');
    list3.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0];
  });
};
const getData4 = () => {
  const param = {
    params: sm2.sm2Encrypt(
      JSON.stringify({
        normGuid: 'e26ba482-def3-4de1-a2c1-77e7b4360210',
        fieldList: [],
      })
    ),
  };
  appApi.getList('zyjtyyjk', 'zyjtyyjkrzoeeih', param).then(res => {
    console.log(JSON.parse(sm2.sm2Decrypt(res.data)).custom.result, '4');
    list4.value = JSON.parse(sm2.sm2Decrypt(res.data)).custom.result.cockpitData[0];
  });
};
</script>

<style lang="less" scoped>

* {
  // font-size: 16px;
  box-sizing: border-box;
  font-family: 'PingFang SC Medium';
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
  padding: 12px 12px;
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

  align-items: center;
  padding: 12px 15px;
  position: relative;
  margin-bottom: 12px;
  width: 100%;
  height: 74px;
  background: url('../assets/img/15.png') no-repeat center center / 100%;
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
.ab{
  font-size: 12px;
}
.footer{
    width: 100%;
    // position: relative;
    // bottom: 0;

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
