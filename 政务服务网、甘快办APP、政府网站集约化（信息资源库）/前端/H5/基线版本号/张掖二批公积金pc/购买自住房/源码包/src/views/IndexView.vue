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
      <div class="pagetitle">基本信息</div>
      <el-form
        style="padding: 0.2rem 0.4rem"
        action=""
        :model="basicInfo"
        ref="basicInfo"
        :rules="formRules"
        label-position="top"
        label-width="100px"
      >
        <div class="flex">
          <el-form-item label="申请提取金额" prop="sqtqjexx" style="width: 48%">
            <el-input
              placeholder="请填写申请提取金额"
              clearable
              v-model.trim="basicInfo.sqtqjexx"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="申请人收款户名"
            prop="sqrskhm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写申请人收款户名"
              clearable
              v-model.trim="basicInfo.sqrskhm"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item
            label="申请人收款账号"
            prop="sqrskzh"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写申请人收款账号"
              clearable
              v-model.trim="basicInfo.sqrskzh"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="申请人开户银行名称"
            prop="sqrkhyhmc"
            style="width: 48%"
          >
            <!-- <el-input
              placeholder="请填写申请人开户银行名称"
              clearable
              v-model.trim="basicInfo.sqrkhyhmc"
            >
            </el-input> -->
            <el-cascader
              ref="cascader"
              :options="bankColumns"
              :show-all-levels="false"
              :props="{
                value: 'yhdm',
                label: 'yhmc',
                children: 'nodes',
                disabled: true,
                leafOnly: true,
              }"
              style="width: 100%"
              placeholder="请选择申请人开户银行名称"
              @change="handleChange"
            ></el-cascader>
          </el-form-item>
          <!-- <el-form-item label="提取时间" prop="TQSD" style="width: 48%">
            <el-date-picker
              readonly
              value-format="yyyyMMdd"
              v-model="basicInfo.TQSD"
              type="date"
              placeholder="请选择提取时间"
            >
            </el-date-picker>
          </el-form-item> -->
        </div>
        <div class="flex">
          <el-form-item label="购房人姓名" prop="gfrxm" style="width: 48%">
            <el-input
              placeholder="请填写购房人姓名"
              clearable
              v-model.trim="basicInfo.gfrxm"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="购房人身份证号"
            prop="gfrsfzh"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写购房人身份证号"
              clearable
              v-model.trim="basicInfo.gfrsfzh"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="申请人身份证号" prop="zjhm" style="width: 48%">
            <el-input
              placeholder="请填写申请人身份证号"
              clearable
              v-model.trim="zjhm"
              readonly
            >
            </el-input>
          </el-form-item>
          <!-- <el-form-item label="房源地址" prop="FYDZ" style="width: 48%">
            <el-input
              placeholder="建议格式为：【 】小区【 】幢【 】单元【 】室"
              clearable
              v-model.trim="basicInfo.FYDZ"
            >
            </el-input>
          </el-form-item> -->
        </div>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="下一步"
            @click="queryList('basicInfo')"
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
    this.getLoginUser()
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
    // // 校验输入框
    // let validateInputhz = (rule, value, callback) => {
    //   if (!this.patternKey(value)) {
    //     callback(new Error("请输入中文"));
    //   } else {
    //     callback();
    //   }
    // };
    // 校验输入框
    let validateInputsz = (rule, value, callback) => {
      if (!this.patternKeynum(value)) {
        callback(new Error("请输入正确的身份证号"));
      } else {
        callback();
      }
    };
    // // 校验金额
    // let validateInputye = (rule, value, callback) => {
    //   if (!this.Remaining(value)) {
    //     callback(new Error("提取金额不能大于账户余额"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      show: true,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "6300",
        grzhyetm: "",
        dwmc: "",
      },
      zjhm:"",
      basicInfo: {
        grzh: "",
        sqtqjexx: "",
        zjhm: "",
        gfrsfzh: "",
        sqrskhm: "",
        sqrskzh: "",
        tqlx: "11",
        xxly: "G",
        tqyydm: "01",
        sqrkhyhdm: "",
        sqrkhyhmc: "",
        gfrxm: "",
      },
      userInfo: {}, //用户信息
      token: "",
      authCode: "",
      formRules: {
        sqtqjexx: [
          {
            required: true,
            message: "申请提取金额不能为空",
            trigger: "blur",
          },
        ],
        sqrskhm: [
          {
            required: true,
            message: "申请人收款户名不能为空",
            trigger: "blur",
          },
        ],
        sqrskzh: [
          {
            required: true,
            message: "申请人收款账号不能为空",
            trigger: "blur",
          },
        ],
        sqrkhyhmc: [
          {
            required: true,
            message: "申请人开户银行名称不能为空",
            trigger: "change",
          },
        ],
        gfrxm: [
          {
            required: true,
            message: "购房人姓名不能为空",
            trigger: "blur",
          },
          // { validator: validateInput, trigger: 'blur' }
        ],
        gfrsfzh: [
          {
            required: true,
            message: "购房人身份证号不能为空",
            trigger: "blur",
          },
        ],
        zjhm: [
          {
            required: true,
            message: "身份证号不能为空",
            trigger: "blur",
          },
          {
            required: true,
            validator: validateInputsz,
            trigger: "blur",
          }
        ],
      },
      bankColumns: [],
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
      const pattern = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9xX]$/;
      if (!pattern.test(str)) {
        return false;
      } else {
        return true;
      }
    },
    // 校验支取金额是否大于剩余金额
    Remaining(str) {
      if (str > this.accountInfo.grzhye) {
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
      // this.getLoginUser();
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
        fullscreen: false,
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
          res = JSON.parse(tools.decryptSM2(res.data.data)).result;
          if (res.code == "00" && res.data) {
            console.log(res, "解密公积金信息");
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.basicInfo.grzh = res.data.grzh;
            this.basicInfo.zjhm = res.data.zjhm;
            this.zjhm = this.IdNumberDesensitization(res.data.zjhm); 
            this.$store.commit("accountInfo", res.data);
            loading.close();
          } else {
            loading.close();
            if (res.code == 502 || res.code == 506) {
              this.$message.info("请进行开户操作");
            } else {
              this.$message.info(res.message || "未查询到相关信息");
            }
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
          // Toast({
          //   message: '网络异常，请稍后重试！'
          // })
        });
    },
    // 职工银行列表查询
    getBank() {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            // zjhm: this.userInfo.userCertNum,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "G",
          })
        ),
        ds: "zy",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "jcyhTree.action",
      };
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "银行列表");
          res = JSON.parse(tools.decryptSM2(res.data.data)).result;
          console.log(res, "银行列表");
          if (res.code == "00" && res.data) {
            let newBankColumns = res.data;
            newBankColumns.forEach((item) => {
              if (item.nodes.length > 0) {
                return true;
              } else {
                delete item.nodes;
              }
            });
            this.bankColumns = newBankColumns;
            console.log("this.bankColumns", this.bankColumns);
          } else {
            this.$message.info({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          // Toast.clear();
          // Toast({
          //   message: "网络异常，请稍后重试！",
          // });
        });
    },
    handleChange() {
      let result = this.$refs.cascader.getCheckedNodes();
      console.log(result, "result");
      this.basicInfo.sqrkhyhmc = result[0].label;
      this.basicInfo.sqrkhyhdm = result[0].value;
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

    queryList(formName) {
      console.log(formName);
      this.$refs.basicInfo.validate((valid) => {
        if (valid) {
          const loading = this.$loading({
            fullscreen: false,
            // lock: true,
            text: "正在加载业务信息,请稍等",
            spinner: "el-icon-loading",
            background: "rgba(f,f,f,0.7)",
          });
          let param = {
            params: tools.encryptSM2(
              JSON.stringify({
                zjhm: this.userid,
                // xingming: userInfo.userName,
                // zjhm: "411082199810172473",
                xxly: "G",
                // grzh: "20107000077360000002",
                tqlx: "11",
                tqyydm: "01",
              }),
              this.publicKey1
            ),
            ds: "zy",
            token: this.$store.state.token,
            authCode: this.$store.state.authCode,
            method: "judgeGjjtqsqGrxx.action",
          };
          IndexApi.queryList("tygjj", "commonGjj", param).then((res) => {
            console.log(res, "提取验证");
            res = JSON.parse(
              tools.decryptSM2(res.data.data, this.privateKey1)
            ).result;
            console.log(res, "123456");
            if ((res.code == "01" || res.code == "00") && res.data) {
              loading.close();
              this.$router.push("/extractInfo");
            } else {
              loading.close();
              this.$message.info({
                message: res.msg || "未查询到相关信息",
              });
            }
          }).catch(() => {
            loading.close();
            this.$message.error({
              message: "网络异常，请稍后重试！",
            });
          });
          console.log(this.basicInfo, "this.basicInfo");
          this.$store.commit("basicInfo", this.basicInfo);
        }
      });
    },
    resetForm() {
      // this.$refs[formName].resetFields();
       window.location.href =
        "https://zwfw.gansu.gov.cn/zhangye/zwcs/zfgjjglzx/index.html";
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
