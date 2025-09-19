<template>
  <div class="main">
    <div class="search">
      <div class="search_bg"></div>
      <p>张掖市疫情防控捐赠监督举报电话查询</p>
    </div>
    <div class="list list_show" v-if="this.List.length > 0">
      <div class="list_title">
        <span>查询结果</span>
      </div>
      <div class="list_box show_box">
        <div>张掖市疫情防控捐赠监督举报电话：<a :href="'tel:'+8298232">8298232</a></div>
      </div>
    </div>
    <div class="down">
      <p class="footer">本服务由张掖市提供</p>
    </div>
  </div>
</template>
<script>
import { HTTP } from "../tools/request";
export default {
  data() {
    return {
      List: [],
      input_text: "",
      isShow: false,
    };
  },
  created() {
    this.search();
  },
  methods: {
    // 查询
    search() {
      var formdata = new FormData();
      formdata.append("app_id", "hqzysgjsjxx");
      formdata.append("interface_id", "hqzysgjsjzkjk");
      formdata.append("version", "1.0");
      formdata.append("header", "");
      formdata.append("charset", "UTF-8");
      formdata.append("origin", "0");
      formdata.append("timestamp", new Date().valueOf());
      formdata.append(
        "biz_content",
        JSON.stringify({ param: "三甲医院", type: "20" })
      );
      HTTP.jpass(formdata).then((res) => {
        this.List = JSON.parse(JSON.parse(res.data).data);
        console.log(88, this.List);
        if (this.List.length === 0) {
          this.isShow = true;
        }
      });
    },
   resetList() {
      this.List = [];
      this.input_text = "";
      this.isShow = false;
    },
    formSubmit() {
      return false;
    },
  },
};
</script>
<style lang="less" scoped>
.main {
  // position: relative;
  // min-height: 100vh;
  .down {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bg_bottom.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 0;
    margin-top: -20px;
    p {
      font-size: 14px;
      color: #999;
      position: fixed;
      bottom: 0;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
  .search {
    position: relative;
    // margin-bottom: 20px;
    .search_bg {
      width: 100%;
      height: 141px;
      background-image: url(../assets/images/bg_top.png);
      background-size: cover;
      background-repeat: no-repeat;

      img {
        width: 100%;
      }
    }
    p {
      width: 100%;
      box-sizing: border-box;
      font-size: 18px;
      position: absolute;
      top: 40px;
      color: #fff;
      text-align: center;
      font-weight: Bold;
      font-family: "PingFang SC";
      left: 50%;
      transform: translate(-50%, 55%);
    }
    .input_box {
      width: 92%;
      border-radius: 4px;
      background-color: #fff;
      box-shadow: 0 0 12px rgba(0, 0, 0, 0.16);
      position: absolute;
      top: 110px;
      margin-left: 4%;
      padding: 0 20px;
      box-sizing: border-box;
      ul {
        li {
          height: 55px;
          line-height: 55px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          span {
            color: #000;
            font-size: 16px;
          }
          input {
            height: 98%;
            width: 100%;
            color: #333;
          }
          input::-webkit-input-placeholder {
            color: #ccc;
            font-size: 16px;
          }
        }
      }
    }
    .btn {
      width: 92%;
      height: 45px;
      margin-left: 4%;
      background-color: aquamarine;
      height: 45px;
      border-radius: 4px;
      font-size: 18px;
      text-align: center;
      line-height: 45px;
    }
    .btn_search {
      margin-top: 50px;
      background: #3f87f3;
      color: #fff;
    }
    .btn_reset {
      margin-top: 20px;
      background-color: #fff;
      box-sizing: border-box;
      border: 1px solid #bcbcbc;
    }
  }
  .list {
    margin-top: 12px;
    width: 100%;
    // padding-bottom: 100px;
    position: relative;
    .list_title {
      height: 30px;
      width: 343px;
      margin-left: 4%;
      font-size: 15px;
      margin-bottom: 15px;
      font-family: PingFangSC-Regular;
      color: #000000;
      font-weight: 400;
      border-bottom: 1px solid #f5f5f5;
      span {
        text-align: center;
        display: block;
        width: 68px;
        height: 100%;
        box-sizing: border-box;
        border-bottom: 2px solid #3f87f3;
      }
    }

    .list_box {
      width: 92%;
      margin-left: 4%;
      a{
        color: #3f87f3
      }
    }

    .footer {
      position: absolute;
      bottom: 17px;
      left: 124.5px;
      font-size: 14px;
      color: #999;
    }
  }
  .list_show {
    // display: none;
    z-index: 1;
  }
  .list_undefined {
    // display: none;

    .undefined_box {
      // height: 150px;
      text-align: center;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      align-items: center;
      img {
        width: 185px;
        height: 107.5px;
        margin-top: 40px;
      }
      p {
        font-size: 14px;
        color: #373d41;
      }
    }
  }
}
</style>