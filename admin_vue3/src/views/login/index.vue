<template>
    <div class="login_container">
            <el-row>
                <!-- :xs screen < 768 일경우 사라짐-->
                <el-col :span="12" :xs="0"></el-col> 
                <!-- :xs screen < 768 일경우 전체 차지함-->
                <el-col :span="12" :xs="24">
                    <el-form class="login_form" :model="loginForm" :rules="rules" ref="inputValidate">
                        <h1>Hello</h1>
                        <h2>admin Login</h2>
                        <el-form-item prop="username">
                            <el-input :prefix-icon="User" v-model="loginForm.username"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input :prefix-icon="Lock" type="password" v-model="loginForm.password" show-password ></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button class="login_btn" :loading="isLoading" size="default" type="primary" @click="login" :disabled="isLoading">로그인</el-button>
                        </el-form-item>
                        
                    </el-form>
                </el-col>
            </el-row>
    </div>
</template>

<script setup lang="ts">
import { User, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import useUserStore from '@/store/modules/user'
import { useRouter, useRoute } from 'vue-router'
import { ElNotification } from 'element-plus'
import { getTime } from '@/utils/time'
// user관련 store
let useStore = useUserStore()
// form 
let inputValidate = ref()
// loading
let isLoading = ref(false)
// 로그인후 시간
let time = getTime()
// loginForm의 username password 수집
let loginForm = reactive ({
    username: 'admin',
    password: '111111'
})
// 경로 이동
let $router = useRouter()
// url 주소및 쿼리 확인가능 
let $route = useRoute()
const login = async () => {
    // 유효성 체크 완료시 로그인 진행 promis로 반환 await 로 판단 reject 로 되면 reren false
    await inputValidate.value.validate()
    // loading 시작
    isLoading.value = true
    // 로그인 성공/실패
    try {      
        await useStore.userLogin(loginForm)
        // 로그인후 route에 쿼리가 있는지 확인 -> 있으면 해당 쿼리패스로 이동 없으면 "/" 로 이동
        let redirect = $route.query.redirect
        console.log(reactive, '======redirect')
        // 성공시 -> router 변경 메인페이지이동
        $router.push({path: redirect || '/'})
        // alert
        ElNotification({
            type: 'success',
            message: '로그인 성공',
            title: `HI, welcome ${time}`
        })
    } catch (error) {
        // 실패시
        ElNotification({
            type: 'error',
            message:( error as Error).message
        })
    } finally {
        isLoading.value = false
    }
}
// 유효성검사함수--> username
const validatorUserName = (_: any, value:any, callback: any) => {
    // rule: 校验规则对像
    // value: 即为表单元素文本内容
    // callback: 如果符合条件callback放行通过即为 如果不符合条件callBack方法， 注入错误提示信息
    if (value.length >= 5) {
      callback();
    } else {
      callback(new Error('아이디 5자 이상으로 늘리세요'))
    }
}
// 유효성검사 함수
// input 유효성 --> password
const validatorPassword = (_: any, value:any, callback: any) => {
    if (value.length >= 6) {
      callback();
    } else {
      callback(new Error('비밀번호 6자 이상으로 늘리세요'))
    }
}
const rules = {
   // required: true (유효성 체크여부) ==> 꼭입력할지 확인
   // message: 'Please input Activity name' (에러 발생시 메세지 노출)
   // trigger: 'blur' -> input 에 대한 이벤트 발생시 ex) 'change' -> input 내용 변경시
   // min : input 최소길이 max: 최장 길이 제한 
    username: [
        // {required: true, min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'change' }
        // 自定义校验
        { trigger: 'change', validator: validatorUserName }
        ],
    password: [
        // {required: true, min: 6, message: 'Length should be > 6', trigger: 'change' }
        { trigger: 'change', validator: validatorPassword }
        ]
}

</script>

<style scoped lang="scss">
.login_container{
  width: 100%;
  height: 100vh;
  background: url('@/assets/images/background.jpg') no-repeat;
  background-size: cover;

  .login_form {
    position: relative;
    width: 80%;
    top: 30vh;
    background: url('@/assets/images/login_form.png') no-repeat;
    background-size: cover;
    padding: 40px;
    h1 {
        color: white;
        font-size: 40px;
    }
    h2 {
        color: white;
        font-size: 20px;
        margin: 20px 0px;
    }
    .login_btn {
        width: 100%;
    }
  }
}
</style>