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
      <div class="pagetitle">提取信息</div>
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
          <el-form-item label="租赁合同日期" prop="qzrq" style="width: 48%">
            <!-- <el-input
              placeholder="请填写租赁合同日期"
              clearable
              v-model.trim="basicInfomation.qzrq"
            >
            </el-input> -->
            <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="basicInfomation.qzrq"
              type="date"
              placeholder="请选择租赁合同日期"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="租房合同编号" prop="zfhtbh" style="width: 48%">
            <el-input
              placeholder="请填写租房合同编号"
              clearable
              v-model.trim="basicInfomation.zfhtbh"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="房租支出" prop="fzzc" style="width: 48%">
            <el-input
              placeholder="请填写房租支出"
              clearable
              v-model.trim="basicInfomation.fzzc"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="房屋类型" prop="zzfwlx" style="width: 48%">
            <!-- <el-input
              placeholder="请填写房屋类型"
              clearable
              v-model.trim="basicInfomation.zzfwlx"
            >
            </el-input> -->
            <el-select
              style="width: 100%"
              v-model="basicInfomation.zzfwlx"
              placeholder="请选择"
            >
              <el-option
                v-for="item in roomTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                placeholder="请选择房屋类型"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="提取类型" prop="zftqlx" style="width: 48%">
            <!-- <el-input
              placeholder="请填写提取类型"
              clearable
              v-model.trim="basicInfomation.zftqlx"
            >
            </el-input> -->
            <el-select
              style="width: 100%"
              v-model="basicInfomation.zftqlx"
              placeholder="请选择"
            >
              <el-option
                v-for="item in zftqlxOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                placeholder="请选择提取类型"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否多孩家庭" prop="sfdhjt" style="width: 48%">
            <!-- <el-input
              placeholder="请填写是否多孩家庭"
              clearable
              v-model.trim="basicInfomation.sfdhjt"
            >
            </el-input> -->
            <el-select
              style="width: 100%"
              v-model="basicInfomation.sfdhjt"
              placeholder="请选择"
            >
              <el-option
                v-for="item in sfdhjtOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                placeholder="请选择是否多孩家庭"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="租房地址" prop="fwdz" style="width: 48%">
            <el-input
              placeholder="请填写租房地址"
              clearable
              v-model.trim="basicInfomation.fwdz"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="提取月份" prop="bctqyfjh" style="width: 48%">
            <el-date-picker
              type="months"
              value-format="M"
              v-model="bctqyfjh"
              placeholder="请选择提取月份"
              style="width: 100%"
              @change="monthChange"
            >
            </el-date-picker>
          </el-form-item>
        </div>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="下一步"
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
    // // 校验输入框
    // let validateInputhz = (rule, value, callback) => {
    //   if (!this.patternKey(value)) {
    //     callback(new Error("请输入中文"));
    //   } else {
    //     callback();
    //   }
    // };
    // // 校验输入框
    // let validateInputsz = (rule, value, callback) => {
    //   if (!this.patternKeynum(value)) {
    //     callback(new Error("请输入数字"));
    //   } else {
    //     callback();
    //   }
    // };
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
      basicInfomation: {
        qzrq: "",
        zfhtbh: "",
        fzzc: "",
        zzfwlx: "",
        zftqlx: "",
        sfdhjt: "",
        fwdz: "",
        bctqyfjh: "",
      },
      bctqyfjh: "",
      userInfo: {}, //用户信息
      token: "",
      authCode: "",
      formRules: {
        qzrq: [
          {
            required: true,
            message: "租赁合同日期不能为空",
            trigger: "change",
          },
        ],
        zfhtbh: [
          {
            required: true,
            message: "租房合同编号不能为空",
            trigger: "blur",
          },
        ],
        fzzc: [
          {
            required: true,
            message: "房租支出不能为空",
            trigger: "blur",
          },
        ],
        zzfwlx: [
          {
            required: true,
            message: "房屋类型不能为空",
            trigger: "change",
          },
        ],
        zftqlx: [
          {
            required: true,
            message: "提取类型不能为空",
            trigger: "change",
          },
          // { validator: validateInput, trigger: 'blur' }
        ],
        sfdhjt: [
          {
            required: true,
            message: "是否多孩家庭不能为空",
            trigger: "change",
          },
          // { validator: validateInput, trigger: 'blur' }
        ],
        fwdz: [
          {
            required: true,
            message: "租房地址不能为空",
            trigger: "blur",
          },
        ],
        bctqyfjh: [
          {
            required: true,
            message: "提取月份不能为空",
            trigger: "change",
          },
          // { validator: validateInput, trigger: 'blur' }
        ],
      },
      bankColumns: [],
      roomTypeOptions: [
        {
          value: "1",
          label: "公共租赁租房",
        },
        {
          value: "2",
          label: "商品住房",
        },
      ], // 购房类型
      zftqlxOptions: [
        {
          value: "01",
          label: "按月提取",
        },
        {
          value: "02",
          label: "按年提取",
        },
      ], // 提取类型
      sfdhjtOptions: [
        {
          value: "0",
          label: "否",
        },
        {
          value: "1",
          label: "二孩",
        },
        {
          value: "2",
          label: "三孩及以上",
        },
      ], // 是否多孩家庭
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
      if (str > this.accountInfo.grzhye) {
        return false;
      } else {
        return true;
      }
    },
    monthChange(value) {
      if (value) {
        this.basicInfomation.bctqyfjh = value.join(",");
      } else {
        this.basicInfomation.bctqyfjh = "";
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
          // console.log(res);
          tokenData = res.data;
          return tokenData;
        },
      });
      // this.token = sm2.decrypt3(tokenData.token);
      this.token = tokenData.token;
      this.$store.commit("token", tokenData.token);
      // console.log(this.token, "this.token");
      // this.authCode = sm2.decrypt3(tokenData.authCode);
      this.authCode = tokenData.authCode;
      this.$store.commit("authCode", tokenData.authCode);
      // console.log(this.authCode, "this.authCode");
      this.getLoginUser();
    },
    getLoginUser() {
      getUserInfo().then((res) => {
        // console.log(res);
        if (res.code === "200") {
          // console.log(res);
          // this.basicInfo.name = sm2.decrypt2(res.params.currentUserName);
          // this.username = sm2.decrypt2(res.params.userName);
          // let sfz = sm2.decrypt2(res.params.userId);
          // this.basicInfo.num = "***************" + sfz.slice(14, 18);
          this.userid = sm2.decrypt2(res.params.userId);
          let userInfo = res.params;
          this.userInfo = userInfo;
          this.$store.commit("userInfo", userInfo);
          // console.log(userInfo, "用户信息");
          this.getInfo(this.userInfo);
          // this.getBank();
        } else {
          location.href =
            "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
            encodeURIComponent(location.href);
        }
      });
    },
    getInfo() {
      // console.log(userInfo, "userInfo");
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
      // console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          // console.log(res, "公积金信息");
          if (res.code == 200 && res.success) {
            res = JSON.parse(tools.decryptSM2(res.data.data)).result;
            // console.log(res, "解密公积金信息");
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.$store.commit("accountInfo", res.data);
            loading.close();
          } else {
            loading.close();
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch(() => {
          loading.close();
          // console.log(err, "err");
          this.$message.error("网络异常，请稍后重试！");
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
          // console.log(res, "银行列表");
          res = JSON.parse(tools.decryptSM2(res.data.data)).result;
          // console.log(res, "银行列表");
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
            // console.log("this.bankColumns", this.bankColumns);
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
    // handleChange() {
    //   let result = this.$refs.cascader.getCheckedNodes();
    //   // console.log(result, "result");
    //   this.basicInfo.sqrkhyhmc = result[0].label;
    //   this.basicInfo.sqrkhyhdm = result[0].value;
    // },
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
            method: "tqspSaveJbxx.action",
          };
          IndexApi.queryList("tygjj", "commonGjj", param)
            .then((res) => {
              // console.log(res, "提取验证");
              res = JSON.parse(tools.decryptSM2(res.data.data)).result;
              // console.log(res, "123456");
              if (res.code == "00" && res.data) {
                loading.close();
                this.$router.push({
                  path: "/results",
                  query: {
                    jlbh: res.data.jlbh,
                  },
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
