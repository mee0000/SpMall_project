import request from '@/utils/request'
import type {
  loginFormData,
  loginResponseData,
  userInfoResponseData,
  user,
  userRoles,
  ResponseData,
  roleResponseData
} from './type'

enum API {
  LOGIN_URL = '/admin/acl/index/login',
  USERINFO_URL = '/admin/acl/index/info',
  LOGOUT_URL = '/admin/acl/index/logout',
  USERLIST_URL = '/admin/user/list',
  ADDUSER_URL = '/admin/user/add',
  UPDATEUSER_URL = '/admin/user/update',
  DELETEUSER_URL = '/admin/user/delete',
  DELETEUSERS_URL = '/admin/user/deleteUsers',
  ROLES_URL = '/admin/user/getRoles',
  BUTTONS_URL = '/admin/user/getButtons',
  ROUTES_URL = '/admin/user/getRoutes',
  SAVE_USER_ROLE_URL = '/admin/user/saveUserRoles',
  GET_USER_ROLE_URL = '/admin/user/getUserRoles'
}

export const reqLogin = (data: loginFormData) =>
  request.post<any, loginResponseData>(API.LOGIN_URL, data)

export const reqUserInfo = () =>
  request.get<any, userInfoResponseData>(API.USERINFO_URL)

export const reqLogOut = () => request.post<any, any>(API.LOGOUT_URL)

export const req_getUserList = (pageNo: number, pageSize: number, keyword: string) => {
  console.log('keyword', pageNo)
  return request<any, userInfoResponseData>({url: API.USERLIST_URL + `/${pageNo}/${pageSize}`, params: {keyword}, method: 'get'})
}

export const req_saveUser = (data: user) => {
  console.log(data.id, '------id 값있는지 확인')
  if (data.id) {
    return request.put<any, ResponseData>(API.UPDATEUSER_URL, data)
  } else {
    return request.post<any, ResponseData>(API.ADDUSER_URL, data)
  }
}

// 삭제 
export const req_deleteUser = (id: number) => {
  return request.delete<any, ResponseData>(API.DELETEUSER_URL + `/${id}`)
}

//여러개 삭제 
export const req_deleteUsersByIds = (userIds: number[]) => {
  return request.delete<any, ResponseData>(API.DELETEUSERS_URL, {data:userIds})
}

// 모든 역할 가져오기
export const req_getRoles = () => request.get<any, ResponseData>(API.ROLES_URL)

// 모든 버튼  가져오기
export const req_getButtons = () => request.get<any, any>(API.BUTTONS_URL)

// 모든 Routes 가져오기
export const req_getRoutes = () => request.get<any, any>(API.ROUTES_URL)
// getUserRoles
export const req_getUserRoles = (userId: number) => request.get<any, roleResponseData>(API.GET_USER_ROLE_URL + `/${userId}`)
// save user Roles
export const req_saveUserRoles = (userId: number, data: userRoles) => request.post<any, ResponseData>(API.SAVE_USER_ROLE_URL + `/${userId}`, data)