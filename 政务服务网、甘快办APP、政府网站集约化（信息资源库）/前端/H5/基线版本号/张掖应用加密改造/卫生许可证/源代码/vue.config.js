const path = require("path");
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ['js', 'css'];
const UglifyJsPlugin = require('uglifyjs-webpack-plugin') //引入插件
module.exports = {
  // 例如 https://www.ruoyi.vip/。如果应用被部署在一个子路径上，你就需要用这个选项指定这个子路径。例如，如果你的应用被部署在 https://www.ruoyi.vip/admin/，则设置 baseUrl 为 /admin/。
  // publicPath: '/dist/',
  publicPath: './',
  // 在npm run build 或 yarn build 时 ，生成文件的目录名称（要和baseUrl的生产环境路径一致）（默认dist）
  // outputDir: 'dist',
  // 用于放置生成的静态资源 (js、css、img、fonts) 的；（项目打包之后，静态资源会放在这个文件夹下）
  // assetsDir: 'static',
  lintOnSave: false,
  productionSourceMap: false,
  configureWebpack: {
    plugins: [
      new CompressionWebpackPlugin({
        filename: '[path].gzip[query]',   // 提示compression-webpack-plugin@2.0.0的话filename改为asset
        algorithm: 'gzip',
        test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
        threshold: 10240, //内容超过10KB进行压缩
        minRatio: 0.8
      })
    ],
    //关闭 webpack 的性能提示
    performance: {
      hints: false
    },
    optimization: {
      minimizer: [
        new UglifyJsPlugin({
          uglifyOptions: {
            // 删除注释
            output: {
              comments: false
            },
            // 删除console debugger 删除警告
            compress: {
              drop_console: true, //console
              drop_debugger: false,
              pure_funcs: ['console.log'] //移除console
            }
          }
        })
      ]
    }
  },
  chainWebpack: config => {
    // 移除 prefetch 插件
    config.plugins.delete('prefetch')
  },
  css: {
    loaderOptions: {
      postcss: {
        plugins: [
          require("postcss-pxtorem")({
            rootValue: 37.5, // 换算的基数
            propList: ["*"]
          })
        ]
      }
    },
    // 是否使用css分离插件 ExtractTextPlugin
    extract: true,
    // 开启 CSS source maps?
    sourceMap: false,
    // 启用 CSS modules for all css / pre-processor files.
    modules: false
  },
  runtimeCompiler: true,
  devServer: {
    port: 8071,
    open: true, //配置自动启动浏览器
    proxy: {
      "/proxyServer1": {
        target: "http://zwfw.sd.gov.cn/", //代理服务server1
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/proxyServer1": ""
        }
      },
      "/proxyServer2": {
        target: "http://192.168.85.14:9001/jmas-api-gateway-server/", //代理服务server2
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/proxyServer2": ""
        }
      }
    }
  }
};
