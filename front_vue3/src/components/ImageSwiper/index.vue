<template>
   <swiper
    :loop= "true"
    :slides-per-view="1"
    :space-between="50"
    :modules = "modules"
    @swiper="onSwiper"
    :autoplay="{delay:1500, disableOnInteracion: false, stopOnLastSlide: false}"
    @slideChange="onSlideChange"
  >
    <swiper-slide class="item" v-for="item in skuImageList">
      <img :src="item.imgUrl"/>
    </swiper-slide>
  </swiper>
</template>
  
<script setup lang="ts">
import { Grid, Autoplay} from 'swiper/modules'
import {Swiper, SwiperSlide} from 'swiper/vue'
import { defineProps } from 'vue';
defineProps(['skuImageList'])
const modules = [Grid, Autoplay]
 const onSwiper = (swiper) => {
        console.log(swiper)
      };
 const onSlideChange = () => {
        console.log('slide change')
      }
</script>
  
<style lang="less" scoped>
  .swiper {
     width: 100%;
     max-height: 580px;
     .item img{
       padding:8px 16px;
     }
  }
  </style>