import Vue from "vue";
import Vuex from "vuex";
// import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex);
export default new Vuex.Store({
  // plugins: [
  //   createPersistedState()
  // ],
  state: {
    userInfo: {}, //用户信息
    hospital: {},
    hospitalInformation: [],
    basicInfo: {}
  },
  getters: {},
  mutations: {
    userInfo: (state, detail) => {
      state.userInfo = detail
    },
    hospital: (state, detail) => {
      state.hospital = detail
    },
    hospitalInformation: (state, detail) => {
      state.hospitalInformation = detail
    },
    basicInfo: (state, detail) => {
      state.basicInfo = detail
    },

  },
  actions: {},
  modules: {}
});
