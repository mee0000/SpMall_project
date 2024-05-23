import { defineStore } from "pinia";
import { req_c1 } from '@/api/category/index'
import { CategoryState } from './types/type'
import type { categoryResponseData } from '@/api/category/type'

let useCategoryStore = defineStore('category', {
    state:():CategoryState => {
        return {
            c1List: [],
            c2List: [],
            category1Id: '',
            category2Id: ''
        }
    },
    actions: {
        async getC1() {
            const result: categoryResponseData = await req_c1()
            if (result.code == 200) {
                this.c1List = result.data
            }
       }
    }
})

export default useCategoryStore;
