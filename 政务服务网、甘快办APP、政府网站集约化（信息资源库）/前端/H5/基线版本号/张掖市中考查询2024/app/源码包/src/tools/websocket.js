// 创建构造函数
const QueryWebSocket = function (obj1) {
  // eslint-disable-next-line no-redeclare
  const obj = obj1 || {}
  const defaultObj = {
    url: '',
    lockReconnect: false,
    ws: '',
    tt: '',
    timeout: 30000,
    timeoutObj: null
  }
  this.config = Object.assign(defaultObj, obj)
  if ('WebSocket' in window) {
    console.log('支持WebSocket')
    const that = this
    setTimeout(function () {
      that.createWebSocket()
    }, 1000)
  } else {
    alert('该浏览器不支持WebSocket')
  }
}
QueryWebSocket.prototype = {
  QueryWebSocket,
  reset: function () {
    clearTimeout(this.config.timeoutObj)
    this.config.ws.onclose()
  },
  start: function () {
    const that = this
    this.config.timeoutObj = setInterval(function () {
      const message = 'I'
      that.config.ws.send(message) // 启动心跳
    }, 15000)
  },
  // 创建webSocket
  createWebSocket: function () {
    try {
      // 成功
      this.config.ws = new WebSocket(this.config.url)
      this.webSocketInit() // 初始化webSocket连接函数
      this.config.createSuccess()
    } catch (e) {
      // 失败
      console.log('catch')
      // 重连函数
      this.webSocketReconnect(this.config.url)
    }
  },
  // 像websocket发送信息
  sendContent: function (data) {
    this.config.ws.send(JSON.stringify(data))
  },
  // 初始化方法，成功后执行
  webSocketInit: function () {
    const that = this
    // 连接关闭函数
    that.config.ws.onclose = function () {
      console.log('连接已关闭...', new Date())
      clearTimeout(that.config.timeoutObj)
      that.webSocketReconnect(that.config.url) // 如果连接关闭则重连
    }
    // 连接错误函数
    that.config.ws.onerror = function () {
      console.log('连接错误...', new Date())
      that.webSocketReconnect(that.config.url) // 如果连接错误则重连
    }
    // 连接建立,发送信息
    that.config.ws.onopen = function () {
      console.log('链接成功', new Date())
      that.config.ws.send('I')
      that.start() // 订阅业务发送之后启动心跳检测机制
    }
    // 业务订阅成功后接受服务端推送消息  ，其实是个字符串
    that.config.ws.onmessage = function (evt) {
      if (evt.data !== 'O') {
        if (JSON.parse(evt.data).content !== 'PONG') {
          that.config.queryData(JSON.parse(evt.data))
        }
      }
    }
  },
  // 重连
  webSocketReconnect: function (url) {
    const that = this
    console.log('socket 连接断开，正在尝试重新建立连接')
    if (this.lockReconnect) {
      return
    }
    this.lockReconnect = true
    this.tt && clearTimeout(this.tt)
    this.tt = setTimeout(function () {
      that.createWebSocket(url)
    }, 4000)
  }
}
export default QueryWebSocket
