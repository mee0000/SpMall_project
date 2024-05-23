export interface ResponseData {
    code: number,
    message: string
}

export interface orderItem {
    id: number | string,
    skuId: number | string,
    skuName: string,
    spuName: string,
    skuSaleAttr: string,
    orderId: number | string,
    orderQty: number | string,
    orderPrice: string
}