import { ref, reactive, watch } from 'vue'

export function useValidation() {
    type InputType = {
        isValid: Boolean,
        inputValue: string,
        errorMsg: string     
    }
    //유효성검사 userName
    let userName = ref({isValid:false, inputValue:'', errorMsg:''})
    let password = ref({ isValid: false, inputValue: '', errorMsg: '' })
    let phoneNumber = ref({isValid:false, inputValue:''})
    let inputValid = ref<boolean>(false)
    let errMsg = reactive(
       { 
        idInvalid: "6~20자의 영문 소문자와 숫자만 사용 가능합니다",
        pwInvalid: " 8~20자의 영문, 숫자, 특수문자를 모두 포함",   
        success: "사용 가능한 아이디입니다",
        fail: "사용할 수 없는 아이디입니다"
        
    }
    )
watch([()=>userName.value.inputValue,()=> password.value.inputValue],([idValue],[pwValue])=> {
            if(idValue.length> 0) {
                userName.value.isValid = false
                userName.value.errorMsg = ''
            }
            if(pwValue.length > 0) {
                password.value.isValid = false
                password.value.errorMsg = ''
            }
            inputValid.value = false
})
    // userName 유일한지 확인하기
const checkuserName = () => {
   // 유효성검사
   const idRegExp = /^[a-zA-Z0-9]{6,20}$/ // 6~20자의 영문 소문자와 숫자
    if (userName.value.inputValue != '' && idRegExp.test(userName.value.inputValue)) {
       userName.value.isValid =false
       return true
    } else {
        userName.value.isValid = true
        userName.value.errorMsg = errMsg.idInvalid
        return false;
   }
   
}

// userPassword 길이 5개 이상
const checkPassword =()=> {
     const pwRegExp = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/
    // 비밀번호는 “8~20자의 영문, 숫자, 특수문자를 모두 포함” 조건
    if (password.value.inputValue.length > 8 && pwRegExp.test(password.value.inputValue)) {
       password.value.isValid = false
       return true
    } else {
        password.value.isValid = true
        password.value.errorMsg = errMsg.pwInvalid
        return false
    }
      
}
    
//checkPhoneNumber
const checkPhoneNumber = () => {
     console.log(phoneNumber.value.inputValue)
     const regExp_tel1 = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/
     console.log(regExp_tel1.test(phoneNumber.value.inputValue), 'phonNumber')
        if(phoneNumber.value.inputValue.length > 0 && regExp_tel1.test(phoneNumber.value.inputValue)){
            phoneNumber.value.isValid = false
        } else {
            phoneNumber.value.isValid = true
        }
}

const enteredInput = (userName:InputType, password:InputType, phoneNumber?:InputType ) => {
    inputValid.value = false
    if(userName.inputValue == '') {
        inputValid.value = true
        userName.isValid = true 
    }
    if(password.inputValue == '') {
        inputValid.value = true
        password.isValid = true 
    }
    if(phoneNumber?.inputValue == '') {
        inputValid.value = true
        phoneNumber.isValid = true 
    }


}
    
    return {
        checkuserName,
        checkPassword,
        userName,
        password,
        errMsg,
        phoneNumber,
        enteredInput,
        checkPhoneNumber,
        inputValid
    }
}