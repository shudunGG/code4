<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <template>
        <table v-for="(item, index) of dataDetail" :key="index">
          <tr>
            <td class="tab_bt">体检人姓名</td>
            <td>{{ name(item.patName) || "暂无数据" }}</td>
            <td class="tab_bt">证件号码</td>
            <td>{{ IdNumberDesensitization(item.cardNo) || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">电话</td>
            <td>{{ phone(item.tel) || "暂无数据" }}</td>
            <td class="tab_bt">体检日期</td>
            <td>{{ item.examDate || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">操作</td>
<!--            <td><el-button type="primary"><a :href='item.reportUrl' target="_blank">查看详情</a></el-button></td>-->
            <td><a style="color: #3f87f3;" :href='item.reportUrl' target="_blank">查看详情</a></td>
          </tr>
        </table>
      </template>
    </div>
  </section>
</template>

<script>
import {name, phone, IdNumberDesensitization} from "@/tools/desensitized";

export default {
  name: "ResultsView",
  data() {
    return {
      dataDetail: {},
    };
  },
  async mounted() {
    this.dataDetail = this.$store.state.fsyyResult

    if(!this.dataDetail[0]) {
      this.$router.push('/')
    }

    this.dataDetail.sort(function (a, b) {
      // console.log('-', b.examDate.replaceAll('-', '').split(' ')[0])
      return b.examDate.replaceAll('-', '').split(' ')[0] - a.examDate.replaceAll('-', '').split(' ')[0]
    })
  },
  methods: {
    name,
    phone,
    IdNumberDesensitization,
  }
};
</script>

<style scoped>
.result-container table {
  margin-bottom: 0.2rem;
}

.result-container table td {
  background: #ffffff;
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