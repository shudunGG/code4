const TerserPlugin = require("terser-webpack-plugin");

module.exports = {
  lintOnSave: false,
  productionSourceMap: false,
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
  publicPath: "./",
  outputDir: "../发布包/dist",
  runtimeCompiler: true,
  configureWebpack: {
    //关闭 webpack 的性能提示
    performance: {
      hints: false
    },
    optimization: {
      minimizer: [
        new TerserPlugin({
          terserOptions: {
            output: {
              comments: false
            }
          },
          extractComments: false
        })
      ]
    }
  },
  devServer: {
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
    }
  }
};