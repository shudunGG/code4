<template>
  <div>
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>汽车票查询</h1>
      </div>
    </div>
    <div class="main box1200">
      <div class="content-container" v-loading="loading">
        <section class="query-container">
          <el-form action="" :model="formData" ref="formData" label-position="right" label-width="120px" :rules="rules">
            <el-form-item label="出发站点" prop="fromCityValue">
<!--              <el-input maxlength="20" class="slt" placeholder="请输入出发站点" clearable v-model.trim="formData.depart">-->
<!--              </el-input>-->
              <el-select v-model="formData.fromCityValue" placeholder="出发站点" style="width: 100%" @change="fromacityChange(formData.fromCityValue)">
                <el-option
                    v-for="item in fromacity"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="出发时间" prop="departDate">
              <el-date-picker value-format="yyyy-MM-dd" :editable="false" v-model.trim="formData.departDate"
                :picker-options="pickerOptions" type="date" placeholder="请选择出发日期" @change="dataChange"  style="width: 100%">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="到达站点" prop="destination">
<!--              <el-input maxlength="20" class="slt" placeholder="请输入到达站点" clearable v-model.trim="formData.destination">-->
<!--              </el-input>-->
<!--              <el-select v-model="toCityValue" placeholder="到达站点"  style="width: 100%" @change="selectToCityValue(toCityValue)">-->
<!--                <el-option-->
<!--                    v-for="item in tocolumns"-->
<!--                    :key="item.value"-->
<!--                    :label="item.label"-->
<!--                    :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
              <el-autocomplete style="width: 100%"
                  v-model="state"
                  :fetch-suggestions="querySearchAsync"
                  placeholder="请输入内容"
                  @select="handleSelect"
              ></el-autocomplete>
            </el-form-item>
            <div class="verification-code">
              <el-form-item label="验证码" prop="code">
                <el-input class="ipt" placeholder="请输入" maxlength="4" v-model.trim="formData.code"
                  @keyup.enter.native="submitForm('formData')">
                </el-input>
              </el-form-item>
              <div class="code" @click="updateCode">
                <canvas id="canvas" width="100" height="38"></canvas>
              </div>
            </div>
            <div class="form-btn">
              <input type="button" class="submit" value="查询" @click="submitForm('formData')" />
              <input type="button" value="重置" @click="resetForm('formData')" class="reset" />
            </div>
          </el-form>
        </section>
        <router-view></router-view>
      </div>
    </div>
    <div class="footer">
      <div class="footer_bottom">本服务由甘肃省提供</div>
    </div>
  </div>
</template>
<script>
// import { applicationAPI } from '@/api/index'
import serverApi from '@/api/index'
import { createCode, validateCode } from '@/utils/checkCode'
// import {fromPairs} from "lodash/array";
export default {
  name: 'IndexView',
  data() {
    const checkCode = (rule, value, callback) => {
      if (!validateCode(value.toLowerCase())) {
        this.formData.code = ''
        return callback(new Error('验证码错误'))
      }
      callback()
    }
    // let checkCodefromCity = (rule, value, callback) => {
      // if (!this.fromCity(value)) {
      //   callback(new Error("不能含有特殊字符"));
      // } else {
      //   callback();
      // }
    // };
    // let checkCodedata = (rule, value, callback) => {
      // if (!this.data(value)) {
      //   callback(new Error("不能含有特殊字符"));
      // } else {
      //   callback();
      // }
    // };
    return {
      restaurants: [],
      state: '',
      timeout:  null,
      formData: {
        name: '',
        card: '',
        selectArr: [],
        region: '',
        code: '',
        depart: '',
        destination: '',
        departDate: '',
        fromCity: '',
        data: '',
        fromCityValue: ''
      },
      pickerOptions: {
        disabledDate: time => {
          // 禁用今天之前的日期选择
          return (
            time.getTime() < new Date().getTime() - 24 * 60 * 60 * 1000 ||
            time.getTime() > new Date().getTime() + 24 * 60 * 60 * 1000 * 7
          )
        }
      },
      userInfo: {},
      loading: false,
      fromacity: [
        { label: '兰州市', value: '兰州市' },
        { label: '嘉峪关市',value: '嘉峪关市' },
        { label: '金昌市',value: '金昌市' },
        { label: '酒泉市',value: '酒泉市' },
        { label: '张掖市',value: '张掖市' },
        { label: '武威市',value: '武威市' },
        { label: '白银市',value: '白银市' },
        { label: '天水市',value: '天水市' },
        { label: '平凉市',value: '平凉市' },
        { label: '庆阳市',value: '庆阳市' },
        { label: '定西市',value: '定西市' },
        { label: '陇南市',value: '陇南市' },
        { label: '甘南藏族自治州',value: '甘南藏族自治州' },
        { label: '临夏回族自治州',value: '临夏回族自治州' },
        { label: '兰州新区', value: '兰州新区' },
      ],
      fromCityValue: '',
      fromCityId: '',
      tocolumns: [],
      toCityValue: '',
      data1: '',
      rules: {
        code: [
          { required: true, message: '验证码不能为空', trigger: 'blur' },
          { min: 4, max: 4, message: '验证码长度为4个字符', trigger: 'blur' },
          { validator: checkCode, trigger: 'blur' }
        ],
        fromCityValue: [
          { required: true, message: '请选择出发站点', trigger: 'blur' },
        ],
        departDate: [
          { required: true, message: '请选择出发时间', trigger: 'blur' },
        ]
      }
    }
  },
  mounted() {
    createCode()
  },

  methods: {
    dataChange(val) {
      console.log(val)
      this.data1 = val
    },
    updateCode() {
      createCode()
    },
    submitForm(formName) {
      this.$router.push('/')
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          const params = {
            uid: '6202000027',
            departcityid: this.fromCityId, // 出发城市id
           // reachstationname: this.toCityValue, // 目的站名称、
           reachstationname: this.state, // 目的站名称、
            departdate: this.formData.departDate, // 出发日期
            disable: '1',//是否查询不可售（0：只查询可售班次 1：查询所有班次 默认为0）
            start: '1',
            count: '5',
          }
          console.log('---上传数据 ----', params)
          console.log('params', params)
          serverApi.fromCity('findSailingsgs', 'gssztravelinquiry', params).then(res => {
              this.loading = false
            console.log('---asd---asd--asd---asd-', JSON.parse(res.data))
              if (res.code == 200 && res.success && Number(JSON.parse(res.data).totalrows)-1 > 0) {
                this.$router.push({
                  name: 'Results',
                  params: {
                    data: JSON.stringify(res.data),
                    departDate: this.formData.departDate,
                    fromCityId: this.fromCityId,
                    toCityValue: this.toCityValue
                  }
                })
                // if () {
                //   // this.$router.push({
                //   //   name: 'Results',
                //   //   params: {
                //   //     data: JSON.stringify(res.data),
                //   //     departDate: this.formData.departDate,
                //   //     fromCityId: this.fromCityId,
                //   //     toCityValue: this.toCityValue
                //   //   }
                //   // })
                // } else {
                //   this.$router.push('/no-results')
                // }
              } else {
                this.$router.push('/no-results')
              }
            })
            .catch(() => {
              this.loading = false
              this.$eleMessage({
                type: 'info',
                message: '网络异常，请稍后再试！',
                custom: true,
                showClose: true
              })
            })
        } else {
          return false
        }
      })
      createCode()
    },
    // 查询出发城市
    async fromacityChange(fromCityValue) {
      this.toCityValue = ''
      this.tocolumns = []
      this.state = ''
      this.restaurants = []


      console.log('---',fromCityValue)
      try {
        const params = {
          uid: '6202000027',
          returntype: '0',
          provincecode: '62',
          word: fromCityValue,
        }
        const fromCityResult = await serverApi.fromCity('departureCitygs', 'gssztravelinquiry', params)
        const fromCityResultData = JSON.parse(fromCityResult.data)
        console.log('---出发城市---', JSON.parse(fromCityResult.data).data)
        if (fromCityResult.code === '200' && fromCityResultData.data) {
          this.fromCityId = JSON.parse(fromCityResult.data).data[0].id

          this.restaurants = await this.loadAll();
          // 查询目的站名称
          await this.queryToCity()
        } else {
          this.fromCityId = ''
          this.$eleMessage({
            type: 'info',
            message: '暂无城市信息，亲切换出发城市再试！',
          })
        }
      } catch (e) {
        this.$eleMessage({
          type: 'info',
          message: '网络异常，请稍后再试！'
        })
      }
    },
    // 获取目的城市
    async queryToCity() {
      this.tocolumns = []
      try {
        const params = {
          uid: '6202000027',
          returntype: '0',
          departcityid: this.fromCityId
        }
        const toCityResult = await serverApi.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
        const toCityResultData = JSON.parse(toCityResult.data)
        console.log('----', JSON.parse(toCityResultData.data))
        if (toCityResult.code === 'success' && toCityResult.success && JSON.parse(toCityResultData.data).totalrows >= '0') {
          for (const item of JSON.parse(toCityResultData.data).reachstations) {
            this.tocolumns.push({
              label: item.name,
              value: item.name
            })
          }
        } else {
          this.tocolumns = []
          this.$eleMessage({
            type: 'info',
            message: '暂无城市信息，请稍后再试！',
          })
        }
      } catch (e) {
        console.log('----', e)
        this.$eleMessage({
          type: 'info',
          message: '网络异常，请稍后再试！'
        })
      }
    },
    // 目的城市
    selectToCityValue(ToCityValue) {
      console.log('--目的城市--', ToCityValue)
      this.toCityValue = ToCityValue
    },
    // 重置
    resetForm(formName) {
      createCode()
      this.$refs[formName].resetFields()
      this.fromCityValue = ''
      this.toCityValue = ''
      this.formData.departDate = ''
      this.state = ''
      sessionStorage.removeItem('pdfEntity')
      this.$router.push('/')
    },

    async loadAll() {
      const params = {
        uid: '6202000027',
        returntype: '0',
        departcityid: this.fromCityId,
      }
      const toCityResult = await serverApi.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
      const toCityResultData = JSON.parse(toCityResult.data)
      console.log('--asda12321312312--', JSON.parse(toCityResultData.data).reachstations)
      let result = []

      for(var item of JSON.parse(toCityResultData.data).reachstations) {
        result.push({
          value: item.name,
          city: item.city
        })
      }
      return result
    },

    async querySearchAsync(queryString, cb) {

      const regtest = /^[a-zA-Z\u4e00-\u9fa5]+$/
      if(!regtest.exec(queryString) && queryString != '') {
        console.log('--queryString---', queryString)
        this.$eleMessage({
          type: 'info',
          message: '请输入汉字和字母',
        })
        queryString = ''
        this.state = ''
        return
      }

      console.log('-----', queryString)
      const params = {
        uid: '6202000027',
        returntype: '0',
        departcityid: this.fromCityId,
        word: queryString
      }
      const toCityResult = await serverApi.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
      const toCityResultData = JSON.parse(toCityResult.data)
      // console.log('--asda12321312312--', JSON.parse(toCityResultData.data).reachstations)
      let result = []
      for(var item of JSON.parse(toCityResultData.data).reachstations) {
        result.push({
          value: item.name,
          city: item.city
        })
      }
      console.log('--asd-sa-d-asd-as-d-as', result)
      const uniqueArr = result.filter((item, index, result) => { 
          return result.findIndex(t => t.value === item.value) === index; 
      });
      console.log('-12312312312--', uniqueArr)
      if(uniqueArr.length == '0') {
        this.state = ''
      }
      this.restaurants = uniqueArr
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 3000 * Math.random());
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    handleSelect(item) {
      console.log(item);
      this.toCityValue = item.value
    },
  }
}
</script>

<style scoped lang="less">
/deep/ .el-icon-date:before {
  content: '';
}

/deep/ .el-input__inner {
  height: 50px;
  font-family: MicrosoftYaHei;
  font-size: 16px;
  font-weight: 400;
  padding: 0 12px;
}

/deep/ .el-form-item__label {
  line-height: 50px;
  font-family: MicrosoftYaHei;
  font-size: 16px;
  color: #333333;
  text-align: right;
  font-weight: 400;
}

@media only screen and (max-width: 1980px) {
  .slt {
    width: 490px;
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

  /deep/ .el-form-item__label {
    line-height: 0.9rem;
  }
}

@media screen and (max-width: 768px) {
  .slt {
    width: 200px;
  }

  .ipt {
    width: 80px;
  }

  .banner-content h1 {
    margin-left: 16px;
  }
}

.footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  flex-direction: column;

  .footer_bottom {
    margin: 0.2rem 0;
    color: #666;
    font-size: 16px;
  }
}

#canvas {
  cursor: pointer;
}
</style>
