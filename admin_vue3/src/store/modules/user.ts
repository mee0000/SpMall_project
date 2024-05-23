import { defineStore } from 'pinia'
import { reqLogin, reqUserInfo} from '@/api/user'
// 로컬스토어
import { GET_TOKEN, SET_TOKEN, REMOVE_TOKEN } from '@/utils/token'
import type { loginFormData, loginResponseData, userInfoResponseData } from '@/api/user/type'
import { UserState } from './types/type'
// 깊은 복사 ---> 형식이 js 이기에 ==> ts 타입을 무효
//@ts-ignore
import cloneDeep from 'lodash/cloneDeep';
//  route 
import { constantRoute, asyncRoute, anyRoute } from '@/router/routes'

import router from '@/router'
// 유저에 의해 메뉴 필터
function filterAsyncRoute(asyncRoute: any, routes: any[]) {
   return asyncRoute.filter((item: any) => {
      if (routes.includes(item.name)) {
         if (item.children && item.children.length >0) {
            item.children = filterAsyncRoute(item.children, routes)
         }
         return true;
       }
    })
}

// 창고 만들기
let useUserStore = defineStore('user', {
   state:(): UserState => {
       return {
          token: GET_TOKEN(), // user판단 
          menuRoutes: constantRoute, //store메뉴 array,
          username: '',
          avatar: '',
          // 현재 유저가 갖고 있는 버튼
          buttons: []
       }
   },
   actions: {
      // 유저로그인
      async userLogin(data: loginFormData) {
        const result: loginResponseData = await reqLogin(data)
        console.log('login', result)
        // 성공시 
        if (result.code === 200) {
            const token = result.data
            this.token = (token as string)
            SET_TOKEN(token as string)
            return 'ok'
        } else {
            return Promise.reject(new Error(result.data))
        }
        // 실패시 
      },
      // 유저정보 가져오기
      async userInfo() {
        console.log('userInfo')
        // 유저 정보중 username
        let result: userInfoResponseData  = await reqUserInfo()
        console.log(result.data)
        if ( result.code == 200) {
           this.username = result.data.name
           this.avatar = result.data.avatar
           this.buttons = result.data.buttons
           // 성공시 유저에 해당한 route를 찾는다 
           let userAsyncRoute = filterAsyncRoute(cloneDeep(asyncRoute), result.data.routes)
           // menuList ==> constantRoute + asyncRoute + anyRoute
           console.log('....route', userAsyncRoute)
           // menu에 필요한 데이터 수집 
           this.menuRoutes = [...constantRoute, ...userAsyncRoute, anyRoute];
           // 현재 route 는 constantRoute 밖에 없음 
           [...userAsyncRoute, anyRoute].forEach((route: any) => {
              router.addRoute(route)
           })
           console.log(router.getRoutes())
           return 'ok'
        } else {
           return Promise.reject('유저정보 획득 실패')
        }
      },
      async userLogout() {
         // data비우기
         this.username = ''
         this.token = ''
         this.avatar = ''
         // localStorage 안에 있는 token 지우기
         REMOVE_TOKEN()
      }
   },
   getters: {

   }
})

export default useUserStore   