<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div>有机合作社名单</div>
    </div>
    <div class="card-wrap" style="margin-top: 110px">
      <div class="card-title">
        <span style="color: #999; font-family: 'Microsoft YaHei'; font-weight: 400; font-size: 12px">查询信息</span>
      </div>
      <div class="card-list">
        <div class="card-list-title">名称</div>
        <input v-model="code" @keyup="keyUp" placeholder="请输入名称" type="text" />
      </div>
      <van-button class="queryButton" type="info" @click="queryList">查询</van-button>
      <van-button class="resetButton" plain type="info" @click="resetInfo">重置</van-button>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
    <!-- 所在市州弹窗 -->
    <!-- <van-popup v-model="cityShow" round position="bottom">
      <van-picker
        value-key="label"
        :columns="options"
        show-toolbar
        @cancel="cityShow = false"
        @confirm="onConfirm"
      ></van-picker>
    </van-popup> -->
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import IndexApi from '@/api/index'
import { Toast } from 'vant'
// import { getArea } from '@/utils/getArea'
// import aes from '@/utils/aes'
export default {
  data() {
    return {
      code: '',
      cityShow: false,
      selctIndex: 0,
      cityArr: [],
      selectName: '',
      selectArr: '',
      queryArr: ''
    }
  },
  created() { },
  methods: {
    ...mapMutations(['setDetail']),
    keyUp() {
      // eslint-disable-next-line no-useless-escape
      const pattern = /[`~!@#$^&*=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*——|{}【】'；：""'。，、？\s]/g
      this.code = this.code.replace(pattern, '')
    },
    // 查询
    async queryList() {
      Toast({
        message: '查询中',
        duration: 0
      })

      let params = {
        pageNum: 1,
        pageSize: 20
      }
      console.log(this.selectName)
      if (this.code) {
        params = {
          pageNum: 1,
          pageSize: 20,
          mc: encodeURIComponent(
            JSON.stringify({
              type: 2,
              content: this.code,
              left: '',
              right: ''
            })
          )
        }
      }
      try {
        let res = await IndexApi.queryList(params, '28084767d5fe4492aabb99a6f1b860c8')
        Toast.clear()
        if (res.data.dataResult.totalCount && res.data.dataResult.totalCount > 0) {
          this.setDetail(res.data.dataResult)
          this.$store.state.total = res.data.dataResult.totalCount
          this.$router.push({
            path: '/listDetail',
            query: {
              city: JSON.stringify(this.code)
            }
          })
        } else {
          Toast({
            message: '未查询到相关信息'
          })
        }
      } catch (err) {
        Toast({
          message: '查询失败'
        })
      }
    },
    // 重置信息
    resetInfo() {
      this.code = ''
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
    font-family: 'Microsoft YaHei';
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker__confirm {
    color: #3f87f3;
    font-family: 'Microsoft YaHei';
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker-column__item {
    font-family: 'Microsoft YaHei';
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }

  /deep/.van-picker-column__item--selected {
    font-family: 'Microsoft YaHei';
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
      font-family: 'Microsoft YaHei Bold';
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
      font-family: 'Microsoft YaHei';
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
        font-family: 'Microsoft YaHei';
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #999;
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
        font-family: 'Microsoft YaHei';
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
          font-family: 'Microsoft YaHei';
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
      background: #1677ff;
      border-color: #1677ff;
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
