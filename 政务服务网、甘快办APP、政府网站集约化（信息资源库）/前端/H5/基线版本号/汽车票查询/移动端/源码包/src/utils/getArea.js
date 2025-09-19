import axios from 'axios'
const getArea = (areaCode) => {
  const baseParams = new URLSearchParams()
  baseParams.append('app_id', 'sx')
  baseParams.append('interface_id', 'qhwjld')
  baseParams.append('version', '1.0')
  // baseParams.append('header', {})
  baseParams.append('biz_content', JSON.stringify({
    areaCode: areaCode || ''
  }))
  baseParams.append('charset', 'UTF-8')
  baseParams.append('origin', '0')
  baseParams.append('timestamp', new Date().valueOf())
  return new Promise((resolve, reject) => {
    axios({
      method: 'post',
      url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/createsign.do',
      data: baseParams
    }).then(res => {
      baseParams.append('sign', res.data.data.sign)
      axios({
        method: 'post',
        url: 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/gateway.do',
        data: baseParams
      }).then(data => {
        resolve(JSON.parse(data.data.data))
      }).catch(error => {
        reject(error)
      })
    }).catch(error => {
      reject(error)
    })
  })
}
export {
  getArea
}
