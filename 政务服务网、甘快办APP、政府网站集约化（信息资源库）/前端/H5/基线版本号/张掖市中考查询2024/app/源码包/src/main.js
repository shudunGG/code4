import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router/index'
import './assets/css/common.css'
import 'vant/lib/index.css'
// 移动端适配
import 'lib-flexible'
import { axios } from '../src/tools/axios'
import {
  Button,
  Cell,
  CellGroup,
  Image as VanImage,
  Field,
  Toast,
  Collapse,
  CollapseItem,
  Popup,
  Picker,
  List,
  Uploader
} from 'vant'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
const app = createApp(App)
app.config.globalProperties.axios = axios
app
  .use(Button)
  .use(Cell)
  .use(CellGroup)
  .use(VanImage)
  .use(Field)
  .use(Toast)
  .use(Collapse)
  .use(CollapseItem)
  .use(Popup)
  .use(Picker)
  .use(List)
  .use(Uploader)

/* 路由发生变化修改页面title */
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
app.use(pinia).use(router).mount('#app')
