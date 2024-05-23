import request from "@/utils/request";

enum API {
    ORDER_URL = '/product/getOrder',
    PAYMENT_URL = '/product/payment'
}

export const req_getOrder = (mode: string, obj: any) => {
    return request<any, any>({url: API.ORDER_URL, params: {mode,dilivery:0,...obj}, method:'get'})//sku 한개 가여오기
}

export const req_payment = (data: any) => {
    return request<any, any>({ url: API.PAYMENT_URL, data, method:'post' })
}