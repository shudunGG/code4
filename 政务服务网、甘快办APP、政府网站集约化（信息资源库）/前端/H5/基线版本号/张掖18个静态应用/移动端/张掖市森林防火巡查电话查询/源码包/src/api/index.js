import requestJpaas from '../utils/request'
import sm2 from '../utils/sm2'
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('aea8e7ca794443ccb9b421bf0400646f')
    })
  }
}
