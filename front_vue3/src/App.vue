<template>
  <div>
    <router-view/>
  </div>
</template>

<script setup lang="ts">
import { provide, nextTick, ref } from 'vue'
const isRouterActive = ref (true)
provide('reload', () => {
  isRouterActive.value = false
  nextTick(()=> {
    isRouterActive.value = true
  })
})
</script>

<style scoped>

</style>
