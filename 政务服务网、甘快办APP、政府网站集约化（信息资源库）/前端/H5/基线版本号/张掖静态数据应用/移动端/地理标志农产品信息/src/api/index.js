import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
  queryList (params, columnId) {
    return getData({
      columnId: columnId,
      params
    })
  }
}
