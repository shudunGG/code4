import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'c92e9a2522f1453ea00d209e202d4097',
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
