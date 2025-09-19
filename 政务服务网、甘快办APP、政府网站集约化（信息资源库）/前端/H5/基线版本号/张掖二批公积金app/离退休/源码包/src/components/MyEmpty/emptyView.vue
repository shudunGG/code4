<template>
  <div class="empty">
    <div class="empty_img">
      <img src="../../assets/images/qs.png" alt="" />
      <div class="empty_text"><slot name="concent"></slot></div>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省市场监督管理局提供</div>
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
  background: #f6f8fb;
  height: 100vh;
  .empty_img {
    position: relative;
    top: 50%;
    transform: translateY(-50%);
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
