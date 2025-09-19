<template>
  <div>
    <!-- <div class="top-bg">
          <div>甘谷县天然气查询</div>
        </div> -->
        <div class="quesheng">
          <img src="../assets/images/none.png" alt="" />
          <span>{{ text || '暂无数据' }}</span>
        </div>
  </div>
</template>

<script>
export default {
  props: {
    text: String
  }
}
</script>
<style lang="less" scoped>
.quesheng {
  padding-top: 10vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  img {
    width: 60vw;
  }
  span {
    color: #999;
  }
}
</style>
