
<template>
  <div class="wrap" v-show="showWrap">
    <div>
      <img src="../imgs/加载中.gif" alt />
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
.wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 120px;
  height: 120px;
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