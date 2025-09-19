<template>
  <div
    class="bottom-content"
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
  >
    <div class="content_top">
      <div class="content_cx">
        <div></div>
        报告列表
      </div>
    </div>
    <div class="content_from">
      <div
        class="card"
        v-for="(item, index) in cfcxmzzxxList"
        :key="index"
        @click="queryList(item)"
      >
        <div class="card_border">
          <img src="../assets/img/zyiyuan.png" alt="" srcset="" />
          <div class="card_text">
            <div class="title">{{ item.hospitalName || "/" }}</div>
            <div>
              报告类型：{{ item.reportType == 0 ? "检验报告" : "检验报告" }}
            </div>
            <div>检测科室：{{ item.deptName }}</div>
            <div>检查项目：{{ item.checkItem }}</div>
          </div>
        </div>
        <div class="card_time">{{ item.date }}</div>
      </div>
      <NoResult v-if="noresult" />
      <div class="button">
        <el-button class="btn" type="primary" @click="$router.go(-1)"
          >返回</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import { serveAPI } from "@/api/serve";
import tools, { formatDate } from "../tools/tool";
export default {
  data() {
    return {
      cfcxmzzxxList: [],
      total: 0,
      loading: false,
      noresult: false,
    };
  },

  created() {
    this.getcfcxmzzxx();
  },
  methods: {
    formatDate,
    // 高台县医院报告列表查询
    getcfcxmzzxx() {
      console.log(
        this.formatDate(this.$store.state.basicInfo.timeType[0]),
        this.formatDate(this.$store.state.basicInfo.timeType[1])
      );
      this.loading = true;
      let params = {
        results: tools.encrypt(
          JSON.stringify({
            authCode: this.$store.state.userInfo.authCode,
            token: this.$store.state.userInfo.token, //token移动端和pc端传值
            timeType: this.$store.state.basicInfo.timeTypeval, //（0：近三个月1：近半年2：最近一年3：其他时段）
            reportType: this.$store.state.basicInfo.type || "0",
            startTime: this.formatDate(this.$store.state.basicInfo.timeType[0]),
            endTime: this.formatDate(this.$store.state.basicInfo.timeType[1]),
            dataType: `gtzyy`,
            //   patientName: "王栋全",
            // cardNo: "62222519841205151X"
          })
        ),
      };
      serveAPI
        .queryList("zyyyjkrzmpgab", "zyyycheckList", params)
        .then((res) => {
          if (
            res.code == 200 &&
            res.success &&
            JSON.parse(tools.decrypt(res.data)).length > 0
          ) {
            this.loading = false;
            let result = JSON.parse(tools.decrypt(res.data));
            result.forEach((item) => {
              item.hospitalPictureBig =
                this.$store.state.hospitalInformation.hospitalImg2;
            });
            this.cfcxmzzxxList = result.sort((a, b) => {
              return new Date(b.date) - new Date(a.date);
            }); // 更新报告列表数据
            console.log(this.cfcxmzzxxList, "this.cfcxmzzxxList");
          } else {
            this.loading = false;
            this.noresult = true;
          }
        })
        .catch(() => {
          this.loading = false;
          this.noresult = true;
        });
    },
    queryList(val) {
      console.log(val);
      this.$store.commit("setInfoDetal", val);
      this.$router.push({
        name: "infoDetail",
      });
    },
  },
};
</script>

<style scoped lang="less">
.bottom-content {
  background: #fff;
  box-shadow: 0 3px 18px #0000000d;
  margin-bottom: 40px;
  padding: 30px;
  font-size: 16px;
  .content_top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #eee;
    padding-bottom: 17px;
    .content_cx {
      display: flex;
      align-items: center;
      font-weight: 700;
      color: #333;
      div {
        width: 3px;
        height: 15px;
        margin-right: 10px;
        border-radius: 1.5px;
        background: #1e82d8;
      }
    }
  }
  .content_from {
    .card {
      display: flex;
      border-bottom: 1px solid #eee;
      padding: 20px 0;
      position: relative;
      cursor: pointer;
      height: 171px;
      .card_border {
        display: flex;
        // align-items: center;
        img {
          width: 200px;
          height: 130px;
        }
        .card_text {
          margin-left: 20px;

          .title {
            font-weight: 700;
            font-size: 16px;
            line-height: 32px;
            color: #333;
          }
          div {
            font-weight: 700;
            font-size: 14px;
            line-height: 28px;
            color: #999;
          }
        }
      }
      .card_time {
        position: absolute;
        right: 0;
        bottom: 31px;
        font-weight: 700;
        font-size: 16px;
      }
    }
  }
}
/deep/ .el-pagination {
  padding-top: 20px;
  text-align: center;
}
.button {
  text-align: center;

  /deep/ .el-button {
    width: 180px;
    height: 50px;
    border-radius: 0;
    margin-top: 8px;
    border: none !important;
  }
  .btn {
    background: #e2edfe;
    box-shadow: 0 3px 18px #0000000d;
    color: #3f87f3;
  }
}
@media screen and (max-width: 750px) {
  .card {
    flex-direction: column;
    height: unset !important;
  }
  .card_time {
    position: unset !important;
  }
}
</style>
