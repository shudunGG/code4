/* eslint-disable */
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import affirms from './components/affirm/index' // 确认提示
import permission from './components/nopermission/index' // 确认提示
import loads from './components/load/index' // 加载提示
import cautions from './components/caution/index' // 页面强提醒
import tops from './components/top/index' // 顶部提示
import weaks from './components/weak/index' // 弱提示
import "./registerServiceWorker";
import "lib-flexible";
import "./assets/common.css";
//
import "../node_modules/hanweb-ui/dist/hg-vcomponents.min.css"

import { HTTP } from "./tools/request";
// 集成rsa加解密
import JSEncrypt from 'jsencrypt';
// 网络请求注入vue实例
Vue.prototype.$http = HTTP;
//引入swiper
import "swiper/dist/css/swiper.css";
//引入vant
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
  CheckboxGroup
} from "vant";

Vue.use(Tabbar)
  .use(TabbarItem)
  .use(List)
  .use(Cell)
  .use(PullRefresh)
  .use(Toast)
  .use(Image)
  .use(Lazyload)
  .use(Dialog)
  .use(Notify)
  .use(Popup)
  .use(Uploader)
  .use(ActionSheet)
  .use(Checkbox)
  .use(CheckboxGroup);
Vue.use(Switch);
Vue.use(affirms) // 确认提示
Vue.use(permission) // 无权限提示
Vue.use(loads) // 加载提示
Vue.use(cautions) // 页面强提醒
Vue.use(tops) // 顶部提示
Vue.use(weaks) // 弱提示
Vue.use(DatetimePicker) // 时间选择
Vue.prototype.$Dialog = Dialog;
Vue.config.productionTip = false;

/* 路由发生变化修改页面title */
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  if (from.name == 'confirm') {
    to.meta.isBack = true;
  }
  next()
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
/* eslint-disable */