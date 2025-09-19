const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: './',
  productionSourceMap: false,
  outputDir: '../发布包/dist',
  chainWebpack: (config) => {
    config.optimization.minimizer('terser').tap((args) => {
      args[0].terserOptions.output = {
        comments: false,
      }
      args[0].extractComments = false
      args[0].terserOptions.compress.pure_funcs = ['console.log']
      return args
    })
  },
  devServer: {
    //  //historyApiFallback: true,
    port: 8081,
    open: false, //配置自动启动浏览器
    proxy: {
      "/api-gateway": {
        target: "https://zwfw.gansu.gov.cn",
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/api-gateway": ""
        }
      },
      '/hanweb-payment-server': {
        target: 'https://zwfw.gansu.gov.cn',
        changeOrigin: true,
        ws: true,
      },
      '/szgs/member/wwLogin': {
        target: 'https://zwfw.gansu.gov.cn',
        changeOrigin: true,
        ws: true,
      },
    }
  }
})