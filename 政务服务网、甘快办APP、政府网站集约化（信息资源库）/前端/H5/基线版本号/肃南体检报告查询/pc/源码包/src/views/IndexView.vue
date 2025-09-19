<template>
  <div class="bottom-content">
    <section class="query-container content_from" id="query-container_overy">
      <el-form
        action=""
        :model="formData"
        ref="formData"
        :rules="formRules"
        label-position="right"
        label-width="82px"
      >
        <el-form-item label="证件号码" prop="name">
          <el-input
            class="slt"
            placeholder="请输入身份证号码"
            prefix-icon="el-icon-document"
            v-model="jmuserId"
            maxlength="50"
            disabled
          >
          </el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone" v-show="showPhone">
          <el-input
            class="slt"
            placeholder="请输入电话号码"
            prefix-icon="el-icon-document"
            v-model="jmphone"
            maxlength="50"
            disabled
          >
          </el-input>
        </el-form-item>
        <div class="verification-code">
          <el-form-item label="验证码" prop="code">
            <el-input
              placeholder="请输入验证码"
              prefix-icon="el-icon-edit"
              v-model="formData.code"
              class="yanzhengma"
              maxlength="4"
              onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')"
              @keyup.enter.native="queryList('formData')"
              @input="inputKeyUp"
            >
            </el-input>
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
            :disabled="userType1"
            @click="queryList('formData')"
          />
          <input type="button" value="重置" @click="resetForm('formData')" />
        </div>
      </el-form>
    </section>
  </div>
</template>
<script>
import { createCode } from "@/utils/checkCode";
import { requestJpaas, requestJpaas1 } from "@/api/code";
import IndexApi from "@/api/index";
import sm2 from "../utils/sm2";
import getUserInfo from "@/api/userInfo";
import $ from "jquery";
import tools, { IdNumberDesensitization, phone } from '../tools/tool'
// import { IdNumberDesensitization, phone } from "../tools/desensitized";
import { Loading } from "element-ui";

export default {
  name: "IndexView",
  data() {
    const checkCode = async (rule, value, callback) => {
      await this.verifyCode();
      if (!this.codeFlag) {
        this.formData.code = "";
        return callback(new Error("验证码错误"));
      }
      callback();
    };
    // 校验验证码
    const validateInputCode = (rule, value, callback) => {
      if (!this.checkSpecialKeyCode(value)) {
        callback(new Error("不能含有特殊字符"));
      } else {
        callback();
      }
    };

    const validateInputCard = (rule, value, callback) => {
      if (!this.checkSpecialKeyCart(value)) {
        callback(new Error("证件号码格式有误"));
      } else {
        callback();
      }
    };

    const validateInputPhone = (rule, value, callback) => {
      if (!this.checkSpecialKeyPhone(value)) {
        callback(new Error("电话号码格式有误"));
      } else {
        callback();
      }
    };

    return {
      jmuserId: "",
      jmphone: "",
      loading: true,
      key: 0,
      isOpenResult: false,
      showPhone: true,
      formData: {
        name: "",
        code: "",
        area: "",
        phone: "",
      },
      userType1: false,
      show: true,
      phone: "",
      code: "",
      formRules: {
        code: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
          { min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur" },
          { validator: validateInputCode, trigger: "blur" },
          { validator: checkCode, trigger: "click" },
        ],
        name: [
          { required: true, message: "证件号码不能为空", trigger: "blur" },
          { validator: validateInputCard, trigger: "blur" },
        ],
        phone: [
          { required: true, message: "电话号码不能为空", trigger: "blur" },
          { validator: validateInputPhone, trigger: "blur" },
        ],
        area: [
          { required: true, message: "查询医院不能为空", trigger: "change" },
        ],
      },
      value: "",
      captcha: {
        uuid: "",
        sandCode: "",
      },
      codeFlag: false,
      token: "",
      authCode: "",
      privateKey:
        "00ad1ecc86390c0439d3235d1b1a19a19297f365a0d8d436710800f565f61675d2",
      publicKey:
        "04e18f52110e0e1bc1b1dbe10b3d20607b436fd6952b3391c27ab0fcad690f2ef07a115a5e18012e63fd7a46e94b4d9ff9d71e78a4ea9f1b376b1954b865f8b59e",
      userId: "",
      MobilePhone: "",
    };
  },
  created() {
    getUserInfo().then((res) => {
      console.log("--用户信息--", res);
      if (res.code === "200") {
        if (res.params.userType == "1") {
          let userInfo = JSON.parse(JSON.stringify(res.params));
          userInfo.card = IdNumberDesensitization(
            tools.decryptSM2(res.params.userId)
          );
          userInfo.phone = phone(tools.decryptSM2(res.params.phone));
          sessionStorage.setItem(
            "userInfo",
            sm2.encrypt(JSON.stringify(userInfo))
          );
          this.userId = userInfo.card

          this.formData.name = tools.decryptSM2(res.params.userId);
          this.formData.phone = tools.decryptSM2(res.params.phone)

          this.jmuserId = userInfo.card
          this.jmphone = userInfo.phone
          this.getToken();
        } else {
          this.$message.warning("该业务仅限个人用户办理");
          this.userType1 = true;
        }
      } else {
        location.href =
          "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
          encodeURIComponent(location.href);
      }
    });
  },
  async mounted() {
    // 获取验证码
    await this.queryCode();
    this.$nextTick(() => {
      createCode(this.captcha.sandCode);
    });
  },
  methods: {
    // 验证码特殊字符校验
    checkSpecialKeyCode(str) {
      let specialKey = "/[`-~!@#$^&*=|{}':;',\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/"
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
      }
      return true;
    },
    checkSpecialKeyCart(str) {
      let specialKey = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
      console.log('---', specialKey.test(str))
      if(specialKey.test(str)) {
        return true
      } else {
        return false;
      }
    },
    checkSpecialKeyPhone(str)  {
      let specialKey = /^1[3456789]\d{9}$/
      console.log('---', specialKey.test(str))
      if(specialKey.test(str)) {
        return true
      } else {
        return false;
      }
    },

    getToken() {
      let tokenData = null;
      $.ajax({
        type: "get",
        async: false,
        headers: {},
        url: "https://zwfw.gansu.gov.cn/api/sso/loginTrust?backUrl=https://zwfw.gansu.gov.cn/api-gateway/jpaas-yyjrzzxt-ds-server/interface/login/loginTrust",
        success: function (res) {
          console.log(res);
          tokenData = res.data;
          return tokenData;
        },
      });
      this.token = tokenData.token;
      sessionStorage.setItem("token", this.token);
      this.authCode = tokenData.authCode;
      sessionStorage.setItem("authCode", this.authCode);
    },

    async queryList(formName) {
      this.$refs[formName].validate(async (valid) => {
        await this.updateCode();
        this.formData.code = "";
        if (valid) {
          await this.queryDeHosipatil();
        } else {
          return false;
        }
      });
    },
    // 张掖市第二人民医院
    async queryDeHosipatil() {
      let loadingInstance = Loading.service({
        target: document.getElementById("query-container_overy"),
      });
      const param = {
        // sfzh: '62220119881112632x'
        tokenNo: sm2.encrypt(
          sm2.decrypt(
            this.token,
            "0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7"
          ),
          this.publicKey
        ),
        authCode: sm2.encrypt(
          sm2.decrypt(
            this.authCode,
            "0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7"
          ),
          this.publicKey
        ),
      };
      try {
        const result = await IndexApi.queryList(
          "zyyyjkrzmpgab",
          "zysnPhysical",
          param,
          {}
        );
        console.log(sm2.decrypt(result.data, this.privateKey));
        const data = JSON.parse(sm2.decrypt(result.data, this.privateKey))
        if (result.code === "200" && result.success && data?.reports?.length > 0) {
          loadingInstance.close();
          this.$store.commit(
            "drResultData",
            data
          );
          this.$router.push("/drResult");
        } else {
          loadingInstance.close();
          this.$router.push("/no-results");
        }
      } catch (e) {
        console.log("--2--", e);
        loadingInstance.close();
        this.$message({
          message: "网络异常，请稍后再试！",
          duration: 2000,
        });
      }
    },

    async resetForm() {
      await this.updateCode();
      // this.$refs[formName].resetFields();
      // this.formData.selectName = ''
      this.formData.code = "";
      this.$router.push({ name: "Index" });
    },
    // 获取验证码
    async queryCode() {
      const result = await requestJpaas.getData();
      if (result.code === "200") {
        this.captcha.uuid = result.data.captcha.uuid;
        this.captcha.sandCode = result.data.captcha.capStr;
      } else {
        this.$message({
          type: "error",
          message: "验证码获取失败，请重试或联系管理员",
        });
      }
    },
    // 验证码获取更新
    async updateCode() {
      this.captcha.uuid = "";
      this.captcha.sandCode = "";
      await this.queryCode();
      createCode(this.captcha.sandCode);
    },
    // 验证码校验
    async verifyCode() {
      try {
        let formData = new FormData();
        formData.append("uuid", this.captcha.uuid);
        formData.append("sandCode", this.formData.code);
        this.captcha.sandCode = this.formData.code;
        const result = await requestJpaas1.getData(this.captcha);
        console.log("asdasd", result);
        if (result.code == "200") {
          this.codeFlag = true;
        } else {
          this.codeFlag = false;
        }
      } catch (e) {
        this.$message({
          type: "error",
          message: "验证码校验失败，请重试或联系管理员",
        });
      }
    },
    keyUp(name) {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.formData[name] = this.formData[name].replace(pattern, "");
    },
    inputKeyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.formData.code = this.formData.code.replace(pattern, "");
    },
  },
};
</script>

<style scoped>
.select_w {
  width: 4.7rem;
}
.bottom-content {
  background: #fff;
  box-shadow: 0 3px 18px #0000000d;
  margin-bottom: 40px;
  padding: 30px;
  font-size: 16px;
}
.content_from {
  padding: 30px 320px;
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
@media screen and (max-width: 750px) {
  .content_from {
    padding: 0 !important;
  }
}
</style>