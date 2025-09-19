<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <!-- <table v-for="(item, index) in dataList" :key="index">
        <tr>
          <td class="tab_bt">项目</td>
          <td colspan="3">{{ item.tbxh || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">价格变化(环比)</td>
          <td colspan="3">{{ item.jgbh_hb || "-" }}</td>
        </tr>
        <tr>
          <td class="tab_bt">价格变化(同比)</td>
          <td>{{ item.jgbh_tb || "-" }}</td>
          <td class="tab_bt">单价/斤</td>
          <td>{{ item.dj || "-" }}</td>
        </tr>
      </table> -->
      <table border="1" class="card-wrap">
          <tr class="title">
            <!-- <td align="center">序号</td> -->
            <td align="center">项目</td>
            <td align="center">价格变化(环比)</td>
            <td align="center">价格变化(同比)</td>
            <td align="center">单价/斤</td>
          </tr>
          <tr v-for="(item, index) in dataList" :key="index">
            <!-- <td align="center">{{ item.xh }}</td> -->
            <td align="center">{{ item.xm }}</td>
            <td align="center">{{ item.jgbh_hb }}</td>
            <td align="center">{{ item.jgbh_tb }}</td>
            <td align="center">{{ item.dj }}</td>
          </tr>
        </table>
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
      console.log(state.dataList);
    });
    // const stopWatch = watch(
    //   state.dataList,
    //   (newValue, oldValue) => {
    //     console.log(newValue, oldValue);
    //   },
    //   { deep: true }
    // );
    return { ...toRefs(state) };
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