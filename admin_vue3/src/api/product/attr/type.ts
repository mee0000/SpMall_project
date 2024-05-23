// product 속성 type 관련
export interface ResponseData {
    code: number,
    message: string
    ok: boolean
}
// category 유형
export interface category {
    id: number | string,
    categoryName: string,
    category1Id?: number
}

export interface categoryResponseData extends ResponseData{
    data: category[]
}
// attrValue
export interface attrValue {
    id?: number|string,
    attrId?: number|string,
    valueName: string
}

// 속성값 리스트
export type attrValueList= attrValue[]

// 상품속성 type
export interface attr {
    id?: number|string,
    attrName: string,
    categoryId: number|string,
    categoryLevel: number,
    attrValueList: attrValueList
}

export interface attrResponseData extends ResponseData {
    data: attr
}