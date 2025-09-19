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
      <img class="background" src=".././assets/images/msk.png" alt="" />
      <div class="ck-content">
        <img class="zuo" src=".././assets/images/zuo15.png" alt="" />
        <img class="you" src=".././assets/images/you16.png" alt="" />

        <van-tabs type="card">
          <van-tab title="个人生命周期">
            <div class="gr">
              <div class="box">
                <div
                  class="model"
                  v-for="(item, index) in grsmzq"
                  :key="index"
                  @click="more(item, index)"
                >
                  <img :src="item.iconPath" alt="" />

                  <div class="posi">
                    <div class="up">
                      <span :class="title[index]">{{ item.title }}</span>
                      <div :class="text[index]">
                        {{ item.descriptionName }}
                      </div>
                    </div>
                    <div :class="down[index]">点击进入</div>
                  </div>
                </div>
              </div>
            </div>
          </van-tab>

          <van-tab title="企业生命周期">
            <div class="gr">
              <div class="box">
                <div
                  class="model"
                  v-for="(item, index) in frsmzq"
                  :key="index"
                  @click="more(item, index)"
                >
                  <img :src="item.iconPath" alt="" />
                  <div class="posi">
                    <div class="up">
                      <span :class="title1[index]">{{ item.title }}</span>
                      <div :class="text1[index]">
                        {{ item.descriptionName }}
                      </div>
                    </div>
                    <div :class="down1[index]">点击进入</div>
                  </div>
                </div>
              </div>
            </div>
          </van-tab>
        </van-tabs>
      </div>
    </div>

    <div v-show="!isShow" style="padding-top: 200px">
      <van-loading type="spinner" size="50px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant'
import { HTTP } from '../tools/request'
export default {
  name: 'home',
  data() {
    return {
      grsmzq: [],
      frsmzq: [],
      isShow: false,
      text: [
        'text0',
        'text1',
        'text2',
        'text3',
        'text4',
        'text5',
        'text6',
        'text7',
      ],
      down: [
        'down0',
        'down1',
        'down2',
        'down3',
        'down4',
        'down5',
        'down6',
        'down7',
      ],
      title: [
        'title0',
        'title1',
        'title2',
        'title3',
        'title4',
        'title5',
        'title6',
        'title7',
      ],
      text1: [
        'text0',
        'text1',
        'text2',
        'text3',
        'text8',
        'text5',
        'text9',
        'text7',
      ],
      down1: [
        'down0',
        'down1',
        'down2',
        'down3',
        'down8',
        'down5',
        'down9',
        'down7',
      ],
      title1: [
        'title0',
        'title1',
        'title2',
        'title3',
        'title8',
        'title5',
        'title9',
        'title7',
      ],
    }
  },
  created() {
    let that = this
    const timestamp = new Date().valueOf()
    let param = {
      app_id: 'jmportalnzjk',
      version: '1.0',
      charset: 'UTF-8',
      origin: '1',
      header: '',
      timestamp: timestamp,
      interface_id: 'showcomponent',
      biz_content: JSON.stringify({
        siteId: SiteId,
        mobileId: MobileId,
        colId: ColId,
      }),
    }
    HTTP.jpass(param)
      .then((res) => {
        console.log(JSON.parse(res.data), 'data')
        console.log(
          JSON.parse(res.data).data.lists[0].list[0].list[0].contentConfigs
        )
        console.log(
          JSON.parse(res.data).data.lists[0].list[1].list[0].contentConfigs
        )

        that.grsmzq = JSON.parse(
          res.data
        ).data.lists[0].list[0].list[0].contentConfigs
        that.frsmzq = JSON.parse(
          res.data
        ).data.lists[0].list[1].list[0].contentConfigs

        this.isShow = true
      })
      .catch(() => {
        this.isShow = true
        // Toast("系统繁忙");
      })
  },
  beforeRouteEnter(to, form, next) {
    to.meta.keepAlive = true
    next()
  },
  methods: {
    more(list, num) {
      console.log(list, num)
      console.log()
      this.$router.push({
        path: '/more',
        query: {
          name: list.title,
          id: list.jumpUrl,
        },
      })
    },
  },
}
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
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 153.5px;
  }
  .ck-content {
    position: relative;
    /deep/ .van-tabs__nav--card {
      width: 306px;
      padding: 0 40px 0;
      height: 44px;
      position: absolute;
      top: 15.5px;
      left: 16px;
      border: 0;
      border-radius: 22px;
      overflow: hidden;
      background: #ffffff;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    }
    /deep/ .van-tabs__nav--card .van-tab {
      border: 0;
      font-family: PingFangSC-Semibold;
      font-size: 13px;
      color: #333333;
      overflow: hidden;
    }
    /deep/ .van-tab.van-tab--active {
      background-color: #ffffff;
      color: #1d70e2;
      font-size: 17px;
    }
    /deep/ .van-tabs__content {
      background-color: #ffffff;
      width: 92.6%;
      margin: 0 3.2%;
      padding-top: 10px;
      border-radius: 8px;
    }
    .zuo {
      position: absolute;
      left: 52px;
      top: 33.5px;
      z-index: 999;
      width: 22px;
      height: 8px;
    }
    .you {
      position: absolute;
      right: 52px;
      z-index: 999;
      width: 22px;
      top: 33.5px;
      height: 8px;
    }
    .gr {
      .box {
        margin-top: 38px;
        .model {
          position: relative;
          margin-bottom: 12px;
          img {
            width: 92.6%;
            height: 90px;
            margin: 0 3.2%;
            display: block;
          }
          .posi {
            position: absolute;
            left: 30px;
            top: 15.5px;
            z-index: 999;
            // top: 0;
            .up {
              display: flex;
              text-align: bottom;
              span {
                font-family: 'PingFang SC';
                font-weight: 400;
                font-size: 17px;
                text-align: left;
                color: #5883c1;
              }
              .title1 {
                color: #b14d17;
              }
              .title2 {
                color: #5966cb;
              }
              .title4,
              .title7 {
                color: #a71f18;
              }
              .title6 {
                color: #f2a21a;
              }
              .title8 {
                color: #f2a21a;
              }
              .title9 {
                color: #5966cb;
              }
              div {
                font-family: 'PingFang SC';
                font-weight: 400;
                font-size: 13px;
                text-align: center;
                color: #5883c1;
                margin-top: 5.5px;
                margin-left: 8.5px;
              }
              .text1 {
                color: #b14d17;
              }
              .text2 {
                color: #5966cb;
              }
              .text4,
              .text7 {
                color: #a71f18;
              }
              .text6 {
                color: #f2a21a;
              }
              .text8 {
                color: #f2a21a;
              }
              .text9 {
                color: #5966cb;
              }
            }
            .down0,
            .down3,
            .down5 {
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
              color: #5883c1;
            }
            .down1 {
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
              color: #b14d17;
            }
            .down2 {
              color: #5966cb;
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
            }
            .down4,
            .down7 {
              color: #a71f18;
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
            }
            .down6 {
              color: #f2a21a;
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
            }
            .down8 {
              color: #f2a21a;
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
            }
            .down9 {
              color: #5966cb;
              width: 84px;
              height: 22px;
              font-size: 15px;
              background: #ffffff;
              border-radius: 12px;
              margin-top: 13.5px;
              text-align: center;
            }
          }
        }
      }
    }
  }
}
</style>
