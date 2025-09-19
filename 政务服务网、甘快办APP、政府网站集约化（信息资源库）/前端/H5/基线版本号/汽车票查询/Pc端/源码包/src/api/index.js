import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    // 出发城市api
    // eslint-disable-next-line camelcase
    fromCity (interface_id, app_id, params) {
        return getData({
            interface_id,
            app_id,
            params
        })
    }
}
