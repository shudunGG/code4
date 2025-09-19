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
      <!-- <div class="ck-content">{{background}}</div>
       -->
      <img class="background"
           :src="background"
           alt="" />
      <div class="ck-content">
        <!-- <keep-alive> -->
        <van-tabs type="card"
                  v-model="active">
          <van-tab title="疫情防控">
            <a class="healthcode"
               :href="healthcode1"><img :src="healthcode"
                   alt="" /></a>
            <!-- href="healthcode1" -->
            <div class="fyfw">
              <span class="title1">防疫服务</span>
              <div class="box">
                <div class="fw"
                     v-for="(item, index) in fyfwlist"
                     :key="index">
                  <a :href="item.jumpUrl"><span :class="fwwz[index]">
                      {{ item.title }}
                    </span>
                  </a>
                </div>
              </div>
            </div>

            <div class="fyrx"
                 ref="">
              <span class="title2">防疫热线</span>
              <div class="box2">
                <div class="rx">
                  <div class="phone"
                       v-for="(item, index) in fyrxlist"
                       :key="index">
                    <div>{{ item.title.split(",")[0] }}</div>
                    <div>{{ item.title.split(",")[1] }}</div>
                    <div @click="handletophone(item.title.split(',')[1])">
                      <img src=".././assets/images/dh.png"
                           alt="" />
                      <span> 点击拨打</span>
                    </div>
                  </div>
                </div>
                <div></div>
              </div>
            </div>

            <div class="fyzx">
              <span class="title3">防疫资讯</span>
              <div class="box3">
                <div class="zx"
                     v-for="(item, index) in fyzclist"
                     v-if="index < 3"
                     :key="index"
                     @click="getdetail(item)">
                  <img :src="item.firstPicPath"
                       alt="" />
                  <div>
                    <span>{{ item.title }}</span>
                    <span>{{ item.source }}</span>
                  </div>
                </div>
                <div class="more"
                     @click="getmore(fyzclist)">
                  查看更多
                  <img style="width: 10px; height: 7px"
                       src=".././assets/images/down.png"
                       alt="" />
                </div>
              </div>
            </div>
          </van-tab>

          <van-tab title="复工复产">
            <div class="fgfcfw">
              <span class="title1">复工复产服务</span>
              <div class="box1">
                <div class="fgfc"
                     v-for="(item, index) in fgfclist"
                     :key="index">
                  <img @click="handletoDetail(item)"
                       :src="item.iconPath"
                       alt="" />
                </div>
              </div>
            </div>

            <div class="zczx">
              <span class="title2">政策资讯</span>

              <div class="box2">
                <van-tabs color="#3F77F8"
                          v-model="active2">
                  <van-tab title="复工复产政策">
                    <div class="fgfczc">
                      <div v-for="(item, index) in fgfczclist"
                           v-if="index < 3"
                           :key="index"
                           style="padding-top: 12px"
                           @click="getnews1(item)">
                        <span>{{ item.title }}</span><span>发文日期：{{ item.time }}</span><span>发文部门：{{ item.source }}</span>
                      </div>
                      <span class="more"
                            @click="getmore1(fgfczclist)">
                        查看更多
                        <img style="
                            width: 10px;
                            height: 7px;
                            position: relative;
                            top: 0px;
                            right: 0px;
                            border: 0;
                          "
                             src=".././assets/images/down.png"
                             alt="" />
                      </span>
                    </div>
                  </van-tab>

                  <van-tab title="投资项目信息">
                    <div class="tzxmxx">
                      <div v-for="(item, index) in tzxmxxlist"
                           :key="index"
                           @click="getnews2(item)">
                        <span>{{ item.title }}</span><span>发文日期：{{ item.time }}</span><span>发文部门：{{ item.source }}</span>
                      </div>
                      <span class="more"
                            @click="getmore2(tzxmxxlist)">
                        查看更多
                        <img style="
                            width: 10px;
                            height: 7px;
                            position: relative;
                            top: 0px;
                            right: 0px;
                          "
                             src=".././assets/images/down.png"
                             alt="" />
                      </span>
                    </div>
                  </van-tab>
                </van-tabs>
              </div>
            </div>
          </van-tab>
        </van-tabs>
        <!-- </keep-alive> -->
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
// import { Toast } from "vant";
import { HTTP } from "../tools/request";
import {baseUrl} from '../HTTP'

export default {
  name: "home",
  data () {
    return {
      baseUrl,
      active: 0,
      active2: 0,
      isShow: false,
      background: "",
      healthcode: "",
      healthcode1: "",
      fyfwlist: [],
      fyrxlist: [],
      fyzclist: [],
      fgfclist: [],
      fgfczclist: [],
      tzxmxxlist: [],
      fwwz: ["fwwz1", "fwwz2", "fwwz3", "fwwz4", "fwwz5", "fwwz6"],
      page: 3,
      ind: 0,
    };
  },
  created () {
    // window.location.href =
    //   "alipays://platformapi/startapp?appId=2019011763060066&page=pages/health-code/health-code?siteid=2";

    this.active = Number(sessionStorage.getItem("num"));
    sessionStorage.clear();
    let that = this;
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
        console.log(JSON.parse(res.data).data);
        that.background = JSON.parse(
          res.data
        ).data.lists[0].contentConfigs[0].iconPath;
        that.healthcode = JSON.parse(
          res.data
        ).data.lists[1].list[0].list[0].contentConfigs[0].iconPath;
        that.healthcode1 = JSON.parse(
          res.data
        ).data.lists[1].list[0].list[0].contentConfigs[0].jumpUrl;
        that.fyfwlist = JSON.parse(
          res.data
        ).data.lists[1].list[0].list[1].contentConfigs;
        that.fyrxlist = JSON.parse(
          res.data
        ).data.lists[1].list[0].list[2].contentConfigs;
        that.fyzclist = JSON.parse(
          res.data
        ).data.lists[1].list[0].list[3].infos;
        that.fgfclist = JSON.parse(
          res.data
        ).data.lists[1].list[1].list[0].contentConfigs;
        that.fgfczclist = JSON.parse(
          res.data
        ).data.lists[1].list[1].list[1].list[0].list[0].infos;
        that.tzxmxxlist = JSON.parse(
          res.data
        ).data.lists[1].list[1].list[1].list[1].list[0].infos;
        this.isShow = true;
      })
      .catch(() => {
        that.$Toast.clear();
        that.$Toast.fail("系统繁忙");
      });
  },
  beforeRouteEnter (to, form, next) {
    to.meta.keepAlive = true;
    next();
  },
  mounted () { },
  methods: {
    // health() {
    //   // window.location.href =
    //   //   `https://ds.alipay.com/?scheme=` +
    //   //   encodeURIComponent(
    //   //     `alipays://platformapi/startapp?appId=2019011763060066&page=pages/health-code/health-code&query=${encodeURIComponent(
    //   //       "siteid=2"
    //   //     )}`
    //   //   );
    //   location.href =
    //     "alipays://platformapi/startapp?appId=2019011763060066&page=pages/health-code/health-code?siteid=2";
    // },

    getnews1 (item) {
      this.active = 1;
      sessionStorage.setItem("num", this.active);
      window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html`;
    },
    getnews2 (item) {
      this.active = 1;
      sessionStorage.setItem("num", this.active);
      window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html`;
    },
    getdetail (item) {
      window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html`;
    },
    handletophone (num) {
      window.location.href = `tel:${num}`;
    },
    handletoDetail (item) {
      this.active = 1;
      sessionStorage.setItem("num", this.active);
      this.$router.push({
        path: "/notify",
        query: {
          id: item.jumpUrl,
          name: item.jumpName.split("-")[1],
        },
      });
    },
    getmore (fyzclist) {
      this.$router.push({
        path: "/more",
        query: {
          news: JSON.stringify(fyzclist),
          name: "防疫资讯",
        },
      });
    },
    getmore1 (fgfczclist) {
      this.active = 1;
      sessionStorage.setItem("num", this.active);
      this.$router.push({
        path: "/more",
        query: {
          news: JSON.stringify(fgfczclist),
          name: "复工复产政策",
        },
      });
    },
    getmore2 (fgfczclist) {
      this.active = 1;
      sessionStorage.setItem("num", this.active);
      this.$router.push({
        path: "/more",
        query: {
          news: JSON.stringify(fgfczclist),
          name: "投资项目信息",
        },
      });
    },
    // fyfw(index, url) {
    //   console.log(index, url);
    //   if (index === 0) {
    //     window.location.href = url;
    //   } else if (index === 1) {
    //     location.href = `alipays://platformapi/startapp?appId=2019011763060066&page=pages/journey-webview/journey-webview`;
    //     // `https://ds.alipay.com/?scheme=` +
    //     // encodeURIComponent(
    //     // `alipays://platformapi/startapp?appId=2019011763060066&page=pages/journey-webview/journey-webview`;
    //     // );
    //   } else if (index === 2) {
    //     window.location.href = url;
    //   } else if (index === 3) {
    //     window.location.href = url;
    //   }
    // },
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
  background: #f4f7ff;

  // background-color: #ffffff;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 260px;
  }
  .ck-content {
    /deep/ .van-tabs__nav--card {
      width: 200px;
      height: 35px;
      position: absolute;
      top: 6px;
      left: 71.5px;
      z-index: 999;
      border: 1px solid #7ab0ff;
      border-radius: 17.5px;
      overflow: hidden;
      background: #ffffff;
    }
    /deep/ .van-tabs__nav--card .van-tab {
      border: 0;
      font-family: PingFangSC-Semibold;
      font-size: 15px;
      color: #333333;
      font-weight: 600;
      z-index: 999;
      overflow: hidden;
    }
    /deep/ .van-tab.van-tab--active {
      background-image: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
      // overflow: hidden;
      color: #ffffff;
    }
    /deep/ .van-tabs__content {
      position: absolute;
      width: 100%;
      background: #f4f7ff;
      // height: 100%;
      top: -14px;
      border-radius: 14px 14px 0 0;
    }

    .healthcode {
      width: 93.6%;
      display: block;
      position: relative;
      margin-left: 3.2%;
      margin-right: 3.2%;
      top: 77.5px;
      border-radius: 8px;
      img {
        border-radius: 8px;

        width: 100%;
      }
    }
    .fgfcfw {
      // height: 405.5px;
      width: 93.4%;
      margin-left: 3.2%;
      margin-right: 3.2%;
      background: #ffffff;
      box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
      margin-top: 80px;
      border-radius: 8px;
      padding-bottom: 12px;
      position: relative;
      .title1 {
        // width: 125px;
        // height: 35px;
        // display: block;
        // position: absolute;
        // top: -20px;
        // left: -5.5px;
        // font-family: PingFangSC-Semibold;
        // font-size: 17px;
        // color: #ffffff;
        // letter-spacing: 0.2525px;
        // text-align: center;
        // text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        // font-weight: 600;
        // background-image: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
        // box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.4);
        // line-height: 35px;
        // // border-radius: 8px;
        // border-radius: 35px 35px 35px 0px;
        display: block;
        width: 140px;
        height: 50px;
        display: block;
        position: absolute;
        top: -17.5px;
        left: -10.5px;
        font-family: PingFangSC-Semibold;
        font-size: 17px;
        color: #ffffff;
        letter-spacing: 0.2525px;
        text-align: center;
        text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        font-weight: 600;
        background-image: url(.././assets/images/title.png);
        background-repeat: no-repeat;
        background-size: 135px 45px;
        line-height: 43px;
        // margin-right: 6px;
      }
      .box1 {
        padding-top: 37.5px;
        .fgfc {
          width: 87.2%;
          margin-left: 3.2%;
          margin-right: 3.2%;
          height: 60px;
          background: #ecf1fd;
          border-radius: 8px;
          margin-bottom: 12px;

          img {
            width: 327px;
            height: 60px;
            background: #ecf1fd;
            border-radius: 8px;
          }
        }
      }
    }
    .zczx {
      width: 93.4%;
      margin-left: 3.2%;
      margin-right: 3.2%;
      background: #ffffff;
      box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
      margin-top: 45px;
      border-radius: 0 0 8px 8px;
      position: relative;
      .title2 {
        // width: 125px;
        // height: 35px;
        // display: block;
        // position: absolute;
        // top: -32px;
        // left: -5.5px;
        // color: #ffffff;
        // font-family: PingFangSC-Semibold;
        // font-size: 17px;
        // letter-spacing: 0.2525px;
        // text-align: center;
        // text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        // font-weight: 600;
        // background-image: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
        // box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.4);
        // line-height: 35px;
        // border-radius: 35px 35px 35px 0px;
        // border-radius: 8px;
        display: block;
        width: 140px;
        height: 50px;
        display: block;
        position: absolute;
        top: -32px;
        left: -10.5px;
        font-family: PingFangSC-Semibold;
        font-size: 17px;
        color: #ffffff;
        letter-spacing: 0.2525px;
        text-align: center;
        text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        font-weight: 600;
        background-image: url(.././assets/images/title.png);
        background-repeat: no-repeat;
        background-size: 130px 45px;
        line-height: 43px;

        z-index: 999;
      }

      .box2 {
        margin-bottom: 15px;
        /deep/ .van-tabs__wrap {
          border-radius: 14px;
          border-bottom: 1px solid #ecf1fd;
          overflow: hidden;
          position: relative;
          top: -10px;
          height: 51px;
        }
        /deep/ .van-tabs__content {
          // margin-top: 12px;
          position: relative;
          padding: 0;
          border-radius: 14px;
          width: 100%;
          top: -6px;
        }
        /deep/ .van-tab,
        .van-tab--active {
          background-image: none;
          color: #3c83ff;
          overflow: hidden;
        }

        .more {
          display: block;
          font-family: PingFangSC-Regular;
          font-size: 15px;
          color: #3f77f8;
          font-weight: 400;
          padding-top: 12px;
          padding-bottom: 6px;
          text-align: center;
          // border-bottom: 1px solid #ecf1fd;
        }
        .fgfczc,
        .tzxmxx {
          background: #ffffff;

          div {
            display: flex;
            flex-direction: column;
            width: 87%;
            margin-left: 6.4%;
            margin-right: 6.4%;
            border-bottom: 1px solid #ecf1fd;
            background: #ffffff;
            span:nth-of-type(1) {
              font-family: PingFangSC-Semibold;
              font-size: 15px;
              color: #333333;
              text-align: justify;
              font-weight: 600;
            }
            span:nth-of-type(2) {
              font-family: PingFangSC-Regular;
              font-size: 13px;
              color: #999999;
              font-weight: 400;
              padding-top: 11px;
            }
            span:nth-of-type(3) {
              font-family: PingFangSC-Regular;
              font-size: 13px;
              color: #999999;
              font-weight: 400;
              padding-bottom: 16px;
            }
          }
        }
      }
    }
    .fyfw {
      // height: 225.5px;
      width: 93.4%;
      margin-left: 3.2%;
      margin-right: 3.2%;
      background: #ffffff;
      box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
      margin-top: 125px;
      border-radius: 8px;
      padding-bottom: 15px;
      position: relative;
      .title1 {
        display: block;
        width: 140px;
        height: 50px;
        display: block;
        position: absolute;
        top: -17.5px;
        left: -10.5px;
        font-family: PingFangSC-Semibold;
        font-size: 17px;
        color: #ffffff;
        letter-spacing: 0.2525px;
        text-align: center;
        text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        font-weight: 600;
        background-image: url(.././assets/images/title.png);
        background-repeat: no-repeat;
        background-size: 130px 45px;
        line-height: 43px;
      }
      .box {
        display: flex;
        justify-content: space-around;
        margin: 0 10px;
        flex-wrap: wrap;
        padding-top: 25.5px;
        .fw {
          margin-top: 12px;
          text-align: center;
          width: 157.5px;
          height: 48px;
          .fwwz1 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #4867a5;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            background: #ecf1fd;
            border-radius: 8px;
          }
          .fwwz2 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #ac6a02;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            border-radius: 8px;
            background: #fdf6e7;
          }
          .fwwz3 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #6150a4;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            border-radius: 8px;
            background: #f1f1fe;
          }
          .fwwz4 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #985540;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            border-radius: 8px;
            background: #fdf1eb;
          }
          .fwwz5 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #ac6a02;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            border-radius: 8px;
            background: #fdf6e7;
          }
          .fwwz6 {
            display: block;
            font-family: PingFangSC-Semibold;
            font-size: 15px;
            color: #4867a5;
            letter-spacing: 0;
            text-align: center;
            font-weight: 600;
            line-height: 48px;
            border-radius: 8px;
            background: #ecf1fd;
          }
        }
      }
    }
    .fyrx {
      width: 93.4%;
      margin-left: 3.2%;
      margin-right: 3.2%;
      height: 157.5px;
      background: #ffffff;
      box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
      margin-left: 12px;
      margin-top: 45px;
      border-radius: 8px;
      position: relative;
      .title2 {
        // width: 125px;
        // height: 35px;
        // display: block;
        // position: absolute;
        // top: -19px;
        // left: -5.5px;
        // font-family: PingFangSC-Semibold;
        // font-size: 17px;
        // color: #ffffff;
        // letter-spacing: 0.2525px;
        // text-align: center;
        // text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        // font-weight: 600;
        // background-image: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
        // box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.4);
        // line-height: 35px;
        // border-radius: 35px 35px 35px 0px;
        // // border-radius: 8px;

        display: block;
        width: 140px;
        height: 50px;
        display: block;
        position: absolute;
        top: -17.5px;
        left: -10.5px;
        font-family: PingFangSC-Semibold;
        font-size: 17px;
        color: #ffffff;
        letter-spacing: 0.2525px;
        text-align: center;
        text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        font-weight: 600;
        background-image: url(.././assets/images/title.png);
        background-repeat: no-repeat;
        background-size: 130px 45px;
        line-height: 43px;
      }
      .box2 {
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        padding-top: 25.5px;
        height: 200px;
        // padding-bottom: 30px;
        // margin-bottom: 30px;

        .rx {
          display: flex;
          overflow: scroll;
          /deep/ .phone {
            width: 280px;
            height: 100px;
            border-radius: 8px;
            margin-left: 12px;
            background: url("https://zwfw.gansu.gov.cn/jmportal_files/web/site/info/c2c7feeca1f24334b68bf5ff1bfa4161.png");
            background-size: cover;
            div:nth-of-type(1) {
              font-family: PingFangSC-Semibold;
              font-size: 15px;
              color: #333333;
              letter-spacing: 0;
              font-weight: 600;
              word-break: keep-all;
              width: 280px;
              white-space: nowrap;
              padding-left: 20px;
              padding-top: 16px;
            }
            div:nth-of-type(2) {
              font-family: PingFangSC-Semibold;
              font-size: 20px;
              color: #333333;
              letter-spacing: 0;
              font-weight: 600;
              word-break: keep-all;
              width: 280px;
              white-space: nowrap;
              padding-left: 20px;
              padding-top: 16px;
            }
            div:nth-of-type(3) {
              width: 87.5px;
              height: 24px;
              background: #ffffff;
              border-radius: 12px;
              text-align: center;
              font-family: PingFangSC-Regular;
              font-size: 13px;
              color: #3f77f8;
              letter-spacing: 0;
              font-weight: 400;
              position: relative;
              left: 180px;
              top: -10px;
              display: flex;
              align-items: center;
              img {
                width: 13px;
                height: 13px;
                // background: #3f77f8;
                margin-left: 13.5px;
              }
              span {
                line-height: 24px;
              }
            }
          }
        }
      }
    }
    .fyzx {
      width: 93.4%;
      margin-left: 3.2%;
      margin-right: 3.2%;
      // height: 157.5px;
      background: #ffffff;
      box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
      margin-left: 12px;
      margin-top: 45px;
      border-radius: 8px;
      margin-bottom: 20px;
      position: relative;
      .title3 {
        // width: 125px;
        // // height: 35px;
        // display: block;
        // position: absolute;
        // top: -20px;
        // left: -5.5px;
        // font-family: PingFangSC-Semibold;
        // font-size: 17px;
        // color: #ffffff;
        // letter-spacing: 0.2525px;
        // text-align: center;
        // text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        // font-weight: 600;
        // background-image: linear-gradient(270deg, #a4cfff 0%, #3c83ff 100%);
        // box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.4);
        // line-height: 35px;
        // border-radius: 35px 35px 35px 0px;
        // border-radius: 8px;

        display: block;
        width: 140px;
        height: 50px;
        display: block;
        position: absolute;
        top: -17.5px;
        left: -10.5px;
        font-family: PingFangSC-Semibold;
        font-size: 17px;
        color: #ffffff;
        letter-spacing: 0.2525px;
        text-align: center;
        text-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
        font-weight: 600;
        background-image: url(.././assets/images/title.png);
        background-repeat: no-repeat;
        background-size: 130px 45px;
        line-height: 43px;
      }
      .box3 {
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        padding-top: 25.5px;
        // height: 200px;
        .zx {
          width: 98%;
          // height: 70px;
          background: #ffffff;
          display: flex;
          // justify-content: space-around;
          padding-left: 12px;
          margin-bottom: 12px;
          img {
            width: 70px;
            height: 52.5px;
            border-radius: 5px;
          }
          div {
            display: flex;
            flex-direction: column;
            padding-left: 12px;
            border-bottom: 1px solid #ecf1fd;

            span:nth-of-type(1) {
              font-family: PingFangSC-Semibold;
              font-size: 15px;
              color: #333333;
              text-align: justify;
              font-weight: 600;
              // margin-left: 12px;
              width: 245px;
            }
            span:nth-of-type(2) {
              font-family: PingFangSC-Regular;
              font-size: 13px;
              color: #999999;
              font-weight: 400;
              padding: 11px 0;
              // margin-left: 12px;
            }
          }
        }
        .more {
          // width: 70px;
          // height: 21px;
          font-family: PingFangSC-Regular;
          font-size: 15px;
          color: #3f77f8;
          font-weight: 400;
          padding-bottom: 12px;
        }
      }
    }
  }
}
</style>
