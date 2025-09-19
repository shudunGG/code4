import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: '5cc9aa58a70e489ea6360a3e926a7657',
            params
        })
    }
}
