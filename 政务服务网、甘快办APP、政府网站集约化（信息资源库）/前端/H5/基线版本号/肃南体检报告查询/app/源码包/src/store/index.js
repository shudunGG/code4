import { defineStore } from 'pinia'
export const useStore = defineStore('useStore', {
  state: () => {
    return {
      listDetail: {},
      cartNo: '',
      phone: '',
      itemDetail: {},
      resultReportDetail: {},
      resultInfo: {},
      fsyyResult: []
    }
  },
  getters: {},
  actions: {
    changeListDetail (list) {
      this.listDetail = list
    },
    changeToken (token) {
      this.cartNo = token
    },
    changeAuthCode (authCode) {
      this.phone = authCode
    },
    changeItemDetail (itemDetail) {
      this.itemDetail = itemDetail
    },
    changeReportDetail (resultReportDetail) {
      this.resultReportDetail = resultReportDetail
    },
    changeFsyyResult (fsyyResult) {
      this.fsyyResult = fsyyResult
    }
  },
  persist: {
    storage: sessionStorage
  }
})
