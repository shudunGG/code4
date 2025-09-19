<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <template>
        <table v-for="(item, index) of tjrylb" :key="index">
          <tr>
            <td class="tab_bt">医院代码</td>
            <td>{{ item.hospitalcode || "暂无数据" }}</td>
            <td class="tab_bt">电话号码</td>
            <td>{{ phone(item.mobile) || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">姓名</td>
            <td>{{ name(item.patientname) || "暂无数据" }}</td>
            <td class="tab_bt">性别</td>
            <td>{{ item.sex === '1' ? '男' : '女' || "暂无数据" || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">体检日期</td>
            <td>{{ item.examdate || "暂无数据" }}</td>
            <td class="tab_bt">报告预览</td>
            <td style="cursor: pointer; color: #3f87f3" @click="pdfView(item.httppdf)">点击预览</td>
          </tr>
<!--          <tr>-->
<!--            <td class="tab_bt">报告下载</td>-->
<!--            <td style="cursor: pointer; color: #3f87f3" @click="downLoadPdf((item.httppdf))">点击下载</td>-->
<!--          </tr>-->
        </table>
      </template>
<!--      <div class="primary_btn" style="width: 100%;">-->
<!--        <el-button type="primary" round style="margin-left: 40%" @click="pdfView(item.httppdf)">报告预览</el-button>-->
<!--        <el-button type="primary" round style="float: right; margin-right: 40%" @click="downLoadPdf((item.httppdf))">报告下载</el-button>-->
<!--      </div>-->
    </div>
  </section>
</template>

<script>
import {name, phone} from "@/tools/desensitized";
import IndexApi from "@/api";

export default {
  name: "ResultsView",
  data() {
    return {
      tjrylb: {}
    };
  },
  async mounted() {
    this.tjrylb = this.$store.state.drResultData.tjrylb
    console.log('---', this.tjrylb)
    await this.fileBase()
  },
  methods: {
    name,
    phone,
    // 报告预览接口
    async fileBase () {
      const param = {
        downloadMaterialUrl: this.tjrylb.httppdf
      }
      try {
        const result = await IndexApi.queryList('zyyy', 'download', param, {})
        console.log('--- base64----result', result)

        if (result.code === '200' && result.success && result.data.data !== '') {
          // 将Base64字符串转换为Blob对象
          const blob = this.base64toBlod(result.data.data)
          this.pdfSrc = URL.createObjectURL(blob)
          console.log('----asd', this.pdfSrc)
        } else {
          this.$message({
            message: '暂无报告数据，请稍后再试',
            duration: 2000
          })
          return
        }
      } catch (e) {
        console.log('--- base64----e', e)
        this.$message({
          message: '网络异常，请稍后再试',
          duration: 2000
        })
        return
      }
    },
    // 预览
    async pdfView(pdfSrc) {
      const a = document.createElement('a');
      // a.href = this.pdfSrc;
      a.href = pdfSrc;
      a.target = '_blank'
      a.click();
    },
    // 下载
    downLoadPdf(pdfSrc) {
      const a = document.createElement('a');
      a.download = '体检报告';
      // a.href = this.pdfSrc;
      a.href = pdfSrc;
      a.click();
    },
    // base64转blod
    base64toBlod(base) {
      base = base.replace(/[\n\r]/g, '')
      const raw = window.atob(base)
      const rawLength = raw.length
      const uInt8Array = new Uint8Array(rawLength)
      for (let i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i)
      }
      return new Blob([uInt8Array], { type: 'application/pdf' })
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