<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市体检报告查询</div>
      </div>
      <div class="container-bottom">
        <div class="card-wrap">
          <div class="list">
            <div class="list-left">体检人姓名</div>
            <div class="list-right">{{ name(itemDetail.userName) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">证件号码</div>
            <div class="list-right">{{ IdNumberDesensitization(itemDetail.examineeIcno) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">电话</div>
            <div class="list-right">{{ phone(itemDetail.examineePhone) || "暂无数据" }}</div>
          </div>
          <div class="list">
            <div class="list-left">体检人编号</div>
            <div class="list-right">{{ itemDetail.serviceNumber || "暂无数据" }}</div>
          </div>
        </div>
      </div>
      <div class="container-bottom" style="margin-top: 0">
        <div class="card-wrap">
          <div class="list" style="border-bottom: 0px">
            <div class="title_left" :class="{ 'titile_bottom': showIndex == '0' }" @click="changeShotName('0')">
              {{ resultReport[0].shotName }}</div>
            <div class="title_left" :class="{ 'titile_bottom': showIndex == '1' }" @click="changeShotName('1')">
              {{ resultReport[1].shotName }}</div>
          </div>
          <div class="container_report" v-if="constShowFlage">
            <div class="report_list" v-for="(item, index) of resultReport[showIndex].data" :key="index">
              <div class="zjlu">
                <div class="zjlu_title">{{ index + 1 }}、{{ item.summary }}</div>
                <div class="zjlu_description">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {{ item.advice }}</div>
              </div>
            </div>
          </div>
          <div class="container_report" v-else>
            <div v-for="(item, index) of resultReport[showIndex].data" :key="index">
              <div class="list">
                <div style="width: 60%">{{ item.itemName }}</div>
                <div @click="toggleExpand(item, index)" v-if="!isExpanded(index)">
                  重点关注 <span style="color: red">{{ item.abnormalCount }}</span> 项
                  <van-icon name="arrow-down" />
                </div>
                <div @click="toggleUp(item, index)" v-else>
                  重点关注 <span style="color: red">{{ item.abnormalCount }}</span> 项
                  <van-icon name="arrow-up" />
                </div>
              </div>
              <div class="report_container" v-show="isExpanded(index)">
                <div v-if="item.imgUrlList.length < 1">
                  <div class="list" style="text-align: center">
                    <div style="width: 30%">项目名称</div>
                    <div style="width: 25%">检查结果</div>
                    <div style="width: 15%">范围</div>
                    <div style="width: 20%">检查结果</div>
                  </div>
                  <div class="list" v-for="(itemb, index) of item.l3Dtos" :key="index" style="text-align: center; border-bottom: 1px solid #dcdcdc">
                    <div style="width: 30%">{{ itemb.reportItemName }}</div>
                    <div style="width: 25%">{{ itemb.reportStandardValue }}</div>
                    <div style="width: 15%">{{ itemb.reportCheckValue }}</div>
                    <div style="width: 20%">{{ itemb.reportCheckResult }}</div>
                  </div>
                </div>
                <div v-else>
                  <div style="padding: 12px 0; border-bottom: 1px solid #dcdcdc">查看影像资料：<a :href="item.imgUrlList[0]"
                      style="color: #2A6FF6">点击查看</a></div>
                  <div class="list" style="text-align: center">
                    <div style="width: 20%">项目名称</div>
                    <div style="width: 80%">检查结果</div>
                  </div>
                  <div class="list" v-for="(itemb, index) of item.l3Dtos" :key="index"
                    style="text-align: center; border-bottom: 1px solid #dcdcdc">
                    <div style="width: 20%">{{ itemb.reportItemName }}</div>
                    <div style="width: 80%; text-align: left">{{ itemb.reportCheckValue }}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useStore } from '@/store/index.js'
import { IdNumberDesensitization, name, phone } from '../utils/desensitization'

const store = useStore()
const itemDetail = store.itemDetail
const resultReport = store.resultReportDetail.data.cardInfo
const constShowFlage = ref(true)
const showIndex = ref(0)
// 展开状态
const expandedMap = reactive({})
const toggleExpand = (item, index) => {
  console.log('---expand---')
  expandedMap[index] = true
}
const toggleUp = (item, index) => {
  console.log('---up---')
  expandedMap[index] = false
}
const isExpanded = (index) => {
  return expandedMap[index]
}
const changeShotName = (index) => {
  console.log('---', resultReport)
  showIndex.value = index
  if (index == 0) {
    constShowFlage.value = true
  } else {
    constShowFlage.value = false
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

    .list:last-child {
      border-bottom: 0px;
    }

    .title_left {
      margain-left: 27px;
      font-size: 21px;
      font-weight: 500;

    }

    .title_rigth {
      margain-right: 27px;
      font-size: 17px;
    }

    .titile_bottom {
      border-bottom: 3px solid #02A7F0;
      color: #02A7F0;
    }

    .report_list {}

    .container_report {
      padding-bottom: 24px;

      .report_list {
        .zjlu {
          .zjlu_title {
            font-size: 16px;
            font-weight: 600;
            padding: 12px 0;
          }

          .zjlu_description {
            background-color: #FAFAFA;
            font-size: 14px;
            font-weight: 500;
            padding: 6px;
            border-radius: 5%;
            line-height: 20px;
          }
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
