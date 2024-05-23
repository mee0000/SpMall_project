<template>
  <div class="single-product-area section-padding-100 clearfix">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div
            class="product-topbar d-xl-flex align-items-end justify-content-between"
          >
            <!-- Total Products -->
            <!-- Sorting -->
            <div class="product-sorting d-flex">
              <div class="sort-by-date d-flex align-items-center">
                  <div class="input-group mb-3">
                  <input
                    type="text"
                    class="form-control"
                    placeholder="검색어 입력하세요"
                    aria-label="Recipient's username"
                    aria-describedby="button-addon2"
                    v-model="searchParams.keyword"
                  />
                  <button
                    class="btn btn-outline-secondary"
                    type="button"
                    id="button-addon2"
                    @click="search"
                  >
                    Search
                  </button>
          
                  <label class="input-group-text" for="inputGroupSelect01"
                    >Options</label
                  >
                  <select class="form-select" id="inputGroupSelect01" v-model="searchParams.sort">
                    <option value="0" selected>Choose...</option>
                    <option value="1">낮은가격</option>
                    <option value="2">높은가격</option>
                    <option value="3">할인율</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-12 col-sm-3" v-for="item in products" :key="item.id">
          <ProductItem :sku="item" />
        </div>
        <!-- Single Product Area -->
      </div>
      <!-- PageNation Area -->
      <PageNation :pageNo="pageNo" :pageSize="pageSize" :total="pageTotal" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ProductItem from "@/components/products/ProductItem.vue"
import { req_getSaleProducts } from "@/api/product/sku/index"
import { onMounted, reactive, ref, watch} from "vue"

let pageNo = ref<number>(1)
let pageSize = ref<number>(20)
let searchParams = reactive({
  keyword: '',
  dateMin: '',
  dateMax: '',
  category2Id: '',
  sort: 0
})

let products = ref({})
let pageTotal = ref<number>(0)
onMounted(() => {
  getList()
})

watch([() => searchParams.sort, ()=> searchParams.keyword], ([newSort,newKeyword])=> {
  if(newSort != '' || newKeyword == '') {
    getList()
  }
}, {deep:true})

const getList = async () => {
  console.log('.....searchparam', searchParams)
  const {data: { rows, total }} = await req_getSaleProducts(pageNo.value, pageSize.value, searchParams)
  products.value = rows
  pageTotal.value = total
}

const search = () => {
    getList()
}
</script>

<style scoped></style>
