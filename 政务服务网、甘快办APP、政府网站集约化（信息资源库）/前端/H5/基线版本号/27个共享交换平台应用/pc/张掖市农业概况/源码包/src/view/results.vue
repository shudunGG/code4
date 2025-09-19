<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <div class="card">
        <div class="title">
          <div class="line"></div>
          <div class="dq">种植业</div>
        </div>
        <div class="box1" v-if="list3">
          <div class="box1_left">
            <div class="tag"></div>
            <div style="padding-top:20px">蔬菜面积、产量</div>
            <div style="padding-top:20px" class="ab">{{ list2.scmj }}万亩、{{ list2.sccl }}万吨</div>
          </div>
          <div class="box1_right">
            <div class="tag"></div>
            <div style="padding-top:20px">年末粮食面积、产量</div>
            <div style="padding-top:20px" class="ab">{{ list2.nmgdmj }}</div>
          </div>
        </div>
        <div class="box1">
          <div class="box2">
            <div>
              <img
                style="width: 52px; height: 52px; margin-right: 20px"
                src="../assets/img/14.png"
                alt=""
              />
            </div>
            <div style="display: flex; flex-direction: column">
              <span style="color: #3266c2; text-align: left; margin-bottom: 8px"
                >粮食、油料、蔬菜播种面积</span
              >
              <span style="color: #000"
                >{{ list4.ls }}、{{ list4.yl }}、{{ list4.sc }}万亩</span
              >
            </div>
          </div>
          <div class="box2">
            <div>
              <img
                style="width: 52px; height: 52px; margin-right: 20px"
                src="../assets/img/12.png"
                alt=""
              />
            </div>
            <div style="display: flex; flex-direction: column">
              <span style="color: #3266c2; text-align: left; margin-bottom: 8px"
                >粮食、油料、蔬菜播种产量</span
              >
              <span style="color: #000"
                >{{ list3.ls }}、{{ list3.yl }}、{{ list3.sc }}万吨</span
              >
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="title">
          <div class="line"></div>
          <div class="dq">农业自动化</div>
        </div>
        <div class="box1">
          <div class="box1_left" style="width: calc((100% - 40px) / 3);">
            <div class="tag"></div>
            <div style="padding-top:20px">农机拥有量</div>
            <div style="padding-top:20px" class="ab">{{ list.njyyl }}台</div>
          </div>
          <div class="box1_right" style="width: calc((100% - 40px) / 3);">
            <div class="tag"></div>
            <div style="padding-top:20px">农机总动力</div>
            <div style="padding-top:20px" class="ab">{{ list.njzdl }}万千瓦特</div>
          </div>
          <div class="box21" style="width: calc((100% - 40px) / 3);">
          <div>
            <img
              style="width: 52px; height: 52px; margin-right: 20px"
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
              width: 100%;
              justify-content: space-around;
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
              {{ list1[0].lx }}&nbsp;&nbsp;
              {{ list1[0].sl }}台
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
              {{ list1[1].lx }}&nbsp;&nbsp;
              <div>
              {{ list1[1].sl }}台
            </div>
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
              {{ list1[2].lx }}&nbsp;&nbsp;
              {{ list1[2].sl }}台
            </div>
            <!-- <div style="font-size: 15px">{{ list1[0].sl }} 台</div> -->
          </div>
          <!-- <div
            style="
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              height: 80px;
            "
          >
            <div style="display: flex; align-items: center; font-size: 15px">
              {{ list1[0].sl }}台
            </div>
            <div style="display: flex; align-items: center; font-size: 15px">
              {{ list1[1].sl }}台
            </div>
            <div style="display: flex; align-items: center; font-size: 15px">
              {{ list1[2].sl }}台
            </div>
          </div> -->
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
const list1 = ref([]);
const list2 = ref([]);
const list3 = ref([]);
const list4 = ref([]);

onMounted(() => {
  state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
  console.log(state.dataList);
  list.value = state.dataList[0];
  // list.value = state.dataList
  console.log(list.value);
  getData1();
  getData2();
  getData3();
  getData4();
});
const getData1 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "27241ec7-4222-47dd-8bf9-a2ffc3946b22",
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
      list1.value = resStart.custom.result.cockpitData;
      console.log(list1.value);
    },
    () => {
      loading.close();
      ElMessage.error("网络繁忙，请稍后再试");
    }
  );
};
const getData2 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "abea1f65-594f-425e-af46-5ce2e83f39c2",
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
      list2.value = resStart.custom.result.cockpitData[0];
      console.log(list2.value);
    },
    () => {
      loading.close();
      ElMessage.error("网络繁忙，请稍后再试");
    }
  );
};
const getData3 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "aa85957e-ffc2-4db3-8c48-a2693a86d8d5",
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
      list3.value = resStart.custom.result.cockpitData[0];
      console.log(list3.value);
    },
    () => {
      loading.close();
      ElMessage.error("网络繁忙，请稍后再试");
    }
  );
};
const getData4 = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "e26ba482-def3-4de1-a2c1-77e7b4360210",
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
      list4.value = resStart.custom.result.cockpitData[0];
      console.log(list4.value);
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
  padding: 12px 15px;
  position: relative;
  width: calc((100% - 16px) / 2);
  height: 148px;
  background: url("../assets/img/img2.png") no-repeat center center / 100%;
  > div:nth-child(2) {
    margin-bottom: 8px;
  }
}
.box1_right {
  padding: 12px 12px;
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
  align-items: center;
  padding: 12px 15px;
  position: relative;
  margin-bottom: 12px;
  width: calc((100% - 16px) / 2);
  height: 148px;
  background: url("../assets/img/15.png") no-repeat 100%;
  .box2_child div:first-child {
    margin-bottom: 8px;
  }
}
.box21 {
  color: #fff;
  font-size: 15px;
  display: flex;
  align-items: center;
  padding: 12px 15px;
  position: relative;
  margin-bottom: 12px;
  width: calc((100% - 16px) / 2);
  height: 148px;
  background: url("../assets/img/15.png") no-repeat   100%;
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
.ab {
  font-size: 12px;
}
.footer {
  width: 100%;
  // position: relative;
  // bottom: 0;
}
.footerIn {
  background: url("../assets/img/footer.png") no-repeat;
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