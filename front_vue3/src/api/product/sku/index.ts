import request from "@/utils/request";
import type { SkuResponseData, SkuInfoResponseData } from "./type";
enum API {
    SKULIST_URL = "/product/getSkuList",
    SKU_URL = "/product/getSkuById",
    SALE_PRODUCTS_URL = "/product/getSaleProductList"
}

// skuList 가져오기
export const req_skuList = (pageNo:number, pageSize:number, searchParams: any) => {
    return request<any, SkuResponseData>({ url: API.SKULIST_URL + `/${pageNo}/${pageSize}`, params:searchParams, method: 'get' })
}

//sku 한개 가여오기
export const req_getOneSku = (skuId:number) => {
    return request<any,SkuInfoResponseData>({url:API.SKU_URL+`/${skuId}`})
}

//세일 상품 리스트 
export const req_getSaleProducts = (pageNo:number, pageSize:number, searchParams:any) => {
    return request<any, SkuResponseData>({ url: API.SALE_PRODUCTS_URL + `/${pageNo}/${pageSize}`, params:searchParams, method: 'get' })
}