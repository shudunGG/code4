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
        @clear="clear()"
      >
        <template #action>
          <div @click="onSearch">搜索</div>
        </template>
      </van-search>

      <div class="ck-content">
        <div class="sntb">
          <a
            class="box"
            v-for="(item, index) in searchlist"
            :key="index"
            :href="`${baseUrl.Url}jmopen_files/webapp/html5/bszngkbxq/index.html?id=${item.ITEM_ID}`"
          >
            <div class="top">
              <div>
                {{ item.TaskName }}
              </div>
            </div>
            <div class="down">
              <!-- <div
                style="
                  width: 60px;
                  height: 15px;
                  background: #e7f1ff;
                  border-radius: 2px;
                  font-family: PingFangSC-Regular;
                  font-size: 11px;
                  color: #1677ff;
                  text-align: center;
                  font-weight: 400;
                "
              >
                {{ item.TaskName }}
              </div> -->
              <div>
                {{ item.DeptName }}
              </div>
              <a>
                {{ '天水市' }}
                <img
                  src=".././assets/images/jt.png"
                  alt=""
                  style="width: 10px; height: 10px; transform: rotate(45deg)"
              /></a>
            </div>
          </a>
          <div v-show="havelist" style="width: 16%; margin: 0 42%">
            暂无数据
          </div>
        </div>

        <div class="tab" v-show="tab">
          <van-tabs type="card">
            <van-tab title="个人办事服务">
              <div class="box">
                <a
                  v-for="(item, index) in grlist"
                  :key="index"
                  @click="
                    getlist(
                      item.name,
                      item.code,
                      (type = 'gr'),
                      (type2 = '个人')
                    )
                  "
                >
                  <span> {{ item.name }}</span>
                </a>
                <!-- <div class="more" @click="getmore((type = 'gr'))">
                  <span> <a>查看更多主题</a> </span
                  ><img src=".././assets/images/xl.png" alt="" /><img
                    src=".././assets/images/hs.png"
                    alt=""
                  />
                </div> -->
              </div>
            </van-tab>
            <van-tab title="法人办事服务">
              <div class="box">
                <a
                  @click="
                    getlist(
                      item.name,
                      item.code,
                      (type = 'fr'),
                      (type2 = '法人')
                    )
                  "
                  v-for="(item, index) in frlist"
                  :key="index"
                >
                  <span> {{ item.name }}</span>
                </a>
                <!-- <div class="more" @click="getmore((type = 'fr'))">
                  <span><a>查看更多主题</a></span
                  ><img src=".././assets/images/xl.png" alt="" /><img
                    src=".././assets/images/hs.png"
                    alt=""
                  />
                </div> -->
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
import { HTTP } from '../tools/request'
import { baseUrl } from '../HTTP'

export default {
  name: 'home',
  data() {
    return {
      baseUrl,
      isShow: false,
      havelist: false,
      tab: true,
      value: '',
      searchlist: [],
      grlist: [],
      frlist: [],
    }
  },
  created() {
    let that = this;
    that.isShow = true;
    $.ajax({
      type: "post",
      url: `${this.baseUrl.Url}api-gateway/hanweb-gsitemdzcx-server/open-api/themeClassify/list?type=gr&pageSize=200&pageNo=1`,
      success: function (res) {
        console.log(res);
        that.grlist = res.data;
        that.isShow = true
      },
    });
    $.ajax({
      type: "post",
      url: `${this.baseUrl.Url}api-gateway/hanweb-gsitemdzcx-server/open-api/themeClassify/list?type=fr&pageSize=200&pageNo=1`,
      success: function (res) {
        console.log(res);
        that.frlist = res.data;
        that.isShow = true
      },
    });
  },

  // mounted() {
  //   let that = this
  //   lightAppJssdk.request.request({
  //     type: 'post',
  //     url: `${this.baseUrl.Url}api-gateway/hanweb-gsitemdzcx-server/open-api/themeClassify/list?type=gr&pageSize=200&pageNo=1`,
  //     success: (res) => {
  //       console.log(res)
  //       that.grlist = res.data
  //       that.isShow = true
  //     },
  //   })
  //   lightAppJssdk.request.request({
  //     type: 'post',
  //     url: `${this.baseUrl.Url}api-gateway/hanweb-gsitemdzcx-server/open-api/themeClassify/list?type=fr&pageSize=200&pageNo=1`,
  //     success: (res) => {
  //       console.log(res)
  //       that.frlist = res.data
  //       that.isShow = true
  //     },
  //   })
  // },

  methods: {
    clear() {
      if (this.value == '') {
        this.tab = true
        this.havelist = false
        this.searchlist = false
      }
    },
    onSearch() {
      if (this.value == '') {
        return
      }
      console.log('search')
      let that = this
      const timestamp = new Date().valueOf()
      let param = {
        app_id: 'sx',
        version: '1.0',
        charset: 'UTF-8',
        origin: '1',
        header: '',
        timestamp: timestamp,
        interface_id: 'sxjs',
        biz_content: JSON.stringify({
          areacode: AreaCode,
          searchKey: that.value,
        }),
      }
      HTTP.jpass(param).then((res) => {
        console.log(JSON.parse(res.data).data.data)

        if (JSON.parse(res.data).data.data.length > 0) {
          that.searchlist = JSON.parse(res.data).data.data
          that.havelist = false
          that.tab = false
        } else {
          that.havelist = true
          that.searchlist = false
          that.tab = false
        }
      })
    },
    getlist(title, id, type, type2) {
      console.log(title, id, type, type2)
      window.location.href = `${this.baseUrl.Url}jmopen_files/webapp/html5/fwdtgdnfuje/index.html#/list?id=${id}&cid=${type}&title=${title}&active=1&grfr=${type2}&areaCode=${AreaCode}&areaName=${AreaName}`
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
      .box {
        display: block;
        // margin-top: -50px;
        position: relative;
        top: -50px;
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
            span {
              display: inline-block;
              width: 52px;
              height: 15px;
              background: #e7f1ff;
              border-radius: 2px;
              font-size: 11px;
              color: #1677ff;
              text-align: center;
              margin-left: 10px;
              margin-top: 5px;
            }
          }
        }
        .down {
          display: flex;
          padding-top: 12px;
          padding-bottom: 12px;
          padding-left: 12px;
          padding-right: 8px;
          justify-content: space-between;
          div {
            font-family: PingFangSC-Regular;
            font-size: 13px;
            color: #999999;
            font-weight: 400;
          }
          a {
            font-family: PingFangSC-Regular;
            font-size: 13px;
            color: #999999;
            font-weight: 400;
          }
        }
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
        justify-content: left;
        flex-wrap: wrap;
        a {
          width: 29%;
          background: #ecf1fd;
          border-radius: 8px;
          font-family: PingFangSC-Regular;
          font-size: 15px;
          color: #333333;
          letter-spacing: 0;
          text-align: center;
          font-weight: 400;
          margin-bottom: 12px;
          margin-left: 13px;
          overflow: hidden;
          span {
            display: block;
            margin-top: 12px;
            margin-bottom: 12px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
          }
        }
        .model2 {
          background: #f6f9fc;
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
}
</style>
