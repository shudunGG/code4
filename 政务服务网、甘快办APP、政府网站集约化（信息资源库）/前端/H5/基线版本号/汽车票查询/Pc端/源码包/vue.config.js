const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ["js", "css"]

module.exports = {
  // 例如 https://www.ruoyi.vip/。如果应用被部署在一个子路径上，你就需要用这个选项指定这个子路径。例如，如果你的应用被部署在 https://www.ruoyi.vip/admin/，则设置 baseUrl 为 /admin/。
  // publicPath: '/dist/',
  publicPath: "./",
  // 放置打包文件的目录（要和baseUrl的生产环境路径一致）（默认dist）
  outputDir: '../发布包',
  // 用于放置生成的静态资源 (js、css、img、fonts) 的；
  assetsDir: "",
  productionSourceMap: false,
  transpileDependencies: true,
  configureWebpack: {
    devtool: "source-map",
    plugins: [
      new CompressionWebpackPlugin({
        filename: "[path][base].gz", // 提示compression-webpack-plugin@2.0.0的话filename改为asset
        algorithm: "gzip",
        test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
        threshold: 10240, //内容超过10KB进行压缩
        minRatio: 0.8,
      }),
    ],
    //关闭 webpack 的性能提示
    performance: {
      hints: false,
    },

  },

  css: {
    // 开启 CSS source maps?
    sourceMap: false,
    // css预设器配置项
    loaderOptions: {
      less: {
        implementation: require("less"), // This line must in sass option
      },
      css: {
        modules: {
          auto: () => false,
        },
      },
    },
  },

  chainWebpack: (config) => {
    config.optimization.minimizer('terser').tap((args) => {
    args[0].terserOptions.output = {
    comments: false
    }
    args[0].extractComments = false
    return args
    })
  },
  // devServer: {
  //   port: 8071,
  //   proxy: {
  //     '/': {
  //       target: 'https://work.gsjtw.cc/jpaas-ucenterextend-server',
  //       changeOrigin: true,
  //       ws: true,
  //       pathRewrite: {
  //         '^/': ''
  //       }
  //     }
  //   }
  // }
};