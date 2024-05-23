import request from "@/utils/request"
import type { spuResponseData, spu, ResponseData, baseSaleAttrResponseData, spuSaleAttrResponseData } from './type'
enum API {
    SPU_URL = '/admin/product/getSpuList',
    SPUBYID_URL = '/admin/product/getSpuById',
    SPU_UPDATE_URL = '/admin/product/updateSpuInfo',
    SPU_SAVE_URL = '/admin/product/saveSpuInfo',
    SPU_REMOVE_URL = '/admin/product/deleteSpu',
    BASE_SALE_ATTR_URL = '/admin/product/baseSaleAttrList',
    SPU_SALEATTRLIST_URL = '/admin/product/spuSaleAttrList',
    SPU_IMG_URL = '/admin/product/getSpuImageList',
    POLICY_URL = '/oss/policy'
}

// getSpuList
export const req_spu =
    (category2Id: number | string, page: number, pageSize: number) => {
        return request<any, spuResponseData>({ url: API.SPU_URL + `/${page}/${pageSize}`,params:{category2Id}, method:'get'})
    }
// getBaseSaleAttrList
export const req_getBaseSaleAttrList = () => request.get<any, baseSaleAttrResponseData>(API.BASE_SALE_ATTR_URL)
// spu한개의 데이터를 가져오기
export const req_getSpuById = (id: string | number) => request.get<any, spu>(API.SPUBYID_URL + `/${id}`) 

// saveAndUpdate
export const req_saveAndUpdate = (spu: spu) => {
    if (spu.id) {
      return request<any, ResponseData>({url: API.SPU_UPDATE_URL, method: 'put', data: spu})
    } else {
      return request<any, ResponseData>({url: API.SPU_SAVE_URL, method: 'post', data: spu})
    }
}
// getSpuSaleList
export const req_spuSaleAttrList = (spuId: number) => request.get<any,spuSaleAttrResponseData>(API.SPU_SALEATTRLIST_URL+ `/${spuId}`) 
// removeSpuInfo
export const req_removeSpuById = (spuId: number) => {
  return request<any, ResponseData>({url: API.SPU_REMOVE_URL + `/${spuId}`, method:'delete'})
}
//getSPUImage
export const req_imageList = (spuId: number) => request.get<any, any>(API.SPU_IMG_URL + `/${spuId}`)

// get POlicy
export const req_policy = () => request.get(API.POLICY_URL) 