// import { axios } from './request'
import requestJpaas from '../tools/request'
import requestJpaasblob from '../tools/requestblob'
import requestcms from '../tools/requestcms'

const { getData } = requestJpaas
const { getDataBlob } = requestJpaasblob
const { getDataCms } = requestcms

export const serverApi = {
  queryList (appid, interface_id, params, file) {
    return getData(
      {
        appid,
        interface_id,
        params
      },
      file
    )
  },
  queryCmsList (params, columnId) {
    return getDataCms({
      params,
      columnId: tools.encryptSM2(columnId)
    })
  },
  queryListBlob (appid, interface_id, params) {
    return getDataBlob({
      appid,
      interface_id,
      params
    })
  }
}
