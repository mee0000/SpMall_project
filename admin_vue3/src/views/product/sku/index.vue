<template>
    <Category :scene="scene"></Category>
      <el-card style="margin-top: 10px;" v-show="scene == 0">
        <Search :searchParams="searchParms" @update:searchParams="search"></Search>
        <el-table :data="skuList" style="width: 100%" border>
        <el-table-column label="No" type="index" align="center" width="80px"/>
        <el-table-column label="img" width="120">
                <template #="{ row }">
                <img :src="row.skuDefaultImg" style="width:100px; height:100px">
                </template>
        </el-table-column>
        <el-table-column prop="createTime" label="등록일" width="120" type="date" />
        <el-table-column prop="skuName" label="Name" width="120" />
        <el-table-column prop="price" label="Pirce" width="120" />
        <el-table-column prop="discount" label="Discount" width="120" />
        <el-table-column prop="decoration" label="Decoration" show-overflow-tooltip width="120px"/>
        <el-table-column prop="quantity" label="Qty" width="120" />
        <el-table-column fixed="right" label="Operations" width="250">
          <template #="{ row }">
            <el-button :type="row.status == 1?'primary':'info'" size="small" :icon="row.status == 1?'Top':'Bottom'" @click="changeStatus(row)"></el-button>
            <el-button type="warning" size="small" icon="InfoFilled" @click="showDrawer(row)"></el-button>
            <el-button type="success" size="small" icon="Edit" @click="editSku(row)"></el-button>
             <el-popconfirm :title="`${row.skuName}을/를 삭제하겠습니까?`" @confirm="confirmRemove(row)">
                    <template #reference>
                        <el-button  type="danger" plain size="small" icon="Delete"></el-button>
                    </template>
                </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
         <el-pagination
            v-model:current-page="page"
            v-model:page-size="pageSize"
            layout=" prev, pager, next,jumper,->,sizes, total"
            :total="total"
            :page-sizes="[3,5,7,10]"
            @size-change="getSkuList(pageSize)"
            @current-change="getSkuList"
        />
    </el-card>
    <!--    sku Edit  start   -->
     <div v-show="scene == 2">
      <el-card style="margin-top: 20px;">
        <SkuForm @changeScene="changeScene" ref="skuForm"></SkuForm>
      </el-card>
    </div>
     <!--    sku Edit  end   -->
     <!--    sku Detail start -->
    <Drawer :drawer="skuInfo" @update="changeDrawer"></Drawer>
     <!--    sku Detail end -->
</template>

<script setup lang="ts">
import { watch, ref, onBeforeUnmount } from 'vue'
import type{skuInfoData} from '@/api/product/sku/type'
import { req_getAllSkuInfo, req_changeSkuStatus, req_deleteSkuById } from '@/api/product/sku'
import Search from '@/components/Search/index.vue'
import Drawer from '@/components/SkuList/Drawer.vue'
import useCategoryStore from '@/store/modules/category'
import { ElMessage } from 'element-plus'
let skuList = ref<any>([])
let scene = ref(0)
let page=ref<number>(1)
let pageSize = ref<number>(10)
let total = ref<number>(3)
let skuForm = ref()
let categoryStore = useCategoryStore()
let skuInfo = ref({
      id: '',
      spuId: '',
      price: '',
      quantity: '',
      skuName: '',
      category2Id: '',
      skuDefaultImg : '',
      discount : '',
      decoration: '',
      skuAttrValueList : [],
      skuSaleAttrValueList: []
})
let searchParms = ref<any>({
  keyword: '',
  min: '',
  max: '',
  category2Id: ''
})
// skuInfoList 가져오기
watch(() => categoryStore.category2Id, () => {
    // 카테고리 2 값이 없으면 리스트 지우기
  if(categoryStore.category2Id == '') {
      skuList.value = []
        return
      } 
  searchParms.value = {
      keyword: '',
      min: '',
      max: '',
      category2Id: ''
  }
  getSkuList()
})
const changeScene = (obj: any) => {
    console.log('changeScene')
    scene.value = obj.flag
    if(obj.params == 'update' || obj.params == 'cancel') {
        getSkuList(page.value)
    } else {
        getSkuList()
    }
}
const getSkuList = async() => {
  searchParms.value.category2Id = categoryStore.category2Id
  const result:skuInfoData = await req_getAllSkuInfo(page.value, pageSize.value, searchParms.value)
  if(result.code == 200) {
    skuList.value = result.data.rows
    total.value = result.data.total
  }
}

// sku 편집
const editSku = (row) => {
    scene.value = 2
        const { category1Id, category2Id} = categoryStore
    skuForm.value.initSku(category1Id, category2Id, row, 'editSku')
}

const search = (params) => {
  Object.assign(searchParms.value, params)
  console.log(searchParms.value, '---------')
  getSkuList()
}
const showDrawer = (row) => {
  //해당 데이터 전송
  // 해당 saleAttr and Attr가져오기
  
  // 해당 Attr 가져오기 
   skuInfo.value = {
    ...row,
    isShow: true
  }
}
// 상품 올림 status =1 상품내림 status = 2
const changeStatus =async (row) => {
  const s = row.status == 1 ? 2: 1
  const data = {
    ...row,
    status: s
  }
  const result = await req_changeSkuStatus(data)
  if(result.code == 200) {
    getSkuList()
  }
}
// sku 삭제하기
const confirmRemove = async(row) => {
  const result = await req_deleteSkuById(row.id)
  if(result.code == 200) {
    // 성공시 
    ElMessage({
        type:'success',
        message: 'SKU 삭제 완료'
      })
      getSkuList()
    } else {
        ElMessage({
            type:'error',
            message: 'SKU 삭제 실패'
        })
    }
}

const changeDrawer = () => {
  skuInfo.value.isShow = false
}
onBeforeUnmount(()=> {
  categoryStore.$reset()
})
</script>

<style scoped>

</style>