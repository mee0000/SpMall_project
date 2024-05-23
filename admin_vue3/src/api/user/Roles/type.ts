// 반환값의 type를 정의한다.
export interface ResponseData {
  code: number,
  message: string
}
export type Roles = role[]
export interface role {
   id: number | string,
   name: string;
   createTime: string;
   updateTime: string;
}
export type rows = Roles
export interface allRoleData extends ResponseData {
    data: {
        total: number,
        rows: rows
    }
}

//메뉴 와 버튼 데이터의 ts type
export interface Permisstion {
  id?: string|number,
	createTime: string,
	updateTime: string,
	pid: number,
	name: string,
	code: string,
	toCode: string,
	type: number,
	status: null,
	level: number,
	children?: PermisstionList,
	select: boolean
}

export type PermisstionList = Permisstion[]

// menu 권한 및 버튼 권한 ts 타입
export interface MenuResponseData extends ResponseData {
  data: Permisstion
}