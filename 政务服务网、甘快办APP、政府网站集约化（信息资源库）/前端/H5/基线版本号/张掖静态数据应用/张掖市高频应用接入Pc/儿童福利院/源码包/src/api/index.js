import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'f085123c153a4558b5375746fcd6865c',
            params
        })
    }
}
