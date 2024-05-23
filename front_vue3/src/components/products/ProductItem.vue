<template>   
<div class="single-product-wrapper">
        <!-- Product Image -->
        <div class="product-img">
          <base-button link :to="detailPath">
            <img :src="props.sku.skuDefaultImg" />
          </base-button>
          <!-- Hover Thumb -->
        </div>

        <!-- Product Description -->
        <div
          class="product-description d-flex align-items-center justify-content-between"
        >
          <!-- Product Meta Data -->
          <div class="product-meta-data">
            <div class="line"></div>
            <p class="product-price"><span :class="{'price-old__amount':newPrice != props.sku.price}">₩ {{props.sku.price}}</span><span v-show="props.sku.discount > 0"> ₩ {{newPrice}}<span class="discount">{{props.sku.discount}}%</span></span></p>
            <base-button link :to="detailPath">
              <h6>{{props.sku.spuName}}</h6>
            </base-button>
          </div>
          <!-- Ratings & Cart -->
          <div class="ratings-cart text-right">
             <span class="product-tag" v-show="props.sku.quantity < 5">품절임박</span>
            <div class="cart">
              <base-button
                @click="addCart"
                data-toggle="tooltip"
                data-placement="left"
                title="Add to Cart"
                ><img src="@/assets/core-img/cart.png" alt=""/></base-button>
            </div>
          </div>
        </div>
</div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue"
import { ElMessage } from 'element-plus'
import {checkUser} from '@/hooks/loginConfirm'
import type { Cart} from '@/api/product/cart/type'
import useUserStore from '@/stores/modules/user'
import { req_saveCart } from '@/api/product/cart/index'
import useCartStore from '@/stores/modules/cart'

const props = defineProps(['sku']) //skuInfo 정보
const qty = ref<number>(1) // 수량
let userStore = useUserStore() // 유저 정보
let cartStore = useCartStore() // 차트

let { loginConfirm } = checkUser() // cart --> 로그인 필요
const newPrice = computed(()=> { // 할인후 금액
  return props.sku.price * (100 - props.sku.discount)/100
})

// 상세페이지 이동
const detailPath = computed(()=> {
    return {
      name: 'productDetail',
      query: {
        sku: `${props.sku.id}:${props.sku.skuName}`,
        spuId: props.sku.spuId,
        title: props.sku.spuName
      }
    }
})

// 장바구니 저장
const addCart =async () => {
    // 로그인후 장바구니 넣기
    if (loginConfirm())return false 
        // cart
        const cart:Cart = {
            id: '',
            userId: userStore.userInfo.id,
            skuId: props.sku.id,
            qty: qty.value,
         }
        // addCart
        const result = await req_saveCart(cart)
        if(result.code == 200) {
            ElMessage({
                type: "success",
                message: '상품보관함에 등록했습니다.'
            })
          // 유저의 차트리스트 수량 가져오기
          await cartStore.getCartTotal(userStore.userInfo.id)
        }
}
</script>

<style scoped>
.product-tag {
  font-size: 12px;
  color: #959595;
}
.price-old__amount {
  text-decoration: line-through;
  font-size: 14px;
  color:#606266;
  margin-right: 5px;
}
.discount {
  font-size: 11px;
  color: red;
  margin:0 5px;
}
</style>
