<template>
  <div>
    <section class="query-container">
      <el-form
          action=""
          :model="formData"
          ref="formData"
          :rules="formRules"
          label-position="right"
          label-width="80px"
      >
        <el-form-item label="地区" prop="name">
          <el-input
              class="slt"
              placeholder="请输入地区"
              prefix-icon="el-icon-document"
              v-model.trim="formData.name"
              @input="keyUp"
          >
          </el-input>
        </el-form-item>
        <!-- <el-form-item label="站点">
          <el-select
            class="slt"
            v-model="formData.selectName"
            placeholder="请选择站点"
          >
            <el-option
              v-for="item in formData.selectArr"
              :key="item.name"
              :label="item.name"
              :value="item.name"
            >
            </el-option>
          </el-select>
        </el-form-item> -->
        <div class="verification-code">
          <el-form-item label="验证码" prop="code">
            <el-input
                placeholder="请输入验证码"
                prefix-icon="el-icon-edit"
                v-model.trim="formData.code"
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
              @click="queryList('formData')"
          />
          <input type="reset" @click="resetForm('formData')"/>
        </div>
      </el-form>
    </section>
    <router-view :key="key"></router-view>
  </div>
</template>
<script>
// import { createCode } from "@/utils/checkCode";
import {getArea} from "@/utils/getArea";
import {createCode} from "@/utils/checkCode";
import {requestJpaas, requestJpaas1} from "@/api/code";

export default {
  name: "IndexView",
  created() {
    getArea().then((res) => {
      console.log(res.data);
      this.formData.selectArr = this.cityArr.concat(res.data);
      this.formData.selectArr.unshift(this.formData.selectArr[15]);
      this.formData.selectArr.splice(16, 17);
      this.formData.selectArr.splice(1, 2);
    });
    this.$router.push({
      name: "Results",
      params: {res: this.formData},
    });
  },
  async mounted() {
    // 获取验证码
    await this.queryCode()
    this.$nextTick(() => {
      createCode(this.captcha.sandCode);
    })
  },
  data() {
    // const checkCode = (rule, value, callback) => {
    //   if (!validateCode(value.toLowerCase())) {
    //     return callback(new Error("验证码错误"));
    //   }
    //   callback();
    // };
    const checkCode = async (rule, value, callback) => {
      await this.verifyCode()
      if (!this.codeFlag) {
        this.formData.code = ''
        return callback(new Error("验证码错误"));
      }
      callback();
    };
    return {
      key: 0,
      isOpenResult: false,
      formData: {
        name: "",
        code: "",
        selectName: "",
        selectArr: [],
      },
      cityArr: [],
      show: true,
      phone: "",
      code: "",
      formRules: {
        code: [
          {required: true, message: "验证码不能为空", trigger: "blur"},
          {min: 4, max: 4, message: "验证码长度为4个字符", trigger: "blur"},
          {validator: checkCode, trigger: "click"},
        ],
      },
      value: "",
      captcha: {
        uuid: '',
        sandCode: ''
      },
      codeFlag: false
    };
  },
  methods: {
    async queryList(formName) {
      this.$refs[formName].validate(async (valid) => {
        await this.updateCode()
        this.formData.code = ''
        if (valid) {
          this.$router.push({name: "Index"});
          this.$router.push({
            name: "Results",
            params: {res: this.formData},
          });
        } else {
          return false;
        }
      })
    },
    async resetForm(formName) {
      await this.updateCode()
      this.$refs[formName].resetFields();
      this.formData.selectName = ''
      this.formData.name = ''
      this.$router.push({name: "Index"});
      this.$router.push({
        name: "Results",
        params: {res: this.formData, flage: 'resetForm'},
      });
      // this.formData.selectName = null;
      // if (this.$route.name == "Index") {
      //   this.$router.push({
      //     name: "Index",
      //   });
      // } else if (this.$route.name == "NoResults") {
      //   this.$router.push({
      //     name: "Index",
      //   });
      // } else {
      //   this.$router.push({
      //     name: "Results",
      //   });
      // }
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
      // try {
      //   const result = await requestJpaas.getData()
      //   console.log("result",result)
      //   this.captcha.uuid = result.data.data.captcha.uuid
      //   this.captcha.sandCode = result.data.data.captcha.capStr
      // } catch (e) {
      //   this.$message({
      //     type: "error",
      //     message: "验证码获取失败，请重试或联系管理员",
      //   });
      // }
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
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g;
      this.formData.name = this.formData.name.replace(pattern, "");
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