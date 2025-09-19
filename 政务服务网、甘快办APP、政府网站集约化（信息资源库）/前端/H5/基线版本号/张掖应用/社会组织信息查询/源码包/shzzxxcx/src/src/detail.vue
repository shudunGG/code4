<template>
  <div class="boxAll">
    <div class="box" v-for="(item, index) in list" :key="index">
      <p>{{ item.name }}</p>
      <div class="info">
        <span class="infoLeft">机构类型</span>
        <span class="address">{{ item.type }} </span>
      </div>
      <div class="info">
        <span class="infoLeft">机构电话</span>
        <span class="address">{{ item.tele }} </span>
      </div>
      <div class="info">
        <span class="infoLeft">详细地址</span>
        <span class="address">{{ item.address }} </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
    };
  },
  created() {
    this.list = JSON.parse(sessionStorage.getItem("list"));
    console.log("list", this.list);
  },
};
</script>

<style  lang="less" scoped>
.boxAll {
  padding:0 12px;
  .box {
    padding: 12px 0;
    border-bottom: 1px solid #f5f5f5;
    p {
      font-family: PingFangSC-Regular;
      font-size: 15px;
      color: #333333;
      font-weight: 900;
    }
    .info {
      display: flex;
      justify-content: space-between;
      margin-top: 6px;
      .infoLeft {
        width: 45%;
        font-family: PingFangSC-Regular;
        font-size: 15px;
        color: #666666;
        font-weight: 400;
      }
      .address {
        width: 55%;
        font-family: PingFangSC-Regular;
        font-size: 15px;
        color: #333333;
        text-align: right;
        font-weight: 400;
      }
    }
  }
}
</style>