/* eslint-disable */
import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/store'
import './registerServiceWorker' //
import 'lib-flexible' // 移动屏幕适配
import './style/common.css' // 全局重置样式
import '../node_modules/hanweb-ui/dist/hg-vcomponents.min.css' //
import 'element-ui/lib/theme-chalk/index.css';
// 引入swiper
import 'swiper/dist/css/swiper.css'


// 引入vant
import {
  Tabbar,
  TabbarItem,
  List,
  Cell,
  PullRefresh,
  Toast,
  Image,
  Lazyload,
  Dialog,
  Notify,
  DatetimePicker,
  Switch,
  Popup,
  Uploader,
  ActionSheet,
  Checkbox,
  CheckboxGroup,
  Field,
  Picker,
  Icon,
  Search,
  Button,
  Col,
  Row,
  Tab,
  Tabs,
  Loading,
  Swipe,
  SwipeItem,
  TreeSelect,
  Overlay
} from 'vant'

import { DatePicker,Form, FormItem,Autocomplete } from 'element-ui';


Vue.use(Tabbar)
  .use(TabbarItem)
  .use(Cell)
  .use(PullRefresh)
  .use(Toast)
  .use(Image)
  .use(Lazyload)
  .use(Dialog)
  .use(Notify)
  .use(Popup)
  .use(Uploader)
  .use(Icon)
  .use(ActionSheet)
  .use(Checkbox)
  .use(CheckboxGroup)
  .use(Field)
  .use(Search)
  .use(List)
  .use(Button)
  .use(Col)
  .use(Row)
  .use(Tab)
  .use(Tabs)
  .use(Loading)
  .use(Picker)
  .use(Swipe)
  .use(SwipeItem)
  .use(TreeSelect)
  .use(Overlay)
  .use(DatePicker)
  .use(FormItem)
  .use(Autocomplete)
  .use(Form)
Vue.use(Switch)
Vue.use(DatetimePicker)

Vue.prototype.$Dialog = Dialog
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  if (from.name == 'confirm') {
    to.meta.isBack = true
  }
  next()
})

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app')
/* eslint-disable */
