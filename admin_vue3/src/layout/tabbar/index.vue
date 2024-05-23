<template>
    <div class="tabbar">
       <div class="tabbar_left">
          <!-- 상측 좌변 메뉴 -->
          <Breadcrumb></Breadcrumb>
       </div>
       <div class="tabbar_right">
          <Setting></Setting>
       </div>
    </div>
</template>

<script setup lang="ts">
import Breadcrumb from './breadcrumb/index.vue'
import Setting from './setting/index.vue'
</script>
<script lang="ts">
export default {
    name: "Tabbar"
}
</script>
<style scoped lang="scss">
.tabbar {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content:space-between;

    .tabbar_left {
      display: flex;
      align-items: center;
      margin: 20px;
    }
    .tabbar_right {
        display: flex;
        align-items: center;
    }
}
</style>