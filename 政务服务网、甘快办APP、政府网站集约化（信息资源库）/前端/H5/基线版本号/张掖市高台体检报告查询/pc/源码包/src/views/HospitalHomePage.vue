<template>
  <div class="pc-content">
    <div class="pc">
      <div class="pc-left">
        <div class="left_top">
          <div class="pc-left-top">
            <div class="yy">
              <img
                src="../assets/img/yiyuan.jpg"
                alt=""
                width="120x"
                height="120px"
              />
              <span class="title">高台县人民医院</span>
            </div>
          </div>
          <div class="pc-left-center">
            <div class="xx">
              <div class="one">
                <div class="one-left"></div>
                <div class="one-tite">基本信息</div>
              </div>
              <div class="two">
                <img
                  src="../assets/img/dh.png"
                  alt=""
                  width="18px"
                  height="15px"
                />
                <div class="two-title">
                  0936-6621016
                </div>
              </div>
              <div class="two">
                <img
                  src="../assets/img/dz.png"
                  alt=""
                  width="17px"
                  height="20px"
                />
                <div class="two-title">
                  {{ '高台县医院西路1号' }}
                  <span @click="map">查看地图</span>
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
            高台县人民医院始建于1942年，是一所集医疗、预防保健、科研、教学、急救和康复于一体的全县唯一一所综合性二级甲等医院，是高台县紧急医疗救援中心，2016年加挂高台县中西结合医院牌子，2024年设立河西地区医疗中心高台分中心，承担着全县及周边地区约16万人民群众的医疗急救任务。现有门诊综合楼、住院医技综合楼、康复中心楼和感染楼，占地面积34679平方米，建筑面积44730.93平方米。全院职工总数374人，现有党员73人，占职工总数的20%，卫生专业技术人员343人：其中高级职称59人，中级职称114人，初级职称170人；现有甘肃省高层次人才1名、陇原青年英才1名、甘肃省卫生健康行业骨干人才1名，市级卫生健康行业骨干人才3名、县管拔尖人才4名，编制床位450张。内设高台县“120”急救指挥中心、甘肃省远程会诊中心高台县分中心、河西区域医疗中心高台县分中心、高台县危重孕产妇救治中心、危重新生儿救治中心、中医康复治疗中心、残疾人康复中心以及门急诊、内、外、妇、儿、中医、康复、重症医学科等22个临床科室，影像、超声、检验等10个医技科室，党政办、医务科、护理部等12个职能科室。年门诊量20余万人（次），住院病人11000余人（次）。<br/>
          </div>
          <div class="two info-text">
            现拥有德国西门子1.5T磁共振、64排128层螺旋CT、超声诊断仪、医用血管造影X射线机(DSA)、蔡司手术显微镜、美国GE全数字化彩超、R860呼吸机、奥林巴斯高清电子腹腔镜、电子胃镜、日立全自动生化分析仪、医用高压氧舱等现代化大中型医疗设备200余台（件）。现能开展内、外、妇、儿、中医等各科常见病、多发病的诊断和治疗和肺叶切除术、胃癌根治术、经尿道前列腺电切术、颅脑外伤血肿清除术等微创新技术和三、四级手术。近年来，积极推广无痛胃肠镜检查、癌痛规范化治疗等新技术和心肌梗塞溶栓、临时起搏器植入等各类医疗技术的临床应用，极大的降低了危重症患者的死亡率，部分技术在全市县（区）级医院中领先。特别是2023年介入室自成立以来，先后开展了冠状动脉造影术、腔内影像及支架置入术、全脑血管造影术及血管内治疗术、颅内动脉瘤栓塞术、肝癌选择性肝动脉化疗、选择性肝血管瘤栓塞术、肺血栓栓塞肺动脉置管溶栓术、外周血管造影及腔静脉滤器植入术、周围神经嵌压松解术等多类手术，日平均介入手术量居全市县级医院前列。<br/>
          </div>
          <div class="two info-text">
            医院先后获得“全国综合医院中医药示范单位”“全国院务公开示范医院”“全国首批‘千县工程’示范单位”“全国上消化道癌早诊早治优秀项目点”“甘肃省五一劳动奖状”“全省医德医风示范医院”“市级文明单位”“全市医疗工作先进集体”等荣誉，多次受到县委、县政府表彰奖励。
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
import { getUserInfo, getToken } from "@/api/userInfo";
import { serveAPI } from "@/api/serve";
import tools, {
  name,
  IdNumberDesensitization,
  phone,
  getRequest,
} from "../tools/tool";
export default {
  data() {
    return {
      HospitalList: [],
      hospitalName: null,
      hospitalInformation: {},
    };
  },
  created() {
    let url = location.href; // 上线使用这个
    this.hospitalName = getRequest(url, "hospitalName", "");
    console.log(this.hospitalName, "hospitalName");
    this.getUserInfo();
  },
  methods: {
    // 获取用户信息
    async getUserInfo() {
      const loading = this.$loading({
        fullscreen: true,
        text: "加载中,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      await getUserInfo().then((res) => {
        console.log(res);
        // 获取token
        getToken().then((result) => {
          console.log(result, "result");
          if (res.code === "200") {
            loading.close();
            if (res.params.userType == "1") {
              console.log(res);
              let userInfo = res.params;
              userInfo.name = name(tools.decryptSM2(res.params.userName));
              userInfo.card = IdNumberDesensitization(
                tools.decryptSM2(res.params.userId)
              );
              userInfo.phone = phone(tools.decryptSM2(res.params.phone));
              userInfo.authCode = tools.decryptSM2(
                result.data.authCode,
                "0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7"
              );
              userInfo.token = tools.decryptSM2(
                result.data.token,
                "0e967306db2ed5f93efb8eb41a1043cf4782824a5c5e41874ab3305e4db6e6e7"
              );
              this.userInfo = userInfo;
              this.$store.commit("userInfo", userInfo);
              console.log(userInfo, "用户信息");
            } else {
              this.$message.warning("该业务仅限个人用户办理");
            }
          } else {
            loading.close();
            location.href =
              "https://zwfw.gansu.gov.cn/szgs/member/wwLogin/againLgin.do?againUrl=" +
              encodeURIComponent(location.href);
          }
        });
      });
    },
    // 获取医院列表接口
    getHospitalList() {
      console.log(this.hospitalName, "hospitalName");
      let params = {
        pageNum: 1,
        pageSize: 10,
        hospitalName: JSON.stringify({
          type: 1,
          content: this.hospitalName || "高台县人民医院",
          left: "",
          right: "",
        }),
      };
      serveAPI
        .getCmsInfo(
          "cmsnzjk",
          "cmslistInfonzjk",
          params,
          "452dc08cbf3e40eb872e2b326191b5f2"
        )
        .then((res) => {
          if (
            res.code == 200 &&
            res.success &&
            res.data.dataResult.dataList.length > 0
          ) {
            console.log(res, "天祝藏族自治县藏医院");
            this.hospitalInformation = res.data.dataResult.dataList[0];
            this.$store.commit("hospitalInformation", this.hospitalInformation);
          } else {
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch((res) => {
          this.$message.error(res.message || "网络异常，请稍后重试！");
        });
    },
    map() {
      window.open(
        `https://zwfw.gansu.gov.cn/jmopen_files/webapp/html5/zwdtmhwzrcufp/index.html#/search?location=${encodeURIComponent(
          '高台县医院西路1号'
        )}`
      );
    },
  },
};
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
              font-family: "PingFang SC Bold";
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
                font-family: "PingFang SC Bold";
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
                font-family: "PingFang SC Bold";
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
            font-family: "PingFang SC Bold";
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
          font-family: "PingFang SC Bold";
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
              font-family: "PingFang SC Bold";
              font-weight: 700;
              font-size: 16px;
              color: #333;
              margin-bottom: 10px;
            }
            .bottom {
              font-family: "PingFang SC Bold";
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
          font-family: "PingFang SC Bold";
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
              font-family: "PingFang SC Bold";
              font-weight: 700;
              font-size: 16px;
              color: #333;
              margin-bottom: 10px;
            }
            .bottom {
              font-family: "PingFang SC Bold";
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
  font-family: "PingFang SC Bold";
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
