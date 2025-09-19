<template class="header">
  <div>
    <div class="tops"></div>
    <div class="top">
      <img src="./imgs/无法完成操作.png" alt />
    </div>
    <h1 style="text-align:center;font-size:20px;padding-top:10px;">无法完成操作</h1>
    <div class="bottom">内容详情可拆行，建议不超过两行</div>
    <div class="hui">
       <Normal :normal='main' />
      <Normalf :normal='assist' />
    </div>
  </div>
</template>
<script>
import { Normal,Normalf } from "hanweb-ui" 
export default {
  data() {
    return {
      main: this.$route.query.main,
      assist: this.$route.query.assist
    };
  },
  components: {
    Normal,
    Normalf
  },
};
</script>
<style lang="less" scoped>
.normal {
  margin-top: 16px;
  margin-left: 16px;
}
.tops {
  height: 100px;
}
.top {
  display: flex;
  justify-content: center;
}
img {
  width: 80px;
  height: 80px;
}
.bottom {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #999999;
  letter-spacing: 0;
  margin-top: 10px;
  text-align: center;
  height: 37px;
}
.hui {
  position: fixed;
  top:44%;
  bottom: 0;
  left: 0;
  right: 0;
  padding-top: 30px;
  z-index: 10;
  background-color: #f5f5f5;
}

.blue {
  color: #fff;
  background: #108ee9;
}
.white {
  color: #333333;
  background: #ffffff;
}
</style>