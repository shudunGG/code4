<template>
  <section class="result-container">
    <h2 class="cx">查询结果</h2>
    <!-- <hr style="color: #eeeeee;"> -->
    <div style="background: #eeeeee; width: 1140px; height: 1px; margin-bottom: 70px"></div>
    <div class="logo">
      <img src="../assets/img/zwjl.png" alt="" width="200px" />
    </div>
    <div class="info">暂无数据</div>
  </section>
</template>

<script>
export default {
  name: 'NoResultsView',
  methods: {
  }
}
</script>
<style scoped>
.logo {
  width: 100%;
  display: flex;
  justify-content: center;
}
.info {
  margin-top: 0.2rem;
  line-height: 0.5rem;
  width: 100%;
  text-align: center;
  font-family: PingFangSC-Medium;
  font-size: 14px;
  color: #999999;
  text-align: center;
  font-weight: 500;
}
</style>
