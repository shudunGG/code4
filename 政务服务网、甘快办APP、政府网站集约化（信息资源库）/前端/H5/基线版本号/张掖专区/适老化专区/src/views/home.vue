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
      <img class="background" src=".././assets/images/oldman.png" alt="" />
      <div class="ck-content">
        <div class="cyfwtj">
          <div class="title">常用服务推荐</div>
          <div class="box">
            <div class="model" v-for="(item, index) in cyfwtjlist" :key="index">
              <a @click="getFu(item)">
                <img :src="item.iconPath" alt="" />
                <span>{{ item.title }}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="bjfwzq">
          <div class="title">便捷服务专区</div>
          <div class="box">
            <img class="top" src=".././assets/images/shbz.png" alt="" />
            <div class="nei-title">社会保障</div>
            <div
              class="model"
              v-for="(item, index) in bjfwzqlist"
              :key="index"
              v-if="index < 2"
            >
              <a @click="getFu(item)">
                <div>
                  {{ item.title }}
                </div>
                <img src=".././assets/images/tz.png" alt="" />
              </a>
            </div>
            <div class="more" @click="getmore(a)">查看更多</div>
          </div>
        </div>

        <div class="bjfwzq">
          <!-- <div class="title">便捷缴费</div> -->
          <div class="box">
            <img class="top" src=".././assets/images/bjjf.png" alt="" />
            <div class="nei-title">便捷缴费</div>
            <div class="model" v-for="(item, index) in bjjflist" :key="index">
              <a @click="getFu(item)">
                <div>
                  {{ item.title }}
                </div>
                <img src=".././assets/images/tz.png" alt="" />
              </a>
            </div>

            <div class="more" @click="getmore1(b)">查看更多</div>
          </div>
        </div>

        <div class="bjfwzq">
          <div class="box">
            <img class="top" src=".././assets/images/gjj.png" alt="" />
            <div class="nei-title">公积金</div>
            <div class="model" v-for="(item, index) in gjjlist" :key="index">
              <a @click="getFu(item)">
                <div>
                  {{ item.title }}
                </div>
                <img src=".././assets/images/tz.png" alt="" />
              </a>
            </div>
            <div class="more" @click="getmore2(c)">查看更多</div>
          </div>
        </div>

        <div class="bjfwzq">
          <div class="box">
            <img class="top" src=".././assets/images/jtcx.png" alt="" />
            <div class="nei-title">交通出行</div>
            <div class="model" v-for="(item, index) in jtcxlist" :key="index">
              <a @click="getFu(item)">
                <div>
                  {{ item.title }}
                </div>
                <img src=".././assets/images/tz.png" alt="" />
              </a>
            </div>
            <div class="more" @click="getmore3(d)">查看更多</div>
          </div>
        </div>

        <div class="jkkp">
          <div class="nei-title">健康科普</div>
          <div class="box">
            <div
              class="model"
              v-for="(item, index) in jkkplist"
              :key="index"
              v-if="index < 3"
            >
              <a
                :href="
                  item.url ||
                  `${baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html`
                "
              >
                <img
                  :src="`${baseUrl.Url}/jmportal_files/web/site533629ca23dd4c63954c69a637971ac2/info/241b930a5a3347898ebd0196ba618db5/202201/28e3594d68f2469098482f9cfe423aca/6f92de16cf9940e7b681ad7ff4cd7161.jpg`"
                  alt=""
                />
                <div class="text">
                  <div>{{ item.title }}</div>
                  <div>{{ item.source }}</div>
                </div>
              </a>
            </div>

            <div class="more" @click="germorenews(jkkplist)">查看更多</div>
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
import { HTTP } from '../tools/request'
import { getUserInfo } from '../tools/getUserInfo'
import { baseUrl } from '../HTTP'

export default {
  name: 'home',
  data() {
    return {
      baseUrl,
      isShow: false,
      cyfwtjlist: [],
      bjfwzqlist: [],
      bjjflist: [],
      gjjlist: [],
      jtcxlist: [],
      jkkplist: [],
      mask: '',
      a: [],
      b: [],
      c: [],
      d: [],
      token: '',
      userType:''
    }
  },
  created() {
    getUserInfo().then((res) => {
      this.token = res.data.tokenNo
      this.userType = res.data.userType

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
          that.mask = JSON.parse(
            res.data
          ).data.lists[0].contentConfigs[0].iconPath

          that.a = JSON.parse(res.data).data.lists[2]
          that.b = JSON.parse(res.data).data.lists[3]
          that.c = JSON.parse(res.data).data.lists[4]
          that.d = JSON.parse(res.data).data.lists[5]
          that.cyfwtjlist = JSON.parse(
            res.data
          ).data.lists[1].contentConfigs.splice(
            1,
            JSON.parse(res.data).data.lists[1].contentConfigs.length - 1
          )
          that.bjfwzqlist = JSON.parse(
            res.data
          ).data.lists[2].contentConfigs.splice(
            1,
            JSON.parse(res.data).data.lists[2].contentConfigs.length - 2
          )
          that.bjjflist = JSON.parse(
            res.data
          ).data.lists[3].contentConfigs.splice(
            1,
            JSON.parse(res.data).data.lists[3].contentConfigs.length - 2
          )
          that.gjjlist = JSON.parse(
            res.data
          ).data.lists[4].contentConfigs.splice(
            1,
            JSON.parse(res.data).data.lists[4].contentConfigs.length - 2
          )
          that.jtcxlist = JSON.parse(
            res.data
          ).data.lists[5].contentConfigs.splice(
            1,
            JSON.parse(res.data).data.lists[5].contentConfigs.length - 2
          )
          that.jkkplist = JSON.parse(res.data).data.lists[6].infos
          that.isShow = true
        })
        .catch(() => {
          that.isShow = true
        })
    })
  },
  beforeRouteEnter(to, form, next) {
    to.meta.keepAlive = true
    next()
  },
  methods: {
    getmore(item) {
      let id = item.contentConfigs[item.contentConfigs.length - 1].jumpUrl
      this.$router.push({
        path: '/notify',
        query: {
          id: id,
          name: '社会保障',
        },
      })
    },
    getmore1(item) {
      let id = item.contentConfigs[item.contentConfigs.length - 1].jumpUrl
      this.$router.push({
        path: '/notify',
        query: {
          id: id,
          name: '便捷缴费',
        },
      })
    },
    getmore2(item) {
      let id = item.contentConfigs[item.contentConfigs.length - 1].jumpUrl

      this.$router.push({
        path: '/notify',
        query: {
          id: id,
          name: '公积金',
        },
      })
    },
    getmore3(item) {
      let id = item.contentConfigs[item.contentConfigs.length - 1].jumpUrl

      this.$router.push({
        path: '/notify',
        query: {
          id: id,
          name: '交通出行',
        },
      })
    },
    germorenews(item) {
      this.$router.push({
        path: '/notify',
        query: {
          list: JSON.stringify(item),
          name: '健康科普',
          mask: this.mask,
          pic: `${this.baseUrl.Url}/jmportal_files/web/site533629ca23dd4c63954c69a637971ac2/info/241b930a5a3347898ebd0196ba618db5/202201/28e3594d68f2469098482f9cfe423aca/6f92de16cf9940e7b681ad7ff4cd7161.jpg`,
        },
      })
    },
    isWeiXin() {
      var ua = window.navigator.userAgent.toLowerCase()
      if (ua.includes('micromessenger')) {
        return true
      } else {
        return false
      }
    },
    getFu (item) {
      // console.log(item);
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
  background: #f5f5f5;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 180px;
  }
  .ck-content {
    .cyfwtj {
      // margin-bottom: 30px;
      .title {
        // width: 100%;
        height: 39px;
        font-family: AlibabaPuHuiTi_2_105_Heavy;
        font-size: 28px;
        color: #8a5144;
        font-weight: 900;
        padding-left: 16px;
        padding-bottom: 12px;
        padding-top: 30px;
      }
      .box {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        margin: 0 12px;
        // padding-top: 12px;
        .model {
          position: relative;
          // margin-top: 12px;
          padding-top: 12px;
          img {
            width: 165.5px;
            height: 165.5px;
            // opacity: 0.8;
          }
          span {
            width: 100px;
            position: absolute;
            font-family: PingFangSC-Semibold;
            font-size: 20px;
            color: #8a5144;
            text-align: center;
            font-weight: 600;
            top: 44px;
            left: 35px;
          }
        }
      }
    }
    .bjfwzq {
      .title {
        // width: 100%;
        height: 39px;
        font-family: AlibabaPuHuiTi_2_105_Heavy;
        font-size: 28px;
        color: #8a5144;
        font-weight: 900;
        padding-left: 16px;
        padding-top: 30px;
        padding-bottom: 12px;
      }
      .box {
        position: relative;
        background: #fff;
        width: 92.6%;
        margin: 0 3.2%;
        border-radius: 8px;
        .top {
          width: 100%;
          // margin: 0 3.2%;
          height: 70px;
          // opacity: 0;
          border-radius: 8px 8px 0 0;
        }
        .nei-title {
          font-family: PingFangSC-Semibold;
          font-size: 24px;
          color: #ffffff;
          letter-spacing: 0;
          font-weight: 600;
          position: absolute;
          top: 20px;
          left: 32px;
        }
        .model {
          a {
            display: flex;
            justify-content: space-between;
            margin-right: 16px;
            margin-left: 16px;
            border-bottom: 1px solid #d8d8d8;
            div {
              font-family: PingFangSC-Medium;
              font-size: 20px;
              color: #333333;
              letter-spacing: 0;
              font-weight: 500;
              padding-top: 20px;
              padding-bottom: 20px;
            }
            img {
              width: 16px;
              height: 16px;
              margin-top: 26px;
            }
          }
        }
        .more {
          font-family: PingFangSC-Regular;
          font-size: 17px;
          color: #8a5144;
          font-weight: 400;
          text-align: center;
          padding-top: 12px;
          margin-bottom: 30px;
          padding-bottom: 12px;
        }
      }
    }
    .jkkp {
      .nei-title {
        // width: 100%;
        height: 39px;
        font-family: AlibabaPuHuiTi_2_105_Heavy;
        font-size: 28px;
        color: #8a5144;
        font-weight: 900;
        padding-left: 16px;
        padding-bottom: 12px;
      }
      .box {
        background: #fff;
        padding-top: 12px;
        width: 92.6%;
        margin: 0 3.2%;
        border-radius: 8px;
        .model {
          // width: 92.6%;
          padding: 0 16px;
          a {
            display: flex;

            img {
              width: 70px;
              height: 52.5px;
              padding-top: 12px;
            }
            .text {
              border-bottom: 1px solid #eeeeee;
              padding-left: 12px;
              padding-top: 12px;
              div:nth-of-type(1) {
                font-family: PingFangSC-Semibold;
                font-size: 20px;
                color: #333333;
                text-align: justify;
                font-weight: 600;
                margin-bottom: 12px;
              }
              div:nth-of-type(2) {
                font-family: PingFangSC-Regular;
                font-size: 17px;
                color: #999999;
                font-weight: 400;
                margin-bottom: 11.5px;
              }
            }
          }
        }
        .more {
          font-family: PingFangSC-Regular;
          font-size: 17px;
          color: #8a5144;
          font-weight: 400;
          text-align: center;
          padding-top: 12px;
          padding-bottom: 12px;
        }
      }
    }
  }
}
</style>
