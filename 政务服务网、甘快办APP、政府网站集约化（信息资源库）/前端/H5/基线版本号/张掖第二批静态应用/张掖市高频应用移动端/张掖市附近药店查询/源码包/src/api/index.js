import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('ee03a73aef6f4ed98bb8401918dfa588'),
    })
  }
}
