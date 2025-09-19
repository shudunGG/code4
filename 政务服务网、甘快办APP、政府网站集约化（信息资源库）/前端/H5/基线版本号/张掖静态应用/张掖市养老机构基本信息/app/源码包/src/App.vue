<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive" />
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive" />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {};
  },
  created() {
    const isiOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
    if (isiOS) {
      // 解决页面刷新vuex数据消失
      window.addEventListener("pagehide", () => {
        localStorage.setItem("stateInfo", JSON.stringify(this.$store.state));
      });
      if (localStorage.stateInfo) {
        this.$store.replaceState(
          Object.assign(
            {},
            this.$store.state,
            JSON.parse(localStorage.stateInfo)
          )
        );
        localStorage.removeItem("stateInfo");
      }
    } else {
      // 解决页面刷新vuex数据消失
      window.addEventListener("beforeunload", () => {
        localStorage.setItem("stateInfo", JSON.stringify(this.$store.state));
      });
      if (localStorage.stateInfo) {
        this.$store.replaceState(
          Object.assign(
            {},
            this.$store.state,
            JSON.parse(localStorage.stateInfo)
          )
        );
        localStorage.removeItem("stateInfo");
      }
    }
  },
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
