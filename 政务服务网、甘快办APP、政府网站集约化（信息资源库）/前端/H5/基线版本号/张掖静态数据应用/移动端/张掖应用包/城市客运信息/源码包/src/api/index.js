import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('12ccdf6cc7a34efdadb4aa396078764e'),
    })
  }
}
