<template>
  <section class="result-container">
    <div v-loading="loading1">
      <div class="query-title">
        <div style="display: flex; align-items: center">
          <div class="title-vertical"></div>
          <div>查询结果</div>
        </div>
      </div>
      <div class="result">
        <template>
          <table>
            <!-- <tr>
              <td class="tab_bt">用户姓名</td>
              <td>{{ desensitizedName(resultData.yhxm) || "暂无数据" }}</td>
              <td class="tab_bt">用户地址</td>
              <td>
                {{
                  resultData.yhdz
                    ? desensitizedCommon(resultData.yhdz)
                    : "暂无数据"
                }}
              </td>
            </tr> -->
            <tr>
              <td class="tab_bt">合同编号</td>
              <td>
                {{ resultData.ZLFHTBH || "暂无数据" }}
              </td>
              <td class="tab_bt">出租方姓名</td>
              <td>
                {{
                  hyposensitization(resultData.SELLERXM, "姓名") || "暂无数据"
                }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">出租方证件号码</td>
              <td>
                {{
                  hyposensitization(resultData.SELLERZJHM, "号码") || "暂无数据"
                }}
              </td>
              <td class="tab_bt">出租方联系方式</td>
              <td>{{ phone(resultData.SELLERLXFS) || "暂无数据" }}</td>
            </tr>
            <tr>
              <td class="tab_bt">出租方联系地址</td>
              <td>
                {{ address(resultData.SELLERLXDZ) || "暂无数据" }}
              </td>
              <td class="tab_bt">房屋性质</td>
              <td>{{ resultData.FWXZ || "暂无数据" }}</td>
            </tr>
            <tr>
              <td class="tab_bt">房屋坐落</td>
              <td>
                {{ resultData.FWZL || "暂无数据" }}
              </td>
              <td class="tab_bt">房屋面积（㎡）</td>
              <td>
                {{ resultData.FWMJ || "暂无数据" }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">成交价格（元）</td>
              <td>
                {{ resultData.CJJG || "暂无数据" }}
              </td>
              <td class="tab_bt">抵押情况</td>
              <td>
                {{ resultData.DYQK == "1" ? `未抵押` : `已抵押` || "暂无数据" }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">抵押权人</td>
              <td>
                {{ resultData.DYQR || "暂无数据" }}
              </td>
              <td class="tab_bt">抵押期限</td>
              <td>
                {{ resultData.DYQX || "暂无数据" }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">租赁情况</td>
              <td>
                {{ resultData.ZLQK == "1" ? `未出租` : `已出租` || "暂无数据" }}
              </td>
              <td class="tab_bt">房屋押金</td>
              <td>
                {{ resultData.FWDJ || "暂无数据" }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">交房日期</td>
              <td>
                {{ resultData.JFRQ || "暂无数据" }}
              </td>
              <td class="tab_bt">承租人姓名</td>
              <td>
                {{
                  hyposensitization(resultData.MSRLIST[0].MSRXM, "姓名") ||
                  "暂无数据"
                }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">承租人证件类型</td>
              <td>
                {{ resultData.MSRLIST[0].MSRZJLX || "暂无数据" }}
              </td>
              <td class="tab_bt">承租人证件号码</td>
              <td>
                {{
                  hyposensitization(resultData.MSRLIST[0].MSRZJHM, "号码") ||
                  "暂无数据"
                }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">共有份额</td>
              <td>
                {{ resultData.MSRLIST[0].GYFE || "暂无数据" }}
              </td>
              <td class="tab_bt">联系方式</td>
              <td>
                {{ phone(resultData.MSRLIST[0].MSRLXFS) || "暂无数据" }}
              </td>
            </tr>
            <tr>
              <td class="tab_bt">联系地址</td>
              <td colspan="3">
                {{ address(resultData.MSRLIST[0].MSRLXDZ) || "暂无数据" }}
              </td>
            </tr>
          </table>
        </template>
      </div>
      <div class="form-btn" style="margin-left: 12px">
        <input type="button" class="submit" value="返回" @click="goBack" />
      </div>
    </div>
    <!-- <NoResult v-if="show" :text="text"/> -->
  </section>
</template>
<script>
// import sm2 from "../utils/sm2";
import IndexApi from "@/api/index";
import { desensitizedName, desensitizedCommon } from "@/tools/desensitized";
import tools, {
  name1,
  IdNumberDesensitization,
  phone,
  address,
} from "../tools/tool";
// import moment from 'moment'
export default {
  name: "IndexView",
  created() {},
  mounted() {
    this.resultData = JSON.parse(sessionStorage.getItem("data"))[0];
    // this.formData.identifierno = this.resultData.jf;
    console.log(this.resultData, "0.0.0.0.0.");
    // setTimeout(() => {
    // this.getList();
    // }, 1000);
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
        callback(new Error("不能含有特殊字符"));
      } else {
        callback();
      }
    };
    // 校验输入框
    // let validateInput = (rule, value, callback) => {
    //   if (!this.checkSpecialKey(value)) {
    //     callback(new Error("请输入正确金额"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      formDataParams: {},
      key: 0,
      show: false,
      isOpenResult: false,
      formData: {
        identifierno: "",
        code: "",
      },
      noData: false,
      startTime: "",
      endTime: "",
      list: [],
      total: 0,
      pageSize: 5,
      dialogTableVisible: false,
      loading: false,
      loading1: false,
      formRules: {
        code: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
          { min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur" },
          { validator: validateInputCode, trigger: "blur" },
          { validator: checkCode, trigger: "click" },
        ],
        identifierno: [
          { required: true, message: "缴费金额不能为空", trigger: "blur" },
          // { validator: validateInput, trigger: "blur" },
        ],
      },
      captcha: {
        uuid: "",
        sandCode: "",
      },
      resultzf: {},
      resultData: {},
      userInfo: JSON.parse(
        tools.decryptSM2(
          sessionStorage.getItem("userInfo"),
          "2cf4e55142ef69a391af5f03fda100533fb0f1fcdc43052b6d701624efd2cb44"
        )
      ),
      publicKey:
        "042a455299593c3a7ac95171d5147273238aa4d144bc0b2b7cad45aff042f2008090ee95dfe86a741044acbb517db86969527ca7d9ba122a030deac8844d050f8e",
    };
  },
  methods: {
    name1,
    IdNumberDesensitization,
    phone,
    address,
    goBack() {
      this.$router.push({
        path: "/",
      });
    },
    desensitizedName,
    desensitizedCommon,
    getList() {
      this.loading1 = true;
      const params = JSON.parse(sessionStorage.getItem("params"));
      let header = {
        token: this.userInfo.token,
        "auth-code": this.userInfo.authCode,
      };
      IndexApi.queryList1("wsgrjf", "wxGrUserQuery", params, header).then(
        (res) => {
          this.loading1 = false;
          if (res.code == "200" && res.success && res.data) {
            let resultData = JSON.parse(res.data);
            console.log(resultData, "resultData");
            this.formData.identifierno = resultData.jf;
            this.resultData = resultData;
          } else {
            this.loading1 = false;
            this.$message({
              type: "error",
              message: res.message,
            });
          }
        },
        () => {
          this.loading1 = false;
          this.$message({
            type: "error",
            message: "网络异常",
          });
        }
      );
    },
    // 文本框特殊字符校验
    checkSpecialKey(str) {
      if (Number(str) <= 0) {
        return false;
      }
      let specialKey =
        "/[`-~!@#$^&*=|{}':;'\\\\\\[\\]\\<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。、？\\]/";
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
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
      }
      return true;
    },
    async queryList(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.resultData.retMsg == "success") {
            this.bizOrderNo = await this.getBizOrderNo(this.resultData.yhckh);
            sessionStorage.setItem("bizOrderNo", this.bizOrderNo);
            this.payScenarioZS();
          } else if (
            !/^(([1-9]\d*)|\d)(\.\d{1,2})?$/.test(this.resultData.jf) ||
            this.resultData.jf == "0.00" ||
            this.resultData.jf == "0"
          ) {
            this.$message({
              type: "error",
              message: "此用户未欠费",
            });
            return;
          }
        } else {
          return false;
        }
      });
    },
    async resetForm(formName) {
      await this.updateCode();
      this.$refs[formName].resetFields();
    },
    // 获取时间yyyyMMddHHmmss和yyyyMMdd两种格式的时间
    formatTime(value) {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");
      const day = currentDate.getDate().toString().padStart(2, "0");
      const hours = currentDate.getHours().toString().padStart(2, "0");
      const minutes = currentDate.getMinutes().toString().padStart(2, "0");
      const seconds = currentDate.getSeconds().toString().padStart(2, "0");
      return value
        ? `${year}${month}${day}${hours}${minutes}${seconds}`
        : `${year}${month}${day}`;
    },
    convertTimestamp(timestamp) {
      let date = new Date(parseInt(timestamp));
      let year = date.getFullYear();
      let month = String(date.getMonth() + 1).padStart(2, "0");
      let day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    hyposensitization(str, value) {
      console.log(str);
      let arr = str.split(",");
      let argument = [];
      for (let i = 0; i < arr.length; i++) {
        console.log(i);
        if (value == "姓名") {
          if (!arr[i]) {
            argument.push("无");
          }
          // 两个字的姓名，隐藏第一个字
          if (arr[i].length === 2) {
            argument.push(arr[i].replace(/^./, "*"));
          }
          // 三个字的姓名，隐藏第一个字
          else if (arr[i].length === 3) {
            argument.push(arr[i].replace(/^./, "*"));
          }
          // 六个字以下，三个字以上，只显示最后两个字
          else if (arr[i].length <= 6 && arr[i].length >= 3) {
            argument.push(
              arr[i].replace(arr[i].substr(0, arr[i].length - 2), "***")
            );
          }
          // 六个字以上，只显示第一个字和最后一个字
          else if (arr[i].length > 6) {
            argument.push(arr[i].replace(/^(.).*(.)$/, "$1*****$2"));
          }
        } else {
          argument.push(arr[i].replace(/^(.).*(.)$/, "$1****************$2"));
        }
      }
      return argument.join();
    },
  },
};
</script>

<style scoped>
.table {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 50px;
  font-weight: 700;
  font-size: 16px;
  color: #333;
}
.table li {
  width: 30%;
  text-align: center;
}
.table-title {
  margin-top: 20px;
  border: 1px solid #dcdfe6;
  background: #fafafa;
}
.title-information {
  padding: 20px 0px;
  font-weight: 700;
  font-size: 16px;
  line-height: 32px;
  text-align: left;
  color: #333;
}
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
  margin-bottom: 100px;
  color: #999;
}
.select_w {
  width: 4.7rem;
}
.result-container .result {
  margin-top: 20px;
  margin-bottom: 30px;
}
.result-container .result:last-child {
  margin-bottom: 0px;
}
.result-container table td {
  background: #ffffff;
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
  padding: 0.14rem;
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
