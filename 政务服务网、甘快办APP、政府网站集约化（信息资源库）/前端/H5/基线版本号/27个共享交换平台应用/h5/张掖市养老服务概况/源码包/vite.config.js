import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { terser } from 'rollup-plugin-terser'
import esbuild from 'rollup-plugin-esbuild'

// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  plugins: [
    vue(),
    esbuild({
      target: 'chrome64',
      loaders: {
        '.vue': 'js',
        '.ts': 'js'
      }
    })
  ],
  build: {
    outDir: '../发布包',
    assetsDir: 'assets',
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
      ],
    },
    minify: 'terser',
    target: ['edge90', 'chrome90', 'firefox90', 'safari15'], // 适配低版本浏览器
  },

  server: {
    host: '0.0.0.0',
    proxy: {
      '/jimps': {
        target: 'https://app.gjzwfw.gov.cn', // 代理服务server1
        changeOrigin: true,
        ws: true,
      },
      '/api-gateway': {
        target: 'https://zwfw.gansu.gov.cn',
        changeOrigin: true,
        ws: true,
      }
    }
  },
  define: {
    global: 'window'
  }
})
