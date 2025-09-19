<template>
  <div>
    
    <router-view :key="key"></router-view>
  </div>
</template>

<script setup>
import { getUserInfo, getToken } from "@/api/getUserInfo";
import { serveAPI } from "@/api/serve";
import tools from "@/tools/tool";
import aes from "../tools/aes";
import { createCode } from "@/utils/checkCode";
import { requestJpaas, requestJpaas1 } from "@/api/code";
import { toRefs, reactive, ref, nextTick } from "vue";
import { useRouter } from "vue-router";
import { ElMessage, ElLoading } from "element-plus";


const state = reactive({
  token: "",
  userInfo: "",
  time: "",
  num: 0,
  toast: "",
  pageNum: "1",
  requestCount: 0,
  captcha: {
    uuid: "",
    sandCode: "",
  },
  codeFlag: false,
  key: "",
  secretKey:
    "00e4bce5f10d8a3e96b7e62df284cda5078c8f263056a0f6305370a5e63438bd20",
  PublicKey:
    "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b",
  PrivateKey:
    "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b",
  startTime: null,
  endTime: null,
  list:[]
});
const router = useRouter();

// 组件生命周期
onMounted(async () => {
   queryList();
});

const queryList = () => {
  const loading = ElLoading.service({
    lock: true,
    text: "加载中...",
    background: "rgba(255,255,255,0.9)",
    target: document.getElementById("resultDiag"),
  });
  const param = {
    params: tools.encryptSM2(
      JSON.stringify({
        normGuid: "97ebbb38-3c02-4618-8881-3719be2e141e",
        fieldList: [],
      }),
      state.PublicKey
    ),
  };
  serveAPI
    .queryListHeaders(
      "zyjtyyjkrzoeeih",
      "zyjtyyjk",
      param
      
    )
    .then(
      (res) => {
        let resStart;
        resStart = JSON.parse(res.data.data);
        resStart = JSON.parse(
          tools.decryptSM2(resStart.data, state.PrivateKey)
        );
         loading.close();
        state.list = resStart.custom.result.cockpitData;
        sessionStorage.setItem("dataList", JSON.stringify(state.list));
         router.push("/results");
        
      },
      () => {
        loading.close();
        ElMessage.error("网络繁忙，请稍后再试");
      }
    );
};
</script>
<style scoped lang="less">
.select_w {
  width: 4.7rem;
}

::v-deep(.el-input__wrapper) {
  border-radius: inherit !important;
  height: 40px;
}
::v-deep(.el-form-item__label) {
  line-height: 40px;
}
::v-deep(.el-form-item) {
  margin-bottom: 20px !important;
}

@media only screen and (max-width: 1980px) {
  .slt {
    width: 470px;
  }

  .ipt {
    width: 340px;
  }
}

@media only screen and (max-width: 765px) and (min-width: 365px) {
  .slt {
    width: 200px;
  }

  .ipt {
    width: 80px;
  }
}
</style>

