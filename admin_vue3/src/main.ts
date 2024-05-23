import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
// 다크모드
import 'element-plus/theme-chalk/dark/css-vars.css'
// import element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 전역 router
import './permission'
// set pinia
import pinia from './store'
// 버튼 권한 自定义
import {isHasBotton} from '@/directive/has'

// import 'virtual:svg-icons-register'
import globalComponent from '@/components'
import '@/styles/index.scss'
const app = createApp(App)
isHasBotton(app)
app.use(globalComponent)
app.use(ElementPlus)
app.use(router)
app.use(pinia)
app.mount('#app')