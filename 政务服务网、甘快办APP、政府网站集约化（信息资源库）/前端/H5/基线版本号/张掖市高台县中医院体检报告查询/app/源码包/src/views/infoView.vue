<template>
  <div class="page">
    <div class="page_wrap">
      <!-- 循环展示医院报告列表 -->
      <div
        class="container-wrap"
        v-for="(item, index) in cfcxmzzxxList"
        :key="index"
        @click="queryList(item)"
      >
        <div class="pickertitle">
          <div class="title">{{ item.hospitalName || "/" }}</div>
          <div class="time">{{ item.date || "/" }}</div>
        </div>
        <div class="pickertext">
          <div>
            报告类型：{{ item.reportType == 0 ? "检验报告" : "检验报告" }}
          </div>
          <div>检测科室：{{ item.deptName || "/" }}</div>
          <div>检查项目：{{ item.checkItem || "/" }}</div>
        </div>
      </div>
    </div>

    <empty v-show="show" />

    <!-- 底部 -->
    <div class="footer">
      <div class="footer-content">本服务由高台县提供</div>
    </div>
  </div>
</template>
<script>
import tools, {
  name,
  IdNumberDesensitization,
  formatDate,
  replaceAll,
} from "../tools/tool";
import { serveAPI } from "../api/serve";
import { Toast } from "vant";
import empty from "../components/MyEmpty/emptyView.vue"; // 导入自定义空数据视图组件
export default {
  components: {
    empty, // 本地注册空数据视图组件
  },
  name: "index",
  data() {
    return {
      cfcxmzzxxList: [], // 医院报告列表数据
      showdialog: false, // 控制对话框显示的变量
      message: "", // 用于显示对话框中的信息
      show: false, // 控制空数据视图显示的变量
    };
  },
  created() {
    this.getcfcxmzzxx(); // 页面创建时获取医院报告列表数据
  },

  methods: {
    name,
    IdNumberDesensitization,
    formatDate,
    replaceAll,
    // 获取高台县医院报告列表数据
    getcfcxmzzxx() {
      Toast.loading({
        duration: 0, // 持续展示 toast
        message: "加载中...",
        forbidClick: true,
      });
      let params = {
        results: tools.encryptSM2(
          JSON.stringify({
            authCode: "",
            token:
              this.$store.state.userInfo.tokenNo ||
              "naturalToken7156850363506678594320084065620021177651618483264323524208036061", // 使用的token
            timeType: this.$store.state.basicInfo.dateType, // 查询的时间类型
            reportType: this.$store.state.basicInfo.type || "0", // 报告类型
            startTime: "", // 开始时间
            endTime: "", // 结束时间，
            dataType: "gtzyy",
          })
        ),
      };
      serveAPI
        .queryList("zyyyjkrzmpgab", "zyyycheckList", params)
        .then((res) => {
          console.log(res, "高台县医院报告列表查询");
          if (
            res.code == 200 &&
            res.success &&
            JSON.parse(tools.decryptSM2(res.data)).length > 0
          ) {
            Toast.clear();
            let result = JSON.parse(tools.decryptSM2(res.data));
            console.log(result, "result");
            this.cfcxmzzxxList = result.sort((a, b) => {
              return new Date(b.date) - new Date(a.date);
            }); // 更新报告列表数据
          } else {
            Toast.clear();
            this.show = true; // 显示空数据视图
          }
        })
        .catch(() => {
          Toast.clear();
          this.show = true; // 显示网络异常提示
          Toast({
            message: "网络异常，请稍后重试！",
          });
        });
    },
    // 点击报告列表项跳转到报告详情页
    queryList(val) {
      console.log(val);
      this.$router.push({
        name: "infoDetail",
        query: { info: JSON.stringify(val) },
      });
    },
  },
};
</script>

<style scoped lang="less">
.page {
  padding: 12px 12px 0;
  width: 100%;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
  .container-wrap {
    border-radius: 4px;
    background: linear-gradient(180deg, #fff 0%, #fff 100%);
    box-shadow: 0 0 12px #237dff26;
    padding: 12px;
    margin-bottom: 12px;
    .pickertitle {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-weight: 700;
      margin-bottom: 8px;
      .title {
        font-size: 15px;
        color: #3f87f3;
      }
      .time {
        font-size: 12px;
        color: #999;
      }
    }
    .pickertext {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      div {
        font-weight: 700;
        font-size: 12px;
        color: #333;
      }
    }
  }
  // 底部固定区域
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
}
</style>