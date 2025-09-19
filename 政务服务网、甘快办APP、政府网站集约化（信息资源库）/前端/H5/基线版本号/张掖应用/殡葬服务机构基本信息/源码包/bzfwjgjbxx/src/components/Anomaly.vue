<template>
  <div class="center">
    <div>
      <div class="top">
        <img src="@/assets/images/xtfm.png" alt />
      </div>
      <div class="bottom">系统繁忙,请稍后...</div>
      <div @click="shuxin" class="bottoms">刷新</div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      
    }
  },
  methods: {
    shuxin(){
      this.$router.go(-1);
    }
  },
}
</script>
<style lang="less" scoped>
* {
  margin: 0;
  padding: 0;
}
.center {
  position: fixed;
  top: 0;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  z-index: 10;
  bottom:0;
}
.top {
  display: flex;
  justify-content: center;
}
img {
  width: 139px;
  height: 99px;
}
.bottom {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #999999;
  letter-spacing: 0;
  margin-top: 10px;
  text-align: center;
  width: 300px;
  height: 37px;
}
.bottoms{
  text-align: center;
  color: #2783F7;
}
</style>