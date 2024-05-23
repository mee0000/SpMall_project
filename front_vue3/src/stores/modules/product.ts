import { defineStore } from "pinia"
import { req_skuList } from "@/api/product/sku"
import { ProductState } from "./types/type"
import { req_getSpuById } from '@/api/product/spu/index'
import type { SkuResponseData } from '@/api/product/sku/type'
import type{ spuResponseData } from '@/api/product/spu/type'
let useProductsStore = defineStore('product', {
    state:():ProductState => {
        return {
            skuList: [], // 모든 skuList 가져오기 상품리스트 ->shop
            total: 0, // skuList total 가져오기 -> shop
            spu: {}, // 한개의 spu 가져오기 -> productDetail
            skuInfoList: [] // spuId에 의해 skuInfoList 가져오기 -> productDetail
        }
    },
    actions: {
        async getSkuList(pageNo:any, pageSize:any, searchParams:any) {
            const {data:{rows,total}}:SkuResponseData = await req_skuList(pageNo, pageSize, searchParams)
            this.skuList = rows
            this.total = total
        },
        async getSpuById(spuId:number){
            const { data }: spuResponseData = await req_getSpuById(spuId)
            this.spu = data
            if(data.skuInfoList) {
                this.skuInfoList = data.skuInfoList
            }
       }
    }
})

export default useProductsStore;