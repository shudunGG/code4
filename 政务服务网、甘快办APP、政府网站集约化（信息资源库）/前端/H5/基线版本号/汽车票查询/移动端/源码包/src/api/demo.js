import { postAction } from '@/api/mutations'

export const demoAPI = {
  getYanqian (param) {
    return postAction('/jpaas-jags-server/interface/createsign.do', param)
  },
  getAfterYanqian (param) {
    return postAction('/jpaas-jags-server/interface/gateway.do', param)
  }
}
