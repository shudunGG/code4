<template>
  <div class="downloadpage">
      <img @click="godownload()" src="./imgs/downloadimg.png" alt />
      <a href="https://jxb.tj.gov.cn/download/pack/jxb.apk"></a>
  </div>
</template>
<script>
export default {
  data() {
    return {
        userAgent:''
    };
  },
  props: {
    
  },
  components: {
      
  },
  created(){
      let userAgent = this.$route.query.userAgent;
      this.userAgent = userAgent;
  },
  mounted() {},
  methods: {
    godownload() {
        // ios 跳转
        if(this.userAgent.indexOf("iPhone") != -1) {
            window.location.href = "https://apps.apple.com/cn/app/%E6%B4%A5%E5%BF%83%E5%8A%9E/id1290112555?from=singlemessage&isappinstalled=0"
        }else {
        // 安卓跳转
            window.location.href = "https://jxb.tj.gov.cn/download/pack/jxb.apk"
        }
    }
  }
};
</script>
<style lang="less" scoped>
 .downloadpage img {
     width: 100%;
     height: 100%;
 }
</style>