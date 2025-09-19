<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <template>
        <table v-for="(item, index) in resultsObj.dataList" :key="index">
          <tr>
            <td class="tab_bt">品种</td>
            <td>{{ item.pinzhong || "暂无数据" }}</td>
            <td class="tab_bt">期数</td>
            <td>{{ item.qishu || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">日期</td>
            <td>{{ item.date || "暂无数据" }}</td>
            <td class="tab_bt">单位</td>
            <td >{{ item.danwei || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">全市采购均价</td>
            <td>{{ item.quanshicaigou || "暂无数据" }}</td>
            <td class="tab_bt">全市销售均价</td>
            <td >{{ item.quanshixiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">甘州区采购均价</td>
            <td>{{ item.ganzhoucaigou || "暂无数据" }}</td>
            <td class="tab_bt">甘州区销售均价</td>
            <td >{{ item.ganzhouxiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">临泽县采购均价</td>
            <td>{{ item.linzecaigou || "暂无数据" }}</td>
            <td class="tab_bt">临泽县销售均价</td>
            <td >{{ item.linzexiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">高台县采购均价</td>
            <td>{{ item.gaotaicaigou || "暂无数据" }}</td>
            <td class="tab_bt">高台县销售均价</td>
            <td >{{ item.gaotaixiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">山丹县采购均价</td>
            <td>{{ item.shandancaigou || "暂无数据" }}</td>
            <td class="tab_bt">山丹县销售均价</td>
            <td >{{ item.shandanxiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">民乐县采购均价</td>
            <td>{{ item.minlecaigou || "暂无数据" }}</td>
            <td class="tab_bt">民乐县销售均价</td>
            <td >{{ item.minlexiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">肃南县采购均价</td>
            <td>{{ item.sunancaigou || "暂无数据" }}</td>
            <td class="tab_bt">肃南县销售均价</td>
            <td >{{ item.sunanxiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">粮油储备库有限责任公式采购均价</td>
            <td>{{ item.liangyoucaigou || "暂无数据" }}</td>
            <td class="tab_bt">粮油储备库有限责任公式销售均价</td>
            <td >{{ item.liangyouxiaoshou || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">军粮供应有限责任公司采购均价</td>
            <td>{{ item.junliangcaigou || "暂无数据" }}</td>
            <td class="tab_bt">军粮供应有限责任公司销售均价</td>
            <td >{{ item.junliangxiaoshou || "暂无数据" }}</td>
          </tr>
          <!--          <tr>-->
<!--            <td class="tab_bt">简介</td>-->
<!--            <td colspan="3">{{ item.jianjie || "暂无数据" }}</td>-->
<!--          </tr>-->
        </table>
        <div class="block">
          <!--分页区域-->
<!--          <el-pagination-->
<!--            prev-text="上一页"-->
<!--            next-text="下一页"-->
<!--            background-->
<!--            :hide-on-single-page="true"-->
<!--            layout="prev, pager, next"-->
<!--            :page-size="params.pageSize"-->
<!--            @current-change="currentChange"-->
<!--            :total="resultsObj.totalCount"-->
<!--          >-->
<!--          </el-pagination>-->
          <el-pagination prev-text="上一页" next-text="下一页" background :hide-on-single-page="true" layout="prev, pager, next, jumper"
                         :page-size="params.pageSize" @current-change="currentChange" :total="resultsObj.totalCount"
                         @size-change="handleSizeChange" :current-page.sync="currentPage3"
          >
          </el-pagination>
        </div>
      </template>
    </div>
  </section>
</template>

<script>
import IndexApi from "@/api/index";
import { desensitizedName, desensitizedCommon } from "@/tools/desensitized";

export default {
  name: "ResultsView",
  data() {
    return {
      formData: {},
      params: {
        pageSize: 5,
        pageNum: 1,
      },
      resultsObj: {},
      currentPage3: 1
    };
  },

  created() {
    this.formData = this.$route.params.res;
    if (this.formData === undefined) {
      this.formData = {
        name: "",
        code: "",
        selectName: "",
        selectArr: [],
      };
    }
    this.selectResult(this.params);
  },
  beforeMount() {
    this.selectResult(this.params);
  },

  methods: {
    desensitizedName,
    desensitizedCommon,

    // 页码改变触发
    currentChange(pageNum) {
      this.params.pageNum = pageNum;
      // 根据新的页码重新请求
      this.selectResult(this.params);

      // 页码重置为1，确保在翻页后可以查询到数据
      // this.params.pageNum = 1;
    },
    handleSizeChange() {
      this.params.pageNum = this.currentPage3;
      // 根据新的页码重新请求
      this.selectResult(this.params);

      // 页码重置为1，确保在翻页后可以查询到数据
      // this.params.pageNum = 1;
    },
    // 请求获取结果并渲染
    async selectResult(params) {
      if (this.formData.name) {
        this.params.pinzhong = encodeURIComponent(
          JSON.stringify({
            type: 2,
            content: this.formData.name,
            left: "",
            right: "",
          })
        );
      } else {
        this.params = {
          pageSize: 5,
          pageNum: 1,
        };
      }
      const loading = this.$loading({
        text: "Loading...",
        spinner: "el-icon-loading",
        background: "rgba(255,255,255,0.9)",
        target: document.getElementById('resultDiag')
      });
      try {
        // if(!this.formData.name) {
        //   params = {
        //     pageSize: 5,
        //     pageNum: 1,
        //   }
        // }
        const res = await IndexApi.queryList(params);
        if (res.code === "200" && res.success) {
          console.log(res);
          if (res.data.dataResult.dataList.length === 0) {
            this.$router.push("/index/no-results");
          } else {
            this.resultsObj = res.data.dataResult;
            this.$forceUpdate();
          }
        } else {
          throw new Error("查询失败");
        }
      } catch (e) {
        this.$message({
          type: "error",
          message: "查询失败，请重试或联系管理员",
        });
        this.$router.push({ name: "Index" });
      }
      loading.close();
    },
  },
  watch: {
    $route() {
      this.params = {
        pageNum: 1,
        pageSize: 5
      }
      this.currentPage3 = 1
      this.formData = this.$route.params.res;
      this.selectResult(this.params); // 这是我ajax获取用户信息的方法
    },
  },
};
</script>

<style scoped>
.result-container table {
  margin-bottom: 0.2rem;
}

.result-container table td {
  background: #ffffff;
}

.result-container table thead td {
  font-size: 0.16rem;
  font-weight: bold;
}

.result-container table td,
.result-container table th {
  border-right: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
  font-size: 0.14rem;
  padding: 0 0.14rem;
}

.tips {
  margin-top: 0.3rem;
  color: #999999;
  text-align: center;
}

.block {
  text-align: center;
}
</style>