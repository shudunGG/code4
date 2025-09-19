<template>
  <div>
    <section class="query-container" id="query-container_overy">
      <el-form
          action=""
          :model="formData"
          ref="formData"
          :rules="formRules"
          label-position="right"
          label-width="82px"
      >
        <el-form-item label="查询医院" prop="area">
          <el-select style="width:100%" v-model="formData.area" placeholder="请选择医院" @change="hosipatilStyle">
            <el-option label="张掖市中医医院" value="zyy"></el-option>
            <el-option label="河西学院附属张掖人民医院" value="hxfy"></el-option>
            <el-option label="张掖市第二人民医院" value="dermyy"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="name">
          <el-input
              class="slt"
              placeholder="请输入身份证号码"
              prefix-icon="el-icon-document"
              v-model="jmuserId"
              maxlength="50"
              @blur="selectHoipatil"
              disabled="true"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone" v-show="showPhone">
          <el-input
              class="slt"
              placeholder="请输入电话号码"
              prefix-icon="el-icon-document"
              v-model="jmphone"
              maxlength="50"
              @blur="selectHoipatil"
              disabled="true"
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
                @input="inputKeyUp"
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
              :disabled="userType1"
              @click="queryList('formData')"
          />
          <input type="reset" @click="resetForm('formData')"/>
        </div>
      </el-form>
    </section>
  </div>
</template>
<script>
import {createCode} from "@/utils/checkCode";
import {requestJpaas, requestJpaas1} from "@/api/code";
import IndexApi from '@/api/index'
import sm2 from '../utils/sm2'
import getUserInfo from "@/api/userInfo"
import $ from "jquery"
import {IdNumberDesensitization, phone} from '../tools/desensitized'
import {Loading} from "element-ui";

export default {
  name: "IndexView",
  data() {
    const checkCode = async (rule, value, callback) => {
      await this.verifyCode()
      if (!this.codeFlag) {
        this.formData.code = ''
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

    const validateInputCard = (rule, value, callback) => {
      if (!this.checkSpecialKeyCart(value)) {
        callback(new Error("证件号码格式有误"));
      } else {
        callback();
      }
    };

    const validateInputPhone = (rule, value, callback) => {
      if (!this.checkSpecialKeyPhone(value)) {
        callback(new Error("电话号码格式有误"));
      } else {
        callback();
      }
    };


    return {
      jmuserId: '',
      jmphone: '',
      loading: true,
      key: 0,
      isOpenResult: false,
      showPhone: true,
      formData: {
        name: "",
        code: "",
        area: "",
        phone: ''
      },
      userType1: false,
      show: true,
      phone: "",
      code: "",
      formRules: {
        code: [
          {required: true, message: "验证码不能为空", trigger: "blur"},
          {min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur"},
          {validator: validateInputCode, trigger: "blur"},
          {validator: checkCode, trigger: "click"},
        ],
        name: [
          {required: true, message: "证件号码不能为空", trigger: "blur"},
          {validator: validateInputCard, trigger: "blur"},
        ],
        phone: [
          {required: true, message: "电话号码不能为空", trigger: "blur"},
          {validator: validateInputPhone, trigger: "blur"},
        ],
        area: [
          {required: true, message: "查询医院不能为空", trigger: "change"},
        ]
      },
      value: "",
      captcha: {
        uuid: '',
        sandCode: ''
      },
      codeFlag: false,
      token: '',
      authCode: '',
      privateKey: '009b0cddd1a07a54222d0433165e95ec76b08ea5af4967ddecd3f5bac7fb4b0796',
      publicKey: '04753c8780ad3c343218f9ba4368669de096601ce3259124fd23f9496f5bea3ff398c7ae83e07e1e1250883dbf3c9c01af75415f06b267414f93ef318ffb2523f2',
      userId: '',
      MobilePhone: ''
    };
  },
  created() {
    getUserInfo().then((res) => {
      console.log('--用户信息--', res)
      if (res.code === "200") {
        const userInfo = res.params
        if (userInfo.userType == "1") {
          sessionStorage.setItem('userInfo', sm2.encrypt(JSON.stringify(userInfo)))
          this.userId = res.params.userId
          this.MobilePhone  = res.params.phone

          this.formData.name = res.params.userId
          this.formData.phone = res.params.phone

          this.jmuserId = IdNumberDesensitization(res.params.userId)
          this.jmphone = phone(res.params.phone)
          this.getToken()
        } else {
          this.$message.warning("该业务仅限个人用户办理");
          this.userType1 = true;
        }
      } else {
        location.href = "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" + encodeURIComponent(location.href);
      }
    })
  },
  async mounted() {
    // 获取验证码
    await this.queryCode()
    this.$nextTick(() => {
      createCode(this.captcha.sandCode);
    })

  },
  methods: {
    hosipatilStyle() {
      console.log('1')
      if(this.formData.area) {
        if(this.formData.area === 'zyy') {
          this.showPhone = true
          this.formData.name = this.userId
          this.formData.phone = this.MobilePhone
          this.$forceUpdate()
        } else if(this.formData.area === 'hxfy') {
          this.showPhone = false
          this.formData.name = this.userId
          this.formData.phone = this.MobilePhone
          this.$forceUpdate()
        } else if(this.formData.area === 'dermyy') {
          this.showPhone = false
          this.formData.name = this.userId
          this.formData.phone = this.MobilePhone
          this.$forceUpdate()
        }
      }
    },
    // 判断选择
    selectHoipatil() {
      if(!this.formData.area) {
        this.$message({
          message: '请先选择医院'
        })
        this.formData.name = ''
        this.formData.phone = ''
        return
      }
    },
    // 验证码特殊字符校验
    checkSpecialKeyCode(str) {
      let specialKey = "/[`-~!@#$^&*=|{}':;',\\\\\\[\\]\\.<>\\/?~！@#￥……&%*（）——|{}【】'；：\"\"'。，、？\\]/"
      for (let i = 0; i < str.length; i++) {
        if (specialKey.indexOf(str.substr(i, 1)) != -1) {
          return false;
        }
      }
      return true;
    },
    checkSpecialKeyCart(str) {
      let specialKey = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
      console.log('---', specialKey.test(str))
      if(specialKey.test(str)) {
        return true
      } else {
        return false
      }
    },
    checkSpecialKeyPhone(str)  {
      let specialKey = /^1[3456789]\d{9}$/
      console.log('---', specialKey.test(str))
      if(specialKey.test(str)) {
        return true
      } else {
        return false
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
      this.token = tokenData.token;
      sessionStorage.setItem('token', this.token)
      this.authCode = tokenData.authCode;
      sessionStorage.setItem('authCode', this.authCode)
    },

    async queryList(formName) {
      this.$refs[formName].validate(async (valid) => {

        await this.updateCode()
        this.formData.code = ''
        if (valid) {
          console.log('-formData.area-111', this.formData.area)
          if(this.formData.area === 'dermyy') {
            await this.queryDeHosipatil()
          } else if(this.formData.area === 'zyy') {
            await this.queryZyyHosipatil()
          } else if (this.formData.area === 'hxfy') {
            console.log('---asd-as---')
            await this.queryHfyyHosipatil()
          }
        } else {
          return false;
        }
      })
    },

    // 中医院
    async queryZyyHosipatil() {
      let loadingInstance = Loading.service({
        target: document.getElementById('query-container_overy')
      })
      const params = {
        // token: this.token,
        // authCode: this.authCode
        // token: '622224198910196512',
        // authCode: '19993688858'
        token: sm2.encrypt(this.formData.name,this.publicKey),
        authCode: sm2.encrypt(this.formData.phone,this.publicKey)
      }
      try {
        const result = await IndexApi.queryList('zyyy', 'reportOrderList', params, {})
        const resultData = JSON.parse(sm2.decrypt(result.data, this.privateKey))
        if (result.code === '200' && result.success && resultData.length >= 1) {
          loadingInstance.close()
          this.$store.commit('zyyResultData',resultData)
          this.$store.commit('formateData', this.formData)
          this.$store.commit('token', this.token)
          this.$store.commit('authCode', this.authCode)
          this.$router.push('/zyy_result')
        } else {
          loadingInstance.close()
          // this.$message({
          //   message: '暂无报告，请稍后再试',
          //   duration: 2000
          // })
          this.$router.push('/no-results')
        }
      } catch (e) {
        console.log('--1--',e)
        loadingInstance.close()
        this.$message({
          message: '网络异常，请稍后再试！',
          duration: 2000
        })
      }
    },
    // 附院
    async queryHfyyHosipatil() {
      let loadingInstance = Loading.service({
        target: document.getElementById('query-container_overy')
      })
      const params = {
        // token: this.token,
        // authCode: this.authCode,

        // token: '123',
        // authCode: '123',

        token: sm2.encrypt(this.formData.name,this.publicKey),
        isTest: sm2.encrypt(this.formData.name,this.publicKey),
        // authCode: sm2.encrypt(this.formData.phone,this.publicKey)
        // isTest: '622323199202247220'
      }
      try {
        const result = await IndexApi.queryList('zyyy', 'healthExamReport', params, {})
        // const resultData = JSON.parse(sm2.decrypt(result.data, this.privateKey))
        // const resultData = JSON.parse(sm2.decrypt(result?.data, this.privateKey))
        console.log('--', result)
        if (result.code === '200' && result.success && result.data) {
          loadingInstance.close()
          const resultData = JSON.parse(sm2.decrypt(result.data, this.privateKey))
          this.$store.commit('fsyyResult', resultData)
          this.$router.push('/fsyy_result')
        } else {
          loadingInstance.close()
          this.$router.push('/no-results')
          // this.$message({
          //   message: '暂无报告，请稍后再试',
          //   duration: 2000
          // })
        }
      } catch (e) {
        console.log('---3',e)
        loadingInstance.close()
        this.$message({
          message: '网络异常，请稍后再试',
          duration: 2000
        })
      }
    },
    // 张掖市第二人民医院
    async queryDeHosipatil() {
      let loadingInstance = Loading.service({
        target: document.getElementById('query-container_overy')
      })
      const param = {
        // sfzh: '62220119881112632x'
        token: sm2.decrypt(this.token, '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'),
        authCode: sm2.decrypt(this.authCode,'0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7')
      }
      try {
        const result = await IndexApi.queryList('zyyy','getBodyReport', param, {})
        if (result.code === '200' && result.success && result.data.data.tjrylb) {
          loadingInstance.close()
          this.$store.commit('drResultData',result.data.data)
          this.$router.push('/drResult')
        } else {
          loadingInstance.close()
          this.$router.push('/no-results')
        }
      } catch (e) {
        console.log('--2--',e)
        loadingInstance.close()
        this.$message({
          message: '网络异常，请稍后再试！',
          duration: 2000
        })
      }
    },

    async resetForm(formName) {
      await this.updateCode()
      this.$refs[formName].resetFields();
      this.formData.selectName = ''
      this.formData.name = ''
      this.formData.phone = ''
      this.$router.push({name: "Index"});
    },
    // 获取验证码
    async queryCode() {
      const result = await requestJpaas.getData()
      if (result.code === '200') {
        this.captcha.uuid = result.data.captcha.uuid
        this.captcha.sandCode = result.data.captcha.capStr
      } else {
        this.$message({
          type: "error",
          message: "验证码获取失败，请重试或联系管理员",
        });
      }
    },
    // 验证码获取更新
    async updateCode() {
      this.captcha.uuid = ''
      this.captcha.sandCode = ''
      await this.queryCode()
      createCode(this.captcha.sandCode);
    },
    // 验证码校验
    async verifyCode() {
      try {
        let formData = new FormData()
        formData.append('uuid', this.captcha.uuid)
        formData.append('sandCode', this.formData.code)
        this.captcha.sandCode = this.formData.code
        const result = await requestJpaas1.getData(this.captcha)
        console.log('asdasd', result)
        if (result.code == '200') {
          this.codeFlag = true
        } else {
          this.codeFlag = false
        }
      } catch (e) {
        this.$message({
          type: "error",
          message: "验证码校验失败，请重试或联系管理员",
        });
      }
    },
    keyUp(name) {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.formData[name] = this.formData[name].replace(pattern, "");
    },
    inputKeyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.formData.code = this.formData.code.replace(pattern, "");
    }
  },
};
</script>

<style scoped>
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