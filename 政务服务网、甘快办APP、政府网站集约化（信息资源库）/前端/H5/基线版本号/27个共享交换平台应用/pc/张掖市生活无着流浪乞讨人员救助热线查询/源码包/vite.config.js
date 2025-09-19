import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from "path";
import { mkdirSync } from 'fs';
import path from 'path'
import { terser } from 'rollup-plugin-terser'
// 自动导入vue中hook reactive ref等
import AutoImport from "unplugin-auto-import/vite"
//自动导入ui-组件 比如说ant-design-vue  element-plus  vant等
import Components from 'unplugin-vue-components/vite';

// 注册 Vant 组件
import { VantResolver } from '@vant/auto-import-resolver';
// https://vitejs.dev/config/
export default defineConfig({
  base: './',// 指定输出路径 
  publicPath: './',// 指定输出路径
  build: {
    outDir: '../发布包/dist',// 指定输出路径
    cssCodeSplit: true,// 启用 CSS 代码拆分
    extract: true, // 是否使用css分离插件 ExtractTextPlugin
    minify: 'terser', // 启用 terser 压缩 
    sourcemap: false, // 构建后是否生成 source map 文件 
    target: ['edge90', 'chrome90', 'firefox90', 'safari15'], // 适配低版本浏览器
    rollupOptions: {
      output: {
        entryFileNames: 'js/[name]-[hash].js', // 指定 JS 文件的输出路径及命名规则
        chunkFileNames: 'js/[name]-[hash].js', // 指定分片文件的输出路径及命名规则
        assetFileNames: (assetInfo) => {
          // 设置不同类型文件的输出路径及命名规则
          if (
            assetInfo.type === 'asset' &&
            /\.(jpe?g|png|gif|svg)$/i.test(assetInfo.name)
          ) {
            return 'img/[name].[hash].[ext]' // 图像文件输出路径及命名规则
          }
          if (
            assetInfo.type === 'asset' &&
            /\.(ttf|woff|woff2|eot)$/i.test(assetInfo.name)
          ) {
            return 'fonts/[name].[hash].[ext]' // 字体文件输出路径及命名规则
          }
          return '[ext]/name1-[hash].[ext]' // 其他资源文件输出路径及命名规则
        }
      }
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')// @代替src
    }
  },
  plugins: [
    terser({
      format: {
        comments: false,//在压缩过程中去除所有的注释
      },
      compress: {
        drop_console: true,// 删除所有 console.* 函数调用
        drop_debugger: true,// 删除所有 debugger 语句
      },
    }),
    vue(),
    AutoImport({
      //安装两行后你会发现在组件中不用再导入ref，reactive等
      imports: ['vue', 'vue-router'],// 自动导入vue和vue-router相关函数
      //存放的位置
      dts: "src/auto-import.d.ts",// 生成位置
    }),
    Components({
      // 引入组件的,包括自定义组件
      // 存放的位置
      dts: "src/components.d.ts",// 生成位置
      // 全局注册 Vant 组件库
      resolvers: [VantResolver()],// 自动导入组件库
    }),
    {
      name: 'vite-plugin-babel',// 插件名称
      enforce: 'pre',// Vite 核心插件之前调用该插件
    },
  ],
  server: {
    // host: '127.0.0.1', // 指定服务器应该监听哪个 IP 地址
    hot: true,// 保存文件时自动刷新
    strictPort: false,//通过server.strictPort控制端口冲突时是否自动刷新
    // port: 8080, // 指定开发服务器端口
    open: true, // 启动时自动在浏览器中打开应用程序
    proxy: { // 配置自定义代理规则
      '/api': {
        target: 'http://jsonplaceholder.typicode.com',// 代理目标地址
        changeOrigin: true,// 是否需要代理跨域
        // rewrite: (path) => path.replace(/^\/api/, '')// 重写路径
      },
      '/jpaas-jags-server': {
        target: 'https://zwfw.gansu.gov.cn',
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^/api /, '')
      }
    },
  },
})
// 创建文件夹的函数
function createDirs () {
  const dirs = ['update', 'document'];
  dirs.forEach(dir => {
    mkdirSync(resolve('../', dir), { recursive: true });
  });
}
// 在配置生效前执行创建文件夹的函数
createDirs();