<template>
  <section class="result-container">
    <div class="logo"><img src="../assets/img/success.png" alt="" /></div>
    <div class="info1">暂无数据</div>
    <div class="form-btn info">
      <input
      style="margin-left:25px"
        type="button"
        class="submit"
        value="返回首页"
        @click="queryList()"
      />
    </div>
  </section>
</template>

<script>
export default {
  name: "NoResultsView",
  methods: {
    queryList() {
      this.$router.push({path: "/"})
    },
  },
};
</script>
<style scoped lang="less">
.logo {
  width: 100%;
  display: flex;
  justify-content: center;
  img{
    width: 45%;
  }
}
.form-btn {
    height: 0.45rem;
    width: 100%;
    text-align: center;
}
.form-btn input {
    width: 1.25rem;
    height: 0.40rem;
    outline: none;
    border: none;
    text-align: center;
    cursor: pointer;
}

.form-btn input.submit {
    background: #1677ff;
    color: #ffffff;
    margin-right: 0.35rem;
}
.info {
  margin: 10px 0 20px 0;
  line-height: 0.2rem;
  width: 100%;
  color: #666666;
  font-size: 0.2rem;
  font-weight: bold;
  text-align: center;
}
.info1 {
  margin-top: 0.4rem;
  line-height: 0.5rem;
  width: 100%;
  color: #666666;
  font-size: 18px;
  text-align: center;
}
</style>
