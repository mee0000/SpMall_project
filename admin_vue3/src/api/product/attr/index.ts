// 상품속성관련 API
import request from "@/utils/request"
import type { categoryResponseData,attrResponseData, attr, ResponseData} from './type'
enum API {
    C1_URL = '/admin/getBaseCategoryList',
    C2_URL = '/admin/getCategory2',
    AttrInfo_URL = '/admin/product/attrInfoList',
    SAVE_ATTR = '/admin/product/saveAttrInfo',
    UPDATE_ATTR = '/admin/product/updateAttrInfo',
    DELETE_ATTR = '/admin/product/deleteAttr'
}

export const req_c1 = () => request.get<any, categoryResponseData>(API.C1_URL)
export const req_c2 = (category1Id: number | string) => request.get<any, categoryResponseData>(API.C2_URL + `/${category1Id}`)

// 상품속성리스트 가져오기
export const req_attrInfo = (category1Id: number | string, category2Id: number | string) => request.get<any, attrResponseData>(API.AttrInfo_URL + `/${category1Id}/${category2Id}`)

// 상품리스트 저장하기
export const req_saveAttr = (data: attr) => {
    console.log(data, '===========')
    if (data.id) {
        return request({url:API.UPDATE_ATTR, method:'put', data})
    } else {
        return request({url:API.SAVE_ATTR, method:'post', data})
    }
}
// 상품삭제하기 attrId에 의해
export const req_removeAttr = (attrId: number) => request.delete<any,ResponseData>(API.DELETE_ATTR+`/${attrId}`)