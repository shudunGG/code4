<template>
  <div class="main">
    <div class="infoAll">
      <div class="info">
        <div class="infoLeft">接种点名称</div>
        <div class="address">{{ this.jiezhongdianname }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">详细地址</div>
        <div class="address">{{ this.address }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">联系人</div>
        <div class="address">{{ this.lxr }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">联系电话</div>
        <div class="address">{{ this.tele }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">详细地址</div>
        <div class="address">{{ this.address=="" ? "暂无" : this.address }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">负责单位</div>
        <div class="address">{{ this.fuzedanwei }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">接种门诊</div>
        <div class="address">{{ this.jiezhongmenzhen }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">机构编码</div>
        <div class="address">{{ this.jgbm }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">级别</div>
        <div class="address">{{ this.level }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">接种数目</div>
        <div class="address">{{ this.num }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">备注</div>
        <div class="address">{{ this.beizhu }}</div>
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
      jiezhongdianname: "",
      address: "",
      lxr: "",
      tele: "",
      fuzedanwei: "",
      fzr: "",
      tele: "",
      jiezhongmenzhen: "",
      jgbm: "",
      level: "",
      num: "",
      beizhu: "",
    };
  },
  created() {
    let a = JSON.parse(
      JSON.parse(JSON.parse(sessionStorage.getItem("info"))).data
    );
    console.log(a);
    this.jiezhongdianname = a.jiezhongdianname;
    this.address = a.address;
    this.lxr = a.lxr;
    this.tele = a.tele;
    this.address = a.address;
    this.fuzedanwei = a.fuzedanwei;
    this.jiezhongmenzhen = a.jiezhongmenzhen;
    this.jgbm = a.jgbm;
    this.level = a.level;
    this.num = a.num;
    this.beizhu = a.beizhu == ''? '暂无':a.beizhu;
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