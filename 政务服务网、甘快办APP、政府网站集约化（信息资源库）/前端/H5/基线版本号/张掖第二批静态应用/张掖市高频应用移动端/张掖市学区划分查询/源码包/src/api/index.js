import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('60e0e8b6c1ff474e87ed19f88e26df6e'),
    })
  }
}
