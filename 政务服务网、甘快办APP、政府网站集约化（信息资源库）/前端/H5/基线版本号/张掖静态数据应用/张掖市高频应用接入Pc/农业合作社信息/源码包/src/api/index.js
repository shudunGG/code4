import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: '200cce4c3b9041bea06df2c1105e8a3f',
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
