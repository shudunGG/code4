import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: '032e975e8d6a4eab92a16f44b6f9f5ad',
            params
        })
    }
}
