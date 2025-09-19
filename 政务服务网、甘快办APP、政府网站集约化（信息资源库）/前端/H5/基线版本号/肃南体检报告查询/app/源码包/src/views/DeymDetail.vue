<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>肃南裕固族自治县体检报告查询</div>
      </div>
      <div class="container-bottom">
        <div class="card-wrap" v-for="(item, index) in dataDetail" :key="index">
          <div class="list">
            <div class="list-left">电话号码</div>
            <div class="list-right">{{ phone(info.mobile) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">姓名</div>
            <div class="list-right">{{ name(info.name) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">性别</div>
            <div class="list-right">{{ info.sex === '1' ? '男' : '女' || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">体检日期</div>
            <div class="list-right">{{ item.reportTime || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left" style="margin-left: 12px">
              <van-button type="default" @click="brow(item.reportPath)">报告预览</van-button>
            </div>
            <!-- <div class="list-right" style="margin-right: 12px">
              <van-button type="primary" @click="downloadPDF(item.reportPath)">报告下载</van-button>
            </div> -->
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由肃南裕固族自治县提供</div>
    </div>
  </div>
</template>

<script setup>
import { useStore } from '../store/index.js'
import { nextTick, onMounted, ref } from 'vue'
import { phone, name } from '../utils/desensitization'
import { useRouter } from 'vue-router'
import { closeToast, showLoadingToast, showToast } from 'vant'
import { base64toBlod } from '../utils/file.js'
import { serverApi } from '../api/serverApi.js'
import { createLoadingTask } from 'vue3-pdfjs'
import * as Taro from 'vant'

const store = useStore()
const router = useRouter()
const info = ref(store.listDetail)
const dataDetail = Array.isArray(store.listDetail.reports) ? ref(store.listDetail.reports) : [store.listDetail.reports];

const pdfSrc = ref('')
const pageNum = ref('')

onMounted(() => {
  console.log(store.listDetail);
  
  // showLoadingToast({
  //   message: '加载中...',
  //   forbidClick: true
  // })
  // nextTick(() => {
  //   fileBase()
  // })
})

// 报告预览
const brow = async (httppdf) => {
  // await fileBase(httppdf)
  showLoadingToast({
    message: '加载中...',
    forbidClick: true
  })

  const param = {
    // downloadMaterialUrl: dataDetail.value.httppdf
    downloadMaterialUrl: httppdf
  }
  try {
    const result = await serverApi.queryList('zyyy', 'download', param)
    console.log('12321312', result)
    if (result.code === '200' && result.success && result.data.data !== '') {
      // 将Base64字符串转换为Blob对象
      const blob = base64toBlod(result.data.data)
      pdfSrc.value = URL.createObjectURL(blob)
      // 处理pdf的页数
      createLoadingTask(URL.createObjectURL(blob)).promise.then(pdf => {
        pageNum.value = pdf.numPages
      })

      // console.log('qweqweqweqw', pdfSrc.value,  pageNum.value)

      router.push({
        path: '/pdfBrow',
        query: { pdfSrc: pdfSrc.value, pdfNum: '24' }
      })

      closeToast()
    } else {
      showToast({
        message: '暂无报告数据，请稍后再试'
      })
    }
  } catch (e) {
    showToast({
      message: '网络异常，请稍后再试'
    })
  }
}
// 报告下载
const downloadPDF = (httppdf) => {
  const a = httppdf
  if (a) {
    if (/(iPad|iPhone|iPod)/g.test(navigator.userAgent)) {
      const textarea = document.createElement('textarea')
      textarea.value = a
      textarea.readOnly = 'readonly'
      document.body.appendChild(textarea)
      textarea.select()
      textarea.setSelectionRange(0, textarea.value.length)
      const successful = document.execCommand('copy')
      if (successful) alert('复制链接成功,请前往浏览器进行下载')
      document.body.removeChild(textarea)
    } else {
      location.href = a
    }
  } else {
    Taro.showToast({
      title: '下载失败',
      icon: 'error',
      duration: 2000
    })
  }
}
// 文件转base64
const fileBase = async (httppdf) => {
  const param = {
    // downloadMaterialUrl: dataDetail.value.httppdf
    downloadMaterialUrl: httppdf
  }
  try {
    const result = await serverApi.queryList('zyyy', 'download', param)
    console.log('12321312', result)
    if (result.code === '200' && result.success && result.data.data !== '') {
      // 将Base64字符串转换为Blob对象
      const blob = base64toBlod(result.data.data)
      pdfSrc.value = URL.createObjectURL(blob)
      // 处理pdf的页数
      createLoadingTask(URL.createObjectURL(blob)).promise.then(pdf => {
        pageNum.value = pdf.numPages
      })
      closeToast()
    } else {
      showToast({
        message: '暂无报告数据，请稍后再试'
      })
    }
  } catch (e) {
    showToast({
      message: '网络异常，请稍后再试'
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
      .list:last-child{
        border: none !important;
      }
    }

    .list {
      padding: 16px 0;
      border-bottom: 1px solid #dcdcdc;
      display: flex;
      // justify-content: flex-end;

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
    flex-shrink: 0;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    // bottom: 0;
    // left: 0;
    // z-index: 0;

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
