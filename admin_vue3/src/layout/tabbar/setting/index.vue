<template>
        <el-button size="small" icon="Refresh" @click="updateRefresh" circle></el-button>
          <el-button size="small" icon="FullScreen" @click="fullScreen" circle></el-button>
          <el-popover
              placement
              title="테마 설정"
              :width="300"
              trigger="hover">
              <el-from>
                  <el-form-item label="테마색상">
                        <el-color-picker v-model="color" size="small" show-alpha :predefine="predefineColors" />
                  </el-form-item>
                  <!--inline-prompt 스위치 내부에 아이콘이 들어감-->
                   <el-form-item label="다크모드">
                        <el-switch
                            @change="changeDark"
                            v-model="dark"
                            size="small"
                            active-icon="MoonNight"
                            inactive-icon="Sunny"
                          />
                  </el-form-item>
              </el-from>
              <template #reference>
                 <el-button size="small" icon="Setting" circle></el-button>
              </template>
            </el-popover>

          <img :src="userStore.avatar" alt="" style="width:24px; heigith:24px; margin: 0 10px; border-radius:50%;"/>
          <el-dropdown>
            <span class="el-dropdown-link">
            {{userStore.username}}
            <el-icon class="el-icon--right">
                <arrow-down />
            </el-icon>
            </span>
            <template #dropdown>
            <el-dropdown-menu >
                <el-dropdown-item @click="logout">Logout</el-dropdown-item>
            </el-dropdown-menu>
            </template>
          </el-dropdown>
</template>

<script setup lang="ts">
import useLayoutSettingStore from '@/store/modules/setting'
import useUserStore from '@/store/modules/user'
import { useRouter, useRoute } from 'vue-router'
import {ref} from 'vue'
let userStore = useUserStore()
let layoutSettingStore = useLayoutSettingStore()
let $router = useRouter()
let $route = useRoute()

const color = ref('rgba(255, 69, 0, 0.68)')
const dark = ref(true)
const predefineColors = ref([
  '#ff4500',
  '#ff8c00',
  '#ffd700',
  '#90ee90',
  '#00ced1',
  '#1e90ff',
  '#c71585',
  'rgba(255, 69, 0, 0.68)',
  'rgb(255, 120, 0)',
  'hsv(51, 100, 98)',
  'hsva(120, 40, 94, 0.5)',
  'hsl(181, 100%, 37%)',
  'hsla(209, 100%, 56%, 0.73)',
  '#c7158577',
])
// 새로고침 함수
const updateRefresh = () => {
  layoutSettingStore.refresh = !layoutSettingStore.refresh
}
// 전체화면 
const fullScreen = () => {
   // document.fullscreenElment --> Dom환경의 속성: 전체화면인지 확인 가능 (true, false   반환)
   let full = document.fullscreenElement
   console.log('------', full)
   if (!full) {
      // 전체화면으로 변경
      document.documentElement.requestFullscreen()
   } else {
      // 전체화면 취소
      document.exitFullscreen()
   }

}
// logout
const logout = () => {
  console.log('로그아웃')
  // 1: server에 logout 요청
  // 2: userStore에 관련 데이터 삭제 [token| usernae| avatar]
  // 3: router 주소 변경
  userStore.userLogout()
  $router.push({path: '/login', query:{redirect: $route.path}})
}
// 다크모드 체인지
const changeDark = () => {
  // 전체 html 에 class 추가
  let html = document.documentElement
  dark.value ? html.className = 'dark' : html.className = ''
}
</script>
<script lang="ts">
export default {
    name: "Setting"
}
</script>
<style lang="scss" scoped>

</style>