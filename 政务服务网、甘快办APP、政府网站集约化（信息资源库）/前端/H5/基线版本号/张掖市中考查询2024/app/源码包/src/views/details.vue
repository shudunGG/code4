<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div style="">张掖市中考成绩查询</div>
    </div>
    <div class="result"  style="margin-top: 155px">
      <div class="title"><span>查询结果</span></div>
      <table border="1">
        <tr>
          <td class="key">统编学号</td>
          <td colspan="3">{{ dataList[0].tbxh }}</td>
        </tr>
        <!-- <tr>
          <td class="key">身份证号</td>
          <td colspan="3">{{ dataList[0].sfzh }}</td>
        </tr> -->
        <tr>
          <td class="key">姓名</td>
          <td colspan="3">{{ dataList[0].xm }}</td>
        </tr>
        <tr>
          <td class="key">语文</td>
          <td>{{ dataList[0].yw }}</td>
          <td class="key">数学</td>
          <td>{{ dataList[0].sx }}</td>
        </tr>
        <tr>
          <td class="key">英语</td>
          <td>{{ dataList[0].yy }}</td>
          <td class="key">化学</td>
          <td>{{ dataList[0].hx }}</td>
        </tr>
        <tr>
          <td class="key">物理</td>
          <td>{{ dataList[0].wl }}</td>
          <td class="key">体育</td>
          <td>{{ dataList[0].ty }}</td>
        </tr>
         <tr>
          <td class="key">物理实验操作</td>
          <td>{{ dataList[0].wlsycz }}</td>
          <td class="key">化学实验操作</td>
          <td>{{ dataList[0].hxsycz }}</td>
        </tr>
        <tr>
          <td class="key">道德与法治/历史</td>
          <td>{{ dataList[0].ddyfz_ls }}</td>
          <td class="key">生物/地理</td>
          <td>{{ dataList[0].swdl }}</td>
        </tr>
        <tr>
          <td class="key">综合素质评价</td>
          <td colspan="3">{{ dataList[0].zhszpj }}</td>
        </tr>
        <tr>
          <td class="key">照顾加分</td>
          <td colspan="3">{{ dataList[0].zgjf }}</td>
        </tr>
        <tr>
          <td class="key">总分（含照顾项加分）</td>
          <td colspan="3">{{ dataList[0].zf }}</td>
        </tr>
      </table>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市教育局提供</div>
    </div>
  </div>
</template>

<script>
import { onBeforeMount, ref, reactive, toRefs } from 'vue'
export default {
  setup() {
    const state = reactive({
      dataList: {}
    })
    const arr = ref([])
    const finished = ref(true)
    onBeforeMount(() => {
      state.dataList = JSON.parse(sessionStorage.getItem('dataList'))
      console.log(sessionStorage.getItem('dataList'), state.dataList, '111')
    })
    return {
      ...toRefs(state),
      finished,
      arr
    }
  }
}
</script>


<style lang="less" scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: white;
  padding: 0 16px;
  overflow: auto;
  .top-bg {
    height: 140px;
    background: url("../assets/img/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    position: absolute;
    left: 0;
    right: 0;
    div {
      margin-top: 47px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }
}
.result {
  margin-bottom: 12px;
  height: auto;
  background-color: #fff;
  box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.2);
  border-radius: 4px;
  box-sizing: border-box;
  padding: 16px;
}

.title {
  width: 100%;
  height: 36px;
  font-family: PingFangSC-Semibold;
  font-size: 16px;
  color: #000;
  font-weight: 600;
  border-bottom: 1px solid #e8e8e8;
}

.title span {
  display: block;
  width: 72px;
  height: 100%;
  box-sizing: border-box;
  border-bottom: 2.5px solid #3f87f3;
  text-align: center;
}

table {
  width: 100%;
  text-align: center;
  border-color: #e8e8e8;
  margin-top: 12px;
  font-family: PingFangSC-Regular;
  font-size: 13px;
  color: #333;
  font-weight: 400;
}

table td {
  width: 25%;
  height: 44px;
  box-sizing: border-box;
  padding: 0 3px;
}

table td.key {
  background-color: #f5f5f5;
}

.zhu {
  width: 100%;
  margin-top: 16px;
  font-family: PingFangSC-Regular;
  font-size: 13px;
  color: #333;
  font-weight: 400;
}
.footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/img/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    // bottom: 0;
    // left: 0;
    // z-index: 0;
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
</style>
