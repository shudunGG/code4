<template>
  <div id="app">
    <!-- 使用路由器视图来渲染路由对应的组件 -->
    <router-view v-slot="{ Component, route }">
      <!-- 如果路由元信息中指定保持活力，则使用<keep-alive> -->
      <keep-alive>
        <component :is="Component" :key="route.path" v-if="route.meta.keepAlive" />
      </keep-alive>
      <!-- 否则直接渲染组件 -->
      <component :is="Component" :key="route.path" v-if="!route.meta.keepAlive" />
    </router-view>
  </div>
</template>
<script setup>
import { onMounted, onUnmounted, reactive } from 'vue';
const state = reactive({
  windowWidth: window.innerWidth
});
onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});

onMounted(() => {
  window.addEventListener('resize', handleResize);
})

const handleResize = () => {
  console.log('窗口宽度：', state.windowWidth);
  state.windowWidth = window.innerWidth;
  location.reload()
};
onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped lang="less">
#app {
  font-size: 16px;
}
</style>
