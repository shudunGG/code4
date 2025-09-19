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
      <img class="background" src=".././assets/images/banner.png" alt="" />

      <van-search
        v-model="value"
        show-action
        left-icon="none"
        placeholder="请输入您要办理的事项或查询的服务"
        @click="onSearch"
      >
        <template #action>
          <div @click="onSearch">搜索</div>
        </template>
      </van-search>

      <div class="ck-content">
        <div class="rmtj">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>热门推荐</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="box">
            <a
              @click="getFu(item)"
              class="inbox"
              v-for="(item, index) in rmtjbox"
              :key="index"
              ><img :src="item.iconPath" alt="" />
              <span>{{ item.title }}</span>
            </a>
          </div>
          <div class="list">
            <a
              @click="getFu(item)"
              v-for="(item, index) in rmtjlists"
              :key="index"
              class="model"
              ><span>{{ item.title }}</span>
              <img src=".././assets/images/tz.png" alt=""
            /></a>
          </div>
        </div>

        <div class="sntb">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>市内通办</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>
          <div class="tab">
            <van-tabs type="card">
              <van-tab title="个人办事服务">
                <div class="box">
                  <a
                    @click="getFu(item)"
                    :class="model[index]"
                    v-for="(item, index) in grbslists"
                    :key="index"
                    v-if="index < 6"
                  >
                    <span> {{ item.title }}</span>
                  </a>
                  <div class="more" @click="getmore">
                    <span> <a>查看更多主题</a> </span
                    ><img src=".././assets/images/xl.png" alt="" /><img
                      src=".././assets/images/hs.png"
                      alt=""
                    />
                  </div>
                </div>
              </van-tab>
              <van-tab title="法人办事服务">
                <div class="box">
                  <a
                    :href="item.jumpUrl"
                    :class="model[index]"
                    v-for="(item, index) in frlists"
                    :key="index"
                    v-if="index < 6"
                  >
                    <span> {{ item.title }}</span>
                  </a>
                  <div class="more" @click="getmore()">
                    <span><a>查看更多主题</a></span
                    ><img src=".././assets/images/xl.png" alt="" /><img
                      src=".././assets/images/hs.png"
                      alt=""
                    />
                  </div></div
              ></van-tab>
            </van-tabs>
          </div>
        </div>

        <div class="qyzwfwzq">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>区域政务服务专区</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>

          <div class="box">
            <a
              :href="item.jumpUrl"
              v-for="(item, index) in qyfwzqlists"
              :key="index"
            >
              <img :src="item.iconPath" alt="" />
              <span>{{ item.title }}</span>
            </a>
          </div>
        </div>

        <div class="hdjl">
          <div class="title">
            <img src=".././assets/images/zuo15.png" alt="" />
            <span>互动交流</span>
            <img src=".././assets/images/you16.png" alt="" />
          </div>

          <div
            :class="models[index]"
            v-for="(item, index) in hdjllists"
            :key="index"
          >
            <div>{{ item.title }}</div>
            <div>{{ item.descriptionName.split(':')[0] || '我要评价' }}</div>
            <a @click="getFu(item)"
              ><span :class="span[index]">{{
                item.descriptionName.split(':')[1] || '我要评价'
              }}</span>
              <img src=".././assets/images/tz2.png" alt=""
            /></a>
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
import { Toast } from 'vant'
import { HTTP } from '../tools/request'
import { getUserInfo } from '../tools/getUserInfo'

export default {
  name: 'home',
  data() {
    return {
      isShow: false,
      value: '',
      model: ['model1', 'model2', 'model3', 'model4', 'model5', 'model6'],
      models: ['models1', 'models2', 'models3'],
      span: ['span1', 'span2', 'span3'],
      rmtjbox: [],
      rmtjlists: [],
      grbslists: [],
      frlists: [],
      // grbslistsmore: "",
      // frlistsmore: "",
      qyfwzqlists: [],
      hdjllists: [],
      token: '',
    }
  },
  beforeRouteEnter(to, form, next) {
    to.meta.keepAlive = true
    next()
  },
  created() {
    getUserInfo().then((res) => {
      this.token = res.data.tokenNo
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
          that.rmtjbox = JSON.parse(res.data).data.lists[1].contentConfigs
          that.rmtjlists = JSON.parse(res.data).data.lists[2].contentConfigs

          that.grbslists = JSON.parse(
            res.data
          ).data.lists[3].list[0].list[0].contentConfigs

          that.frlists = JSON.parse(
            res.data
          ).data.lists[3].list[1].list[0].contentConfigs
          that.qyfwzqlists = JSON.parse(res.data).data.lists[4].contentConfigs
          that.hdjllists = JSON.parse(res.data).data.lists[5].contentConfigs
          that.isShow = true
        })
        .catch(() => {
          that.isShow = true
        })
    })
  },
  methods: {
    onSearch() {
      let that = this
      that.$router.push({
        path: '/notify',
      })
    },
    getmore() {
      this.$router.push({
        path: '/notify',
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
    getFu(item) {
      if (this.isWeiXin()) {
        // console.log(' 是来自微信内置浏览器')
        if (item.jumpUrl.indexOf('?') !== -1) {
          window.location.href = item.jumpUrl + `&token=${this.token}`
        } else {
          window.location.href = item.jumpUrl + `?token=${this.token}`
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
  background: #f4f7ff;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 240px;
  }
  .van-search {
    width: 92.6%;
    margin: 0 3.2%;
    opacity: 0.8;
    background: #ffffff;
    height: 40px;
    padding: 0;
    position: absolute;
    top: 142px;

    font-family: PingFangSC-Regular;
    font-size: 13px;
    color: #666666;
    letter-spacing: 0;
    font-weight: 400;
  }
  ::v-deep .van-field__left-icon {
    display: none;
  }
  ::v-deep.van-search__action {
    width: 50px;
    height: 40px;
    font-family: PingFangSC-Medium;
    font-size: 13px;
    color: #ffffff;
    letter-spacing: -0.325px;
    font-weight: 500;
    background: #f03938;
    padding: 0;
    // margin: 2px 0 0 0;
    text-align: center;

    div {
      padding-top: 2px;
    }
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
      .box {
        display: flex;
        justify-content: space-between;
        width: 90%;
        padding-top: 20px;
        padding-left: 5px;
        padding-right: 5px;
        border-bottom: 1px solid #d8d8d8;
        margin: 0 3.2%;
        padding-bottom: 20px;
        .inbox {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;

          img {
            width: 35px;
            height: 35px;
            padding-bottom: 8px;
          }
          span {
            font-family: PingFangSC-Regular;
            font-size: 15px;
            color: #333333;
            text-align: center;
            font-weight: 400;
          }
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
      .tab {
        margin-top: 20px;
        ::v-deep.van-tabs__nav,
        ::v-deep.van-tabs__nav--card {
          // width: 230px;
          width: 61%;
          margin: 0 19.5%;
          height: 27px;
          border: 1px solid #3f77f8;
          border-radius: 20px;
          overflow: hidden;
        }
        ::v-deep.van-tab {
          font-family: PingFangSC-Regular;
          font-size: 14px;
          color: #333333;
          text-align: center;
          font-weight: 400;
          border: 0;
        }
        ::v-deep.van-tab--active {
          font-family: PingFangSC-Medium;
          font-size: 14px;
          color: #ffffff;
          text-align: center;
          font-weight: 500;
          background: #3f77f8;
          border: 0;
        }
        .box {
          width: 93.6%;
          margin: 20px 3.2% 0;
          display: flex;
          justify-content: space-between;
          flex-wrap: wrap;
          a {
            width: 48.5%;
            height: 48px;
            background: #ecf1fd;
            border-radius: 8px;
            font-family: PingFangSC-Regular;
            font-size: 15px;
            color: #4867a5;
            letter-spacing: 0;
            text-align: center;
            font-weight: 400;
            margin-bottom: 12px;
            span {
              display: block;
              margin-top: 12px;
            }
          }
          .model2 {
            background: #fdf6e7;
            color: #ac6a02;
          }
          .model3 {
            background: #f1f1fe;
            color: #6150a4;
          }
          .model4 {
            background: #fdf1eb;
            color: #985540;
          }
          .model5 {
            background: #fdf6e7;
            color: #ac6a02;
          }
          .more {
            width: 40%;
            margin: 0 30%;
            display: flex;
            padding-bottom: 12px;
            a {
              font-family: PingFangSC-Regular;
              font-size: 15px;
              color: #3f77f8;
              font-weight: 400;
              background: none;
            }
            img {
              width: 12px;
              height: 12px;
              padding: 6px 0 0 6px;
            }
          }
        }
      }
    }
    .qyzwfwzq {
      .title {
        width: 60%;
        margin: 15px 20%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        // margin-top: 20px;
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
      .box {
        width: 93.6%;
        margin: 0 3.2%;
        border-radius: 8px;
        a {
          display: block;
          margin-bottom: 12px;
          background: #ffffff;
          border-radius: 8px;
          box-shadow: 0px 1px 5px 0px rgba(153, 153, 153, 0.3);
          img {
            width: 100%;
            border-radius: 8px 8px 0 0;
          }
          span {
            display: block;
            font-family: PingFangSC-Medium;
            font-size: 15px;
            color: #333333;
            letter-spacing: 0;
            text-align: center;
            font-weight: 500;
            padding: 16px;
          }
        }
      }
    }
    .hdjl {
      .title {
        width: 42%;
        margin: 0 29%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        margin-bottom: 12px;
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
      .models1 {
        width: 93.6%;
        // height: 123px;
        margin: 0 3.2%;
        display: flex;
        flex-direction: column;
        background: #ecf1fd;
        border-radius: 8px;
        margin-top: 6.5px;
        margin-bottom: 6.5px;

        div:nth-of-type(1) {
          font-family: PingFangSC-Medium;
          font-size: 17px;
          color: #4867a5;
          letter-spacing: 0;
          font-weight: 500;
          margin: 16px 0 0 12px;
        }

        .title3 {
          color: #985540;
        }
        div:nth-of-type(2) {
          font-family: PingFangSC-Regular;
          font-size: 13px;
          color: #333333;
          letter-spacing: 0;
          margin: 8px 0 0 12px;
          font-weight: 400;
        }
        a {
          width: 100px;
          height: 32px;
          background: #ffffff;
          border-radius: 16px;
          margin: 12px 0 0 12px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          margin-bottom: 12px;
          span {
            font-family: PingFangSC-Regular;
            font-size: 11px;
            color: #4867a5;
            font-weight: 400;
          }
          .span2 {
            color: #ac6a02;
          }
          .span3 {
            color: #985540;
          }

          img {
            width: 15px;
            height: 15px;
          }
        }
      }

      .models2 {
        width: 93.6%;
        // height: 123px;
        margin: 0 3.2%;
        display: flex;
        flex-direction: column;
        background: #fdf6e7;
        border-radius: 8px;
        margin-top: 6.5px;
        margin-bottom: 6.5px;

        div:nth-of-type(1) {
          font-family: PingFangSC-Medium;
          font-size: 17px;
          color: #ac6a02;
          letter-spacing: 0;
          font-weight: 500;
          margin: 16px 0 0 12px;
        }

        div:nth-of-type(2) {
          font-family: PingFangSC-Regular;
          font-size: 13px;
          color: #333333;
          letter-spacing: 0;
          margin: 8px 0 0 12px;
          font-weight: 400;
        }
        a {
          width: 100px;
          height: 32px;
          background: #ffffff;
          border-radius: 16px;
          margin: 12px 0 0 12px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          margin-bottom: 12px;
          span {
            font-family: PingFangSC-Regular;
            font-size: 11px;
            color: #4867a5;
            font-weight: 400;
          }
          .span2 {
            color: #ac6a02;
          }
          .span3 {
            color: #985540;
          }

          img {
            width: 15px;
            height: 15px;
          }
        }
      }

      .models3 {
        width: 93.6%;
        // height: 123px;
        margin: 0 3.2%;
        display: flex;
        flex-direction: column;
        background: #fdf1eb;
        border-radius: 8px;
        margin-top: 6.5px;
        margin-bottom: 6.5px;

        div:nth-of-type(1) {
          font-family: PingFangSC-Medium;
          font-size: 17px;
          color: #985540;
          letter-spacing: 0;
          font-weight: 500;
          margin: 16px 0 0 12px;
        }
        .title2 {
          color: #ac6a02;
        }
        .title3 {
          color: #985540;
        }
        div:nth-of-type(2) {
          font-family: PingFangSC-Regular;
          font-size: 13px;
          color: #333333;
          letter-spacing: 0;
          margin: 8px 0 0 12px;
          font-weight: 400;
        }
        a {
          width: 100px;
          height: 32px;
          background: #ffffff;
          border-radius: 16px;
          margin: 12px 0 0 12px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          margin-bottom: 12px;
          span {
            font-family: PingFangSC-Regular;
            font-size: 11px;
            color: #4867a5;
            font-weight: 400;
          }
          .span2 {
            color: #ac6a02;
          }
          .span3 {
            color: #985540;
          }

          img {
            width: 15px;
            height: 15px;
          }
        }
      }
    }
  }
}
</style>
