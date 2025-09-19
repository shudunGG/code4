<template>
  <div class="main">
    <div class="search">
      <div class="search_bg"></div>
      <p>张掖市村居委会数据</p>
    </div>
    <div class="list list_show" v-if="this.List.length > 0">
      <div class="list_title">
        <span>查询结果</span>
      </div>
      <div class="list_box show_box">
        <ul>
          <li v-for="(item, index) in List" :key="index">
            <p>{{ item.groupname }}</p>
            <div class="info">
              <span class="infoLeft">所属街道</span>
              <span class="address">{{ item.street | num}} </span>
            </div>
            <div class="info">
              <span class="infoLeft">详细住所</span>
              <span class="address">{{ item.address }} </span>
            </div>
            <div class="info">
              <span class="infoLeft">法定人</span>
              <span class="address">{{ item.fdr }} </span>
            </div>
            <!-- <div class="info">
              <span class="infoLeft">备注</span>
              <span class="address">{{ item.beizhu ==''?'暂无':item.beizhu}} </span>
            </div> -->
            <!-- <div class="info">
              <span class="infoLeft">传真</span>
              <span class="address">{{ item.fax }} </span>
            </div> -->
          </li>
        </ul>
      </div>
    </div>
    <div class="list list_undefined" v-if="isShow && this.List.length == 0">
      <div class="list_title">
        <span>查询结果</span>
      </div>
      <div class="list_box undefined_box">
        <img src="../assets/images/bg_middle.png" alt="" />
        <p>暂无查询结果</p>
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
  filters:{
    num(value){
      console.log(value);
      if (value == 1) {
        value = '东街街道'
        return value
      }
      if (value == 2) {
        value = '南街街道'
        return value
      }
      if (value == 3) {
        value = '西街街道'
        return value
      }
      if (value == 4) {
        value = '北街街道'
        return value
      }
      if (value == 5) {
        value = '火车站街道'
        return value
      }
      if (value == 6) {
        value = '梁家墩镇'
        return value
      }
      if (value == 7) {
        value = '上秦镇'
        return value
      }
      if (value == 8) {
        value = '大满镇'
        return value
      }
      if (value == 9) {
        value = '沙井镇'
        return value
      }
      if (value == 10) {
        value = '乌江镇'
        return value
      }
      if (value == 11) {
        value = '甘浚镇'
        return value
      }
      if (value == 12) {
        value = '甘浚镇'
        return value
      }
      if (value == 13) {
        value = '党寨镇'
        return value
      }
      if (value == 14) {
        value = '碱滩镇'
        return value
      }
      if (value == 15) {
        value = '明永镇'
        return value
      }
      if (value ==16) {
        value = '平山湖蒙古族乡'
        return value
      }
      if (value == 17) {
        value = '龙渠乡'
        return value
      }
      if (value == 18) {
        value = '安阳乡'
        return value
      }
      if (value == 19) {
        value = '花寨乡'
        return value
      }
      if (value == 20) {
        value = '靖安乡'
        return value
      }
      if (value == 21) {
        value = '三闸镇'
        return value
      }
      if (value == 22) {
        value = '小满镇'
        return value
      }
      if (value == 23) {
        value = '长安镇'
        return value
      }
      if (value == 24) {
        value = '沙河镇'
        return value
      }
      if (value == 25) {
        value = '新华镇'
        return value
      }
      if (value == 26) {
        value = '蓼泉镇'
        return value
      }
      if (value == 27) {
        value = '平川镇'
        return value
      }
      if (value == 28) {
        value = '板桥镇'
        return value
      }
      if (value == 29) {
        value = '鸭暖镇'
        return value
      }
      if (value == 30) {
        value = '倪家营镇'
        return value
      }
      if (value == 31) {
        value = '城关镇'
        return value
      }
      if (value == 32) {
        value = '宣化镇'
        return value
      }
      if (value == 33) {
        value = '南华镇'
        return value
      }
      if (value == 34) {
        value = '巷道镇'
        return value
      }
      if (value == 35) {
        value = '新坝镇'
        return value
      }
      if (value == 36) {
        value = '骆驼城镇'
        return value
      }
      if (value == 37) {
        value = '合黎镇'
        return value
      }
      if (value == 38) {
        value = '黑泉镇'
        return value
      }
      if (value == 39) {
        value = '罗城镇'
        return value
      }
      if (value == 40) {
        value = '东乐镇'
        return value
      }
      if (value == 41) {
        value = '位奇镇'
        return value
      }
      if (value == 42) {
        value = '霍城镇'
        return value
      }
      if (value == 43) {
        value = '陈户镇'
        return value
      }
      if (value == 44) {
        value = '大马营镇'
        return value
      }
      if (value == 45) {
        value = '老军乡'
        return value
      }
      if (value == 46) {
        value = '李桥乡'
        return value
      }
      if (value == 47) {
        value = '清泉镇'
        return value
      }
      if (value == 48) {
        value = '洪水镇'
        return value
      }
      if (value == 49) {
        value = '六坝镇'
        return value
      }
      if (value == 50) {
        value = '新天镇'
        return value
      }
      if (value == 51) {
        value = '南古镇'
        return value
      }
      if (value == 52) {
        value = '南丰镇'
        return value
      }
      if (value == 53) {
        value = '民联镇'
        return value
      }
      if (value == 54) {
        value = '顺化镇'
        return value
      }
      if (value == 55) {
        value = '丰乐镇'
        return value
      }
      if (value == 56) {
        value = '永固镇'
        return value
      }
      if (value == 57) {
        value = '三堡镇'
        return value
      }
      if (value == 58) {
        value = '红湾寺镇'
        return value
      }
      if (value == 59) {
        value = '皇城镇'
        return value
      }
      if (value == 60) {
        value = '康乐镇'
        return value
      }
      if (value == 61) {
        value = '马蹄藏族乡'
        return value
      }
      if (value == 62) {
        value = '白银蒙古族乡'
        return value
      }
      if (value == 63) {
        value = '大河乡'
        return value
      }
      if (value == 64) {
        value = '明花乡'
        return value
      }
      if (value == 65) {
        value = '祁丰藏族乡'
        return value
      }
    }
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
      formdata.append("biz_content", JSON.stringify({ type: "53" }));
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
    position: relative;
    bottom: 0;
    left: 0;
    z-index: 0;
    margin-top: -20px;
    p {
      font-size: 14px;
      color: #999;
      position: absolute;
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
      ul {
        li {
          width: 100%;
          border-radius: 8px;
          background: #fff;
          box-shadow: 0 0 12px rgba(0, 0, 0, 0.16);
          box-sizing: border-box;
          padding: 16px 16px 10px 16px;
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          margin-bottom: 10px;
          p {
            font-size: 16px;
            border-bottom: 0.5px solid #e8e8e8;
            padding-bottom: 14px;
            font-weight: bold;
            margin-bottom: 10px;
          }
          .info {
            padding-bottom: 6px;
            display: flex;
            justify-content: space-between;
            line-height: 25px;
            align-items: center;
            flex-direction: row;
            .infoLeft {
              width: 40%;
              font-family: PingFangSC-Regular;
              font-size: 14px;
              color: #666666;
              font-weight: 400;
            }
            .address {
              width: 60%;
              text-align: right;
              font-family: PingFangSC-Regular;
              font-size: 14px;
              color: #000000;
              text-align: right;
              font-weight: 400;
              img {
                width: 14px;
              }
            }
          }
        }
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