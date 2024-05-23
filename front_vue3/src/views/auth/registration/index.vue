<template>
      <div class="cart-table-area section-padding-100">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12 col-lg-8" style="margin-left:15%;">
                        <div class="checkout_details_area  ">

                            <div class="cart-title">
                                <h2>Registration</h2>
                            </div>
                            <form @submit.prevent="onSubmit">
                                <div class="row">
                                      <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" style="border-bottom:2px solid red;" v-model.trim="userName.inputValue" placeholder="userName:6~20자의 영문 소문자와 숫자만" ref="" @blur="duplicationuserName">
                                        <span :class="userName.isValid?'error-msg':'success-msg'"> {{userName.errorMsg}} </span>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="password" class="form-control" style="border-bottom:2px solid red;" v-model="password.inputValue" placeholder="Password:8~20자의 영문, 숫자, 특수문자" @blur="checkPassword">
                                        <span class="error-msg" v-show="password.isValid"> {{password.errorMsg}} </span>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" v-model="user.firstName" placeholder="First Name">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control"  v-model="user.lastName" placeholder="Last Name">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control"  v-model="user.zipCode" placeholder="Zip Code" value="">
                                    </div>
                                    <div class="col-12 mb-3">
                                        <input type="text" class="form-control mb-3"  v-model="user.address" placeholder="Address" value="">
                                    </div>
                                    <div class="col-md-12 mb-3">
                                        <input type="text" class="form-control"style="border-bottom:2px solid red;" v-model="phoneNumber.inputValue" min="0" placeholder="Phone No" @blur="checkPhoneNumber">
                                        <span class="error-msg" v-show="phoneNumber.isValid">错误提示信息</span>
                                    </div>

                                    <div class="col-12">
                                        <div class="custom-control custom-checkbox d-block mb-2">
                                            <input type="checkbox" class="custom-control-input" id="customCheck2" :checked="agree.isChecked">
                                            <label class="custom-control-label" for="customCheck2">Create an accout</label>
                                        </div>
                                        <span class="error-msg" v-show="agree.isValid">错误提示信息</span>
                                    </div>
                                    <div class="col-12 mb-3">
                                        <button class="submit">가입하기</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup lang="ts">
import { useValidation } from '@/hooks/useValidation'
import { ref, reactive, toRefs, watch } from 'vue'
import { req_checkUserName, req_register } from '@/api/user/index.ts'
import type{User} from '@/api/user/index'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
let user:User = reactive({
    userName: '',
    password: '',
    firstName: '',
    lastName: '',
    address: '',
    phoneNumber: '',
    zipCode:''
})

const { checkuserName,phoneNumber, checkPassword, userName, password, errMsg, enteredInput, inputValid } = useValidation()

// 동의 여부
let agree = ref({isChecked: true, isValid:false})

let $router = useRouter()
// 회원가입하기
const onSubmit = async() => {
   enteredInput(userName.value, password.value, phoneNumber.value)
   if(inputValid.value){
       ElMessage({
         type:"error",
         message:"유효성검사 실패"
       })
     return false
   }
   let productUser = {
      userName: userName.value.inputValue,
      password: password.value.inputValue,
      phoneNumber: phoneNumber.value.inputValue
   }
   Object.assign(user, productUser)
   console.log(user, '--------User')
   // 유효성검사 통과시 서버에 저장
   const result = await req_register(user)

   // 저장성공시 login 페이지로 이동
   if(result.code == 200) {
      $router.replace('/login')
      ElMessage({
            type: 'success',
            message: '회원가입 성공'
        })
   } else {
        ElMessage({
                type: 'error',
                message: '회원가입 실패'
            })
   }
}

// userName 유일한지 확인하기
const duplicationuserName = async () => {
   // 중복확인
   if(checkuserName()) {
       const {data} = await req_checkUserName(userName.value.inputValue)  
       userName.value.isValid = !data
       if(!data) {
          userName.value.errorMsg = errMsg.fail
       }else {
          userName.value.errorMsg = errMsg.success
       }
   } 
   inputValid.value = userName.value.isValid
}

</script>

<style scoped>
.submit {
    width:100%;
    border: none;
    padding: 10px 0px;
    margin-top: 20px;
    color:white;
    background: #000000;
}
.submit:hover,
.submit:active {
    color:white;
    background: #fbb710;
}
.error-msg {
    color: red;
    font-size: 12px;
}
.success-msg {
    color: blue;
    font-size: 12px;
}
</style>