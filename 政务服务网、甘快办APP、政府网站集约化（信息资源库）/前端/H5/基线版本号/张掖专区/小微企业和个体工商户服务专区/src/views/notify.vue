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
      <img class="background" src=".././assets/images/zct2.png" alt="" />
      <div class="ck-content">
        <div class="zct">
          <!-- <div class="top">
            <img src=".././assets/images/zct2.png" alt="" />
          </div> -->
          <van-tabs @click="onClick" color="#206FF1" v-model="active">
            <van-tab title="国家政策"
              ><div class="first">
                <div
                  class="model"
                  v-for="(item, index) in first"
                  :key="index"
                  @click="getdetail(item.iid, item.url)"
                >
                  <div>
                    <div
                      class="dian"
                      style="width: 3px; height: 3px; background: #333"
                    ></div>
                    <div>{{ item.title }}</div>
                  </div>
                  <div class="text">
                    <span>{{ item.time }}</span>
                    <span>发文部门：{{ item.source }}</span>
                  </div>
                </div>
              </div>
            </van-tab>
            <van-tab title="甘肃省政策"
              ><div class="second">
                <div
                  class="model"
                  v-for="(item, index) in second"
                  :key="index"
                  @click="getdetail(item.iid, item.url)"
                >
                  <div
                    class="dian"
                    style="width: 3px; height: 3px; background: #333"
                  ></div>
                  <div>{{ item.title }}</div>
                  <div class="text">
                    <span>{{ item.time }}</span>
                    <span>发文部门：{{ item.source }}</span>
                  </div>
                </div>
              </div></van-tab
            >
            <van-tab title="地方政策"
              ><div class="third">
                <div
                  class="model"
                  v-for="(item, index) in third"
                  :key="index"
                  @click="getdetail(item.iid, item.url)"
                >
                  <div
                    class="dian"
                    style="width: 3px; height: 3px; background: #333"
                  ></div>
                  <div>{{ item.title }}</div>
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

    <div v-show="!isShow" style="padding-top: 200px">
      <van-loading type="spinner" size="50px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
import { HTTP } from "../tools/request";
import {baseUrl} from '../HTTP'
export default {
  name: "home",
  data() {
    return {
      baseUrl,
      isShow: false,
      mask: "",
      first: [],
      second: [],
      third: [],
      active: 0,
    };
  },
  created() {
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
        console.log(JSON.parse(res.data), "data");
        that.mask = JSON.parse(
          res.data
        ).data.lists[0].contentConfigs[0].iconPath;

        that.first = JSON.parse(res.data).data.lists[1].list[0].list[0].infos;
        console.log(that.first);
        that.second = JSON.parse(res.data).data.lists[1].list[1].list[0].infos;
        that.third = JSON.parse(res.data).data.lists[1].list[2].list[0].infos;
        that.isShow = true;
      })
      .catch(() => {
        that.$Toast.clear();
        that.$Toast.fail("系统繁忙");
      });
  },
  methods: {
    onClick(name, title) {
      console.log(name, title);
    },
    getdetail(id, url) {
      console.log(id);
      console.log(url);
      if (url) {
        window.location.href = url;
      } else {
        window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${id}.html`;
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
  position: relative;
  .background {
    width: 100%;
    height: 120px;
  }
  .ck-content {
    .zct {
      .top {
        padding-top: 20px;
        img {
          display: block;
          width: 100%;
        }
      }
      ::v-deep.van-tabs__wrap {
        border-bottom: 0.5px solid #000;
        width: 93.6%;
        height: 50px;
        margin: 0 3.2%;
      }
      ::v-deep.van-tab__text {
        padding-top: 5px;
      }
      ::v-deep.van-tab {
        width: 93.6%;
        margin: 0 3.2%;
      }
      ::v-deep .van-tab--active {
        border-bottom: 3px solid #1677ff;
      }
      ::v-deep.van-tabs__line {
        display: none;
      }
      ::v-deep.van-tabs__content {
        width: 93.6%;
        margin: 0 3.2%;
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
      .second {
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
      .third {
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
</style>
