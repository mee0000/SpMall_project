import request from "@/utils/request";
import type { spuResponseData } from './type'

enum API {
    SPU_URL = "/product/getSpuById"
}

export const req_getSpuById = (spuId:number) => request.get<any, spuResponseData>(API.SPU_URL+`/${spuId}`)
