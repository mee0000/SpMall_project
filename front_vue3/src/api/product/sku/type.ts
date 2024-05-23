// product 속성 type 관련
export interface ResponseData {
    code: number,
    message: string
    ok: boolean
}

export type SkuAttrList = SkuAttr[]
export interface SkuAttr {
   id : number |string,
    attrId: number | string,
    valueId: number| string,
   skuId :number | string
}

export type SkuSaleAttrList = SkuSaleAttr[]
export interface SkuSaleAttr {
    id : number |string,
    saleAttrId: number | string,
    saleValueId: number| string,
    skuId :number | string
}
// sku 기본 유형 
export interface SkuInfo {
    id : number | string,
    spuId : number | string,
    quantity : number | string,
    skuName: string,
    price: number | string,
    skuSaleAttrValueList? : SkuSaleAttrList,
    category2Id : number | string,
    skuDefaultImg : number | string,
    discount : number,
    skuAttrValueList : SkuAttrList
}
export interface SkuInfoResponseData extends ResponseData {
    data: SkuInfo
}
export interface SkuResponseData extends ResponseData {
    data: {
        total: number,
        rows: SkuInfo[]
    }
}
