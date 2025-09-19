import requestJpaas from '../utils/request'
import sm2 from '../utils/sm2'
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('15d61d7e06d44bc38159e65fdcca4c39')
    })
  }
}
