import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    drResultData: {},
    zyyResultData: {},
    token: '',
    authCode: '',

    zyyDetail: {},
    zyResultReportDetail: {},

    fsyyResult: [],

    formateData: {}
  },
  getters: {
  },
  mutations: {
    drResultData: (state, detail) => {
      state.drResultData = detail
    },
    zyyResultData: (state, detail) => {
      state.zyyResultData = detail
    },
    token: (state, detail) => {
      state.token = detail
    },
    authCode: (state, detail) => {
      state.authCode = detail
    },
    zyyDetail: (state, detail) => {
      state.zyyDetail = detail
    },
    zyResultReportDetail: (state, detail) => {
      state.zyResultReportDetail = detail
    },
    fsyyResult: (state, detail) => {
      state.fsyyResult = detail
    },
    formateData: (state, detail) => {
      state.formateData = detail
    }
  },
  actions: {},
  modules: {
  }
})
