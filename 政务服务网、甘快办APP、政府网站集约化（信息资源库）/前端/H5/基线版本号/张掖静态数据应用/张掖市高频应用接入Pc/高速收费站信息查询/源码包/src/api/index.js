import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'e96bd00fad924fa3997c64799c06a031',
            params
        })
    }
}
