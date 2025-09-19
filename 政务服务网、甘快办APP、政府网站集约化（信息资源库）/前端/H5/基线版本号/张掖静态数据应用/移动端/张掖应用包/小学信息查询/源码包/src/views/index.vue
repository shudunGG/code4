<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div>小学信息查询</div>
    </div>
    <div class="card-wrap" style="margin-top: 110px">
      <div class="card-title">
        <span
          style="color:#999;font-family:'Microsoft YaHei';
          font-weight:400;
          font-size:12px">查询信息</span>
      </div>
      <div class="card-list">
        <div class="card-list-title">学校名称</div>
        <div class="card-list-picker">
          <input v-model="name" placeholder="请输入学校名称" @keyup="keyUp" maxlength="18"/>
        </div>
      </div>
      <van-button class="queryButton" type="info" @click="queryList">查询</van-button>
      <van-button class="resetButton" plain type="info" @click="resetInfo">重置</van-button>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import IndexApi from '@/api/index'
import { Toast } from 'vant'
import { getArea } from '@/utils/getArea'
import sm2 from '../utils/sm2'
export default {
  data () {
    return {
      code: '',
      id: '620100000000',
      cityShow: false,
      selctIndex: 0,
      cityArr: [{}, { values: [] }],
      selectName: '',
      selectArr: [],
      queryArr: '',
      pageNum: '1',
      name: ''
    }
  },
  mounted () {
    getArea().then(res => {
      // console.log(res.data)
      this.cityArr[0].values = res.data
      // console.log(this.cityArr)
    })
  },
  methods: {
    select () {
      getArea(this.id).then(res => {
        this.cityArr[1].values = res.data
        this.cityShow = true
      })
      this.cityShow = true
    },
    ...mapMutations(['setDetail']),
    keyUp () {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？\s]/g
      this.name = this.name.replace(pattern, '')
    },
    // 查询
    async queryList () {
      Toast({
        message: '查询中',
        duration: 0
      })
      // if (this.name === '') {
      //   Toast({
      //     message: '请输入商品名称'
      //   })
      //   return
      // }
      var params = {};
      if (this.name !== "") {
        params = {
          pageNum: sm2.encrypt(this.pageNum),
          pageSize: sm2.encrypt("10"),
          map: sm2.encrypt(
            JSON.stringify({
              name: JSON.stringify({
                type: 2,
                content: this.name,
                left: "",
                right: "",
              }),
            })
          ),
        };
      } else {
        params = {
          pageNum: sm2.encrypt(this.pageNum),
          pageSize: sm2.encrypt("10"),
        };
      }
      await IndexApi.queryList(params).then(res => {
        Toast.clear()
        res = JSON.parse(sm2.decrypt(res.data.dataResult))
        if (res.dataList && res.dataList.length > 0) {
          this.setDetail(res.dataList)
          this.$store.state.total = res.totalCount
          this.$router.push({
            path: '/listDetail',
            query: {
              code: JSON.stringify(this.name)
            }
          })
        } else {
          Toast({
            message: '未查询到相关信息'
          })
        }
      }).catch(() => {
        Toast({
          message: '查询失败'
        })
      })
    },
    // 所在市州确认
    onConfirm (text, index) {
      if (typeof (text) === 'string') {
        this.selectName = text
        this.selectArr = text
      } else {
        this.selectName = text[0].name + '-' + text[1].name
        this.selectArr = text
      }
      this.cityShow = false
    },
    // 重置信息
    resetInfo () {
      this.selectArr = []
      this.selectName = ''
      this.name = ''
    },
    onChange (picker, value, index) {
      this.id = value[0].code
      getArea(this.id).then(res => {
        this.$set(this.cityArr[1], 'values', res.data)
      })
    }
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
    background: white;
    padding: 0 16px;
    overflow: auto;
    // picker默认样式修改
    /deep/.van-picker__toolbar {
      border-bottom: 1px solid #e5e5e5;
    }
    /deep/ .van-picker__cancel {
      color: #3f87f3;
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 15px
    }
    /deep/ .van-picker__confirm {
      color: #3f87f3;
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 15px
    }
     /deep/ .van-picker-column__item {
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 17px;
      text-align: center;
      color: rgba(51, 51, 51, 0.4);
    }
    /deep/.van-picker-column__item--selected {
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 19px;
      text-align: center;
      color: #333;
    }
    .top-bg {
      height: 140px;
      background: url('~@/assets/images/bg.png');
      background-size: 100% 100%;
      overflow: hidden;
      position: absolute;
      left: 0;
      right: 0;
      div {
        margin-top: 33.5px;
        font-family: "Microsoft YaHei Bold";
        font-weight: bold;
        font-size: 18px;
        text-align: center;
        color: #fff;
      }
    }
    .card-wrap {
      border-radius: 4px;
      background: #fff;
      box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
      padding: 0 12px;
      position: relative;
      z-index: 3;
      margin-top: 12.5px;
      .upload-wrap {
        display: flex;
        justify-content: space-between;
      }
      &.card-wrap-last {
        margin-bottom: 69px;
      }
      .card-title {
        height: 37.5px;
        display: flex;
        align-items: center;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 18px;
        text-align: left;
        color: #333;
        border-bottom: 1px solid #e8e8e8;
        img {
          width: 14.3px;
          margin-right: 7px;
        }
      }
      .card-list {
        padding: 12px 0;
        border-bottom: 1px solid #e8e8e8;
        input {
          width: 100%;
          font-family: "Microsoft YaHei";
          font-weight: 400;
          font-size: 15px;
          text-align: left;
          color: #999;
          // margin: 12px 0;
        }
        input::-webkit-input-placeholder {
          color: #999;
        }
        input::-moz-input-placeholder {
          color: #999;
        }
        input::-ms-input-placeholder {
          color: #999;
        }
        .card-list-title {
          font-family: "Microsoft YaHei";
          font-weight: 400;
          font-size: 15px;
          text-align: left;
          color: #333;
          margin-bottom: 8px;
        }
        .card-list-picker {
          display: flex;
          justify-content: space-between;
          align-items: center;
          >span {
            font-family: "Microsoft YaHei";
            font-weight: 400;
            font-size: 15px;
            text-align: left;
            color: #999;
          }
        }

      }
      .queryButton {
        width: 100%;
        margin-top: 30px;
        border-radius: 4px;
        font-size: 18px;
        background:#1677ff;
        border-color:#1677ff
      }
      .resetButton {
        width: 100%;
        margin-top: 12px;
        margin-bottom: 18px;
        border-radius: 4px;
        font-size: 18px;
        border: 0.37px solid #bcbcbc;
        color: #333;
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
