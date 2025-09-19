import requestJpaas from '../utils/request'
const {
    getData
} = requestJpaas
export default {
    queryList(appid, interface_id, params, file) {
        return getData({
                appid,
                interface_id,
                params
            },
            file)
    }
}