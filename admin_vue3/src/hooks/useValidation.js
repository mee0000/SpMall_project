import {ref, reactive} from 'vue'
export function validation() {
        // 유효성검사함수--> username
    const validatorUserName = (rule, value, callback) => {
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
    const validatorPassword = (rule, value, callback) => {
        if (value.length >= 6) {
        callback();
        } else {
        callback(new Error('비밀번호 6자 이상으로 늘리세요'))
        }
    }
   // 유효성검사
    const rules = reactive({
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
    })
   
    return {
        rules
    }
}