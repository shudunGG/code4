import requestJpaas from '../tools/request'
const { getData } = requestJpaas
export default {
  getList (interface_id, app_id, params) {
    return getData({
      interface_id,
      app_id,
      params
    })
  }
}