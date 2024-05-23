<template>
     <el-card style="height:80px;">
        <el-form :inline="true" class="form">
            <el-form-item label="userName">
                <el-input placeholder="유저네임 입력하세요" v-model="keyword"/>
            </el-form-item>
            <el-form-item>
                <el-button @click="search" :disabled="keyword? false : true">Search</el-button>
                <el-button type="primary" plain @click="reset">reset</el-button>
            </el-form-item>
        </el-form>
    </el-card>
    <el-card style="margin: 10px 0px;">
        <el-button type="primary" plain size="default" @click="saveAndUpdateRole" v-has="`btn.Role.add`">역할 추가</el-button>
        <el-table style="margin: 10px 0px;" :data="allRoles" border >
            <el-table-column label="#" align="center" width="80" type="index"></el-table-column>
            <el-table-column label="id" align="center"  width="80" prop="id"></el-table-column>
            <el-table-column label="유저 역할" align="center"  width="100" prop="name"></el-table-column>
            <el-table-column label="createTime" align="center" prop="createTime" show-overflow-tooltip></el-table-column>
            <el-table-column label="updateTime" align="center" prop="updateTime" show-overflow-tooltip></el-table-column>
            <el-table-column label="비고" align="center" width="260px">
                <template #="{row}">
                    <el-button icon="User" size="small" type="primary" @click="setPermission(row)" v-has="`btn.Role.assgin`">권한분배</el-button>
                    <el-button icon="Edit" size="small" type="success" @click="saveAndUpdateRole(row)" v-has="`btn.Role.update`">편집</el-button>
                    <el-button icon="Delete" size="small" type="danger" @click="deleteBtn(row)" v-has="`btn.Role.remove`">삭제</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            v-model:current-page="pageNo"
            v-model:page-size="pageSize"
            layout=" prev, pager, next,jumper,->,sizes, total"
            :total="total"
            :page-sizes="[3,5,7,10]"
            @size-change="getRoles(pageNo)" 
            @current-change="getRoles"
        />
    <!--dialog  Role 추가혹은 편집-->
        <el-dialog
            v-model="dialogVisible"
            :title="title"
            width="500"
            :before-close="handleClose"
        >
            <el-input placeholder="역할을 추가하세요" v-model="role.name"></el-input>
            <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="confirmRoleBtn" :disabled="role.name? false: true">
                Confirm
                </el-button>
            </div>
            </template>
        </el-dialog>
    <!-- 권한분배 -->
         <el-drawer v-model="drawer" :before-close="handleClose">
            <template #header>
            <h4>권한 분배</h4>
            </template>
            <template #default>
            <el-tree
                ref="treeRef"
                :data="menuList"
                show-checkbox
                node-key="id"
                default-expand-all
                :default-checked-keys="selectedArr"
                :props="defaultProps"
            />
            </template>
            <template #footer>
            <div style="flex: auto">
                <el-button @click="drawer == false">cancel</el-button>
                <el-button type="primary" @click="confirmClick">confirm</el-button>
            </div>
            </template>
        </el-drawer>
    </el-card>
</template>

<script setup lang="ts">
import { ref,onMounted, nextTick, watch} from 'vue'
import { reqPermissionList, req_permissionById, req_setPermission} from '@/api/user/Permission/index'
import { req_allRoles, req_saveRoles, req_deleteRoles } from '@/api/user/Roles/index'
import type { Roles, role} from '@/api/user/Roles/type'
import type { rPermission } from '@/api/user/Permission/type'
import useLayoutSettingStore from '@/store/modules/setting'
import { ElMessage} from 'element-plus'
// pageNo
let pageNo = ref<number>(1)
// 페이지 수
let pageSize = ref<number>(5)
// 페이지 토탈
let total = ref<number>(0)
// drawer
let drawer = ref<boolean>(false)
// search 키워드
let keyword = ref<string>('')
// allRoles
let allRoles = ref<Roles>([])
// dialog
let dialogVisible = ref<boolean>(false)
// dialog title
let title = ref<string>('')
// 배열: 선택된 마지막단의 id들의 집합
let selectedArr = ref<Array<number>>([])
// tree 원형
const treeRef = ref<any>()
// role
let role = ref<role>({
    id: '',
    name: '',
    createTime: '',
    updateTime: ''
})
// tree drawer
const defaultProps = {
    children: 'children',
    label: 'name'
}
//전체 메뉴리스트
let menuList = ref<any>([])

  


// all List 가져오기
const getPermission = async ()=> {
   const result = await reqPermissionList()
   menuList.value = result.data
}
// 권한 설정 클릭
const setPermission = async (row)=> {
    console.log(selectedArr.value, '=========selectedArr')
  role.value = row
  if(menuList.value.length == 0) {
    getPermission()
  }
  // roleId 에 의한 권한을 가져오기
  const result = await req_permissionById(role.value.id)
   
  if (result.code == 200) {
      selectedArr.value = result.data
      drawer.value = true
      nextTick(()=>{
          treeRef.value!.setCheckedKeys(selectedArr.value,true)
      })
  }
}
// reset
let settingStore = useLayoutSettingStore()
onMounted(() => {
    getRoles()
})
// role 리스트 가져오기
const getRoles = async(pager=1) => {
   pageNo.value = pager
   const result = await req_allRoles(pageNo.value, pageSize.value, keyword.value);
   if(result.code == 200) {
      allRoles.value = result.data.rows
      total.value = result.data.total
   }
}

// 리서치
const search = () => {
    getRoles()
    keyword.value = ''
}
// reset
const reset = () => {
  settingStore.refresh = !settingStore.refresh
}
// saveRole
const saveAndUpdateRole = (param:role) => {
    title.value = param.id? '업데이트' : '새로운 역할 추가하기'
    if(param.id) {
      role.value = param
    } else {
      role.value = []
    }
  dialogVisible.value = true
  console.log('role', row)
  
}
// ROle확인 버튼
const confirmRoleBtn =async () => {
    // roleName 유효성 검사
    if(roleAvaliability()) {
      ElMessage({
        type:'error',
        message: 'roleName 중복'
      })
      role.value.name = ''
      return false
    }
    // saveAndupdate
    const result = await req_saveRoles(role.value)
    if(result.code == 200) {
        ElMessage({
            type:'success',
            message: role.value.id? '업데이트 성공': '새로운 ROLE 추가완료'
        })
        getRoles()
        dialogVisible.value = false
    }
}
// 유효성 검사 - role네임 중복됐는지 확인
const roleAvaliability = () => {
    return allRoles.value.some(item => {
      if(role.value.id != item.id) {
          return item.name == role.value.name
      }
  })
}
// 삭제하기
const deleteBtn =async (param: role) => {
    const result = await req_deleteRoles(param)
    if(result.code == 200) {
        ElMessage({
            type: 'success',
            message: '삭제성공'
        })
        getRoles()
    } else {
        ElMessage({
            type: 'error',
            message: '삭제실패'
        })
    }
}
// permission 확인
const confirmClick = async() => {
  // 체크된 menuList의 id들의 집합
  const selectedData = treeRef.value!.getCheckedNodes(false,true)
  const checkedKeys = treeRef.value!.getCheckedKeys(true)
  let roleId:number = role.value.id
  console.log('data', selectedData)
  const resultData: rPermission[] = selectedData.map(item => {
    if(item.pId != 0) {
        return {
            id: '',
            'r_id': roleId,
            'p_id': item.id,
            'p_name': item.name,
            'type': item.type,
            'code': item.code,
            'level': item.level
        }
    }
  })
   console.log('resultData', resultData)
  const result = await req_setPermission(roleId, resultData)
  if (result.code == 200) {
    // drawer 닫음
    drawer.value = false
    // 메시지
    ElMessage({
        type:'success',
        message: '권한설정성공'
    })
    // 화면 새로고침
    reset()
  }
  
}
// drawer닫을 경우
const handleClose = () => {
   console.log(selectedArr.value,'=============')
   selectedArr.value = []
   drawer.value = false
}
</script>

<style scoped>
.form {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.form el-form-item {
    border: none;
}
</style>