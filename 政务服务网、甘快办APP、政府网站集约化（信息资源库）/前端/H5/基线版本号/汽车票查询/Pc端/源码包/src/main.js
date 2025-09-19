import "core-js/stable"

import Vue from "vue"
import App from "./App.vue"
import router from "./router"
// 导入字体图标
import "./assets/iconfont/iconfont.css"
import "./assets/css/global.css"
// import ElementUI from 'element-ui';
import "element-ui/lib/theme-chalk/index.css"
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
		Cascader,
		Pagination,
		Select,
		Option,
		Radio,
		DatePicker,
		Autocomplete
	} from "element-ui"
import MyMessage from "./components/Message/main"
Vue.use(Loading.directive)
Vue.use(Button)
	.use(Form)
	.use(FormItem)
	.use(Input)
	.use(Checkbox)
	.use(CheckboxGroup)
	.use(CheckboxButton)
	.use(Cascader)
	.use(Pagination)
	.use(Select)
	.use(Option)
	.use(Radio)
	.use(DatePicker)
	.use(Autocomplete)
Vue.prototype.$message = Message
Vue.prototype.$loading = Loading.service
Vue.prototype.$eleMessage = MyMessage
new Vue({
	router,
	render: (h) => h(App)
}).$mount("#app")
