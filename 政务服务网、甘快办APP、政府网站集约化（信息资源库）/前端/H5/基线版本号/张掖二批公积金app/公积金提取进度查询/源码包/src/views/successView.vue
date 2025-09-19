<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div class="icon"><img src="../assets/images/success.png" alt="" /></div>
      <div class="success">提交成功</div>
    </div>
    <button class="queryButton" type="info" @click="backHome">返回首页</button>
    <div class="footer">
      <div class="footer-content">本服务由临夏州住房公积金管理中心提供</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  created() {},
  methods: {
    backHome() {
      // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close();    }
  }
}
</script>
<style lang="less" scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f8fbff;
  padding: 0 16px;
  overflow: auto;
  .top-bg {
    padding: 50px 67.5px 0px;
    .icon {
      margin: 0 auto 14px;
    }
    .success {
      font-weight: 700;
      font-size: 15px;
      text-align: center;
      color: #999;
      margin-bottom: 30px;
    }
  }
  //  按钮
  .queryButton {
    height: 45px;
    border-radius: 4px;
    background: #3f87f3;
    color: #fff;
    font-size: 18px;
    margin: 0 0 74px;
    width: 100%;
    font-weight: 700;
  }
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;

    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>
