<template>
  <div class="main">
    <div class="switch">
      <van-cell center title="标题">
        <template #right-icon>
          <van-switch v-model="checked" size="24"/>
        </template>
      </van-cell>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
        checked: true
    };
  },
  components: {},
  mounted() {},
  methods: {}
};
</script>
<style lang="less" scoped>
.main {
  //   background-color: #f8f8f8;
  box-sizing: border-box;
  padding: 32px 16px 32px 16px;
  min-height: 100vh;
  
  .title {
    font-size: 24px;
  }
}
.HanwebUI_desc {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #999999;
  letter-spacing: 0.52px;
  margin-bottom: 34px;
}
.HanwebUI_title {
  font-family: PingFangSC-Medium;
  font-size: 23px;
  color: #333333;
  letter-spacing: 0.74px;
  text-align: left;
}
</style>