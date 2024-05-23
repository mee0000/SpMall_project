<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
onMounted (() => { 
  
})
   
</script>

<style scoped lang="scss">
  div {
    h1 {
      color: $color
    }
  }
</style>
