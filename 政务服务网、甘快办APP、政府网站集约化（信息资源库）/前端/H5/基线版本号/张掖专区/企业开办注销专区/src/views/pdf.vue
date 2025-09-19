<template>
  <div class="content">
    <div style="height: 100vh" ref="pdfRef">
      <button class="btn" @click="xiazia">下载文件</button>
    </div>
  </div>
</template>
<script>
import Pdfh5 from "pdfh5";
import "pdfh5/css/pdfh5.css";
export default {
  mounted() {
    let pdf = this.$route.query.pdf;
    const pdfh5 = new Pdfh5(this.$refs.pdfRef, {
      // pdfurl:
      //   "https://zwfw.gansu.gov.cn/jmportal_files/filemanager/jmpdoc/2022-01-10/580e4c53d84e4c20a351a2baa4210087.pdf",
      pdfurl: pdf,
    });
    // 监听完成事件
    pdfh5.on("complete", function (status, msg, time) {
      console.log(
        `状态：${status}，信息：${msg}，耗时：${time}毫秒，总页数：${this.totalNum}`
      );
    });
  },
  methods: {
    xiazia() {
      let pdf = this.$route.query.pdf;
      window.location.href = pdf;
    },
  },
};
</script>
<style lang="less" scoped>
.content {
  position: relative;
  .btn {
    position: fixed;
    background: rgba(0, 0, 0, 0.4);
    border-radius: 50%;
    z-index: 999;
    height: 50px;
    width: 50px;
    color: #fff;
    top: 70%;
    right: 4%;
    font-size: 17px;
  }
}
</style>