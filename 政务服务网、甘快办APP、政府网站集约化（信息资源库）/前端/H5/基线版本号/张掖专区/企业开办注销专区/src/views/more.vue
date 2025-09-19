<template>
  <div class="notify">
    <div class="content" v-show="isShow">
      <div class="ck-content">
        <img class="mask" src=".././assets/images/cx.png" alt="" />
        <!-- <img class="mask" :src="mask" alt="" /> -->
        <div class="box">
          <div class="top"><span>输入信息查询</span></div>
          <div class="down">
            <span>企业名称</span>
            <input type="text" placeholder="请输入信息查询" />
          </div>
        </div>
        <button class="btn">查询</button>
        <!-- <div v-if="list.length <= 0" style="text-align: center">暂无数据</div> -->
      </div>
    </div>

    <div v-show="!isShow" style="padding-top: 200px">
      <van-loading type="spinner" size="50px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
// import { HTTP } from "../tools/request";

export default {
  name: "notify",
  data() {
    return {
      isShow: false,
      list: [],
      mask: "",
      name: "",
    };
  },
  created() {
    let that = this;
    // let id = that.$route.query.id;
    // this.name = that.$route.query.name;
    // const timestamp = new Date().valueOf();
    // let param = {
    //   app_id: "jmportalnzjk",
    //   version: "1.0",
    //   charset: "UTF-8",
    //   origin: "1",
    //   header: "",
    //   timestamp: timestamp,
    //   interface_id: "showcomponent",
    //   biz_content: JSON.stringify({
    //     siteId: "533629ca23dd4c63954c69a637971ac2",
    //     mobileId: "29568f08cab7482b99cbeae02db932c9",
    //     colId: id,
    //   }),
    // };
    // HTTP.jpass(param).then((res) => {
    //   console.log(JSON.parse(res.data).data);
    that.isShow = true;
    // });
  },
  methods: {},
};
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
    .box {
      width: 93.6%;
      margin: 0 3.2%;
      height: 92.5px;
      background: #ffffff;
      box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.2);
      border-radius: 8px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      position: relative;
      top: -40px;
      .top {
        padding-left: 12px;
        span {
          font-family: PingFangSC-Regular;
          font-size: 14px;
          color: #999999;
          font-weight: 400;
        }
      }
      .down {
        padding-left: 12px;
        // margin-bottom: 12px;
        span {
          font-family: PingFangSC-Regular;
          font-size: 17px;
          color: #333333;
          font-weight: 400;
        }
        input {
          margin-left: 22px;
        }
      }
    }
    .btn {
      width: 93.6%;
      margin: 0 3.2%;
      height: 49px;
      background: #1677ff;
      border-radius: 4px;
      font-family: PingFangSC-Regular;
      font-size: 18px;
      color: #ffffff;
      text-align: center;
      font-weight: 400;
    }
  }
}
</style>