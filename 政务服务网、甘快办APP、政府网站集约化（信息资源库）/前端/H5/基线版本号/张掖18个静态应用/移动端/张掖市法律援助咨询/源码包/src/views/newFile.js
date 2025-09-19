import { mapMutations } from 'vuex';
import IndexApi from '@/api/index';
import { Toast } from 'vant';
import { getArea } from '@/utils/getArea';
import sm2 from '../utils/sm2';

export default (await import('vue')).defineComponent({
data() {
return {
code: '',
id: '620100000000',
cityShow: false,
selctIndex: 0,
cityArr: [{}, { values: [] }],
selectName: '',
selectArr: [],
queryArr: '',
pageNum: '1',
jgmc: '',
num: '',
clientHeight: document.documentElement.clientHeight,
showBtn: true
};
},
mounted() {
window.onresize = () => {
if (this.clientHeight > document.documentElement.clientHeight) {
this.showBtn = false;
} else {
this.showBtn = true;
}
};
getArea().then((res) => {
this.cityArr[0].values = res.data;
});
},
methods: {
focus() {
// document.body.addEventListener('touchmove', function (e) {
//   if (e._isScroller) return
//   // 阻止默认事件
//   e.preventDefault()
// }, {
//   passive: false
// })
document.body.addEventListener('touchmove', this.stop, {
passive: false
});
},
blur() {
document.body.removeEventListener('touchmove', this.stop, {
passive: false
});
},
stop(e) {
e.preventDefault(); // 阻止默认的处理方式(阻止下拉滑动的效果)
},
select() {
getArea(this.id).then((res) => {
this.cityArr[1].values = res.data;
this.cityShow = true;
});
this.cityShow = true;
},
...mapMutations(['setDetail']),
keyUp() {
},
// 查询
async queryList() {
this.jgmc = this.jgmc.replace(/\s+/g, '');
if (/[`~!@#$^&*()=|{}':;',\\\[\]\.<>\/?~！@#￥……&%*（）——|{}【】'；：""'。，、？]/g.test(this.jgmc) || this.jgmc.includes('-')) {
Toast({
message: '不能含有特殊字符',
duration: 1000
});
return;
}
Toast({
message: '查询中',
duration: 0,
forbidClick: true
});
var params = {};
params = {
pageNum: sm2.encrypt(this.pageNum),
pageSize: sm2.encrypt('10'),
map: sm2.encrypt(
JSON.stringify({
jgmc: this.jgmc !== '' ? JSON.stringify({
type: 2,
content: this.jgmc,
left: '',
right: ''
}) : undefined
})
)
};
await IndexApi.queryList(params)
.then((res) => {
Toast.clear();
res = JSON.parse(sm2.decrypt(res.data.dataResult));
if (res.dataList && res.dataList.length > 0) {
this.setDetail(res.dataList);
this.$store.state.total = res.totalCount;
this.$router.push({
path: '/listDetail',
query: {
param: JSON.stringify(params)
}
});
} else {
Toast({
message: '暂无数据'
});
}
})
.catch(() => {
Toast({
message: '网络异常'
});
});
},
// 所在市州确认
onConfirm(text, index) {
if (typeof text === 'string') {
this.selectName = text;
this.selectArr = text;
} else {
this.selectName = text[0].name + '-' + text[1].name;
this.selectArr = text;
}
this.cityShow = false;
},
// 重置信息
resetInfo() {
this.selectArr = [];
this.selectName = '';
this.jgmc = '';
},
onChange(picker, value, index) {
this.id = value[0].code;
getArea(this.id).then((res) => {
this.$set(this.cityArr[1], 'values', res.data);
});
}
}
});
