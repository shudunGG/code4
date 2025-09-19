<template>
  <div class="page">
    <div class="container-wrap">
      <!-- banner背景 -->
      <div class="top-bg">
        <div class="top-bg-l">
          <img src="../assets/images/touxiang.png" alt="" srcset="" />
        </div>
        <div class="top-bg-r">
          <div>姓名：{{ name(accountInfo.xingming) }}</div>
          <div>开户日期：{{ accountInfo.khrq.slice(0, 10) }}</div>
          <div>证件号码：{{ IdNumberDesensitization(accountInfo.zjhm) }}</div>
        </div>
      </div>
      <!-- 主内容 -->
      <div class="concent">
        <!-- 个人账户余额： -->
        <div class="concent_account">
          <div class="concent_account_t">
            <div class="account_l">信息详情</div>
          </div>
          <div class="concent_account_l"></div>
          <div class="concent_account_b">
            <div class="account_b_t">提取编号：</div>
            <div class="account_b_m">{{ infoList.tqxx.tqbh || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">申请人收款户名：</div>
            <div class="account_b_m">{{ infoList.tqxx.sqrskhm || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">申请人收款账号：</div>
            <div class="account_b_m">{{ infoList.tqxx.sqrskzh || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">申请人开户银行名称：</div>
            <div class="account_b_m">{{ infoList.tqxx.sqrkhyhmc || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">申请时间：</div>
            <div class="account_b_m">{{ infoList.tqxx.sqsj || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">申请提取金额小写：</div>
            <div class="account_b_m">{{ infoList.tqxx.sqtqjexx || '暂无数据'}}</div>
          </div>
          <div class="concent_account_b">
            <div class="account_b_t">提取类型：</div>
            <div class="account_b_m" v-if="infoList.tqxx.tqlx == 11">{{'部分提取' }}</div>
            <div class="account_b_m" v-if="infoList.tqxx.tqlx == 22">{{'销户提取' }}</div>
            <div class="account_b_m" v-if="!infoList.tqxx.tqlx">{{'暂无数据'}}</div>
          </div>
          <!-- <div class="concent_account_b">
            <div class="account_b_t">提取信息来源：</div>
            <div class="account_b_m">{{ infoList.tqxx.tqxxly || '暂无数据'}}</div>
          </div> -->
          <!-- <div class="concent_account_b">
            <div class="account_b_t">提取原因代码：</div>
            <div class="account_b_m">{{ infoList.tqxx.tqyydm || '暂无数据'}}</div>
          </div> -->
          <div class="concent_account_b">
            <div class="account_b_t">提取账户类别：</div>
            <div class="account_b_m" v-if="infoList.tqxx.tqzhlb == 1">{{'个人'}}</div>
            <div class="account_b_m" v-if="infoList.tqxx.tqzhlb == 2">{{'开发商'}}</div>
            <div class="account_b_m" v-if="!infoList.tqxx.tqzhlb">{{'暂无数据'}}</div>
          </div>
          <!-- 购买住房 -->
          <div v-if="infoList.tqxx.tqyydm == '01'">
            <div class="concent_account_b">
              <div class="account_b_t">购房人姓名：</div>
              <div class="account_b_m">{{ infoList.tqxx.gfrxm || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">购房人身份证号：</div>
              <div class="account_b_m">{{ infoList.tqxx.gfrsfzh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">不动产账号：</div>
              <div class="account_b_m">{{ infoList.tqxx.bdczh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋地址类型：</div>
              <div class="account_b_m" v-if="infoList.tqxx.fwdzlx == 0">{{'本地购房'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.fwdzlx == 1">{{'异地购房'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.fwdzlx">{{'暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">售房单位：</div>
              <div class="account_b_m">{{ infoList.tqxx.sfdw || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">售房地址：</div>
              <div class="account_b_m">{{ infoList.tqxx.sfdz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">售房联系电话：</div>
              <div class="account_b_m">{{ infoList.tqxx.sflxdh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋地址：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwdz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">购房合同或协议号：</div>
              <div class="account_b_m">{{ infoList.tqxx.gfhthxyh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋现状：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwxz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">建筑面积：</div>
              <div class="account_b_m">{{ infoList.tqxx.jzmj || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋单价：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwdj || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋总价：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwzj || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">购房时间：</div>
              <div class="account_b_m">{{ infoList.tqxx.gfsj || '暂无数据'}}</div>
            </div>
          </div>
          <!-- 建造、翻修、大修住房 -->
          <div v-if="infoList.tqxx.tqyydm == '02'">
            <div class="concent_account_b">
              <div class="account_b_t">提取原因详情：</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == '01'">{{'本市老旧住宅小区增设电梯的'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == '02'">{{'城镇老旧小区改造'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == '04'">{{'老旧房屋改造'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == '05'">{{'大修自住住房'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">建筑面积：</div>
              <div class="account_b_m">{{ infoList.tqxx.jzmj || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋总价：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwzj || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">计划建筑费用：</div>
              <div class="account_b_m">{{ infoList.tqxx.jhjzfy || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">修建部位：</div>
              <div class="account_b_m">{{ infoList.tqxx.xjbw || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房屋地址：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwdz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">计划开工日期：</div>
              <div class="account_b_m">{{ infoList.tqxx.jhkgrq || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">计划竣工日期：</div>
              <div class="account_b_m">{{ infoList.tqxx.jhjgrq || '暂无数据'}}</div>
            </div>
          </div>
          <!-- 离休、退休 -->
          <div v-if="infoList.tqxx.tqyydm == '03'">
            <div class="concent_account_b">
              <div class="account_b_t">退休批文单位：</div>
              <div class="account_b_m">{{ infoList.tqxx.txpzdw || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">退休批文号：</div>
              <div class="account_b_m">{{ infoList.tqxx.txpwwh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">退休批文文号：</div>
              <div class="account_b_m">{{ infoList.tqxx.ltxlx || '暂无数据'}}</div>
            </div>
          </div>
          <!-- 完全丧失劳动能力，并与单位终止劳动关系 -->
          <div v-if="infoList.tqxx.tqyydm == '04'">
            <div class="concent_account_b">
              <div class="account_b_t">终止劳动关系类型：</div>
              <div class="account_b_m">{{ infoList.tqxx.zzldgxlx || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">终止劳动合同文件号：</div>
              <div class="account_b_m">{{ infoList.tqxx.zzldhtwjh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">发文单位：</div>
              <div class="account_b_m">{{ infoList.tqxx.fwdw || '暂无数据'}}</div>
            </div>
          </div>
          <!-- 偿还购房贷款本息 -->
          <div v-if="infoList.tqxx.tqyydm == '06'">
            <div class="concent_account_b">
              <div class="account_b_t">偿还贷款方式：</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 1">{{'一次性结清'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 2">{{'按年偿还'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 3">{{'本中心公积金贷款'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 4">{{'外中心公积金贷款'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 5">{{'本中心全部还清贷款一年内提取'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.chsydkfs == 6">{{'外中心全部还清贷款一年内提取'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.chsydkfs">{{'暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">贷款银行：</div>
              <div class="account_b_m">{{ infoList.tqxx.sdjksydkyh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">详细地址：</div>
              <div class="account_b_m">{{ infoList.tqxx.xxdz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">还贷地址：</div>
              <div class="account_b_m">{{ infoList.tqxx.hddz || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">借款合同号：</div>
              <div class="account_b_m">{{ infoList.tqxx.jkhth || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">贷款余额：</div>
              <div class="account_b_m">{{ infoList.tqxx.fbzxdkye || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">贷款中心名称：</div>
              <div class="account_b_m">{{ infoList.tqxx.dkzxmc || '暂无数据'}}</div>
            </div>
          </div>
          <div v-if="infoList.tqxx.tqyydm == '07'">
            <div class="concent_account_b">
              <div class="account_b_t">租房合同日期：</div>
              <div class="account_b_m">{{ infoList.tqxx.qzrq || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">租房合同编号：</div>
              <div class="account_b_m">{{ infoList.tqxx.zfhtbh || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">房租支出：</div>
              <div class="account_b_m">{{ infoList.tqxx.fzzc || '暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">租住房屋类型：</div>
              <div class="account_b_m" v-if="infoList.tqxx.zzfwlx == 1">{{'公共租赁租房'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.zzfwlx == 2">{{'商品住房'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.zzfwlx">{{'暂无数据'}}</div>
            </div>
            <div class="concent_account_b">
              <div class="account_b_t">租房政策类型：</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == 71">{{'正常职工'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == 72">{{'新市民、青年人'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == 73">{{'无房职工缴存地和工作地不在同一城市'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </div>
          </div>
          <!-- 大病医疗 -->
          <div v-if="infoList.tqxx.tqyydm == '09'">
            <div class="concent_account_b">
              <div class="account_b_t">租房政策类型：</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == 1">{{'大病医疗'}}</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == 2">{{'缴存职工遇到突发事件造成家庭生活严重困难'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </div>
          </div>
          <div v-if="infoList.tqxx.tqyydm == '99'">
            <div class="concent_account_b">
              <div class="account_b_t">提取原因详情：</div>
              <div class="account_b_m" v-if="infoList.tqxx.tqyyxq == '01'">{{'其他'}}</div>
              <div class="account_b_m" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </div>
          </div>
        </div>
      </div>
        <button class="queryButton" @click="backhome">返回</button>
      <!-- 底部 -->
      <div class="footer">
        <div class="footer-content">本服务由张掖市住房公积金管理中心提供</div>
      </div>
    </div>
  </div>
</template>
<script>
import { getUserInfo } from "../api/getUserInfo";
import tools, {
  name,
  IdNumberDesensitization,
  formatDate,
  replaceAll,
} from "../tools/tool";
import { serveAPI } from "../api/serve";
import { Toast } from "vant";

export default {
  name: "index",
  data() {
    return {
      show: true,
      accountInfo: {
        xingming: "",
        khrq: "",
        zjhm: "",
        grzhye: "",
        grzhyetm: "",
        dwmc: "",
      },
      basicInfomation: {
        gfsj: "",
        bdczh: "",
        fwzj: "",
        gfhthxyh: "",
        fwxz: "",
        jzmj: "",
        fwdj: "",
        sfdw: "",
        sfdz: "",
        sflxdh: "",
        fwdz: "",
        fwdzlx: "",
      },
      userInfo: {}, //用户信息
      publicKey1:
        "044b51c136955a625d0e7d3c41e69ee9b32f661d0a275770171ffe5d3200b4548f54ae938047e9191a63baffdd64ce3f983bd0246c873a2c63cf2e9db4178c3f3e",
      privateKey1:
        "3fce91c669c4769381384edcd8d63515362f222e9025f7f8b52f4f7ef2c38c69",
      infoList:{}
    };
  },
  created() {
    this.$store.commit("userInfo", {}); //第一次进入先清空一下缓存
    this.getUserInfo();
    this.infoList = JSON.parse(sessionStorage.getItem('infoDetail'))
  },
  mounted() {},
  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    // 获取用户信息
    getUserInfo() {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "加载中...",
      });

      getUserInfo().then((res) => {
        console.log(res);
        if (res.data == "{}" || !res.data) {
          this.$Dialog
            .alert({
              message: "当前未登录，请先登录",
            })
            .then(() => {
              // eslint-disable-next-line no-undef
              lightAppJssdk.navigation.close();
            });
        } else {
          let userInfo = res.data;
          this.$store.commit("userInfo", userInfo);
          // console.log(userInfo, "用户信息");
          this.userInfo = userInfo;
          this.getInfo(this.userInfo);
        }
      });
    },
    getInfo(userInfo) {
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            zjhm: userInfo.userCertNum,
            xxly: "2",
          }),
          this.publicKey1
        ),
        ds: "zy",
        token: tools.encryptSM2(
          userInfo.tokenNo,
          "04348740fdab0f8bcdae23a5a0298f91db94e9cff3e8bd9aa387c62e6f721bf011419918654d8eba9dd84b415d3f415d67f3a9e4a7b28a9d7eaa0d36eb7c7ad56c"
        ),
        method: "grxxDetail.action",
      };
      serveAPI
        .queryList("tygjj", "commonGjj", param)
        .then((res) => {
          res = JSON.parse(
            tools.decryptSM2(res.data.data, this.privateKey1)
          ).result;
          if (res.code == "00" && res.data) {
            this.accountInfo = res.data;
            this.accountInfo.grzhyetm = "****";
            this.$store.commit("accountInfo", res[0]);
            this.$toast.clear();
          } else {
            Toast({
              message: res.msg || "未查询到相关信息",
            });
          }
        })
        .catch(() => {
          Toast({
            message: "网络异常，请稍后重试！",
          });
        });
    },
    backhome(){
      this.$router.push({path: "/"})
    }
  },
};
</script>

<style scoped lang="less">
.page {
  width: 100%;
  min-height: 100vh;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  .container-wrap {
    // 头部背景
    .top-bg {
      display: flex;
      align-items: center;
      padding: 16px 12px 12px 22px;
      .top-bg-l {
        width: 75px;
        height: 75px;
        margin-right: 12px;
      }
      .top-bg-r {
        font-weight: 700;
        font-size: 13px;
        div {
          line-height: 28px;
        }
      }
    }
    // 主内容
    .concent {
      padding: 0 12px;

      .concent_account {
        padding: 12px;
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 0 6px #00000012;
        .concent_account_t {
          display: flex;
          justify-content: space-between;
          align-items: center;
          .account_l {
            font-weight: 700;
            font-size: 15px;
            color: #3f87f3;
          }
          .account_r {
            display: flex;
            .account_r_t {
              margin-right: 8px;
            }
            span {
              line-height: 15px;
              img {
                width: 16px;
                height: 16px;
              }
            }
          }
        }
        .concent_account_l {
          margin: 12px 0;
          border-bottom: 2px dashed #eeeeee;
        }
        .concent_account_b {
          line-height: 38px;
          border-bottom: 1px solid rgb(245, 245, 245);
          .account_b_t {
            font-weight: 700;
            font-size: 13px;
            color: #333;
          }
          .account_b_m {
            color: #999;
          }
        }
      }
      // 基本信息
      .concent_info {
        border-radius: 4px;
        background: #fff;
        box-shadow: 0 0 6px #00000012;
        margin-top: 8px;
        padding: 0 12px;
        margin-bottom: 30px;
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
      width: 90%;
      font-weight: 700;
      margin: 30px 5% 20px;
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
    position: relative;
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
