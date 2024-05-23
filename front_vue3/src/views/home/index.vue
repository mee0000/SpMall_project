<template>
    <!-- Product Catagories Area Start -->
        <div class="products-catagories-area clearfix">
            <div class="amado-pro-catagory clearfix">

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix" v-for="item in bannerList">
                    <base-button link :to="{path:'/shop'}">
                        <img :src="item.imgUrl" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>{{item.price}}</p>
                            <h4>{{item.productName}}</h4>
                        </div>
                    </base-button>
                </div>
            </div>
        </div>
        <!-- Product Catagories Area End -->    
</template>

<script setup lang="ts">
import {reqBannerList} from '@/api/home'
import { onMounted, reactive } from 'vue'

onMounted(()=> {
    getList()
})

let bannerList = reactive([])

const getList = async ()=> {
    const {data} = await reqBannerList()
    Object.assign(bannerList, data)
}
</script>

<style scoped>
.single-products-catagory {
  display: inline-block;
}
</style>