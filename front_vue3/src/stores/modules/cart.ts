import { defineStore } from "pinia";
import { req_getCartList, req_cartTotal } from '@/api/product/cart'
let useCartStore = defineStore('cart', {
    state: () => {
        return {
            cartList: [],
            total: 0
        }
    },
    actions: {
        async getCartList(userId: number) { 
            const result = await req_getCartList(userId)
            if (result.code == 200) {
                this.cartList = result.data
            } else {
                return Promise.reject(new Error(result.data))
            }
        },
        async getCartTotal(userId: number) {
             const {data} = await req_cartTotal(userId)
             this.total = data
        }
    }
})

export default useCartStore;