import "core-js/stable";
import 'regenerator-runtime/runtime'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store/index";
import "./assets/css/global.css"
import 'element-ui/lib/theme-chalk/index.css';
import Result from '@/views/NoResultsView.vue'
Vue.config.productionTip = false
import { Button, Form, FormItem, Input, Message, Loading, Checkbox, CheckboxGroup, CheckboxButton, Pagination, Select, Option, Breadcrumb, BreadcrumbItem, Steps, Step, Table, TableColumn, Icon, Empty, Dialog, DatePicker, Tooltip } from 'element-ui';
Vue.use(Button).use(Form).use(FormItem).use(Input).use(Checkbox).use(CheckboxGroup).use(CheckboxButton).use(Pagination).use(Select).use(Option).use(Breadcrumb).use(BreadcrumbItem).use(Steps).use(Step).use(Table).use(TableColumn).use(Icon).use(Empty).use(Dialog).use(Loading).use(DatePicker).use(Tooltip)
Vue.prototype.$message = Message
Vue.prototype.$loading = Loading.service;
Vue.component('NoResult', Result)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
