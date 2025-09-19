import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'ac1ab4e372ff4e139ed99374718d2717',
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
