// type 정리
import { category } from '@/api/product/attr/type';
import type{ RouteRecordRaw } from 'vue-router'
export interface UserState {
    token: string | null,
    menuRoutes: RouteRecordRaw[],
    username: string,
    avatar: string,
    buttons: string[]
}

export interface CategoryState {
    c1List: category[],
    c2List: category[],
    category1Id: number | string,
    category2Id: number | string
}