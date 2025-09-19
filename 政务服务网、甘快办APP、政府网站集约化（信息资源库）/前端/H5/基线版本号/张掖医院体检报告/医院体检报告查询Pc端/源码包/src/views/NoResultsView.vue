<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="logo"><img src="../assets/img/zwjl.png" alt=""></div>
    <div class="info">暂无报告</div>
  </section>
</template>

<script>
export default {
  name: "NoResultsView"
}
</script>
<style scoped>
.logo {
  width: 100%;
  display: flex;
  justify-content: center;
}

img {
  width: 30%;
  display: flex;
  justify-content: center;
}

.info {
  margin-top: 0.4rem;
  line-height: 0.6rem;
  width: 100%;
  color: #999999;
  font-weight: 500;
  text-align: center;
  font-size: 14px;
}
</style>
