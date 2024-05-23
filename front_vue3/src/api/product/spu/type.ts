export interface ResponseData {
    code: number,
    message: string
}
export interface spu {
    id: string | number,
    spuName: string,
    description: string,
    category2Id: string | number,
    skuInfoList: skuInfoList | null,
    spuSaleAttrList: skuAttrValueList | null,
    spuImageList: imgList | null
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
export type skuInfoList =skuInfo[]
export interface skuInfo{
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
export type skuAttrValueList = skuAttrValue[]
export interface skuAttrValue {
    id: string | number,
    spuAttr: string,
    spuAttrValueName: string,
    skuId: string | number
}

export type imgList = img[]
export interface img {
    id?: number | undefined,
    imgName?: string,
    imgUrl?: string,
    // createTime?: string | undefined,
    // updateTiem?: string | undefined,
    spuId?: number | undefined,
    name?: string,
    url?: string
}
export type rows = spu[]
export interface spuResponseData extends ResponseData {
   data: spu
}
