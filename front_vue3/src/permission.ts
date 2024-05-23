import pinia from "@/stores";
import useUserStore from "@/stores/modules/user";
import router from '@/router'
import setting from './setting'

let userStore = useUserStore(pinia)
let userInfo = userStore.userInfo
const authUrls = ['/payment', '/myOrder', '/cart', '/user', '/login', '/logout']
router.beforeEach(async (to: any, from: any, next: any) => {
    document.title = `${setting.title}_${to.meta.title}`
    
    let token = userStore.token
    if (!authUrls.includes(to.path)) {
        next()
    } else {
        if (token) {
            if (to.path == '/login') {
                next({ path: '/home' })
            } else {
                if (userInfo) {
                    next()
                } else {
                    try {
                        await userStore.getUserInfo()
                    } catch (error) {
                        userStore.userLogout()
                        next({ path: '/login', query: { redirect: to.fullPath } })
                    }
                }
            }
        } else {
            if (to.path == '/login') {
                next()
            } else {
                console.log(to, 'to........')
                next({ path: '/login', query: { redirect: to.fullPath} })
             }
        }
    }
   
})

