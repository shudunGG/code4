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
          <el-form-item label="单位名称" prop="dwmc" style="width: 48%">
            <el-input
              placeholder="请填写单位名称"
              clearable
              v-model.trim="basicInfo.dwmc"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="单位地址" prop="dwdz" style="width: 48%">
            <el-input
              placeholder="请填写单位地址"
              clearable
              v-model.trim="basicInfo.dwdz"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item
            label="单位法人代表姓名"
            prop="dwfrdbxm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写单位法人代表姓名"
              clearable
              v-model.trim="basicInfo.dwfrdbxm"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="单位法人代表证件类型"
            prop="dwfrdbzjlx"
            style="width: 48%"
          >
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwfrdbzjlx"
              placeholder="请选择单位法人代表证件类型"
            >
              <el-option
                v-for="item in zjlxOptions"
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
            label="单位法人代表证件号码"
            prop="dwfrdbzjhm"
            style="width: 48%"
          >
            <el-input
              placeholder="请填写单位法人代表证件号码"
              clearable
              v-model.trim="basicInfo.dwfrdbzjhm"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="单位经济类型" prop="dwjjlx" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwjjlx"
              placeholder="请选择单位经济类型"
            >
              <el-option
                v-for="item in dwjjlxOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="单位所属行业" prop="dwsshy" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwsshy"
              placeholder="请选择单位所属行业"
            >
              <el-option
                v-for="item in dwsshyOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单位邮编" prop="dwyb" style="width: 48%">
            <el-input
              placeholder="请填写单位邮编"
              clearable
              v-model.trim="basicInfo.dwyb"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="单位电子邮箱" prop="dwdzxx" style="width: 48%">
            <el-input
              placeholder=" 请填写单位电子邮箱"
              clearable
              v-model.trim="basicInfo.dwdzxx"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="单位发薪日" prop="dwfxr" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwfxr"
              placeholder="请选择单位发薪日"
            >
              <el-option
                v-for="item in dwfxrOptions"
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
            label="单位公积金经办部门"
            prop="dwjbbm"
            style="width: 48%"
          >
            <el-input
              placeholder=" 请填写单位公积金经办部门"
              clearable
              v-model.trim="basicInfo.dwjbbm"
            >
            </el-input>
          </el-form-item>
          <el-form-item label="单位性质" prop="dwxz" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwxz"
              placeholder="请选择单位性质"
            >
              <el-option
                v-for="item in dwxzOptions"
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
            label="单位开户行户名"
            prop="dwkhhhm"
            style="width: 48%"
          >
            <el-input
              placeholder=" 请填写单位开户行户名"
              clearable
              v-model.trim="basicInfo.dwkhhhm"
            >
            </el-input>
          </el-form-item>
          <el-form-item
            label="单位开户行账号"
            prop="dwkhhzh"
            style="width: 48%"
          >
            <el-input
              placeholder=" 请填写单位开户行账号"
              clearable
              v-model.trim="basicInfo.dwkhhzh"
            >
            </el-input>
          </el-form-item>
        </div>
        <div class="flex">
          <el-form-item label="单位隶属关系" prop="dwlsgx" style="width: 48%">
            <el-select
              style="width: 100%"
              v-model="basicInfo.dwlsgx"
              placeholder="请选择单位隶属关系"
            >
              <el-option
                v-for="item in dwlsgxOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单位设立日期" prop="dwslrq" style="width: 48%">
            <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="basicInfo.dwslrq"
              type="date"
              placeholder="请选择单位设立日期"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </div>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="下一步"
            @click="queryList('basicInfo')"
            :disabled="buttonDisabled"
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
    this.getLoginUser();
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
    // 校验身份证号
    let validateInputhz = (rule, value, callback) => {
      let sfz =
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (!sfz.test(value) && this.basicInfo.dwfrdbzjlx == "01") {
        callback(new Error("请输入正确的身份证号"));
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
        grzhye: "6300",
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
      userInfo: {}, //用户信息
      token: "",
      authCode: "",
      formRules: {
        dwmc: [
          {
            required: true,
            message: "单位名称不能为空",
            trigger: "blur",
          },
        ],
        dwdz: [
          {
            required: true,
            message: "单位地址不能为空",
            trigger: "blur",
          },
        ],
        dwfrdbxm: [
          {
            required: true,
            message: "单位法人代表姓名不能为空",
            trigger: "blur",
          },
        ],
        dwfrdbzjlx: [
          {
            required: true,
            message: "单位法人代表证件类型不能为空",
            trigger: "change",
          },
        ],
        dwfrdbzjhm: [
          {
            required: true,
            message: "单位法人代表证件号码不能为空",
            trigger: "blur",
          },
          { validator: validateInputhz, trigger: "blur" },
        ],
        dwjjlx: [
          {
            required: true,
            message: "单位经济类型不能为空",
            trigger: "blur",
          },
        ],
        dwsshy: [
          {
            required: true,
            message: "单位所属行业不能为空",
            trigger: "blur",
          },
        ],
        dwyb: [
          {
            required: true,
            message: "单位邮编不能为空",
            trigger: "blur",
          },
        ],
        dwdzxx: [
          {
            required: true,
            message: "单位电子邮箱不能为空",
            trigger: "blur",
          },
        ],
        dwfxr: [
          {
            required: true,
            message: "单位发薪日不能为空",
            trigger: "blur",
          },
        ],
        dwjbbm: [
          {
            required: true,
            message: "单位公积金经办部门不能为空",
            trigger: "blur",
          },
        ],
        dwxz: [
          {
            required: true,
            message: "单位性质不能为空",
            trigger: "blur",
          },
        ],
        dwkhhhm: [
          {
            required: true,
            message: "单位开户行户名不能为空",
            trigger: "blur",
          },
        ],
        dwkhhzh: [
          {
            required: true,
            message: "单位开户行账号不能为空",
            trigger: "blur",
          },
        ],
        dwlsgx: [
          {
            required: true,
            message: "单位隶属关系不能为空",
            trigger: "blur",
          },
        ],
        dwslrq: [
          {
            required: true,
            message: "单位设立日期不能为空",
            trigger: "blur",
          },
        ],
      },
      zjlxOptions: [
        { label: "护照", value: "03" },
        { label: "外国人永久居留证", value: "04" },
        { label: "身份证", value: "01" },
        { label: "军官证", value: "02" },
        { label: "其他", value: "99" },
      ], // 证件类型
      dwjjlxOptions: [
        { value: "110", label: "国有全资" },
        { value: "120", label: "集体全资" },
        { value: "130", label: "股份合作" },
        { value: "140", label: "联营" },
        { value: "141", label: "国有联营" },
        { value: "142", label: "集体联营" },
        { value: "143", label: "国有与集体联营" },
        { value: "149", label: "其他联营" },
        { value: "150", label: "有限责任(公司)" },
        { value: "159", label: "其他有限责任(公司)" },
        { value: "160", label: "股份有限(公司)" },
        { value: "170", label: "私有" },
        { value: "171", label: "私有独资" },
        { value: "172", label: "私有合伙" },
        { value: "173", label: "私营有限责任(公司)" },
        { value: "174", label: "私营股份有限(公司)" },
        { value: "175", label: "个体经营" },
        { value: "179", label: "其他私有" },
        { value: "190", label: "其他内资" },
        { value: "200", label: "港、澳、台投资" },
        { value: "210", label: "内地和港、澳或台合资" },
        { value: "220", label: "内地和港、澳或台合作" },
        { value: "230", label: "港、澳或台独资" },
        { value: "240", label: "港、澳或台投资股份有限(公司)" },
        { value: "290", label: "其他港澳台投资" },
        { value: "300", label: "国外投资" },
        { value: "310", label: "中外合资" },
        { value: "320", label: "中外合作" },
        { value: "330", label: "外资" },
        { value: "340", label: "国外投资股份有限(公司)" },
        { value: "390", label: "其他国外投资" },
        { value: "900", label: "其他" },
      ],
      dwsshyOptions: [
        { value: "8410", label: "医院" },
        { value: "8411", label: "综合医院" },
        { value: "8416", label: "疗养院" },
        { value: "8421", label: "社区卫生服务中心（站）" },
        { value: "8431", label: "疾病预防控制中心" },
        { value: "8433", label: "妇幼保健院（所、站）" },
        { value: "8436", label: "计划生育技术服务活动" },
        { value: "9519", label: "其他群众团体" },
        { value: "9520", label: "社会团体" },
        { value: "9521", label: "专业性团体" },
        { value: "9522", label: "行业性团体" },
        { value: "9529", label: "其他社会团体" },
        { value: "9530", label: "基金会" },
        { value: "9210", label: "国家权力机构" },
        { value: "9220", label: "国家行政机构" },
        { value: "9221", label: "综合事务管理机构" },
        { value: "9222", label: "对外事务管理机构" },
        { value: "9223", label: "公共安全管理机构" },
        { value: "9224", label: "社会事务管理机构" },
        { value: "9225", label: "经济事务管理机构" },
        { value: "9290", label: "其他国家机构" },
        { value: "9226", label: "行政监督检查机构" },
        { value: "9299", label: "其他未列明国家机构" },
        { value: "9230", label: "人民法院和人民检察院" },
        { value: "9310", label: "人民政协" },
        { value: "9231", label: "人民法院" },
        { value: "9400", label: "社会保障" },
        { value: "9232", label: "人民检察院" },
        { value: "9411", label: "基本养老保险" },
        { value: "9412", label: "基本医疗保险" },
        { value: "9291", label: "消防管理机构" },
        { value: "9300", label: "人民政协、民主党派" },
        { value: "9320", label: "民主党派" },
        { value: "9419", label: "其他基本保险" },
        { value: "9511", label: "工会" },
        { value: "9512", label: "妇联" },
        { value: "9513", label: "共青团" },
        { value: "01", label: "农业" },
        { value: "02", label: "林业" },
        { value: "03", label: "畜牧业" },
        { value: "04", label: "渔业" },
        { value: "05", label: "农、林、牧、渔服务业" },
        { value: "06", label: "煤炭开采和洗选业" },
        { value: "0610", label: "烟煤和无烟煤开采洗选" },
        { value: "0620", label: "褐煤开采洗选" },
        { value: "0690", label: "其他煤炭采选" },
        { value: "07", label: "石油和天然气开采业" },
        { value: "08", label: "黑色金属矿采选业" },
        { value: "0810", label: "铁矿采选" },
        { value: "0820", label: "锰矿、铬矿采选" },
        { value: "0890", label: "其他黑色金属矿采选" },
        { value: "9100", label: "中国共产党机关" },
        { value: "9200", label: "国家机构" },
        { value: "9410", label: "基本保险" },
        { value: "9413", label: "失业保险" },
        { value: "9414", label: "工伤保险" },
        { value: "9415", label: "生育保险" },
        { value: "9420", label: "补充保险" },
        { value: "9540", label: "宗教组织" },
        { value: "9541", label: "宗教团体服务" },
        { value: "9542", label: "宗教活动场所服务" },
        { value: "9610", label: "社区居民自治组织" },
        { value: "9620", label: "村民自治组织" },
        { value: "9700", label: "国际组织" },
        { value: "8400", label: "卫生" },
        { value: "9600", label: "基层群众自治组织及其他组织" },
      ],
      dwfxrOptions: [
        { value: "01", label: "01" },
        { value: "02", label: "02" },
        { value: "03", label: "03" },
        { value: "04", label: "04" },
        { value: "05", label: "05" },
        { value: "06", label: "06" },
        { value: "07", label: "07" },
        { value: "08", label: "08" },
        { value: "09", label: "09" },
        { value: "10", label: "10" },
        { value: "11", label: "11" },
        { value: "12", label: "12" },
        { value: "13", label: "13" },
        { value: "14", label: "14" },
        { value: "15", label: "15" },
        { value: "16", label: "16" },
        { value: "17", label: "17" },
        { value: "18", label: "18" },
        { value: "19", label: "19" },
        { value: "20", label: "20" },
        { value: "21", label: "21" },
        { value: "22", label: "22" },
        { value: "23", label: "23" },
        { value: "24", label: "24" },
        { value: "25", label: "25" },
        { value: "26", label: "26" },
        { value: "27", label: "27" },
        { value: "28", label: "28" },
        { value: "29", label: "29" },
        { value: "30", label: "30" },
        { value: "31", label: "31" },
      ],
      dwxzOptions: [
        { label: "国家机关", value: "01" },
        { label: "事业单位", value: "02" },
        { label: "国有企业", value: "03" },
        { label: "城镇集体企业", value: "04" },
        { label: "外商投资企业", value: "05" },
        { label: "城镇私营企业", value: "06" },
        { label: "其他城镇企业", value: "07" },
        { label: "民办非企业单位", value: "08" },
        { label: "社会团体", value: "09" },
        { label: "个人自愿", value: "10" },
        { label: "其他", value: "99" },
      ],
      dwlsgxOptions: [
        { label: "省", value: "20" },
        { label: "市、地区", value: "40" },
        { label: "县", value: "50" },
        { label: "街道、镇、乡", value: "60" },
        { label: "街道", value: "61" },
        { label: "镇", value: "62" },
        { label: "乡", value: "63" },
        { label: "居民、村民委员", value: "70" },
        { label: "居民委员会", value: "71" },
        { label: "村民委员会", value: "72" },
        { label: "中央", value: "10" },
        { label: "其他", value: "99" },
      ],
      bankColumns: [],
      buttonDisabled:false
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
          if (userInfo.userType == "1") {
            this.$message.info("该业务仅支持法人用户办理，请切换账号后重试");
            this.buttonDisabled = true;
          } else {
            this.getInfo(this.userInfo);
            this.getBank();
            this.buttonDisabled = false;
          }
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
          res = JSON.parse(tools.decryptSM2(res.data.data)).result;
          if (res.code == "00" && res.data) {
            console.log(res, "解密公积金信息");
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.basicInfo.grzh = res.data.grzh;
            // this.basicInfo.zjhm = res.data.zjhm;
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
          this.$router.push("/extractInfo");
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
