<template>
          <!-- Header Area Start -->
        <header class="header-area clearfix">
            <!-- Close Icon -->
            <div class="nav-close">
                <i class="fa fa-close" aria-hidden="true"></i>
            </div>
            <!-- Logo -->
            <div class="logo">
                <base-button link :to="{path:'/home'}"><img src="@/assets/core-img/unologo.png"></base-button>
            </div>
            <!-- Amado Nav -->
            <nav class="amado-nav">
                <ul>
                   <template v-for="item in menuList" :key="item.path">
                       <template v-if="item.meta.type == 'menu' && item.children.length > 0">
                          <template v-for="i in item.children" :key="i.path">
                               <li v-if="!i.meta.hidden"><base-button link :to="{path:`${i.path}`}">{{i.meta.title}}</base-button></li>
                          </template> 
                       </template>
                   </template>
                </ul>
            </nav>
            <!-- Button Group -->
            <div class="amado-btn-group mt-30 mb-100">
                <a href="#" class="btn amado-btn mb-15">%Discount%</a>
                <a href="#" class="btn amado-btn active">New this week</a>
            </div>
            <!-- Cart Menu -->
            <div class="cart-fav-search mb-100" v-if="!userStore.token">
                <base-button link :to="{path:'/sigin'}" class="fav-nav"> sign in</base-button>
                <base-button  link :to="{path:'/login'}" class="search-nav"> Login</base-button>
            </div>
                <div class="cart-fav-search mb-100" v-else>
                <base-button link :to="{path:'/cart'}" class="cart-nav"> Cart <span style="color: red">({{cartNum}} )</span></base-button>
                <a @click="logout">logout</a>
            </div>
            <!-- Social Button -->
            <div class="social-info d-flex justify-content-between">
                <a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-instagram" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
            </div>
        </header>
        <!-- Header Area End -->
</template>

<script setup lang="ts">
import useUserStore from '@/stores/modules/user'
import useCartStore from '@/stores/modules/cart'
import { defineProps } from "vue"
import { nextTick, watch, onMounted, ref, computed } from 'vue'
import { useRouter} from 'vue-router'

defineProps(['menuList'])
let userStore = useUserStore()
let cartStore = useCartStore()
let $router = useRouter()
let cartTotal = ref<numbre>(0)

const cartNum = computed(()=> {
    return cartStore.total || 0
})

watch(()=>userStore.userInfo, (newValue)=>{
  if(newValue.id){
    cartStore.getCartTotal(newValue.id)
  }
})

const logout = () => {
   userStore.userLogout()
   nextTick(()=> {
       $router.replace('/home')
   })
}
</script>

<style scoped>
.menuBtn {
    color: red;
}
</style>