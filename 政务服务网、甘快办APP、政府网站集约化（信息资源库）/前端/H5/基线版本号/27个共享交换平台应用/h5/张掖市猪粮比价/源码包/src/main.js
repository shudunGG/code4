import { createApp } from 'vue'
// 1. 引入公共样式
import '@/styles/style.css'
import '@/styles/common.css'
// 2. 引入组件样式
import 'vant/lib/index.css';
//routes
import router from "@/router/index";
import Vconsole from "vconsole";
import pinia from '@/store/index'
import App from '@/App.vue'
// amfe-flexible会根据当前页面的尺寸去设置根元素的font-size
import 'amfe-flexible/index'

const app = createApp(App)
//routes
app.use(router)
// pinia
app.use(pinia)


// Vconsole
if (process.env.NODE_ENV !== 'production') {
  const vconsole = new Vconsole()
  window['vconsole'] = vconsole
}
router.beforeEach((to, _from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});

app.mount('#app')
