<template>
  <div class="container-wrap">
    <div class="container">
      <div class="containertitle">
        <div class="containertitlebor"></div>
        <div class="containertitletext">现代农业产业</div>
        <!-- <img class="containertitleimg" src="../assets/images/xy.png" alt=""> -->
      </div>
      <div class="top_box">
        <div class="top_wrap top_ltqy">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            农业产业龙头企业数量
          </div>
          <div class="top_wrap_num">
            {{ topData.nycyltqysl || '0' }}家
          </div>
        </div>
        <div class="top_wrap top_tszz">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            优势特色种植规模面积
          </div>
          <div class="top_wrap_num">
            {{ topData.ystszzgmmj || '0' }}万亩
          </div>
        </div>
        <div class="top_wrap top_spyb">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            “三品一标”农产品数量
          </div>
          <div class="top_wrap_num">
            {{ topData.spybncpsl || '0' }}个
          </div>
        </div>
        <div class="top_wrap top_tsyz">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            优势特色养殖规模数量
          </div>
          <div class="top_wrap_num">
            {{ topData.ystsyzgmsl || '0' }}万头
          </div>
        </div>
        <div class="top_wrap top_gwqy">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            “甘味”企业商标品牌数量
          </div>
          <div class="top_wrap_num">
            {{ topData.gwqysbppsl || '0' }}个
          </div>
        </div>
        <div class="top_wrap top_cyzz">
          <div class="top_wrap_line"></div>
          <div class="top_wrap_title">
            全产业链产值总额
          </div>
          <div class="top_wrap_num">
            {{ topData.qcylczze || '0' }}亿元
          </div>
        </div>
      </div>
      <div class="bottom_box">
        <div class="bottom_wrap bottom_ltqy" @click="goDetail(0)">
          <div class="bottom_wrap_block">
            <img src="../assets/images/ltqy.png" alt="" class="bottom_icon">
            市级以上龙头企业数量
          </div>
          <div class="bottom_wrap_block">
            {{ bottomData.ltqysl }}家
            <img src="../assets/images/arrow.png" class="bottom_arrow" alt="">
          </div>
        </div>
        <div class="bottom_wrap bottom_hzs" @click="goDetail(1)">
          <div class="bottom_wrap_block">
            <img src="../assets/images/hzs.png" alt="" class="bottom_icon">
            农民专业合作社数量
          </div>
          <div class="bottom_wrap_block">
            {{ bottomData.nmzyhzssl }}家
            <img src="../assets/images/arrow.png" class="bottom_arrow" alt="">
          </div>
        </div>
        <div class="bottom_wrap bottom_jtnc" @click="goDetail(2)">
          <div class="bottom_wrap_block">
            <img src="../assets/images/jtnc.png" alt="" class="bottom_icon">
            家庭农场数量
          </div>
          <div class="bottom_wrap_block">
            {{ bottomData.jtncsl }}家
            <img src="../assets/images/arrow.png" class="bottom_arrow" alt="">
          </div>
        </div>
      </div>
    </div>
    <div class="provider">
      本服务由甘肃省张掖市提供
    </div>
  </div>
</template>

<script>
// import IndexApi from "@/api/index";
// import { Toast } from "vant";
import * as echarts from "echarts";
import { color } from "echarts/core";
import requestJpaas from "../utils/request";
const { getData } = requestJpaas;
import { tools } from "../tools/tool";
export default {
  name: "MyChart",
  data() {
    return {
      topData:{},
      bottomData: {},
      detailData: []
    };
  },
  created() {},
  mounted() {
    this.getTopData()
    this.getBottomData();
    this.getlist();
  },
  methods: {
    async getTopData() {
      const data = {
        normGuid: "bc270d16-039a-4577-8e37-b6fe8ba1a7ca",
        fieldList: [],
      };
      const res = await getData({
        params: tools.encryptSm2(JSON.stringify(data)),
      });
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result
        .cockpitData;
      this.topData = newres[newres.length - 2];
    },
    async getBottomData() {
      const data = {
        normGuid: "6ac9e356-45a7-4573-b400-43ab2c1e74ff",
        fieldList: [],
      };
      const res = await getData({
        params: tools.encryptSm2(JSON.stringify(data)),
      });
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result
        .cockpitData;
      console.log('==============',newres);
      this.bottomData = newres[0];
    },
    async getlist() {
      const data = {
        normGuid: "11500784-acd9-41c0-9850-4fd4ba2b020f",
        fieldList: [],
      };
      const res = await getData({
        params: tools.encryptSm2(JSON.stringify(data)),
      });
      let newres = JSON.parse(tools.decryptSm2(res.data)).custom.result
      .cockpitData;
      // console.log("------------", newres);
      this.detailData = newres
    },
    goDetail(e) {
      sessionStorage.setItem('detailData', JSON.stringify(this.detailData))
      this.$router.push({
        path:'/listDetail',
        query: {
          type: e
        }
      })
    }
  },
};
</script>
<style scoped>
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f6f6f8;
  overflow: auto;
}
.container {
  width: 100%;
  /* border: 1px solid red; */
  margin-top: 12px;
  background: #fff;
  padding-bottom: 33px;
  padding-top: 16px;
}
.containertitle {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.containertitlebor {
  width: 3.5px;
  height: 15px;
  border-radius: 3.5px;
  background: #3266c2;
  margin-left: 12px;
}
.containertitletext {
  font-family: "PingFang SC Bold";
  font-weight: 700;
  line-height: 21px;
  font-size: 15px;
  text-align: left;
  color: #000;
  margin-left: 8px;
}
.containertitleimg {
  width: 7px;
  height: 12px;
  margin-left: 8px;
}

.top_box {
  width: 375px;
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 12px;
  flex-wrap: wrap;
  margin: 12px auto 0;
}

.top_wrap {
  width: 167px;
  height: 74px;
  position: relative;
  background-size: 100% 100%;
  background-position: center center;
  margin-bottom: 16px;
  box-sizing: border-box;
  padding-top: 12px;
  padding-left: 15px;
  color: #fff;
  font-size: 15px;
  line-height: 21px;
}

.top_ltqy {
  background-image: url(../assets/images/ltqybg1.png);
}
.top_tszz {
  background-image: url(../assets/images/tszzbg.png);
}
.top_spyb {
  background-image: url(../assets/images/spybbg1.png);
}
.top_tsyz {
  background-image: url(../assets/images/tsyzbg.png);
}
.top_gwqy {
  background-image: url(../assets/images/gwqybg.png);
}
.top_cyzz {
  background-image: url(../assets/images/czzebg.png);
}

.top_wrap_line {
  width: 3px;
  height: 12px;
  position: absolute;
  top: 12px;
  left: 0;
  background-color: rgba(255, 255, 255, 0.5);
}

.top_wrap_title {
  margin-bottom: 8px;
}

.top_spyb .top_wrap_title, .top_gwqy .top_wrap_title {
  line-height: 25px;
}

.top_spyb .top_wrap_num, .top_gwqy .top_wrap_num {
  position: absolute;
  top: 41px;
  right: 12px;
}

.bottom_wrap {
  width: 351px;
  height: 49px;
  margin: 0 auto 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 15px;
  color: #fff;
  line-height: 21px;
  background-size: 100% 100%;
  background-position: center center;
  box-sizing: border-box;
  padding: 0 12px;
}

.bottom_ltqy {
  background-image: url(../assets/images/ltqybg2.png);
}

.bottom_hzs {
  background-image: url(../assets/images/hzsbg.png);
}

.bottom_jtnc {
  background-image: url(../assets/images/jtncbg.png);
}

.bottom_wrap_block {
  display: flex;
  align-items: center;
}

.bottom_icon {
  width: 25px;
  height: 25px;
  margin-right: 12px;
}

.bottom_arrow {
  width: 6px;
  height: 10px;
  margin-left: 8px;
}

.provider {
  width: 100%;
  height: 58px;
  background-image: url(../assets/images/bottomBg.png);
  background-position: center center;
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 15px;
  color: #999;
  position: absolute;
  left: 0;
  bottom: 0;
}
</style>
