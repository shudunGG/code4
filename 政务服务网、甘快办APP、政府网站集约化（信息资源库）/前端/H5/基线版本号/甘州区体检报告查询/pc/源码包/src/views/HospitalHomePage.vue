<template>
  <div class="pc-content">
    <div class="pc">
      <div class="pc-left">
        <div class="left_top">
          <div class="pc-left-top">
            <div class="yy">
              <img src="../assets/img/yiyuan.jpg" alt="" width="120x" height="120px" />
              <span class="title">甘州区人民医院</span>
            </div>

          </div>
          <div class="pc-left-center">
            <div class="xx">
              <div class="one">
                <div class="one-left"></div>
                <div class="one-tite">基本信息</div>
              </div>
              <div class="two">
                <img src="../assets/img/dh.png" alt="" width="18px" height="15px" />
                <div class="two-title">0936-4421282</div>
              </div>
              <div class="two">
                <img src="../assets/img/dz.png" alt="" width="17px" height="20px" />
                <div class="two-title">
                  {{ '甘肃省张掖市甘州区东大街67号' }} <span @click="map">查看地图</span>
                </div>
              </div>
            </div>

          </div>
        </div>

        <div class="pc-left-content">
          <div class="one">
            <div class="one-left"></div>
            <div class="one-tite">医院介绍</div>
          </div>
          <div class="two info-text">
            甘州区人民医院始建于1946年，一所集医疗、急救、教学、科研、预防、保健、康复于一体的综合性“三级乙等”医院。占地面积4万多平方米，建筑面积约4.8万平方米，编制床位600张,实际开放床位1000张。年门急诊30余万人次，年住院3.2万余人次，年手术5200余例。担负着甘州区及毗邻地区近40多万群众的医疗保健服务和危急重症患者的救治工作。目前占地1.5万平方米包含公共卫生服务综合体、老年康养中心、慢病管理服务中心等主体的医院扩建项目主体工程已全面完工，预计2024年底投入使用，届时将形成集医疗、预防、保健、康复、医养结合、公共卫生服务为一体的医疗健康服务综合体，开放床位将达到1500张。
          </div>
          <div class="two info-text">
            医院现有职工619名（在编179人），专业技术人员540名，其中高级职称55人，中级职称86人。设有普外科、骨外科、呼吸与危重症医学科、重症医学科、介入医学科等26个临床科室，检验、影像等10个医技科室；其中普外科、骨外科是省级重点学科，呼吸与危重症医学科、心内科、儿科是市级重点学科；拥有介入治疗、血液净化、慢病等5个临床诊疗中心；建成胸痛、卒中、创伤、危重孕产妇、危重新生儿/儿童五大救治中心及检验、影像、心电、病理、消毒供应五个区域医学中心。
          </div>
          <div class="two info-text">
            目前拥有GE3.0T、GE1.5T多通道超导磁共振、GE64排128层CT、GE血管造影机（DSA介入）、芬兰普兰梅卡口腔CT、奥林巴斯、卡尔史托斯腹腔镜、胆道镜、宫腔镜、电切镜、电子胃镜、电子结肠镜、电子喉镜、西门子DR、数字胃肠机、高端彩色四维多普勒超声诊断仪、全数字化彩色多普勒超声诊断系统、德国贝朗血液透析机、国家最新标准高压氧舱、15立方的制氧机等大中型医疗设备100余台件,医院的诊疗水平和服务水平已领先省内同级别医院。
          </div>
          <div class="two info-text">
            医院先后被国家卫健委、省卫健委等部门授予“全国综合医院中医药工作示范单位”“全省群众满意的医疗机构”“甘肃省优质医疗服务示范医院”“甘肃省健康促进医院”“甘肃省卫生单位”“甘肃省青年文明号”“全省优秀医师团队”“甘肃省先进支援单位”“甘肃省抗击新冠肺炎疫情先进集体”等荣誉称号。
          </div>
          <div class="two info-text">
            多年来，医院坚持“以病患健康为中心”的服务宗旨，注重人文内涵建设，优化服务流程，改善诊疗环境，加强学科建设和人才培养，全面提升医疗服务质量和技术水平，结合“千县工程”县医院综合能力提升工作，全力构建“一体两翼”发展新格局，助推医院高质量发展，打造省内一流、区域顶尖、辐射广泛的县级综合医院。
          </div>
        </div>
      </div>

    </div>
    <div class="pc-content" style="margin-top: 30px">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { getUserInfo, getToken } from '@/api/userInfo'
// import { serveAPI } from '@/api/serve'
import tools, { name, IdNumberDesensitization, phone } from '../tools/tool'
export default {

  data () {
    return {
      HospitalList: [],
      hospitalName: null,
      hospitalInformation: {}
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    // 获取用户信息
    async getUserInfo () {
      const loading = this.$loading({
        fullscreen: true,
        text: '加载中,请稍等',
        spinner: 'el-icon-loading',
        background: 'rgba(f,f,f,0.7)'
      })
      await getUserInfo().then(res => {
        console.log(res)
        // 获取token
        getToken().then(result => {
          console.log(result, 'result')
          if (res.code === '200') {
            loading.close()
            if (res.params.userType == '1') {
              console.log(res)
              let userInfo = res.params
              userInfo.name = name(tools.decryptSM2(res.params.userName))
              userInfo.card = IdNumberDesensitization(tools.decryptSM2(res.params.userId))
              userInfo.phone = phone(tools.decryptSM2(res.params.phone))
              userInfo.authCode = tools.decryptSM2(
                result.data.authCode,
                '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
              )
              userInfo.token = tools.decryptSM2(
                result.data.token,
                '0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7'
              )
              this.userInfo = userInfo
              this.$store.commit('userInfo', userInfo)
              console.log(userInfo, '用户信息')
            } else {
              this.$message.warning('该业务仅限个人用户办理')
            }
          } else {
            loading.close()
            location.href =
              'https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=' + encodeURIComponent(location.href)
          }
        })
      })
    },
    map () {
      window.open(
        `https://zwfw.gansu.gov.cn/jmopen_files/webapp/html5/zwdtmhwzrcufp/index.html#/search?location=${encodeURIComponent(
          '甘肃省张掖市甘州区东大街67号'
        )}`)
    }
  }
}
</script>

<style scoped lang="less">
.pc-content {
  width: 1200px;
  margin: 0 auto;
  .pc {
    display: flex;
    margin-bottom: 30px;

    .pc-left {
      box-shadow: 0 3px 18px #0000000d;
      padding: 20px;
      background: linear-gradient(179.8deg, #e8f3ff 0%, #fff 100%);
      box-shadow: 0 3px 18px #0000000d;
      .left_top {
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #eee;
        .pc-left-top {
          .yy {
            display: flex;
            align-items: center;

            img {
              width: 120px;
              height: 120px;
            }
            .title {
              font-family: 'PingFang SC Bold';
              font-weight: 700;
              font-size: 20px;
              color: #333;
              margin-left: 20px;
            }
          }
        }
        .pc-left-center {
          display: flex;
          justify-content: space-between;
          padding-right: 174px;
          .xx {
            .one {
              display: flex;
              align-items: center;
              margin-bottom: 20px;
              .one-left {
                width: 3px;
                height: 15px;
                background: #1e82d8;
              }
              .one-tite {
                font-family: 'PingFang SC Bold';
                font-weight: 700;
                font-size: 16px;
                color: #333;
                margin-left: 10px;
              }
            }
            .two {
              display: flex;
              align-items: center;
              margin-bottom: 20px;

              .two-title {
                font-family: 'PingFang SC Bold';
                font-weight: 700;
                font-size: 16px;
                color: #333;
                margin-left: 16px;
                span {
                  padding: 2.5px 5px;
                  border-radius: 2px;
                  background: #d5e9fb;
                  color: #3f87f3;
                  margin-left: 20px;
                  cursor: pointer;
                }
              }
            }
          }
        }
      }

      .pc-left-content {
        padding-top: 20px;
        .one {
          display: flex;
          align-items: center;
          margin-bottom: 20px;
          .one-left {
            width: 3px;
            height: 15px;
            background: #1e82d8;
          }
          .one-tite {
            font-family: 'PingFang SC Bold';
            font-weight: 700;
            font-size: 16px;
            color: #333;
            margin-left: 10px;
          }
        }
      }
    }
    .pc-right {
      width: 370px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .pc-right-top {
        background-color: #fff;
        box-shadow: 0 3px 18px #0000000d;
        .title {
          display: flex;
          align-items: center;
          font-family: 'PingFang SC Bold';
          font-weight: 700;
          font-size: 16px;
          color: #333;
          border-bottom: 1px solid #eee;
          padding: 20px 0;
        }
        .content {
          display: flex;
          align-items: center;
          margin: 20px;
          padding-bottom: 20px;
          border-bottom: 1px solid #eee;
          .content-right {
            margin-left: 20px;
            .top {
              font-family: 'PingFang SC Bold';
              font-weight: 700;
              font-size: 16px;
              color: #333;
              margin-bottom: 10px;
            }
            .bottom {
              font-family: 'PingFang SC Bold';
              font-weight: 700;
              font-size: 14px;
              color: #999;
              width: 154px;
              line-height: 28px;
            }
          }
        }
      }
      .pc-right-bottom {
        box-shadow: 0 3px 18px #0000000d;
        background-color: #fff;
        .title {
          display: flex;
          align-items: center;
          font-family: 'PingFang SC Bold';
          font-weight: 700;
          font-size: 16px;
          color: #333;
          border-bottom: 1px solid #eee;
          padding: 20px 0;
        }
        .content {
          .content-right {
            margin: 0 20px;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
            .top {
              font-family: 'PingFang SC Bold';
              font-weight: 700;
              font-size: 16px;
              color: #333;
              margin-bottom: 10px;
            }
            .bottom {
              font-family: 'PingFang SC Bold';
              font-weight: 700;
              font-size: 14px;
              color: #999;
            }
          }
        }
      }
    }
  }
}
.info-text{
  text-indent: 2em;
}
.two {
  font-family: 'PingFang SC Bold';
  font-weight: 700;
  font-size: 16px !important;
  line-height: 32px;
  color: #333;
  /deep/ p {
    font-size: 16px !important;
    line-height: 32px;
    color: #333;
    span {
      background: none !important;
    }
  }
}
/deep/ .el-dialog__body {
  text-align: center;
}
@media screen and (max-width: 750px) {
  .pc-content{
    width: 100% !important;
  }
  .pc-left-center{
    padding: 0 !important;
  }
  .left_top{
    flex-direction: column;
  }
  .pc-left-top{
    margin-bottom: 20px;
  }
}
</style>
