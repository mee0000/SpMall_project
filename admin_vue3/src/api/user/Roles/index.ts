import request from "@/utils/request";
import type {allRoleData, role, ResponseData} from './type'
enum API{
    ROLES_URL = "/admin/user/getAllRoles",
    SAVE_ROLE_URL = "/admin/user/saveRole",
    UPDATE_ROLE_URL = "/admin/user/updateRole",
    DELETE_ROLE_URL = "/admin/user/deleteRole"
}

export const req_allRoles = (page: number, pageSize: number, keyword: string) => {
    return request<any,allRoleData>({url: API.ROLES_URL+ `/${page}/${pageSize}`, method:'get', params:{keyword}})
}

export const req_saveRoles = (role: role) => {
    console.log(role, '--------')
    if (role.id) {
        return request<any, ResponseData>({url: API.UPDATE_ROLE_URL, method: 'put', data:role})
    } else {
        return request<any, ResponseData>({url: API.SAVE_ROLE_URL, method: 'post', data:role})
    }
}

// deleteRole
export const req_deleteRoles = (role: role) => request.delete<any, ResponseData>(API.DELETE_ROLE_URL, {data:role})