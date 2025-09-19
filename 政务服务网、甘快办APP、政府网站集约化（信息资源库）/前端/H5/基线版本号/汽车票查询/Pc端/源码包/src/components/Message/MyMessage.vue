<template>
  <transition name="el-message-fade" @after-leave="handleAfterLeave" @after-enter="handleAfterEnter">
    <div
      :class="[
        'el-message',
        type && !iconClass ? `el-message--${type}` : '',
        center ? 'is-center' : '',
        showClose ? 'is-closable' : '',
        customClass,
        custom ? 'custom' : ''
      ]"
      :style="positionStyle"
      v-show="visible"
      @mouseenter="clearTimer"
      @mouseleave="startTimer"
      role="alert"
    >
      <div class="tips" v-if="custom"><i></i></div>
      <template v-else>
        <i :class="iconClass" v-if="iconClass"></i>
        <i :class="typeClass" v-else></i>
      </template>
      <slot>
        <p v-if="!dangerouslyUseHTMLString" class="el-message__content">{{ message }}</p>
        <p v-else v-html="message" class="el-message__content"></p>
      </slot>
      <i v-if="showClose" class="el-message__closeBtn el-icon-close" @click="close"></i>
    </div>
  </transition>
</template>

<script type="text/babel">
const typeMap = {
  success: 'success',
  info: 'info',
  warning: 'warning',
  error: 'error'
}

export default {
  data() {
    return {
      visible: false,
      message: '',
      duration: 2000,
      type: 'info',
      iconClass: '',
      customClass: '',
      onClose: null,
      showClose: true,
      closed: false,
      verticalOffset: 20,
      timer: null,
      dangerouslyUseHTMLString: false,
      center: false,
      custom: true,
      animateStatus: false
    }
  },

  computed: {
    typeClass() {
      return this.type && !this.iconClass ? `el-message__icon el-icon-${typeMap[this.type]}` : ''
    },
    positionStyle() {
      return {
        top: `${this.verticalOffset}px`
      }
    }
  },

  watch: {
    closed(newVal) {
      if (newVal) {
        this.visible = false
      }
    }
  },

  methods: {
    handleAfterLeave() {
      this.$destroy(true)
      this.$el.parentNode.removeChild(this.$el)

      this.animateStatus = false
    },
    handleAfterEnter() {
      this.animateStatus = true
    },
    close() {
      this.closed = true
      if (typeof this.onClose === 'function') {
        this.onClose(this)
      }
    },

    clearTimer() {
      clearTimeout(this.timer)
    },

    startTimer() {
      if (this.duration > 0) {
        this.timer = setTimeout(() => {
          if (!this.closed) {
            this.close()
          }
        }, this.duration)
      }
    },
    keydown(e) {
      if (e.keyCode === 27) {
        // esc关闭消息
        if (!this.closed) {
          this.close()
        }
      }
    }
  },
  mounted() {
    this.startTimer()
    document.addEventListener('keydown', this.keydown)
  },
  beforeDestroy() {
    document.removeEventListener('keydown', this.keydown)
  }
}
</script>
<style lang="less">
.animate__rubberBand {
  animation-name: rubberBand1;
}
.animate__animated {
  animation-duration: 0.5s;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
  transform-origin: 0 50%;
  //animation-iteration-count:2;
  //animation-direction:alternate;
}
.el-message {
  z-index: 3000 !important;
  &.custom {
    display: block;
    max-width: 6.15rem;

    .tips {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      margin-bottom: 5px;
      i {
        display: block;
        width: 0.24rem;
        height: 0.24rem;
      }
      &:after {
        height: 0.24rem;
        line-height: 0.24rem;
        display: block;
        font-size: 16px;
        color: #333333;
        letter-spacing: 0;
        font-weight: 500;
        margin-left: 0.16rem;
      }
    }
    &.el-message--warning {
      background: #fffaeb;
      border: 1px solid rgba(255, 226, 141, 1);
      border-radius: 4px;
      .tips {
        i {
          background: url('@/assets/img/message/warning.svg') 0 0 no-repeat;
        }
        // 警告
        &:after {
          content: '\6e29\99a8\63d0\793a';
        }
      }
    }
    &.el-message--info {
      background: #edf5ff;
      border: 1px solid rgba(151, 195, 255, 1);
      border-radius: 4px;
      .tips {
        i {
          background: url('@/assets/img/message/info.svg') 0 0 no-repeat;
        }
        // 信息
        &:after {
          content: '\4fe1\606f\ff01';
        }
      }
    }
    &.el-message--success {
      background: #edf5ff;
      border: 1px solid rgba(151, 195, 255, 1);
      border-radius: 4px;
      .tips {
        i {
          background: url('@/assets/img/message/success.svg') 0 0 no-repeat;
        }
        // 成功
        &:after {
          content: '\6210\529f\ff01';
        }
      }
    }
    &.el-message--error {
      background: #fff0ef;
      border: 1px solid rgba(255, 171, 166, 1);
      border-radius: 4px;
      .tips {
        i {
          background: url('@/assets/img/message/error.svg') 0 0 no-repeat;
        }
        //错误
        &:after {
          content: '\9519\8bef\ff01';
        }
      }
    }
    .el-message__content {
      margin-left: 0.4rem;
      font-size: 14px;
      color: #666666;
      letter-spacing: 0;
      line-height: 22px;
      font-weight: 400;
    }
    .el-message__closeBtn {
      top: 15px;
    }
  }
}
@keyframes rubberBand1 {
  0% {
    opacity: 0;
    top: 0;
    transform: translate(-50%);
  }
  100% {
    top: 20px;
    opacity: 1;
    transform: translate(-50%);
  }
}
</style>
