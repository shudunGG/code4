<template>
  <div class="list_box">
    <div class="cont" @click="jump(item)" v-for="(item,index) in list" :key="index">
        <!-- 带说明列表项  listType == '1'-->
      <div class="list_item" v-if="item.listType == '1'">
        <div class="item_title2">{{item.title}}</div>
        <div class="item_desc">{{item.desc}}</div>
      </div>
      <!-- 带图标的说明列表项 listType == '2'-->
      <div class="list_item" v-if="item.listType == '2'">
        <img class="iconimg" :src="item.icon" alt>
        <div class="item_title">{{item.title}}</div>
        <div class="item_desc">{{item.desc}}</div>
      </div>
        <!-- 带跳转的列表项  listType == '3'-->
      <div class="list_item" v-if="item.listType == '3'">
        <div class="item_title2">{{item.title}}</div>
        <!-- <div class="item_desc">{{item.desc}}</div> -->
        <img class="right" src="./imgs/right.png" alt>
      </div>
       <!-- 带说明，带跳转的列表项  listType == '4'-->
      <div class="list_item" v-if="item.listType == '4'">
        <div class="item_title2">{{item.title}}</div>
        <div class="item_desc">{{item.desc}}</div>
        <img class="right" src="./imgs/right.png" alt>
      </div>
      <!-- 带图标，说明，跳转的列表项  listType == '5'-->
      <div class="list_item" v-if="item.listType == '5'">
        <img class="iconimg" :src="item.icon" alt>
        <div class="item_title3">{{item.title}}</div>
        <div class="item_desc">{{item.desc}}</div>
        <img class="right" src="./imgs/right.png" alt>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  //url("https://tfs.alipayobjects.com/images/partner/T1bNRtXlpXXXXXXXXX")
  props: {
    list: {
      type: Array,
      default() {
        return [
          {
            icon:"",
            title: "标题",
            desc: "说明文字",
            listType: ""
          }
        ];
      }
    },
  },
  components: {},
  mounted() {},
  methods: {
    jump(event) {
      this.$emit('jump', event);
    },
  }
};
</script>
<style lang="less" scoped>
.right {
    width: 16px;
    height: 20px;
    margin-left: 10px;
}
.list_item {
  width: 100%;
  height: 52px;
  background-color: #fff;
  display: flex;
  padding: 0 16px 0 16px;
  box-sizing: border-box;
  //   justify-content: center;
  align-items: center;
  border-bottom: 1px solid #f5f5f5;
  .item_img {
    flex: 1;
    text-align: center;
  }
  .iconimg {
    width: 30px;
    height: 30px;
  }
  .item_title {
    font-family: PingFangSC-Regular;
    font-size: 17px;
    color: #333333;
    letter-spacing: 0.58px;
    text-align: left;
    flex: 2;
    margin-left: 16px;
  }
   .item_title2 {
    font-family: PingFangSC-Regular;
    font-size: 17px;
    color: #333333;
    letter-spacing: 0.58px;
    text-align: left;
    flex: 6;
  }
   .item_title3 {
    font-family: PingFangSC-Regular;
    font-size: 17px;
    color: #333333;
    letter-spacing: 0.58px;
    text-align: left;
    flex: 6;
    margin-left: 16px;
  }
  .item_desc {
    font-family: PingFangSC-Regular;
    font-size: 16px;
    color: #999999;
    letter-spacing: 0.58px;
    text-align: right;
    flex: 3;
  }
}
</style>