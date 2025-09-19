<template>
  <div id="app">
    <div class="banner-container">
      <div class="banner-content box1200">
        <h1>张掖市存量房买卖合同查询</h1>
      </div>
    </div>
    <!-- <h2 class="title box1200">
      <img src="../src/assets/img/路径 91@2x.png" alt="" />
      当前位置：<a href="https://zwfw.gansu.gov.cn/index.html">首页</a> >
      <a href="https://zwfw.gansu.gov.cn/grfw/rmdh/index.html">个人服务</a> >
      <a href="https://zwfw.gansu.gov.cn/grfw/rmdh/index.html">热门导航</a> > 供水缴费
    </h2> -->
    <main class="main box1200">
      <div class="content-container" id="resultDiag">
        <router-view />
        <div class="footer">本服务由张掖市提供</div>
      </div>
    </main>
  </div>
</template>

<script>
import { getUserInfo, getToken } from '@/api/userInfo'
import tools, { name1, IdNumberDesensitization, phone } from './tools/tool'
export default {
  created() {
    this.getUserInfo()
  },
  methods: {
    // 获取用户信息
    async getUserInfo () {
      // const loading = this.$loading({
      //   fullscreen: true,
      //   text: '加载中,请稍等',
      //   spinner: 'el-icon-loading',
      //   background: 'rgba(f,f,f,0.7)',
      //   height: '100%'
      // })
      await getUserInfo().then(res => {
        console.log(res)
        // 获取token
        getToken().then(result => {
          console.log(result, 'result....')
          if (res.code === '200') {
            // loading.close()
            if (res.params.userType == '1') {
              let userInfo = res.params
              userInfo.name = name1(tools.decryptSM2(res.params.userName))
              userInfo.card = IdNumberDesensitization(tools.decryptSM2(res.params.userId))
              userInfo.phone = phone(tools.decryptSM2(res.params.phone))
              userInfo.authCode = tools.decryptSM2(
                result.data.authCode,
                '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
              )
              // console.log(userInfo.authCode);
              userInfo.token = tools.decryptSM2(
                result.data.token,
                '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
              )
              userInfo.userCard = tools.decryptSM2(res.params.userId)
              this.userInfo = userInfo
              this.$store.commit('userInfo', userInfo)
              sessionStorage.setItem('userInfo', tools.encryptSM2(JSON.stringify(userInfo), '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e'))
            } else {
              let userInfo = res.params
              this.$message.warning('该业务仅限个人用户办理')
              sessionStorage.setItem('userInfo', tools.encryptSM2(JSON.stringify(userInfo), '044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e'))
            }
          } else {
            // loading.close()
            location.href =
              'https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=' + encodeURIComponent(location.href)
          }
        })
      })
    },
  },
}
</script>
<style lang="less" scoped>
.title {
  font-weight: 400;
  font-size: 16px;
  line-height: 24px;
  text-align: left;
  color: #454545;
  margin: 20px auto;
  img{
    height:100%;
    // display:block;
    margin-right:10px;
  }
}
.footer {
  margin-top: 0.3rem;
  text-align: center;
  color: #666;
  font-size: 0.14rem;
}
</style>
