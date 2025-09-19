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
      <img class="background" src=".././assets/images/bg.png" alt="" />

      <div class="ck-content">
        <div class="sntb">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>政策直达</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="tab">
            <van-tabs type="card">
              <van-tab title="政策文件">
                <div class="first">
                  <div
                    class="model"
                    v-for="(item, index) in zcwjlist"
                    v-if="index < 3"
                    :key="index"
                    @click="detailnews(item.iid, item.url)"
                  >
                    <div>
                      <div>{{ item.title }}</div>
                    </div>
                    <div class="text">
                      <span>{{ item.time }}</span>
                      <span>发文部门：{{ item.source }}</span>
                    </div>
                  </div>
                  <div class="more" @click="getmorenews()">
                    查看更多<img src=".././assets/images/xy.png" alt="" />
                  </div>
                </div>
              </van-tab>
              <van-tab title="营商动态">
                <div class="first">
                  <div
                    class="model"
                    v-for="(item, index) in zcgslist"
                    v-if="index < 3"
                    :key="index"
                    @click="detailnews(item.iid, item.url)"
                  >
                    <div>
                      <div>{{ item.title }}</div>
                    </div>
                    <div class="text">
                      <span>{{ item.time }}</span>
                      <span>发文部门：{{ item.source }}</span>
                    </div>
                  </div>
                  <div class="more" @click="getmorenews()">
                    查看更多<img src=".././assets/images/xy.png" alt="" />
                  </div>
                </div>
              </van-tab>
              <van-tab title="政策解读">
                <div class="first">
                  <div
                    class="model"
                    v-for="(item, index) in zcjdlist"
                    :key="index"
                    v-if="index < 3"
                    @click="detailnews(item.iid, item.url)"
                  >
                    <div>
                      <div>{{ item.title }}</div>
                    </div>
                    <div class="text">
                      <span>{{ item.time }}</span>
                      <span>发文部门：{{ item.source }}</span>
                    </div>
                  </div>
                  <div class="more" @click="getmorenews()">
                    查看更多<img src=".././assets/images/xy.png" alt="" />
                  </div>
                </div>
              </van-tab>
            </van-tabs>
          </div>
        </div>

        <div class="fwzdzq">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>服务专区直达</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="tab">
            <div class="box">
              <a
                @click="getFu(item)"
                class="model"
                v-for="(item, index) in fwzqzd"
                :key="index"
              >
                <img :src="item.iconPath" alt="" />
                <span>{{ item.title }}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="bszd">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>办事直达</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="tab">
            <div class="box">
              <div
                class="fw"
                v-for="(item, index) in bszd"
                :key="index"
                @click="getdatail(item.jumpUrl, item.title)"
              >
                <!-- <a :href="item.jumpUrl"
                  > -->
                <span :class="fwwz[index]">
                  {{ item.title }}
                </span>
                <!-- </a> -->
              </div>
            </div>
          </div>
        </div>

        <div class="hdzd">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>互动直达</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="tab">
            <div class="box">
              <div class="model" v-for="(item, index) in hdzd" :key="index">
                <img class="img" :src="item.iconPath" alt="" />
                <span>{{ item.title }}</span>
                <div>
                  <a @click="getFu(item)"
                    >{{ item.descriptionName
                    }}<img src=".././assets/images/tz2.png" alt=""
                  /></a>
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
import { Toast } from "vant";
import { HTTP } from "../tools/request";
import { getUserInfo } from "../tools/getUserInfo";
import { baseUrl } from "../HTTP";
export default {
  name: "home",
  data() {
    return {
      baseUrl,
      isShow: false,
      zcwjlist: [],
      zcgslist: [],
      zcjdlist: [],
      fwzqzd: [],
      bszd: [],
      hdzd: [],
      fwwz: [
        "fwwz1",
        "fwwz2",
        "fwwz3",
        "fwwz4",
        "fwwz5",
        "fwwz6",
        "fwwz3",
        "fwwz4",
      ],
      token: "",
      userType: "",
    };
  },
  created() {
    getUserInfo().then((res) => {
      this.token = res.data.tokenNo;
      this.userType = res.data.userType;

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
          console.log(JSON.parse(res.data).data, "data");
          that.zcwjlist = JSON.parse(
            res.data
          ).data.lists[1].list[0].list[0].infos;
          that.zcgslist = JSON.parse(
            res.data
          ).data.lists[1].list[1].list[0].infos;
          that.zcjdlist = JSON.parse(
            res.data
          ).data.lists[1].list[2].list[0].infos;
          that.fwzqzd = JSON.parse(res.data).data.lists[2].contentConfigs;
          that.bszd = JSON.parse(res.data).data.lists[3].contentConfigs;
          that.hdzd = JSON.parse(res.data).data.lists[4].contentConfigs;
          console.log(that.zcwjlist);
          that.isShow = true;
        })
        .catch(() => {
          Toast("系统繁忙");
        });
    });
  },
  beforeRouteEnter(to, form, next) {
    to.meta.keepAlive = true;
    next();
  },
  methods: {
    getmorenews() {
      this.$router.push("/more");
    },
    detailnews(iid, url) {
      console.log(url);
      if (url) {
        window.location.href = url;
      } else {
        window.location.href = `${
          this.baseUrl.Url
        }jmportal_files/resources/jmp/html/${iid}.html`;
      }
    },
    getdatail(id, name) {
      this.$router.push({
        path: "/notify",
        query: {
          id: id,
          name: name,
        },
      });
    },
    isWeiXin() {
      var ua = window.navigator.userAgent.toLowerCase();
      if (ua.includes("micromessenger")) {
        return true;
      } else {
        return false;
      }
    },
    getFu(item) {
      if (this.isWeiXin()) {
        // console.log(' 是来自微信内置浏览器')
        if (item.jumpUrl.indexOf("?") !== -1) {
          window.location.href =
            item.jumpUrl + `&token=${this.token}&userType=${this.userType}`;
        } else {
          window.location.href =
            item.jumpUrl + `?token=${this.token}&userType=${this.userType}`;
        }
      } else {
        window.location.href = item.jumpUrl;
        // console.log('不是来自微信内置浏览器')
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
  background: #2a8cff;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 200px;
  }
  .ck-content {
    .rmtj {
      .title {
        width: 42%;
        margin: 0 29%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        img {
          width: 23px;
          height: 7px;
        }
        span {
          font-family: PingFangSC-Semibold;
          font-size: 20px;
          color: #333333;
          letter-spacing: 0;
          text-align: center;
          font-weight: 600;
        }
      }

      .list {
        display: flex;
        flex-direction: column;
        .model {
          width: 92.6%;
          margin: 0 3.2%;
          display: flex;
          justify-content: space-between;
          border-bottom: 1px solid #d8d8d8;
          img {
            width: 8.35px;
            height: 10px;
            margin-top: 17px;
            margin-right: 2px;
          }
          span {
            font-family: PingFangSC-Regular;
            font-size: 17px;
            color: #333333;
            letter-spacing: 0.58px;
            text-align: left;
            font-weight: 400;
            line-height: 47.5px;
          }
        }
      }
    }
    .sntb {
      width: 93.6%;
      margin: 0 3.2%;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(42, 140, 255, 1);
      border-radius: 8px;
      position: relative;
      top: -60px;
      .title {
        width: 50%;
        margin: 0 25%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        transform: translateY(-10px);
        background: #fff;
        height: 35px;
        border-radius: 16px;
        box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.8);
        img {
          width: 23px;
          height: 7px;
        }
        span {
          font-family: PingFangSC-Semibold;
          font-size: 18px;
          color: #3f77f8;
          letter-spacing: 0.325px;
          text-align: center;
          font-weight: 600;
        }
      }
      .tab {
        /deep/.van-tabs__nav,
        /deep/.van-tabs__nav--card {
          width: 60%;
          margin: auto;
          height: 27px;
          border: 1px solid #3f77f8;
          border-radius: 20px;
          overflow: hidden;
        }
        /deep/.van-tab {
          font-family: PingFangSC-Regular;
          font-size: 14px;
          color: #333333;
          text-align: center;
          font-weight: 400;
          border: 0;
        }
        /deep/.van-tab--active {
          font-family: PingFangSC-Medium;
          font-size: 14px;
          color: #ffffff;
          text-align: center;
          font-weight: 500;
          background: #3f77f8;
          border: 0;
        }
        .first {
          .model {
            padding-top: 15px;
            display: flex;
            flex-direction: column;
            padding-left: 12.5px;
            position: relative;
            border-bottom: 0.5px solid #d7d8d9;
            padding-bottom: 15px;
            padding-right: 15px;

            .dian {
              position: absolute;
              left: -1.5px;
              top: 25px;
            }
            div:nth-of-type(1) {
              font-family: PingFangSC-Medium;
              font-size: 15px;
              color: #101f30;
              text-align: justify;
              line-height: 22px;
              font-weight: 500;
            }
            div:nth-of-type(2) {
              font-family: PingFangSC-Regular;
              font-size: 15px;
              color: #333333;
              line-height: 24px;
              font-weight: 400;
            }
            .text {
              padding-top: 12px;
              span {
                font-family: PingFangSC-Regular;
                font-size: 13px;
                color: #999999;
                line-height: 10px;
                font-weight: 400;
              }
              span:nth-of-type(2) {
                padding-left: 27px;
              }
            }
          }
          .more {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            font-family: PingFangSC-Regular;
            font-size: 15px;
            color: #3f77f8;
            font-weight: 400;
            img {
              width: 10.05px;
              height: 6.5;
            }
          }
        }
      }
    }
    .fwzdzq {
      width: 93.6%;
      margin: 0 3.2%;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(42, 140, 255, 1);
      border-radius: 8px;
      position: relative;
      top: -60px;
      .title {
        width: 50%;
        margin: 0 25%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        transform: translateY(-10px);
        background: #fff;
        height: 35px;
        border-radius: 16px;
        box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.8);
        img {
          width: 23px;
          height: 7px;
        }
        span {
          font-family: PingFangSC-Semibold;
          font-size: 18px;
          color: #3f77f8;
          letter-spacing: 0.325px;
          text-align: center;
          font-weight: 600;
        }
      }
      .tab {
        padding-bottom: 20px;
        .box {
          display: flex;
          justify-content: space-around;
          .model {
            display: flex;
            flex-direction: column;
            align-items: center;
            img {
              width: 40px;
              height: 40px;
            }
            span {
              font-family: PingFangSC-Regular;
              font-size: 15px;
              color: #333333;
              text-align: center;
              font-weight: 400;
              width: 93px;
            }
          }
        }
      }
    }
    .bszd {
      width: 93.6%;
      margin: 0 3.2%;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(42, 140, 255, 1);
      border-radius: 8px;
      position: relative;
      top: -60px;
      .title {
        width: 50%;
        margin: 0 25%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        transform: translateY(-10px);
        background: #fff;
        height: 35px;
        border-radius: 16px;
        box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.8);
        img {
          width: 23px;
          height: 7px;
        }
        span {
          font-family: PingFangSC-Semibold;
          font-size: 18px;
          color: #3f77f8;
          letter-spacing: 0.325px;
          text-align: center;
          font-weight: 600;
        }
      }
      .tab {
        padding-bottom: 25.5px;

        .box {
          display: flex;
          justify-content: space-around;
          flex-wrap: wrap;
          // padding-top: 25.5px;
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
    }
    .hdzd {
      width: 93.6%;
      margin: 0 3.2%;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(42, 140, 255, 1);
      border-radius: 8px;
      position: relative;
      top: -60px;
      .title {
        width: 50%;
        margin: 0 25%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        transform: translateY(-10px);
        background: #fff;
        height: 35px;
        border-radius: 16px;
        box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.8);
        img {
          width: 23px;
          height: 7px;
        }
        span {
          font-family: PingFangSC-Semibold;
          font-size: 18px;
          color: #3f77f8;
          letter-spacing: 0.325px;
          text-align: center;
          font-weight: 600;
        }
      }
      .tab {
        .box {
          display: flex;
          padding-bottom: 20px;
          .model {
            position: relative;
            .img {
              width: 152.5px;
              height: 114.5px;
              // position: absolute;
              margin-left: 14px;
            }
            span {
              position: absolute;
              width: 100px;
              left: 54px;
              top: 16px;
              font-family: PingFangSC-Medium;
              font-size: 17px;
              color: #4867a5;
              letter-spacing: 0;
              font-weight: 500;
            }
            div {
              position: absolute;
              top: 70px;
              left: 20px;
              a {
                font-family: PingFangSC-Regular;
                font-size: 11px;
                color: #3f77f8;
                font-weight: 400;
                display: inline-block;
                width: 100px;
                height: 32px;
                background: #ffffff;
                border-radius: 16px;
                display: flex;
                align-items: center;
                justify-content: space-around;
                img {
                  width: 15px;
                  height: 15px;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
