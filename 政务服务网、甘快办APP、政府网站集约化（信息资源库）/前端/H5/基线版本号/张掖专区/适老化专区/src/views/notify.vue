<template>
  <div class="notify">
    <div class="content" v-show="isShow">
      <div class="ck-content">
        <img class="mask" :src="mask != '' ? mask : mask1" alt="" />
        <div class="title" v-if="name != '健康科普'">{{ name }}</div>
        <div class="title1" v-if="name == '健康科普'">{{ name }}</div>
        <!-- <div class="box"> -->
        <div
          v-if="mask"
          class="model"
          v-for="(item, index) in lists"
          :key="index"
        >
          <a @click="getFu(item)">
            <div>
              {{ item.title }}
            </div>
            <img src=".././assets/images/tz.png" alt=""
          /></a>
        </div>

        <div class="model" v-for="(item, index) in list" :key="index">
          <a
            :href="
              item.url ||
              `${baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html`
            "
          >
            <div>
              {{ item.title }}
            </div>
            <img src=".././assets/images/tz.png" alt=""
          /></a>
        </div>
        <!-- </div> -->

        <div
          v-if="lists.length <= 0 && list.length <= 0"
          style="text-align: center"
        >
          暂无数据
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
import { getUserInfo } from '../tools/getUserInfo'

export default {
  name: 'notify',
  data() {
    return {
      baseUrl,
      isShow: false,
      lists: [],
      mask: '',
      mask1: '',
      name: '',
      pic: '',
      list: [],
      token: '',
      userType:''
    }
  },
  created() {
    this.isShow = false
    this.name = this.$route.query.name
    this.mask1 = this.$route.query.mask
    this.pic = this.$route.query.pic
    if (this.mask1) {
      let list = this.$route.query.list
      this.list = JSON.parse(list)
    }

    let that = this

    let id = that.$route.query.id
    if (!this.mask1) {
      getUserInfo().then((res) => {
        this.token = res.data.tokenNo
        this.userType = res.data.userType
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
          // console.log(JSON.parse(res.data));
          that.mask = JSON.parse(
            res.data
          ).data.lists[0].contentConfigs[0].iconPath
          that.lists = JSON.parse(res.data).data.lists[1].contentConfigs
          this.isShow = true
        })
      })
    }
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
.content {
  background-color: #ffffff;
  min-height: 100vh;
  .ck-content {
    position: relative;
    .mask {
      width: 100%;
      height: 160px;
      // margin-bottom: -50px
    }
    .title {
      font-family: PingFangSC-Semibold;
      font-size: 24px;
      color: #ffffff;
      letter-spacing: 0;
      font-weight: 600;
      position: absolute;
      top: 60px;
      left: 32px;
    }
    .title1 {
      font-family: PingFangSC-Semibold;
      font-size: 24px;
      color: #ffffff;
      letter-spacing: 0;
      font-weight: 600;
      position: absolute;
      top: 100px;
      left: 16px;
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
  }
}
</style>
