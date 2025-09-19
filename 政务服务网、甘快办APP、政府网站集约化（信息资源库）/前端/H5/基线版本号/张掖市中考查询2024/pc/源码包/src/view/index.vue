<template>
  <div>
    <section class="query-container">
      <el-form
        ref="ruleFormRef"
        :model="formData"
        :rules="formRules"
        label-position="right"
        label-width="82px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input
            class="slt"
            v-model="formData.name"
            :maxlength="20"
            placeholder="请输入姓名"
          />
        </el-form-item>
        <el-form-item label="统编学号" prop="value">
          <el-input
            class="slt"
            v-model="formData.value"
            :maxlength="10"
            placeholder="请输入统编学号"
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="card">
          <el-input
            class="slt"
            v-model="formData.card"
            :maxlength="18"
            placeholder="请输入身份证号"
          />
        </el-form-item>
        <div class="verification-code">
          <el-form-item label="验证码" prop="code">
            <el-input
              class="yanzhengma"
              placeholder="请输入验证码"
              v-model="formData.code"
              maxlength="4"
              @keyup.enter.native="queryList(ruleFormRef)"
            />
          </el-form-item>
          <div class="code" @click="updateCode">
            <canvas id="canvas" width="100" height="38"></canvas>
          </div>
        </div>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="查询"
            @click="queryList(ruleFormRef)"
          />
          <input type="reset" value="重置" @click="resetForm(ruleFormRef)" />
        </div>
      </el-form>
      <div class="query-tip">
        <span style="font-weight: bold; margin-bottom: 20px">温馨提示：</span>
        <span style="line-height: 30px; color: red; font-weight: bold"
          >请在登录后查询！</span
        >
        <div style="line-height: 30px">
          可查询成绩为张掖市县区（即：甘州区、临泽县、高台县、山丹县、民乐县、肃南裕固族自治县）所有中考科目。考生还可以通过“甘快办”APP查询成绩。
        </div>
        <span class="img-box"
          ><img
            class="appewm"
            src="https://zwfw.gansu.gov.cn/cms_files/filemanager/892888968/picture/20222/72ed6033b864440e9490755e22ef0283.png"
            alt=""
        /></span>
        <div style="line-height: 30px">
          注：为保护考生隐私，每个账号每日仅限5次查询，查询成绩后请截图或拍照保存！
        </div>
      </div>
    </section>
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
const checkCode = async (rule, value, callback) => {
  await verifyCode();
  if (!state.codeFlag) {
    formData.code = "";
    return callback(new Error("验证码错误"));
  }
  callback();
};
const validateInputCode = (rule, value, callback) => {
  if (!checkSpecialKeyCode(value)) {
    callback(new Error("不能含有特殊字符"));
  } else if (value.includes(" ")) {
    callback(new Error("不能含有特殊字符"));
  } else {
    callback();
  }
};
// 校验输入框
let validateInput = (rule, value, callback) => {
  if (!checkSpecialKey(value)) {
    callback(new Error("不能含有特殊字符"));
  } else if (value.includes(" ")) {
    callback(new Error("不能含有特殊字符"));
  } else {
    callback();
  }
};
// 校验学号
let validateStudentId = (rule, value, callback) => {
  const regStudentId = /^[0-9]{10}$/;
  if (!regStudentId.test(value)) {
    callback(new Error("统编学号格式不正确"));
  } else if (value.includes(" ")) {
    callback(new Error("统编学号格式不正确"));
  } else {
    callback();
  }
};
const validateName = (rule, value, callback) => {
  const regName = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
  if (!regName.test(value)) {
    callback(new Error("姓名格式不正确"));
  } else if (value.includes(" ")) {
    callback(new Error("姓名格式不正确"));
  } else {
    callback();
  }
};
// 身份证号码校验
function validateIDCard(rule, idCard, callback) {
  var idCardReg =
    /^[1-9]\d{5}(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dX]$/;
  if (!idCardReg.test(idCard)) {
    callback(new Error("身份证号格式不正确"));
  } else if (idCard.includes(" ")) {
    callback(new Error("身份证号格式不正确"));
  }
  return true;
}
const ruleFormRef = ref(null);
const formData = ref({
  name: "",
  value: "",
  card: "",
  code: "",
});
const formRules = ref({
  name: [
    {
      required: true,
      message: "请输入姓名",
      trigger: "blur",
    },
    { validator: validateName, trigger: "blur" },
  ],
  value: [
    {
      required: true,
      message: "请输入统编学号",
      trigger: "blur",
    },
    { validator: validateInput, trigger: "blur" },
    { validator: validateStudentId, trigger: "blur" },
  ],
  card: [
    {
      required: true,
      message: "请输入身份证号",
      trigger: "blur",
    },
    { min: 18, max: 18, message: "身份证号长度为18个字符", trigger: "blur" },
    { validator: validateIDCard, trigger: "blur" },
  ],
  code: [
    { required: true, message: "验证码不能为空", trigger: "blur" },
    { min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur" },
    { validator: validateInputCode, trigger: "blur" },
    { validator: checkCode, trigger: "click" },
  ],
});

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
  secretKey: "00e4bce5f10d8a3e96b7e62df284cda5078c8f263056a0f6305370a5e63438bd20",
  startTime: null,
  endTime: null,
});
const router = useRouter();
// 获取验证码
const queryCode = async () => {
  const result = await requestJpaas.getData();
  if (result.code === "200") {
    state.captcha.uuid = result.data.captcha.uuid;
    state.captcha.sandCode = result.data.captcha.capStr;
  } else {
    ElMessage.error("验证码获取失败，请重试或联系管理员");
  }
};
// 验证码获取更新
const updateCode = async () => {
  state.captcha.uuid = "";
  state.captcha.sandCode = "";
  await queryCode();
  createCode(state.captcha.sandCode);
};
// 验证码校验
const verifyCode = async () => {
  try {
    let formDatas = new FormData();
    formDatas.append("uuid", state.captcha.uuid);
    formDatas.append("sandCode", formData.value.code);
    state.captcha.sandCode = formData.value.code;
    const result = await requestJpaas1.getData(state.captcha);
    if (result.code == "200") {
      state.codeFlag = true;
    } else {
      state.codeFlag = false;
    }
  } catch (e) {
    ElMessage.error("验证码获取失败，请重试或联系管理员");
  }
};
// 文本框特殊字符校验
const checkSpecialKey = (str) => {
  let specialKey =
    '/[`-~!@#$^&*=|{}":",\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】"；：\'\'"。，、？\\]/';
  for (let i = 0; i < str.length; i++) {
    if (specialKey.indexOf(str.substr(i, 1)) != -1) {
      return false;
    }
  }
  return true;
};
// 验证码特殊字符校验
const checkSpecialKeyCode = (str) => {
  let specialKey =
    '/[`-~!@#$^&*=|{}":",\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】"；：\'\'"。，、？\\]/';
  for (let i = 0; i < str.length; i++) {
    if (specialKey.indexOf(str.substr(i, 1)) != -1) {
      return false;
    }
  }
  return true;
};
// 组件生命周期
onMounted(async () => {
  await queryCode();
  nextTick(() => {
    createCode(state.captcha.sandCode);
  });
  await getUser();
   // 获取密钥
  const data = {
    type: "fe7e0f2d42b6898bd321e172e9dbb0ce6247a0e5923079f8cebb249e4049693b",
  };
  await serveAPI
    .queryListHeaders("jsdnzjk", "findSecretKey", data, JSON.stringify({}))
    .then((res) => {
      res = JSON.parse(res.data.data);
      state.key = res.data;
    });
  // 获取埋点
  let date = new Date();
  let y = date.getFullYear();
  let m = date.getMonth() + 1;
  let d = date.getDate();
  let h = date.getHours();
  let M = date.getMinutes();
  state.time = y + "-" + m + "-" + d;
  // 判断分钟 如果小于30 就传 00 如果大于30 就传 30
  if (M >= 30) {
    M = 30;
  } else {
    M = "00";
  }
  let year = y + "/" + m + "/" + d;
  let time = h + ":" + M;
  await getRequestCount();
  const params = {
    type: "zys", // 市州名称
    appName: "zyszkcx", // 应用名称
    time: year + " " + time, // 2022/6/23 10：00 时间入参格式
  };
  serveAPI
  .queryListHeaders("sjmd", "sjmdjk", params, JSON.stringify({}))
  .then((res) => {
      console.log(res, "埋点");
    });
});
const getUser = async () => {
  await getUserInfo().then(async (res) => {
    // 获取token
    await getToken().then((result) => {
      if (res.code == "200" && res.success) {
        if (res.params.userType == "1") {
          let userInfo = res.params;
          userInfo.name = tools.decryptSM2(res.params.userName);
          userInfo.card = tools.decryptSM2(res.params.userId);
          userInfo.phone = tools.decryptSM2(res.params.phone);
          userInfo.authCode = tools.decryptSM2(result.data.authCode);
          userInfo.token = tools.decryptSM2(result.data.token);
          state.userInfo = userInfo;
        } else {
          ElMessage.error("该业务仅限个人用户办理");
        }
      } else {
        location.href =
          "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
          encodeURIComponent(location.href);
      }
    });
  });
};
// 获取查询次数
const getRequestCount = async () => {
  console.log(state.userInfo.name, "state.userInfo.name");
  let key = state.userInfo.name + state.time + "_6204";
  const params = {
    key: aes.encrypt(key),
  };
  await serveAPI.jpass2("zkcx", "zkcxhcxcs", params).then((res) => {
    console.log(JSON.parse(res.data), "次数");
    if (JSON.parse(res.data).code == "200" && JSON.parse(res.data).success) {
      state.requestCount = Number(JSON.parse(res.data).data);
    } else {
      state.requestCount = 0;
    }
    console.log(state.requestCount, "state.requestCount");
  });
};
const writeRequestCount = async () => {
  let keys = state.userInfo.name + state.time + "_6204";
  state.requestCount += 1;
  const paramss = {
    key: aes.encrypt(keys),
    value: state.requestCount,
  };
  await serveAPI.jpass2("zkcx", "zkcxxrcxcs", paramss).then((res) => {
    console.log(res, "写入次数");
    getRequestCount();
  });
};
const queryList = () => {
  state.startTime = new Date("2024-6-25 15:15:00").getTime();
  state.endTime = new Date().getTime();
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    updateCode();
    if (valid) {
      // if(state.startTime > state.endTime){
      //   ElMessage.error('未到查询时间，请于2024年6月25日15:15:00以后查询')
      //   return
      // }
      if (state.requestCount > 4) {
        ElMessage.error('今日查询次数已到上限')
        return
      }
      const loading = ElLoading.service({
        lock: true,
        text: "加载中...",
        background: "rgba(255,255,255,0.9)",
        target: document.getElementById("resultDiag"),
      });
      const param = {
        param: tools.encryptSM2(
          JSON.stringify({
            pageNum: state.pageNum,
            pageSize: "10",
            params: {
              xm: formData.value.name,
              tbxh: formData.value.value,
              id: formData.value.card,
            },
          }),
          state.key
        ),
      };
      serveAPI
        .queryListHeaders(
          "jsdnzjk",
          "findDataInfo",
          param,
          JSON.stringify({
            "res-key": "qIygTRtz",
            "key-secret":
              "C6D55C747EB308ACE0BC791F4317D99CA8A99C744649AC740A08F2E444671FAD",
          })
        )
        .then( res => {
          let resStart;
          resStart = JSON.parse(res.data.data);
          console.log(res, "res555");
          res = JSON.parse(tools.decryptSM2(resStart.data, state.secretKey));
          if (resStart.message == "您当前查询的信息暂未对外开放！") {
            loading.close();
            ElMessage.error("暂未到查询时间");
          } else {
            if (res.rows && res.total > 0) {
              writeRequestCount();
              sessionStorage.setItem("dataList", JSON.stringify(res.rows));
              router.push("/");
              setTimeout(() => {
                loading.close();
                router.push("/results");
              }, 1000);
             
            } else {
              loading.close();
              router.push("/fail");
            }
          }
        },
        () => {
          loading.close();
          ElMessage.error("网络繁忙，请稍后再试");
        })
    } else {
      return false;
    }
  });
};
const resetForm = (val) => {
  if (!val) return;
  val.resetFields();
  router.push("/");
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

