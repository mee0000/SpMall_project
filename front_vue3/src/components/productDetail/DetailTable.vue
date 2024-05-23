<template>
        <thead>
            <tr>
                <th scope="col">SkuName</th>
                <!--SaleAttrName -->
                <th scope="col" v-show="spuSaleAttrList" v-for="saleAttr in spuSaleAttrList">{{saleAttr.baseAttrName}}</th>
                <!-- <th scope="col">First</th> -->
            </tr>
        </thead>
        <tbody>
            <tr v-for="(sku, index) in skuInfoList" :key="sku.id">
                <th scope="row">{{sku.skuName}}</th>
                <!-- attrSaleValueName -->
                <td v-if="sku.skuSaleAttrValueList" v-for="saleAttr in sku.skuSaleAttrValueList">
                    {{saleAttr.saleAttrValueName}}</td>
            </tr>
        </tbody>
</template>

<script setup lang="ts">
import {defineProps} from 'vue'
defineProps(['skuInfoList', 'spuSaleAttrList'])

</script>

<style scoped>

</style>