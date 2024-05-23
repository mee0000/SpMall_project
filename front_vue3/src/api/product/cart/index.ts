import request from "@/utils/request";
import type { Cart } from "./type"
enum API {
    SAVECART_URL = '/product/saveCart',
    CARTLIST_URL = '/product/cartList',
    CARTOTAL_URL = '/product/cartTotal',
    UPDATEDQTY_URL = '/product/updateCart',
    DELETEBYIDS_RUL = '/product/deleteCart'
}

//차트 상품 추가 및  업데이트
export const req_saveCart = (data: Cart) => request.post<any, any>(API.SAVECART_URL, data)

//상품추가 리스트 가져오기 ->by userId
export const req_getCartList = (userId: number) => request.get<any, any>(API.CARTLIST_URL + `/${userId}`)

// 차트 수량
export const req_cartTotal = (userId: number) => request.get<any, any>(API.CARTOTAL_URL + `/${userId}`)

// 차트 수량 수정
export const req_changeQty = (cart: Cart) => request.post<any, any>(API.UPDATEDQTY_URL, cart)

// 삭제 차트
export const req_deleteCartByIds = (userId: number, ids: string) => request.delete<any, any>(API.DELETEBYIDS_RUL + `/${userId}`, {data:ids}) 