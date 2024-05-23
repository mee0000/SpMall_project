<template>
    <div class="logo" >
       <img src="/unologo-1.png" alt="logo" v-if="setting.logoHidder">
       <p>{{setting.title}}</p>
    </div>
</template>

<script setup lang="ts">
import setting from '@/setting'
</script>
<script lang="ts">
export default {
    name: "Logo"
}
</script>
<style lang="scss" scoped>
.logo {
    width: 100%;
    height: $base-menu-logo-height;
    color: white;
    display: flex;
    align-items: center;
    padding: 20px;
    img {

        height: 40px;
    }
    p {
        font-size: $base-logo-title-fontSize;
        margin-left: 10px;
    }
}
</style>