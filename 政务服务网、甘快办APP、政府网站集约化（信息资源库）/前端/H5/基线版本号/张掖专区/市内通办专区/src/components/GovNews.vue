<template>
  <div class="GovNews">
    <router-link :to="'/about/'+item.id">
    <div
      v-if="
        item.images.length > 1 || (item.images.length == 1 && item.id % 2 == 0)
      "
    >
      <p class="title">{{ item.title }}</p>
      <div class="imglist">
        <div>
          <van-image
            :width="item.images.length == 1 ? '100%' : '32%'"
            height="100"
            lazy-load
            fit="cover"
            class="img"
            v-for="(img, index) in item.images"
            :key="index"
            :src="img.url"
            v-show="index < 3"
          />
        </div>
      </div>
      <p class="from">
        <span>{{ item.depart }}</span
        ><span class="time">{{ item.date }}</span>
      </p>
    </div>
    <div class="rightPic" v-else>
      <div class="article">
        <p class="title">{{ item.title }}</p>
        <p class="from">
          <span>{{ item.depart }}</span
          ><span class="time">{{ item.date }}</span>
        </p>
      </div>
      <van-image
        width="100%"
        height="100"
        lazy-load
        fit="cover"
        :src="item.images[0].url"
        class="img"
        v-if="item.images.length > 0"
      />
    </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "GovNews",
  props: {
    item: Object
  }
};
</script>

<style lang="less" scoped>
.GovNews {
  margin: 0px 15px;
  padding: 8px 0px 16px 0px;
  border-top: 1px solid #eee;
  .title {
    font-size: 17px;
    font-weight: 400;
  }
  .from {
    font-size: 12px;
    .time {
      margin-left: 20px;
      color: #666;
    }
  }
  .imglist {
    .img:first-child {
      margin: 0px;
    }
    .img {
      margin-left: 2%;
    }
  }
  &.onePicCenter {
  }
  .rightPic {
    display: flex;
    justify-content: space-between;
    .article {
      padding-right: 18px;
    }
  }
}
</style>
