<template>
  <div class="concent_info_picker">
    <div class="upload" v-for="(item, i) in fileItem" :key="i">
      <div class="picker_t"><span>*</span> {{ item.lbmc }}</div>
      <div class="picker">
        <div
          class="picker_y"
          v-for="(items, index) in item.dzdaxxVoList"
          :key="index"
        >
          <van-image
            width="125"
            height="125"
            :src="items.url || 'data:image/png;base64,' + items.basestr"
            @click="previewImage(items, index)"
          />
          <div class="cross" @click="deleteFile(item, i, index)">
            <van-icon name="cross" />
          </div>
        </div>
        <!-- <div class="picker_y" v-if="max"> -->
        <div class="picker_y">
          <div @click="uploadFile(item, i)">
            <img src="../../assets/icon/sc.png" alt="" />
            <div class="button">上传</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ImagePreview, Toast } from "vant";

export default {
  props: {
    maxCount: {
      default: 0,
    }, //最大数量
    maxSize: {
      default: 0,
    }, //最大尺寸
    fileFormat: {
      default: 0,
    }, //文件返回格式，1：base64格式 0：返回链接
    fileCount: {
      default: 1,
    }, // 文件上传数：最大六张，最少一张
    fileItem: {
      default: Array,
    }, //目录
  },
  data() {
    return {
      show: false,
      fileList: [],
      loading: false,
      maxShow: 1,
    };
  },
  created() {
    console.log(
      this.maxCount,
      this.fileFormat,
      this.fileCount,
      this.fileItem,
      "maxCount,fileFormat,fileCount,fileItem"
    );
  },
  computed: {
    // max() {
    //   return this.fileList.length === this.maxCount ? false : true
    // }
  },
  methods: {
    // 上传文件
    uploadFile(item, i) {
      let that = this;

      console.log(item, i, "item,i");
      // eslint-disable-next-line no-undef
      lightAppJssdk.media.chooseImage({
        arg: "200",
        type: that.fileFormat.toString(),
        count: that.fileCount.toString(),
        success: function (data) {
          // console.log(data, data.picPath, 'data.picPath')
          item.dzdaxxVoList.push(...data.picPath.map((url) => ({ url })));
          that.$forceUpdate();
          // console.log(that.fileList, 'item.fileList')
          that.$emit("afterRead", item, i, that.fileItem);
          // 文件读取完成后的回调函数
        },
        fail: function (data) {
          console.log(data);
          that.loading = false;
          Toast({
            message: "网络异常，请稍后重试",
          });
        },
      });
    },
    // 删除文件
    deleteFile(item, i, index) {
      console.log(item, i, index, "index");
      // item.dzdaxxVoList.splice(index, 1)
      this.$forceUpdate();
      this.$emit("before-delete", item, i, index);
      // 文件删除前的回调函数
    },

    // 预览图片
    previewImage(item, index) {
      console.log(item, index);
      let arr = [];
      arr.push("data:image/png;base64," + item.basestr);
      console.log(arr);
      ImagePreview({
        images: arr,
      });
    },
  },
};
</script>

<style scoped lang="less">
.concent_info_picker {
  margin: 8px 0;
  background: #fff;
  .upload {
    margin-top: 12px;
  }
  .picker_t {
    margin-left: 4px;
    color: #333333;
    font-size: 13px;
    font-weight: 700;
    span {
      color: red;
    }
  }
  .picker {
    display: flex;
    width: 100%;
    overflow-x: scroll;
    border-bottom: 1px solid #ddd;
  }
  .picker_y {
    position: relative;
    width: 125px;
    height: 125px;
    flex-shrink: 0;
    margin: 12px 12px 12px 0;
    // overflow-x: scroll;
    // white-space: nowrap;
    background: #fafafa;
    border: 1px dashed #ddd;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    img {
      width: 32.5px;
      height: 32.5px;
    }
    .button {
      margin-top: 18px;
      font-weight: 700;
      font-size: 13px;
      color: #333;
    }
  }
  .cross {
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: -3px;
    right: -3px;
    width: 20px;
    height: 20px;
    background-color: rgba(0, 0, 0, 0.7);
    border-radius: 0 0 0 12px;
    border-radius: 50px;
    cursor: pointer;
    /deep/ .van-icon {
      color: #fff;
    }
  }
}
</style>
