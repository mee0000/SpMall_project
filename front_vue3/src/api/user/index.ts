import request from "@/utils/request";
import type { User, ResponseData } from "./type"
enum API {
    CHECKUSERID_URL = "/product/checkUserName",
    REGISTER_URL = "/product/register",
    LOGIN_URL = "/product/login",
    USERINFO_URL = "/product/userInfo"
}

// 회원가입-> 유저 ID 확인
export const req_checkUserName = (userName: string) => {
    console.log(userName, '-userId')
    return request({url:API.CHECKUSERID_URL,params:{userName} ,method:'post'})
}

//회원가입
export const req_register = (data: User) => request.post<any, ResponseData>(API.REGISTER_URL, data)

//로그인
export const req_login = (data:object) => request.post<any, any>(API.LOGIN_URL, data)

//유저정보 가져오기
export const req_userInfo = () => request.get<any, any>(API.USERINFO_URL)

