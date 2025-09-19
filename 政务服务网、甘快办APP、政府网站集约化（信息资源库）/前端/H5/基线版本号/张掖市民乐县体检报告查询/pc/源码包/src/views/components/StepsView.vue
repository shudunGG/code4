<template>
  <div>
    <el-steps :active="active" align-center>
      <el-step title="选择科室"></el-step>
      <el-step title="选择医生"></el-step>
      <el-step title="确认预约信息"></el-step>
      <el-step title="预约成功"></el-step>
    </el-steps>
  </div>
</template>

<script>
export default {
  props: {
    active: Number
  }
}
</script>

<style scoped lang="less">
/deep/ .el-step__icon.is-text {
  width: 40px;
  height: 40px;
}
/deep/ .el-step.is-horizontal .el-step__line {
  top: 20px;
}
/deep/ .el-step.is-center .el-step__line {
  left: 65%;
  right: -35%;
}
/deep/ .el-step__head.is-process {
  .el-step__icon.is-text {
    background: #3F87F3;
    border: none;
    color: #fff;
  }
}
/deep/ .el-step__title.is-process {
  color: #3F87F3;
}
</style>
