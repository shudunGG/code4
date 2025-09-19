<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>商品房预售许可发放信息</div>
      </div>
      <van-list v-model="loading" offset="10" :error.sync="error" :immediate-check="false" @load="onLoad"
        :finished="finished">
        <div class="container-bottom">
          <div class="card-wrap" v-for="(item, index) in list" :key="index">
            <table>
              <tr>
                <td colspan="4">商品房预售许可发放信息</td>
              </tr>
              <tr>
                <td class="tab_bt">名称</td>
                <td>{{ item.mc || '暂无数据' }}</td>
              </tr>
              <tr>
                <td class="tab_bt">位置</td>
                <td>{{ item.wz || '暂无数据' }}</td>
              </tr>
              <tr>
                <td class="tab_bt">简介</td>
                <td >{{ item.jj || '暂无数据' }}</td>
              </tr>
            </table>
          </div>
        </div>
      </van-list>
    </div>
    <div class="message">本服务由张掖市提供</div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { Toast } from 'vant'
import IndexApi from '@/api/index'
export default {
  data() {
    return {
      loading: false,
      error: false,
      finished: false,
      pageNo: 1,
      count: 0,
      selectArr: '',
      list: []
    }
  },
  computed: {
    ...mapState(['detail', 'total']),
  },
  created() {
    this.selectArr = JSON.parse(this.$route.query.city)
    this.renderList()
    // console.log(this.selectArr)
  },
  methods: {
    renderList() {
      // Toast({
      //   message: '查询成功'
      // })
      this.list = this.detail.dataList
      // this.list.forEach((item) => {
      //   item.jj = item.jj.replace(/<br\/>/g, "\n")
      // })
      this.count = this.total
      if (this.list.length >= this.count) {
        this.finished = true
      } else {
        this.finished = false
      }
    },
    queryList(value) {
      let params = {
        pageNum: this.pageNo,
        pageSize: 20
      }
      if (this.selectArr.length !== 0) {
        params = {
          pageNum: this.pageNo,
          pageSize: 20,
          mc: encodeURIComponent(
            JSON.stringify({
              type: 2,
              content: this.selectArr.substring(0, 2),
              left: '',
              right: ''
            })
          )
        }
      }
      IndexApi.queryList(params, '03667ae5e2fb49bb831f68c845baa490')
        .then(res => {
          this.count = res.data.dataResult.totalCount
          if (value === 'add') {
            this.list = this.list.concat(res.data.dataResult.dataList)
          } else {
            this.list = res.data.dataResult.dataList
          }
          if (this.list.length >= this.count) {
            this.finished = true
          } else {
            this.finished = false
          }
          this.loading = false
        })
        .catch(() => {
          this.error = true
          this.loading = false
          Toast({
            message: '分页加载失败'
          })
        })
    },
    onLoad() {
      // console.log(this.count)
      if (this.list.length >= this.count) {
        this.finished = true
      } else {
        this.pageNo += 1
        this.queryList('add')
      }
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
    background: url('~@/assets/images/bg.png');
    background-size: 100% 100%;
    overflow: hidden;
    text-align: center;

    div {
      margin-top: 33.5px;
      font-family: 'Microsoft YaHei Bold';
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
      padding: 12px;
      position: relative;
      z-index: 3;
      margin-bottom: 12.5px;

      table tr td {
        border: 0.02667rem solid #dcdcdc;
        padding: 0.42667rem 0;
        text-align: center;
      }
    }

    .list {
      padding: 0.42667rem 0;
      border-left: 0.02667rem solid #dcdcdc;
      display: flex;
      justify-content: space-between;
      border-top: 0.02667rem solid #dcdcdc;
      border-right: 0.02667rem solid #dcdcdc;

      .list-left {
        width: 115px;
        font-family: 'Microsoft YaHei';
        font-weight: 400;
        font-size: 17px;
        text-align: left;
        color: #333;
        word-break: break-all;
      }

      .list-right {
        width: 208px;
        word-break: break-all;
        font-family: 'Microsoft YaHei';
        font-weight: 400;
        font-size: 16px;
        text-align: right;
        color: #999;
      }
    }
  }

  .message {
    font-family: 'Microsoft YaHei';
    font-weight: 400;
    font-size: 14px;
    text-align: center;
    color: #999;
    margin: 16px auto;
  }
}

.tab_bt {
  width: 20%;
}
</style>
