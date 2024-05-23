import pinia from "@/store"
import useUserStore from "@/store/modules/user"
let userStore = useUserStore(pinia)
export const isHasBotton = (app: any) => {
    //获取对应的用户仓库
   //全局自定义指令： 实现按钮的权限
    app.directive('has', {
        //代表使用这个全局自定义指令的DOM ｜ 组件挂在完毕的时候会执行

        mounted(el: any, options: any) {
            // options --> btn... 들어오는 값이 포함되지 않으면 
            if(!userStore.buttons.includes(options.value)){
                el.parentNode.removeChild(el)
            }
        },
    })
}