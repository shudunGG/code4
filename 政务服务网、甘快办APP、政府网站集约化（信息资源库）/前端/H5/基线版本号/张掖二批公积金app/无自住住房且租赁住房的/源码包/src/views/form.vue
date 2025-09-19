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
          <div class="concent_info_picker" @click="showPicker = true">
            <div class="picker_t"><span>*</span>租赁合同日期：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.qzrq"
              placeholder="请选择租赁合同日期"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>租房合同编号：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.zfhtbh"
              placeholder="请填写租房合同编号"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>房租支出（元）：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.fzzc"
              placeholder="请填写房租支出"
            />
          </div>
          <div class="concent_info_picker" @click="showPickerRoomType = true">
            <div class="picker_t"><span>*</span>房屋类型：</div>
            <van-field
              maxlength="30"
              v-model="zzfwlx"
              placeholder="请选择房屋类型"
              readonly
            />
          </div>
          <div class="concent_info_picker" @click="showPickers = true">
            <div class="picker_t"><span>*</span>提取类型：</div>
            <van-field
              maxlength="30"
              v-model="zftqlx"
              placeholder="请选择提取类型"
              readonly
            />
          </div>
          <div class="concent_info_picker" @click="showPicker1 = true">
            <div class="picker_t"><span>*</span>是否多孩家庭：</div>
            <van-field
              maxlength="30"
              v-model="sfdhjt"
              placeholder="请选择是否多孩家庭"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>租房地址：</div>
            <van-field
              maxlength="30"
              v-model="basicInfomation.fwdz"
              placeholder="请填写租房地址"
            />
          </div>
          <div class="concent_info_picker" @click="showMonth = true">
            <div class="picker_t"><span>*</span>提取月份：</div>
            <van-field
              maxlength="30"
              v-model="bctqyfjh"
              placeholder="请选择提取月份"
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
    <van-popup round position="bottom" v-model="showPicker">
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
    <van-popup round position="bottom" v-model="showPickers">
      <van-picker
        title="提取类型"
        show-toolbar
        :columns="zftqlxColumns"
        @confirm="Confirm"
        @cancel="showPickers = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPicker1">
      <van-picker
        title="是否多孩家庭"
        show-toolbar
        :columns="sfdhjtColumns"
        @confirm="Confirm1"
        @cancel="showPicker1 = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showPickerRoomType">
      <van-picker
        title="房屋类型"
        show-toolbar
        :columns="roomTypeColumns"
        @confirm="confirm"
        @cancel="showPickerRoomType = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showMonth">
      <van-picker
        title="提取月份"
        show-toolbar
        @confirm="monthConfirm"
        @cancel="showMonth = false"
        :columns="month"
      >
        <!-- :columns="month" -->
        <template #option="item">
          <!-- <div v-for="(item, index) in month" :key="index"> -->
          <div class="monthPicker">
            <van-checkbox v-model="item.checked" @change="monthChange(item)" />
            <div>{{ item.text }}</div>
          </div>
          <!-- </div> -->
        </template>
      </van-picker>
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
      maxDate: new Date(),
      currentDate: new Date(),
      show: true,
      showPicker: false,
      showPickers: false,
      showPicker1: false,
      showPickerRoomType: false,
      showMonth: false,
      checked: false,
      accountInfo: {
        xingming: "",
        xingmingtm: "",
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
      zzfwlx: "",
      roomTypeColumns: [
        { text: "公共租赁租房", value: "1" },
        { text: "商品住房", value: "2" },
      ], //购房类型
      zftqlxColumns: [
        {
          value: "01",
          text: "按月提取",
        },
        {
          value: "02",
          text: "按年提取",
        },
      ], // 提取类型
      zftqlx: "", // 提取类型中文字段
      sfdhjtColumns: [
        {
          value: "0",
          text: "否",
        },
        {
          value: "1",
          text: "二孩",
        },
        {
          value: "2",
          text: "三孩及以上",
        },
      ], //是否多孩家庭
      sfdhjt: "", //是否多孩家庭中文
      month: [
        { text: "1月", value: "1", checked: false },
        { text: "2月", value: "2", checked: false },
        { text: "3月", value: "3", checked: false },
        { text: "4月", value: "4", checked: false },
        { text: "5月", value: "5", checked: false },
        { text: "6月", value: "6", checked: false },
        { text: "7月", value: "7", checked: false },
        { text: "8月", value: "8", checked: false },
        { text: "9月", value: "9", checked: false },
        { text: "10月", value: "10", checked: false },
        { text: "11月", value: "11", checked: false },
        { text: "12月", value: "12", checked: false },
      ],
      bctqyfjh:"",
      monthArray: [],
      monthText:[],
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
          this.userInfo = userInfo;
          this.getInfo(this.userInfo);
        }
      });
    },
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern =
        /[`~!@#$^&*()=|{}':;',\\[\]<>/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.basicInfomation.zzfwlx = this.basicInfomation.zzfwlx.replace(
        pattern,
        ""
      );
      this.basicInfomation.zfhtbh = this.basicInfomation.zfhtbh.replace(
        pattern,
        ""
      );
      this.basicInfomation.fzzc = this.basicInfomation.fzzc.replace(
        pattern,
        ""
      );
      this.basicInfomation.sflxdh = this.basicInfomation.sflxdh.replace(
        pattern,
        ""
      );
      this.basicInfomation.fwdz = this.basicInfomation.fwdz.replace(
        pattern,
        ""
      );
      this.basicInfomation.gfhthxyh = this.basicInfomation.gfhthxyh.replace(
        pattern,
        ""
      );
      this.basicInfomation.zftqlx = this.basicInfomation.zftqlx.replace(
        pattern,
        ""
      );
      this.basicInfomation.jzmj = this.basicInfomation.jzmj.replace(
        pattern,
        ""
      );
      this.basicInfomation.fwdj = this.basicInfomation.fwdj.replace(
        pattern,
        ""
      );
      this.basicInfomation.fwzj = this.basicInfomation.fwzj.replace(
        pattern,
        ""
      );
      this.basicInfomation.sfdhjt = this.basicInfomation.sfdhjt.replace(
        pattern,
        ""
      );
      this.basicInfomation.qzrq = this.basicInfomation.qzrq.replace(
        pattern,
        ""
      );
    },
    onConfirm(value) {
      console.log(changDateFormate(value));
      this.basicInfomation.qzrq = changDateFormate(value);
      this.showPicker = false;
    },
    confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.zzfwlx = text;
      this.basicInfomation.zzfwlx = value;
      this.showPickerRoomType = false;
    },
    Confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.zftqlx = text;
      this.basicInfomation.zftqlx = value;
      this.showPickers = false;
    },
    Confirm1({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.sfdhjt = text;
      this.basicInfomation.sfdhjt = value;
      this.showPicker1 = false;
    },
    monthConfirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.basicInfomation.bctqyfjh = this.monthArray.join(",");
      this.bctqyfjh = this.monthText.join(",");
      this.showMonth = false;
      console.log(this.basicInfomation.bctqyfjh,"bctqyfjh");
    },
    monthChange(item) {
      console.log(item, "item");
      if (item.checked) {
        this.monthArray.push(item.value);
        this.monthText.push(item.text);
      }else{
        this.monthArray.splice(this.monthArray.indexOf(item.value), 1);
        this.monthText.splice(this.monthText.indexOf(item.text), 1);
      }
      console.log(" this.monthArray", this.monthArray);
      console.log(" this.monthText", this.monthText);
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
            this.$store.commit("accountInfo", res.data);
            this.$store.commit("userInfo", userInfo);
            console.log(this.$store.state.userInfo, "用户信息");
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
      if (this.basicInfomation.qzrq === "") {
        Toast("请选择租赁合同日期");
        return;
      }
      if (this.basicInfomation.zfhtbh === "") {
        Toast("请填写租房合同编号");
        return;
      }
      if (this.basicInfomation.fzzc === "") {
        Toast("请填写房租支出");
        return;
      }
      if (this.basicInfomation.zzfwlx === "") {
        Toast("请选择房屋类型");
        return;
      }
      if (this.basicInfomation.zftqlx === "") {
        Toast("请选择提取类型");
        return;
      }
      if (this.basicInfomation.sfdhjt === "") {
        Toast("请选择是否多孩家庭");
        return;
      }
      if (this.basicInfomation.fwdz === "") {
        Toast("请填写租房地址");
        return;
      }

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
        method: "tqspSaveJbxx.action",
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
              path: "/detail",
              query: {
                jlbh: res.data.jlbh,
              },
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
  .monthPicker {
    display: flex;
    justify-content: space-around;
    width: 80px;
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
