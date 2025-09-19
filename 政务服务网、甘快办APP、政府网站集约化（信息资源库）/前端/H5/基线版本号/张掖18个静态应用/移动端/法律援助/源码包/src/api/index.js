import requestJpaas from '../utils/request'
import sm2 from '../utils/sm2'
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('7f920f0d4403471e9f0625a35346b813')
    })
  }
}
