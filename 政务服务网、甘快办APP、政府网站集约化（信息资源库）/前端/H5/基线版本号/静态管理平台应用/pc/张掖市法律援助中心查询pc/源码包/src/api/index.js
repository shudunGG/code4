import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: sm2.encrypt('e0b44d05cff2405bb8db2d5c086e88bf'),
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
