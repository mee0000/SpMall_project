export interface responseData {
    code: number,
    message: string
}

export interface Cart {
    id: number| string,
    userId: number| string,
    skuId: number| string,
    createDate?: string,
    qty: number,
    skuSaleAttrs: string
}