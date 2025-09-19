<template>
  <section class="result-container">
    <div class="result">
      <h3 style="color: #3f87f3">用户信息</h3>
      <ul>
        <li>
          体检人姓名：<span>{{ name(zyyDetail.userName) || "暂无数据" }}</span>
        </li>
        <li>
          证件号码：<span>{{ IdNumberDesensitization(zyyDetail.examineeIcno) || "暂无数据" }}</span>
        </li>
        <li>
          电话：<span>{{ phone(zyyDetail.examineePhone) || "暂无数据" }}</span>
        </li>
        <li>
          体检人编号：<span>{{ zyyDetail.serviceNumber || "暂无数据" }}</span>
        </li>
      </ul>
    </div>
    <div class="result-wapper" v-for="(item, index) in tjbgDetaitl.cardInfo" :key="index" style="cursor: pointer">
      <div class="title" style="margin: 0; cursor: pointer">
        <div class="title-time" style="cursor: pointer">{{ item.longName }}</div>
      </div>
      <div v-if="index === 0" style="cursor: pointer">
        <div class="result-contain" v-for="(itemA, index) in item.data.slice(0, zjjlIndex)" :key="index" style="cursor: pointer">
          <div>
            <label style="cursor: pointer">{{ itemA.summary }}</label> <br>
            <span class="text" style="padding-top: 12px; cursor: pointer">{{ itemA.advice || "暂无数据" }}</span>
          </div>
        </div>
        <span v-if="zjjlOpen" @click="zjjlExpend(item)" style="color: #3f87f3; padding-left: 45%; display: block; padding-top: 12px; height: 30px; cursor:pointer">点击展开</span>
        <span v-else @click="zjjlPickUp" style="color: #3f87f3; padding-left: 45%; display: block; padding-top: 12px; height: 30px; cursor:pointer">点击收起</span>
      </div >
      <div v-else>
        <div class="result-contain_tjxq" v-for="(itemA, index) in item.data.slice(0, tjxqIndex)" :key="index" style="cursor: pointer">
          <div style="border-bottom: 1px solid #d8d8d8; padding: 12px 0; cursor: pointer" @click="openTJXQ(itemA, index)" v-if="!itemA.openFlage">
            <label style="cursor: pointer">{{ itemA.itemName }}</label> <label>重点关注 <span style="color: red; cursor: pointer">{{ itemA.abnormalCount }}</span> 项</label> <i class="el-icon-arrow-down"></i> <br>
          </div>
          <div style="border-bottom: 1px solid #d8d8d8; padding: 12px 0; cursor: pointer" @click="pickUpTJXQ(itemA, index)" v-else>
            <label style="cursor: pointer">{{ itemA.itemName }}</label> <label>重点关注 <span style="color: red; cursor: pointer">{{ itemA.abnormalCount }}</span> 项</label> <i class="el-icon-arrow-up"></i> <br>
          </div>
          <div class="desc" v-if="itemA.imgUrlList.length < 1" v-show="itemA.openFlage">
            <div class="header_title">
              <div class="title_desc">项目名称</div>
              <div class="title_desc">范围</div>
              <div class="title_desc">数值</div>
              <div class="title_desc">检查结果</div>
            </div>
            <div v-for="(itemc, index) of itemA.l3Dtos" :key="index" class="jcjg_desc">
              <div class="title_desc">{{ itemc.reportItemName }}</div>
              <div class="title_desc">{{ itemc.reportStandardValue }}</div>
              <div class="title_desc">{{ itemc.reportCheckValue }}</div>
              <div class="title_desc">{{ itemc.reportCheckResult }}</div>
            </div>
          </div>
          <div class="desc_yxzl" v-else v-show="itemA.openFlage" style="cursor: pointer">
            <div v-for="(itemc, index) of itemA.l3Dtos" :key="index" class="jcjg_desc" style="cursor: pointer">
              <div style="border-bottom: 1px solid #d8d8d8; padding: 12px 0"><span>印象资料：</span> <a :href='itemA.imgUrlList[0]' target="_blank" style="color: #3f87f3">点击查看</a> <br></div>
              <span style="padding: 12px 0">检查结果：</span>
              <div class="title_desc" style="padding: 0 0 12px 0">{{ itemc.reportCheckValue }}</div>
            </div>
          </div>
        </div>
        <span v-if="tjxqOpen" @click="tjxqExpend(item)" style="color: #3f87f3; padding-left: 45%; display: block; padding-top: 12px; height: 30px; cursor:pointer">点击展开</span>
        <span v-else @click="tjxqPickUp" style="color: #3f87f3; padding-left: 45%; display: block; padding-top: 12px; height: 30px; cursor:pointer">点击收起</span>
      </div>
    </div>
  </section>
</template>

<script>
import {phone, name, IdNumberDesensitization} from "@/tools/desensitized";

export default {
  data() {
    return {
      zyyDetail: {},
      tjbgDetaitl: {},
      zjjlOpen: true,
      zjjlIndex: 2,
      tjxqOpen: true,
      tjxqIndex: 2,
      tjxqDetail: false
    }
  },
  mounted() {
    this.zyyDetail = this.$store.state.zyyDetail
    this.tjbgDetaitl = this.$store.state.zyResultReportDetail.data

    console.log('----',this.zyyDetail,'===',this.tjbgDetaitl)
    if(!this.tjbgDetaitl || !this.zyyDetail.userName) {
      this.$router.push('/')
    }
  },
  methods: {
    phone,
    name,
    IdNumberDesensitization,
    // 总检结论展开
    zjjlExpend(item) {
      console.log('--', item)
      this.zjjlIndex = item.data.length
      this.zjjlOpen = false
      this.$forceUpdate()
    },
    // 总检结论收起
    zjjlPickUp() {
      this.zjjlIndex = 2
      this.zjjlOpen = true
      this.$forceUpdate()
    },

    // 体检详情展开
    tjxqExpend(item) {
      console.log('--', item)
      this.tjxqIndex = item.data.length
      this.tjxqOpen = false
      this.$forceUpdate()
    },
    // 体检结论收起
    tjxqPickUp() {
      this.tjxqIndex = 2
      this.tjxqOpen = true
      this.$forceUpdate()
    },
    // 体检详情
    openTJXQ(itemA) {
      itemA['openFlage'] = true
      this.$forceUpdate()
    },
    // 体检详情收起
    pickUpTJXQ(itemA) {
      itemA['openFlage'] = false
      this.$forceUpdate()
    }
  }
}
</script>


<style lang="less" scoped>

.desc {
  .header_title {
    display: flex;
  }

  .jcjg_desc {
    display: flex;
    padding: 0;
  }

  .title_desc {
    width: 25%;
    text-align: center;
    border-bottom: 1px solid #d8d8d8;
    color: #999;
    padding: 12px 0;
  }
}

.desc_yxzl {
  .title_desc {
    width: 100%;
    text-align: left;
    border-bottom: 1px solid #d8d8d8;
    color: #999;
    padding: 12px 0;
  }
}


.result-container {
  background: #ffffff;
  min-height: 300px;
}

.result-container::after {
  content: "";
  display: block;
  clear: both;
}

.result {
  border-radius: 5px;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
  padding: 12px;
}

.result ul li {
  font-size: 0.16rem;
  margin: 20px 0px;
  font-family: "PingFang SC Heavy";
  font-weight: 500;
  color: #333;
}

.result ul li span {
  font-size: 0.16rem;
  font-family: "PingFang SC Heavy";
  font-weight: 500;
  color: #999;
}

.result .price {
  width: 200px;
  float: right;
  margin-top: -40px;
  color: red;
  font-size: 0.2rem;
  font-family: "PingFang SC Heavy";
  font-weight: 400;
}

.submit {
  width: 180px;
  height: 40px;
  margin-top: 24px;
  border-radius: 4px;
  font-size: 18px;
  background: #4482e9;
  color: white;
  border: none;
  float: right;
}

.result-wapper {
  border-radius: 5px;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
  padding: 12px;
  margin: 15px auto;
}

.result-wapper .title {
  width: 100%;
  display: flex;
  justify-content: space-between;
  font-size: 16px;
}

.result-wapper .result-contain {
  div {
    padding: 20px 0px;
    border-bottom: 1px solid #d8d8d8;

    span {
      display: inline-block;
      vertical-align: top;
      font-size: 0.16rem;
      font-family: "PingFang SC Heavy";
      font-weight: 500;
      color: #999;
    }

    label {
      display: inline-block;
    }

    .text {
      margin-left: 20px;
    }
  }

  .result-wapper .result-contain:last-child {
    border-bottom: 0px solid white;
  }
}

.result-contain_tjxq {
  div {
    //padding: 20px 0px;
    //border-bottom: 1px solid #d8d8d8;

    span {
      display: inline-block;
      vertical-align: top;
      font-size: 0.16rem;
      font-family: "PingFang SC Heavy";
      font-weight: 500;
      color: #999;
    }

    label {
      display: inline-block;
    }

    .text {
      margin-left: 20px;
    }
  }
}
</style>