<template>
  <div class="ticket">
    <van-image :src="require('@/assets/images/index/top.png')"/>
    <div class="ticket_top">
      <div class="station">
        <div>
          <van-field
            readonly
            clickable
            label="出发地"
            :value="fromvalue"
            required
            placeholder="请选择出发城市/站点"
            @click="fromshowPicker = true"/>
        </div>
        <div class="to_action" style="width: 100%;">
          <span class="mdd" style="padding-left: 16px; color:#333; font-size: 14px; font-weight: 700;">目的站</span>
          <el-autocomplete
            style="width: 90%;"
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入目的站"
            @select="handleSelect"
          ></el-autocomplete>
        </div>
        <!--        <el-form>-->
        <!--          <el-form-item label="目的站" prop="destination">-->
        <!--            -->
        <!--          </el-form-item>-->
        <!--        </el-form>-->
        <!--        <van-field-->
        <!--          readonly-->
        <!--          clickable-->
        <!--          label="目的地"-->
        <!--          :value="tovalue.label"-->
        <!--          placeholder="请选择到达城市/站点"-->
        <!--          @click="toClickAction"/>-->

        <!--        <el-form-item>-->
        <div style="transform: translateY(-5%)">
          <span style="color: red; padding-right: 3px; position: relative; top: 10px; left: 10px">*<span style="color:#333; font-size: 14px; font-weight: 700">出发时间</span></span>
          <el-date-picker
            value-format="yyyy-MM-dd"
            :editable="false"
            v-model.trim="formData.departDate"
            :picker-options="pickerOptions"
            type="date"
            placeholder="请选择出发日期"
            @change="dataChange"
            style="width: 100%; border: none"
          >
          </el-date-picker>
        </div>

      </div>
      <van-button class="queryButton" type="info" @click="queryTicketsList">查询</van-button>
      <van-button class="resetButton" plain type="info" @click="resert">重置</van-button>
    </div>
    <van-popup v-model="fromshowPicker" round position="bottom">
      <van-picker title="选择出发城市/站点" show-toolbar :columns="fromcolumns" @cancel="fromshowPicker = false" @confirm="fromonConfirm"/>
    </van-popup>
    <van-popup v-model="toshowPicker" round position="bottom">
      <van-picker
        title="选择到达城市/站点"
        show-toolbar
        :columns="tocolumns"
        value-key="label"
        @cancel="toshowPicker = false; isShow = false"
        @confirm="toonConfirm"/>
    </van-popup>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省提供</div>
    </div>
    <van-loading type="spinner" size="60px" v-show="isShow" style="transform: translate(40%, -300%)" />
  </div>

</template>

<script>
import api from '@/api/index'
import { Toast } from 'vant'
import calendar from '@/views/calendar/calendat.vue'
import calendarDate from '@/views/calendar/calendar'

export default {
  data() {
    return {
      restaurants: [],
      state: '',
      timeout: null,
      pickerOptions: {
        disabledDate: time => {
          // 禁用今天之前的日期选择
          return (
            time.getTime() < new Date().getTime() - 24 * 60 * 60 * 1000 ||
              time.getTime() > new Date().getTime() + 24 * 60 * 60 * 1000 * 7
          )
        }
      },
      formData: {
        name: '',
        card: '',
        selectArr: [],
        region: '',
        code: '',
        depart: '',
        destination: '',
        departDate: ''
      },
      fromvalue: '',
      fromshowPicker: false,
      fromcolumns: ['兰州市', '嘉峪关市', '金昌市', '酒泉市', '张掖市', '武威市', '白银市', '天水市', '平凉市', '庆阳市', '定西市', '陇南市', '甘南藏族自治州', '临夏回族自治州', '兰州新区'],

      tovalue: '',
      toshowPicker: false,
      tocolumns: [],
      show: '',

      calendarShow: false,
      date: '',
      week: '',
      // date1: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(),
      date1: '',

      NongliData: '',

      fromCityId: '',
      flage: 0,
      isShow: false
    }
  },
  components: {
    calendar
  },
  mounted() {
    // this.queryFromCity()
  },
  methods: {
    // 出发地下拉值
    async fromonConfirm(fromvalue) {
      console.log('--出发地下拉值--', fromvalue)
      this.fromvalue = fromvalue
      this.fromshowPicker = false
      this.tovalue = ''

      this.state = ''
      this.restaurants = []
      
      await this.queryFromCity(fromvalue)
      await this.queryToCity(fromvalue)
    },
    // 目的地下拉值
    async toonConfirm(tovalue) {
      this.tovalue = tovalue
      this.toshowPicker = false
      this.isShow = false
    },
    toClickAction() {
      if (this.fromvalue == '') {
        Toast({
          message: '请选择出发城市',
          duration: 2000
        })
        return
      }
      this.toshowPicker = true
      this.isShow = true
    },
    // 日期下拉框选择
    dateSelect() {
      // console.log('asdsa')
      this.calendarShow = true
    },
    // 获取日期组件传值
    getCalendar(data1) {
      this.calendarShow = false
      this.flage = this.flage + 1
      this.date1 = data1.withoutTime
      this.date = data1.withoutTime.split('-')[1] + '月' + data1.withoutTime.split('-')[2] + '日'

      const solarDayArr = data1.withoutTime.split('-')
      const lunarDay = calendarDate.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])

      // 农历日期
      const lunarMD = lunarDay.IMonthCn + lunarDay.IDayCn
      // 公历节日\农历节日\农历节气
      let festAndTerm = []
      festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival)
      festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival)
      festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term)
      festAndTerm = festAndTerm.join('')
      this.NongliData = festAndTerm == '' ? lunarMD : festAndTerm
    },
    // 获取出发城市
    async queryFromCity(fromvalue) {
      try {
        const params = {
          uid: '6202000027',
          returntype: '0',
          provincecode: '62',
          word: fromvalue
        }
        const fromCityResult = await api.fromCity('departureCitygs', 'gssztravelinquiry', params)
        const fromCityResultData = JSON.parse(fromCityResult.data)
        console.log('---出发城市---', fromCityResult)
        if (fromCityResult.code === '200' && fromCityResultData.data) {
          this.fromCityId = JSON.parse(fromCityResult.data).data[0].id
          // for (var item of fromCityResultData.data) {
          //   this.fromcolumns.push({
          //     label: item.findname,
          //     id: item.id
          //   })
          // }
        } else {
          this.fromcolumns = []
          Toast({
            message: '暂无目的站城市'
          })
        }
      } catch (e) {
        Toast({
          message: '网络异常，请稍后再试',
          duration: 2000
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
        const toCityResult = await api.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
        console.log('目的站数据', toCityResult)
        const toCityResultData = JSON.parse(toCityResult.data)
       
        if (JSON.parse(toCityResultData.data).totalrows >= '0') {
          for (const item of JSON.parse(toCityResultData.data).reachstations) {
            this.tocolumns.push({
              label: item.name,
              value: item.name
            })
          }
        } else {
          this.tocolumns = []
          Toast({
            message: '暂无目的站城市'
          })
        }
      } catch (e) {
        Toast({
          message: '网络异常，请稍后再试',
          duration: 2000
        })
      }
    },
    // 获取车票列表
    async queryTicketsList() {

      this.$store.state.ticketsList = []
      if (!this.fromvalue) {
        Toast({
          message: '请选择出发站点',
          duration: 2000
        })
        return
      }
      if (!this.date1) {
        Toast({
          message: '请选择出发时间',
          duration: 2000
        })
        return
      }
      Toast.loading({
        duration: 0, // 持续展示 toast
        message: '加载中...',
        forbidClick: true
      })

      // this.isShow = true
      try {
        const params = {
          uid: '6202000027',
          departcityid: this.fromCityId, // 出发城市id
          // reachstationname: this.tovalue.label == null ? '' : this.tovalue.label, // 目的站名称、
          reachstationname: this.state,
          departdate: this.date1, // 出发日期
          disable: '1', //是否查询不可售（0：只查询可售班次 1：查询所有班次 默认为0）
          start: '1',
          count: '10'
        }
        console.log('--上传数据-', params)
        const ticketsListResult = await api.fromCity('findSailingsgs', 'gssztravelinquiry', params)
        const ticketsListResultData = JSON.parse(ticketsListResult.data)
        console.log('asd-sd-sad--', ticketsListResultData)
        if (ticketsListResult.code === '200' && ticketsListResult.success && ticketsListResultData.totalrows > 1) {
          this.$store.commit('ticketsList', ticketsListResultData.data)
          this.$store.commit('totalrows', ticketsListResultData.totalrows)
          this.$store.commit('fromAction', this.fromvalue)
          // this.$store.commit('toAction', this.tovalue.label)
          this.$store.commit('toAction',this.state)

          this.$store.commit('fromData', this.date1)
          // this.$store.commit('fromCityId', this.fromCityId)
          this.$router.push({
            path: '/ticketlist',
            query: {
              param: JSON.stringify(params)
            }
          })
          // this.isShow = false
          Toast.clear()
        } else {
          Toast.clear()
          // this.isShow = false
          Toast({
            message: '未查询到相关汽车票信息，请咨询出发地客运站',
            duration: 2000
          })
        }
        // Toast.clear()
      } catch (e) {
        Toast({
          message: '网络异常，请稍后再试',
          duration: 2000
        })
        
        Toast.clear()
      }
    },
    // 重置
    resert() {
      this.fromvalue = ''
      this.tovalue = ''
      this.fromCityId = ''
      this.date1 = ''
      this.formData.departDate = ''
      this.state = ''
      // this.date1 = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()

      const year = new Date().getFullYear()
      const month = new Date().getMonth() + 1
      const day = new Date().getDate()

      const solarDayArr = (year + '-' + month + '-' + day).split('-')
      const lunarDay = calendarDate.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])
      // 农历日期
      const lunarMD = lunarDay.IMonthCn + lunarDay.IDayCn
      // 公历节日\农历节日\农历节气
      let festAndTerm = []
      festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival)
      festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival)
      festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term)
      festAndTerm = festAndTerm.join('')
      this.NongliData = festAndTerm == '' ? lunarMD : festAndTerm
    },
    dataChange(val) {
      console.log(val)
      this.date1 = val
    },
    async loadAll() {
      const params = {
        uid: '6202000027',
        returntype: '0',
        departcityid: this.fromCityId
      }
      const toCityResult = await api.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
      const toCityResultData = JSON.parse(toCityResult.data)
      console.log('--asda12321312312--', JSON.parse(toCityResultData.data).reachstations)
      const result = []
      for (var item of JSON.parse(toCityResultData.data).reachstations) {
        result.push({
          value: item.name,
          city: item.city
        })
      }
      console.log('---', result)
      return result
    },
    async querySearchAsync(queryString, cb) {
     
      const regtest = /^[a-zA-Z\u4e00-\u9fa5]+$/
      if(!regtest.exec(queryString) && queryString != '') {
        console.log('--queryString---', queryString)
        Toast({
          message: '请输入汉字和字母',
          duration: 1000
        })
        queryString = ''
        this.state = ''
        return
      }
      const params = {
        uid: '6202000027',
        returntype: '0',
        departcityid: this.fromCityId,
        word: queryString
      }
      const toCityResult = await api.fromCity('arrivalStationgsJm', 'gssztravelinquiry', params)
      const toCityResultData = JSON.parse(toCityResult.data)
      console.log('--asda12321312312--', JSON.parse(toCityResultData.data).reachstations)
      const result = []
      for (var item of JSON.parse(toCityResultData.data).reachstations) {
        result.push({
          value: item.name,
          city: item.city
        })
      }
      console.log('--asd-sa-d-asd-as-d-as', result)
      const uniqueArr = result.filter((item, index, result) => {
        return result.findIndex(t => t.value === item.value) === index
      })
      if(uniqueArr.length == '0') {
        this.state = ''
      }
      console.log('-12312312312--', uniqueArr)
      this.restaurants = uniqueArr
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants

      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 3000 * Math.random())
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    handleSelect(item) {
      console.log(item)
      // this.toCityValue = item.value
      // this.tovalue.value = item.value
      // console.log('---', this.tovalue.value)
    }
  }
}
</script>

<style lang="less" scoped>

.ticket {
  .mdd{
    display: inline-block;
    margin-top: -10px;
    box-sizing: border-box;
    width: 30%;
  }
  /deep/.van-field__control {
    margin-left: 8%;
  }
    /deep/ .to_action {
    transform: translateY(40%);
  }
    /deep/ .el-input__inner {
      //transform: translateY(0%);
      //position: absolute;
      //top: -100;
    }
 .to_action {
   display: flex;
    /deep/ span:nth-child(4) {
      transform: translateY(100%);
      color: red;
    }
   /deep/ .el-input__inner {
     padding-left: 12px;
     width: 100%;
     border: none;
     transform: translate(0%, -50%);
   }
  }
  //height: 100vh;
  .el-form-item__content {
    .el-autocomplete {
      /deep/ .el-input__inner::placeholder {
        //color: red;
        transform: translate(-5%, -70%);
      }
    }
  }
  /deep/ .el-form-item {
    margin: 0;
  }
  /deep/ .el-form-item__content {
    height: 25px;
  }
  /deep/ .el-form-item__label {
    color:#333; font-size: 14px; font-weight: 700;
    padding-left: 14px;
    transform: translateY(20%);
  }
  /deep/ .el-input__prefix {
    padding-left: 15px;
    padding-right: 13px;
  }
  /deep/ .el-input__inner::placeholder {
    font-family: "PingFang SC Bold";
    font-weight: 700;
    font-size: 14px;
    text-align: left;
    color: #999;
    //transform: translateX(-10%);
    transform: translate(0%, -0%);
  }
  /deep/ .el-input__inner {
    padding-left: 12px;
    width: 70%;
    border: none;
    transform: translate(36.5%, -50%);
  }
  /deep/ .el-input__icon{
    display: none;
  }
  .ticket_top {
    .station {
      width: 90%;
      transform: translate(5%, -25%);
      border-radius: 8px;
      background: #fff;
      box-shadow: 0 0 12px #00000029;
      position: relative;
      /deep/ .van-field__label {
        width: 4.2em;
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 12px;
        color: #333;
      }
      /deep/ .van-field__label {
        span {
          font-size: 14px;
        }
      }
      /deep/ .van-field__body {
        input::placeholder {
          font-family: "PingFang SC Bold";
          font-weight: 700;
          font-size: 14px;
          text-align: left;
          color: #999;
        }
      }
      .img {
        position: absolute;
        top: 20%;
        right: 0;
      }
      .dateSelect {
        padding: 12px;
        span {
          font-family: "PingFang SC Bold";
          font-weight: 700;
          font-size: 13px;
          color: #333;
        }
        span:nth-child(1) {
          span {
            font-family: "PingFang SC Bold";
            font-weight: 700;
            font-size: 13px;
            text-align: left;
            color: #999;
          }
        }
        .riqi {
          float: right;
          transform: translateY(-20%);

          .van-image {
            //transform: translateY(25%);
          }
        }
      }
    }
    .queryButton {
      width: 90%;
      margin-top: 30px;
      border-radius: 4px;
      font-size: 18px;
      background: #1677ff;
      border-color: #1677ff;
      transform: translateX(5%);
    }
    .resetButton {
      width: 90%;
      margin-top: 12px;
      margin-bottom: 18px;
      border-radius: 4px;
      font-size: 18px;
      border: 0.37px solid #bcbcbc;
      color: #333;
      transform: translateX(5%);
    }
  }
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/index/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
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

  .calendar_style {
    background-color: #fff;
    position: fixed;
    bottom: 0px;
  }
  .dialog {
    position: fixed;
    top: 0;
    height: 100vh;
    width: 100%;
    background: #000;
    opacity: 0.3;
  }
}
</style>
