
<template>
  <div class="wrap" v-show="showWrap">
    <div>
      <img src="../imgs/加载中.gif" alt>
      <div class="jzz">加载中</div>
    </div>
  </div>
</template>
 <script>
export default {
  data() {
    return {
      showWrap: false
    };
  }
};
</script>
<style scoped>
.jzz {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #ffffff;
  letter-spacing: 0.58px;
  text-align: center;
  line-height: 38px;
  margin-top: 20px;
}
.wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 105px;
  height: 105px;
  background-color: #4d4d4d;
  z-index: 99;
  position: fixed;
  left: 50%;
  top: 50%;
  padding: 10px;
  border-radius: 5px;
  transform: translate(-50%, -50%);
  color: #fff;
}
img {
  margin: 0 auto;
  width: 72px;
  height: 22px;
  border-radius: 4px;
  animation: 5s infinite;
}
</style>