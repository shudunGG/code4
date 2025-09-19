import requestJpaas from '../utils/request'
import sm2 from '../utils/sm2'
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('c42b1bee24cd4599838e2c664b17acf1')
    })
  }
}
