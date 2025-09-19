import requestJpaas from '../utils/request'
const {
    getData
} = requestJpaas
export default {
    queryList(appid, interface_id, params) {
        return getData({
                appid,
                interface_id,
                params
            })
    }
}