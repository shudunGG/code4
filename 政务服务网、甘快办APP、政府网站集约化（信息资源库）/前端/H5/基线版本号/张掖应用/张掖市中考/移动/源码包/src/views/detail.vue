<template>
  <div class="container-wrap">
    <div class="top">
      <!-- <div class="container-top">
        <div>白银市2023年中考成绩查询平台</div>
      </div> -->
      <div class="result">
        <div class="title"><span>查询结果</span></div>
        <table border="1">
          <tr>
            <td class="key">统编学号</td>
            <td colspan="3">{{ list[0].xh }}</td>
          </tr>
          <tr>
            <td class="key">身份证号</td>
            <td colspan="3">{{ list[0].sfzh }}</td>
          </tr>
          <tr>
            <td class="key">姓名</td>
            <td colspan="3">{{ list[0].xm }}</td>
          </tr>
          <tr>
            <td class="key">语文</td>
            <td>{{ list[0].yw }}</td>
            <td class="key">数学</td>
            <td>{{ list[0].sx }}</td>
          </tr>
          <tr>
            <td class="key">英语</td>
            <td>{{ list[0].yy }}</td>
            <td class="key">化学</td>
            <td>{{ list[0].hx }}</td>
          </tr>
          <tr>
            <td class="key" >道德与法治</td>
            <td rowspan="2">{{ list[0].ddfz_ls }}</td>
            <td class="key" >生物学</td>
            <td rowspan="2" >{{ list[0].sw_dl }}</td>
          </tr>
          <tr>
            <td class="key">历史</td>
            <td class="key">地理</td>
          </tr>
          <tr>
            <td class="key">综合素质评价</td>
            <td>{{ list[0].zhpj }}</td>
            <td class="key">照顾加分</td>
            <td>{{ list[0].jf }}</td>
          </tr>
          <tr>
            <td class="key">总成绩</td>
            <td colspan="3">{{ list[0].zf }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div class="message">本服务由张掖市教育局提供</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { Toast } from "vant";
import IndexApi from "@/api/index";
import sm2 from "../utils/sm2";
export default {
  data() {
    return {
      loading: false,
      error: false,
      finished: false,
      pageNum: 1,
      count: 0,
      list: [],
      name: JSON.parse(this.$route.query.name),
      value: JSON.parse(this.$route.query.value),
    };
  },
  computed: {
    ...mapState(["detail", "total"]),
  },
  created() {
    // this.name = JSON.parse(this.$route.query.name)
    this.renderList();
  },
  filters: {
    getCard(cardStr) {
      if (cardStr.length !== 18) {
        return "暂无数据";
      } else {
        let star = "*";
        const len = cardStr.toString().length - 2;
        for (let i = 1; i < len; i++) {
          star = star + "*";
        }
        return cardStr.substring(0, 1) + star + cardStr.substring(17, 18);
      }
    },
    getName(name) {
      if (name === "" || name === undefined || name === null) {
        return "暂无数据";
      } else {
        return name.replace(/.(?=.)/g, "*");
      }
    },
  },
  methods: {
    renderList() {
      this.list = this.detail;
      this.count = this.total;
      if (this.list.length >= this.count) {
        this.finished = true;
      } else {
        this.finished = false;
      }
    },
    queryList(value) {
      let params = {
        pageNum: sm2.encrypt(this.pageNum),
        pageSize: sm2.encrypt("10"),
        map: sm2.encrypt(
          JSON.stringify({
            name: JSON.stringify({
              type: 2,
              content: this.name,
              left: "",
              right: "",
            }),
            sfzh: JSON.stringify({
              type: 2,
              content: this.value,
              left: "",
              right: "",
            }),
          })
        ),
      };
      IndexApi.queryList(params)
        .then((res) => {
          res = JSON.parse(sm2.decrypt(res.data.dataResult));
          console.log(res, "00000000000");
          this.count = res.totalCount;
          if (value === "add") {
            this.list = this.list.concat(res.dataList);
          } else {
            this.list = res.dataList;
          }
          if (this.list.length >= this.count) {
            this.finished = true;
          } else {
            this.finished = false;
          }
          this.loading = false;
        })
        .catch(() => {
          this.error = true;
          this.loading = false;
          Toast({
            message: "分页加载失败",
          });
        });
    },
    onLoad() {
      if (this.list.length >= this.count) {
        this.finished = true;
      } else {
        this.pageNum += 1;
        this.queryList("add");
      }
    },
  },
};
</script>

<style lang="less" scoped>
.result {
  margin: 24px 16px 0;
  /* height: 443px; */
  background-color: #fff;
  box-shadow: 0px 2px 10px 0px rgba(153, 153, 153, 0.2);
  border-radius: 4px;
  box-sizing: border-box;
  padding: 16px;
}

.title {
  width: 100%;
  height: 36px;
  font-family: PingFangSC-Semibold;
  font-size: 16px;
  color: #000;
  font-weight: 600;
  border-bottom: 1px solid #e8e8e8;
}

.title span {
  display: block;
  width: 72px;
  height: 100%;
  box-sizing: border-box;
  border-bottom: 2.5px solid #3f87f3;
  text-align: center;
}

table {
  width: 100%;
  text-align: center;
  border-color: #e8e8e8;
  margin-top: 12px;
  font-family: PingFangSC-Regular;
  font-size: 13px;
  color: #333;
  font-weight: 400;
}

table td {
  width: 25%;
  height: 44px;
  box-sizing: border-box;
  padding: 0 3px;
}

table td.key {
  background-color: #f5f5f5;
}

.zhu {
  width: 100%;
  margin-top: 16px;
  font-family: PingFangSC-Regular;
  font-size: 13px;
  color: #333;
  font-weight: 400;
}

.message {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 14px;
    text-align: center;
    color: #ccc;
    margin: 16px auto;
  }
</style>
