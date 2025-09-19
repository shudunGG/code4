<template>
  <div class="page">
    <div class="container-wrap">
      <!-- banner背景 -->
      <div class="top-bg">
        <div class="top-bg-l">
          <img src="../assets/images/touxiang.png" alt="" srcset="" />
        </div>
        <div class="top-bg-r">
          <div>姓名：{{ name(accountInfo.xingming) }}</div>
          <div>开户日期：{{ accountInfo.khrq.slice(0, 10) }}</div>
          <div>证件号码：{{ IdNumberDesensitization(accountInfo.zjhm) }}</div>
        </div>
      </div>
      <!-- 主内容 -->
      <div class="concent">
        <!-- 个人账户余额： -->
        <div class="concent_account">
          <div class="concent_account_t">
            <div class="account_l">个人账户余额：</div>
            <div class="account_r">
              <span class="account_r_t"
                >{{ show ? accountInfo.grzhyetm : accountInfo.grzhye }}元</span
              >
              <span @click="show = !show">
                <img src="@/assets/icon/by.png" alt="" v-if="show" />
                <img src="@/assets/icon/zy.png" alt="" v-else />
              </span>
            </div>
          </div>
          <div class="concent_account_l"></div>
          <div class="concent_account_b">
            <div class="account_b_t">缴存单位：</div>
            <div class="account_b_m">{{ accountInfo.dwmc }}</div>
          </div>
        </div>
        <!-- 基本信息（必填） -->
        <div class="concent_info">
          <div class="concent_info_t">基本信息（必填）</div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>提取金额（元）：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.sqtqjexx"
              placeholder="请输入提取金额"
            />
          </div>
          <div class="concent_info_picker" @click="showPickers = true" >
            <div class="picker_t"><span>*</span>申请人账户类别：</div>
            <van-field
              maxlength="30"
              v-model="tqzhlb"
              placeholder="请选择申请人账户类别"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>申请人收款户名：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.sqrskhm"
              placeholder="请填写申请人收款户名"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>申请人收款账号</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.sqrskzh"
              placeholder="请填写申请人收款账号"
            />
          </div>
          <div class="concent_info_picker" @click="showBankPicker = true">
            <div class="picker_t"><span>*</span>申请人开户银行名称：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.sqrkhyhmc"
              placeholder="请选择申请人开户银行名称"
              readonly
            />
          </div>
        </div>
        <button class="queryButton" type="info" @click="queryList">
          下一步
        </button>
      </div>

      <!-- 底部 -->
      <div class="footer">
        <div class="footer-content">本服务由张掖市住房公积金管理中心提供</div>
      </div>
    </div>
    <van-popup round position="bottom" v-model="showBankPicker">
      <van-cascader
        v-model="cascaderValue"
        title="请选择开户银行"
        :options="bankColumns"
        @close="showBankPicker = false"
        @change="onChange"
        @finish="onFinish"
        :field-names="{ text: 'yhmc', value: 'yhdm', children: 'nodes' }"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPickers">
      <van-picker
        title="申请人账户类别"
        show-toolbar
        :columns="columns"
        @confirm="Confirm"
        @cancel="showPickers = false"
      />
    </van-popup>
  </div>
</template>
<script>
import { getUserInfo } from "../api/getUserInfo";
import tools, {
  name,
  IdNumberDesensitization,
  // changDateFormate,
  formatDate,
  replaceAll,
} from "../tools/tool";
import { serveAPI } from "../api/serve";
import { Toast } from "vant";

export default {
  name: "index",
  data() {
    return {
      minDate: new Date(1900, 0, 1),
      maxDate: new Date(2025, 10, 1),
      currentDate: new Date(),
      show: true,
      showPicker: false,
      showPickers: false,
      showBankPicker: false,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "",
        grzhyetm: "",
        dwmc: "",
      },
      basicInfo: {
        grzh: "",
        tqzhlb: "",
        zjhm: "",
        // gfrsfzh: "411082199810172473",
        sqrskhm: "",
        sqrskzh: "",
        tqlx: "12",
        xxly: "G",
        tqyydm: "04",
        sqrkhyhdm: "",
        sqrkhyhmc: "",
        // gfrxm: "测试",
      },
      columns: [
        {text:"个人",value:"1"},
        {text:"开发商",value:"2"},
      ], //提取人身份
      bankColumns: [], // 银行列表
      cascaderValue: "",
      userInfo: {}, //用户信息
      publicKey1:
        "044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e",
      privateKey1:
        "3fce91c669c4769381384edcd8d63515362f222e9025f7f8b52f4f7ef2c38c69",
    };
  },
  created() {
    this.$store.commit("userInfo", {}); //第一次进入先清空一下缓存
    this.getUserInfo();
  },
  mounted() {},
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    // 获取用户信息
    getUserInfo() {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });

      getUserInfo().then((res) => {
        console.log(res);
        if (res.data == "{}" || !res.data) {
          this.$Dialog
            .alert({
              message: "当前未登录，请先登录",
            })
            .then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close();
            });
        } else {
          let userInfo = res.data;
          this.$store.commit("userInfo", userInfo);
          console.log(userInfo, "用户信息");
          this.userInfo = userInfo;
          this.getInfo(this.userInfo);
          this.queryBankList();
        }
      });
    },
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern =
        /[`~!@#$^&*()=|{}':;',\\[\]<>/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.basicInfo.tqzhlb = this.basicInfo.tqzhlb.replace(pattern, "");
      this.basicInfo.sqrskhm = this.basicInfo.sqrskhm.replace(pattern, "");
      this.basicInfo.sqrskzh = this.basicInfo.sqrskzh.replace(pattern, "");
      this.basicInfo.sqrkhyhmc = this.basicInfo.sqrkhyhmc.replace(pattern, "");
      this.basicInfo.gfrxm = this.basicInfo.gfrxm.replace(pattern, "");
      this.basicInfo.gfrsfzh = this.basicInfo.gfrsfzh.replace(pattern, "");
      this.basicInfo.zjhm = this.basicInfo.zjhm.replace(pattern, "");
    },
    // 验证收款人名称是否为汉字
    // validate() {
    //   const pattern = /^[\u4e00-\u9fa5]+$/;
    //   if (!pattern.test(this.basicInfo.SKRMC)) {
    //     console.log(pattern.test(this.basicInfo.SKRMC));
    //     Toast({
    //       message: "请输入汉字",
    //     });
    //     return;
    //   }
    //   if (!pattern.test(this.basicInfo.SKRYH)) {
    //     Toast({
    //       message: "请输入汉字",
    //     });
    //     return;
    //   }
    // },
    onFinish({ selectedOptions }) {
      this.showBankPicker = false;
      console.log("selectedOptions", selectedOptions);
      this.basicInfo.sqrkhyhmc = selectedOptions.map((option) => option.yhmc)[
        selectedOptions.length - 1
      ];
      this.basicInfo.sqrkhyhdm = selectedOptions.map((option) => option.yhdm)[
        selectedOptions.length - 1
      ];
    },
    // onChange() {},

    Confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.tqzhlb = text;
      this.basicInfo.tqzhlb = value;
      this.showPickers = false;
    },

    // 基础银行列表查询
    queryBankList() {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            // zjhm: this.userInfo.userCertNum,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "jcyhTree.action",
      };
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "银行列表");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
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
            Toast({
              message: res.msg,
            });
          }
        })
        .catch(() => {
          Toast({
            message: "网络异常",
          });
        });
    },

    getInfo(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: userInfo.userCertNum,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "grxxDetail.action",
      };
      console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "123");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          console.log(res, "公积金信息");
          if (res.code == "00" && res.data) {
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.basicInfo.grzh = res.data.grzh;
            this.basicInfo.zjhm = res.data.zjhm;
            this.$store.commit("accountInfo", res.data);
            this.$toast.clear();
          } else {
            Toast({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          Toast({
            message: "网络异常，请稍后重试！",
          });
        });
    },

    queryList() {
      // this.keyUp()
      if (this.basicInfo.tqzhlb === "") {
        Toast("请选择申请人账户类别");
        return;
      }
      if (this.basicInfo.sqrskhm === "") {
        Toast("请填写申请人收款户名");
        return;
      }
      if (this.basicInfo.sqrskzh === "") {
        Toast("请填写申请人收款账号");
        return;
      }
      if (this.basicInfo.sqrkhyhmc === "") {
        Toast("请选择申请人开户银行名称");
        return;
      }
      // if (this.basicInfo.gfrxm === "") {
      //   Toast("请填写购房人姓名");
      //   return;
      // }
      // if (this.basicInfo.gfrsfzh === "") {
      //   Toast("请填写购房人身份证号");
      //   return;
      // }
      // if (this.basicInfo.zjhm === "") {
      //   Toast("请填写身份证号");
      //   return;
      // }
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: this.userInfo.userCertNum,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "G",
            // grzh: "20107000077360000002",
            tqlx: "12",
            tqyydm: "04",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "judgeGjjtqsqGrxx.action",
      };
      serveAPI.queryList("tygjj", "commonGjj", param).then((res) => {
        console.log(res, "提取验证");
        res = JSON.parse(
          tools.decryptSM2(res.data.data, this.privateKey1)
        ).result;
        console.log(res, "123456");
        if ((res.code == "01" || res.code == "00") && res.data) {
          this.$router.push("/form");
          this.$toast.clear();
        } else {
          Toast({
            message: res.msg || "未查询到相关信息",
          });
        }
      });
      this.$store.commit("basicInfo", this.basicInfo);
    },
  },
};
</script>

<style scoped lang="less">
.page {
  width: 100%;
  min-height: 100vh;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  .container-wrap {
    // 头部背景
    .top-bg {
      display: flex;
      align-items: center;
      padding: 16px 12px 12px 22px;
      .top-bg-l {
        width: 75px;
        height: 75px;
        margin-right: 12px;
      }
      .top-bg-r {
        font-weight: 700;
        font-size: 13px;
        div {
          line-height: 28px;
        }
      }
    }
    // 主内容
    .concent {
      padding: 0 12px;

      .concent_account {
        padding: 12px;
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 0 6px #00000012;
        .concent_account_t {
          display: flex;
          justify-content: space-between;
          align-items: center;
          .account_l {
            font-weight: 700;
            font-size: 15px;
            color: #3f87f3;
          }
          .account_r {
            display: flex;
            .account_r_t {
              margin-right: 8px;
            }
            span {
              line-height: 15px;
              img {
                width: 16px;
                height: 16px;
              }
            }
          }
        }
        .concent_account_l {
          margin: 12px 0;
          border-bottom: 2px dashed #eeeeee;
        }
        .concent_account_b {
          line-height: 28px;
          .account_b_t {
            font-weight: 700;
            font-size: 13px;
            color: #333;
          }
          .account_b_m {
            color: #999;
          }
        }
      }
      // 基本信息
      .concent_info {
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 0 6px #00000012;
        margin-top: 8px;
        padding: 0 12px;
        margin-bottom: 30px;
        .concent_info_t {
          padding: 12px 0;
          font-weight: 700;
          font-size: 15px;
          color: #3f87f3;
          border-bottom: 1px solid #eee;
        }
        .concent_info_picker:last-child {
          border: 0;
        }
        .concent_info_picker {
          border-bottom: 1px solid #eee;
          .picker_t {
            font-size: 13px;
            font-weight: 700;
            padding: 12px 0 0;
            span {
              color: #d11313;
              margin-right: 6px;
            }
          }
        }
      }
    }
    .queryButton {
      height: 45px;
      border-radius: 4px;
      background: #3f87f3;
      color: #fff;
      font-size: 18px;
      width: 100%;
      font-weight: 700;
    }
  }
  // 底部
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    bottom: 0;
    left: 0;
    z-index: 0;

    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
  /deep/ .van-cell {
    padding: 8px 15px 12px;
    font-size: 13px;
  }
  /deep/ .van-field__control {
    -webkit-text-fill-color: #999;
  }
  /deep/ .van-field__control:disabled {
    -webkit-text-fill-color: #999;
  }
}
</style>
