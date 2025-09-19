import requestJpaas from '../utils/request'
import sm2 from '../utils/sm2'
const { getData } = requestJpaas
export default {
  queryList (params) {
    return getData({
      params,
      columnId: sm2.encrypt('78a4373852f54eac92061ae4e0d99929')
    })
  }
}
