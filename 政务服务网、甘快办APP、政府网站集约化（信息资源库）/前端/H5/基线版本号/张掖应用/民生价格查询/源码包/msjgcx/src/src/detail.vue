<template>
  <div class="main">
    <div class="infoAll">
      <div class="info">
        <div class="infoLeft">定价部门</div>
        <div class="address">{{ this.Department }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">定价文件</div>
        <div class="address">{{ this.Document }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">商品内容</div>
        <div class="address">{{ this.GoodsContent }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">商品价格</div>
        <div class="address">{{ this.GoodsPrice }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">商品项目</div>
        <div class="address">{{ this.GoodsProject }}</div>
      </div>
    </div>
    <div class="down">
      <p class="footer">本服务由张掖市提供</p>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      Department: "",
      Document: "",
      GoodsContent: "",
      GoodsPrice: "",
      GoodsProject: "",
    };
  },
  created() {
    let a = JSON.parse(
      JSON.parse(JSON.parse(sessionStorage.getItem("info"))).data
    );
    console.log(a);
    this.Department = a.Department;
    this.Document = a.Document;
    this.GoodsContent = a.GoodsContent;
    this.GoodsPrice = a.GoodsPrice;
    this.GoodsProject = a.GoodsProject;
  },
  methods: {},
};
</script>
<style lang="less" scoped>
.main {
  position: relative;
  min-height: 100vh;
  .infoAll {
    padding: 0 12px 80px;
    width: 100vw;
    box-sizing: border-box;
    .info {
      padding: 12px 0;
      display: flex;
      justify-content: space-between;
      .infoLeft {
        font-family: PingFangSC-Regular;
        font-size: 15px;
        color: #666666;
        font-weight: 400;
        width: 30%;
      }
      .address {
        font-family: PingFangSC-Regular;
        font-size: 15px;
        color: #333333;
        text-align: right;
        font-weight: 400;
        width: 70%;
      }
    }
  }

  .down {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bg_bottom.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;
    p {
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>