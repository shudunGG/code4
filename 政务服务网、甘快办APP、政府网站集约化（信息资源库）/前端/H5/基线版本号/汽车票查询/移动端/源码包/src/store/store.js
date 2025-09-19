import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    ticketsList: [],
    fromAction: '',
    toAction: '',
    data1: '',
    fromCityId: '',
    totalrows: 0
  },
  mutations: {
    totalrows(state, totalrows) {
      state.totalrows = totalrows
    },
    ticketsList(state, tickets) {
      state.ticketsList = tickets
    },
    fromAction(state, fromAction) {
      state.fromAction = fromAction
    },
    toAction(state, toAction) {
      state.toAction = toAction
    },
    fromData(state, data1) {
      state.data1 = data1
    },
    fromCityId(state, fromCityId) {
      state.fromCityId = fromCityId
    }
  }
})
export default store
