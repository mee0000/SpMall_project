import{r,x as v,w as V}from"./index-HA-C2-6D.js";function g(){let l=r({isValid:!1,inputValue:"",errorMsg:""}),a=r({isValid:!1,inputValue:"",errorMsg:""}),u=r({isValid:!1,inputValue:""}),t=r(!1),n=v({idInvalid:"6~20자의 영문 소문자와 숫자만 사용 가능합니다",pwInvalid:" 8~20자의 영문, 숫자, 특수문자를 모두 포함",success:"사용 가능한 아이디입니다",fail:"사용할 수 없는 아이디입니다"});return V([()=>l.value.inputValue,()=>a.value.inputValue],([e],[i])=>{e.length>0&&(l.value.isValid=!1,l.value.errorMsg=""),i.length>0&&(a.value.isValid=!1,a.value.errorMsg=""),t.value=!1}),{checkuserName:()=>{const e=/^[a-zA-Z0-9]{6,20}$/;return l.value.inputValue!=""&&e.test(l.value.inputValue)?(l.value.isValid=!1,!0):(l.value.isValid=!0,l.value.errorMsg=n.idInvalid,!1)},checkPassword:()=>{const e=/^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;return a.value.inputValue.length>8&&e.test(a.value.inputValue)?(a.value.isValid=!1,!0):(a.value.isValid=!0,a.value.errorMsg=n.pwInvalid,!1)},userName:l,password:a,errMsg:n,phoneNumber:u,enteredInput:(e,i,s)=>{t.value=!1,e.inputValue==""&&(t.value=!0,e.isValid=!0),i.inputValue==""&&(t.value=!0,i.isValid=!0),(s==null?void 0:s.inputValue)==""&&(t.value=!0,s.isValid=!0)},checkPhoneNumber:()=>{console.log(u.value.inputValue);const e=/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;console.log(e.test(u.value.inputValue),"phonNumber"),u.value.inputValue.length>0&&e.test(u.value.inputValue)?u.value.isValid=!1:u.value.isValid=!0},inputValid:t}}export{g as u};
