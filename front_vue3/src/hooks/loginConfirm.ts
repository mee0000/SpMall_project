import useUserStore from "@/stores/modules/user"
import Swal from "sweetalert2"
import { useRoute, useRouter} from 'vue-router'
export function checkUser() {
let userStore = useUserStore()
let $route = useRoute() // 페이지 주소및 쿼리내용
let $router = useRouter() //페이지이동시
  const  loginConfirm  = () => {
        if (!userStore.token) {
            Swal.fire({
                icon: 'info',
                title: '로그인 후 이용 가능합니다.',
                text: '로그인 하시겠습니까?',
                showCancelButton: true,
                confirmButtonText: '예',
                cancelButtonText: '아니오',
                confirmButtonColor: '#429f50',
                cancelButtonColor: '#d33',

            }).then(result => {
                if (result.isConfirmed) {
                    $router.push({path:'/login', query:{redirect: $route.fullPath}})
                } else if (result.isDismissed) {
                    console.log('아니요')
                }
            })
            return true
        } else {
            return false
        }
}
  return { loginConfirm }
    
}