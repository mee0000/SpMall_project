<template>
   <div class="main-content-wrapper d-flex clearfix">
            <!-- ##### MenuSide Area Start ##### -->
            <Menu :menuList="userStore.menuRoutes"/>
            <!-- ##### Main Area Start ##### -->
            <router-view></router-view>
   </div>
            <!-- ##### NewsLetter Area Start ##### -->
            <NewsLetter/>
            <!-- ##### Footer Area Start ##### -->
            <Footer/>
</template>

<script setup lang="ts">
import Footer from './footer/index.vue'
import Menu from './menu/index.vue'
import NewsLetter from './newsletter/index.vue'
import Main from './main/index.vue'
import useUserStore from '@/stores/modules/user'
import { onMounted } from 'vue'
// 경로
let userStore = useUserStore() //유저 스토어
onMounted(() => {
    getUserInfo()
})

// 유저정보 가져오기
const getUserInfo =async () => { 
    try {     
       await userStore.getUserInfo()
    } catch (error) {
        userStore.userLogout()
    }
}
</script>
<script lang="ts">
export default {
    name: "Layout"
}
</script>
<style lang="scss" scoped>

</style>