<template>
  <section class="result-container">
    <h2 class="cx">查询结果</h2>
    <div class="result" v-loading="loading">
      <template>
        <table v-for="(item, index) in list" :key="index">
          <tr>
            <td>出发站点</td>
            <td>{{ item.busshortname || '暂无数据' }}</td>
            <td>全票价</td>
            <td>{{ item.fullprice + '元' || '暂无数据' }}</td>
          </tr>
          <tr>
            <td>发车日期</td>
            <td>{{  $route.params.departDate || '暂无数据' }}</td>
            <td>发车时间</td>
            <td>{{ item.starttime || '暂无数据' }}</td>
          </tr>
          <tr>
            <td>到达站点</td>
            <td>{{ item.endstationname || '暂无数据' }}</td>
            <td>是否可售</td>
            <td>{{ item.iscansell == '1' ? '可售' : '不可售' || '暂无数据' }}</td>
          </tr>
        </table>
      </template>
    </div>

    <div class="block" style="text-align: center">
      <el-pagination
          prev-text="上一页"
          next-text="下一页"
          background
          :hide-on-single-page="true"
          layout="prev, pager, next, jumper"
          :page-size="params.pageSize"
          @current-change="currentChange"
          :total="JSON.parse(JSON.parse($route.params.data)).totalrows"
          @size-change="handleSizeChange"
          :current-page.sync="currentPage3">
      </el-pagination>
    </div>
  </section>
</template>

<script>

import serverApi from "@/api";

export default {
  name: 'ResultsView',
  mounted() {
    console.log('----ads--阿大撒-asd---sad---', JSON.parse(JSON.parse(this.$route.params.data)))
    this.list = JSON.parse(JSON.parse(this.$route.params.data)).data
  },
  data() {
    return {
      list: [],
      params: {
        pageSize: 5,
        pageNum: 1,
      },
      currentPage3: 1,
     loading: false,
    }
  },
  methods: {
    // 页码改变触发
    currentChange(pageNum) {
      this.params.pageNum = pageNum;
      // 根据新的页码重新请求
      this.selectResult(this.params);
    },
    handleSizeChange() {
      this.params.pageNum = this.currentPage3;
      // 根据新的页码重新请求
      this.selectResult(this.params);
    },
    async selectResult() {
      this.loading = true
      let params = {
        uid: '6202000027',
        departcityid: this.$route.params.fromCityId, // 出发城市id
        reachstationname: this.$route.params.toCityValue, // 目的站名称、
        departdate: this.$route.params.departDate, // 出发日期
        disable: '1',//是否查询不可售（0：只查询可售班次 1：查询所有班次 默认为0）
        start: this.params.pageNum,
        count: this.params.pageSize,
      }
      try {
        const res = await serverApi.fromCity('findSailingsgs', 'gssztravelinquiry', params)
        console.log('--asd---asd--', JSON.parse(res.data))
        if (res.code === "200" && res.success) {
          this.loading = false
            this.list = JSON.parse(res.data).data;
            this.$forceUpdate();
        } else {
          this.loading = false
          this.$message({
            type: "error",
            message: "暂无数据，请稍后再试！",
          });
        }
      } catch (e) {
        this.loading = false
        this.$message({
          type: "error",
          message: "查询失败，请重试或联系管理员",
        });
        this.$router.push({ name: "Index" });
      }
    },
  }
}
</script>

<style scoped>
.result-container table {
  margin-bottom: 0.3rem;
}

/* .result-container table tr:nth-child(1) { */
/* background: #f5f6f8; */
/* font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #333333;
  font-weight: 400; */
/* width: 30%; */
/* } */
.result-container table thead td {
  font-size: 0.16rem;
  font-weight: bold;
}

.result-container table td,
.result-container table th {
  border: 1px solid #e6e6e6;
  font-size: 0.14rem;
  padding: 0 0.14rem;
  width: 30%;
}

.result-container table td:nth-child(1) {
  background: #f5f6f8;
  width: 20%;
}

.result-container table td:nth-child(3) {
  background: #f5f6f8;
  width: 20%;
}

.pagination {
  text-align: center;
}

.tips {
  margin-top: 0.3rem;
  color: #999999;
  text-align: center;
}

::v-deep.el-pagination.is-background .btn-next {
  padding: 0 10px;
}

::v-deep.el-pagination.is-background .btn-prev {
  padding: 0 10px;
}

::v-deep .el-pagination .btn-prev {
  background: #ffffff;
  border: 1px solid rgba(221, 221, 221, 1);
  border-radius: 6px;
  padding-right: 0;
  padding-left: 0;
  margin-right: 8px;
}

::v-deep .el-pager li {
  background: #ffffff;
  border: 1px solid rgba(221, 221, 221, 1);
  border-radius: 6px;
  margin-right: 8px;
}

::v-deep .el-pagination .btn-next {
  background: #ffffff;
  border: 1px solid rgba(221, 221, 221, 1);
  border-radius: 6px;
  padding-right: 0;
  padding-left: 0;
}</style>
