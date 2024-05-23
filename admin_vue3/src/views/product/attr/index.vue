<template>
  <Category :scene="scene"></Category>
  <el-card style="margin-top: 10px">
    <div v-show="scene == 0">
      <el-button
        type="primary"
        size="default"
        icon="Plus"
        :disabled="categoryStore.category2Id ? false : true"
        @click="addAndUpdate"
      >
        속성 추가
      </el-button>
      <el-table style="margin: 10px 0" :data="attrInfo" border>
        <el-table-column
          label="NO"
          type="index"
          align="center"
          width="80px"
        ></el-table-column>
        <el-table-column
          label="AttrName"
          align="center"
          width="150px"
          prop="attrName"
        ></el-table-column>
        <el-table-column label="AttrValueName" align="center">
          <template #="{ row, $index }">
            <el-tag
              v-for="item in row.attrValueList"
              :key="item.id"
              style="margin: 0px 5px"
            >
              {{ item.valueName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="비고" align="center" width="150px">
          <template #="{ row, $index }">
            <el-button
              type="primary"
              plain
              size="small"
              @click="addAndUpdate(row)"
              icon="Edit"
            ></el-button>
            <el-popconfirm :title="`${row.attrName} 을/를 삭제하겠습니까?`" @confirm="deleteAttr(row)">
              <template #reference>
                <el-button  type="danger" plain size="small" icon="Delete"></el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-show="scene == 1">
      <el-form inline="true">
        <el-form-item label="속성 네임">
          <el-input
            placeholder="Please Input"
            size="large"
            v-model="attr.attrName"
          />
        </el-form-item>
      </el-form>
      <el-button
        type="primary"
        :disabled="attr.attrName ? false : true"
        @click="addAttrValue"
      >
        속성값 추가
      </el-button>
      <el-button>취소</el-button>
      <el-table border :data="attr.attrValueList" style="margin: 10px 0">
        <el-table-column
          type="index"
          label="No번호"
          width="80"
          align="center"
        />
        <el-table-column label="AttrName">
          <template #="{ row, $index }">
            <el-input
              v-if="row.flag"
              v-model="row.valueName"
              @blur="writeValueName(row, $index)"
              :ref="(vc) => (inputArr[$index] = vc)"
            />
            <div v-else @click="editValueName(row, $index)">
              {{ row.valueName }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="Address" align="center" width="120">
            <template  #="{ row, $index }">
                <el-button
                  type="danger"
                  plain
                  size="small"
                  icon="Delete"
                  @click="deleteAttrValue($index)"            
              ></el-button>
            </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" @click="saveAttr">저장</el-button>
      <el-button @click="cancel">취소</el-button>
    </div>
  
  </el-card>
</template>

<script setup lang="ts">
import type { attr, attrResponseData} from '@/api/product/attr/type'
import useCategoryStore from '@/store/modules/category'
import { req_attrInfo, req_saveAttr, req_removeAttr } from '@/api/product/attr'
import { watch, ref, reactive, nextTick, onBeforeUnmount } from 'vue'
import { ElMessage } from 'element-plus'
//
let scene = ref<number>(0)
//attrInfo
//
let inputArr = ref([])
let attr = reactive<attr>({
  attrName: '',
  id: '',
  categoryId: '',
  categoryLevel: 2,
  attrValueList: [],
})
//scene ====0
let categoryStore = useCategoryStore()
let attrInfo = ref<attr>([])
watch(
  () => categoryStore.category2Id,
  () => {
    if (categoryStore.category2Id == '') return
    attrInfo.value = []
    getAttrInfo()
  }
)
const getAttrInfo = async () => {
  const { category1Id, category2Id } = categoryStore
  const { data }: attr = await req_attrInfo(category1Id, category2Id)
  attrInfo.value = data
}

// scen =======1
const addAndUpdate = (row) => {
  scene.value = 1
  console.log(row, '----------------')
  if (row) {
    Object.assign(attr, row)
  } else {
    Object.assign(attr, {
       attrName: '',
        id: '',
        categoryId: '',
        categoryLevel: 2,
        attrValueList: [],
    })
  }
  attr.categoryId = categoryStore.category2Id
}
// 취소시
const cancel = () => {
  // 페이지 변경
  scene.value = 0
}
// 속성값 추가
const addAttrValue = () => {
  attr.attrValueList.push({
    valueName: '',
    flag: true,
    attrId: attr.id,
    id: ''
  })
  //input focus  ===>ref inputAttr 의 마지막 input focus
  nextTick(() => {
    inputArr.value[inputArr.value.length - 1].focus()
  })
}

// editValueName -> div
const editValueName = (row, index) => {
  console.log(row)
  row.flag = true
  nextTick(() => {
    inputArr.value[index].focus()
  })
}

// 속성값 추가 --> input
const writeValueName = (row, index) => {
  if (row.valueName.trim() == '') {
    // 속성값이 비워있을경우 해당 index 삭제하기
    attr.attrValueList.splice(index, 1)
    ElMessage({
      message: '속성값이 비었습니다.',
      type: 'error',
    })
    return
  }
  const repeat = attr.attrValueList.find((i) => {
    if (i != row) {
      return i.valueName == row.valueName
    }
  })
  if (repeat) {
    ElMessage({
      message: '속성값이 중복되었습니다',
      type: 'error',
    })
    row.flag = false
    return false
  } else {
    ElMessage({
      message: '속성값이 등록되었습니다',
      type: 'success',
    })
  }
  row.flag = false
}
// 속성값 삭제
const deleteAttrValue = (index) => {
   attr.attrValueList.splice(index, 1)
}
// attr 저장하기
const saveAttr = async () => {
   const result:attrResponseData = await req_saveAttr(attr)
   if (result.code == 200) {
    ElMessage({
      message: '성공적으로 저장되었습니다.',
      type: 'success',
    })
      scene.value = 0
      getAttrInfo()
    } else {
      ElMessage({
          message: '실패',
          type: 'error',
        })
    }
}
// attr 삭제하기
const deleteAttr = async(row) => {
  console.log(row)
  const result:ResponseData = await req_removeAttr(row.id)
   if (result.code == 200) {
    ElMessage({
      message: '성공적으로 삭제되었습니다.',
      type: 'success',
    })
      // 성공시 다시 로딩
      getAttrInfo()
    } else {
      ElMessage({
          message: '실패',
          type: 'error',
        })
    }
}

// url경로 주소 변경시, store에 있는 데이터를 없애기
onBeforeUnmount(() => {
  categoryStore.$reset()
})
</script>

<style scoped></style>
