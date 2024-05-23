<template>
  <div class="shop_sidebar_area">
    <!-- ##### Single Widget ##### -->
    <div class="widget catagory mb-50">
      <!-- Widget Title -->
      <h6 class="widget-title mb-30">Catagories</h6>

      <!--  Catagories  -->
      <div class="catagories-menu">
        <button id="btn" @click="getAllList">all</button>
        <el-tree
          style="max-width: 600px; background: none"
          :data="categoryStore.c1List"
          :props="defaultProps"
          @node-click="handleNodeClick"
        > 
          <template #default="{ node, data }">
            <div :class="{ 'selected-node': data === selectedNode }">
              {{ node.label }}
            </div>
          </template>
        </el-tree>
      </div>
    </div>
    <!-- productAttr Area start -->
     <template v-if="baseAttrAndValue"  v-for="attr in baseAttrAndValue" :key="attr.id">
        <div class="widget brands mb-50" v-if="attr.attrName != 'color' && attr.attrValueList.length > 0">
          <!-- Widget Title -->
          <h6 class="widget-title mb-30">{{attr.attrName}}</h6>
          <div class="widget-desc" >
            <!-- Single Form Check -->
            <div class="form-check" v-for="attrValue in attr.attrValueList" :key="attrValue.id">
               
              <base-badge>{{attrValue.valueName}}</base-badge>
            </div>
          </div>
      </div>
 
    <!-- ##### Single Widget ##### -->
    </template>
  </div>
</template>

<script setup lang="ts">
import useCategoryStore from "@/stores/modules/category"
import type { Category } from "@/api/category/type"
import { onMounted, reactive, ref, watch, defineProps, defineEmits } from "vue"

let selectedNode = ref(null)
let categoryStore = useCategoryStore()
let category1Id = ref("")
let category2Id = ref("")
let $emit = defineEmits(['allList'])
defineProps(['baseAttrAndValue'])
onMounted(() => {
  getC1()
})

const getC1 = () => {
  categoryStore.getC1()
}

const defaultProps = {
  children: "category2List",
  label: "categoryName",
}

const handleNodeClick = async (data: any) => {
  selectedNode.value = data
  console.log(data, "==========")
  if (!data.level) {
    category2Id.value = data.id
    categoryStore.category1Id = data.category1Id
  } else {
    category2Id.value = ""
  }

  categoryStore.category2Id = category2Id.value
}

const getAllList= () => {
  selectedNode.value = null
  $emit('allList')
}
</script>

<style scoped>
.selected-node {
  color: #fbb710;
  font-weight: bold;
}
#btn {
display: inline-block;
  width:156px;
  text-align: center;
  font-size: 14px;
  padding: 0 25px;
  color:#ffffff;
  border:none;
  background-color: #b3b6bc;
}
#btn:hover,
#btn:active {
  border:none;
  outline:none !important;
  box-shadow:none !important;
  border-radius: 0;
  background: #fbb710;
}
#btn:focus {
  outline:none !important;
  box-shadow:none !important;
  border-radius: 0;
  background: #fbb710;
}

</style>
