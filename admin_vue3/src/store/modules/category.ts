import { defineStore } from 'pinia'
import { req_c1, req_c2} from '@/api/product/attr'
import { CategoryState } from './types/type'
import type { categoryResponseData } from '@/api/product/attr/type'
let useCategoryStore = defineStore('category', {
    state: ():CategoryState => {
        return {
            c1List: [],
            category1Id: '',
            category2Id: '',
            c2List: []
        }
    }, 
    getters: {

    },
    actions: {
       async getC1() {
            const result:categoryResponseData = await req_c1()
            if (result.code == 200) {
                this.c1List = result.data
            }
        },
        async getC2() {

            const result:categoryResponseData = await req_c2(this.category1Id)
            if (result.code == 200) {
                this.c2List = result.data
            }
      }
    }

})

export default useCategoryStore