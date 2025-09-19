
<template>
  <div @click="quxiao" class="wrap" v-show="showWrap" :class="showContent ?'fadein':'fadeout'">
    <span>{{text}}</span>
  </div>
</template>
 <script>
 export default {
   data() {
     return {
       showWrap:false
     }
   },
   methods: {
     quxiao(){
       this.showWrap = false
     }
   },
 }
 </script>
<style scoped>
.wrap {
  display: flex;
  padding: 10px 0;
  justify-content: center;
  width: 100%;
  z-index: 99;
  position: fixed;
  top: 4%;
  background: #F9FCFF;
  border-radius: 5px;
  color: #108EE9;
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