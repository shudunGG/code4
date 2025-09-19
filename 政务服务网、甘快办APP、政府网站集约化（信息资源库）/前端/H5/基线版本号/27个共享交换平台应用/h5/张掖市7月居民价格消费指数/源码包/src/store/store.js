import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    infoList: [],
    total: 0,
    detail: {}
  },
  mutations: {
    setList (state, obj = {}) {
      state.total = obj.total
      state.infoList = obj.rows
    },
    setDetail (state, obj = {}) {
      state.detail = obj
    }
  }
})
export default store
