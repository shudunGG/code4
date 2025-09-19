<template>
  <div id="app">
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>张掖市城市客运信息</h1>
      </div>
    </div>
    <main class="main box1200">
      <div class="content-container" id="resultDiag">
        <router-view />
        <div class="footer">本服务由张掖市提供</div>
      </div>
    </main>
  </div>
</template>
<style lang="less" scoped>
.footer {
  margin-top:  0.3rem;
  text-align: center;
  color: #666;
  font-size:  0.14rem;
}
</style>