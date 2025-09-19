import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('5ca1b7b9b1e34b0aae2b0709d7e55498'),
    })
  }
}
