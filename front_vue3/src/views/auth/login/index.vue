<template>
    <div id="loginBox">
            <!-- 로그인 페이지 타이틀 -->
            <div id="loginBoxTitle">Uno984 Login</div>
            <!-- 아이디, 비번, 버튼 박스 -->
            <div id="inputBox">
            <div class="input-form-box">
                <span>아이디 </span>
                <input type="text" class="form-control" v-model="userName.inputValue" @blur="checkuserName">
            </div>
                 <span class="error-msg" v-show="userName.isValid"> {{userName.errorMsg}} </span>
            <div class="input-form-box">
                <span>비밀번호 </span>
                <input type="password" class="form-control" v-model="password.inputValue" @blur="checkPassword">   
            </div>
                <span class="error-msg" v-show="password.isValid"> {{password.errorMsg}} </span>
            <div class="button-login-box" >
                <base-button @click.prevent="login"  mode="sigBtn" style="color:white">로그인</base-button>
                <base-button link mode="sigBtn" :to="{path: '/sigin'}">회원가입</base-button>
            </div>
            </div>
        </div>
</template>

<script setup lang="ts">
import { ref, watch, nextTick } from 'vue'
import { useValidation } from '@/hooks/useValidation'
import { ElMessage } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
import useUserStore from '@/stores/modules/user'

let isValid = ref<boolean>(false) // 로그인 input 유효성 검사
let userStore = useUserStore() // 유저 스토어
let $route = useRoute() // path
let $router = useRouter()
const { password, userName, checkPassword, checkuserName, enteredInput, inputValid } = useValidation()

// 로그인
const login = async() => {
    enteredInput(userName.value, password.value)
   if(inputValid.value){
    ElMessage({
      type:'error',
      message: '아이디, 비밀번호 입력하세요'
    })
    return false
   }
   // 로그인
   const userParam = {
      userName: userName.value.inputValue,
      password: password.value.inputValue
    }

  try {
    await userStore.userLogin(userParam)
    nextTick(()=> {
      userStore.getUserInfo()
      // 성공시 화면 이동 ---> 쿼리있는지 확인
      let toPath = $route.query.redirect || '/'
      $router.push(toPath)
    })
    
  } catch (error) {
    ElMessage({
      type: 'error',
      message:(error as Error).message
    })
    userName.value.inputValue = ''
    password.value.inputValue = ''
  }  
  


   
  
}

</script>

<style scoped>
#loginBox {
  width: 300px;
  margin-left: 200px;
  text-align: center;
  margin-top: 200px;;
  background-color: #ffffff;
}
.input-form-box {
  border: 0px solid #ff0000;
  display: flex;
  margin-bottom: 5px;
}
.input-form-box > span {
  display: block;
  text-align: left;
  padding-top: 5px;
  min-width: 65px;
}
.button-login-box {
  margin: 10px 0;
}
#loginBoxTitle {
  color:#000000;
  font-weight: bold;
  font-size: 32px;
  text-transform: uppercase;
  padding: 5px;
  margin-bottom: 20px;
  background: linear-gradient(to right, #270a09, #8ca6ce);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
#inputBox {
  margin: 10px;
}

#inputBox button {
  padding: 3px 5px;
}

.error-msg {
    color: red;
    font-size: 12px;
}
</style>