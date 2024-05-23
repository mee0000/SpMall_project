<template>
    <div class="layout_container">
      <!-- 좌측메뉴 -->
      <div class="layout_sider" :class="{fold:layoutSettingStore.fold}">
        <Logo></Logo>
        <!-- 메뉴 -->
        <el-scrollbar class="scrollbar" :class="{fold:layoutSettingStore.fold}">
            <!-- default-active => 1급 메뉴 경로로 판단 -->
            <el-menu :default-active="$route.path" background-color="#001529" text-color="white" :collapse="layoutSettingStore.fold">
               <!-- 동적데이터로 메뉴 생성 -->
               <Menu :menuList="useStore.menuRoutes"></Menu>
            </el-menu>
        </el-scrollbar>
      </div>
      <!-- 상단네비게이션-->
      <div class="layout_tabbar" :class="{fold:layoutSettingStore.fold}">
        <!-- layout 상단 tabbar-->
         <Tabbar></Tabbar>
      </div>
      <!-- 메인전시구역 -->
      <div class="layout_main" :class="{fold:layoutSettingStore.fold}">
         <Main></Main>
      </div>
    </div>
</template>

<script setup lang="ts">
import Logo from './logo/index.vue'
import Menu from './menu/index.vue'
import Main from './main/index.vue'
import Tabbar from './tabbar/index.vue'
// 유저관련 메뉴 창고 가져오기
import useUserStore from '@/store/modules/user'
// router path경로 획득 
import { useRoute } from 'vue-router'
// flad 
import useLayoutSettingStore from '@/store/modules/setting'
let useStore = useUserStore()
// 경로 대상 
let $route = useRoute()

let layoutSettingStore = useLayoutSettingStore()

</script>
<script lang="ts">
export default {
    name: "Layout"
}
</script>
<style lang="scss" scoped>
.layout_container{
    width: 100%;
    height: 100vh;
    background-color: white;
    .layout_sider {
        width: $base-menu-width;
        height: 100vh;
        background-color: $base-menu-background;
        color: white;
        transition:all .3s;
        .scrollbar {
            width: 100%;
            height: calc(100vh - $base-menu-logo-height);
        }
        &.fold {
            width: $base-menu-min-width;
        }
    }
    .layout_tabbar {
        position: fixed;
        width: calc(100% - $base-menu-width);
        height: $base-tabbar-height;
        top: 0px;
        left: $base-menu-width;
        transition:all .3s;
        &.fold {
          width:calc(100% - $base-menu-min-width) ;
          left: $base-menu-min-width;
        }
    }
    .layout_main {
        transition:all .3s;
        position: absolute;
        width: calc(100% - $base-menu-width);  
        height: calc(100% - $base-tabbar-height);
        left: $base-menu-width;
        top: $base-tabbar-height;
        padding: 20px;
        &.fold {
          width:calc(100% - $base-menu-min-width) ;
          left: $base-menu-min-width;
        }
    }
   
    
}
</style>