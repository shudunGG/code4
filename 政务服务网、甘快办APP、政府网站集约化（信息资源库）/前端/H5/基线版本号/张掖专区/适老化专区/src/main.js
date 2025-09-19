/* eslint-disable */
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import axios from "axios";
import Vconsole from "vconsole";
import affirms from "./components/affirm/index"; // 确认提示
import loads from "./components/load/index"; // 加载提示
import cautions from "./components/caution/index"; // 页面强提醒
import tops from "./components/top/index"; // 顶部提示
import weaks from "./components/weak/index"; // 弱提示
import "./registerServiceWorker";
import "lib-flexible";
import "./assets/common.css";
import { HTTP } from "./tools/request";
// 集成rsa加解密
import JSEncrypt from "jsencrypt";
// 网络请求注入vue实例
Vue.prototype.$http = HTTP;
// rsa加密
Vue.prototype.$RSAencryption = function(str) {
  // 注册方法
  let pubKey = `-----BEGIN PUBLIC KEY-----
  MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJHpnOVHGNdvPGAsNZgHAtPI7h
  fvDmd4o08HqzdVrL28q9zDuSPAC1OL6zR2qV0wN6+gHrB/H7w1WtfZEMZAWr95X2
  qASXka20mrf7sDolbmuB203UW6XgFg/bdZc/GGH11GddCxZKHgeSr/BivQ9MY+iB
  JkcttRAVtWZltwgPnQIDAQAB
  -----END PUBLIC KEY-----`; // ES6 模板字符串 引用 rsa 公钥
  let encryptStr = new JSEncrypt();
  encryptStr.setPublicKey(pubKey); // 设置 加密公钥
  let data = encryptStr.encrypt(str.toString()); // 进行加密
  return data;
};
// rsa解密
Vue.prototype.$RSAdecryption = function(str) {
  // 注册方法
  let pubKey = `-----BEGIN PRIVATE KEY-----
  MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMkemc5UcY1288YC
  w1mAcC08juF+8OZ3ijTwerN1Wsvbyr3MO5I8ALU4vrNHapXTA3r6AesH8fvDVa19
  kQxkBav3lfaoBJeRrbSat/uwOiVua4HbTdRbpeAWD9t1lz8YYfXUZ10LFkoeB5Kv
  8GK9D0xj6IEmRy21EBW1ZmW3CA+dAgMBAAECgYAPWA5T941SRakDimJtCaeFTAF6
  oryuWKBquY4i8ukidHpbUKGa3okPSm0CMIipNZgmYbyTud90XcrDmbjfQSJNgcEt
  uc3n9nwZKAzF3Z5NOkRSGoyBeHK6FUPLvE/DyCg8hAvioopO9L98FJWKn/BAlLeG
  sNMzclT55HptrzfDAQJBAO+/vtXmjGQDS07gvr6vUktSrApiNtfnolL7xmuTSAwI
  O6ShuQCq12aucqT534g6WO8zYgZssao3MhzobG7SUFkCQQDWwIktroq/r1hKTvKu
  +UVi6lOfBTbcaVMPyEFFOm/iXZWDT1V6ZaMY6avq7lxsY9/zRLd6Rzgs0odceCDt
  0bDlAkEAuG/JLqKy5AnIuICbHPShGYMbCM3vpP70WGSKi519Moxu3cLwILxNAxg2
  6AW2WgWQ/0uHIKwjrW/S5HxFVqgC2QJAMpB8/EOXqdCYgk6ad7Z7rvGrpMqugzFN
  fG7TZ4B97ZfJZ3SblMkvoOMMNrHPEzpk9budU+5G/M7JTm1+m2B/PQJAKPWyrnrA
  UzJe7DJVUJd385MGUrI05bLQfj3LtjrZBQ/z+ACaBnXmFBsg9HIoNn1qgn2jVVVh
  MeFRpxPx6rEMGw==
  -----END PRIVATE KEY-----
  `; // ES6 模板字符串 引用 rsa 私钥
  let encryptStr = new JSEncrypt();
  encryptStr.setPrivateKey(pubKey); // 设置 私钥
  let data = encryptStr.decrypt(str.toString()); // 进行解密
  return data;
};
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
  Area,
  Calendar,
  Loading,
  Overlay,
  Uploader,
  DropdownMenu,
  DropdownItem,
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
  .use(Area)
  .use(Calendar)
  .use(Loading)
  .use(Overlay);

Vue.use(affirms); // 确认提示
Vue.use(loads); // 加载提示
Vue.use(cautions); // 页面强提醒
Vue.use(tops); // 顶部提示
Vue.use(weaks); // 弱提示
Vue.use(DatetimePicker); // 时间选择

Vue.prototype.$http = axios;
Vue.config.productionTip = false;
// Vue.prototype.$vConsole = new Vconsole()
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title && to.meta.title != '') {
    let titleName = to.meta.title
    document.title = titleName
    dd.ready(function () {
      dd.setTitle({
        title: titleName,
      }).then(res => {}).catch(err => {})
    })

  }
  if (from.name == 'confirm') {
    to.meta.isBack = true;
  }
  next()
})

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
/* eslint-disable */
