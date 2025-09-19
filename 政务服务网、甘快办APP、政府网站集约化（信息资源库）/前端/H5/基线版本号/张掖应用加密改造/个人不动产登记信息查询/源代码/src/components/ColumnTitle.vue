<template>
  <div class="ColumnTitle">
    <p>——&nbsp;&nbsp;{{ msg }}&nbsp;&nbsp;——</p>
  </div>
</template>

<script>
export default {
  name: "ColumnTitle",
  props: {
    msg: String
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
.ColumnTitle {
  line-height: 52px;
  text-align: center;
  font-size: 18px;
  font-weight: normal;
}
</style>
