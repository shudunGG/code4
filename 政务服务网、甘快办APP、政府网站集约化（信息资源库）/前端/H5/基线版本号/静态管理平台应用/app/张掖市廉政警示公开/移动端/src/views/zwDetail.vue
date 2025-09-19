<template>
  <div class="container-wrap">
    <div class="title">{{ $route.query.title }}</div>
    <div v-html="zw" style="margin-top: 12px;"></div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      zw: ''
    }
  },
  created () {
    this.zw = sessionStorage.getItem('zw')
  }
}
</script>

<style lang="less" scoped>
.container-wrap {
  padding: 16px;
  // margin: 16px;
  overflow: auto;
  height: 95vh;
  .title {
    font-size: 20px;
    text-align: center;
    font-weight: 700;
    padding: 16px 16px;
  }
}
</style>
