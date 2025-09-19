<template>
  <div class="box">
    <div id="scroll-container" 
      @touchstart.stop="handlerTouchStart"
      @touchmove.stop="handlerTouchMove"
      @touchend.stop="handlerTouchEnd"
      ref="scrollContainer"
      :class="{'transition': isTransition}"
    >
      <slot></slot>
      <div v-show="isDisplay.load"  :class="['load']">
        <div :class="['load']" v-if="load">
          <van-loading  size="12" />加载中
        </div>
        <div v-else>{{ text }}</div>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  data () {
    return {
      startLocation: '', // 记录鼠标点击的位置
      moveDistance: 0,  // 记录移动的位置
      distance: '', // 记录移动的距离
      isTransition: false,
      isDisplay: {
        refresh: false,
        load: false
      },
      load: false,
      text: '上拉加载下一分类...',
      emitEvents: new Array(2).fill(undefined)
    }
  },
  props: {
    // 能够拖拽的最大距离
    showLoading: false,
    canTouch: {
      style: Boolean,
      default: true
    },
    maxDistance: {
      style: Number,
      default: 100
    },
    // 定义触发加载刷新事件的拉伸长度
    triggerDistance: {
      style: Number,
      default: 50
    }
  },
  methods: {
    // 获取手指触屏时的屏幕Y轴位置
    handlerTouchStart (e) {
      this.isTransition = false
      this.startLocation = e.touches[0].pageY
    },
    // 获取手指移动的距离
    handlerTouchMove (e) {
      this.text = '上拉加载下一分类...'
      if(!this.canTouch) return
      // console.log(e.touches[0].pageY - this.startLocation);
      if(e.touches[0].pageY - this.startLocation > 0) return //只允许上拉
      if (this.moveDistance > this.maxDistance + 1) {
        return
      }
      this.isDisplay.load = true

      this.moveDistance = Math.floor(e.touches[0].pageY - this.startLocation)
      this.$refs.scrollContainer.style.transform = `translateY(${this.moveDistance/2}px)`
      if (-this.moveDistance <= 0 && this.isDisplay.load) {
        this.isDisplay.load = false
      }
      if(-this.moveDistance >= this.triggerDistance && this.isDisplay.load) {
        this.text = '松开立即加载...'
      }
      // 缓存加载的emit
      if (this.moveDistance < -this.triggerDistance && !this.emitEvents[1]) {
      	this.emitEvents[1] = function () { this.$emit('load', this.displayDiv) }
      }
    },
    // 获取手指松开的Y轴位置
    handlerTouchEnd (e) {
      this.isTransition = true
      
      if (Math.abs(this.moveDistance) < this.triggerDistance)  {
        this.$refs.scrollContainer.style.transform = 'translateY(0px)' 
        this.moveDistance = 0
        this.isDisplay.load = false
        return
      }
      // 清除已移动的距离
      this.moveDistance = 0
      this.$refs.scrollContainer.style.transform = 'translateY(-15px)'
      this.load = true
      // 恢复原位
      setTimeout(() => {
        // 遍历emit并执行
        this.emitEvents.forEach((fn, index) => {
          if (!fn) return
          this.emitEvents[index] = undefined
          fn.apply(this)
        })
        this.$refs.scrollContainer.style.transform = 'translateY(0px)'
        this.load = false
        this.isDisplay.load = false
      }, 500)
      
    },
    // 该方法通过$emit()传给外部组件调用 然后隐藏刷新、加载的gif图片
    displayDiv () {
      this.isDisplay.refresh = false
      this.isDisplay.load = false
    }
  }
}
</script>
<style scoped>
  #scroll-container {
    /* background-color: yellow; */
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .box{
    /* overflow: hidden; */
    min-height: 100%;
    display: flex;
    flex-direction: column;
  }
  .transition {
    transition: all .5s; 
  }
  .load {
  flex-shrink: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-size: 12px;
    color: #999;
  }

</style>

