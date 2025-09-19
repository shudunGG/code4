<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市体检报告查询</div>
      </div>
      <div class="container-bottom">
        <div class="card-wrap" v-for="(item, index) of dataDetail" :key="index">
          <div class="list">
            <div class="list-left">体检人姓名</div>
            <div class="list-right">{{ name(item.userName) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">证件号码</div>
            <div class="list-right">{{ IdNumberDesensitization(item.examineeIcno) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">电话</div>
            <div class="list-right">{{ phone(item.examineePhone) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">体检人编号</div>
            <div class="list-right">{{ item.serviceNumber || "暂无数据" }}</div>
          </div>
          <div style="width: 100%; padding: 6px 0;">
            <van-button type="primary" style="width: 35%; font-size: 13px; padding: 3px 0; transform: translateX(90%)" @click="queryReportDetail(item.serviceNumber, item)">查看详情</van-button>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
  </div>
</template>

<script setup>
import { useStore } from '../store/index.js'
import { ref } from 'vue'
import sm2 from '../utils/tool'
import { serverApi } from '@/api/serverApi.js'
import { useRouter } from 'vue-router'
import { closeToast, showLoadingToast, showToast } from 'vant'
import { IdNumberDesensitization, name, phone } from '../utils/desensitization'

const store = useStore()
const router = useRouter()
const dataDetail = ref(store.listDetail)
const publicKey = '04753c8780ad3c343218f9ba4368669de096601ce3259124fd23f9496f5bea3ff398c7ae83e07e1e1250883dbf3c9c01af75415f06b267414f93ef318ffb2523f2'
const privateKey = '009b0cddd1a07a54222d0433165e95ec76b08ea5af4967ddecd3f5bac7fb4b0796'

// 查询报告详情
const queryReportDetail = async (serviceNumber, itemDetail) => {
  const params = {
    // token: store.token,
    // authCode: store.authCode,

    // token: '622224198910196512',
    // authCode: '19993688858',

    token: sm2.encryptSM2(store.cartNo, publicKey),
    authCode: sm2.encryptSM2(store.phone, publicKey),
    serviceNumber
  }
  showLoadingToast({
    message: '加载中...',
    forbidClick: true
  })

  try {
    const result = await serverApi.queryList('zyyy', 'reportDetail', params)
    const resultReportDetail = JSON.parse(sm2.decryptSM2(result.data, privateKey))
    if (result.code === '200' && result.success && resultReportDetail.data) {
      closeToast()
      store.changeItemDetail(itemDetail)
      store.changeReportDetail(resultReportDetail)
      router.push({
        path: '/report'
      })
    }
  } catch (e) {
    console.log('--- 报告详情 e---', e)
    showToast({
      message: '网络异常，请稍后再试',
      duration: 2000
    })
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
