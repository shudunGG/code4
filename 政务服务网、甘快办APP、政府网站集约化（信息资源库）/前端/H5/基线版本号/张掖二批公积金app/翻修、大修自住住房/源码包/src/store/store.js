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
    AccessToken: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDAxMjg4MDQsInVzZXJuYW1lIjoiZ2tiVXNlcjYyMjQyMTE5OTkwODI2NjExOSJ9.3vL2pgNjsIvDly2zQkzfBzxlyEapx3tqxjkSe593hQ0',//AccessToken
    accountInfo: {},//公积金信息
    basicInfo: {}
  },
  getters: {},
  mutations: {
    userInfo: (state, detail) => {
      state.userInfo = detail
    },
    AccessToken: (state, detail) => {
      state.AccessToken = detail
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
