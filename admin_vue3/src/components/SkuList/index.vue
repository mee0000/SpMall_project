<template>
    <el-table
        :data="skuList"
        style="width: 100%"
    >
    <el-table-column prop="skuName" label="SkuName" width="180" />
    <el-table-column prop="price" label="SkuPrice" width="180" />
    <el-table-column prop="quantity" label="수량(EA)" />
    <el-table-column label="Image">
        <template #="{row}">
           <img :src="row.skuDefaultImg" style="whidth:100px; height:100px;">
        </template>
    </el-table-column>
  </el-table>
</template>
<script setup lang="ts">
defineProps(['skuList'])
</script>

<style scoped>

</style>