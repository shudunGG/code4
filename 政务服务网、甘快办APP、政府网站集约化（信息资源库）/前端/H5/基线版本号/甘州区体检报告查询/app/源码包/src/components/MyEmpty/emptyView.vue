<template>
  <div class="empty">
    <div class="empty_img">
      <img src="../../assets/images/qs.png" alt="" />
      <div class="empty_text">暂无数据</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'MyEmpty'
}
</script>
<style lang="less" scoped>
.empty {
  background: #f8fbff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .empty_img {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    img {
      width: 100%;
      height: 100%;
    }
    .empty_text {
      font-size: 15px;
      color: #999999;

      font-weight: 500;
    }
  }
}
.footer {
  width: 100%;
  height: 127.5px;
  background-image: url(../../assets/images/bgc.png);
  background-repeat: no-repeat;
  background-size: contain;
  background-position: bottom;
  position: absolute;
  bottom: 0;
  left: 0;

  .footer-content {
    width: 100%;
    text-align: center;
    font-size: 14px;
    color: #999;
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
