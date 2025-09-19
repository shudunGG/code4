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
    <div class="content" v-show="isShow">
      <img class="background" :src="mask" alt="" />
      <div class="ck-content">
        <div class="bstj">
          <div class="top">
            <img src=".././assets/images/bstj.jpg" alt="" />
          </div>

          <div class="box">
            <van-swipe
              class="my-swipe"
              indicator-color="white"
              ref="swipe"
              @change="onChange"
            >
              <van-swipe-item>
                <div class="list1">
                  <div
                    :class="model[index]"
                    v-for="(item, index) in lbtlist"
                    :key="index"
                    v-if="index < 6"
                    @click="getdetail(item.jumpUrl, item.jumpName)"
                  >
                    <img :src="item.iconPath" alt="" />
                    <span>{{ item.title }}</span>
                    <span>{{ item.descriptionName }}项服务</span>
                    <img src=".././assets/images/tz.png" alt="" />
                  </div>
                </div>
              </van-swipe-item>
              <van-swipe-item>
                <div class="list1">
                  <div
                    :class="model[index - 6]"
                    v-for="(item, index) in lbtlist"
                    :key="index"
                    v-if="index > 5"
                    @click="getdetail(item.jumpUrl, item.jumpName)"
                  >
                    <img :src="item.iconPath" alt="" />
                    <span>{{ item.title }}</span>
                    <span>{{ item.descriptionName }}项服务</span>
                    <img src=".././assets/images/tz.png" alt="" />
                  </div>
                </div>
              </van-swipe-item>
            </van-swipe>
          </div>
        </div>
        <div class="fwtj">
          <div class="top">
            <img src=".././assets/images/fwtj.jpg" alt="" />
          </div>

          <div class="box">
            <div class="list">
              <div
                class="model"
                v-for="(item, index) in downlist"
                :key="index"
                @click="getdetaild(item.jumpUrl)"
              >
                <img :src="item.iconPath" alt="" />
                <div class="text">
                  <span>{{ item.title }}</span>
                  <span>{{ item.descriptionName }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-show="!isShow" style="padding-top: 200px">
      <van-loading type="spinner" size="50px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
import { HTTP } from "../tools/request";
export default {
  name: "home",
  data() {
    return {
      isShow: false,
      mask: "",
      model: ["model0", "model1", "model2", "model3", "model0", "model1"],
      bstlist: [],
      lbtlist: [],
      downlist: [],
      num: "",
    };
  },

  created() {
    let that = this;
    setTimeout(() => {
      that.$refs.swipe.resize();
    }, 500);

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

        that.lbtlist = JSON.parse(res.data).data.lists[1].contentConfigs;

        that.downlist = JSON.parse(res.data).data.lists[2].contentConfigs;
        that.isShow = true;
      })
      .catch(() => {
        that.$Toast.clear();
        that.$Toast.fail("系统繁忙");
      });
  },
  // mounted() {
  //   let that = this;
  //   this.$nextTick(() => {
  //     console.log(sessionStorage.getItem("num"));
  //     let num = sessionStorage.getItem("num");
  //     that.$refs.swipe.active = Number(num);
  //     sessionStorage.clear();
  //   });
  // },
  beforeRouteEnter(to, form, next) {
    to.meta.keepAlive = true;
    next();
  },
  methods: {
    onChange(index) {
      console.log(index);
    },
    getdetail(id, name) {
      // // this.$refs.swipe
      // this.num = this.$refs.swipe.active;
      // sessionStorage.setItem("num", this.num);
      // console.log(sessionStorage.getItem("num"));
      // // console.log(id);
      this.$router.push({
        path: "/more",
        query: {
          id: id,
          name: name,
        },
      });
    },
    getdetaild(id) {
      this.$router.push({
        path: "/notify",
        query: {
          id: id,
          // name: name,
        },
      });
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
    .bstj {
      .top {
        margin-top: 15px;
        img {
          width: 100%;
          display: block;
        }
      }
      .box {
        position: relative;
        ::v-deep.van-swipe__indicators {
          position: absolute;
          top: 508px;
          z-index: 999;
        }

        ::v-deep.my-swipe .van-swipe-item {
        }
        ::v-deep.van-swipe__indicator {
          width: 4px;
          height: 4px;
          background-color: #dddddd !important;
        }
        ::v-deep.van-swipe__indicator--active {
          width: 16px;
          height: 4px;
          background-color: #006eff !important;
          border-radius: 4px;
        }
        .list1 {
          margin-top: 20px;
          margin-bottom: 4px;
          .model0 {
            background: #ecf1fd;
          }
          .model1 {
            background: #fdf6e7;
          }
          .model2 {
            background: #fdf1eb;
          }
          .model3 {
            background: #f1f1fe;
          }
          div {
            width: 93.6%;
            margin: 0 3.2%;
            height: 70px;
            background: #ecf1fd;
            border-radius: 16px;
            margin-bottom: 12px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            img:nth-of-type(1) {
              width: 39px;
              height: 39px;
              margin-left: 16px;
            }
            span:nth-of-type(1) {
              padding-right: 32px;
              font-family: PingFangSC-Semibold;
              font-size: 17px;
              color: #333333;
              letter-spacing: 0;
              font-weight: 600;
            }
            span:nth-of-type(2) {
              font-family: PingFangSC-Regular;
              font-size: 13px;
              color: #666666;
              letter-spacing: 0;
              text-align: right;
              font-weight: 400;
              padding-left: 32px;
            }
            img:nth-of-type(2) {
              width: 16px;
              height: 16px;
              margin-right: 16px;
            }
          }
        }
      }
    }
    .fwtj {
      .top {
        margin-top: 15px;
        img {
          width: 100%;
          display: block;
        }
      }
      .box {
        margin-top: 22px;
        .list {
          width: 93.6%;
          margin: 0 3.2%;
          .model {
            display: block;

            position: relative;
            margin-bottom: 12px;
            img {
              width: 100%;
              height: 74px;
            }
            .text {
              position: absolute;
              top: 14px;
              left: 16px;
              span:nth-of-type(1) {
                display: block;
                font-family: PingFangSC-Semibold;
                font-size: 17px;
                color: #ffffff;
                letter-spacing: 0;
                font-weight: 600;
              }
              span:nth-of-type(2) {
                padding-top: 8px;
                display: block;
                font-family: PingFangSC-Regular;
                font-size: 13px;
                color: #ffffff;
                letter-spacing: 0;
                font-weight: 400;
              }
            }
          }
        }
      }
    }
  }
}
</style>
