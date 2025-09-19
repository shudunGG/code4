<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <table>
        <tr>
          <td class="tab_bt">统编学号</td>
          <td colspan="3">{{ dataList[0].tbxh || "-" }}</td>
        </tr>
        <!-- <tr>
          <td class="tab_bt">身份证号</td>
          <td colspan="3">{{ dataList[0].id || "-" }}</td>
        </tr> -->
        <tr>
          <td class="tab_bt">姓名</td>
          <td colspan="3">{{ dataList[0].xm || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">语文</td>
          <td>{{ dataList[0].yw || "-" }}</td>
          <td class="tab_bt">数学</td>
          <td>{{ dataList[0].sx || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">英语</td>
          <td>{{ dataList[0].yy || "-" }}</td>
          <td class="tab_bt">化学</td>
          <td>{{ dataList[0].hx || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">物理</td>
          <td>{{ dataList[0].wl || "-" }}</td>
          <td class="tab_bt">体育</td>
          <td>{{ dataList[0].ty || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">物理实验操作</td>
          <td>{{ dataList[0].wlsycz || "-" }}</td>
          <td class="tab_bt">化学实验操作</td>
          <td>{{ dataList[0].hxsycz || "-" }}</td>
        </tr>

        <tr>
          <td class="tab_bt">道德与法治/历史</td>
          <td>{{ dataList[0].ddyfz_ls || "-" }}</td>
          <td class="tab_bt">生物/地理</td>
          <td>{{ dataList[0].swdl || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">综合素质评价</td>
          <td colspan="3">{{ dataList[0].zhszpj || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">照顾加分</td>
          <td colspan="3">{{ dataList[0].zgjf || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">总分（含照顾项加分）</td>
          <td colspan="3">{{ dataList[0].zf || "-" }}</td>
        </tr>
      </table>
    </div>
    <div class="reult-tip" style="color: red">
      温馨提示：由于查询次数限制，请截图或拍照保存，或者于第二天再次查询！
    </div>
  </section>
</template>
<script>
import { toRefs, onBeforeMount, reactive, watch } from "vue";
export default {
  setup() {
    const state = reactive({
      dataList: {},
    });
    onBeforeMount(() => {
      state.dataList = JSON.parse(sessionStorage.getItem("dataList"));
    });
    const stopWatch = watch(
      state.dataList,
      (newValue, oldValue) => {
        console.log(newValue, oldValue);
      },
      { deep: true }
    );
    return { ...toRefs(state),stopWatch };
  },
};
</script>
<style scoped>
.result-container table {
  margin-bottom: 0.2rem;
}

.result-container table td {
  background: #ffffff;
  text-align: center;
}

.result-container table thead td {
  font-size: 0.16rem;
  font-weight: bold;
}

.result-container table td,
.result-container table th {
  border-right: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
  font-size: 0.14rem;
  padding: 0 0.14rem;
}

.tips {
  margin-top: 0.3rem;
  color: #999999;
  text-align: center;
}

.block {
  text-align: center;
}
</style>