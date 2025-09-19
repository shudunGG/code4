import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
  queryList (appId, interfaceId, params,header) {
    return getData({
      appId,
      interfaceId,
      params,
      header
    })
  }
}
