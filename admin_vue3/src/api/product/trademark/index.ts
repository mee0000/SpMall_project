
import axios from "axios"
import type { TrademarkResponseData, Trademark } from './type'
// 
enum API{
    //브랜드 url 가져오기 /admin/product/baseTrademark/{page}/{limit}
    TRADEMARK_URL = "http://sph-api.atguigu.cn/admin/product/baseTrademark",
    TRADEMARK_SAVE_URL = "http://sph-api.atguigu.cn/admin/product/baseTrademark/save",
    TRADEMARK_UPDATE_URL = "http://sph-api.atguigu.cn/admin/product/baseTrademark/update",
}

// 브랜드 값 요청 (인자값 page(현재페이지)/ limit(한페이지 수량) )
export const reqHasTrademark = (page: number, limit: number) =>{
   return axios.get<any, TrademarkResponseData>(API.TRADEMARK_URL+`/${page}/${limit}`)
}

// saveTrademark
export const reqSaveTrademark = (trademark: Trademark ) => {
    // id값이 있으면 수정 / 없으면 추가 
    console.log('saveTrademark', trademark)
    if(trademark.id) {
      return axios.put(API.TRADEMARK_UPDATE_URL, {data: trademark})
    } else {
       return axios.post(API.TRADEMARK_SAVE_URL, {data: trademark})
    }
}