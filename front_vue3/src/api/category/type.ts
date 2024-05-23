export interface ResponseData {
    code: number,
    message: string
}

// category1
export interface Category {
    id: number | string,
    categoryName: string,
    level?: number,
    category2List: Category2[]
}

// category2
export interface Category2 {
    id: number,
    categoryName: string,
    category1Id: number
}

// export
export interface categoryResponseData extends ResponseData {
    data: Category[]
}