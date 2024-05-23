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
        <el-button type="primary" plain size="default" @click="addAndUpdateUserInfo" v-has="`btn.User.add`">유저 추가</el-button>
        <el-button size="default" type="danger" plain @click="deleteUsers" :disabled="multipleSelection.length? false: true" v-has="`btn.User.remove`">여러개 삭제</el-button>
        <!-- 유저 상세정보 -->
        <el-table style="margin: 10px 0px;" border :data="userList"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="60px" align="center"></el-table-column>
            <el-table-column label="#" align="center" width="80" type="index"></el-table-column>
            <el-table-column label="id" align="center" prop="id" width="80"></el-table-column>
            <el-table-column label="userName" align="center" prop="username" width="100"></el-table-column>
            <el-table-column label="닉네임" align="center" prop="nickName"></el-table-column>
            <el-table-column label="유저 역할"  align="center">
                <template  #="{row}">
                    <el-tag v-for="item in row.roles" :key="item.id" class="ml-2">{{item.roleName}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="createTime" align="center" prop="createTime" show-overflow-tooltip></el-table-column>
            <el-table-column label="updateTime" align="center" prop="updateTime" show-overflow-tooltip></el-table-column>
            <el-table-column label="비고" align="center" width="260px">
                <template #="{row}">
                    <el-button icon="User" size="small" type="primary" @click="setRole(row)" v-has="`btn.User.assgin`">역할분배</el-button>
                    <el-button icon="Edit" size="small" type="success" @click="addAndUpdateUserInfo(row)" v-has="`btn.User.update`">편집</el-button>
                    <el-button icon="Delete" size="small" type="danger" @click="deleteUserInfo(row)" v-has="`btn.User.remove`">삭제</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            v-model:current-page="pageNo"
            v-model:page-size="pageSize"
            layout=" prev, pager, next,jumper,->,sizes, total"
            :total="total"
            :page-sizes="[3,5,7,10]"
            @size-change="getUserList(pageNo)" 
            @current-change="getUserList"
        />
        <!-- drawer - user 새로추가 + 업데이트 -->
          <el-drawer v-model="drawer" :direction="direction">
            <template #header>
            <h4 v-if="user.id">유저 업데이트</h4>
            <h4 v-else>유저 새로 추가</h4>
            </template>
            <template #default>
              <el-form label-width="80px" :rules="rules" ref="inputValidate">
                 <el-form-item label="유저 네임" prop="username">
                    <el-input placeholder="이름을 입력하세요" v-model="user.username"/>
                 </el-form-item>
                 <el-form-item label="닉 네임" prop="nickName">
                    <el-input placeholder="닉네임을 입력하세요" v-model="user.nickName"/>
                 </el-form-item>
                 <el-form-item label="비밀번호" prop="password">
                    <el-input placeholder="비밀번호를 입력하세요" v-model="user.password"/>
                 </el-form-item>
              </el-form>
            </template>
            <template #footer>
                <div style="flex: auto">
                    <el-button @click="drawer = false">cancel</el-button>
                    <el-button type="primary" @click="confirmClick">confirm</el-button>
                </div>
            </template>
        </el-drawer>
        <!-- drawer1 역할분배 -->
           <el-drawer v-model="drawer1">
            <template #header>
            <h4 >역할분배</h4>
            </template>
            <template #default>
               <el-form>
                  <el-form-item label="userName" >
                      <el-input v-model="user.username" disabled></el-input>
                  </el-form-item>
                  <el-form-item label="역할 설정">
                        <el-checkbox style="width: 100%"
                            v-model="checkAll"
                            :indeterminate="isIndeterminate"
                            >Check all </el-checkbox>
                        <el-checkbox-group
                            v-model="userRoles"
                            @change="handleCheckedCitiesChange">
                            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role">{{
                             role.name
                            }}</el-checkbox>
                        </el-checkbox-group>
                  </el-form-item>
               </el-form>
            </template>
            <template #footer>
                <div style="flex: auto">
                    <el-button @click="drawer1 = false">cancel</el-button>
                    <el-button type="primary" @click="confirmClickRole">confirm</el-button>
                </div>
            </template>
        </el-drawer>
    </el-card>
</template>

<script setup lang="ts">
import {ref, onMounted, reactive} from 'vue'
import { ElMessage } from 'element-plus'
import useLayoutSettingStore from '@/store/modules/setting'
import type { userRoles, userResponseData } from '@/api/user/type'
import { req_getUserList, req_saveUser, req_deleteUser, req_getRoles, req_saveUserRoles, req_deleteUsersByIds, req_getUserRoles } from '@/api/user'
// pageNo
let pageNo = ref<number>(1)
// 페이지 수
let pageSize = ref<number>(5)
// 페이지 토탈
let total = ref<number>(0)
// drawer
let drawer = ref<boolean>(false)
// drawer1
let drawer1 = ref<boolean>(false)
// search 키워드
let keyword = ref<string>('')
// 역할
let direction = ref('rtl')
// userRole
let userRoles = ref<any>([])
// 역할 전체 선택
let checkAll = ref<boolean>(false)
// 모든 역할
let allRoles = ref<any>([])
const isIndeterminate = ref(true)
// 테이블 셀렉션
const multipleSelection = ref<any>([])
// userInfoList
let userList = ref<any>([])
// 유효성 체크
let inputValidate = ref()  
// store-reset
let settingStore = useLayoutSettingStore()
// user 
let user = reactive<any>({
    id: '',
    createTime: '',
    updateTime: '',
    nickName: '',
    username: '',
    avatar: '',
    roles: [],
    buttons: [],
    routes: []
})
// 유효성검사


onMounted(()=> {
   getUserList()
   getAllRoles()
})

const getUserList = async (pager = 1) => {
    // 현재 페이지 수집
    pageNo.value = pager
    const result:userResponseData = await req_getUserList(pageNo.value, pageSize.value, keyword.value)
    console.log(result, 'result')
    if(result.code ==200) {
        userList.value = result.data.rows
        total.value = result.data.total
    }

}
// 모든 role 가져오기 
const getAllRoles = async () => {
    const {data} = await req_getRoles()
    allRoles.value = data
}
// search
const search = () => {
    getUserList()
    keyword.value = ''
}
// reset
const reset = () => {
    settingStore.refresh = !settingStore.refresh
}
// user 편집하기 혹은 유저추가
const addAndUpdateUserInfo = (row) => {
    console.log(row)
  if(row.id) {
      Object.assign(user, row)
  }else {
      Object.assign(user, {
        id: '',
        createTime: '',
        updateTime: '',
        nickName: '',
        username: '',
        password: '',
        avatar: '',
        roles: [],
        buttons: [],
        routes: []
      })
  }
  drawer.value = true
}
// user 삭제
const deleteUserInfo = async(row) => {
  const result = await req_deleteUser(row.id)
  if(result.code == 200) {
     ElMessage({
        type:'success',
        message: '삭제 성공'
      })
      getUserList()
    } else {
        ElMessage({
            type:'error',
            message: '삭제 실패'
        })
    }
}
// user 여러개 삭제하기
const deleteUsers = async() => {
    const userIds:any = multipleSelection.value.map(item => {
        return (item.id as number)
    })
    
    const result = await req_deleteUsersByIds(userIds)
    if(result.code == 200) {
        ElMessage({
            type:'success',
            message: '삭제 성공'
      })
      getUserList()
    } else {
        ElMessage({
            type:'error',
            message: '삭제 실패'
        })
    }
}
// 선택된 유저
const handleSelectionChange = (val: any) => {     
  multipleSelection.value = val
}
// set Role
const setRole =async (row) => {
    drawer1.value = true
    user = row
    // user_id = row.id
    const {data} = await req_getUserRoles(row.id)
    userRoles.value = data
} 
// 전부 체크외었는지 확인
const handleCheckedCitiesChange = (value: any) => {
    const checkedCount = value.length
    checkAll.value = checkedCount === allRoles.value.length
    isIndeterminate.value = checkedCount > 0 && checkedCount < allRoles.value.length
}
// save User
const confirmClick =async () => {
    // 유효성 체크 완료시 로그인 진행 promis로 반환 await 로 판단 reject 로 되면 reren false
   //  await inputValidate.value.validate()
  const result =await req_saveUser(user)
  if(result.code == 200) {
     ElMessage({
        type:'success',
        message: user.id? '업데이트 완료' : '새로 추가 완료'
      })
      drawer.value = false
      getUserList()
    } else {
        ElMessage({
            type:'error',
            message: user.id? '업데이트 실패' : '새로 추가 실패'
        })
    }
}
// set Role 
const confirmClickRole =async () => {
      const newRoles = []
      userRoles.value.forEach((item) => {
        newRoles.push({
            roleId: item.id,
            userId: user.id,
            roleName: item.name
        })
    })
    // Role 저장하기
     const result = await req_saveUserRoles(user.id, newRoles)
     if(result.code == 200) {
        drawer1.value = false
        getUserList(pageNo.value)
     }
}
</script>

<style scoped>
.form {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.form el-form-item{
    border:none;
}
</style>