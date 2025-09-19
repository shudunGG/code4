<template>
  <div class="container">
    <div class="main">
      <div class="top">
        <p>
          <span>{{ $store.state.fromAction }}</span>
          <span v-if="$store.state.toAction"><van-image width="6%" :src="require('@/assets/images/ticket/icon.png')"/></span>
          <span>{{ $store.state.toAction }}</span>
        </p>
        <p>
          {{ $store.state.data1.split('-')[0] + '年' + $store.state.data1.split('-')[1] + '月' + $store.state.data1.split('-')[2] + '日' }}
        </p>
      </div>
      <van-list v-model="loading" offset="10" :error.sync="error" :immediate-check="false" @load="onLoad" :finished="finished" finished-text="没有更多了">
        <div class="container-bottom">
          <div class="ticket" v-for="(item,index) of list" :key="index">
<!--            <div class="list" style="background-color: azure; margin-bottom: 12px">-->
            <div class="list" style="margin-bottom: 12px">
              <div class="main_top" style="width: 100%">
                <div class="left-0" style="width: 20%">
                  {{ item.departtime }}
                </div>
                <div class="middle-0" style="width: 50%;">
                  <p><span>始</span><span>{{ $store.state.fromAction }}</span></p>
                  <p><span>终</span><span>{{ item.endstationname }}</span></p>
                </div>
                <div class="right-0" style="width: 20%; padding-right: 6px">
                  <p>¥{{ item.fullprice }}</p>
                  <p>剩余票{{ item.residualnumber }}张</p>
                </div>
              </div>
              <div class="main_bottom">
                <span>{{ item.vehicletype == '1' ? '特大型' : item.vehicletype == '2' ? '大型' : item.vehicletype == '3' ? '中型' : item.vehicletype == '4' ? '小型' : '乘用车' }}</span>
                <span>约{{ item.rundistance }}公里  约{{ item.runtime }}分钟</span>
              </div>
            </div>
          </div>

        </div>
      </van-list>
    </div>
    <!--    <div class="footer">-->
    <!--      <div class="footer-content">本服务由甘肃省提供</div>-->
    <!--    </div>-->
  </div>
</template>

<script>
import api from '@/api'
import { Toast } from 'vant'
import {mapState} from "vuex";

export default {
  name: 'TicketList',
  data() {
    return {
      list: [],
      loading: false,
      error: false,
      finished: false,
      pageNum: 1,
      sum: 0,
      param: {}
    }
  },
  created() {
    this.param = JSON.parse(this.$route.query.param)
    this.renderList()
    this.$forceUpdate()
  },
  computed: {
    ...mapState(['ticketsList', 'totalrows'])
  },
  methods: {
    renderList() {
      this.list = this.ticketsList
      this.sum = Number(this.totalrows) - 1
      console.log('asdasdsa', this.list, this.sum)
      if (this.list.length >= this.sum) {
        this.finished = true
      } else {
        this.finished = false
      }
    },
    queryList(value) {
      // const param = {
      //   uid: '6202000027',
      //   departcityid: this.$store.state.fromCityId, // 出发城市id
      //   reachstationname: this.$store.state.toAction == undefined ? '' : this.$store.state.toAction, // 目的站名称、
      //   departdate: this.$store.state.data1, // 出发日期
      //   disable: '1', //是否查询不可售（0：只查询可售班次 1：查询所有班次 默认为0）
      //   start: this.pageNum,
      //   count: '10'
      // }
      api.fromCity('findSailingsgs', 'gssztravelinquiry', this.param).then((res) => {
        console.log('---asd---', JSON.parse(res.data))
        this.total = JSON.parse(res.data).totalrows
        if (value === 'add') {
          this.list = this.list.concat(JSON.parse(res.data).data)
        } else {
          this.list = JSON.parse(res.data).data
        }
        if (this.list.length >= this.sum) {
          this.finished = true
        } else {
          this.finished = false
        }
        this.loading = false
      })
        .catch((e) => {
          this.error = true
          this.loading = false
          Toast({
            message: '分页加载失败'
          })
        })
    },
    onLoad() {
      console.log('-asd-asd-asd-asd-')
      if (this.list.length >= this.sum) {
        this.finished = true
      } else {
        this.pageNum = this.pageNum + 1
        this.param.start = this.pageNum
        this.queryList('add')
      }
    }
  }
}
</script>

<style scoped lang="less">
.container {
  //min-height: 100vh;
  //height: 100vh;
  background: #f8fbff;
  .top {
    width: 100%;
    padding: 24px 0;
    background: #3f87f3;
    text-align: center;
    margin-bottom: 12px;
    p:nth-child(1) {
      span:nth-child(1),  span:nth-child(3) {
        font-family: "PingFang SC Bold";
        font-weight: 700;
        font-size: 18px;
        color: #fff;
      }
      span:nth-child(2) {
        padding: 0 12px;
      }
    }
    p:nth-child(2) {
      font-family: "PingFang SC Bold";
      font-weight: 700;
      font-size: 14px;
      text-align: center;
      color: #fff;
      padding-top: 8px;
    }

  }
///deep/ .van-list__finished-text {
//  position: fixed;
//  bottom: 0;
//  left: 40%;
//  //margin-top: 20px;
//}
  .main {
    //margin-top: 12px;
    .ticket {
      text-align: center;
      width: 90%;
      transform: translateX(2%);
      border-radius: 8px;
      background: linear-gradient(180.00deg, #f7fcff 0%, #fff 100%);
      box-shadow: 0 0 12px #c6e6ff94;
      padding: 12px 12px 6px 12px;
      margin-top: 12px;
      .main_top {
        width: 100%;
        display: flex;
        flex-direction: row;
        .left-0 {
          padding: 0 12px;
          font-family: "PingFang SC Bold";
          font-weight: 700;
          font-size: 15px;
          text-align: center;
          color: #333;
          transform: translateY(25%);
        }
        .middle-0 {
          text-align: left;
          padding-left: 12px;
          p:nth-child(1) {
            padding-bottom: 6px;
            span:nth-child(1) {
              padding: 1px;
              font-family: "PingFang SC";
              font-weight: 400;
              font-size: 13px;
              text-align: center;
              color: #ffa42a;
            }
          }
          p:nth-child(2) {
            padding-bottom: 6px;
            span:nth-child(1) {
              padding: 1px;
              font-family: "PingFang SC";
              font-weight: 400;
              font-size: 13px;
              text-align: center;
              color: #3f87f3;
            }
          }
          p:nth-child(1), p:nth-child(2) {
            span:nth-child(2) {
              font-family: "PingFang SC";
              font-weight: 400;
              font-size: 13px;
              text-align: center;
              color: #333;
              padding-left: 16px;
            }
          }
        }
        .right-0 {
          //width: 100%;
          text-align: right;
          p:nth-child(1) {
            font-family: "PingFang SC Bold";
            font-weight: 700;
            font-size: 18px;
            text-align: center;
            color: #3f87f3;
            padding-bottom: 4px;
          }
          p:nth-child(2) {
            font-family: "PingFang SC";
            font-weight: 400;
            font-size: 12px;
            text-align: center;
            color: #999;
          }
        }
        padding-bottom: 6px;
        border-bottom: 0.1px solid #eee;
      }
      .main_bottom {
        padding: 12px 0 12px 0;
        span:nth-child(1) {
          margin-left: 24px;
          border-radius: 4px;
          background: #fffaf2;
          border: 1px solid #ffa42a;
          padding: 3px 6px;
          float: left;
          font-family: "PingFang SC";
          font-weight: 400;
          font-size: 12px;
          text-align: center;
          color: #ffa42a;
        }
        //span:nth-child(2) {
        //  margin-left: 12px;
        //}
        span:nth-child(2) {
          padding-left: 25%;
          font-family: "PingFang SC";
          font-weight: 400;
          font-size: 12px;
          text-align: center;
          color: #999;
        }
      }
    }

    //.ticket:last-child {
    //  margin-bottom: 230px;
    //}
  }

  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/index/bgc.png);
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
