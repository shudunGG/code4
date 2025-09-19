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
      <!-- <img class="background" src=".././assets/images/hy2.png" alt="" /> -->
      <img class="background" :src="mask" alt="" />
      <div class="ck-content">
        <div class="zct">
          <a
            @click="getFu(item)"
            class="model"
            v-for="(item, index) in lists"
            :key="index"
          >
            <span>{{ item.title }}</span>
            <img src=".././assets/images/tz.png" alt="" />
          </a>
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
import { getUserInfo } from '../tools/getUserInfo'
export default {
  name: "home",
  data() {
    return {
      isShow: false,
      mask: "",
      lists: [],
      active: 0,
    };
  },
  created() {
    let that = this;
    let id = that.$route.query.id;
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
        colId: id,
      }),
    };
    HTTP.jpass(param)
      .then((res) => {
        console.log(JSON.parse(res.data), "data");
        that.lists = JSON.parse(res.data).data.lists[1].contentConfigs;
        that.mask = JSON.parse(
          res.data
        ).data.lists[0].contentConfigs[0].iconPath;
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
    getdetail(id) {
      console.log(id);
      window.location.href = `https://zwfw.gansu.gov.cn/jmportal_files/resources/jmp/html/${id}.html`;
    },
    isWeiXin () {
      var ua = window.navigator.userAgent.toLowerCase()
      console.log(ua)// mozilla/5.0 (iphone; cpu iphone os 9_1 like mac os x) applewebkit/601.1.46 (khtml, like gecko)version/9.0 mobile/13b143 safari/601.1
      if (ua.match(/MicroMessenger/i) === 'micromessenger') {
        return true
      } else {
        return false
      }
    },
    getFu(item) {
      if (this.isWeiXin()) {
          // console.log(' 是来自微信内置浏览器')
          getUserInfo().then(res => {
            window.location.href = item.jumpUrl + `?token=${res.data.tokenNo}`
          })
        } else {
          window.location.href = item.jumpUrl
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
  background: #fff;
  min-height: 100vh;
  position: relative;
  .background {
    width: 100%;
    height: 200px;
  }
  .ck-content {
    position: relative;
    top: -20px;
    .zct {
      width: 93.6%;
      margin: 0 3.2%;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.2);
      border-radius: 8px;
      .model {
        margin-left: 12px;
        border-bottom: 1px solid #d8d8d8;
        display: flex;
        justify-content: space-between;
        span {
          font-family: PingFangSC-Regular;
          font-size: 17px;
          color: #333333;
          letter-spacing: 0.29px;
          text-align: left;
          font-weight: 400;
          line-height: 54px;
        }
        img {
          width: 18px;
          height: 18px;
          margin-right: 12px;
          margin-top: 18.5px;
        }
      }
    }
  }
}
</style>
