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
        <!-- <div class="concent_account">
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
        </div> -->
        <!-- 基本信息（必填） -->
        <div class="concent_info">
          <div class="concent_info_t">单位信息（必填）</div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位名称：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwmc"
              placeholder="请填写单位名称"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位地址：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwdz"
              placeholder="请填写单位地址"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位法人代表姓名：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwfrdbxm"
              placeholder="请填写单位法人代表姓名"
            />
          </div>
          <div class="concent_info_picker" @click="showZjlxPicker = true">
            <div class="picker_t"><span>*</span>单位法人代表证件类型：</div>
            <van-field
              maxlength="30"
              v-model="dwfrdbzjlx"
              placeholder="请选择单位法人代表证件类型"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位法人代表证件号码：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwfrdbzjhm"
              placeholder="请填写单位法人代表证件号码"
            />
          </div>
          <div class="concent_info_picker" @click="showDwjjlxPicker = true">
            <div class="picker_t"><span>*</span>单位经济类型：</div>
            <van-field
              maxlength="30"
              v-model="dwjjlx"
              placeholder="请选择单位经济类型"
              readonly
            />
          </div>
          <div class="concent_info_picker" @click="showDwsshyPicker = true">
            <div class="picker_t"><span>*</span>单位所属行业：</div>
            <van-field
              maxlength="30"
              v-model="dwsshy"
              placeholder="请选择单位所属行业"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位邮编：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwyb"
              placeholder="请填写单位邮编"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位电子邮箱：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwdzxx"
              placeholder="请填写单位电子邮箱"
            />
          </div>
          <div class="concent_info_picker" @click="showPicker1 = true">
            <div class="picker_t"><span>*</span>单位发薪日：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwfxr"
              placeholder="请选择单位发薪日"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位公积金经办部门：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwjbbm"
              placeholder="请填写单位公积金经办部门"
            />
          </div>
          <div class="concent_info_picker" @click="showDwxzPicker = true">
            <div class="picker_t"><span>*</span>单位性质：</div>
            <van-field
              maxlength="30"
              v-model="dwxz"
              placeholder="请选择单位性质"
              readonly
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位开户行户名：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwkhhhm"
              placeholder="请填写单位开户行户名"
            />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>单位开户行账号：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwkhhzh"
              placeholder="请填写单位开户行账号"
            />
          </div>
          <div class="concent_info_picker" @click="showDwlsgxPicker = true">
            <div class="picker_t"><span>*</span>单位隶属关系：</div>
            <van-field
              maxlength="30"
              v-model="dwlsgx"
              placeholder="请选择单位隶属关系"
              readonly
            />
          </div>
          <div class="concent_info_picker" @click="showPicker = true">
            <div class="picker_t"><span>*</span>单位设立日期：</div>
            <van-field
              maxlength="30"
              v-model="basicInfo.dwslrq"
              placeholder="请选择单位设立日期"
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
    <van-popup round position="bottom" v-model="showZjlxPicker">
      <van-picker
        title="证件类型"
        show-toolbar
        :columns="zjlxColumns"
        @confirm="confirm"
        @cancel="showZjlxPicker = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showDwxzPicker">
      <van-picker
        title="单位性质"
        show-toolbar
        :columns="dwxzColumns"
        @confirm="confirm1"
        @cancel="showDwxzPicker = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showDwlsgxPicker">
      <van-picker
        title="单位隶属关系"
        show-toolbar
        :columns="dwlsgxColumns"
        @confirm="confirm2"
        @cancel="showDwlsgxPicker = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showDwjjlxPicker">
      <van-picker
        title="单位经济类型"
        show-toolbar
        :columns="dwjjlxColumns"
        @confirm="confirm3"
        @cancel="showDwjjlxPicker = false"
      />
    </van-popup>
    <van-popup round position="bottom" v-model="showDwsshyPicker">
      <van-picker
        title="单位所属行业"
        show-toolbar
        :columns="dwsshyColumns"
        @confirm="confirm4"
        @cancel="showDwsshyPicker = false"
      />
    </van-popup>
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
    <van-popup round position="bottom" v-model="showPicker1">
      <van-picker
        title="单位发薪日"
        show-toolbar
        :columns="dayColumns"
        @confirm="onConfirm1"
        @cancel="showPicker1 = false"
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
import { Toast,Dialog } from "vant";

export default {
  name: "index",
  data() {
    return {
      minDate: new Date(1900, 0, 1),
      maxDate: new Date(),
      currentDate: new Date(),
      show: true,
      showPicker: false,
      showPicker1: false,
      showPickers: false,
      showZjlxPicker: false,
      showDwlsgxPicker: false,
      showDwxzPicker: false,
      showDwjjlxPicker: false,
      showDwsshyPicker: false,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "",
        grzhyetm: "",
        dwmc: "",
      },
      basicInfo: {
        dwmc: "",
        dwdz: "",
        dwfrdbxm: "",
        dwfrdbzjlx: "",
        dwfrdbzjhm: "",
        dwjjlx: "",
        dwsshy: "",
        dwyb: "",
        dwdzxx: "",
        dwfxr: "",
        dwjbbm: "",
        dwxz: "",
        dwkhhhm: "",
        dwkhhzh: "",
        dwlsgx: "",
        dwslrq: "",
      },
      dwfrdbzjlx: "",
      zjlxColumns: [
        { text: "护照", value: "03" },
        { text: "外国人永久居留证", value: "04" },
        { text: "身份证", value: "01" },
        { text: "军官证", value: "02" },
        { text: "其他", value: "99" },
      ], // 证件类型
      dwxz: "",
      dwxzColumns: [
        { text: "国家机关", value: "01" },
        { text: "事业单位", value: "02" },
        { text: "国有企业", value: "03" },
        { text: "城镇集体企业", value: "04" },
        { text: "外商投资企业", value: "05" },
        { text: "城镇私营企业", value: "06" },
        { text: "其他城镇企业", value: "07" },
        { text: "民办非企业单位", value: "08" },
        { text: "社会团体", value: "09" },
        { text: "个人自愿", value: "10" },
        { text: "其他", value: "99" },
      ],
      dwlsgx: "",
      dwlsgxColumns: [
        { text: "省", value: "20" },
        { text: "市、地区", value: "40" },
        { text: "县", value: "50" },
        { text: "街道、镇、乡", value: "60" },
        { text: "街道", value: "61" },
        { text: "镇", value: "62" },
        { text: "乡", value: "63" },
        { text: "居民、村民委员", value: "70" },
        { text: "居民委员会", value: "71" },
        { text: "村民委员会", value: "72" },
        { text: "中央", value: "10" },
        { text: "其他", value: "99" },
      ],
      dwjjlx: "",
      dwjjlxColumns: [
        { value: "110", text: "国有全资" },
        { value: "120", text: "集体全资" },
        { value: "130", text: "股份合作" },
        { value: "140", text: "联营" },
        { value: "141", text: "国有联营" },
        { value: "142", text: "集体联营" },
        { value: "143", text: "国有与集体联营" },
        { value: "149", text: "其他联营" },
        { value: "150", text: "有限责任(公司)" },
        { value: "159", text: "其他有限责任(公司)" },
        { value: "160", text: "股份有限(公司)" },
        { value: "170", text: "私有" },
        { value: "171", text: "私有独资" },
        { value: "172", text: "私有合伙" },
        { value: "173", text: "私营有限责任(公司)" },
        { value: "174", text: "私营股份有限(公司)" },
        { value: "175", text: "个体经营" },
        { value: "179", text: "其他私有" },
        { value: "190", text: "其他内资" },
        { value: "200", text: "港、澳、台投资" },
        { value: "210", text: "内地和港、澳或台合资" },
        { value: "220", text: "内地和港、澳或台合作" },
        { value: "230", text: "港、澳或台独资" },
        { value: "240", text: "港、澳或台投资股份有限(公司)" },
        { value: "290", text: "其他港澳台投资" },
        { value: "300", text: "国外投资" },
        { value: "310", text: "中外合资" },
        { value: "320", text: "中外合作" },
        { value: "330", text: "外资" },
        { value: "340", text: "国外投资股份有限(公司)" },
        { value: "390", text: "其他国外投资" },
        { value: "900", text: "其他" },
      ],
      dwsshy: "",
      dwsshyColumns: [
        { value: "8410", text: "医院" },
        { value: "8411", text: "综合医院" },
        { value: "8416", text: "疗养院" },
        { value: "8421", text: "社区卫生服务中心（站）" },
        { value: "8431", text: "疾病预防控制中心" },
        { value: "8433", text: "妇幼保健院（所、站）" },
        { value: "8436", text: "计划生育技术服务活动" },
        { value: "9519", text: "其他群众团体" },
        { value: "9520", text: "社会团体" },
        { value: "9521", text: "专业性团体" },
        { value: "9522", text: "行业性团体" },
        { value: "9529", text: "其他社会团体" },
        { value: "9530", text: "基金会" },
        { value: "9210", text: "国家权力机构" },
        { value: "9220", text: "国家行政机构" },
        { value: "9221", text: "综合事务管理机构" },
        { value: "9222", text: "对外事务管理机构" },
        { value: "9223", text: "公共安全管理机构" },
        { value: "9224", text: "社会事务管理机构" },
        { value: "9225", text: "经济事务管理机构" },
        { value: "9290", text: "其他国家机构" },
        { value: "9226", text: "行政监督检查机构" },
        { value: "9299", text: "其他未列明国家机构" },
        { value: "9230", text: "人民法院和人民检察院" },
        { value: "9310", text: "人民政协" },
        { value: "9231", text: "人民法院" },
        { value: "9400", text: "社会保障" },
        { value: "9232", text: "人民检察院" },
        { value: "9411", text: "基本养老保险" },
        { value: "9412", text: "基本医疗保险" },
        { value: "9291", text: "消防管理机构" },
        { value: "9300", text: "人民政协、民主党派" },
        { value: "9320", text: "民主党派" },
        { value: "9419", text: "其他基本保险" },
        { value: "9511", text: "工会" },
        { value: "9512", text: "妇联" },
        { value: "9513", text: "共青团" },
        { value: "01", text: "农业" },
        { value: "02", text: "林业" },
        { value: "03", text: "畜牧业" },
        { value: "04", text: "渔业" },
        { value: "05", text: "农、林、牧、渔服务业" },
        { value: "06", text: "煤炭开采和洗选业" },
        { value: "0610", text: "烟煤和无烟煤开采洗选" },
        { value: "0620", text: "褐煤开采洗选" },
        { value: "0690", text: "其他煤炭采选" },
        { value: "07", text: "石油和天然气开采业" },
        { value: "08", text: "黑色金属矿采选业" },
        { value: "0810", text: "铁矿采选" },
        { value: "0820", text: "锰矿、铬矿采选" },
        { value: "0890", text: "其他黑色金属矿采选" },
        { value: "9100", text: "中国共产党机关" },
        { value: "9200", text: "国家机构" },
        { value: "9410", text: "基本保险" },
        { value: "9413", text: "失业保险" },
        { value: "9414", text: "工伤保险" },
        { value: "9415", text: "生育保险" },
        { value: "9420", text: "补充保险" },
        { value: "9540", text: "宗教组织" },
        { value: "9541", text: "宗教团体服务" },
        { value: "9542", text: "宗教活动场所服务" },
        { value: "9610", text: "社区居民自治组织" },
        { value: "9620", text: "村民自治组织" },
        { value: "9700", text: "国际组织" },
        { value: "8400", text: "卫生" },
        { value: "9600", text: "基层群众自治组织及其他组织" },
      ],
      bankColumns: [], // 银行列表
      dayColumns: [
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24",
        "25",
        "26",
        "27",
        "28",
        "29",
        "30",
        "31",
      ],
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
          if (userInfo.userType !== "10") {
            this.userInfo = userInfo;
            this.getInfo(this.userInfo);
            this.queryBankList();
          } else {
            Toast.clear();
            Dialog.alert({
              message: "该业务仅支持法人办理，请使用法人账号登录！",
            }).then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close();
            });
          }
        }
      });
    },
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern =
        /[`~!@#$^&*()=|{}':;',\\[\]<>/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.basicInfo.sqtqjexx = this.basicInfo.sqtqjexx.replace(pattern, "");
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
    confirm({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.dwfrdbzjlx = text;
      this.basicInfo.dwfrdbzjlx = value;
      this.showZjlxPicker = false;
    },
    confirm1({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.dwxz = text;
      this.basicInfo.dwxz = value;
      this.showDwxzPicker = false;
    },
    confirm2({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.dwlsgx = text;
      this.basicInfo.dwlsgx = value;
      this.showDwlsgxPicker = false;
    },
    confirm3({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.dwjjlx = text;
      this.basicInfo.dwjjlx = value;
      this.showDwjjlxPicker = false;
    },
    confirm4({ text, value }) {
      console.log(`当前值：${text}, 当前索引：${value}`);
      this.dwsshy = text;
      this.basicInfo.dwsshy = value;
      this.showDwsshyPicker = false;
    },
    onConfirm(value) {
      console.log(changDateFormate(value));
      this.basicInfo.dwslrq = changDateFormate(value);
      this.showPicker = false;
    },
    onConfirm1(value) {
      this.basicInfo.dwfxr = value;
      this.showPicker1 = false;
    },
    onChange() {},

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
      if (this.basicInfo.dwmc === "") {
        Toast("请填写单位名称");
        return;
      }
      if (this.basicInfo.dwdz === "") {
        Toast("请填写单位地址");
        return;
      }
      if (this.basicInfo.dwfrdbxm === "") {
        Toast("请填写单位法人代表姓名");
        return;
      }
      if (this.basicInfo.dwfrdbzjlx === "") {
        Toast("请选择单位法人代表证件类型");
        return;
      }
      if (this.basicInfo.dwfrdbzjhm === "") {
        Toast("请填写单位法人代表证件号码");
        return;
      }
      let sfz =
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!sfz.test(this.basicInfo.dwfrdbzjhm) && this.dwfrdbzjlx == "身份证") {
        Toast("请输入正确的身份证号码");
        return;
      }
      if (this.basicInfo.dwjjlx === "") {
        Toast("请选择单位经济类型");
        return;
      }
      if (this.basicInfo.dwsshy === "") {
        Toast("请选择单位所属行业");
        return;
      }
      if (this.basicInfo.dwyb === "") {
        Toast("请填写单位邮编");
        return;
      }
      if (this.basicInfo.dwdzxx === "") {
        Toast("请填写单位电子邮箱");
        return;
      }
      if (this.basicInfo.dwfxr === "") {
        Toast("请选择单位发薪日");
        return;
      }
      if (this.basicInfo.dwjbbm === "") {
        Toast("请填写单位公积金经办部门");
        return;
      }
      if (this.basicInfo.dwxz === "") {
        Toast("请选择单位性质");
        return;
      }
      if (this.basicInfo.dwkhhhm === "") {
        Toast("请填写单位开户行户名");
        return;
      }
      if (this.basicInfo.dwkhhzh === "") {
        Toast("请填写单位开户行账号");
        return;
      }
      if (this.basicInfo.dwlsgx === "") {
        Toast("请选择单位隶属关系");
        return;
      }
      if (this.basicInfo.dwslrq === "") {
        Toast("请选择单位设立日期");
        return;
      }
      this.$router.push("/form");
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
