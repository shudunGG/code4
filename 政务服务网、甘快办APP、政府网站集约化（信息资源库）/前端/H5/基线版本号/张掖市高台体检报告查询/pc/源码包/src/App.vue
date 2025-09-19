<template>
  <div id="app">
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>高台县体检报告查询</h1>
      </div>
    </div>

    <main class="main box1200 lj">
      <div id="resultDiag">
        <router-view />
      </div>
    </main>
    <div class="footer">本服务由高台县提供</div>
  </div>
</template>
<style lang="less" scoped>
.lj {
  padding: 30px 0 0;
  display: flex;
  align-items: center;
}
#resultDiag {
  width: 100%;
}
.footer {
  margin: 0 0 0.3rem;
  text-align: center;
  color: #666;
  font-size: 0.14rem;
}
</style>
