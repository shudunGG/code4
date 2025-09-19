import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('b7c1b60b848f40bbaa20d1b4a0e5f6f9'),
    })
  }
}
