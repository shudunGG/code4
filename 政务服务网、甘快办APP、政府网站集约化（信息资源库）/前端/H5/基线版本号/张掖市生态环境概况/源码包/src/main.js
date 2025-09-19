import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import './assets/css/common.css'
import 'vant/lib/index.css'
// 移动端适配
// import 'lib-flexible'
import '/src/utils/rem.js'
import { axios } from '/src/tools/axios'

import {
  Button,
  Search,
  Cell,
  CellGroup,
  Image as VanImage,
  Field,
  PullRefresh,
  Toast,
  Collapse,
  Tab,
  Tabs,
  CollapseItem,
  Popup,
  Picker,
  List,
  Uploader,
  ActionSheet,
  Dialog,
  Loading
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
  .use(Tab)
  .use(Tabs)
  .use(Loading)
  .use(PullRefresh)
  .use(CollapseItem)
  .use(Popup)
  .use(Picker)
  .use(List)
  .use(Uploader)
  .use(Search)
  .use(ActionSheet)
  .use(Dialog)

/* 路由发生变化修改页面title */
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
app.use(pinia).use(router).mount('#app')
