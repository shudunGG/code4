<template>
  <div id="app">
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>公积金提取进度查询</h1>
      </div>
    </div>
    <main class="main box1200">
      <div class="content-container">
        <router-view />
      </div>
      <!--  右侧区域结束  -->
    </main>
    <div class="foot">本服务由张掖市提供</div>
  </div>
</template>
<style lang="less">
.foot{
  width: 300px;
  margin: 10px auto 20px;
  font-family: "PingFang SC";
  font-weight: 400;
  font-size: 16px;
  text-align: center;
  color: #999;
}
</style>
