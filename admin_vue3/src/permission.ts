// route 경료 권한제한(경우에 따라 판단)
import router from "@/router"
// lodding program
import nprogress from 'nprogress'
// lodding css
import 'nprogress/nprogress.css'
import pinia from "./store"
// 사용자 title 정의 setting.ts 에서 가져오기
import setting from './setting'
// userStore
import useUserStore from '@/store/modules/user'

let userStore = useUserStore(pinia)
let username = userStore.username
//전역 스크립트 - 전
router.beforeEach(async (to:any, _:any, next:any) => {
  document.title = `${setting.title}_${to.meta.title}`
  // 경로 이동시 발생
  nprogress.start()
  // token으로 로그인했는지 확인 
  let token = userStore.token
  if (token) {
     if(to.path == '/login') {
        //로그인 성공시
        next({path:'/'})
     } else {
        if(username) {
          next()
        } else {
          // 없을시 정보 요청 
          try {
            await userStore.userInfo();
            //새로고침후 userAsyncRoute ,route 异步路由没有加载完毕， 로딩중이면은 화면이 blank 
            next()
          } catch (error) {
            // token 유효기간 지나거나 유저가 token을 건드렸거나
            // logout 진행 
            userStore.userLogout()
            next({path: '/login', query:{redirect:to.path}})
          }
        }     
     }
  } else {
    // 로그인안했을경우
    if (to.path == '/login') {
      next()
    } else {
      // 로그인 전에 갈려고 하던 path를 쿼리로 전환 로그인후 갈려는 경로로 이동 
      next({path: '/login', query:{redirect:to.path}})
    }
  }

})

//전역 스크립트 - 전
router.afterEach(() => {
  nprogress.done()
})