<!--
 * @Author: your name
 * @Date: 2020-05-30 18:14:56
 * @LastEditTime: 2021-12-08 14:15:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /health-code/src/views/home.vue
--> 
<template>
  <div class="home">
    <div class="content"
         v-show="isShow">
      <img class="background"
           :src="mask"
           alt="" />
      <!-- <img class="background" src=".././assets/images/mask1.png" alt="" /> -->
      <div class="ck-content">
        <div class="bashi">
          <div class="box">
            <div class="model"
                 v-for="(item, index) in boxlist"
                 :key="index"
                 @click="getmore(item, index)">
              <img :src="item.iconPath"
                   alt="" />
              <span :class="color[index]">{{ item.title }}</span>
            </div>
          </div>
        </div>
        <div class="bstj">
          <div class="top"
               :id="'id' + 0">
            <img src=".././assets/images/qykblc.png"
                 alt="" />
          </div>
          <div class="box">
            <img src=".././assets/images/qykb.png"
                 alt="" />
          </div>
        </div>

        <div class="fwtj">
          <div class="top"
               :id="'id' + 1">
            <img src=".././assets/images/qyzxlc.png"
                 alt="" />
          </div>
          <div class="box">
            <img src=".././assets/images/qyzx.png"
                 alt="" />
          </div>
        </div>
      </div>
    </div>

    <div v-show="!isShow"
         style="padding-top: 200px">
      <van-loading type="spinner"
                   size="50px"
                   vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
import { HTTP } from "../tools/request";
import { getUserInfo } from '../tools/getUserInfo'

export default {
  name: "home",
  data () {
    return {
      isShow: false,
      mask: "",
      color: ["color", "color1", "color2", "color3"],
      boxlist: [],
      token: '',
      userType:''
    };
  },
  created () {

    getUserInfo().then(res => {
      console.log('res', res)
      this.token = res.data.tokenNo
      this.userType = res.data.userType

      let that = this;
      // setTimeout(() => {
      //   that.$refs.swipe.resize();
      // }, 500);

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
          colId: ColId,
        }),
      };
      HTTP.jpass(param)
        .then((res) => {
          console.log(JSON.parse(res.data), "data");

          that.mask = JSON.parse(
            res.data
          ).data.lists[0].contentConfigs[0].iconPath;
          that.boxlist = JSON.parse(res.data).data.lists[1].contentConfigs;

          that.isShow = true;
        })
        .catch(() => {
          that.isShow = true;
          that.$Toast.clear();
          that.$Toast.fail("系统繁忙");
        });
    })
  },
  beforeRouteEnter (to, form, next) {
    to.meta.keepAlive = true;
    next();
  },
  methods: {
    getmore (item, index) {
      console.log(item, index)
      if (this.isWeiXin()) {
        if (index === 0) {
          window.location.href = item.jumpUrl + `&token=${this.token}&userType=${this.userType}`;
        } else if (index === 1) {
          window.location.href = item.jumpUrl + `&token=${this.token}&userType=${this.userType}`;
        } else if (index === 2) {
          window.location.href = item.jumpUrl + `&token=${this.token}&userType=${this.userType}`;
        } else if (index === 3) {
          this.$router.push({
            path: "/notify",
            query: {
              id: item.jumpUrl,
            },
          });
        } else {
          window.location.href = item.jumpUrl
        }
      } else {
        if (index === 3) {
          this.$router.push({
            path: "/notify",
            query: {
              id: item.jumpUrl,
            },
          });
        } else {
          window.location.href = item.jumpUrl
        }
      }
    },
    isWeiXin () {
      let ua = window.navigator.userAgent.toLowerCase()
      if (ua.includes('micromessenger')) {
        return true
      } else {
        return false
      }
    },
  },
};
</script>

<style lang="less" scoped>
html {
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
}
html::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar {
  display: none;
}
.content {
  background: #fff;
  min-height: 100vh;
  // position: relative;
  .background {
    width: 100%;
    height: 200px;
  }

  .ck-content {
    .bashi {
      .box {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        margin: 10px 10.5px;
        .model {
          margin-bottom: 12px;
          position: relative;
          img {
            width: 169.5px;
            height: 90px;
          }
          span {
            position: absolute;
            left: 1%;
            text-align: center;
            top: 48px;
            width: 100%;
            font-family: PingFangSC-Medium;
            font-size: 15px;
            color: #4766a4;
            letter-spacing: 0.51px;
            font-weight: 500;
          }
          .color1 {
            color: #ab6802;
          }
          .color2 {
            color: #97543f;
          }
          .color3 {
            color: #5f4ea2;
          }
        }
      }
    }
    .bstj {
      width: 93.6%;
      margin: 0 3.2%;
      margin-bottom: 30px;
      .top {
        margin-top: 15px;
        img {
          width: 100%;
          display: block;
        }
      }
      .box {
        img {
          width: 100%;
          display: block;
        }
      }
    }
    .fwtj {
      width: 93.6%;
      margin: 0 3.2%;
      margin-bottom: 30px;
      .top {
        margin-top: 15px;
        img {
          width: 100%;
          display: block;
        }
      }
      .box {
        img {
          width: 100%;
          display: block;
        }
      }
    }
  }
}
</style>
