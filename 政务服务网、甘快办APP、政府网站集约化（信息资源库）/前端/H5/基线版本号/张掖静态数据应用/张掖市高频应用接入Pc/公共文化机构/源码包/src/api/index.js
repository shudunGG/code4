import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'adb31c15237f4057b2b1126cf8ff2d7d',
            params
        })
    }
}
