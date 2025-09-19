<template>
  <div class="content">
    <vue-pdf-embed
      :source="source"
      v-for="i in pdfNum"
      :page="i"
      :key="i"
      textLayer
      ref="vuePdfRef"
    ></vue-pdf-embed>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import VuePdfEmbed from 'vue-pdf-embed'
import { ref } from 'vue'
const route = useRoute()
const source = route.query.pdfSrc
const pdfNum = Number(route.query.pdfNum)
const vuePdfRef = ref(null)

</script>

<style lang="less" scoped>
.content {
  position: relative;
  .queryButton {
    width: 100%;
    margin-top: 30px;
    border-radius: 4px;
    font-size: 18px;
    background: #1677ff;
    border-color: #1677ff;
  }
}
</style>
