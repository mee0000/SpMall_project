import { ConfigEnv, UserConfigExport, loadEnv } from 'vite'
// svg icon
// import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import { viteMockServe } from 'vite-plugin-mock'
// https://vitejs.dev/config/
export default (({ command, mode }: ConfigEnv): UserConfigExport => {
  let env = loadEnv(mode, process.cwd())
  return {
    plugins: [vue(),
      // createSvgIconsPlugin({
      //   // specify the icon folder to be cached
      //   iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
      //   // Specify symbolId format
      //   symbolId: 'icon-[dir]-[name]'
      // })
        viteMockServe as any,({
          enabled: command === 'serve'
        })
    ],
    resolve: {
      alias: {
        "@": path.resolve("./src")
      }
    },
    // scss
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "./src/styles/variable.scss";'
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
