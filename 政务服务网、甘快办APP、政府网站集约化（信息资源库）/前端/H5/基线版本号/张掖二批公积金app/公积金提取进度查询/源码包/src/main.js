/* eslint-disable */
import Vue from "vue";
import App from "./App.vue";
import router from "./router/router";
import store from "./store/store";
import VuePicturePreview from 'vue-picture-preview'


import axios from "axios";
import "lib-flexible";
import "./assets/common.css";
import Vconsole from "vconsole";

//引入swiper
import "swiper/dist/css/swiper.css";
//引入vant
import {
  Search,
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
  Form,
  Field,
  Button,
  Picker,
  Popup,
  RadioGroup,
  Radio,
  Checkbox,
  CheckboxGroup,
  Area,
  Calendar,
  Loading,
  Overlay,
  Uploader,
  DropdownMenu,
  DropdownItem,
  Icon,
  Pagination,
  Cascader,
  NoticeBar
} from "vant";
import { Tab, Tabs } from "vant";
Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Tabbar)
  .use(DropdownMenu)
  .use(DropdownItem)
  .use(Uploader)
  .use(Search)
  .use(TabbarItem)
  .use(List)
  .use(Cell)
  .use(PullRefresh)
  .use(Toast)
  .use(Image)
  .use(Lazyload)
  .use(Dialog)
  .use(Notify)
  .use(Form)
  .use(Field)
  .use(Button)
  .use(Picker)
  .use(Popup)
  .use(RadioGroup)
  .use(Radio)
  .use(Checkbox)
  .use(CheckboxGroup)
  .use(Area)
  .use(Calendar)
  .use(Loading)
  .use(Overlay)
  .use(Icon)
  .use(Pagination)
  .use(NoticeBar)
  .use(Cascader);


Vue.use(DatetimePicker); // 时间选择

Vue.prototype.$http = axios;
Vue.config.productionTip = false;
// Vue.prototype.$vConsole = new Vconsole();
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
/* eslint-disable */
