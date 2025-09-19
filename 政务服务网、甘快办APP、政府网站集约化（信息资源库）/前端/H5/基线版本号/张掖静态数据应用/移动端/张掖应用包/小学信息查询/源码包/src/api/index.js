import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('5cc9aa58a70e489ea6360a3e926a7657'),
    })
  }
}
