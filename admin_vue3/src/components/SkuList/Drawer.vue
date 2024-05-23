<template>
  <el-drawer :model-value="drawer.isShow"  :direction="direction" :before-close="handleClose">
    <template #header>
      <h4>상품 SKU 상세페이지</h4>
    </template>
    <template #default>
      <el-row >
        <el-col :span="6">skuName</el-col>
        <el-col :span="18">{{drawer.skuName}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="6">설명</el-col>
        <el-col :span="18">{{drawer.decoration}}</el-col>
      </el-row>
       <el-row>
        <el-col :span="6">가격</el-col>
        <el-col :span="18">{{drawer.price}}</el-col>
      </el-row>
       <el-row>
        <el-col :span="6">상품속성</el-col>
        <el-col :span="18">
            <template #default>
               <el-tag v-for="item in drawer.skuAttrValueList" :key="item">
                   {{item.valueName}}
               </el-tag>
            </template>
        </el-col>
      </el-row>
       <el-row>
        <el-col :span="6">세일 속성</el-col>
        <el-col :span="18">
            <template #default>
               <el-tag v-for="item in drawer.skuSaleAttrValueList" :key="item.id" style="margin-right:5px;">
                    {{item.saleAttrValueName}}
               </el-tag>
            </template>
        </el-col>
      </el-row>
     <!-- skuAttrValueList : [],
      skuSaleAttrValueList: [] -->
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const direction = ref('rtl')
defineProps(['drawer'])
let $emit = defineEmits(['update'])

const handleClose = () => {
  $emit('update')
}
</script>

<style scoped>

</style>