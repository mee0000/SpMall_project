import axios from 'axios'
import { ElMessage } from 'element-plus'
import useUserStore from '@/store/modules/user'

// axios 
const request = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API,
    timeout: 5000
})

// 요청 인터셉터 추가하기
request.interceptors.request.use(function (config) {
    // userStore: token, 로그인후 헤더에 token추가해야함 
    let userStore = useUserStore()
    if(userStore.token) {
      config.headers.token = userStore.token
    }
    // 요청이 전달되기 전에 작업 수행
    return config;
  }, function (error) {
    // 요청 오류가 있는 작업 수행
    return Promise.reject(error);
  });

// 응답 인터셉터 추가하기
request.interceptors.response.use(function (response) {
    // 2xx 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
    // 응답 데이터가 있는 작업 수행
    return response.data;
  }, function (error) {
    // 2xx 외의 범위에 있는 상태 코드는 이 함수를 트리거 합니다.
    // 응답 오류가 있는 작업 수행
    // 변수 정의 --> 오류 메시지
    let message = '';
    // http 상태 
    let status = error.response.status
    switch (status) {
      case 401: 
        message = 'Token 유효지남'
        break;
      case 403: 
        message = '방문불가'
        break;
      case 404: 
        message = '요청주소 오류'
        break; 
      case 400: 
        message = '서버 오류'
        break;
      default: 
        message = '인터넷 오류'    
    }

    // 오류메시지
    ElMessage({
      type: 'error',
      message
    })
    return Promise.reject(error);
  })

  export default request