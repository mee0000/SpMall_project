import request from "@/utils/request";
import type { } from './type';

enum API {
    ATTR_URL = "/product/getBaseAttrList"
}

export const req_getBaseAttrAndValue = (category1Id: number,category2Id: number) => request.get<any, any>(API.ATTR_URL+ `/${category1Id}/${category2Id}`)