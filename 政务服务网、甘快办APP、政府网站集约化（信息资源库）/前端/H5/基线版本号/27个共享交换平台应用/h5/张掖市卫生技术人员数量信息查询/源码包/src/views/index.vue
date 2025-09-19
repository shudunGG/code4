<template>
  <div class="container-wrap">
    <div style="background: #ffffff; margin-top: 16px; padding-top: 10px">
      <!-- ECharts 图表容器 -->
      <div ref="chartContainer" style="width: 100%; height: 290px"></div>
    </div>
    <div class="footer">
      <div class="footer-content">本服务由甘肃省张掖市提供</div>
    </div>
  </div>
</template>
<script>
import tools from "../tools/tool.js";
import { showToast, showLoadingToast } from "vant";
import { request } from "../tools/request.js";
import { ref, reactive, onBeforeMount } from "vue";
import * as echarts from "echarts"; // 引入 ECharts
export default {
  setup() {
    const state = reactive({
      key: "0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b",
      key2: "3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b",
    });
    const chartContainer = ref(null);
    onBeforeMount(() => {
      state.toast = showLoadingToast({
        message: "加载中...",
        forbidClick: true,
        loadingType: "spinner",
        duration: 0,
      });
      const params = {
        params: tools.sm2Encrypt(
          JSON.stringify({
            normGuid: "a1b10b2c-bbc7-4696-988a-11521acf0f01",
            fieldList: [],
          }),
          state.key
        ),
      };
      request({
        app_id: "zyjtyyjkrzoeeih",
        interface_id: "zyjtyyjk",
        biz_content: JSON.stringify(params),
      }).then(
        (res) => {
          state.toast.close();
          let resStart;
          resStart = JSON.parse(res);
          res = JSON.parse(tools.sm2Decrypt(resStart.data, state.key2));
          if (res.status.text === "请求成功" && res.custom.result) {
            state.rawData = res.custom.result.cockpitData;
            console.log(state.rawData, "1");
            const myChart = echarts.init(chartContainer.value);
            const option = {
              tooltip: {
                trigger: "axis",
                show: false,
              },
              legend: {
                data: ["注册执业医师、助理医师", "注册护士"],
                selectedMode: false, // 禁用图例的点击交互功能
              },
              xAxis: {
                type: "category",
                data: state.rawData.map((item) => item.nf), // 使用年份作为 x 轴数据
              },
              yAxis: {
                type: "value",
                name: "单位 (人)", // Y 轴的单位显示在这里
                nameLocation: "end", // 使单位显示在最上方
                nameGap: 16, // 控制单位与 Y 轴的距离
                nameRotate: 0, // 使单位与 Y 轴垂直
                // axisLabel: {
                //   formatter: (value) => {
                //     // 在这里添加单位（比如单位是 "人"）
                //     return value + "人";
                //   },
                // },
              },
              grid: {
                // 调整 grid 配置，增加左侧的 margin，避免 Y 轴标签被遮挡
                left: "17%", // 让左侧有更多空间
                right: "6%", // 可以调整右侧空间，避免 X 轴重叠
                top: "20%", // 如果顶部也被遮挡，增加顶部的空间
                bottom: "15%", // 如果底部有问题，增加底部空间
              },
              series: [
                {
                  name: "注册执业医师、助理医师",
                  type: "bar",
                  data: state.rawData.map((item) => item.zczyyszlys), // 注册执业医师数据
                  emphasis: {
                    focus: "series",
                  },
                  itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                      { offset: 0, color: "#247cfa" }, // 渐变色的开始颜色
                      { offset: 1, color: "#41be94" }, // 渐变色的结束颜色
                    ]),
                  },
                  silent: true,
                  label: {
                    show: true, // 显示数字
                    position: "top", // 设置数字显示在柱形图的顶部
                    color: "#000", // 设置数字颜色
                    fontSize: 10, // 设置字体大小
                    rotate: 45, // 设置数字倾斜显示的角度
                  },
                },
                {
                  name: "注册护士",
                  type: "bar",
                  data: state.rawData.map((item) => item.zchs), // 注册护士数据
                  emphasis: {
                    focus: "series",
                  },
                  itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                      { offset: 0, color: "#f59045" }, // 渐变色的开始颜色
                      { offset: 1, color: "#f50" }, // 渐变色的结束颜色
                    ]),
                  },
                  silent: true,
                  label: {
                    show: true, // 显示数字
                    position: "top", // 设置数字显示在柱形图的顶部
                    color: "#000", // 设置数字颜色
                    fontSize: 10, // 设置字体大小
                    rotate: 45, // 设置数字倾斜显示的角度
                  },
                },
              ],
            };

            // 设置配置项
            myChart.setOption(option);
          } else {
            showToast("暂无数据");
          }
        },
        () => {
          state.toast.close();
          showToast("查询失败，请重试!");
        }
      );
    });
    return {
      chartContainer,
    };
  },
};
</script>
<style lang="less" scoped>
.buttoms {
  height: 45px;
  border-radius: 4px;
  background: #f6f7f9;
  display: flex;
  justify-content: space-evenly;
  margin: 0 16px;

  button {
    background: #f6f7f9;
    font-family: "PingFang SC Bold";
    font-weight: 600;
    font-size: 15px;
    text-align: center;
    color: #000;
    margin: 8px 0px;
    padding: 0px 5px;
  }
  .highlight {
    background: #fff !important;
    border: 1px solid #cbcbcb;
    color: #3266c2 !important;
    border-radius: 5px;
  }
}
.container-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: #f6f6f8;
  overflow: auto;
  .card-list-title {
    position: relative;
    padding-left: 11px;
  }
  .card-list-picker {
    padding-left: 11px;
  }
  .card-list-title:before {
    content: "*";
    color: red;
    position: absolute;
    left: 2px;
    bottom: -2px;
  }
  ::v-deep(.van-picker__toolbar) {
    border-bottom: 1px solid #e5e5e5;
  }
  ::v-deep(.van-picker__cancel) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker__confirm) {
    color: #3f87f3;
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 15px;
  }
  ::v-deep(.van-picker-column__item) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 17px;
    text-align: center;
    color: rgba(51, 51, 51, 0.4);
  }
  ::v-deep(.van-picker-column__item--selected) {
    font-family: "Microsoft YaHei";
    font-weight: 400;
    font-size: 19px;
    text-align: center;
    color: #333;
  }
  .top-bg {
    height: 140px;
    background: url("../assets/img/bg.png");
    background-size: 100% 100%;
    overflow: hidden;
    position: absolute;
    left: 0;
    right: 0;
    div {
      margin-top: 47px;
      font-family: "Microsoft YaHei Bold";
      font-weight: bold;
      font-size: 18px;
      text-align: center;
      color: #fff;
    }
  }
  .card-wrap {
    border-radius: 4px;
    background: #fff;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.16);
    padding: 0 12px;
    position: relative;
    z-index: 3;
    margin-top: 12.5px;
    .upload-wrap {
      display: flex;
      justify-content: space-between;
    }
    &.card-wrap-last {
      margin-bottom: 69px;
    }
    .card-title {
      height: 37.5px;
      display: flex;
      align-items: center;
      font-family: "Microsoft YaHei";
      font-weight: 400;
      font-size: 18px;
      text-align: left;
      color: #333;
      border-bottom: 1px solid #e8e8e8;
      img {
        width: 14.3px;
        margin-right: 7px;
      }
    }
    .card-list {
      padding: 8px 0;
      border-bottom: 1px solid #e8e8e8;
      input {
        width: 100%;
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #999;
      }
      input::-webkit-input-placeholder {
        color: #999;
      }
      input::-moz-input-placeholder {
        color: #999;
      }
      input::-ms-input-placeholder {
        color: #999;
      }
      .card-list-title {
        font-family: "Microsoft YaHei";
        font-weight: 400;
        font-size: 15px;
        text-align: left;
        color: #333;
        margin-bottom: 8px;
      }
      .card-list-picker {
        display: flex;
        justify-content: space-between;
        align-items: center;
        > span {
          font-family: "Microsoft YaHei";
          font-weight: 400;
          font-size: 15px;
          text-align: left;
          color: #999;
        }
      }
    }
    .queryButton {
      width: 100%;
      margin-top: 30px;
      border-radius: 4px;
      font-size: 18px;
      background: #1677ff;
      border-color: #1677ff;
      color: #fff;
    }
    .resetButton {
      width: 100%;
      margin-top: 12px;
      margin-bottom: 18px;
      border-radius: 4px;
      font-size: 18px;
      border: 0.37px solid #bcbcbc;
      color: #333;
    }
  }
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/img/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;
    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 7px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
</style>
