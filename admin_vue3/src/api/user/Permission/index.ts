import request from "@/utils/request";
import type {permission, permissionResponseData, ResponseData, pResponseData, rPermission} from './type'
enum API {
    PERMISSIONLIST_URL = '/admin/permission/list',
    SETPERMISSION_URL = '/admin/permission/doAssignAcl',
    GETPERMISSIONBYID_URL = '/admin/permission/toAssign',
    ADDPERMISSION_URL = '/admin/permission/addPermission',
    UPDATEPERMISSION_URL = '/admin/permission/savePermission',
    DELETEPERMISSION_URL = '/admin/permission/deletePermission'
}
// 권한 리트스 가져오기
export const reqPermissionList = () => request.get<any, permissionResponseData>(API.PERMISSIONLIST_URL)
// roleID 관한 권한 리스트 가져오기
export const req_permissionById = (roleId: number) => request.get<any, any>(API.GETPERMISSIONBYID_URL + `/${roleId}`)
// roleId 권한 주기
export const req_setPermission = (roleId: number, data: rPermission) => {
   return request.post<any, ResponseData>(API.SETPERMISSION_URL + `/${roleId}`, data)
}
// 권한 업데이트 혹은 새로추가
export const req_savePermission = (data: permission) => {
    if (data.id) {
        return request.put<any, ResponseData>(API.UPDATEPERMISSION_URL, data)
    } else {
        return request.post<any, ResponseData>(API.ADDPERMISSION_URL, data)
    }
}

// 권한 삭제
export const req_removePermission = (data: permission) => {
    return request.put<any, ResponseData>(API.DELETEPERMISSION_URL, data)
}