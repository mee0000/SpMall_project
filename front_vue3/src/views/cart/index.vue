<template>
     <!-- Cart Area Start -->
      <base-container>
                       <div class="col-12 col-lg-8">
                           <div class="cart-title mt-50">
                            <h2>Shopping Cart</h2>
                        </div>
                        <!-- Table Start-->
                        <div>
                           <table class="table table-bordered">
                              <tr>
                                    <th scope="col"><input type="checkbox" @change="changeAllChecked" v-model="allChecked" /></th>
                                    <th scope="col">상품이미지</th>
                                    <th scope="col">상품/옵션정보</th>
                                    <th scope="col">상품금액</th>
                                    <th scope="col">수량</th>
                              </tr>
                              <tr v-if="cartList.length > 0" v-for="(cart,index) in cartList" :key="cart.id">
                                    <th scope="row"><input type="checkbox" :checked="cart.done" @change="isChecked(cart)"/></th>
                                    <td><img :src="cart.skuInfo.skuDefaultImg" style="width:50px;"></td>
                                    <td>{{cart.skuInfo.spuName}}
                                         <p><span v-for="item in cart.skuInfo.skuSaleAttrValueList" style="margin-right:2px; font-size:12px;">{{`${item.saleAttrName}:${item.saleAttrValueName}`}}</span></p>
                                    </td>
                                    <td ><span v-show="cart.skuInfo.discount > 0" class="goods_price_min">{{cart.skuInfo.goods_price_min * cart.qty}}</span><span :class="{'price-old__amount':cart.skuInfo.goods_price_min != 0}">{{cart.skuInfo.price * cart.qty}}</span></td>
                                    <td> <el-input-number v-model="cart.qty" :min="1" :max="cart.skuInfo.quantity" @change="changeQty(cart)"/></td>
                              </tr>
                              <tr v-else>
                                   <td colspan="5" style="text-align: center; color:#dedede;">장바구니가 비었습니다</td>
                              </tr>
                              </table>
                        </div>
                        <!-- Table end-->
                    </div>
                    <!--CartSummary start-->
                    <CartSummary :total="total" @addOrder="addOrder" title="주문하기" mode="cart"/>
                    <!--CartSummary end-->
      </base-container>
</template>

<script lang="ts" setup>
import { onMounted, reactive, nextTick, computed,ref } from 'vue'
import CartSummary from '@/components/payment/CartSummary.vue'
import useUserStore from '@/stores/modules/user'
import useCartStore from '@/stores/modules/cart'
import {req_changeQty } from '@/api/product/cart'
import { useRouter } from 'vue-router'

let cartList = ref([]) // 차트 리스트
let userStore = useUserStore()
let cartStore = useCartStore()
let $router = useRouter()


// 바로 차트 리스트 가져오기
onMounted(()=> {
    getCartList()
})

// 아이템 선택여부 
const isChecked = (cart) => {
  cart.done = ! cart.done
  console.log(cart.done, '-----cart')
}
// allchecked
const allChecked = computed({
  get(){
    return cartList.value.every(item => item.done == true)
  },
  set(val) {
    cartList.value.forEach(item => {
      item.done = val
   })
  }
})

// 구매 총  금액, 수량, 할인후 금액
const total = computed(() => {
   // 구매총 금액
   const price = cartList.value.reduce((acc, item)=> {
      if(item.done) {
        acc += item.skuInfo.price * item.qty
      }
      return acc
   }, 0)
   // 총수량
   const num = cartList.value.reduce((acc, item)=> {
      if(item.done) {
        acc += item.qty
      }
      return acc
   }, 0)
   // 할인후 금액
   const priceMin = cartList.value.reduce((acc, item)=> {
      if(item.done) {
        acc += item.skuInfo.goods_price_min * item.qty
      }
      return acc
   }, 0)
   return {price, num, priceMin}
})

// 유저의 차트리스트 가져오기
const getCartList =async () => {
  if(userStore.userInfo.id) {
      let userId = userStore.userInfo.id
      try {
        await cartStore.getCartList(userId)
        nextTick(()=> {
        cartStore.cartList.forEach(item => {
            item.done = true
            cartList.value.push(item)
          })
        })
      } catch (error) {
        console.log(error)
      }
    
  }
}

// 수량 업데이트
const changeQty = async(cart) => {
  console.log('cart', cart)
  const result = await req_changeQty(cart)
  if(result.code ==200) {
    await cartStore.getCartTotal(userStore.userInfo.id)
  }
}
// 선택된 차트
const selectedCart = computed(()=> {
  return cartList.value.reduce((acc, item)=> {
      if(item.done) {
        acc.push(item)
      }
      return acc
   },[])
})
// 주문하기 
const addOrder = () => {
  const cartIds = selectedCart.value.map(item => item.id)
  $router.push({
    name:'payment',
    query: {
      mode: 'cart',
      cartIds: cartIds.join(',')
    }
  })
}
</script>

<style scoped>
.price-old__amount {
  text-decoration: line-through;
  font-size: 12px;
  color:#606266;
  margin-right: 5px;
}
.goods_price_min {
  font-size: 14px;
  color: red;
  margin-right: 5px;
}
</style>