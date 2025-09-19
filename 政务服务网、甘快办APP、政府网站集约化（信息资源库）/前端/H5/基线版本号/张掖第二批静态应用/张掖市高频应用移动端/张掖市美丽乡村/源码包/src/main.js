/* eslint-disable */
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/store";
import "./registerServiceWorker"; // 
import "lib-flexible"; // 移动屏幕适配
import "./assets/common.css"; // 全局重置样式
import "../node_modules/hanweb-ui/dist/hg-vcomponents.min.css"; //
// 引入swiper
import "swiper/dist/css/swiper.css";
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
  Button
} from "vant";

import Vconsole from "vconsole";
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
  .use(Picker);
Vue.use(Switch);
Vue.use(DatetimePicker) // 时间选择

Vue.prototype.$Dialog = Dialog;
Vue.config.productionTip = false;
// Vue.prototype.$vConsole = new Vconsole();

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