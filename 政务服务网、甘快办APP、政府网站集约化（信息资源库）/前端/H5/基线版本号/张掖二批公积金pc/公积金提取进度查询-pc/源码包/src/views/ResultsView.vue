<template>
  <div>
    <section class="query-container">
      <div class="pagetitle">信息详情</div>
      <div class="title-line"></div>
      <el-form
        style="padding: 0.2rem 0.4rem"
        action=""
      >
        <div class="flex">
          <el-form-item label="申请人开户银行名称" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.sqrkhyhmc || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="申请人收款账号" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.sqrskzh || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="申请人收款户名" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.sqrskhm || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="提取编号" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.tqbh || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="申请时间" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.sqsj || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="申请提取金额小写" style="width: 45%">
            <div class="el-input__inner">{{ infoList.tqxx.sqtqjexx || '暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="提取类型" style="width: 45%">
            <div class="el-input__inner" v-if="infoList.tqxx.tqlx == 11">{{'部分提取' }}</div>
            <div class="el-input__inner" v-if="infoList.tqxx.tqlx == 22">{{'销户提取' }}</div>
            <div class="el-input__inner" v-if="!infoList.tqxx.tqlx">{{'暂无数据'}}</div>
          </el-form-item>
          <el-form-item label="提取账户类别" style="width: 45%">
            <div class="el-input__inner" v-if="infoList.tqxx.tqzhlb == 1">{{'个人' }}</div>
            <div class="el-input__inner" v-if="infoList.tqxx.tqzhlb == 2">{{'开发商' }}</div>
            <div class="el-input__inner" v-if="!infoList.tqxx.tqzhlb">{{'暂无数据'}}</div>
          </el-form-item>
          <!-- ----------- -->
          <template v-if="infoList.tqxx.tqyydm == '01'">
            <el-form-item label="购房人姓名" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.gfrxm || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="购房人身份证号" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.gfrsfzh || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="不动产账号" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.bdczh || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋地址类型" style="width: 45%">
              <div class="el-input__inner" v-if="infoList.tqxx.fwdzlx == 0">{{'本地购房'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.fwdzlx == 1">{{'异地购房'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.fwdzlx">{{'暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="售房单位" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.sfdw || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="售房地址" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.sfdz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="售房联系电话" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.sflxdh || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋地址" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.fwdz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="购房合同或协议号" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.gfhthxyh || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋现状" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.fwxz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="建筑面积" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.jzmj || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋单价" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.fwdj || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋总价" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.fwzj || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="购房时间" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.gfsj || '暂无数据'}}</div>
            </el-form-item>
          </template>
          <!-- 建造、翻修、大修住房 -->
          <template v-if="infoList.tqxx.tqyydm == '02'">
            <el-form-item label="提取原因详情" style="width: 45%">
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == '01'">{{'本市老旧住宅小区增设电梯的'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == '02'">{{'城镇老旧小区改造'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == '04'">{{'老旧房屋改造'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == '05'">{{'大修自住住房'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="建筑面积" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.jzmj || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋总价" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.fwzj || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="计划建筑费用" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.jhjzfy || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="修建部位" style="width: 45%">
              <div class="el-input__inner">{{ infoList.tqxx.xjbw || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="房屋地址" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.fwdz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="计划开工日期" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.jhkgrq || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="计划竣工日期" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.jhjgrq || '暂无数据'}}</div>
            </el-form-item>
          </template>
          <!-- 离休、退休 -->
          <template v-if="infoList.tqxx.tqyydm == '03'">
            <el-form-item label="退休批文单位" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.txpzdw || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="退休批文号" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.txpwwh || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="退休批文文号" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.ltxlx || '暂无数据'}}</div>
           </el-form-item>
          </template>
          <!-- 完全丧失劳动能力，并与单位终止劳动关系 -->
          <template v-if="infoList.tqxx.tqyydm == '04'">
            <el-form-item label="终止劳动关系类型" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.zzldgxlx || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="终止劳动合同文件号" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.zzldhtwjh || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="发文单位" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.fwdw || '暂无数据'}}</div>
            </el-form-item>
          </template>
          <!-- 偿还购房贷款本息 -->
          <template v-if="infoList.tqxx.tqyydm == '06'">
            <el-form-item label="偿还贷款方式" style="width: 45%">
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 1">{{'一次性结清'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 2">{{'按年偿还'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 3">{{'本中心公积金贷款'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 4">{{'外中心公积金贷款'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 5">{{'本中心全部还清贷款一年内提取'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.chsydkfs == 6">{{'外中心全部还清贷款一年内提取'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.chsydkfs">{{'暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="贷款银行" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.sdjksydkyh || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="详细地址" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.xxdz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="还贷地址" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.hddz || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="借款合同号" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.jkhth || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="贷款余额" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.fbzxdkye || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="贷款中心名称" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.dkzxmc || '暂无数据'}}</div>
            </el-form-item>
          </template>
          <template v-if="infoList.tqxx.tqyydm == '07'">
            <el-form-item label="租房合同日期" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.qzrq || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="租房合同编号" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.zfhtbh || '暂无数据'}}</div>
           </el-form-item>
            <el-form-item label="房租支出" style="width: 45%">
              
              <div class="el-input__inner">{{ infoList.tqxx.fzzc || '暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="租住房屋类型" style="width: 45%">
              
              <div class="el-input__inner" v-if="infoList.tqxx.zzfwlx == 1">{{'公共租赁租房'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.zzfwlx == 2">{{'商品住房'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.zzfwlx">{{'暂无数据'}}</div>
            </el-form-item>
            <el-form-item label="租房政策类型" style="width: 45%">
              
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == 71">{{'正常职工'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == 72">{{'新市民、青年人'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == 73">{{'无房职工缴存地和工作地不在同一城市'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </el-form-item>
          </template>
          <!-- 大病医疗 -->
          <template v-if="infoList.tqxx.tqyydm == '09'">
            <el-form-item label="租房政策类型" style="width: 45%">
              
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == 1">{{'大病医疗'}}</div>
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == 2">{{'缴存职工遇到突发事件造成家庭生活严重困难'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
            </el-form-item>
          </template>
          <template v-if="infoList.tqxx.tqyydm == '99'">
            <el-form-item label="提取原因详情" style="width: 45%">
              
              <div class="el-input__inner" v-if="infoList.tqxx.tqyyxq == '01'">{{'其他'}}</div>
              <div class="el-input__inner" v-if="!infoList.tqxx.tqyyxq">{{'暂无数据'}}</div>
           </el-form-item>
          </template>
        </div>
      </el-form>
      <button class="btn" @click="resetForm">返回</button>
    </section>
  </div>
</template>
<script>
export default {
  name: "ResultsView",
  data() {
    return {
      userinfo: {},
      show: true,
      infoList:{}
    };
  },
  created() {
    this.infoList = JSON.parse(sessionStorage.getItem('infoDetail'))
  },
  methods: {
    resetForm() {
     this.$router.push("/")
    },
  },
};
</script>

<style scoped lang="less">
@media screen and (max-width: 800px) {
  .yzm {
    width: 130px;
  }
}
.pagetitle {
  font-weight: 600;
  padding-left: 10px;
  margin-left: 5px;
}
.title-line{
  border: 1.5px solid rgb(244, 244, 244);
  width: auto;
  margin: 20px 0 20px 15px;
}
.flex {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
/deep/ .el-form-item__label{
  font-family: "PingFang SC";
  font-weight: 400;
  font-size: 15px;
  text-align: left;
  color: #333;
}
/deep/ .el-input__inner{
  border-radius: 4px !important;
  // box-shadow: 0 0 12px #00000012;
  font-family: "PingFang SC";
  font-weight: 400;
  font-size: 14px;
  text-align: left;
  color: gray
}
.btn{
  width: 100px;
  height: 40px;
  border-radius: 2px;
  background: #1677ff;
  border: none;
  border-radius: 4px;
  margin-left: 35px;
  font-family: "PingFang SC";
  font-weight: 400;
  font-size: 15px;
  text-align: center;
  color: #fff;
}
</style>
