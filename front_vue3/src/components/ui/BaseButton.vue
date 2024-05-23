<template>
    <a v-if="!link" :class="mode">
        <slot></slot>
    </a>
    <router-link v-else :to="to" :class="mode">
        <slot></slot>
    </router-link>
</template>

<script setup lang="ts">
import { withDefaults, defineProps} from 'vue'
interface Props {
    mode?: string,
    link?: boolean,
    to?: object 
}
const props = withDefaults(defineProps<Props>(), {
    mode: null,
    link: false,
    to: {
      path: '/home'
    } 
})
</script>

<style lang="scss" scoped>
// a {
//     font-size: 12px;
//     text-transform: uppercase;
//     position: relative;
//     z-index: 1;
//     padding: 20px 0;
//     display: block;
//     line-height: 0.9;
//     color: #131212;
//     font-weight: normal;
// }
.flat {
    text-align: center;
    display: inline-block;
    min-width: 160px;
    height: 55px;
    color: #ffffff;
    border: none;
    border-radius: 0;
    font-size: 18px;
    line-height: 56px;
    background-color: #020202;
    font-weight: 400;
    margin:5px 5px;
}
.flat:hover,
.flat:active {
    background-color: #fbb710;
}
.outline:hover,
.outline:active {
    color: #fbb710;
    border: 1;
}
.outline {
    border: 0;
    background-color: transparent;
    text-transform: capitalize;
    font-size: 16px;
    padding: 15px 0 15px 20px;
    display: block;
    font-weight: 400;
    color: #959595;
}

.w-100 {
    width: 100% !important;
}
.sigBtn{
    text-align: center;
    display: inline-block;
    min-width: 280px;
    height: 30px;
    color: #ffffff;
    border: none;
    border-radius: 0;
    font-size: 14px;
    background-color: #020202;
    font-weight: 400;
    margin:5px 0px;
    padding:3px 5px;
}
.sigBtn:hover,
.sigBtn:active {
    background-color: #fbb710;
}
</style>