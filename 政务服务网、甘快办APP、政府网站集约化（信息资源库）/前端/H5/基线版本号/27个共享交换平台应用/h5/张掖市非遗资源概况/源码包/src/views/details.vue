<template>
  <div>
    <div class="app">
      <div class="list">
        <div class="listItem" v-for="(item, index) in list" :key="index">
          {{ index + 1 }}.{{ item.xmmc }}
        </div>
      </div>
    </div>
    <div :class="list.length < 13 ? 'footer' : ''">
      <div class="footerIn">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, reactive } from "vue";
import { request } from "../tools/request";
import tools from "../tools/tool";
import { useRoute } from "vue-router";
const route = useRoute();
const { sm2Encrypt, sm2Decrypt } = tools;
const list = reactive([]);
const title = route.query.title;
console.log(route.query);
const getList = async () => {
  const data = {
    params: sm2Encrypt(
      JSON.stringify({
        normGuid: "7b9cb06a-3de2-4cf7-9e8b-c3503751b09d",
        fieldList: "[]",
      }),
      "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b"
    ),
  };
  const params = {
    interface_id: "zyjtyyjk",
    app_id: "zyjtyyjkrzoeeih",
    biz_content: JSON.stringify(data),
  };
  let res = await request(params);
  if (res.code == 200) {
    const result = sm2Decrypt(
      res.data,
      "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b"
    );
    const result1 = JSON.parse(result);
    console.log(result1);
    if (result1.custom.result.cockpitData.length) {
      if (title == "国家级") {
        list.push(
          ...result1.custom.result.cockpitData.filter(
            (item) => item.xmdj == "国家级"
          )
        );
      } else {
        list.push(
          ...result1.custom.result.cockpitData.filter(
            (item) => item.xmdj == "省级"
          )
        );
      }
    }
  }
};
onMounted(() => {
  getList();
});
</script>
<style scoped>
.app {
  margin: 12px 0;
  padding: 12px;
  background: #fff;
}
.list {
  background: #f5f8ff;
  padding: 0 12px 12px;
  overflow: hidden;
}
.listItem {
  height: 45px;
  background: #fff;
  display: flex;
  align-items: center;
  margin-top: 12px;
  font-weight: 500;
  font-size: 15px;
  color: #000;
  padding-left: 12px;
}
.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
}
.footerIn {
  background: url("../assets/img/footer.png") no-repeat;
  background-size: 100%;
  height: 58.5px;
  font-family: "PingFang SC Medium";
  font-weight: 500;
  font-size: 15px;
  text-align: center;
  color: #999;
  line-height: 58.5px;
}
</style>