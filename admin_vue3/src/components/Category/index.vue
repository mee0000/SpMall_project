<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item label="1level category">
          <el-select placeholder="1급 카테고리 선택하세요" v-model="categoryStore.category1Id" @change="handle" :disabled="scene != 0">
            <el-option v-for="item in categoryStore.c1List" :key="item.id" :label="item.categoryName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="2level category">
          <el-select placeholder="2급 카테고리 선택하세요" v-model="categoryStore.category2Id" :disabled="scene != 0">
            <el-option v-for="item in categoryStore.c2List" :key="item.id" :label="item.categoryName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">

import { onMounted } from 'vue'
import useCategoryStore from '@/store/modules/category'
// scene
defineProps(['scene'])

// 카테고리 스토어
let categoryStore = useCategoryStore()
// 카테고리 1 가져오기 
onMounted(()=> {
  getC1()
})

// 창고에서 카태고리리스트 가져오기
const getC1 = ()=> {
  categoryStore.getC1()
}
// 카테고리1 값 여부를 확인 하여 카테고리 2를 불러오기
const handle = () => {
  categoryStore.getC2()
  categoryStore.c2List = []
  categoryStore.category2Id = ''
}
</script>

<style scoped></style>
