<template>
       <!--sidbar-->
       <ProductFilter :baseAttrAndValue="baseAttrAndValue" @all-list="resetList()"/>
        <div class="amado_product_area section-padding-100">
            <div class="container-fluid" v-if="productStore.skuList.length > 0">
                <!-- Single Product Area -->
                <div class="row">
                    <div class="col-12 col-sm-6 col-md-12 col-xl-6" v-for="item in productStore.skuList" :key="item.id">
                        <ProductItem :sku = "item"/>
                    </div>
                  </div>
                <!-- PageNation Area -->
                <PageNation :pageNo="pageNo" :total="productStore.total" :pageSize="pageSize" @setPageNo="setPageNo"/>
            </div>
            <div class="container-fluid" v-else>
                       없음
                </div>  
        </div>
</template>
<script setup lang="ts">
import ProductItem from '@/components/products/ProductItem.vue'
import ProductFilter from '@/components/products/ProductFilter.vue'
import useProductStore from '@/stores/modules/product'
import useCategoryStore from '@/stores/modules/category'
import { req_getBaseAttrAndValue } from "@/api/product/attr"
import { onMounted, ref, reactive, watch } from 'vue'

let pageNo = ref(1) // 기본 현재 페이지 
let pageSize = ref(8) //페이지 사이즈( 노출)
let baseAttrAndValue = ref([]) // 상품기본 속성
let searchParams = reactive({ // 검색 키워드
    category1Id: '',
    category2Id: '',
    keyword: '',
    order: '1:desc'
    // SaleAttr
})
let isLoading = ref<boolean>(false)
let categoryStore = useCategoryStore() // 카테고리
let productStore = useProductStore() // 상품 스토어

onMounted(()=> {
    getSkuList()
})

watch(
  () => categoryStore.category2Id,
  (newValue, oldValue) => {
    if (newValue != oldValue && newValue != "") {
      searchParams.category2Id = newValue
      getSkuList()
      getBaseAttr()
    }
  }
)

const getBaseAttr = async () => {
  const result = await req_getBaseAttrAndValue(
    categoryStore.category1Id,
    categoryStore.category2Id
  )
  baseAttrAndValue.value = result.data.length > 0 ? result.data : [] 
}
const getSkuList =async (page = 1)=> {
  isLoading.value = true
  pageNo.value = page
  await productStore.getSkuList(pageNo.value, pageSize.value,searchParams)
}

const setPageNo = (page:number) => {
  getSkuList(page)
}

const resetList = () => {
    pageNo.value = 1
    Object.assign(searchParams, {
        category1Id: '',
        category2Id: '',
        keyword: '',
        order: '1:desc'
        // SaleAttr
    })
    getSkuList()
}
</script>

<style scoped>

</style>