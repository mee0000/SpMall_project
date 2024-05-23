<template>
  <div>
   <el-card class="box-card">
     <!--브랜드 추가하기-->
     <el-button type="primary" size="default" icon="Plus" @click="addTrademark()">브랜드 추가</el-button>
     <!--존재하고 있는 브랜드-->
     <el-table style="margin: 10px 0" border :data="trademark">
        <el-table-column label="NO" align="center" width="80px" type="index"></el-table-column>
        <el-table-column label="브랜드 네임" align="center" prop="tmName"></el-table-column>
        <el-table-column label="브랜드 로고" align="center">
            <template #default="{row, $index}">
               <img :src="row.logoUrl" style="width:100px; height:100px;"/>
            </template>
        </el-table-column>
        <el-table-column label="비고" align="center">
             <template #default="{row, $index}">
               <el-button type="primary" plain size="small" icon="Edit" @click="modifyTrademark(row)"></el-button>
               <el-button type="danger" plain icon="Delete" size="small" @click="deleteTrademark(row)"></el-button>
            </template>
        </el-table-column>
     </el-table>
     <!--네비게이션 -->
      <el-pagination
       v-model:current-page="pageNo"
       v-model:page-size="limit"
      layout=" prev, pager, next,jumper,->,sizes, total"
      :total="total"
      @size-change="trademarkList()"
      @current-change="trademarkList(pageNo)"
    />
  </el-card>
  <!-- alert창 띄우기 / 브랜드명 추가 또는 수정  -->

  <el-dialog v-model="dialogFormVisible" :title="form.id ? 'modify Trademark':'add Trademark'">
    <el-form :model="form" ref="ruleFormRef" :rules="rules">
      <el-form-item label="Brand Name" label-width="80px" prop="tmName">
        <el-input v-model="form.tmName" placeholder="브랜드명을 적으세요" />
      </el-form-item>
      <el-form-item label="Brand Logo" label-width="80px" prop="logoUrl">
        <el-upload
          class="avatar-uploader"
          action="http://sph-api.atguigu.cn/admin/product/fileUpload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
    <img v-if="form.logoUrl" :src="form.logoUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveTrademark">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</div>
</template>

<script setup lang="ts">
import type {Records, TrademarkResponseData } from '@/api/product/trademark/type'
import { ref, onMounted, reactive, nextTick} from 'vue'
import { reqHasTrademark, reqSaveTrademark } from '@/api/product/trademark'
import { UploadProps, ElMessage } from 'element-plus'
// 현재페이지(current-page)
let pageNo = ref<number>(1)
// 한페이지 노출될 개수 (page-size)
let limit = ref<number>(3)
// 총개수
let total =ref<number>(0)
// 브랜드 리스트 
let trademark =reactive<Records>([])
// alert 창 띄우기
let dialogFormVisible = ref<boolean>(false)
// 폼리스트
let form =reactive<Trademark>({
  tmName: '',
  logoUrl: ''
})
// 폼el-form 원형 가져오기
let ruleFormRef = ref()
// 현재 있는 브랜드리스트 가져오기 --> 인자값이 없을시 page =1 
//@ current-change="trademarkList()" --> 자동으로 인자값 현재 페이지를 인자값으로 넣음 pageNo
const trademarkList = async(pager =1) => {
   pageNo.value = pager
   console.log(pageNo.value, '===========')
   let {data} :TrademarkResponseData = await reqHasTrademark(pageNo.value, limit.value)
   if (data.code == 200) {
       trademark = data.records
       total.value = data.total
   }
}
// const handleSizeChange =() => {
//     // 이렇게 limit 변경시 현재 페이지가 변경 
//     //  pageNo.value = 1 혹은 trademarkList(page =1) 기본값을 줘도 됨
    
// }
// 폼 유효성 체크
const validateTmName = (rule: any, value: any, callback: any) => {
    if (value.trim().length >=2 ) {
      // 유효성 통과
      callback()
      } else {
      // 실패시 에러 발생
      callback(new Error('브랜드명 2글자 이상'))
    }
  }
const validateLogoUrl = (_: any, value: any, callback: any) => {
  // value : url 값
  if (value) {
    callback()
  } else {
    callback(new Error('이미지 업로드 부탁드립니다.'))
  }
}
const rules = {
   tmName: [{ required: true, validator: validateTmName, trigger: 'blur' }],
   logoUrl: [{required: true, validator: validateLogoUrl, trigger: 'change'}],
}
// trademark 추가
const addTrademark = () => {
  dialogFormVisible.value = true
  form.tmName = ''
  form.logoUrl = ''
  form.id = 0

  // ruleFormRef.value?.clearValidate('tmName')
  // ruleFormRef.value?.clearValidate('logoUrl')
  nextTick(()=> {
    ruleFormRef.value.clearValidate()
  })
}
// trademark 수정 
const modifyTrademark= (row) => {
  dialogFormVisible.value = true
  ruleFormRef.value.clearValidate()
  Object.assign(form, row)
  nextTick(()=> {
    ruleFormRef.value.clearValidate()
  })
}
// trademark 확인시 url 요청 저장하기
const saveTrademark = async () => {
  // 유효성 검사 , await 통과 안되면은 return false , 뒷 코드 진행안함
  await ruleFormRef.value.validate()
  dialogFormVisible.value = false
  let result:any = await reqSaveTrademark(form)
  console.log('저장하기', result)
  if( result.status == 200) {
    ElMessage({
      type: 'success',
      message: form.id ? '수정완료' : '브랜드 추가 완료'
    })
     trademarkList(form.id ? pageNo.value : 1)
  } else {
     ElMessage({
      type: 'error',
      message: form.id ? '수정실패' : '브랜드 추가 실패'
    })
  }
}
// trademark 삭제 
const deleteTrademark = (row) => {
    console.log('삭제하기', row.id)
}
// 마운팅후 브랜드값 리스트 가져오기
onMounted(() => {
    trademarkList()
})
// 파일 업로드 전에 발생하는 함수
// const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
//   console.log('11111')
// }
// file 업로드 성공시
const handleAvatarSuccess: UploadProps['onSuccess'] = (response: anyes) => {
    form.logoUrl = response.data
    // file 업로드 성공하면 clearValidate() ==> 전체 취소
    // clearValidate('logoUrl') ==> logoUrl 에만 취소
    ruleFormRef.value.clearValidate('logoUrl')
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>