<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="container-wrap">
    <div class="top-bg">
      <div>体检报告查询</div>
    </div>
    <div class="card-wrap" style="margin-top: 110px">
      <div class="card-title">
        <span style="color: #999; font-family: 'Microsoft YaHei'; font-weight: 400; font-size: 12px">填写信息</span>
      </div>
      <div class="card-list" style="border-top: 0.2px solid #e8e8e8">
        <div class="card-list-title"><span style="color: red; padding-right: 3px">*</span>查询医院</div>
        <div class="card-list-picker" style=" border-bottom: 1px solid #e5e5e5; padding-bottom: 8px">
          <van-field v-model="hospital" is-link readonly placeholder="请选择查询医院" @click="showPicker = true"/>
        </div>
      </div>
      <div class="card-list">
        <div class="card-list-title"><span style="color: red; padding-right: 3px">*</span>身份证号码</div>
        <div class="card-list-picker">
<!--          <van-field v-model="cartNo" placeholder="身份证号码" @blur="ruleHosipatil"/>-->
          <van-field v-model="jmCartNo" placeholder="身份证号码" @blur="ruleHosipatil" disabled/>

        </div>
      </div>
      <div class="card-list" style="border-top: 0.2px solid #e8e8e8" v-show="showPhone">
        <div class="card-list-title"><span style="color: red; padding-right: 3px">*</span>电话号码</div>
        <div class="card-list-picker">
<!--          <van-field v-model="phone" placeholder="电话号码" @blur="ruleHosipatil"/>-->
          <van-field v-model="jmPhone" placeholder="电话号码" @blur="ruleHosipatil" disabled/>

        </div>
      </div>
    </div>
    <van-button class="queryButton" type="info" @click="queryList">查询</van-button>
    <div class="footer">
      <div class="footer-content">本服务由张掖市提供</div>
    </div>
    <van-popup v-model:show="showPicker" round position="bottom">
      <van-picker :columns="hospitalColumns" @cancel="showPicker = false" @confirm="onConfirm"/>
    </van-popup>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { serverApi } from '../api/serverApi.js'
import { IdNumberDesensitization, TmPhone } from '../utils/desensitization'
import { closeToast, showLoadingToast, showToast } from 'vant'
import { useRouter } from 'vue-router'
import { useStore } from '../store/index.js'
import sm2 from '../utils/tool'
import { getUserInfo } from '../api/getUserInfo'

const router = useRouter()
const store = useStore()

// const cartNo = ref('622201198301102723')
// const token = ref('622224198910196512')
// const authCode = ref('19993688858')
const showPhone = ref(true)
const cartNo = ref('')
const token = ref('')
const authCode = ref('')
const hospital = ref('') // 医院
const queryFlage = ref('')
const phone = ref()
const hospitalColumns = ref([ // 医院列表
  { text: '张掖市中医医院', value: 'zyy' },
  { text: '河西学院附属张掖人民医院', value: 'hxfy' },
  { text: '张掖市第二人民医院', value: 'dermyy' }
])
const showPicker = ref(false)
const publicKey = '04753c8780ad3c343218f9ba4368669de096601ce3259124fd23f9496f5bea3ff398c7ae83e07e1e1250883dbf3c9c01af75415f06b267414f93ef318ffb2523f2'
const privateKey = '009b0cddd1a07a54222d0433165e95ec76b08ea5af4967ddecd3f5bac7fb4b0796'
const userCertNum = ref('')
const userMobile = ref('')

const jmCartNo = ref('')
const jmPhone = ref('')

onMounted(() => {
  queryUserInfo()
})

// 获取用户信息
const queryUserInfo = () => {
  showLoadingToast({
    message: '加载中...',
    forbidClick: true
  })

  getUserInfo().then(res => {
    if (res.data == '{}' || !res.data) {
      lightAppJssdk.navigation.close()
    } else {
      closeToast()
      const userInfo = res.data
      if (userInfo.userType == 10) {
      // cartNo.value = IdNumberDesensitization(userInfo.userCertNum)

        jmCartNo.value = IdNumberDesensitization(userInfo.userCertNum)
        jmPhone.value = TmPhone(userInfo.userMobile)

        cartNo.value = userInfo.userCertNum
        phone.value = userInfo.userMobile

        userCertNum.value = userInfo.userCertNum
        userMobile.value = userInfo.userMobile

        token.value = userInfo.tokenNo
        authCode.value = userInfo.tokenNo
        console.log('---', userInfo)
      } else {
        showToast({
          confirmButtonColor: '#3f87f3',
          message: '该业务仅限个人用户办理'
        })
      }
    }
  })
}

const ruleHosipatil = () => {
  if (!hospital.value) {
    showToast({
      message: '请先选择医院'
    })
    cartNo.value = ''
    phone.value = ''
  }
}

// 查询
const queryList = async () => {
  const specialKey = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
  const specialKeyPhone = /^1[3456789]\d{9}$/
  if (cartNo.value === '') {
    showToast({
      message: '证件号码不能为空',
      duration: 2000
    })
    return
  } else {
    if (!specialKey.test(cartNo.value)) {
      showToast({
        message: '证件号码格式有误',
        duration: 2000
      })
      return
    }
  }

  if (phone.value === '') {
    showToast({
      message: '电话号码不能为空',
      duration: 2000
    })
    return
  } else {
    if (!specialKeyPhone.test(phone.value)) {
      showToast({
        message: '电话号码格式有误',
        duration: 2000
      })
      return
    }
  }
  if (hospital.value === '') {
    showToast({
      message: '请选择医院',
      duration: 2000
    })
    return
  }

  // 重置pinia数据
  store.$reset()
  showLoadingToast({
    message: '加载中...',
    forbidClick: true
  })
  console.log('---', queryFlage.value)
  if (queryFlage.value === 'zyy') {
    await zyyQuery()
  } else if (queryFlage.value === 'dermyy') {
    await dermyyQuery()
  } else if (queryFlage.value === 'hxfy') {
    await hxfyQuery()
  }
}
// 张掖市中医院
const zyyQuery = async () => {
  const params = {
    // token: sm2.encryptSM2(token.value, '04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c')
    // authCode: authCode.value

    token: sm2.encryptSM2(cartNo.value, publicKey),
    authCode: sm2.encryptSM2(phone.value, publicKey)
  }

  console.log('----', {
    token: cartNo.value,
    authCode: phone.value
  })
  try {
    const result = await serverApi.queryList('zyyy', 'reportOrderList', params, '')
    const resultData = JSON.parse(sm2.decryptSM2(result.data, privateKey))
    console.log('---', resultData)
    if (result.code === '200' && result.success && resultData.length >= 1) {
      store.changeListDetail(resultData)
      store.changeToken(cartNo.value)
      store.changeAuthCode(phone.value)
      closeToast()
      router.push('/zyyy')
    } else {
      showToast({
        message: '暂无报告，请稍后再试'
      })
    }
  } catch (e) {
    console.log('--e--', e)
    showToast({
      message: '网络异常，请稍后再试'
    })
  }
}
// 河西附属医院
const hxfyQuery = async () => {
  const params = {
    // token: sm2.encryptSM2(token.value, '04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c')

    // authCode: authCode.value

    token: sm2.encryptSM2(cartNo.value, publicKey),
    // authCode: '123',
    isTest: sm2.encryptSM2(cartNo.value, publicKey)
  }
  try {
    const result = await serverApi.queryList('zyyy', 'healthExamReport', params)
    // const resultData = JSON.parse(sm2.decryptSM2(result.data, privateKey))
    if (result.code === '200' && result.success && result.data) {
      console.log('--123-', result)
      const resultData = JSON.parse(sm2.decryptSM2(result.data, privateKey))
      store.changeFsyyResult(resultData)
      router.push('/fsyy_detail')
    } else {
      showToast({
        message: '暂无报告，请稍后再试'
      })
    }
  } catch (e) {
    console.log('---3', e)
    showToast({
      message: '网络异常，请稍后再试'
    })
  }
}
// 张掖市第二人民医院
const dermyyQuery = async () => {
  const param = {
    // 622201198301102723
    // sfzh: '62220119881112632X'
    // sfzh: '150205198302061338'
    token: token.value
  }
  try {
    const result = await serverApi.queryList('zyyy', 'getBodyReport', param)
    if (result.code === '200' && result.success && result.data.data.tjrylb) {
      closeToast()
      console.log('-----', result)
      store.changeListDetail(result.data.data)
      router.push('/deymDetail')
    } else {
      showToast({
        message: '暂无报告',
        duration: 2000
      })
    }
  } catch (e) {
    showToast({
      message: '网络异常，请稍后再试',
      duration: 2000
    })
  }
}

const onConfirm = ({ selectedOptions }) => {
  if (selectedOptions[0].value === 'zyy') {
    showPhone.value = true
    cartNo.value = userCertNum.value
    phone.value = userMobile.value
  } else if (selectedOptions[0].value === 'hxfy') {
    showPhone.value = false
    cartNo.value = userCertNum.value
    phone.value = userMobile.value
  } else if(selectedOptions[0].value === 'dermyy') {
    showPhone.value = false
    cartNo.value = userCertNum.value
    phone.value = userMobile.value
  }
  hospital.value = selectedOptions[0].text
  queryFlage.value = selectedOptions[0].value
  showPicker.value = false
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
  /deep/ .van-picker__toolbar {
    border-bottom: 1px solid #e5e5e5;
  }

  /deep/ .van-picker__cancel {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker__confirm {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }

  /deep/ .van-picker-column__item {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }

  /deep/ .van-picker-column__item--selected {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }

  /deep/ .van-cell {
    padding: 0;
  }

  .top-bg {
    height: 140px;
    background: url(../assets/images/bg.png);
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
      border-bottom: 0.5px solid #e8e8e8;

      img {
        width: 14.3px;
        margin-right: 7px;
      }
    }

    .card-list {
      padding: 12px 0;

      input {
        width: 100%;
        font-family: "Microsoft YaHei";
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
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 14px;
        text-align: left;
        color: #333;
        margin-bottom: 8px;
      }

      .card-list-picker {
        display: flex;
        justify-content: space-between;
        align-items: center;

        > span {
          font-family: "Microsoft YaHei";
          font-weight: 400;
          font-size: 15px;
          text-align: left;
          color: #999;
        }
      }
    }
  }

  .queryButton {
    width: 100%;
    margin-top: 20px;
    border-radius: 4px;
    font-size: 18px;
    background: #1677ff;
    border-color: #1677ff;
    color: #FFFFFF;
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
