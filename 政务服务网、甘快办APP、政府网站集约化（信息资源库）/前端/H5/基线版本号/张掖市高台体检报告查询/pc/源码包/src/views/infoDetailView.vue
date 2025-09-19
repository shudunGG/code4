<template>
  <div class="bottom-content">
    <div class="content_top">
      <div class="content_cx">
        <div></div>
        报告详情
      </div>
    </div>
    <div class="content_from">
      <div class="card">
        <div class="card_text">
          <!-- <div>
            就诊医院：<span>{{ info.hospitalName || "/" }}</span>
          </div> -->
          <div>
            检测日期：<span>{{ cfcxmzzxxList[0].date || "/" }}</span>
          </div>
          <div>
            报告类型：<span class="moneybgc">{{
              info.reportType == 0 ? "检验报告" : "检验报告" || "/"
            }}</span>
          </div>
          <div>检测列表：</div>
        </div>
        <div class="card_text">
          <!-- <el-tooltip
            class="item"
            effect="dark"
            :content="$store.state.hospitalInformation.hospitalAdd"
            placement="bottom"
          >
            <div>
              医院地址：<span>{{
                $store.state.hospitalInformation.hospitalAdd
              }}</span>
            </div>
          </el-tooltip> -->

          <div>
            检验医生：<span>{{ cfcxmzzxxList[0].checkName || "/" }}</span>
          </div>
          <div>
            报告医生：<span>{{ cfcxmzzxxList[0].reportName || "/" }}</span>
          </div>
        </div>
        <div class="card_text">
          <!-- <el-tooltip
            class="item"
            effect="dark"
            :content="cfcxmzzxxList[0].checkItem"
            placement="bottom"
          > -->
            <div>
              检查项目：<span>{{ info.checkItem || "/" }}</span>
            </div>
          <!-- </el-tooltip> -->
          <div>
            申请医生：<span>{{ cfcxmzzxxList[0].applyName || "/" }}</span>
          </div>
        </div>
      </div>
      <div class="table-responsive">
        <el-table
          :data="tableData"
          style="width: 100%"
          :headerCellStyle="{ background: '#f6f6f6' }"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
        >
          <el-table-column prop="checkItem" label="项目名称" align="center">
          </el-table-column>
          <el-table-column prop="result" label="检查结果" align="center">
          </el-table-column>
          <el-table-column prop="range" label="参考值" align="center">
          </el-table-column>
          <el-table-column prop="unit" label="单位" align="center">
          </el-table-column>
        </el-table>
      </div>
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
import tools from "../tools/tool";
export default {
  data() {
    return {
      info: {},
      pdfUrl: null,
      tableData: [],
      cfcxmzzxxList: [{}],
      loading: false,
    };
  },

  created() {
    this.info = this.$store.state.infoDetal
    console.log(this.info, "222");
  },
  mounted() {
    this.getcfcxmzmx(this.info.id);
  },
  methods: {
    // 高台县医院报告详情查询
    getcfcxmzmx(val) {
      this.loading = true;
      let params = {
        results: tools.encrypt(
          JSON.stringify({
            authCode: this.$store.state.userInfo.authCode,
            token: this.$store.state.userInfo.token, //token移动端和pc端传值
            id: val, //（0：近三个月1：近半年2：最近一年3：其他时段）
            dataType: `gtyy`,
          })
        ),
      };
      serveAPI
        .queryList("zyyyjkrzmpgab", "zyyycheckInfo", params)
        .then((res) => {
          console.log(res, "高台县医院报告详情查询");
          if (
            res.code == 200 &&
            res.success &&
            JSON.parse(tools.decrypt(res.data)).length > 0
          ) {
            this.loading = false;
            let result = JSON.parse(tools.decrypt(res.data))
            console.log(result, "高台县医院报告详情查询");
            this.cfcxmzzxxList = result;
            this.tableData = result;
          } else {
            this.loading = false;
            this.$router.replace("/no-results");
            this.$message.info(res.message || "未查询到相关信息");
          }
        })
        .catch(() => {
          this.loading = false;
          this.$router.replace("/no-results");
          this.$message.error("网络异常，请稍后重试！");
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
      span {
        font-size: 14px;
        margin-left: 20px;
      }
    }
  }
  .content_from {
    .card {
      display: flex;
      padding: 20px;
      justify-content: space-between;
      .card_text {
        margin-right: 100px;
        width: 33.3%;
        div {
          font-weight: 700;
          font-size: 16px;
          line-height: 45px;
          color: #333;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
          span {
            color: #999;
          }
        }
      }
      .card_text:last-child {
        margin: 0;
      }
    }
  }
}
.cards {
  padding: 0 20px;
  font-weight: 700;
  font-size: 16px;
  line-height: 45px;
  color: #333;

  span {
    color: #999;
  }
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
  .card_text{
    margin: 0 !important;
    width: 100% !important;
  }
  .card{
    flex-wrap: wrap;
  }
}
</style>
