
// product 속성 type 관련
export interface ResponseData {
    code: number,
    message: string
    ok: boolean
}

export type skuAttrList = skuAttr[]
export interface skuAttr {
   id : number |string,
    attrId: number | string,
    attrName: string,
    valueId: number | string,
    valueName: string,
   skuId :number | string
}

export type skuSaleAttrList = skuSaleAttr[]
export interface skuSaleAttr {
    id : number |string,
    saleAttrId: number | string,
    saleAttrName: string,
    saleValueId: number | string,
    saleValueName: string,
    skuId: number | string
}
// sku 기본 유형 
export interface skuInfo {
    id : number | string,
    spuId : number | string,
    quantity : number,
    skuName: string,
    price: number | string,
    spuName: string,
    skuSaleAttrValueList : skuSaleAttrList,
    category2Id : number | string,
    skuDefaultImg : number | string,
    discount : number,
    skuAttrValueList : skuAttrList
}

export interface skuResponseData extends ResponseData {
    data: skuInfo
}

export interface skuInfoData extends ResponseData {
    data: skuInfo[]
}