<template>
   <el-table :data="menuList" style="width: 100%; margin-bottom:20px;" row-key="id" border >
      <el-table-column label="name" prop="name"/>
      <el-table-column label="권한" prop="code"/>
      <el-table-column label="수정시간" prop="updateTime"/>
      <el-table-column label="기타">
         <template #= "{row}">
            <el-button type="primary" size="small" :disabled="row.level ==4?true: false" @click="addchildPermission(row)">메뉴추가</el-button>
            <el-button type="primary" size="small" :disabled="row.level ==1? true: false" @click="updatePermission(row)">편집</el-button>
             <el-popconfirm :title="`${row.name}을/를 삭제하겠습니까?`" @confirm="confirmRemove(row)">
                    <template #reference>
                        <el-button  type="primary" size="small" :disabled="row.level ==1? true: false">삭제</el-button>
                    </template>
                </el-popconfirm>
         </template>
      </el-table-column>
   </el-table>
   <el-dialog v-model="dialogVisible" title="Tips" width="30%">
     <el-form label-width="auto">
        <el-form-item label="name">
            <el-input placeholder="권한네임을 입력하세요" v-model="permission.name"></el-input>
        </el-form-item>
        <el-form-item label="권한">
            <el-input placeholder="권한값을 입력하세요" v-model="permission.code"></el-input>
        </el-form-item>
     </el-form>
     <template #footer>
        <span class="dialog-footer">
           <el-button @click="dialogVisible=false">Cancel</el-button>
           <el-button @click="savePermission" type="primary">Confirm</el-button>
        </span>
     </template>
   </el-dialog>
</template>

<script setup lang="ts">
import { reqPermissionList, req_savePermission, req_removePermission } from '@/api/user/Permission/index'
import { onMounted, ref, reactive } from 'vue'
import {ElMessage} from 'element-plus'
let dialogVisible = ref<boolean>(false)
let menuList = ref<any>([])
let permission= reactive<permission>({
  id: '',
  pid: '',
  name: '',
  code: '',
  toCode: '',
  type: '',
  status: '',
  level: '',
  select: '',
  path: '', 
  checked: false,
  createTime: '',
  updateTime: '',
  children:[]
})
onMounted(()=> {
   getAllList()
})

const getAllList = async ()=> {
   const result = await reqPermissionList()
   menuList.value = result.data
}
// 권한을 추가히기--> button
const addchildPermission = (row) => {
  dialogVisible.value = true
  const childPermission = {
   name: '',
   code: '',
   pid: row.id,
   level: row.level +1
  }
  Object.assign(permission, childPermission)
}

// 권한을 업데이트하기--> button
const updatePermission = (row) => {
   dialogVisible.value = true
   Object.assign(permission, row)
}
// 권한 삭제하기
const confirmRemove =async (row) => {
   const result = await req_removePermission(row)
   if(result.code == 200) {
      ElMessage({
         type: 'success',
         message: '삭제 성공'
      })
   }

}
// 업데이트및 추가 확인
const savePermission = async() => {
  const result = await req_savePermission(permission)
  if(result.code == 200) {
     dialogVisible.value = false
     permission = {
      id: '',
      pid: '',
      name: '',
      code: '',
      toCode: '',
      type: '',
      status: '',
      level: '',
      select: '',
      path: '', 
      checked: false,
      createTime: '',
      updateTime: '',
      children:[]
     }
     getAllList()
  }
}
</script>

<style scoped>

</style>