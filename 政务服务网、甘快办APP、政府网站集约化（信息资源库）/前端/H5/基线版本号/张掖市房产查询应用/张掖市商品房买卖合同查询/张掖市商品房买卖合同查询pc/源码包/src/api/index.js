import requestJpaas from '../utils/request'
const { getData, getData1 } = requestJpaas
export default {
    queryList(interface_id, params) {
        return getData({
            interface_id,
            params,
        })
    },
    queryList1(appid, interface_id, params, header) {
        return getData1({
            appid, interface_id,
            params
        }, header)
    },
}
