import Vue from "vue"
import VueRouter from "vue-router"

const originalPush = VueRouter.prototype.push
// 修改 原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch((err) => err)
}

Vue.use(VueRouter)
const routes = [
	{
		path: "/",
		redirect: { name: "Index" }
	},
	{
		path: "/index",
		name: "Index",
		component: () => import("../views/IndexView"),
		children: [
			{
				path: "/results",
				name: "Results",
				component: () => import("../views/ResultsView"),
				beforeEnter: (to, from, next) => {
					if (
						from.name == "Index" ||
						from.name == "Results" ||
						from.name == "NoResults"
					) {
						next()
					} else {
						next({ name: "Index" })
					}
				}
			},
			{
				path: "/no-results",
				name: "NoResults",
				component: () => import("../views/NoResultsView"),
				beforeEnter: (to, from, next) => {
					if (
						from.name == "Index" ||
						from.name == "Results" ||
						from.name == "NoResults"
					) {
						next()
					} else {
						next({ name: "Index" })
					}
				}
			}
		]
	},
]

const router = new VueRouter({
	routes
})

export default router
