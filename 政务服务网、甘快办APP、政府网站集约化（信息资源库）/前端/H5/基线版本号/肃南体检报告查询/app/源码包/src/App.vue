<template>
  <router-view v-slot="{ Component, route }">
    <!-- <transition> -->
    <keep-alive>
      <component :key="route.path" :is="Component" v-if="$route.meta.keepAlive"/>
    </keep-alive>
    <component :key="route.path" v-if="!$route.meta.keepAlive" :is="Component"/>
    <!-- </transition> -->
  </router-view>
</template>

<style lang="less">
body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #333;
}
</style>
