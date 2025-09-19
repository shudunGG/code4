<template>
  <div class="empty">
    <img src="../assets/img/zwjl.png" alt="" srcset="" />
    <div class="empty-text">暂无数据</div>
  </div>
</template>

<script>
export default {
  name: 'NoResultsView',
  methods: {}
}
</script>
<style scoped>
.empty {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  padding: 50px 0;
  /* position: relative; */
  /* left: 50%;
  transform: translateX(-100px); */
}
img {
  width: 550px;
}
@media screen and (max-width: 750px) {
img {
  width: 250px;
}
}
</style>
