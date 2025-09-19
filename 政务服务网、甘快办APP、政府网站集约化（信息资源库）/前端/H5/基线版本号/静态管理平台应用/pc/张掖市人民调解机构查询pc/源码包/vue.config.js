const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: './',
  productionSourceMap: false,
  outputDir: '../发布包',
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
})