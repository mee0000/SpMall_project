<template>
 <!-- Checkout Area End -->
        <div class="cart-table-area section-padding-100">
            <div class="container-fluid">
                <div class="row">
                       <div class="cart-title">
                                <h2>Checkout</h2>
                            </div>
                             <!--order Cart Table Start -->
                             <OrderCart :orderList="orderList"/>
                            <!--order Cart Table end-->
                    <div class="col-12 col-lg-8">
                        <div class="checkout_details_area mt-50 clearfix">
                            <!--order User Form Table Start -->
                            <OrderForm :user="user"/>
                            <!--order User Form Table end-->
                        </div>
                    </div>
                    <!--CartSummary start-->
                    <CartSummary title="Checkout" :total="total" style="margin-top: -50px;" mode="pay" @payment="payment">
                               <div class="payment-method">
                                <!-- Cash on delivery -->
                                <div class="custom-control custom-checkbox mr-sm-2">
                                    <input type="checkbox" class="custom-control-input" id="cod">
                                    <label class="custom-control-label" for="cod">Cash on Delivery</label>
                                </div>
                                <!-- Paypal -->
                                <div class="custom-control custom-checkbox mr-sm-2">
                                    <input type="checkbox" class="custom-control-input" id="paypal">
                                    <label class="custom-control-label" for="paypal">Paypal <img class="ml-15" src="" alt=""></label>
                                </div>
                            </div>
                    </CartSummary>
                    <!--CartSummary end-->
                </div>
            </div>
        </div>
</template>

<script setup lang="ts">
import OrderCart from '@/components/payment/OrderCart.vue'
import OrderForm from '@/components/payment/OrderForm.vue'
import CartSummary from '@/components/payment/CartSummary.vue'
import { req_getOrder, req_payment } from '@/api/product/order'
import {req_changeQty, req_deleteCartByIds } from '@/api/product/cart'
import { defineProps, onMounted, reactive, computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import useUserStore from '@/stores/modules/user'
import useCartStore from '@/stores/modules/cart'
import router from '../../router'
import { ElMessage } from 'element-plus'
let userStore = useUserStore() //유저 스토어
let cartStore = useCartStore() // 차트 스토어
let orderList = reactive([]) // 오더 리스트
let $route = useRoute() // 유저 정보
let $router = useRouter()
let orderItem = reactive({ // 오더아이템
    id: '',
    skuId: '',
    orderId: '',
    orderQty: '',
    orderPrice: ''
})

let ids = ref('')

let user = reactive({ //유저
    id: '',
    userName: '',
    firstName: '',
    lastName: '',
    address: '',
    zipCode: '',
    phoneNumber: ''
})
// 오더리스트 가져오기
onMounted(()=> {
    getOrderList()
})

// 구매 총  금액, 수량, 할인후 금액
const total = computed(() => {
   // 구매총 금액
   const price = orderList.reduce((acc, item)=> {
        acc += item.skuInfo.price * item.qty
      return acc
   }, 0)
   // 총수량
   const num = orderList.reduce((acc, item)=> {
        acc += item.qty
      return acc
   }, 0)
   // 할인후 금액
   const priceMin = orderList.reduce((acc, item)=> {
        acc += item.skuInfo.goods_price_min * item.qty
      return acc
   }, 0)
   return {price, num, priceMin}
})
// buyNow --> sku, qty 가져오기

// cart ---> cart 에서 가져오기
const getOrderList = async() => {
   Object.assign(user, userStore.userInfo)
   if($route.query.mode == 'buyNow') {
     const { skuId, qty ,mode} = $route.query
     const result = await req_getOrder(mode, {
        skuId,
        qty
     })
     Object.assign(orderList, result.data)
   }else if($route.query.mode == 'cart') {
     const { cartIds, mode } = $route.query
     ids.value = cartIds
     const result = await req_getOrder(mode, {
        cartIds
     })
     Object.assign(orderList, result.data)
   }
}
// 결제하기 , 오더 완료 
const payment =async ()=> {
  const orderData = orderList.map(item => {
    return {
        id: '',
        skuId: item.skuInfo.id,
        orderId: '',
        orderQty: item.qty,
        orderPrice: item.skuInfo.goods_price_min
    }
  })
  // 유저 + cartIds 를 보내기
  const data = {
    user,
    orderList: orderData
  }
  const result = await req_payment(data)
  if(result.code == 200) {
    const userId = userStore.userInfo.id
    if(ids.value != '') {
      const r = await req_deleteCartByIds(userId, ids.value)
      }
      ElMessage({
        type: 'success',
        message: '결제완료'
      })
      cartStore.getCartTotal(userId)
      $router.replace("/home")
  }
}

</script>

<style scoped>

</style>