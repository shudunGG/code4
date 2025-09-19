<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <template>
        <table v-for="(item, index) of dataDetail" :key="index">
          <tr>
            <td class="tab_bt">体检人姓名</td>
            <td>{{ name(item.userName) || "暂无数据" }}</td>
            <td class="tab_bt">证件号码</td>
            <td>{{ IdNumberDesensitization(item.examineeIcno) || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">电话</td>
            <td>{{ phone(item.examineePhone) || "暂无数据" }}</td>
            <td class="tab_bt">体检人编号</td>
            <td>{{ item.serviceNumber || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">操作</td>
<!--            <td><el-button type="primary" @click="goDetail(item.serviceNumber, item)">查看详情</el-button></td>-->
            <td><span style="color: #3f87f3; cursor:pointer" @click="goDetail(item.serviceNumber, item)" >查看详情</span></td>
          </tr>
        </table>
      </template>
    </div>
  </section>
</template>

<script>
import {name, phone, IdNumberDesensitization} from "@/tools/desensitized";
import IndexApi from "@/api";
import sm2 from '../utils/sm2'

export default {
  name: "ResultsView",
  data() {
    return {
      dataDetail: {},
      privateKey: '009b0cddd1a07a54222d0433165e95ec76b08ea5af4967ddecd3f5bac7fb4b0796',
      publicKey: '04753c8780ad3c343218f9ba4368669de096601ce3259124fd23f9496f5bea3ff398c7ae83e07e1e1250883dbf3c9c01af75415f06b267414f93ef318ffb2523f2'
    };
  },
  async mounted() {
    this.dataDetail = this.$store.state.zyyResultData

    if(!this.dataDetail[0]) {
      this.$router.push('/')
    }
  },
  methods: {
    name,
    phone,
    IdNumberDesensitization,
    async goDetail(serviceNumber, itemDetail) {
      const params = {
        token: sm2.encrypt(this.$store.state.formateData.name,this.publicKey),
        authCode: sm2.encrypt(this.$store.state.formateData.phone,this.publicKey),
        serviceNumber
      }
      try {
        const result = await IndexApi.queryList('zyyy', 'reportDetail', params, {})
        const resultReportDetail = JSON.parse(sm2.decrypt(result.data, this.privateKey))
        if (result.code === '200' && result.success && resultReportDetail.data) {
          this.$store.commit('zyyDetail', itemDetail)
          this.$store.commit('zyResultReportDetail', resultReportDetail)
          this.$router.push({
            path: '/zy_report'
          })
        }
      } catch (e) {
        console.log('--- 报告详情 e---', e)
        this.$message({
          message: '网络异常，请稍后再试',
          duration: 2000
        })
        return
      }
    }
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