
<template>
  <div @click="quxiao" class="wrap" v-show="showWrap" :class="showContent ?'fadein':'fadeout'">
    <span>{{text}}</span>
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
  justify-content: center;
  width: 30%;
  z-index: 99;
  position: fixed;
  margin: 0 auto;
  left: 0;
  right: 0;
  bottom: 4%;
  opacity: 0.9;
  background: #3a3a3a;
  border-radius: 5px;
  color: #ffffff;
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