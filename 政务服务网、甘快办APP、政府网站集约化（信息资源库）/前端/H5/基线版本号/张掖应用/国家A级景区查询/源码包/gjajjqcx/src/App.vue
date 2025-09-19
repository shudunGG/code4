<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"/>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {};
  },
  created() {
    // if (navigator && navigator.userAgent) {
    //   let userAgent = navigator.userAgent;
    //   // 判断是否为hanwebuserAgent 不是跳转提示页
    //   if ((userAgent.indexOf("hanweb") != -1) == false) {
    //     this.$router.push({
    //       path: "/downloadpage",
    //       query: {
    //         userAgent: userAgent,
    //       }
    //     });
    //   }
    // }
  },
  mounted() {}
};
</script>

<style lang="less">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 14px;
  color: #333;
}
</style>
