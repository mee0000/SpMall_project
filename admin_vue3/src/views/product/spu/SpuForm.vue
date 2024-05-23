<template>
  <el-form label-width="80px" >
    <el-form-item label="SPUName" >
        <el-input v-model="spu.spuName" />
    </el-form-item>
     <el-form-item label="Price" >
        <el-input v-model="spu.price" />
    </el-form-item>
     <el-form-item label="SPUDes" >
        <el-input type="textarea" v-model="spu.description"/>
    </el-form-item>
     <el-form-item label="MainImage" >
       <!-- 
          v-modle =  전시할 이미지 리스트
          action: 업로드 경로 
          list-type: 전시할 이미지 형식  글/ 이미지 등....
          :on-preview="handlePictureCardPreview" // 이미지 크게 보기
          on-remove : 삭제할 경우
        -->
         <el-upload 
          v-model:file-list="fileList"
          action="http://mee0-hello.oss-ap-northeast-2.aliyuncs.com"
          :data="dataObj"
          :headers="mHeaders"
          :on-success="handleUploadSuccess"
          list-type="picture-card"
          :on-remove="handleRemove"
          :before-upload="beforeUpload"
          :multiple="false"
          >
          <template #trigger>
            <el-icon><Plus /></el-icon>
          </template>
        </el-upload>
    </el-form-item>
     <el-form-item label="SPUSaleAttr" >
            <el-select :placeholder="`${unSelectSaleAttr.length} 개 선택가능`" v-model="attrIdAndAttrName" >
            <el-option v-for="item in unSelectSaleAttr" :key="item.id" :label="item.attrName" :value="`${item.id}:${item.attrName}`"/>
        </el-select>
        <el-button
            type="primary"
            size="default"
            icon="Plus"
            @click="addSPUSaleAttr"
            style="margin-left: 10px"
            :disabled="unSelectSaleAttr.length == 0? true: false"
        >
            SPU SaleAttr 추가
        </el-button>
         <el-table border style="margin: 10px 0" :data="spu.spuSaleAttrList">
        <el-table-column
          type="index"
          label="NO"
          width="80"
          align="center"
        />
        <el-table-column label="AttrName" prop="baseAttrName" width="100"> </el-table-column>
        <el-table-column label="AttrValue" prop="baseAttrName">
          <template #="{row, $index}">
              <el-tag
                  style="margin-right:5px;"
                  v-for="(item,index) in row.spuSaleAttrValueList"
                  :key="item.id"
                  class="mx-1"
                  closable
                  :disable-transitions="false"
                  @close="row.spuSaleAttrValueList.splice(index,1)"
                >
                  {{ item.spuAttrValueName }}
            </el-tag>
            <el-input
                  v-if="row.flag"
                  ref="InputRef"
                  size="small"
                  style="width:150px;"
                  v-model="row.saleAttrValue"
                  @keyup.enter="handleInputConfirm(row)"
                  @blur="handleInputConfirm(row)"
            />
            <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput(row)">
              + New Tag
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="비고" align="center" width="120">
            <template  #="{ row, $index }">
                <el-button
                  type="danger"
                  plain
                  size="small"
                  icon="Delete"
                  @click="spu.spuSaleAttrList.splice($index,1)"
              ></el-button>
            </template>
        </el-table-column>
      </el-table>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="saveAndUpdateSpu">저장</el-button>
      <el-button @click="$emit('changeScene', {flag: 0, params: 'cancel'})">취소</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { req_attrInfo} from '@/api/product/attr'
import { req_getSpuById, req_saveAndUpdate, req_getBaseSaleAttrList, req_imageList, req_policy } from '@/api/product/spu'
import { ref, reactive, computed} from 'vue'
import type { spu, img, imgList, spuImgResponseData} from '@/api/product/spu/type'
import { ElMessage } from 'element-plus'
import useUserStore from '@/store/modules/user'
import axios from 'axios'
let spuAttrList = ref([])
let $emit = defineEmits(['changeScene'])
let attrIdAndAttrName = ref('')
let userStore = useUserStore()
let mAction = ref('')
let dataObj= ref({})
// 상품이미지
let fileList = ref<imgList>([])
// 이미갖고 있는 spu 
let spu = ref({
  id: '',
  spuName: '',
  price: 0,
  description: '',
  category2Id: '',
  spuSaleAttrList: [],
  spuImageList: []
})
// spu 추가
const addSPU = async (category1Id:number, category2Id:number) => {
  const {data} = await req_getBaseSaleAttrList()
  spuAttrList.value = data
  spu.value = {
    id: '',
    spuName: '',
    description: '',
    category2Id: category2Id,
    spuSaleAttrList: []
  }
}
// spu 설정
const initSpu =async (row) => {
  const result = await req_getBaseSaleAttrList()
  spuAttrList.value = result.data
  const {id} = row
  const {data}:spu =await req_getSpuById(id)
  spu.value = data
  console.log(spu.value, '=========')
  // 이미지리스트 가져오기 --> img{name:'' , url: ''}
  const imgResult:spuImgResponseData = await req_imageList(id)
  fileList.value = imgResult.data.map(item => {
     return {
       id: item.id,
       spuId: item.spuId,
       name: item.fileOriginalName,
       url: item.imgUrl
     }
    })
}
// 선택되지 않은 saleAttr
const unSelectSaleAttr = computed(() => {
  return spuAttrList.value.filter(item => {
      return spu.value.spuSaleAttrList.every((i) => {
        return item.attrName != i.baseAttrName
      })
  })
})
// spuSaleAttr 삽입
const addSPUSaleAttr = () => {
  const [baseAttrId, baseAttrName] = attrIdAndAttrName.value.split(":")
  const spuSaleAttr = {
    id:'',
    spuId: spu.value.id,
    baseAttrId,
    baseAttrName,
    spuSaleAttrValueList: [],
    flag: false
  }
  spu.value.spuSaleAttrList.push(spuSaleAttr) 
  attrIdAndAttrName.value = ''
}

  const getUUID =() => {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : (+'r&0x3' | +'0x8')).toString(16)
      })
    }
 const beforeUpload = (file) => {
        return new Promise((resolve, reject) => {
          // 前后端提交post异步请求获取签名信息
              console.log('......refdfd')
              req_policy().then(response => {
              dataObj.value = {
                policy: response.data.policy,
                Signature: response.data.signature,
                OSSAccessKeyId: response.data.accessid,
                key: response.data.dir + getUUID() + file.name,
                dir: response.data.dir,
                host: response.data.host
              }
              console.log('.........', dataObj.value)
              resolve(true)
            }).catch(err => {
              console.log('...err..', err)
              reject(false)
            })
        })
       }

const handleUploadSuccess= (response: any, file: any) => {
      console.log('上传成功...', file)
      fileList.value.pop()
      fileList.value.push({id:'', spuId:spu.value.id, name: file.name, url: dataObj.value.host + '/' + dataObj.value.key.replace('${filename}', file.name)})
 
    }

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
  // dialogImageUrl.value = uploadFile.url!
  // dialogVisible.value = true
}
const handleRemove: UploadProps['onRemove'] = (_, uploadFiles) => {
  fileList.value = uploadFiles
}
// spuSaleAttrValue 편집
const showInput = (row) => {
  console.log(row)
  row.saleAttrValue = ''
  row.flag = true
}

// input
const handleInputConfirm = (row) => {
  row.flag = false
  if(row.saleAttrValue == '') return
  let result = row.spuSaleAttrValueList.some(item => {
    return item.spuAttrValueName == row.saleAttrValue
  })
  if(result) {
    return
  }
  row.spuSaleAttrValueList.push({
    id: '',
    spuId: row.spuId,
    spuSaleAttrId: '',
    spuAttrValueName: row.saleAttrValue
  })
}
// 저장및 업데이트
const saveAndUpdateSpu = async() => {
  // 1. fileList 이미지 정리
  console.log('fileLIst', fileList.value)
  spu.value.spuImageList = fileList.value.map(item => {
    return {
      id: item.id,
      imgName: item.name,
      imgUrl: item.url,
      spuId: item.spuId
    } 
  })
  const result = await req_saveAndUpdate(spu.value)
  if(result.code == 200) {
    ElMessage({
      type:'success',
      message: spu.value.id ? '업데이트 완료': 'SPU 추가 완료'

    })
    $emit('changeScene', {flag: 0, params: spu.value.id ? 'update' : 'newAdd' })
  } else {
     ElMessage({
      type:'error',
      message: spu.value.id? '업데이트 실패': 'SPU 추가 실패'

    })
  }
}

const mHeaders = computed(() => {
  return {
    token: userStore.token
  }
})


  // ref 부모한테 노출 
  defineExpose({
    addSPU,
    initSpu
  })

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