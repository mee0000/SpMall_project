<template>
        <template v-for="item in menuList" :key="item.path">
            <!-- ㅁㅔ뉴 1개일 경우 -->
            <template v-if="!item.children">
                <el-menu-item v-if="!item.meta.hidden" :index="item.path" @click="goRoute">
                    <el-icon>
                        <component :is="item.meta.icon"></component>
                    </el-icon>
                    <template #title>
                        <span> {{ item.meta.title}} </span>
                    </template>
                </el-menu-item>
            </template>
            <!-- 하위메뉴 1개만 있을경우 -->
             <template v-if="item.children && item.children.length == 1">       
                <el-menu-item v-if="!item.children[0].meta.hidden" :index="item.children[0].path" @click="goRoute">
                    <el-icon>
                       <component :is="item.children[0].meta.icon"></component>
                    </el-icon>
                    <template #title>
                        <span> {{ item.children[0].meta.title}} </span>
                    </template>
                </el-menu-item>
            </template>
            <!-- 하위메뉴 2개이상 vue3 + vue2 递归组建  -->
             <template v-if="item.children && item.children.length > 1">   
                <el-sub-menu v-if="!item.meta.hidden" :index="item.path">
                    <template #title>
                         <el-icon>
                            <component :is="item.meta.icon"></component>
                        </el-icon>
                        <span> {{ item.meta.title}} </span>
                    </template>
                    <Menu :menuList="item.children"></Menu>
                </el-sub-menu>
            </template>
        </template> 
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
defineProps(['menuList'])
// router 획득
let $router = useRouter()
// 부모한테서 받은 메뉴 리스트 
// 메뉴 클리시 발생하는 함수 
const goRoute = (vc: any) => {
   $router.push(vc.index)
}
</script>
<script lang="ts">
export default {
    name: 'Menu'
}
</script>

<style scoped>

</style>