import "core-js/stable";
import 'regenerator-runtime/runtime'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store/store";
import "./assets/css/global.css"
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
  DatePicker,
  Upload,
  Dialog,
  CheckboxButton,
  Alert,
  MessageBox,
} from 'element-ui';
Vue.use(Button).use(Form).use(FormItem).use(Input).use(Checkbox).use(Upload).use(CheckboxGroup).use(CheckboxButton).use(DatePicker).use(Dialog).use(Alert);
Vue.prototype.$message = Message
Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$alert = MessageBox.alert;
Vue.prototype.$confirm = MessageBox.confirm;
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$loading = Loading.service;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')