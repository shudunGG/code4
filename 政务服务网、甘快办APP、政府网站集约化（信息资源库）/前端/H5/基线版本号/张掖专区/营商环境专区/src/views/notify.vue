<template>
  <div class="notify">
    <div class="content" v-show="isShow">
      <div class="ck-content">
        <!-- <img class="mask" src=".././assets/images/Mask.png" alt="" /> -->
        <img class="mask" :src="mask" alt="" />

        <a
          class="box"
          v-for="(item, index) in list"
          :key="index"
          @click="getFu(item)"
        >
          <div class="top">
            <div>
              {{ item.title }}
            </div>
          </div>
          <div class="down">
            <div
              style="
                width: 52px;
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
              {{ name }}
            </div>
            <div style="position: absolute; left: 100px">
              {{ item.descriptionName.split(':')[0].split(',')[1] }}
            </div>
            <a>
              <!-- {{ item.descriptionName.split(":")[1] }} -->
              <img
                src=".././assets/images/you.png"
                alt=""
                style="width: 13px; height: 13px"
              />
            </a>
          </div>
        </a>
        <div v-if="list.length <= 0" style="text-align: center">暂无数据</div>
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
export default {
  name: 'notify',
  data() {
    return {
      isShow: false,
      list: [],
      mask: '',
      name: '',
      token: '',
      userType:''
    }
  },
  created() {
    getUserInfo().then((res) => {
      this.token = res.data.tokenNo
      this.userType = res.data.userType

      let that = this
      let id = that.$route.query.id
      that.name = that.$route.query.name
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
          colId: id,
        }),
      }
      HTTP.jpass(param).then((res) => {
        console.log(res)
        console.log(JSON.parse(res.data).data)
        that.mask = JSON.parse(
          res.data
        ).data.lists[0].contentConfigs[0].iconPath
        that.list = JSON.parse(res.data).data.lists[1].contentConfigs.splice(
          1,
          JSON.parse(res.data).data.lists[1].contentConfigs.length - 1
        )
        that.isShow = true
      })
    })
  },
  methods: {
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
.content {
  background-color: #ffffff;
  min-height: 100vh;
  .ck-content {
    position: relative;
    .mask {
      width: 100%;
      height: 120px;
      // margin-bottom: -50px
    }
    .tubiao {
      position: absolute;
      top: 40.5px;
      left: 12px;
      display: flex;
      .icon {
        width: 36px;
        height: 36px;
      }
      span {
        display: block;
        font-family: PingFangSC-Medium;
        font-size: 20px;
        color: #ffffff;
        font-weight: 500;
        line-height: 36px;
        padding-left: 12px;
      }
    }
    .box {
      display: block;
      // margin-top: -50px;
      position: relative;
      top: -30px;
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
}
</style>
