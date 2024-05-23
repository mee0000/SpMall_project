// 로그인 폼데이터 유형
export interface loginFormData {
  username: string,
  password: string
}
// 반환값의 type를 정의한다.
export interface ResponseData {
  code: number,
  message: string
}

// 로그인후 반환하는 값 -> token
export interface loginResponseData extends ResponseData {
   data: string
}

// userInfo type
export interface userInfoResponseData extends ResponseData {
  data: {
    routes: string[],
    buttons: string[],
    roles: string[],
    name: string,
    avatar: string
  }
}

export interface role {
  id?: number | string,
  roleName: string,
  userId: number | string,
  roleId: number | string
}
export type userRoles = role[]

export interface user {
    id: number | string,
    createTime: string,
    updateTime: string,
    nickName: string,
    username: string,
    avatar: string,
    roles: userRoles,
    buttons: string[] | null,
    routes: string[] | null
}


export type rows = user[]
// userList
export interface userResponseData extends ResponseData {
  data: {
    total: number,
    rows: rows
  }
}
  // userRoles
export interface roleResponseData extends ResponseData {
  data: role
}
