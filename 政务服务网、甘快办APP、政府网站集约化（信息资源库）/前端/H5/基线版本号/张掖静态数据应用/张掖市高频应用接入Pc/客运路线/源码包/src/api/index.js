import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: '07a215f1961f4cc0b9e50e2ed4419d44',
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
