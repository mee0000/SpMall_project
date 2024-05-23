// 반환값의 type를 정의한다.
export interface ResponseData {
  code: number,
  message: string
}

export interface permission {
  id: number | string,
  pid: number | string,
  name: string,
  code: string,
  toCode: string,
  type: number,
  status: string,
  level: number,
  checked: string,
  select: string,
  path: string,
  createTime: string,
  updateTime: string,
  children: permission[]
}

export interface rPermission {
  id: number,
  r_id: number,
  p_id: number,
  type: number,
  level: number,
  p_name: string,
  code: string
}

export type rows = permission[]

export interface permissionResponseData extends ResponseData {
  data: {
    total: number,
    rows: rows
  }
}

export interface pResponseData extends ResponseData {
  data: permission
}