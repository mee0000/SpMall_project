
import { createApp } from 'vue'
import pinia from './stores'

// swiper css
import 'swiper/css'
// import element-plus
import ElementPlus from './plugins/elementui.js'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import '@/styles/style.scss'
import './permission'
import '@/mock/mockServer'
import globalComponents from '@/components'

const app = createApp(App)

app.use(router)
app.use(globalComponents)
app.use(pinia)
app.use(ElementPlus)
app.mount('#app')
