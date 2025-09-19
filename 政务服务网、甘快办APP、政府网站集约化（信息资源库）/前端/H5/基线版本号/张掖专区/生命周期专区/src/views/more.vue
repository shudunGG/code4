<template>
  <div class="notify">
    <div class="content" v-show="isShow">
      <div class="ck-content">
        <!-- <img class="mask" src=".././assets/images/Mask.png" alt="" /> -->
        <img class="mask" :src="mask" alt="" />
        <!-- <div class="ttile">{{ name }}</div> -->
        <div class="big">
          <div class="biaoti">
            {{ name }}
          </div>
          <a
            class="box"
            v-for="(item, index) in lists"
            :key="index"
            @click="getFu(item)"
          >
            <div class="top">
              <div>
                {{ item.title }}
              </div>
            </div>
            <div class="down">
              <div>
                {{ name }}
                {{
                  item.descriptionName.split(":")[0].split(",")[1] ||
                  "天水市卫生健康委"
                }}
              </div>
              <a style="color: #1d70e2">
                <!-- {{ item.descriptionName.split(":")[1] || "天水市" }} -->
                <img
                  src=".././assets/images/you.png"
                  alt=""
                  style="width: 13px; height: 13px; margin-top: 3px"
              /></a>
            </div>
          </a>
        </div>
      </div>
    </div>

    <div v-show="!isShow" style="padding-top: 200px">
      <van-loading type="spinner" size="50px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
// import { HTTP } from "../tools/request";
import { HTTP } from "../tools/request";
import { getUserInfo } from '../tools/getUserInfo'

export default {
  name: "more",
  data() {
    return {
      isShow: false,
      lists: [],
      name: "",
      id: "",
      mask: "",
      token:'',
      userType:''
    };
  },
  created() {
    this.isShow = false;
    this.id = this.$route.query.id;
    this.name = this.$route.query.name;
    console.log(this.id, this.name);

    getUserInfo().then(res => {
      this.token = res.data.tokenNo
      this.userType = res.data.userType

      let that = this;
      let id = that.id;
      const timestamp = new Date().valueOf();
      let param = {
        app_id: "jmportalnzjk",
        version: "1.0",
        charset: "UTF-8",
        origin: "1",
        header: "",
        timestamp: timestamp,
        interface_id: "showcomponent",
        biz_content: JSON.stringify({
          siteId: SiteId,
          mobileId: MobileId,
          colId: id,
        }),
      };
      HTTP.jpass(param).then((res) => {
        console.log(JSON.parse(res.data).data.lists);
        that.lists = JSON.parse(res.data).data.lists[1].contentConfigs.splice(
          1,
          JSON.parse(res.data).data.lists[1].contentConfigs.length - 1
        );
        that.mask = JSON.parse(res.data).data.lists[0].contentConfigs[0].iconPath;
        this.isShow = true;
      });
    })
  },
  methods: {
    isWeiXin () {
      var ua = window.navigator.userAgent.toLowerCase()
      if (ua.includes('micromessenger')) {
        return true
      } else {
        return false
      }
    },
    getFu(item) {
      if (this.isWeiXin()) {
        // console.log(' 是来自微信内置浏览器')
        if (item.jumpUrl.indexOf('?') !== -1) {
          window.location.href =
            item.jumpUrl + `&token=${this.token}&userType=${this.userType}`
        } else {
          window.location.href =
            item.jumpUrl + `?token=${this.token}&userType=${this.userType}`
        }
      } else {
        window.location.href = item.jumpUrl
        // console.log('不是来自微信内置浏览器')
      }
    },
  },
};
</script>

<style lang="less" scoped>
.content {
  background-color: #fff;
  // min-height: 100vh;
  .ck-content {
    //   top: -100px;
    position: relative;
    .mask {
      width: 100%;
      height: 130px;
    }
    .ttile {
      display: block;
      font-family: PingFangSC-Medium;
      font-size: 20px;
      color: #ffffff;
      font-weight: 500;
      //   line-height: 36px;
      padding-left: 12px;
      position: relative;
      // top: -100px;
    }
    .big {
      background-color: #ffffff;
      width: 92.6%;
      margin: 20px 3.2% 0;
      padding: 30px 0 0;
      height: 100%;
      border-radius: 8px;
      position: relative;
      .biaoti {
        position: absolute;
        z-index: 999;
        width: 132px;
        height: 45px;
        // border-radius: 35px 35px 35px 0;
        // background: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
        background-image: url(".././assets/images/title.png");
        background-size: cover;
        background-repeat: no-repeat;
        // box-shadow: 0 2px 10px rgba(153, 153, 153, 0.4);
        text-align: center;
        line-height: 45px;
        font-family: "PingFang SC";
        font-weight: 400;
        font-size: 17px;
        letter-spacing: 0.02em;
        color: #fff;
        text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
        top: -17.5px;
      }
      .box {
        display: block;
        // margin-top: -50px;
        position: relative;
        // top: -50px;
        width: 93.4%;
        margin-left: 3.2%;
        margin-right: 3.2%;
        background-color: #ffffff;
        box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
        border-radius: 8px;
        margin-bottom: 12px;
        .top {
          display: flex;
          padding-top: 12px;
          padding-left: 12px;
          div:nth-of-type(1) {
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #333333;
            text-align: justify;
            font-weight: 600;
            width: 90%;
          }
        }
        .down {
          display: flex;
          padding-top: 12px;
          // padding-bottom: 12px;
          padding-left: 12px;
          padding-right: 8px;
          justify-content: space-between;
          div {
            font-family: PingFangSC-Regular;
            font-size: 13px;
            color: #999999;
            width: 200px;
            font-weight: 400;
          }
          a {
            font-family: PingFangSC-Regular;
            font-size: 13px;
            color: #999999;
            font-weight: 400;
            padding-bottom: 20px;
            display: flex;
            justify-content: space-between;
            img {
              padding-left: 5px;
            }
          }
        }
      }
    }
  }
}
</style>