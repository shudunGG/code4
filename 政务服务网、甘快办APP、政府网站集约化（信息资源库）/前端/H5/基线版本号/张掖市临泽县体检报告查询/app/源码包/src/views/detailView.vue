<template>
  <div class="page">
    <div class="container-wrap">
      <!-- 主内容 -->
      <div class="concent">
        <!-- 基本信息 -->
        <div class="concent_info">
          <div class="concent_info_t">基本信息</div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>就诊人：</div>
            <van-field maxlength="30" v-model="basicInfo.name" placeholder="请填写就诊人" disabled />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>身份证号：</div>
            <van-field maxlength="30" v-model="basicInfo.card" placeholder="请填写身份证号" disabled />
          </div>
          <div class="concent_info_picker">
            <div class="picker_t"><span>*</span>手机号：</div>
            <van-field maxlength="30" v-model="basicInfo.phone" placeholder="请填写手机号" disabled />
          </div>
          <div class="concent_info_picker" @click="showPicker = true">
            <div class="picker_t"><span>*</span>查询时段：</div>
            <van-field maxlength="30" v-model="basicInfo.dateTypeval" placeholder="请选择查询时段" readonly />
          </div>
          <!-- <div class="concent_info_picker" @click="showPickers = true">
            <div class="picker_t"><span>*</span>报告类型：</div>
            <van-field maxlength="30" v-model="basicInfo.typeval" placeholder="请选择报告类型" readonly />
          </div> -->
        </div>
        <button class="queryButton" type="info" @click="queryList">查询</button>
      </div>
    </div>
    <van-popup round position="bottom" v-model="showPicker">
      <van-picker value-key="time" show-toolbar :columns="columns" @confirm="confirm" @cancel="showPicker = false" />
    </van-popup>
    <van-popup round position="bottom" v-model="showPickers">
      <van-picker value-key="type" show-toolbar :columns="column" @confirm="confirms" @cancel="showPickers = false" />
    </van-popup>
    <!-- 底部 -->
    <div class="footer" v-show="showServiceTip">
      <div class="footer-content">本服务由临泽县提供</div>
    </div>
    <van-dialog v-model="showdialog" title="提示" confirmButtonColor="#3f87f3" :message="message" @confirm="confirmdialog">
    </van-dialog>
  </div>
</template>
<script>
import { name, IdNumberDesensitization, formatDate, replaceAll, phone } from '../tools/tool'
import { Toast } from 'vant'
import { getUserInfo } from '../api/getUserInfo'
export default {
  name: 'detail',
  data () {
    return {
      show: true,
      showPicker: false,
      columns: [
        { time: '近三个月', value: 0 },
        { time: '近半年', value: 1 },
        { time: '最近一年', value: 2 },
        // { time: '其他时段', value: 3 }
      ],
      showPickers: false,
      column: [
        { type: '检验', value: 0 },
      ],
      userInfo: {}, //用户信息
      basicInfo: {
        name: '',
        card: '',
        phone: '',
        dateType: '',
        dateTypeval: '',
        type: '',
        typeval: ''
      },
      initialHeight: document.documentElement.clientHeight || document.body.clientHeight,
      showServiceTip: true, // 显示或者隐藏footer,
      showdialog: false,
      message: ''
    }
  },
  created () {
    this.getUserInfo()
  },
  computed: {},
  mounted () {
    window.addEventListener('resize', this.handleResize)
  },
  destroyed () {
    window.removeEventListener('resize', this.handleResize)
  },
  watch: {
    showHeight (newval) {
      const parsedDocmHeight = parseInt(this.docmHeight, 10) || 0
      if (parsedDocmHeight > newval) {
        // 隐藏
        this.hideshow = false
      } else {
        // 显示
        this.hideshow = true
      }
    }
  },
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    phone,
    handleResize () {
      // 监听屏幕高度变化，判断键盘的状态
      const isKeyboardOpen = window.innerHeight < this.initialHeight
      if (isKeyboardOpen) {
        // 键盘弹起时隐藏提示
        this.showServiceTip = false
      } else {
        // 键盘收起时显示提示
        this.showServiceTip = true
      }
    },
    // 获取用户信息
    getUserInfo () {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: '加载中...'
      })
      getUserInfo().then(res => {
        console.log(res)
        if (res.data == '{}' || !res.data) {
          this.$Dialog
            .alert({
              message: '当前未登录，请先登录'
            })
            .then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close()
            })
        } else {
          this.$toast.clear()
          let userInfo = res.data
          userInfo.username = this.name(userInfo.userName)
          userInfo.usercard = this.IdNumberDesensitization(userInfo.userCertNum)
          userInfo.phone = this.phone(userInfo.userMobile)
          if (userInfo.userType == 10) {
            this.$store.commit('userInfo', userInfo)
            this.userInfo = this.$store.state.userInfo
            console.log(this.userInfo, 'this.userInfo')
            this.basicInfo.name = this.userInfo.username
            this.basicInfo.card = this.userInfo.usercard
            this.basicInfo.phone = this.userInfo.phone
            console.log(userInfo, '用户信息')

          } else {
            this.showdialog = true
            this.message = '该业务仅限个人用户办理'
          }
        }
      })
    },
    confirm (value) {
      console.log(value)
      this.basicInfo.dateType = value.value
      this.basicInfo.dateTypeval = value.time
      this.showPicker = false
    },
    confirms (value) {
      console.log(value)
      this.basicInfo.type = value.value
      this.basicInfo.typeval = value.type
      this.showPickers = false
    },
    // 返回首页
    confirmdialog () {
      // eslint-disable-next-line no-undef
      lightAppJssdk.navigation.close({
        success: function (data) {
          return data
        },
        fail: function (data) {
          return data
        }
      })
    },
    queryList () {
      if (this.basicInfo.dateType === '') {
        Toast('请选择查询时段')
        return
      }
      // if (this.basicInfo.type === '') {
      //   Toast('请选择报告类型')
      //   return
      // }

      console.log(this.basicInfo, 'basicInfo')
      this.$store.commit('basicInfo', this.basicInfo)
      this.$router.push('/info')
    }
  }
}
</script>

<style scoped lang="less">
.page {
  width: 100%;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  // min-height: 100vh;
  .container-wrap {
    // 主内容
    .concent {
      padding: 12px 12px 0;
      // 基本信息
      .concent_info {
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 0 6px #00000012;
        padding: 0 12px;
        .concent_info_t {
          padding: 12px 0;
          font-weight: 700;
          font-size: 15px;
          color: #3f87f3;
          border-bottom: 1px solid #eee;
        }
        .concent_info_picker:last-child {
          border: 0;
        }
        .concent_info_picker {
          border-bottom: 1px solid #eee;
          .picker_t {
            font-size: 13px;
            font-weight: 700;
            padding: 12px 0 0;
            span {
              color: #d11313;
              margin-right: 6px;
            }
          }
        }
      }
    }
    .queryButton {
      height: 45px;
      border-radius: 4px;
      background: #3f87f3;
      color: #fff;
      font-size: 18px;
      width: 100%;
      font-weight: 700;
      margin-top: 30px;
    }
  }
  // 底部
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
  /deep/ .van-cell {
    padding: 8px 15px 12px;
    font-size: 13px;
  }
  /deep/ .van-field__control {
    -webkit-text-fill-color: #999;
  }
  /deep/ .van-field__control:disabled {
    -webkit-text-fill-color: #999;
  }
}
</style>
