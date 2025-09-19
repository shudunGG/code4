<template>
  <section class="result-container">
    <h2 class="title">查询结果</h2>
    <div class="result">
      <template>
        <table v-for="(item, index) in resultsObj.dataList" :key="index">
          <tr>
            <td class="tab_bt">统计截止日期</td>
            <td>{{ item.riqi || "暂无数据" }}</td>
            <td class="tab_bt">区县名称</td>
            <td>{{ item.diqu || "暂无数据" }}</td>
          </tr>
          <tr>
            <td class="tab_bt">区县结婚登记人数</td>
            <td>{{ item.jiehun || "暂无数据" }}</td>
            <td class="tab_bt">区县离婚登记人数</td>
            <td>{{ item.lihun || "暂无数据" }}</td>
          </tr>
        </table>
        <div class="block">
          <el-pagination
            prev-text="上一页"
            next-text="下一页"
            background
            :hide-on-single-page="true"
            layout="prev, pager, next, jumper"
            :page-size="5"
            @current-change="currentChange"
            :total="resultsObj.totalCount"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage3"
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
import sm2 from "../utils/sm2";
export default {
  name: "ResultsView",
  data() {
    return {
      formData: {},
      params: {
        pageSize: 5,
        pageNum: 1,
      },
      list: [],
      resultsObj: {},
      currentPage3: 1,
    };
  },

  created() {
    this.formData = this.$route.params.res
      ? JSON.parse(this.$route.params.res)
      : undefined;
    if (this.formData === undefined) {
      this.formData = {
        diqu: "",
        name: "",
        code: "",
        selectName: "",
        selectArr: [],
      };
    }
    this.params.pageNum = 1;
    this.params.pageSize = 5;
    this.selectResult();
  },
  beforeMount() {
    this.params.pageNum = 1;
    this.selectResult();
  },

  methods: {
    desensitizedName,
    desensitizedCommon,

    // 页码改变触发
    currentChange(pageNum) {
      this.params.pageNum = pageNum;
      // 根据新的页码重新请求
      this.selectResult();

      // 页码重置为1，确保在翻页后可以查询到数据
      // this.params.pageNum = 1;
    },
    handleSizeChange() {
      this.params.pageNum = this.currentPage3;
      // 根据新的页码重新请求
      this.selectResult();

      // 页码重置为1，确保在翻页后可以查询到数据
      // this.params.pageNum = 1;
    },
    // 请求获取结果并渲染
    async selectResult() {
      if (this.formData.diqu) {
        this.params = {
          pageNum: sm2.encrypt(this.params.pageNum),
          pageSize: sm2.encrypt("5"),
          map: sm2.encrypt(
            JSON.stringify({
              diqu: JSON.stringify({
                type: 2,
                content: this.formData.diqu.replace(/\s+/g, ""),
                left: "",
                right: "",
              }),
            })
          ),
        };
      } else {
        this.params = {
          pageNum: sm2.encrypt(this.params.pageNum),
          pageSize: sm2.encrypt("5"),
          queryType: "WILDCARD_QUERY",
        };
      }

      const loading = this.$loading({
        text: "加载中...",
        spinner: "el-icon-loading",
        background: "rgba(255,255,255,0.9)",
        target: document.getElementById("resultDiag"),
      });
      try {
        const res = await IndexApi.queryList(this.params);
        if (res.code === "200" && res.success) {
          this.list = JSON.parse(sm2.decrypt(res.data.dataResult));
          console.log(this.list);
          
          if (this.list.dataList.length === 0) {
            this.$router.push("/index/no-results");
          } else {
            this.resultsObj = this.list;
            this.$forceUpdate();
            loading.close();
            return;
          }
        } else {
          throw new Error("查询失败");
        }
      } catch (e) {
        this.$message({
          type: "error",
          message: "网络繁忙，请稍后再试",
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
        pageSize: 5,
        queryType: "WILDCARD_QUERY",
      };
      this.currentPage3 = 1;
      this.formData = JSON.parse(this.$route.params.res);
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