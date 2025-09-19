<template>
  <div class="pc-content">
    <div class="pc">
      <div class="pc-left">
        <div class="left_top">
          <div class="pc-left-top">
            <div class="yy">
              <img
                src="../assets/img/3ffc45dcd57ae5979709e0cca7027cd.jpg"
                alt=""
                width="120x"
                height="120px"
              />
              <span class="title">临泽县中医医院</span>
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
                  0936-5521853
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
                  甘肃省张掖市临泽县沙河镇滨河南路西侧
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
          <div class="two">
            临泽县中医医院始建于1996年，占地面积6.8万平方米，建筑面积2.26万平方米。2014年通过二级甲等中医医院评审。现有职工206人（其中正式在编职工83人，内聘职工123人），专业技术人员171名，现有正高职称5人，副高职称19人，中级职称35人，县管拔尖人才2人。医院党委下设三个支部，现有党员51人，设置临床、医技、行政后勤科室21个，编制床位数260张，属于财政差额拨款的非营利性医疗机构。
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
    map() {
      window.open(
        `https://zwfw.gansu.gov.cn/jmopen_files/webapp/html5/zwdtmhwzrcufp/index.html#/search?location=${encodeURIComponent(
          '甘肃省张掖市临泽县沙河镇滨河南路西侧'
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
