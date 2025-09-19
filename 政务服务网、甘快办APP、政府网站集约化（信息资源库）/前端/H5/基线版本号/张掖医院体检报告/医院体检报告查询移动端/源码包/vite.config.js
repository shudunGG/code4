import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import postCssPxToRem from 'postcss-pxtorem'
import path from 'path'

export default defineConfig({
  base: './',
  plugins: [
    vue()
  ],
  build: {
    outDir: '../发布包',
    assetsDir: 'static',
    rollupOptions: {
      output: {
        chunkFileNames: 'static/js/[name]-[hash].js',
        entryFileNames: 'static/js/[name]-[hash].js',
        assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
      }
    },
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true
      }
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  css: {
    postcss: {
      plugins: [
        postCssPxToRem({
          rootValue: 37.5, // 换算的基数
          propList: ['*']
        })
      ]
    }
  },
  server: {
    host: '0.0.0.0',
    open: true,
    proxy: {
      '/api-gateway': {
        target: 'https://zwfw.gansu.gov.cn/',
        changeOrigin: true,
        rewrite: {
          '^/api-gateway': ''
        }
      }
    }
  },
  define: {
    global: 'window'
  }
})
