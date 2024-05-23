// layout 관련 store
import { defineStore } from "pinia"

let useLayoutSettingStore = defineStore('SettingStore', {
    state: () => {
        return {
            fold: false,// 좌측메뉴 열고 닫는 역할
            refresh: false // 새로 고침 tebbar에 버튼 있음
        }
    }
})

export default useLayoutSettingStore;