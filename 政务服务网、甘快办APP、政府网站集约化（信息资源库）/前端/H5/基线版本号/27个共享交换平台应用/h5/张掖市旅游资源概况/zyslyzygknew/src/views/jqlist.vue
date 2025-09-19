<template>
  <div class="container-wrap">
    <div class="alllist">
      <div class="listper" v-for="(item, index) in list" :key="index">
        <div class="listperline1">
          <div class="listperline1in1">{{ item.jqmc }}</div>
          <div class="listperline1in2">
            <div class="listperline1in21">景区等级：</div>
            <div class="listperline1in22">{{item.jqdj}}</div>
          </div>
        </div>
        <div class="listperline2">
          <div class="listperline21">所属区县：</div>
          <div class="listperline22">{{ item.ssqx }}</div>
        </div>
        <div class="listperline3">
          <div class="listperline3in1">
            <div class="listperline3in11">填报部门：</div>
            <div class="listperline3in12">{{ item.tbbm }}</div>
          </div>
          <div class="listperline3in2">
            <div class="listperline3in21">统计截止时间：</div>
            <div class="listperline3in22">{{ item.tjjzrq }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      list: []
    }
  },
  created () {
    this.list = JSON.parse(sessionStorage.getItem('xzlistdata'))
    console.log(this.list)
    sessionStorage.removeItem('xzlistdata')
  },
  mounted () {},
  methods: {}
}
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
.alllist {
    width: 100%;
    /* border: 1px solid red; */
    display: flex;
    flex-direction: column;
    align-items: center;
}
.listper{
    width: 93.3%;
    background: linear-gradient(180.00deg, #ebf2fc 0%, #fff 100%);
    box-shadow: 0 0 6px #0000001a;
    /* border: 1px solid red; */
    padding-top: 12px;
    padding-bottom: 12px;
    margin-top: 12px;
}
.listperline1{
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    /* border: 1px solid red; */
    margin-bottom: 12px;
}
.listperline1in1{
    width: 230px;
    font-family: "PingFang SC Heavy";
    font-weight: 900;
    font-size: 15px;
    line-height: 25px;
    text-align: left;
    color: #000;
    margin-left: 12px;
}
.listperline1in2{
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-right: 12px;
}
.listperline1in21{
    color: #000000;
    font-size: 13px;
    font-face: PingFang SC;
    font-weight: 500;
    letter-spacing: 0;
}
.listperline1in22{
    color: #ef8014;
    font-size: 13px;
    font-face: PingFang SC;
    font-weight: 500;
    letter-spacing: 0;
}
.listperline2{
    display: flex;
    flex-direction: row;
    margin-left: 12px;
    margin-bottom: 12px;
}
.listperline21{
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 20px;
    text-align: left;
    color: #000;
    /* border: 1px solid red; */
}
.listperline22{
    width: 250px;
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 20px;
    text-align: left;
    color: #000;
}
.listperline3{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}
.listperline3in1{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-left: 12px;
}
.listperline3in11{
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 25px;
    text-align: left;
    color: #000;
}
.listperline3in12{
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 25px;
    text-align: left;
    color: #000;
}
.listperline3in2{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-right: 12px;
}
.listperline3in21{
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 25px;
    text-align: left;
    color: #000;
}
.listperline3in22{
    font-family: "PingFang SC Medium";
    font-weight: 500;
    font-size: 13px;
    line-height: 25px;
    text-align: left;
    color: #000;
}
</style>