import request from "@/utils/request";
import type { skuResponseData, skuInfo, skuInfoData, ResponseData} from "./type";
enum API {
    SAVE_SKU_URL = '/admin/sku/saveSKUById',
    SKUINFO_URL = '/admin/sku/findSkuBySpuId',
    ALL_SKUINFO_URL = '/admin/sku/skuInfo/list',
    SKUSTATUS_URL = '/admin/sku/changeSkuStatus',
    DELETESKU_URL = '/admin/sku/deleteSkuById',
    UPDATE_SKU_URL = '/admin/sku/updateSku'
}

export const req_saveSku = (data: skuInfo) => {
    if (data.id) {
      return request.put<any, any>(API.UPDATE_SKU_URL, data)
    } else {
      return request.post<any,ResponseData>(API.SAVE_SKU_URL, data)
    }
}
// skuInfo 상품sku리스트 가져오기
export const req_getSkuInfoBySpuId = (spuId: number) => request.get<any, skuInfoData>(API.SKUINFO_URL + `/${spuId}`)

// skuInfo All 리스트
export const req_getAllSkuInfo = (page: number, pageSize: number, searchParms: any) => request.get<any, any>(API.ALL_SKUINFO_URL + `/${page}` + `/${pageSize}`, { params: searchParms })

// change Sku Status
export const req_changeSkuStatus = (data: skuInfo) => request.put<any, any>(API.SKUSTATUS_URL, data)

// skuInfo 삭제
export const req_deleteSkuById = (skuId: number) => request.delete<any, any>(API.DELETESKU_URL + `/${skuId}`)

