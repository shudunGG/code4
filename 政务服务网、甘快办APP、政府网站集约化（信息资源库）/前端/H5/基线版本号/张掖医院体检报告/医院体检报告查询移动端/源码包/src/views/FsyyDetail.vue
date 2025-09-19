<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市体检报告查询</div>
      </div>
      <div class="container-bottom">
        <div class="card-wrap" v-for="(item, index) of fsyyDetail" :key="index">
          <div class="list">
            <div class="list-left">体检人姓名</div>
            <div class="list-right">{{ name(item.patName) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">证件号码</div>
            <div class="list-right">{{ IdNumberDesensitization(item.cardNo) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">电话</div>
            <div class="list-right">{{ phone(item.tel) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">体检日期</div>
            <div class="list-right">{{ item.examDate || "暂无数据" }}</div>
          </div>
          <div class="list" style="width: 100%; padding: 6px 0;">
            <van-button type="primary" style="width: 35%; font-size: 13px; padding: 3px 0; margin-left: 32.5%" @click="queryReportDetail(item.reportUrl)">报告详情</van-button>
          </div>
        </div>
      </div>
    </div>
<!--    <div class="footer">-->
<!--      <div class="footer-content">本服务由张掖市提供</div>-->
<!--    </div>-->
  </div>
</template>

<script setup>
import { useStore } from '../store/index.js'
import { IdNumberDesensitization, name, phone } from '../utils/desensitization'
import { onMounted } from 'vue'

const store = useStore()
const fsyyDetail = store.fsyyResult

onMounted(() => {
  console.log('-----', fsyyDetail)

  fsyyDetail.sort(function (a, b) {
    return b.examDate.replaceAll('-', '').split(' ')[0] - a.examDate.replaceAll('-', '').split(' ')[0]
  })
})

const queryReportDetail = (reportUrl) => {
  console.log('---', reportUrl)
  const user = navigator.userAgent
  // android端
  const isAndroid = user.indexOf('Android') > -1 || user.indexOf('Adr') > -1
  // ios端
  const isiOS = !!user.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)

  if (isAndroid) {
    window.open(reportUrl)
  } else if (isiOS) {
    window.location.href = reportUrl
  }
}

</script>

<style lang="less" scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: auto;

  .container-top {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    height: 140px;
    background: url(../assets/images/bg.png);
    background-size: 100% 100%;
    overflow: hidden;
    text-align: center;

    div {
      margin-top: 33.5px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }

  .container-bottom {
    margin-top: 110px;
    padding: 0 16px;

    .card-wrap {
      border-radius: 4px;
      background: #fff;
      box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
      padding: 0 12px;
      position: relative;
      z-index: 3;
      margin-bottom: 12.5px;
    }

    .list {
      padding: 16px 0;
      border-bottom: 1px solid #dcdcdc;
      display: flex;
      justify-content: space-between;

      .list-left {
        width: 180px;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 17px;
        text-align: left;
        color: #333;
        word-break: break-all;

        .van-button {
          border-radius: 6%;
        }
      }

      .list-right {
        width: 208px;
        word-break: break-all;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 16px;
        text-align: right;
        color: #999;

        .van-button {
          border-radius: 6%;
        }
      }

      .listUp {
        width: 100%;
        text-align: center;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 13px;
        color: #006ae9;

        .van-image {
          padding-left: 3px;
        }
      }
    }
  }

  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;

    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>
