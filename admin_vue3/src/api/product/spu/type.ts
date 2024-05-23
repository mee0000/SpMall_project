export interface ResponseData {
    code: number,
    message: string
    ok: boolean
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

export type skuInfoList =skuInfo[]

export interface skuInfo{
    id: string | number,
    spuId: string | number,
    quantity: number,
    skuName: string,
    spuAttrValue: string,
    category2Id: string | number,
    skuDefaultImg: string,
    discount: string,
    createTime: string,
    updateTime: string
    skuAttrValueList:skuAttrValueList | null
}
export type skuAttrValueList = skuAttrValue[]
export interface skuAttrValue {
    id: string | number,
    spuAttr: string,
    spuAttrValueName: string,
    skuId: string | number
}
export type spuSaleAttrList = spuSaleAttr[]
export interface spuSaleAttr{
    id: string | number
    spuId: string | number
    baseAttrId: string | number
    baseAttrName: string
    spuSaleAttrValueList: spuSaleAttrValueList | null
}
export interface baseSaleAttr {
    id: string | number
    attrName: string
}
export type spuSaleAttrValueList = spuSaleAttrValue[]
export interface spuSaleAttrValue {
    id: string | number
    spuId: string | number
    spuSaleAttrId: string | number
    spuAttrValueName: string
}
export interface baseSaleAttrResponseData extends ResponseData {
  data: baseSaleAttr[]
}

export interface spuSaleAttrResponseData extends ResponseData {
  data: spuSaleAttrList
}
export type rows = spu[]
export interface spuResponseData extends ResponseData {
    data: {
        total: number,
        rows: rows
    }
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

export interface spuImgResponseData extends ResponseData {
    data: imgList
}