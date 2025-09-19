// 直连接口地址
export const baseUrl = {
  serverUrl: '/proxyServer1/' // 开启跨域
  // serverUrl: "/proxyServer2/", //开启跨域
  // serverUrl: "http://zwfw.sd.gov.cn/"
}
/*
  * Jjpaas网关接口地址
*/
export const jpassConfig = {
  // 唯一标识
  // 'APP_ID': 'japplynzjkiagnj',
  // 'httpRequestJpaas': 'http://vlink.hanweb.com/api-gateway/jpaas-jags-server/interface/',
  // 开发环境
  // 'APP_ID': 'jsearchapp',
  // // // 'httpRequestJpaas': 'http://192.168.83.49:8948/jpaas-jsearch-web-server/interface/',
  // 'httpRequestJpaas': 'http://jpaasdev2.hanweb.com/api-gateway/jpaas-jags-server/interface/',
  // 测试环境
  'APP_ID': 'cmsnzjk',
  // 'httpRequestJpaas': 'http://192.168.83.49:8948/jpaas-jsearch-web-server/interface/',
  'httpRequestJpaas': 'https://zwfw.gansu.gov.cn/jpaas-jags-server/interface/'
}
