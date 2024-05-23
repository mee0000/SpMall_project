export interface ResponseData {
    code: number,
    message: string
    ok: boolean
}

// 존재하고 있는 브랜드 ts 유형
export interface Trademark {
    id?: number,
    tmName: string,
    logoUrl: string
}

// 모든 브랜드 리스트 
export type Records = Trademark[]

// 브랜드리스트 획득 
export interface TrademarkResponseData extends ResponseData{
    data: {
        records: Records,
        total: number,
        size: number,
        current: number,
        pages: number,
        searchCount: boolean
    }
}