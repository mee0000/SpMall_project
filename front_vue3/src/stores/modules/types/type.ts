import { Category } from '@/api/category/type'
import type { SkuInfo } from '@/api/product/sku/type'
import type { spu } from '@/api/product/spu/type'
import { User } from '@/api/user/type'
import { RouteRecordRaw } from 'vue-router'
export interface CategoryState {
    c1List: Category[],
    c2List: Category[],
    category1Id: number | string,
    category2Id: number | string
}

export interface ProductState {
    skuList: SkuInfo[],
    total: number | '',
    spu: spu | {},
    skuInfoList: SkuInfo[]
}

export interface UserState {
    menuRoutes: RouteRecordRaw[],
    userInfo: User | {},
    token: null | string
}