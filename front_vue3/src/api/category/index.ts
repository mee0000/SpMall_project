import request from "@/utils/request";

import type {categoryResponseData } from './type'

enum API {
   C1_URL = '/product/getBaseCategoryList',
   C2_URL = '/getCategory2'
}

// 카테고리 1 가져오기
export const req_c1 = () => request.get<any, categoryResponseData>(API.C1_URL)

// 카테고리 2 가져오기