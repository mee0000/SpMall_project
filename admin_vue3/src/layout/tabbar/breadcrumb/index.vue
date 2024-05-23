<template>
        <el-icon style="margin-right:10px;" @click="changeIcon">
           <component :is="layoutSettingStore.fold ? 'Expand': 'Fold'"></component>
        </el-icon>
        <!--面包屑 -->
        <el-breadcrumb separator-icon="ArrowRight">
            <el-breadcrumb-item v-for="(item, index) in $route.matched" :key="index" v-show="item.meta.title" :to="item.path">
                <el-icon ><component :is="item.meta.icon"/></el-icon>
                <span>{{ item.meta.title }}</span>
            </el-breadcrumb-item>
        </el-breadcrumb>
   
</template>

<script setup lang="ts">
import useLayoutSettingStore from '@/store/modules/setting'
import { useRoute } from 'vue-router'
// icon 값 확인
let layoutSettingStore = useLayoutSettingStore()
let $route = useRoute()
const changeIcon = () => {
  layoutSettingStore.fold = !layoutSettingStore.fold
}
</script>
<script lang="ts">
export default {
    name: "BreadCrumb"
}
</script>
<style lang="scss" scoped>

</style>