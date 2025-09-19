// import "core-js/stable";
// import 'regenerator-runtime/runtime'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import "./assets/css/global.css"
import store from "@/store";
// import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.config.productionTip = false
// Vue.use(ElementUI);
import {
  Button,
  Form,
  FormItem,
  Input,
  Message,
  Loading,
  Checkbox,
  CheckboxGroup,
  CheckboxButton,
  Pagination,
  Select,
  Option,
  MessageBox,
  TableColumn,
  Table
} from 'element-ui';
Vue.use(Button).use(Form).use(FormItem).use(Input).use(Checkbox).use(CheckboxGroup).use(CheckboxButton).use(Pagination).use(Select).use(Option).use(TableColumn).use(Table);
Vue.prototype.$message = Message
Vue.prototype.$prompt = MessageBox.prompt
Vue.prototype.$loading = Loading.service;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
