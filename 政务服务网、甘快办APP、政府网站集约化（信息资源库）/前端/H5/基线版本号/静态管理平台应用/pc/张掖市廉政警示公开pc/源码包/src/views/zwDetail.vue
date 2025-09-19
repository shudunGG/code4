<template>
  <div class="app">
    <el-page-header @back="goBack"> </el-page-header>
    <div
      style="
        font-size: 26px;
        text-align: center;
        font-weight: 700;
        margin: 20px 0;
      "
    >
      {{ $route.query.title }}
    </div>
    <div v-html="zw"></div>
  </div>
</template>
<script>
export default {
  name: 'ZwDetail',
  data() {
    return {
      zw: ''
    }
  },
  created() {
    this.zw = sessionStorage.getItem('zw')
  },
  methods: {
    goBack() {
      this.$router.back()
    }
  }
}
</script>

<style scoped lang="less">
/deep/ .el-page-header {
  align-items: center;
  line-height: normal;
  color: #409eff;
}
/deep/ .el-page-header__left::after {
  height: 0;
}
</style>
