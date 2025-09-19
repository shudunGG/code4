<template>
  <div class="notify">
    <div class="content" v-show="isShow">
      <div class="ck-content">
        <img class="mask" src=".././assets/images/Mask.png" alt="" />
        <div class="ttile">{{ name }}</div>
        <div
          class="zx"
          v-for="(item, index) in lists"
          :key="index"
          @click="getnews2(item)"
        >
          <img :src="item.firstPicPath" alt="" v-if="item.firstPicPath" />
          <div class="box">
            <span class="up">{{ item.title }}</span>
            <div>
              <span>{{ item.source }}</span>
              <span v-if="item.time">{{ item.time }}</span>
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
// import { HTTP } from "../tools/request";
import { HTTP } from "../tools/request";
import { getUserInfo }  from '../tools/getUserInfo'
import {baseUrl} from '../HTTP'

export default {
  name: "more",
  data() {
    return {
      baseUrl,
      isShow: false,
      lists: [],
      name: "",
      token:''
    };
  },
  created() {
    this.isShow = true;
    this.lists = JSON.parse(this.$route.query.news);
    this.name = this.$route.query.name;
    getUserInfo().then(res => {
      console.log('token',res.data.tokenNo)
      this.token = res.data.tokenNo
    })
  },
  mounted() {
    console.log(this.$route.query);
  },
  methods: {
    getnews2(item) {
      console.log(item.iid);
      window.location.href = `${this.baseUrl.Url}jmportal_files/resources/jmp/html/${item.iid}.html&token=${this.token}`;
    },
    isWeiXin () {
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
};
</script>

<style lang="less" scoped>
.content {
  background-color: #ffffff;
  // min-height: 100vh;
  .ck-content {
    //   top: -100px;
    position: relative;
    .mask {
      width: 100%;
      height: 160px;
      // margin-bottom: -50px
    }
    .ttile {
      display: block;
      font-family: PingFangSC-Medium;
      font-size: 20px;
      color: #ffffff;
      font-weight: 500;
      //   line-height: 36px;
      padding-left: 12px;
      position: relative;
      top: -100px;
    }
    .zx {
      width: 93.6%;
      margin: 0 3.2%;
      // height: 60px;
      background: #ffffff;
      display: flex;
      padding-left: 6px;
      margin-bottom: 12px;
      border-bottom: 1px solid #ecf1fd;

      img {
        width: 70px;
        height: 52.5px;
        border-radius: 5px;
      }
      .box {
        display: flex;
        flex-direction: column;
        padding: 0 12px;
        position: relative;
        width: 93.6%;
        // margin: 0 3.2%;
        .up {
          font-family: PingFangSC-Semibold;
          font-size: 15px;
          color: #333333;
          text-align: justify;
          font-weight: 600;
        }
        div {
          font-family: PingFangSC-Regular;
          font-size: 13px;
          color: #999999;
          font-weight: 400;
          display: flex;
          // padding-left: 50px;
          align-items: left;
          position: relative;
          right: 0;
          justify-content: space-between;
          span {
            padding-bottom: 10px;
            padding-top: 10px;
          }
          span:nth-of-type(2) {
            margin-top: 2px;
          }
        }
      }
    }
  }
}
</style>