import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { terser } from 'rollup-plugin-terser'
// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  plugins: [vue()],
  build: {
    outDir: './发布包',
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
      },
      plugins: [
        terser({
          format: {
            comments: false // 去除注释
          }
        })
      ]
    },
    minify: 'terser',
    target: ['edge90', 'chrome90', 'firefox90', 'safari15'] // 适配低版本浏览器
  },
  server: {
    host: '0.0.0.0',
    proxy:{
      '/api':{
      target: 'https://zwfw.gansu.gov.cn',  // 后端服务器地址
      changeOrigin: true,               // 修改 Origin 头
      secure: false,                    // 如果是 https 的接口，设置为 true
      rewrite: (path) => path.replace(/^\/api/, ''), // 去除 /api 前缀
    }
    }
  },
  define: {
    global: 'window'
  }
})
