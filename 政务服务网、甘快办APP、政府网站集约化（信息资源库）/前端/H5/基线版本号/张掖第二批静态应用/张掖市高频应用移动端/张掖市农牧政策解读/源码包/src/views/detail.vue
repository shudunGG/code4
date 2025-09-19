<template>
  <div class="container-wrap">
    <div class="top">
      <div class="container-top">
        <div>张掖市农牧政策解读</div>
      </div>
      <van-list
        v-model="loading"
        offset="10"
        :error.sync="error"
        :immediate-check="false"
        @load="onLoad"
        :finished="finished"
        finished-text="没有更多了"
      >
        <div class="container-bottom">
          <div class="card-wrap" v-for="(item, index) in list" :key="index">
            <div class="list">
              <div class="list-left">问题</div>
              <div class="list-right">{{ item.name || "暂无数据" }}</div>
            </div>
            <div class="list">
              <div class="list-left">解决方式</div>
              <div class="list-right">{{ item.jjfs || "暂无数据" }}</div>
            </div>
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
import sm2 from '../utils/sm2'
export default {
  data () {
    return {
      loading: false,
      error: false,
      finished: false,
      pageNum: 1,
      count: 0,
      list: [],
      code: ''
    }
  },
  computed: {
    ...mapState(['detail', 'total'])
  },
  created () {
    this.code = JSON.parse(this.$route.query.code)
    this.renderList()
  },
  methods: {
    renderList () {
      this.list = this.detail
      this.count = this.total
      if (this.list.length >= this.count) {
        this.finished = true
      } else {
        this.finished = false
      }
    },
    queryList (value) {
      let params = {}
      if (this.code == '') {
        params = {
          pageNum: sm2.encrypt(this.pageNum),
          pageSize: sm2.encrypt('10')
        }
      } else {
        params = {
          pageNum: sm2.encrypt(this.pageNum),
          pageSize: sm2.encrypt('10'),
          map: sm2.encrypt(
            JSON.stringify({
              name: JSON.stringify({
                type: 2,
                content: this.code,
                left: '',
                right: ''
              })
            })
          )
        }
      }
      IndexApi.queryList(params)
        .then((res) => {
          res = JSON.parse(sm2.decrypt(res.data.dataResult))
          this.count = res.totalCount
          if (value === 'add') {
            this.list = this.list.concat(res.dataList)
          } else {
            this.list = res.dataList
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
    onLoad () {
      if (this.list.length >= this.count) {
        this.finished = true
      } else {
        this.pageNum += 1
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
    background: url("~@/assets/images/bg.png");
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
        width: 90px;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 17px;
        text-align: left;
        color: #333;
        word-break: break-all;
      }
      .list-right {
        width: 208px;
        word-break: break-all;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 16px;
        text-align: right;
        color: #999;
      }
    }
  }
  .message {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 14px;
    text-align: center;
    color: #ccc;
    margin: 16px auto;
  }
}
</style>
