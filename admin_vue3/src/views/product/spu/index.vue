<template>
    <Category :scene="scene"></Category>
      <el-card style="margin-top: 10px">
       <div v-show="scene == 0">
        <el-button
            type="primary"
            size="default"
            icon="Plus"
            @click="addSPU"
            :disabled="!categoryStore.category2Id"
        >
            SPU 추가
        </el-button>
        <el-table style="margin: 10px 0" :data="spuList">
            <el-table-column label="NO" type="index" align="center" width="80px" ></el-table-column>
            <el-table-column
                label="SPUName"
                align="center"
                width="150px"
                prop="spuName"
            ></el-table-column>
            <el-table-column label="SPUDecoration" align="center" prop="description" ></el-table-column>
            <el-table-column label="비고" align="center" width="250px">
            <template #="{ row, $index }">
                <el-button
                type="primary"
                plain
                size="small"
                icon="Plus"
                @click="addSku(row)"
                ></el-button>
                <el-button
                type="primary"
                plain
                size="small"
                icon="Edit"
                @click="editSpu(row)"
                ></el-button>
                 <el-button
                type="primary"
                plain
                size="small"
                icon="View"
                @click="showSkuList(row)"
                ></el-button>
                <el-popconfirm title="`을/를 삭제하겠습니까?`" @confirm="confirmRemove(row)">
                    <template #reference>
                        <el-button  type="danger" plain size="small" icon="Delete"></el-button>
                    </template>
                </el-popconfirm>
            </template>
            </el-table-column>
        </el-table>
        <!--네비게이션 -->
        <el-pagination
            v-model:current-page="page"
            v-model:page-size="pageSize"
            layout=" prev, pager, next,jumper,->,sizes, total"
            :total="total"
            :page-sizes="[3,5,7,10]"
            @size-change="getSpuList"
            @current-change="getSpuList(page)"
        />
    </div>
    <div v-show="scene == 1">
       <spu-form @changeScene="changeScene" ref="spuForm"></spu-form>
    </div>
     <div v-show="scene == 2">
       <SkuForm @changeScene="changeScene" ref="skuForm"></SkuForm>
    </div>
     <el-dialog :title="`${spuName}的sku列表`" v-model="dialogTableVisible" :before-close="close">
           <!-- table展示sku列表-->
            <sku-list :skuList="skuList"></sku-list> 
      </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import SkuList from '@/components/SkuList/index.vue'
import SpuForm from './SpuForm.vue'
import { ref, watch, onBeforeUnmount } from 'vue'
import useCategoryStore from '@/store/modules/category'
import {req_spu, req_removeSpuById } from '@/api/product/spu'
import {req_getSkuInfoBySpuId} from '@/api/product/sku'
import type {skuInfoData} from '@/api/product/sku/type'
import { ElMessage } from 'element-plus'
// 현재페이지(current-page)
let page = ref<number>(1)
// 한페이지 노출될 개수 (page-size)
let pageSize = ref<number>(3)
let category2Id = ref<any>()
// 토탈페이지
let total = ref<number>(0)
let categoryStore = useCategoryStore()
// spuList
let spuList = ref<any>([])
// 화면 전환용
let scene = ref(0)
// skuForm
let skuForm = ref()
let spuForm = ref()
let spuName = ref('')
let skuList = ref<any>([])
let dialogTableVisible = ref<boolean>(false)
watch(() => categoryStore.category2Id, () => {
    // 카테고리 2 값이 없으면 리스트 지우기
  if(categoryStore.category2Id == '') {
      spuList.value = []
      return
      } 
  getSpuList()
})
// spuList 가져오기
const getSpuList = async(pager = 1) => {
  page.value = pager
  const { data } = await req_spu(categoryStore.category2Id, page.value, pageSize.value)
  spuList.value = data.rows
  total.value = data.total
}
// sku 추가하기
const addSku = (row) => {
    scene.value = 2
        const { category1Id, category2Id} = categoryStore
    skuForm.value.initSku(category1Id, category2Id, row, 'addSku')
}
// spu 추가하기
const addSPU = () => {
    scene.value = 1
    const { category1Id, category2Id} = categoryStore
    spuForm.value.addSPU(category1Id, category2Id)
}
// edit spu
const editSpu = (row) => {
    scene.value = 1
    spuForm.value.initSpu(row)
}
// show 
const showSkuList = async(row) => {
    spuName.value = row.spuName
    dialogTableVisible.value = true
    const result:skuInfoData = await req_getSkuInfoBySpuId(row.id)
    console.log(result)
    skuList.value = result.data
}
const changeScene = (obj: any) => {
    scene.value = obj.flag
    if(obj.params == 'update' || obj.params == 'cancel') {
        getSpuList(page.value)
    } else {
        getSpuList()
    }
}
const confirmRemove = async (row) => {
   let spuId = row.id
   const result = await req_removeSpuById(spuId)
   if(result.code == 200) {
      ElMessage({
        type:'success',
        message: '삭제완료',
      })
        getSpuList()
   }else {
      ElMessage({
        type:'error',
        message: '삭제실패'
    })
   }
}
onBeforeUnmount(() => {
  categoryStore.$reset()
})
</script>

<style scoped>

</style>