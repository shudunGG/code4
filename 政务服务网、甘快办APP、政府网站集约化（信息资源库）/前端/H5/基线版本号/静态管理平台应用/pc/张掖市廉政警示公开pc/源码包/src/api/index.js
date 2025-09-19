import requestJpaas from '../utils/request'
import sm2 from "../utils/sm2"
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: sm2.encrypt('0d2e51ef739f4d2a8e6755f9043e98d1'),
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
