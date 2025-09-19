import { axios } from "./request"
import requestJpaas from '../utils/request'
import requestJpaasblob from '../utils/requestblob'
const { getData } = requestJpaas
const { getDataBlob } = requestJpaasblob
const prefix = "/interface/waterRate/getUserInfo"

const api = {
  // 用户信息
  waterFees: prefix
}

export const serveAPI = {
  getuserInfo (data) {
    return axios({
      url: api.waterFees,
      method: "post",
      data
    })
  },
  queryList (appid, interface_id, params, file) {
    return getData({
      appid, interface_id,
      params
    },
      file)
  },
  queryListBlob (appid, interface_id, params) {
    return getDataBlob({
      appid, interface_id,
      params
    })
  }
}