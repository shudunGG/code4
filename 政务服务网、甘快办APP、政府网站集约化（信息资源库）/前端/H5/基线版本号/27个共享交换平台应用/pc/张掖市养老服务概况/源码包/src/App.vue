<template>
  <div id="app">
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>张掖市养老服务概况</h1>
      </div>
    </div>
    <main class="main box1200">
      <div class="content-container" id="resultDiag">
        <router-view />
        <div class="footer">本服务由甘肃省张掖市提供</div>
      </div>
    </main>
  </div>
</template>

<style  lang="less" scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 16px;
  color: #333;
}
.footer {
  margin-top: 0.3rem;
  text-align: center;
  color: #666;
  font-size: 0.14rem;
}
</style>
