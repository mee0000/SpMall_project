import { fileURLToPath, URL } from 'node:url'

import { UserConfigExport, ConfigEnv, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import VueSetupExtend from 'vite-plugin-vue-setup-extend'
// https://vitejs.dev/config/
export default (({ command, mode }: ConfigEnv): UserConfigExport => {
  let env = loadEnv(mode, process.cwd())
  return {
  plugins: [
    vue(),
    VueSetupExtend()
    ],
    build: {
      outDir: 'dist',
      assetsDir: 'assets',
      sourcemap: false,
      terserOptions: {
        compress: {
          drop_console: true,
          drop_debugger: true
        }
      }
    },
    resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
    // scss
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "./src/styles/index.scss";'
        }
      }
  },
  server: {
    proxy: {
        [env.VITE_APP_BASE_API]: {
          target: env.VITE_SERVE,
          // 需要代理跨域
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    }, 
    base: './'
  }
})
