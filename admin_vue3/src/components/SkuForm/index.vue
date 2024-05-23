<template>
    <el-form label-width="100px">
        <el-form-item label="spuName">
        <el-input  :value="skuForm.spuName" disabled />
        </el-form-item>
       
    </el-form>
    <el-form :model="skuForm" label-width="100px" :rules="rules" ref="ruleFormRef">   
        <el-form-item label="skuName" prop="skuName">
        <el-input v-model="skuForm.skuName" placeholder="네임"/>
        </el-form-item>
         <el-form-item label="price" prop="price" >
        <el-input  :value ="skuForm.price" placeholder="price(원)" disabled/>
        </el-form-item>
        <el-form-item label="skuDes" prop="decoration">
        <el-input v-model="skuForm.decoration" type="textarea" placeholder="내용"/>
        </el-form-item>
        <el-form-item label="stockNum(EA)" prop="quantity">
        <el-input v-model="skuForm.quantity" placeholder="수량"/>
        </el-form-item>
        <el-form-item label="Discount(%)" prop="discount">
        <el-input v-model="skuForm.discount" placeholder="할인"/>
        </el-form-item>
        <el-form-item label="속성" prop="skuAttrValueList">
            <el-form :inline="true">
                <el-form-item v-for="item in baseAttrList" :label="item.attrName">
                    <el-select  placeholder="please select your zone" v-model="item.attrIdAndValueId" filterable>
                    <el-option v-for="i in item.attrValueList" :value="`${i.id}:${i.attrId}`" :label="i.valueName"/>
                    </el-select>
                </el-form-item>
            </el-form>
        </el-form-item>
        <el-form-item label="SpuSaleAttr" prop="skuSaleAttrValueList">
            <el-form :inline="true">
                <el-form-item v-for="item in spuSaleAttrList" :label="item.baseAttrName">
                    <el-select   placeholder="please select your zone" v-model="item.saleAttrIdAandValueId">
                        <el-option v-for="i in item.spuSaleAttrValueList" :value="`${i.id}:${i.spuSaleAttrId}`" :label="i.spuAttrValueName"/>
                    </el-select>
                </el-form-item>
            </el-form>
        </el-form-item>
        <el-form-item label="imageName" prop="skuDefaultImg" >
            <el-table :data="fileList" border style="width: 100%" ref="tableRef">
                <el-table-column type="selection" width="55" align="center" /> 
                <el-table-column label="img">
                    <template #="{ row, $index }">
                    <img :src="row.imgUrl" style="width:100px; height:100px">
                    </template>
                </el-table-column>
                <el-table-column label="ImgName" prop="imgName" width="160" align="center"></el-table-column>
                <el-table-column label="비고" width="160" align="center">
                    <template #="{ row, $index }">
                    <el-button type="primary" size="small" @click="toggleSelection(row)" > setDefaultImage</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-form-item>
        <el-form-item>
        <el-button type="primary" @click="saveSku(ruleFormRef)">저장하기</el-button>
        <el-button @click="cancelBtn(ruleFormRef)">Cancel</el-button>
        </el-form-item>
  </el-form>
  
</template>

<script setup lang="ts">

import { ref, reactive, watch, nextTick} from 'vue'
import { req_spuSaleAttrList, req_imageList } from '@/api/product/spu'
import { req_saveSku } from '@/api/product/sku'
import { req_attrInfo } from '@/api/product/attr'
import type { skuInfo } from '@/api/product/sku/type'
import { ElMessage, FormInstance } from 'element-plus'
let $emit = defineEmits(['changeScene'])

let skuForm = reactive<skuInfo>({
    id: '',
    spuId: '',
    quantity: 1,
    price: 0,
    skuName: '',
    category2Id: '',
    skuDefaultImg : '',
    discount : 0,
    spuName: '',
    decoration: '',
    skuAttrValueList : [],
    skuSaleAttrValueList: []
})
const ruleFormRef = ref<FormInstance>()
let tableRef = ref()
let spuSaleAttrList = ref<any>([])
let baseAttrList = ref<any>([])
let fileList = ref<any>([])
let price = ref<number>()
let spuId = ref()
let defaultImg = ref({})

// 초기화
const initSku =async (category1Id:number, category2Id:number, row, param:string) => {
   
    spuId.value = param == 'addSku' ? row.id : row.spuId
    const baseAttrResult = await req_attrInfo(category1Id, category2Id)
        if (baseAttrResult.code == 200) {
            baseAttrList.value = baseAttrResult.data
        }
    const spuSaleResult = await req_spuSaleAttrList(spuId.value)
        if (spuSaleResult.code == 200) {
            spuSaleAttrList.value = spuSaleResult.data
        }
    const imgResult:spuImgResponseData = await req_imageList(spuId.value)
    fileList.value = imgResult.data

    if(param == 'addSku') {
        skuForm.spuName = row.spuName
        skuForm.price = row.price
        skuForm.spuId = row.id
        skuForm.category2Id = category2Id
    } else if(param == 'editSku'){
        baseAttrList.value.forEach(item => {
            row.skuAttrValueList.forEach(i => {
                if(item.id == i.attrId) {
                  item.attrIdAndValueId = `${i.valueId}:${i.attrId}`
                }
            })
        })
        spuSaleAttrList.value.forEach(item => {
            row.skuSaleAttrValueList.forEach(i => {
                if(item.id == i.saleAttrId) {
                  item.saleAttrIdAandValueId = `${i.saleValueId}:${i.saleAttrId}`
                }
            })
        })
        defaultImg.value = fileList.value.find(item => item.imgUrl == row.skuDefaultImg)
        Object.assign(skuForm, row)
        nextTick(()=> {
            toggleSelection(defaultImg.value)
        })
    }
  

    
}

// 상품 속성,세일속성 선택됐는지 감시
watch( ()=>baseAttrList.value,(newValue)=>{
    const result = newValue.every(item => item.attrIdAndValueId)
    if(result) {
       ruleFormRef.value.clearValidate(['skuAttrValueList'])
       setskuAttrValueList()
   }
},{deep:true})

watch(()=>spuSaleAttrList.value,(newValue)=>{
   const result = newValue.every(item => item.saleAttrIdAandValueId)
   console.log(result, 'result++++++++++')
   if(result) {
        ruleFormRef.value.clearValidate(['skuSaleAttrValueList'])
        setSkuSaleAttrValueList()
   }
},{deep:true})

// setSkuAttrValueList => 값부여
const setskuAttrValueList = () => {
    skuForm.skuAttrValueList = baseAttrList.value.reduce((prev: any, next: any) => {
            const [valueId, attrId]= next.attrIdAndValueId.split(":")
            if(attrId && valueId) {
                prev.push(
                   {
                        attrId,
                        valueId
                    }
                ) 
                return prev  
            }
        },[])
}

// 세일속성아이디 및 값 리스트
const setSkuSaleAttrValueList = () => {
       
       skuForm.skuSaleAttrValueList =spuSaleAttrList.value.reduce((prev: any, next: any) => {
                const ids = next.saleAttrIdAandValueId.split(":")
                const [saleValueId,saleAttrId] = ids
                            prev.push(
                            {   
                                saleValueId,
                                saleAttrId
                                }
                            ) 
                return prev        
       }, [])
}

// 속성 규칙확인
const validateAttrValue = (rule, value, callbakck) => {
    if(value){
         ruleFormRef.value.clearValidate(['skuAttrValueList'])
    } else {
        callbakck(new Error('Please input the attrValue'))
    }
    callbakck()
}

// 세일속성 확인
const validateSaleAttrValue = (rule, value, callbakck) => {
    console.log(rule, value)
    if(value){
         ruleFormRef.value.clearValidate(['skuSaleAttrValueList'])
    } else {
        callbakck(new Error('Please input the skuSaleAttrValueList'))
    }
    callbakck()
}

// 규칙
const rules = {
   skuName: [{ required: true},{ min: 3, message: 'Length should be 3 ', trigger: 'blur' }],
   decoration: [{required: true},{ min: 10, message: 'Length should be 10', trigger: 'blur' },],
   attrName: [{required: true}],
   discount: [{required: true}],
   quantity: [{required: true}],
   skuDefaultImg: [{required: true}],
   skuSaleAttrValueList: [{required: true},{validator:validateSaleAttrValue , trigger:'blur'}],
   skuAttrValueList: [{required: true},{validator:validateAttrValue , trigger:'blur'}]
}
// rule 취소
const clearValidation = () => {
  ruleFormRef.value.clearValidate();
}

// toggleSelection
const toggleSelection = (row) => {
  tableRef.value.clearSelection()
  tableRef.value.toggleRowSelection(row, true)
  skuForm.skuDefaultImg = row.imgUrl
}
// Data 초기화
const initSkuData = () => {
        Object.assign(skuForm, {    
        id: '',
        spuId: '',
        quantity: 1,
        price: 0,
        skuName: '',
        category2Id: '',
        skuDefaultImg : '',
        discount : '',
        spuName: '',
        decoration: '',
        skuAttrValueList : [],
        skuSaleAttrValueList: []})
}
// 저장하기 
const saveSku =async (formEl: FormInstance | undefined) => {
   if(!formEl) return
   if(!ruleFormRef.value) return
    await formEl.validate((valid, fields) => {
        if (valid) {
        console.log('submit!')
        } else {
        console.log('error submit!', fields)
        }
    })
   console.log(skuForm, 'skuForm')
   const result = await req_saveSku(skuForm)
    if(result.code == 200) {
      ElMessage({
        type:'success',
        message: skuForm.id? '업데이트완료' : '추가완료'
      })
      initSkuData()
      $emit('changeScene', {flag:0, params:'update'})
    } else {
        ElMessage({
            type:'error',
            message: 'SKU 추가 실패'
        })
    }
}
// 취소
const cancelBtn = (formEl:FormInstance) => {
if (!formEl) return
formEl.resetFields()
$emit('changeScene', {flag:0, params:'cancel'})
}
defineExpose ({
    initSku
})
</script>
<style scoped>

</style>