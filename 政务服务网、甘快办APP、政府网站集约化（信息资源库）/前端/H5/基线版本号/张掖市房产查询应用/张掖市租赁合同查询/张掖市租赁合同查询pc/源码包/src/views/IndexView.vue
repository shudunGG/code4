<template>
  <div>
    <div class="query-title">
      <div class="title-vertical"></div>
      张掖市房屋租赁合同查询
    </div>
    <section class="query-container" v-loading="loading">
      <el-form
        action=""
        :model="formData"
        ref="formData"
        :rules="formRules"
        label-position="right"
        label-width="120px"
      >
        <el-form-item label="合同编号" prop="spfhtbh">
          <el-input
            class="slt"
            placeholder="请输入合同编号"
            v-model="formData.spfhtbh"
            maxlength="50"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="房屋坐落" prop="fwzl">
          <el-input
            class="slt"
            placeholder="请输入房屋坐落"
            v-model="formData.fwzl"
            maxlength="50"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="出租人姓名" prop="sellerxm">
          <el-input
            class="slt"
            placeholder="请输入出租人姓名（如有多个出租人用英文逗号隔开）"
            v-model="formData.sellerxm"
            maxlength="50"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="出租人身份证号" prop="sellerzjhm">
          <el-input
            class="slt"
            placeholder="请输入出租人身份证号（如有多个出租人用英文逗号隔开）"
            v-model="formData.sellerzjhm"
            maxlength="50"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="承租人姓名" prop="buyerxm">
          <el-input
            class="slt"
            placeholder="请输入承租人姓名（如有多个承租人用英文逗号隔开）"
            v-model="formData.buyerxm"
            maxlength="50"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="承租人身份证号" prop="buyerzjhm">
          <el-input
            class="slt"
            placeholder="请输入承租人身份证号（如有多个承租人用英文逗号隔开）"
            v-model="formData.buyerzjhm"
            maxlength="50"
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
            @click="queryList('formData')"
          />
          <input type="reset" @click="resetForm('formData')" />
        </div>
      </el-form>
    </section>
    <router-view :key="key"></router-view>
  </div>
</template>
<script>
import { createCode } from "@/utils/checkCode";
import { requestJpaas, requestJpaas1 } from "@/api/code";
import sm2 from "../utils/sm2";
import IndexApi from "@/api/index";
import tools from "../tools/tool";
export default {
  name: "IndexView",
  created() {},
  async mounted() {
    // 获取验证码
    await this.queryCode();
    this.$nextTick(() => {
      createCode(this.captcha.sandCode);
    });
  },
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
        callback(new Error("不能含有特殊字符与空格"));
      } else {
        callback();
      }
    };
    // 校验输入框
    let validateInput = (rule, value, callback) => {
      if (!this.checkSpecialKey(value)) {
        callback(new Error("不能含有特殊字符与空格"));
      } else {
        callback();
      }
    };
    return {
      bizOrderNo: "",
      key: 0,
      isOpenResult: false,
      formData: {
        spfhtbh: "",
        fwzl: "",
        buyerxm: "",
        buyerzjhm: "",
        sellerxm: "",
        sellerzjhm: "",
        code: "",
        hhlb: [],
      },

      loading: false,
      show: true,
      // userInfo: JSON.parse(
      //   tools.decryptSM2(
      //     sessionStorage.getItem("userInfo"),
      //     "2cf4e55142ef69a391af5f03fda100533fb0f1fcdc43052b6d701624efd2cb44"
      //   )
      // ),
      formRules: {
        code: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
          { min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur" },
          { validator: validateInputCode, trigger: "blur" },
          { validator: checkCode, trigger: "click" },
        ],
        spfhtbh: [
          { required: true, message: "合同编号不能为空", trigger: "blur" },
          { validator: validateInput, trigger: "blur" },
        ],
        fwzl: [
          { required: true, message: "房屋坐落不能为空", trigger: "blur" },
        ],
       
        // hhlb: [{ required: true, message: "请选择表号", trigger: "change" }],
      },
      captcha: {
        uuid: "",
        sandCode: "",
      },
    };
  },
  methods: {
    // 文本框特殊字符校验
    checkSpecialKey(str) {
      let specialKey =
        "/[`-~!@#$^&*=|{}':;'\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/";
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
        // if (str) {
        //   str = str.replace(/\s/g, "");
        //   if (str.length != this.formData.userNo.length) {
        //     return false;
        //   }
        // }
      }
      return true;
    },
    // 验证码特殊字符校验
    checkSpecialKeyCode(str) {
      let specialKey =
        "/[`-~!@#$^&*=|{}':;',\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/";
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
        if (str) {
          str = str.replace(/\s/g, "");
          if (str.length != this.formData.code.length) {
            return false;
          }
        }
      }
      return true;
    },
    //查询
    async queryList(formName) {
      let userInfo = JSON.parse(
        tools.decryptSM2(
          sessionStorage.getItem("userInfo"),
          "2cf4e55142ef69a391af5f03fda100533fb0f1fcdc43052b6d701624efd2cb44"
        )
      );
      if (userInfo.userType != "1") {
        this.$message.warning("该业务仅限个人用户办理");
        return;
      }
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.formData.buyerxm && this.formData.buyerzjhm == "") {
            this.$message({
              type: "error",
              message: "请输入承租方证件号码",
            });
            return;
          }
          if (this.formData.buyerzjhm && this.formData.buyerxm == "") {
            this.$message({
              type: "error",
              message: "请输入承租方姓名",
            });
            return;
          }
          if (this.formData.buyerxm == "" && this.formData.buyerzjhm == "") {
            delete this.formData.buyerxm;
            delete this.formData.buyerzjhm;
          }
          if (this.formData.sellerxm && this.formData.sellerzjhm == "") {
            this.$message({
              type: "error",
              message: "请输入出租方证件号码",
            });
            return;
          }
          if (this.formData.sellerzjhm && this.formData.sellerxm == "") {
            this.$message({
              type: "error",
              message: "请输入出租方姓名",
            });
            return;
          }
          if (this.formData.sellerxm == "" && this.formData.sellerzjhm == "") {
            delete this.formData.sellerxm;
            delete this.formData.sellerzjhm;
          }
          this.formData.code = "";
          this.loading = true;
          // let header = {
          //   token: userInfo.token,
          //   "auth-code": userInfo.authCode,
          // };
          const params = {
            params: sm2.encrypt(
              JSON.stringify({
                zlfhtbh: this.formData.spfhtbh,
                fwzl: this.formData.fwzl,
                sellerxm: this.formData.sellerxm,
                sellerzjhm: this.formData.sellerzjhm,
                buyerxm: this.formData.buyerxm,
                buyerzjhm: this.formData.buyerzjhm,
                tokenNo: userInfo.token,
                authCode: userInfo.authCode,
              })
            ),
          };
          IndexApi.queryList1("zysslhfwzqmiabu", "leaseContract", params).then(
            (res) => {
              this.loading = false;
              if (res.code == "200" && res.success && res.data) {
                console.log(res);
                let data = sm2.decrypt(res.data);
                console.log(data, "data");
                let resultData = JSON.parse(data);
                console.log(resultData);
                // let data = JSON.parse(resultData.data);
                if (resultData.length == 0) {
                  this.$message({
                    type: "error",
                    message: "当前合同编号暂未查询到数据，请检查后重新查询",
                  });
                  return;
                } else {
                  sessionStorage.setItem("data", data);
                  this.$router.push({
                    path: "/payfee",
                  });
                }
              } else {
                this.loading = false;
                this.$message({
                  type: "error",
                  message: res.message,
                });
              }
            },
            () => {
              this.loading = false;
              this.$message({
                type: "error",
                message: "网络异常",
              });
            }
          );
        } else {
          return false;
        }
      });
      this.updateCode();
    },
    async resetForm(formName) {
      await this.updateCode();
      this.$refs[formName].resetFields();
      this.formData.identifierno = "";
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
    //验证码校验
    async verifyCode() {
      try {
        let formData = new FormData();
        formData.append("uuid", this.captcha.uuid);
        formData.append("sandCode", this.formData.code);
        this.captcha.sandCode = this.formData.code;
        const result = await requestJpaas1.getData(this.captcha);
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
  },
};
</script>

<style scoped>
.query-title {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
  font-weight: 700;
  font-size: 16px;
  line-height: 32px;
  color: #333;
}
.title-vertical {
  width: 3px;
  height: 15px;
  margin-right: 10px;
  border-radius: 1.5px;
  background: #1e82d8;
}
.el-form-item {
  margin-bottom: 30px;
}
::v-deep.el-form-item__label {
  font-weight: 700;
  font-size: 16px;
  line-height: 50px;
  text-align: left;
  color: #333;
}
::v-deep.el-input__inner {
  height: 50px;
}
.question {
  margin-left: 78px;
  font-weight: 700;
  font-size: 16px;
  margin-bottom: 20px;
  color: #333;
}
.answer {
  margin-left: 78px;
  /* font-weight: 700; */
  font-size: 14px;
  margin-bottom: 30px;
  color: #999;
}
.select_w {
  width: 4.7rem;
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
