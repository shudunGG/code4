<template>
  <div class="dashboard-container">
    <div
      style="font-family: 'PingFang SC Bold'; font-weight: 700; font-size: 15px; text-align: center; color: #333; margin-top: 12px">
      选择日期</div>
    <el-calendar v-model="date" @click.native="selectData(date)">
      <template slot="dateCell" slot-scope="{date, data}">
        <div :class="{ selected: isSelected(date, data) }">
          <div class="solar">{{ data.day.split('-')[2] }}</div>
          <!--          <div class="lunar" :class="{ festival : isFestival(date, data) }">{{ solarToLunar(date, data) }}</div>-->
          <div class="lunar">{{ solarToLunar(date, data).length == '4' ? solarToLunar(date, data).slice(2, 4) :
      solarToLunar(date, data) }}</div>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<script>
import calendar from '@/views/calendar/calendar'
export default {
  name: 'Dashboard',
  data() {
    return {
      date: new Date(),
      // 根据selectedDates设置选中日期
      selectedDates: []
    }
  },
  mounted() {
    document.querySelector('thead').innerHTML = `
      <th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>
      <p class="dateP">
      <span>${(new Date().getFullYear() + '年' + (new Date().getMonth() + 1) + '月')}</span>
</p>
    `
  },
  methods: {
    // 是否选中日期
    isSelected: function (slotDate, slotData) {
      return this.selectedDates.includes(slotData.day)
    },
    // 是否节假日
    isFestival(slotDate, slotData) {
      const solarDayArr = slotData.day.split('-')
      const lunarDay = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])

      // 公历节日\农历节日\农历节气
      let festAndTerm = []
      festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival)
      festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival)
      festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term)
      festAndTerm = festAndTerm.join('')

      return festAndTerm != ''
    },
    // 公历转农历
    solarToLunar(slotDate, slotData) {
      const solarDayArr = slotData.day.split('-')
      const lunarDay = calendar.solar2lunar(solarDayArr[0], solarDayArr[1], solarDayArr[2])

      // 农历日期
      const lunarMD = lunarDay.IMonthCn + lunarDay.IDayCn

      // 公历节日\农历节日\农历节气
      let festAndTerm = []
      festAndTerm.push(lunarDay.festival == null ? '' : ' ' + lunarDay.festival)
      festAndTerm.push(lunarDay.lunarFestival == null ? '' : '' + lunarDay.lunarFestival)
      festAndTerm.push(lunarDay.Term == null ? '' : '' + lunarDay.Term)
      festAndTerm = festAndTerm.join('')

      return festAndTerm == '' ? lunarMD : festAndTerm
    },
    selectData(date) {
      console.log('asdsa---asdsad', date)
      this.$emit('getCalendar', this.msToDate(date))
    },
    msToDate(msec) {
      const datetime = new Date(msec)
      const year = datetime.getFullYear()
      const month = datetime.getMonth()
      const date = datetime.getDate()
      const hour = datetime.getHours()
      const minute = datetime.getMinutes()
      const second = datetime.getSeconds()

      const result1 = year +
        '-' +
        ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) +
        '-' +
        ((date + 1) < 10 ? '0' + date : date) +
        ' ' +
        ((hour + 1) < 10 ? '0' + hour : hour) +
        ':' +
        ((minute + 1) < 10 ? '0' + minute : minute) +
        ':' +
        ((second + 1) < 10 ? '0' + second : second)

      const result2 = year +
        '-' +
        ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) +
        '-' +
        ((date + 1) < 10 ? '0' + date : date)

      const result = {
        hasTime: result1,
        withoutTime: result2
      }
      return result
    }
  }
}
</script>

<style scoped>
/deep/ .dateP {
  width: 90vh;
  font-family: "PingFang SC Bold";
  font-weight: 700;
  font-size: 14px;
  /*text-align: center;*/
  color: #333;
  background: #eee;
  padding: 6px 0 6px 0;
  margin-bottom: 6px;
}

/deep/ .dateP span {
  padding-left: 23%;
}

/deep/ .el-calendar__body {
  padding: 6px;
}

/**隐藏上一月、本月、下一月*/
/deep/ .el-calendar__header {
  display: none;
}

/**月份居中*/
.el-calendar__title {
  width: 100%;
  text-align: center;
}

/**日期div的样式*/
/deep/ .el-calendar-table tr td:first-child {
  border-left: 0px;
  border: 0xp solid white;
}

/deep/ .el-calendar-table thead th {
  text-align: center;
  font-family: "PingFang SC Bold";
  font-weight: 700;
  /*font-size: 30px;*/
  text-align: center;
  color: #000;
}

/deep/ .el-calendar-table td {
  /*min-height: 110px;*/
  /*min-width: 110px;*/
  border-right: 0px;
  border: 0xp solid white;
}

/deep/ .el-calendar-table td.is-selected {
  background-color: white;
  border: 0xp solid white;
}

/deep/ .el-calendar-table .el-calendar-day {
  /*height: 100%;*/
  padding: 0px;
  text-align: center;
  height: 50px;
}

.el-calendar-table .el-calendar-day>div {
  height: 104px;
  text-align: center
}

/**日期div的样式-公历*/
/deep/ .el-calendar-table .el-calendar-day>div .solar {
  /*padding-top: 15px;*/
  /*text-align: center;*/
  /*color: red;*/
  font-family: "PingFang SC Bold";
  font-weight: 700;
  font-size: 13px;
  /*line-height: 70px;*/
  text-align: center;
  /*color: #333;*/
}

/deep/ .el-calendar-table td.is-today>div .solar {
  /*border-radius: 50%;*/
}

/deep/ .el-calendar-table td.is-today>div .lunar {
  /*color: #409EF;*/
}

/**日期div的样式-农历*/
.el-calendar-table .el-calendar-day>div .lunar {
  /*padding-top: 10px;*/
  text-align: center
}

/**日期div的样式-选中*/
.el-calendar-table .el-calendar-day>div.selected {
  background-color: #fef2f2;
  border: 3px solid #fb0;
  border-radius: 20px;
  text-align: center;
}

/deep/ .el-calendar-table:not(.is-range) td.next {
  /*color: red;*/
  pointer-events: none
}

/deep/ .el-calendar-table:not(.is-range) td.prev {
  /*color: red;*/
  pointer-events: none
}

/**本月周末设置为红色*/
/*.el-calendar-table .current:nth-last-child(-n+2) .solar {*/
/*  color: red;*/
/*}*/
/**本月农历设置为灰色*/
.el-calendar-table .current .lunar {
  /*color: #606266;*/
  font-family: "PingFang SC Bold";
  font-weight: 600;
  font-size: 11px;
  text-align: left;
  color: #999;
}

/**本月农历节日设置为红色*/
/*.el-calendar-table .current .lunar.festival {*/
/*  color: red;*/
/*}*/
.el-calendar-table .current {
  height: 50px;
}

/deep/ .prev {
  border: none;
}

/deep/ .current {
  border: none;
}

/**禁用点击效果*/
/*.el-calendar-table td {*/
/*pointer-events: none;*/
/*}*/
</style>
