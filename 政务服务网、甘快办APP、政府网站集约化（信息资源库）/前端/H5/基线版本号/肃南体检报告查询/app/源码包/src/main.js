import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import './assets/css/common.css'
import 'vant/lib/index.css'
// 移动端适配
import 'lib-flexible'
import { Button, Field, Picker, Popup, Icon } from 'vant'

// import Vconsole from 'vconsole';
// const vConsole = new Vconsole()

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
const app = createApp(App)

app.use(Button)
  .use(Field)
  .use(Picker)
  .use(Popup)
  .use(Icon)

// app.use(vConsole)

/* 路由发生变化修改页面title */
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

app.use(pinia).use(router).mount('#app')
