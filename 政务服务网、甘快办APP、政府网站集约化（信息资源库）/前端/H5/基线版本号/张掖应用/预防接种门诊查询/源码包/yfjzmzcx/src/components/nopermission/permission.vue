
<template>
  <div class="wrap" v-if="showWrap" :class="showContent ?'fadein':'fadeout'">
    <img src="../imgs/wqx.png" alt />
  </div>
</template>
 
<style scoped>
.wrap {
  z-index: 99;
  position: fixed;
  left: 50%;
  top: 50%;
  padding: 10px;
  border-radius: 5px;
  transform: translate(-50%, -50%);
  color: #fff;
}
img{
  width: 120px;
  height: 120px;
  border-radius: 4px;
}
.fadein {
  animation: animate_in 0.25s;
}
.fadeout {
  animation: animate_out 0.25s;
  opacity: 0;
}
@keyframes animate_in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@keyframes animate_out {
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>