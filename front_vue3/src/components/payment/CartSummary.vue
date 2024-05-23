<template>
     <div class="col-12 col-lg-4">
                        <div class="cart-summary">
                            <h5>최종 결제금액</h5>
                            <ul class="summary-table">
                                <li><span>주문금액:</span> <span>{{total.price}}</span></li>
                                <li><span>할인금액:</span> <span>-{{total.price-total.priceMin}}</span></li>
                                <li><span>배송비:</span> <span>{{deliveryFee}}</span></li>
                                <li style="border-bottom:2px solid #dedede"></li>
                                <li><span>총 결제예정 금액:</span> <span>{{total.priceMin}}</span></li>
                            </ul>
                            <slot></slot>
                            <div class="cart-btn mt-100">
                                <button v-show=" mode == 'cart'" style="color:white;" class="btn amado-btn w-100" @click="$emit('addOrder')" :disabled="total.priceMin == 0">{{title}}({{total.num}})</button>
                                <button  v-show=" mode == 'pay'" style="color:white;" class="btn amado-btn w-100" @click="$emit('payment')" :disabled="!inputValid">{{title}}({{total.num}})</button>
                            </div>
                        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, onUnmounted, ref, onMounted } from "vue"
import emitter from '@/utils/emitter'
defineProps(['total','deliveryFee', 'title', 'mode'])
defineEmits(['addOrder'])
let inputValid = ref<boolean>(false)
let deliveryFee = 'Free' // 배송비

onMounted(() => {
    //inputValid 값부여
    emitter.on('changeValid', (valid)=> {
        console.log('--------inputVallid', valid)
        inputValid.value = valid
    
    })
})

onUnmounted(() => {
    emitter.all.clear()
})
</script>

<style scoped>

</style>