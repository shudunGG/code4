import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('5c8c2dd064ce415c9a6c6e2c4b7fdff1'),
    })
  }
}
