<template>
  <div class="main">
    <div class="search">
      <div class="search_bg"></div>
      <p>企业药品经营许可证查询</p>
      <div class="input_box">
        <ul>
          <li class="li_first">
            <span>企业名称</span>
            <form @submit.prevent="formSubmit" action="javascript:return true" style="width: 70%">
              <input type="text" placeholder="请输入企业名称" v-model="user" ref="inp" @keydown.enter="search" />
            </form>
          </li>
        </ul>
      </div>
      <div class="btn btn_search" @click="search">查询</div>
      <div class="btn btn_reset" @click="resetList">重置</div>
    </div>
    <div class="list list_show" v-if="this.List.length > 0">
      <div class="list_title">
        <span>查询结果</span>
      </div>
      <div class="list_box show_box">
        <ul>
          <li v-for="(item, index) in List" :key="index">
            <p>{{ item.title }}</p>
            <div class="info">
              <span class="infoLeft">企业名称</span>
              <span class="address"> {{ item.xm }}</span>
            </div>
            <div class="info">
              <span class="infoLeft">持证人代码</span>
              <span class="address"> {{ item.zjh | encodeSFZ }}</span>
            </div>
            <div class="info">
              <span class="infoLeft">证照名称</span>
              <span class="address"> {{ item.mc }}</span>
            </div>
            <div class="info">
              <span class="infoLeft">证照编号</span>
              <span class="address"> {{ item.bh }}</span>
            </div>
            <div class="info">
              <span class="infoLeft">颁发部门</span>
              <span class="address"> {{ item.bm }}</span>
            </div>
            <div class="info">
              <span class="infoLeft">颁发日期</span>
              <span class="address"> {{ item.rq }}</span>
            </div>
          </li>
        </ul>
        <div class="more" @click="more" v-if="moreThan">点击加载更多</div>
        <div class="more" v-else>没有更多了</div>
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
import { Jmas } from '../tools/request'
import { getUserInfo } from '../tools/getUserInfo'
import { tools } from '../tools/tool'
import { publicKey, privateKey } from '@/utils/common.js'
import { Toast } from 'vant'
export default {
  data() {
    return {
      List: [],
      user: '',
      isShow: false,
      pageNum: 1,
      moreThan: true,
      params: {},

      searchNum: 1, //查询次数
      searchParams: {}, //查询次数请求参数
      loginName: ''
    }
  },
  filters: {
    encodeSFZ(e) {
      return tools.encodeSFZ(e)
    },
    encodeName(e) {
      return tools.encodeName(e)
    }
  },
  created() {
    this.getInfo()
  },

  methods: {
    // 获取用户信息
    getInfo() {
      let that = this
      getUserInfo().then(res => {
        if (JSON.parse(res.data) == '{}' || !res.data) {
          that.$Dialog
            .alert({
              message: '当前未登录，请先登录'
            })
            .then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close()
            })
        } else {
          that.$toast.clear()
          let userinfo = JSON.parse(res.data).data
          this.loginName = userinfo.loginName
        }
      })
    },
    //点击加载更多
    more() {
      this.pageNum++
      this.search(this.pageNum)
    },

    //查询
    async search(pageNum) {
      this.List = []
      let pat = new RegExp(/\s+/g)
      if (pat.test(this.user)) {
        this.$toast('不可包含空格')
        return
      }
      if (!this.user) {
        this.$toast('请输入企业名称')
        return
      }
      this.$toast.loading({
        message: '加载中...',
        duration: 0,
        forbidClick: true
      })
      let key = this.loginName + '_6207qyypjyxkz'
      let param = {
        key
      }
      this.searchParams = {
        app_id: 'zkcx',
        interface_id: 'zkcxhcxcs',
        header: '',
        biz_content: JSON.stringify(param)
      }
      Jmas(this.searchParams).then(res => {
        if (JSON.parse(res.data).success) {
          if (JSON.parse(res.data).data == '') {
            this.searchNum = 1
          } else {
            this.searchNum = JSON.parse(res.data).data
          }

          if (this.searchNum > 5) {
            this.$toast('您今日查询次数已达上限')
            return
          } else {
            this.pageNum = typeof pageNum == 'object' ? 1 : pageNum
            this.params = {
              app_id: 'cmsnzjk',
              interface_id: 'listInfoEnnzjk',
              header: '',
              biz_content: JSON.stringify({
                columnId: tools.encrypt('ac0aa4b083434cec9d4a696f69be160e', publicKey, 1),
                pageNum: tools.encrypt(this.pageNum, publicKey, 1),
                pageSize: tools.encrypt('10', publicKey, 1),
                map: tools.encrypt(
                  JSON.stringify({
                    xm: JSON.stringify({
                      type: 1,
                      content: this.user ?? '',
                      left: '',
                      right: ''
                    })
                  }),
                  publicKey,
                  1
                )
              })
            }
            Jmas(this.params)
              .then(res => {
                this.$toast.clear()
                let resOne = JSON.parse(tools.decrypt(JSON.parse(res.data).data.dataResult, privateKey, 1))

                if (resOne.dataList) {
                  if (resOne.totalCount > 0) {
                    if (this.pageNum == 1) {
                      this.List = resOne.dataList
                    } else {
                      this.List.push(...resOne.dataList) // 将数据放入list中
                    }
                    if (this.List.length >= resOne.totalCount) {
                      this.moreThan = false
                      // 结束加载状态
                    } else {
                      this.moreThan = true
                    }
                    // 查询成功后 查询次数加1
                    let params2 = {
                      key: key,
                      value: Number(this.searchNum) + 1
                    }
                    let searchParams2 = {
                      app_id: 'zkcx',
                      interface_id: 'zkcxxrcxcs',
                      header: '',
                      biz_content: JSON.stringify(params2)
                    }
                    Jmas(searchParams2).then(res => {
                      console.log(res, '++++++++')
                    })
                  } else {
                    // this.$toast.fail("未查询到相关信息");
                    this.isShow = true
                  }
                } else {
                  // this.$toast.fail("未查询到相关信息");
                  this.isShow = true
                }
              })
              .catch(err => {
                Toast('网络异常')
                return err
              })
          }
        }
      })
    },
    // 重置
    resetList() {
      this.List = []
      this.user = ''
      this.pageNum = 1
      this.isShow = false
    },
    //提交
    formSubmit() {
      return false
    }
  }
}
</script>
<style lang="less" scoped>
.main {
  position: relative;
  min-height: 100vh;

  .down {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bg_bottom.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;

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
      font-family: 'PingFang SC';
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
      background: #1677ff;
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
    padding-bottom: 100px;
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
              width: 38%;
              font-family: PingFangSC-Regular;
              font-size: 14px;
              color: #666666;
              font-weight: 400;
            }

            .address {
              width: 70%;
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

      .more {
        text-align: center;
        padding: 4px 0;
        width: 100%;
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
