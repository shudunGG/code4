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
          <div class="concent_info_t">经办信息</div>
          <div class="concent_info_picker" @click="showPickerRoomType = true">
            <div class="picker_t"><span>*</span>经办人姓名：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.jbrxm"
              placeholder="请填写经办人姓名"
            />
          </div>
          <div class="concent_info_picker" @click="showPicker1 = true">
            <div class="picker_t"><span>*</span>经办人证件类型：</div>
            <van-field
              maxlength="30"
              v-model="jbrzjlx"
              placeholder="请选择经办人证件类型"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>经办人证件号码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.jbrzjhm"
              placeholder="请填写经办人证件号码"
            />
          </div>
          <div class="concent_info_picker" @click="showPickers = true">
            <div class="picker_t"><span>*</span>受托银行名称：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.styhmc"
              placeholder="请选择受托银行名称"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>受托银行代码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.styhdm"
              placeholder="请选择受托银行代码"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>经办人手机号码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.jbrsjhm"
              placeholder="请填写经办人手机号码"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>经办人固定电话号码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.jbrgddhhm"
              placeholder="请填写经办人固定电话号码"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>统一社会信用代码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.tyshxydm"
              placeholder="请填写统一社会信用代码"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位缴存比例(%)：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.dwjcbl"
              placeholder="请填写单位缴存比例"
              type="number"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>个人缴存比例(%)：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.grjcbl"
              placeholder="请填写个人缴存比例"
              type="number"
            />
          </div>
          <div class="concent_info_picker" @click="showPicker = true">
            <div class="picker_t"><span>*</span>网点编号：</div>
            <van-field
              maxlength="30"
              v-model="wdbh"
              placeholder="请选择网点编号"
              readonly
            />
          </div>
        </div>
        <button class="queryButton" type="info" @click="queryList">
          提交
        </button>
      </div>

      <!-- 底部 -->
      <div class="footer">
        <div class="footer-content">本服务由张掖市住房公积金管理中心提供</div>
      </div>
    </div>
    <!-- <van-popup round position="bottom" v-model="showPicker">
      <van-datetime-picker
        v-model="currentDate"
        type="date"
        title="选择年月日"
        :min-date="minDate"
        :max-date="maxDate"
        @confirm="onConfirm"
        @cancel="showPicker = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPicker1">
      <van-datetime-picker
        v-model="currentDate1"
        type="date"
        title="选择年月日"
        :min-date="minDate"
        :max-date="maxDate"
        @confirm="onConfirm1"
        @cancel="showPicker1 = false"
      />
    </van-popup> -->
    <van-popup round position="bottom" v-model="showPicker1">
      <van-picker
        title="证件类型"
        show-toolbar
        :columns="zjlxColumns"
        @confirm="onConfirm"
        @cancel="showPicker1 = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPickers">
      <van-picker
        title="受托银行"
        show-toolbar
        :columns="bankList"
        @confirm="Confirm"
        @cancel="showPickers = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPicker">
      <van-picker
        title="网点编号"
        show-toolbar
        :columns="branchList"
        @confirm="confirm"
        @cancel="showPicker = false"
      />
    </van-popup>
  </div>
</template>
<script>
import { getUserInfo } from "../api/getUserInfo";
import tools, {
  name,
  IdNumberDesensitization,
  changDateFormate,
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
      maxDate: new Date(2045, 12, 31),
      currentDate: new Date(), //开始日期
      currentDate1: new Date(), //结束日期
      show: true,
      showPicker: false,
      showPicker1: false,
      showPickers: false,
      showPickerRoomType: false,
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
      wdbh: "",
      zjlxColumns: [
        { text: "护照", value: "03" },
        { text: "外国人永久居留证", value: "04" },
        { text: "身份证", value: "01" },
        { text: "军官证", value: "02" },
        { text: "其他", value: "99" },
      ], // 证件类型
      jbrzjlx: "", //证件类型
      bankList: [],
      branchList: [],
      chsydkfsColumns: [
        {
          value: "1",
          text: "一次性结清",
        },
        {
          value: "2",
          text: "按年偿还",
        },
        {
          value: "3",
          text: "本中心公积金贷款",
        },
        {
          value: "4",
          text: "外中心公积金贷款",
        },
        {
          value: "5",
          text: "本中心全部还清贷款一年内提取",
        },
        {
          value: "6",
          text: "外中心全部还清贷款一年内提取",
        },
      ], // 偿还贷款方式
      chsydkfs: "", // 偿还贷款方式中文字段
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
    console.log(this.$store.state.basicInfo, "用户信息");
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
          this.getBankList(this.userInfo);
          this.getBranchList(this.userInfo);
        }
      });
    },
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern =
        /[`~!@#$^&*()=|{}':;',\\[\]<>/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.basicInfomation.jbrxm = this.basicInfomation.jbrxm.replace(
        pattern,
        ""
      );
      this.basicInfomation.jbrzjlx = this.basicInfomation.jbrzjlx.replace(
        pattern,
        ""
      );
      this.basicInfomation.jbrzjhm = this.basicInfomation.jbrzjhm.replace(
        pattern,
        ""
      );
      this.basicInfomation.styhmc = this.basicInfomation.styhmc.replace(
        pattern,
        ""
      );
      this.basicInfomation.styhdm = this.basicInfomation.styhdm.replace(
        pattern,
        ""
      );
      this.basicInfomation.jbrsjhm = this.basicInfomation.jbrsjhm.replace(
        pattern,
        ""
      );
      this.basicInfomation.jbrgddhhm = this.basicInfomation.jbrgddhhm.replace(
        pattern,
        ""
      );
      this.basicInfomation.tyshxydm = this.basicInfomation.tyshxydm.replace(
        pattern,
        ""
      );
      this.basicInfomation.fwdj = this.basicInfomation.fwdj.replace(
        pattern,
        ""
      );
      this.basicInfomation.dwjcbl = this.basicInfomation.dwjcbl.replace(
        pattern,
        ""
      );
      this.basicInfomation.grjcbl = this.basicInfomation.grjcbl.replace(
        pattern,
        ""
      );
      this.basicInfomation.wdbh = this.basicInfomation.wdbh.replace(
        pattern,
        ""
      );
    },
    onConfirm({ text, value }) {
      this.jbrzjlx = text;
      this.basicInfomation.jbrzjlx = value;
      this.showPicker1 = false;
    },
    onConfirm1(value) {
      console.log(changDateFormate(value));
      this.basicInfomation.jhjgrq = changDateFormate(value);
      this.showPicker = false;
    },
    confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.wdbh = text;
      this.basicInfomation.wdbh = value;
      this.showPicker = false;
    },
    Confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.basicInfomation.styhmc = text;
      this.basicInfomation.styhdm = value;
      this.showPickers = false;
    },
    // Confirm({ text, value }) {
    //   console.log(`当前值：${text}, 当前索引：${value}`);
    //   this.fwxz = text;
    //   this.basicInfomation.fwxz = value;
    //   this.showPickers = false;
    // },
    getInfo(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: userInfo.userCertNum,
            // xingming: userInfo.userName,
            // zjhm: "411082199810172473",
            xxly: "2",
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
    // 获取受托银行列表
    getBankList(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "cbyhxxQry.action",
      };
      console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "受托银行列表");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          console.log(res, "受托银行列表");
          if (res.code == "00" && res.data) {
            res.data.forEach((item) => {
              this.bankList.push({
                value: item.styhdm,
                text: item.styhmc,
              });
            });
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
    // 获取网点编号
    getBranchList(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            xxly: "G",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "wdxxList.action",
      };
      console.log(param, "param");
      // 'dxgjjbll', 'gjjQuery',
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "网点编号列表");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          console.log(res, "网点编号列表");
          if (res.code == "00" && res.data) {
            res.data.forEach((item) => {
              this.branchList.push({
                value: item.key,
                text: item.value,
              });
            });
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
      if (this.basicInfomation.jbrxm === "") {
        Toast("请填写经办人姓名");
        return;
      }
      if (this.basicInfomation.jbrzjlx === "") {
        Toast("请选择经办人证件类型");
        return;
      }
      if (this.basicInfomation.jbrzjhm === "") {
        Toast("请填写经办人证件号码");
        return;
      }
      let sfz =
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!sfz.test(this.basicInfomation.jbrzjhm) && this.jbrzjlx == "身份证") {
        Toast("请输入正确的身份证号码");
        return;
      }
      if (this.basicInfomation.styhmc === "") {
        Toast("请选择受托银行名称");
        return;
      }
      if (this.basicInfomation.styhdm === "") {
        Toast("请选择受托银行代码");
        return;
      }
      if (this.basicInfomation.jbrsjhm === "") {
        Toast("请填写经办人手机号码");
        return;
      }
      let phone = /^1[34578]\d{9}$/;
      if (!phone.test(this.basicInfomation.jbrsjhm)) {
        Toast("请输入正确的手机号码");
        return;
      }
      if (this.basicInfomation.jbrgddhhm === "") {
        Toast("请填写经办人固定电话号码");
        return;
      }
      if (this.basicInfomation.tyshxydm === "") {
        Toast("请填写统一社会信用代码");
        return;
      }
      if (this.basicInfomation.dwjcbl === "") {
        Toast("请填写单位缴存比例");
        return;
      }
      if (
        Number(this.basicInfomation.dwjcbl) < 5 ||
        Number(this.basicInfomation.dwjcbl) > 12
      ) {
        Toast("单位缴存比例不得低于5%或高于12%");
        return;
      }
      if (this.basicInfomation.grjcbl === "") {
        Toast("请填写个人缴存比例");
        return;
      }
      if (
        Number(this.basicInfomation.grjcbl) < 5 ||
        Number(this.basicInfomation.grjcbl) > 12
      ) {
        Toast("个人缴存比例不得低于5%或高于12%");
        return;
      }
      if (this.basicInfomation.fwxz === "") {
        Toast("请填写不动产证号");
        return;
      }
      if (this.basicInfomation.wdbh === "") {
        Toast("请选择网点编号");
        return;
      }
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });

      let formInfo = {
        ...this.basicInfomation,
        ...this.$store.state.basicInfo,
      };
      console.log(formInfo, "formInfo");
      let param = {
        params: tools.encryptSM2(JSON.stringify(formInfo), this.publicKey1),
        ds: "zy",
        token: tools.encryptSM2(
          this.userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "dwzhxxAdd.action",
      };
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "123");
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          console.log(res, "提交表单");
          if (res.code == "00" && res.data) {
            this.$toast.clear();
            this.$router.push({
              path: "/success",
            });
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
