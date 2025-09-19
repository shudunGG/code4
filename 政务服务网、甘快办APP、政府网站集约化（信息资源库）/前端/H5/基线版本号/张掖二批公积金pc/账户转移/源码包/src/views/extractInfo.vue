<template>
  <div>
    <section class="query-container">
      <div class="pagetitle">账户信息</div>
      <el-form
        style="padding: 0.2rem 0.4rem"
        action=""
        label-position="top"
        label-width="100px"
      >
        <div class="flex">
          <el-form-item label="姓名" style="width: 48%">
            <div class="el-input__inner">{{ name(accountInfo.xingming) }}</div>
          </el-form-item>
          <el-form-item label="开户日期" style="width: 48%">
            <div class="el-input__inner">
              {{ accountInfo.khrq.slice(0, 10) }}
            </div>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="证件号" style="width: 48%">
            <div class="el-input__inner">
              {{ IdNumberDesensitization(accountInfo.zjhm) }}
            </div>
          </el-form-item>
          <el-form-item label="个人账号余额" style="width: 48%">
            <div class="el-input__inner por">
              <span class="account_r_t"
                >{{ show ? accountInfo.grzhyetm : accountInfo.grzhye }}元</span
              >
              <span @click="show = !show" class="poa">
                <img src="@/assets/img/by.png" width="50%" alt="" v-if="show" />
                <img src="@/assets/img/zy.png" width="50%" alt="" v-else />
              </span>
            </div>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="缴存单位" style="width: 48%">
            <!-- <el-input readonly v-model.trim="accountInfo.dwmc"> </el-input> -->
            <div class="el-input__inner">{{ accountInfo.dwmc }}</div>
          </el-form-item>
        </div>
      </el-form>
      <div class="pagetitle">经办信息</div>
      <el-form
        style="padding: 0.2rem 0.4rem"
        action=""
        :model="basicInfomation"
        ref="basicInfomation"
        :rules="formRules"
        label-position="top"
        label-width="100px"
      >
        <div class="flex">
          <el-form-item label="经办人姓名" prop="jbrxm" style="width: 48%">
            <el-input
              placeholder="请填写经经办人姓名"
              clearable
              v-model.trim="basicInfomation.jbrxm"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="经办人证件类型"
            prop="jbrzjlx"
            style="width: 48%"
          >
            <el-select
              style="width: 100%"
              v-model="basicInfomation.jbrzjlx"
              placeholder="请选择经办人证件类型"
            >
              <el-option
                v-for="item in jbrzjlxOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item
            label="经办人证件号码"
            prop="jbrzjhm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写经办人证件号码"
              clearable
              v-model.trim="basicInfomation.jbrzjhm"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="受托银行名称" prop="styhmc" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfomation.styhmc"
              placeholder="请选择受托银行名称"
              @change="changeBank"
            >
              <el-option
                v-for="item in bankList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="受托银行代码" prop="styhdm" style="width: 48%">
            <el-input
              placeholder="请填写受托银行代码"
              clearable
              v-model.trim="basicInfomation.styhdm"
              readonly
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="经办人手机号码"
            prop="jbrsjhm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写经办人手机号码"
              clearable
              v-model.trim="basicInfomation.jbrsjhm"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item
            label="经办人固定电话号码"
            prop="jbrgddhhm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写经办人固定电话号码"
              clearable
              v-model.trim="basicInfomation.jbrgddhhm"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="统一社会信用代码"
            prop="tyshxydm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写统一社会信用代码"
              clearable
              v-model.trim="basicInfomation.tyshxydm"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item
            label="单位缴存比例(%)"
            prop="dwjcbl"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写单位缴存比例(%)"
              clearable
              v-model.trim="basicInfomation.dwjcbl"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="个人缴存比例(%)"
            prop="grjcbl"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写个人缴存比例(%)"
              clearable
              v-model.trim="basicInfomation.grjcbl"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="网点编号" prop="wdbh" style="width: 48%">
            <!-- <el-input
              placeholder="请填写网点编号"
              clearable
              v-model.trim="basicInfomation.wdbh"
            >
            </el-input> -->
            <el-select
              style="width: 100%"
              v-model="basicInfomation.wdbh"
              placeholder="请选择网点编号"
            >
              <el-option
                v-for="item in branchList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="提交"
            @click="queryList('basicInfomation')"
          />
          <input type="button" value="返回" @click="resetForm('basicInfo')" />
        </div>
      </el-form>
    </section>
  </div>
</template>
<script>
import IndexApi from "@/api/index";
import sm2 from "@/utils/sm2";
import tools, {
  name,
  IdNumberDesensitization,
  changDateFormate,
  formatDate,
  replaceAll,
} from "../tools/tool";
// import sm4 from "@/utils/sm4";
import getUserInfo from "@/api/userInfo";
import $ from "jquery";
export default {
  name: "IndexView",
  created() {
    this.getToken();
  },
  mounted() {},
  data() {
    // 校验输入框
    // let validateInput = (rule, value, callback) => {
    //   if (!this.checkSpecialKey(value)) {
    //     callback(new Error("不能含有特殊字符"));
    //   } else {
    //     callback();
    //   }
    // };
    // 校验身份证号
    let validateInputhz = (rule, value, callback) => {
      let sfz =
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!sfz.test(value)&&this.basicInfomation.jbrzjlx == "01") {
        callback(new Error("请输入正确的身份证号"));
      } else {
        callback();
      }
    };
    // 校验个人缴存比例
    let validateInputsz = (rule, value, callback) => {
      if (!this.Remaining1(value)) {
        callback(new Error("个人缴存比例不得低于5%或高于12%"));
      } else {
        callback();
      }
    };
    // 校验单位缴存比例
    let validateInputye = (rule, value, callback) => {
      if (!this.Remaining(value)) {
        callback(new Error("单位缴存比例不得低于5%或高于12%"));
      } else {
        callback();
      }
    };
    return {
      show: true,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "",
        grzhyetm: "",
        dwmc: "",
      },
      basicInfomation: {
        jbrxm: "",
        jbrzjlx: "",
        jbrzjhm: "",
        styhmc: "",
        styhdm: "",
        jbrsjhm: "",
        jbrgddhhm: "",
        tyshxydm: "",
        dwjcbl: "",
        grjcbl: "",
        wdbh: "",
        xxly: "G",
      },
      zzldgxlx: "终止劳动关系满半年",
      userInfo: {}, //用户信息
      token: "",
      authCode: "",
      formRules: {
        jbrxm: [
          {
            required: true,
            message: "经办人姓名不能为空",
            trigger: "blur",
          },
        ],
        jbrzjlx: [
          {
            required: true,
            message: "经办人证件类型不能为空",
            trigger: "change",
          },
        ],
        jbrzjhm: [
          {
            required: true,
            message: "经办人证件号码不能为空",
            trigger: "blur",
          },
          {
            validator: validateInputhz,
            trigger: "blur",
          },
        ],
        styhmc: [
          {
            required: true,
            message: "受托银行名称不能为空",
            trigger: "change",
          },
        ],
        styhdm: [
          {
            required: true,
            message: "受托银行代码不能为空",
            trigger: "blur",
          },
        ],
        jbrsjhm: [
          {
            required: true,
            message: "经办人手机号码不能为空",
            trigger: "blur",
          },
        ],
        jbrgddhhm: [
          {
            required: true,
            message: "经办人固定电话号码不能为空",
            trigger: "blur",
          },
        ],
        tyshxydm: [
          {
            required: true,
            message: "统一社会信用代码不能为空",
            trigger: "blur",
          },
        ],
        dwjcbl: [
          {
            required: true,
            message: "单位缴存比例不能为空",
            trigger: "blur",
          },
          {
            validator: validateInputye,
            trigger: "blur",
          },
        ],
        grjcbl: [
          {
            required: true,
            message: "个人缴存比例不能为空",
            trigger: "blur",
          },
          {
            validator: validateInputsz,
            trigger: "blur",
          },
        ],
        wdbh: [
          {
            required: true,
            message: "网点编号不能为空",
            trigger: "blur",
          },
        ],
      },
      jbrzjlxOptions: [
        { label: "护照", value: "03" },
        { label: "外国人永久居留证", value: "04" },
        { label: "身份证", value: "01" },
        { label: "军官证", value: "02" },
        { label: "其他", value: "99" },
      ], // 证件类型
      bankList: [],
      branchList: [],
    };
  },
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    changDateFormate,
    // 文本框特殊字符校验
    checkSpecialKey(str) {
      let specialKey =
        "/[`~!@$^&*=|{}':;',\\\\\\[\\]\\.<>\\/?~！@￥……&%*（）——|{}'；：\"\"'。，、？\\]/";
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
      }
      return true;
    },
    // 文本框输入汉字校验
    patternKey(str) {
      const pattern = /^[\u4e00-\u9fa5]+$/;
      if (!pattern.test(str)) {
        return false;
      } else {
        return true;
      }
    },
    // 文本框输入数字校验
    patternKeynum(str) {
      const pattern = /^\d+(\.\d+)?$/;
      if (!pattern.test(str)) {
        return false;
      } else {
        return true;
      }
    },
    // 校验支取金额是否大于剩余金额
    Remaining(str) {
      if (Number(str) > 12 || Number(str) < 5) {
        return false;
      } else {
        return true;
      }
    },
    Remaining1(str) {
      if (Number(str) > 12 || Number(str) < 5) {
        return false;
      } else {
        return true;
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
      // this.token = sm2.decrypt3(tokenData.token);
      this.token = tokenData.token;
      this.$store.commit("token", tokenData.token);
      console.log(this.token, "this.token");
      // this.authCode = sm2.decrypt3(tokenData.authCode);
      this.authCode = tokenData.authCode;
      this.$store.commit("authCode", tokenData.authCode);
      console.log(this.authCode, "this.authCode");
      this.getLoginUser();
    },
    getLoginUser() {
      getUserInfo().then((res) => {
        console.log(res);
        if (res.code === "200") {
          console.log(res);
          // this.basicInfo.name = sm2.decrypt2(res.params.currentUserName);
          // this.username = sm2.decrypt2(res.params.userName);
          // let sfz = sm2.decrypt2(res.params.userId);
          // this.basicInfo.num = "***************" + sfz.slice(14, 18);
          this.userid = sm2.decrypt2(res.params.userId);
          let userInfo = res.params;
          this.userInfo = userInfo;
          this.$store.commit("userInfo", userInfo);
          console.log(userInfo, "用户信息");
          this.getInfo(this.userInfo);
          this.getBank();
          this.getBranchList()
        } else {
          location.href =
            "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
            encodeURIComponent(location.href);
        }
      });
    },
    getInfo(userInfo) {
      console.log(userInfo, "userInfo");
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: this.userid,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "grxxDetail.action",
      };
      const loading = this.$loading({
        fullscreen: true,
        // lock: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "公积金信息");
          if (res.code == 200 && res.success) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            console.log(res, "解密公积金信息");
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.$store.commit("accountInfo", res.data);
            loading.close();
          } else {
            loading.close();
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch((err) => {
          loading.close();
          console.log(err, "err");
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    // 职工银行列表查询
    getBank() {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "cbyhxxQry.action",
      };
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "银行列表");
          res = JSON.parse(tools.decryptSM2(res.data.data)).result;
          console.log(res, "银行列表");
          if (res.code == "00" && res.data) {
            res.data.forEach((item) => {
              this.bankList.push({
                value: item.styhdm,
                label: item.styhmc,
              });
            });
            console.log("this.bankColumns", this.bankColumns);
          } else {
            this.$message.info({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          this.$message.info({
            message: "网络异常，请稍后重试！",
          });
        });
    },
    // 获取网点编号
    getBranchList() {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "wdxxList.action",
      };
      console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      IndexApi
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "网点编号列表");
          res = JSON.parse(
            tools.decryptSM2(res.data.data)
          ).result;
          console.log(res, "网点编号列表");
          if (res.code == "00" && res.data) {
            res.data.forEach((item) => {
              this.branchList.push({
                value: item.key,
                label: item.value,
              });
            });
          } else {
            this.$message.info({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          this.$message.info({
            message: "网络异常，请稍后重试！",
          });
        });
    },
    changeBank(value) {
      this.basicInfomation.styhdm = value;
      this.bankList.forEach((item) => {
        if (item.value == value) {
          this.basicInfomation.styhmc = item.label;
        }
      });
    },
    getTime() {
      const y = new Date().getUTCFullYear();
      const m =
        new Date().getMonth() + 1 < 10
          ? "0" + (new Date().getMonth() + 1)
          : new Date().getMonth() + 1;
      return {
        start: y + "01",
        end: y + "" + m,
      };
    },

    queryList() {
      this.$refs.basicInfomation.validate((valid) => {
        if (valid) {
          const loading = this.$loading({
            fullscreen: false,
            // lock: true,
            text: "正在加载业务信息,请稍等",
            spinner: "el-icon-loading",
            background: "rgba(f,f,f,0.7)",
          });
          let formInfo = {
            ...this.basicInfomation,
            ...this.$store.state.basicInfo,
          };
          console.log("formInfo", formInfo);
          let param = {
            params: tools.encryptSM2(JSON.stringify(formInfo)),
            ds: "zy",
            token: this.$store.state.token,
            authCode: this.$store.state.authCode,
            method: "dwzhxxAdd.action",
          };
          IndexApi.queryList("tygjj", "commonGjj", param)
            .then((res) => {
              res = JSON.parse(tools.decryptSM2(res.data.data)).result;
              if (res.code == "00" && res.data) {
                loading.close();
                this.$router.push({
                  path: "/no-results",
                });
              } else {
                loading.close();
                this.$message.info({
                  message: res.msg || "未查询到相关信息",
                });
              }
            })
            .catch(() => {
              loading.close();
              this.$message.error({
                message: "网络异常，请稍后重试！",
              });
            });
        }
      });
    },
    resetForm() {
      // this.$refs[formName].resetFields();
      this.$router.push("/");
    },
  },
};
</script>

<style scoped lang="less">
.yzm {
  width: 320px;
}
@media screen and (max-width: 800px) {
  .yzm {
    width: 130px;
  }
}
.pagetitle {
  font-weight: 600;
  padding-left: 10px;
  margin-left: 5px;
  border-left: 3px solid rgb(24, 144, 255);
}

.flex {
  display: flex;
  justify-content: space-between;
}
.el-date-editor.el-input {
  width: 100%;
}
.por {
  position: relative;
  .poa {
    position: absolute;
    right: 0;
    text-align: center;
    line-height: 50px;
  }
}
</style>
