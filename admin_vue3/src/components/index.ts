// 对外暴露插件对象
// 引入项目中全部的全局组件
import Category from './Category/index.vue'
import SkuForm from './SkuForm/index.vue'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 全局对象
const allGlobalComponent = { Category, SkuForm }
// Object.keys(allGlobalComponent).forEach(item => {
//     console.log(123)
// })
export default {
    // 네임을 install 로 하는게 추천
    install(app) {
        Object.keys(allGlobalComponent).forEach(key => {
            // 注册为全局组件
            app.component(key, allGlobalComponent[key])
        })
        console.log(Object.entries(ElementPlusIconsVue))
        for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
            app.component(key, component)
        }
    }
}