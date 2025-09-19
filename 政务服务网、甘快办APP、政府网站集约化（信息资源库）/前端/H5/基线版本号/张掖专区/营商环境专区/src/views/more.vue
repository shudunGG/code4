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
      <img class="background" src=".././assets/images/zzd.png" alt="" />

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
                    :key="index"
                    @click="detailnews(item.iid)"
                  >
                    <div>
                      <div>{{ item.title }}</div>
                    </div>
                    <div class="text">
                      <span>{{ item.time }}</span>
                      <span>发文部门：{{ item.source }}</span>
                    </div>
                  </div>
                </div>
              </van-tab>
              <van-tab title="政策解读">
                <div class="first">
                  <div
                    class="model"
                    v-for="(item, index) in zcjdlist"
                    :key="index"
                    @click="detailnews(item.iid)"
                  >
                    <div>
                      <div>{{ item.title }}</div>
                    </div>
                    <div class="text">
                      <span>{{ item.time }}</span>
                      <span>发文部门：{{ item.source }}</span>
                    </div>
                  </div>
                </div></van-tab
              >
            </van-tabs>
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
import { baseUrl } from '../HTTP'
export default {
  name: 'home',
  data() {
    return {
      baseUrl,
      isShow: false,
      zcwjlist: [],
      zcjdlist: [],
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
        console.log(JSON.parse(res.data).data, 'data')
        that.zcwjlist = JSON.parse(res.data).data.lists[1].list[0].list[0].infos
        that.zcjdlist = JSON.parse(res.data).data.lists[1].list[1].list[0].infos
        that.isShow = true
      })
      .catch(() => {
        that.$Toast.clear()
        that.$Toast.fail('系统繁忙')
      })
  },
  methods: {
    detailnews(iid) {
      window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${iid}.html`
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
  background: #2a8cff;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 120px;
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
        /deep/ .van-tabs__nav,
        /deep/ .van-tabs__nav--card {
          width: 51%;
          margin: 0 24.5%;
          height: 27px;
          border: 1px solid #3f77f8;
          border-radius: 20px;
          overflow: hidden;
        }
        /deep/ .van-tab {
          font-family: PingFangSC-Regular;
          font-size: 14px;
          color: #333333;
          text-align: center;
          font-weight: 400;
          border: 0;
        }
        /deep/ .van-tab--active {
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
            .dian {
              position: absolute;
              left: -1.5px;
              top: 25px;
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
  }
}
</style>
