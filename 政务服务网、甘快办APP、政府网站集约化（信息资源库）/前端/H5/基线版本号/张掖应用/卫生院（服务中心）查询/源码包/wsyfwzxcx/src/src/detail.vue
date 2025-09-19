<template>
  <div class="main">
    <div class="infoAll">
      <div class="info">
        <div class="infoLeft">所在地区</div>
        <div class="address">{{ this.district }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">所在乡镇</div>
        <div class="address">{{ this.township }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">所在村</div>
        <div class="address">{{ this.village }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">单位名称</div>
        <div class="address">{{ this.name }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">详细地址</div>
        <div class="address">{{ this.address=="" ? "暂无" : this.address }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">负责人</div>
        <div class="address">{{this.fzr == "" ? "暂无" : this.fzr }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">联系电话</div>
        <div class="address">{{ this.tele }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">单位类别</div>
        <div class="address">{{ this.type }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">单位状态</div>
        <div class="address">{{ this.state }}</div>
      </div>
      <div class="info">
        <div class="infoLeft">是否分支</div>
        <div class="address">{{ this.isbranch }}</div>
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
      district: "",
      township: "",
      village: "",
      name: "",
      type: "",
      fzr: "",
      tele: "",
      state: "",
      isbranch: "",
      address: "",
    };
  },
  created() {
    let a = JSON.parse(
      JSON.parse(JSON.parse(sessionStorage.getItem("info"))).data
    );
    console.log(a);
    this.district = a.district;
    this.township = a.township;
    this.village = a.village;
    this.name = a.name;
    this.address = a.address;
    this.type = a.type;
    this.fzr = a.fzr;
    this.tele = a.tele;
    this.state = a.state;
    this.isbranch = a.isbranch;
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