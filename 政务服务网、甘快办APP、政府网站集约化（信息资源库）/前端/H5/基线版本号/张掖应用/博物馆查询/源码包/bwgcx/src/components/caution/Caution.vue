
<template>
  <div class="wrap" v-show="showWrap" :class="showContent ?'fadein':'fadeout'">
    <div style="margin-left:10px;display: flex; align-items: center;">
      <img src="../imgs/通知.png" alt />
      <span style="margin-left:15px;">{{text}}</span>
    </div>
    <div style="margin-right:10px;" @click="quxiao">
      <img src="../imgs/关闭.png" alt />
    </div>
  </div>
</template>
 <script>
export default {
  data() {
    return {
      showWrap: false
    };
  },
  methods: {
    quxiao() {
      this.showWrap = false;
    }
  }
};
</script>
<style scoped>
.wrap {
  display: flex;
  padding: 10px 0;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  z-index: 99;
  position: fixed;
  line-height: 13px;
  top: 4%;
  background: #fefceb;
  border-radius: 5px;
  color: #f96a0e;
}
img {
  width: 13px;
  height: 13px;
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