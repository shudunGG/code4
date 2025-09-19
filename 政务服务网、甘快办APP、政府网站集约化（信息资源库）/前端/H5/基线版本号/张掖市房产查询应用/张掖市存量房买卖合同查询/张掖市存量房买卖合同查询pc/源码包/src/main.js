// import "core-js/stable";
// import 'regenerator-runtime/runtime'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import "./assets/css/global.css"
// import ElementUI from 'element-ui';
import store from "./store/index";
import 'element-ui/lib/theme-chalk/index.css';
// import Result from '@/views/NoResultsView.vue'

Vue.config.productionTip = false
// Vue.use(ElementUI);
import { Button, Form, FormItem, Input, Message ,Loading,Checkbox,CheckboxGroup,CheckboxButton,Pagination, Select, Option, Dialog,Cascader} from 'element-ui';
Vue.use(Loading.directive)
Vue.use(Button).use(Form).use(FormItem).use(Input).use(Checkbox).use(CheckboxGroup).use(CheckboxButton).use(Pagination).use(Select).use(Option).use(Dialog).use(Cascader);
Vue.prototype.$message = Message
Vue.prototype.$loading = Loading.service;
// Vue.component('NoResult', Result)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
