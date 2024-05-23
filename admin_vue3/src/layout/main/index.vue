<template>
   <router-view v-slot="{Component}">
      <transition name="fade" >
        <!-- 1급 카테고리의 하위메뉴 -->
        <component :is="Component" v-if="flag"/>
      </transition>
   </router-view>
</template>

<script setup lang="ts">
import useLayoutSettingStore from '@/store/modules/setting'
import { watch, nextTick, ref } from 'vue'
let layoutSettingStore = useLayoutSettingStore()
// 현재 컴폰넌트를 다시 가져오는지 판단 
let flag = ref(true)
// layoutSettingStore.refresh 값 변경했는지 확인 
watch(() => layoutSettingStore.refresh, ()=> {
    flag.value = false
    nextTick(() => {
    flag.value = true
  })
})
</script>
<script lang="ts">
export default {
    name: "Main"
}
</script>
<style scoped>
.fade-enter-from {
  opacity: 0;
}
.fade-enter-active {
  transition: all 1s;
}
.fade-enter-to {
  opacity:1;
}
</style>