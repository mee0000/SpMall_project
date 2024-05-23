import { defineStore } from 'pinia'
import { SET_TOKEN, GET_TOKEN, REMOVE_TOKEN } from '@/utils/token'
import { req_login, req_userInfo } from '@/api/user/index'
import { constantRoute } from '@/router/routes'
import { UserState } from './types/type'
import type{ UserResponseData} from '@/api/user/type'
// 창고 만들기 

let useUserStore = defineStore('user', {
    state:():UserState => {
        return {
            menuRoutes: constantRoute,
            userInfo: {},
            token: GET_TOKEN()
        }
    },
    actions: {
        async userLogin(data: object) {
            const result = await req_login(data)
            if(result.code == 200){
                console.log('로그인성공')
                this.token = result.data
                SET_TOKEN(result.data)
                return 'ok'
            }else {
                return Promise.reject(new Error(result.data))
            }
        },
        async getUserInfo() {
            const result:UserResponseData = await req_userInfo()
            if (result.code == 200) {
                this.userInfo = result.data
                return 'ok'
            } else {
                return Promise.reject('유저정보 획득 실패')
            }
        },
        userLogout() {
            this.userInfo = {}
            this.token = ''
            //localStorage 안에 있는 token 지우기
            REMOVE_TOKEN()
        }
    }
})

export default useUserStore;
