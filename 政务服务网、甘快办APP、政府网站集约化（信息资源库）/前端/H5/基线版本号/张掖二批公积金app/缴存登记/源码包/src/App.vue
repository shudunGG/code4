<template>
  <div id="app">
    <router-view v-if="!$route.meta.keepAlive" />
    <keep-alive >
      <router-view v-if="$route.meta.keepAlive" />
    </keep-alive>
  </div>
</template>

<script>
// 引入inobounce库
// import iNoBounce from 'inobounce'
export default {
  // mounted() {
  //   console.log(iNoBounce.enable, 'iNoBounce')
  //   iNoBounce.disable()
  // },
  // beforeDestroy() {
  //   iNoBounce.enable()
  // },
  methods: {}
}
</script>

<style lang="less">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 14px;
  color: #333;
}
</style>
