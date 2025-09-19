import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('1bed9df1a9eb4f8faf5e1c9e51b3b436'),
    })
  }
}
