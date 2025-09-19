import { createApp } from 'vue'
// 1. 引入公共样式
import '@/styles/style.css'
// import '@/styles/common.css'
// 2. 引入组件样式
// import 'ant-design-vue/dist/reset.css';
//routes
import router from "@/router/index";
import { createPinia } from 'pinia'
import App from '@/App.vue'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
const app = createApp(App)
//routes
const pinia = createPinia()

router.beforeEach((to, _from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});
app.use(ElementPlus);
app.use(pinia)
app.use(router)
app.mount('#app')
