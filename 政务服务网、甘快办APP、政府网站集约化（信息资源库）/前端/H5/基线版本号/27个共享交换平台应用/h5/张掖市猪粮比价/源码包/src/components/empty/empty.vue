<template>
  <div class="empty">
    <div class="empty_img">
      <img src="@/assets/images/empty.png" alt="" width="137px" />
      <div class="empty_text">{{ emptyText }}</div>
    </div>

  </div>
</template>
<script setup>
const props = defineProps({
  imageUrl: {
    type: String,
    default: '@/assets/images/empty.png',
  },
  emptyText: {
    type: String,
    default: '暂无数据'
  }
})


</script>
<style lang="less" scoped>
/* empty-state.less */
.empty {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-67.5px, -67.5px);

  .empty_img {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    img {
      width: 137px;
      height: 137px;
    }

    .empty_text {
      margin-top: 12px;
      font-size: 14px;
      color: #333;
    }
  }
}
</style>