<template>
 <!-- Product Details Area Start -->
        <div class="single-product-area section-padding-100 clearfix">
            <div class="container-fluid">
                <!-- Product Details SubNavigation Start -->
                <Tabbar/>
                 <!-- Product Details SubNavigation End -->
          
                <div class="row">
                    <div class="col-12 col-lg-7">
                        <div class="single_product_thumb" v-show="spu.spuImageList">
                            <ImageSwiper :skuImageList="spu.spuImageList"/>
                        </div>
                      </div>
                    <div class="col-12 col-lg-5">
                        <div class="single_product_desc">
                            <!-- Product Meta Data -->
                            <div class="product-meta-data">
                                <div class="line"></div>
                                <p class="product-price"><span :class="{'price-old__amount':skuInfo.discount > 0}"> ₩ {{skuInfo.price}}</span></p>
                                <p class="product-price" v-show="skuInfo.discount > 0"><span >₩ {{skuInfo.goods_price_min}}<span class="discount">{{skuInfo.discount}}%</span></span></p>
                                <a href="product-details.html">
                                    <h6>{{spu.spuName}}</h6>
                                </a>
                                <!-- Ratings & Review -->
                                <div class="ratings-review mb-15 d-flex align-items-center justify-content-between">
                                    <div class="ratings">
                                        <i class="fa fa-star" aria-hidden="true" v-for="i in 5"></i>
                                    </div>
                                    <div class="review">
                                        <a href="#">Write A Review</a>
                                    </div>
                                </div>
                                <!-- Avaiable -->
                                <p class="avaibility"><i class="fa fa-circle"></i> In Stock</p>
                            </div>

                            <div class="short_overview my-5">
                                <p>{{spu.description}}</p>
                            </div>

                            <!-- Add to Cart Form -->
                            <form class="cart clearfix">
                            <table class="table table-bordered" v-show="skuInfoList.length > 0">
                              <DetailTable :skuInfoList="spu.skuInfoList" :spuSaleAttrList="spu.spuSaleAttrList"/>
                            </table>
                            <div>
                             <div style="margin: 10px 0px;">
                                <label style="margin-right: 10px;"> product </label>
                                    <el-select
                                        v-model="skuIdAndValue"
                                        style="width: 240px"
                                        >
                                        <el-option
                                            v-for="item in skuInfoList"
                                            :key="item.id"
                                            :label="item.skuName"
                                            :value="`${item.id}:${item.skuName}`"
                                        />
                                    </el-select>
                            </div>
                            <div style="margin: 10px 0px;">
                                 <label style="margin-right: 35px;"> 수량: </label>
                                <el-input-number v-model="qty" :min="1" :max="maxQty" :disabled="maxQty == 0"/>
                                <span v-show="maxQty == 0" style="margin-left:10px; color:red">품절</span>
                            </div>
                            </div>
                            </form>
                            <div style="display:flex; justify-content: space-between;">
                                <button @click="addCart" class="submint_btn">장바구니<span style="margin-left:10px; color:blue"></span></button>
                                <button @click="payment" class="submint_btn">바로구매</button>
                            </div>
                            

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Product Details Area End -->
        <!-- Product Dialog Area Start -->
        <DetailDialog :skuInfo="skuInfo" :qty="qty" :show="isShowDialog" :tryClose="handle" title="구매할 상품확인" fixed/>

</template>

<script setup lang="ts">
import Tabbar from '@/components/productDetail/Tabbar.vue'
import DetailTable from '@/components/productDetail/DetailTable.vue'
import DetailDialog from '@/components/productDetail/DetailDialog.vue'
import {checkUser} from '@/hooks/loginConfirm'
import { req_saveCart } from '@/api/product/cart/index'
import { defineProps, onMounted, reactive, ref, computed, watch, nextTick} from 'vue'
import { useRoute, useRouter} from 'vue-router'
import {storeToRefs} from 'pinia'
import useProductStore from '@/stores/modules/product'
import ImageSwiper from '@/components/ImageSwiper/index.vue'
import useUserStore from '@/stores/modules/user'
import useCartStore from '@/stores/modules/cart'
import { userInfo } from 'os'
import type { Cart} from '@/api/product/cart/type'
import { ElMessage } from 'element-plus'

let currentIndex = ref(0) // 슬라이드 페이지
let $route = useRoute() // 페이지 주소및 쿼리내용
let $router = useRouter() //페이지이동시
let skuIdAndValue = ref('')
let qty = ref(1) // 구매수량
let skuInfo = ref({}) // 선택된 skuInfo
let productStore = useProductStore()
let userStore = useUserStore()
let cartStore = useCartStore() // 차트
let isShowDialog = ref<boolean>(false) // dialog 스위치
let { loginConfirm } = checkUser()
onMounted(() => {
    getSpuById()
})

// 이미지 편경
const changeImg =(index)=> { currentIndex.value = index}

//getSpuByID
const getSpuById= async ()=> {
   await productStore.getSpuById($route.query.spuId)
   skuIdAndValue.value = $route.query.sku
}
const {spu, skuInfoList} = storeToRefs(productStore)

//상품선택시 해당 최대구매수량 구하기
watch(()=>skuIdAndValue.value, (newValue)=> {
    const [skuId, skuName] = newValue.split(':')
    skuInfo.value =skuInfoList.value.find((item)=> item.id == skuId)
}, {implements:true})

//최대 수량 판단 
const maxQty = computed(()=> {
    return skuInfo.value? skuInfo.value.quantity : 2
})
const addCart =async () => {
  // addCart 
    if (loginConfirm())return false
        // cart
        const cart:Cart = {
            id: '',
            userId: userStore.userInfo.id,
            skuId: skuInfo.value.id,
            qty: qty.value,
         }
        // addCart
        const result = await req_saveCart(cart)
        if(result.code == 200) {
            ElMessage({
                type: "success",
                message: '상품보관함에 등록했습니다.'
            })
             await cartStore.getCartTotal(userStore.userInfo.id)
        }
}
// 다이어로그 닫기
const handle = () => {
    isShowDialog.value = false
}

// 구매하기
const payment = () => {
    if (loginConfirm())return false
    isShowDialog.value = true
}
</script>

<style scoped>
.submint_btn {
    width: 45%;
	font: inherit;
	border: 1px solid #fbb710;
	background-color: transparent;
	color: #fbb710;
	padding: 0.5rem 1.5rem;
	cursor: pointer;
    text-align: center;
	
}
.submint_btn:hover,
.submint_btn:active {
    color:aliceblue;
	background-color: #fbb710;
}
.submint_btn.selected {
	background-color: #fbb710;
	color: white;
}
.price-old__amount {
  text-decoration: line-through;
  font-size: 14px;
  color:#606266;
  margin-right: 5px;
}
.discount {
  text-decoration: none;
  font-size: 11px;
  color: red;
  margin:0 5px;
}
</style>