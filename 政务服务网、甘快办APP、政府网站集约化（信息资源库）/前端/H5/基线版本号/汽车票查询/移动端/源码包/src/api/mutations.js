import { axios } from '../utils/axios'

export function getAction (path, params) {
  return axios({
    url: path,
    method: 'get',
    params: params
  })
}

export function postAction (path, params) {
  return axios({
    url: path,
    method: 'post',
    data: params
  })
}

export function postFormdata (path, params) {
  return axios({
    url: path,
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: params
  })
}
