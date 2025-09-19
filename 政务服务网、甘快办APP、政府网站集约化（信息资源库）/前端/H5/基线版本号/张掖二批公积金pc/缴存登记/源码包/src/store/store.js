import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    userInfo: {}, //用户信息
    authCode: "",
    token: "",
    accountInfo: {}, //公积金信息
    basicInfo: {}
  },
  getters: {},
  mutations: {
    userInfo: (state, detail) => {
      state.userInfo = detail
    },
    authCode: (state, detail) => {
      state.authCode = detail
    },
    token: (state, detail) => {
      state.token = detail
    },
    accountInfo: (state, detail) => {
      state.accountInfo = detail
    },
    basicInfo: (state, detail) => {
      state.basicInfo = detail
    },

  },
  actions: {},
  modules: {}
});