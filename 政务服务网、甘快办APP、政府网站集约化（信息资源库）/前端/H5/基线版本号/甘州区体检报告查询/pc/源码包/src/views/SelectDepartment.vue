<template>
  <div class="bottom-content">
    <div class="content_top">
      <div class="content_cx">
        <div></div>
        体检报告查询
      </div>
      <div class="content_bt">* 为必填项</div>
    </div>
    <div class="content_from">
      <el-form :model="basicInfo" :rules="rules" ref="basicInfo" label-width="100px" label-position="top" class="demo-basicInfo">
        <el-form-item label="就诊人" prop="name">
          <el-input v-model="basicInfo.name" placeholder="请输入就诊人名称" disabled></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="card">
          <el-input v-model="basicInfo.card" placeholder="请输入身份证号" disabled></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="basicInfo.phone" placeholder="请输入手机号" disabled></el-input>
        </el-form-item>
        <!-- <el-form-item label="查询时段" prop="timeTypeval">
          <el-select @change="change" v-model="basicInfo.timeTypeval" placeholder="请选择查询时段">
            <el-option v-for="item in columns" :key="item.value" :label="item.time" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起始时间段" prop="timeType" v-if="basicInfo.timeTypeval==3">
          <el-date-picker v-model="basicInfo.timeType" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item> -->
        <!-- <el-form-item label="报告类型" prop="typeval">
          <el-select @change="changes" v-model="basicInfo.typeval" placeholder="请选择报告类型">
            <el-option v-for="item in column" :key="item.value" :label="item.type" :value="item.value"></el-option>
          </el-select>
        </el-form-item> -->
        <div class="verification-code">
          <el-form-item label="验证码" prop="code">
            <el-input placeholder="请输入验证码" prefix-icon="el-icon-edit" v-model="basicInfo.code" class="yanzhengma" maxlength="4" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/ig,'')" @keyup.enter.native="queryList('formData')">
            </el-input>
            <div class="code" style="cursor: pointer;" @click="updateCode">
              <canvas id="canvas" width="100" height="38"></canvas>
            </div>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary" @click="submitForm('basicInfo')">查询</el-button>
          <el-button @click="resetForm" class="bgc">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { createCode } from "@/utils/checkCode";
import { requestJpaas, requestJpaas1 } from "@/api/VerificationCode";
export default {
  data () {
    let checkCode = async (rule, value, callback) => {
      await this.verifyCode();
      if (!this.codeFlag) {
        this.basicInfo.code = "";
        return callback(new Error("验证码错误"));
      }
      callback();
    };
    // 校验验证码
    let validateInputCode = (rule, value, callback) => {
      if (!this.checkSpecialKeyCode(value)) {
        callback(new Error("不能含有特殊字符"));
      } else {
        callback();
      }
    };
    return {
      columns: [
        { time: '近三个月', value: 0 },
        { time: '近半年', value: 1 },
        { time: '最近一年', value: 2 },
        // { time: '其他时段', value: 3 }
      ],

      column: [
        { type: '门诊报告', value: 0 },
        { type: '住院报告', value: 1 }
      ],
      userInfo: {}, //用户信息
      basicInfo: {
        name: this.$store.state.userInfo.name,
        card: this.$store.state.userInfo.card,
        phone: this.$store.state.userInfo.phone,
        timeTypeval: '',
        timeType: '',
        type: '',
        typeval: '',
        code: ''
      },
      captcha: {
        uuid: "",
        sandCode: "",
      },
      rules: {
        name: [{ required: true, message: '请输入就诊人名称', trigger: 'blur' }],
        card: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        timeTypeval: [{ required: true, message: '请选择查询时段', trigger: 'blur' }],
        timeType: [{ required: true, message: '请选择起始时间段', trigger: 'blur' }],
        typeval: [{ required: true, message: '请选择报告类型', trigger: 'blur' }],
        code: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
          { min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur" },
          { validator: validateInputCode, trigger: "blur" },
          { validator: checkCode, trigger: "click" },
        ],
      }
    }
  },
  async mounted () {
    setTimeout(() => {
      this.basicInfo.name = this.$store.state.userInfo.name
      this.basicInfo.card = this.$store.state.userInfo.card
      this.basicInfo.phone = this.$store.state.userInfo.phone
    }, 500);
    await this.getVerificationCode() //获取验证码
    this.$nextTick(() => {
      createCode(this.captcha.sandCode);
    });
  },
  methods: {
    // 查询时段
    change (val) {
      console.log(val)
      this.basicInfo.timeType = val
    },
    changes (val) {
      console.log(val)
      this.basicInfo.type = val
    },
    // 重置
    resetForm () {
      this.basicInfo.timeType = ''
      this.basicInfo.type = ''
      this.basicInfo.timeTypeval = ''
      this.basicInfo.typeval = ''
      this.updateCode();
      this.basicInfo.code = '';
    },
    // 获取验证码
    async getVerificationCode () {
      const result = await requestJpaas.getData();
      if (result.code === "200") {
        this.captcha.uuid = result.data.captcha.uuid;
        this.captcha.sandCode = result.data.captcha.capStr;
      } else {
        this.$message.error("验证码获取失败，请重试或联系管理员")
      }

    },
    //验证码校验
    async verifyCode () {
      try {
        let formData = new FormData();
        formData.append("uuid", this.captcha.uuid);
        formData.append("sandCode", this.basicInfo.code);
        this.captcha.sandCode = this.basicInfo.code;
        const result = await requestJpaas1.getData(this.captcha);
        if (result.code == "200") {
          this.codeFlag = true;
        } else {
          this.codeFlag = false;
        }
      } catch (e) {
        this.$message.error("验证码校验失败，请重试或联系管理员")
      }
    },
    // 验证码获取更新
    async updateCode () {
      this.captcha.uuid = "";
      this.captcha.sandCode = "";
      await this.getVerificationCode();
      createCode(this.captcha.sandCode);
    },
    // 验证码特殊字符校验
    checkSpecialKeyCode (str) {
      let specialKey =
        "/[`-~!@#$^&*=|{}':;',\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/";
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
      }
      return true;
    },
    submitForm (formName) {
      this.$refs[formName].validate(async valid => {
        await this.updateCode();
        this.basicInfo.code = "";
        if (valid) {
          console.log(this.basicInfo, 'basicInfo')
          this.$store.commit('basicInfo', this.basicInfo)
          this.$router.push('/doctor')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
.bottom-content {
  background: #fff;
  box-shadow: 0 3px 18px #0000000d;
  margin-bottom: 40px;
  padding: 30px;
  font-size: 16px;
  .content_top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #eee;
    padding-bottom: 17px;
    .content_cx {
      display: flex;
      align-items: center;
      font-weight: 700;
      color: #333;
      div {
        width: 3px;
        height: 15px;
        margin-right: 10px;
        border-radius: 1.5px;
        background: #1e82d8;
      }
    }
    .content_bt {
      font-weight: 700;
      font-size: 16px;
      color: #b90005;
    }
  }
  .content_from {
    padding: 30px 320px;
  }
}
/deep/ .el-button {
  width: 180px;
  height: 50px;
  border-radius: 0;
  margin-top: 8px;
}
/deep/ .el-form-item {
  margin-bottom: 20px;
}
/deep/ .el-form-item__content {
  display: flex;
  justify-content: center;
}
/deep/ .el-select {
  width: 100%;
}
/deep/ .el-input__inner {
  width: 100%;
}
.bgc {
  background: #d5e9fb;
  color: #3f87f3;
}
@media screen and (max-width: 750px) {
  .content_from {
    padding: 0 !important;
  }
}
</style>
