import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: "",
    islogin: true,
  },
  getters: {

  },
  mutations: {
    loginSuccess(state, payload) {
      state.islogin = payload;
    }
  },
  actions: {},
  modules:{}
});
