<template>
    <teleport to="body">
       <div v-if="show" @click="tryClose()" class="backdrop"></div>
       <transition name="dialog">
          <dialog open v-if="show">
             <header>
                <slot name="header">
                    <h2>{{title}}</h2>
                </slot>
             </header>
             <section>
                <slot></slot>
             </section>
             <!-- <menu v-if="!fixed">
                <slot name="actions">
                    <button @click="tryClose()">Close</button>
                </slot>
             </menu> -->
          </dialog>
       </transition>
    </teleport>
</template>

<script setup lang="ts">
import { defineProps, withDefaults, defineEmits } from "vue";
defineProps(['tryClose','show','title','fixed'])
</script>
<style scoped>
.backdrop {
    position: fixed;
    top: 0;
    left: 0;
    height: 100vh;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.75);
    z-index: 10;
    backdrop-filter:  blur(4px);
}
dialog {
    position: fixed;
    top:20vh;
    margin: 0 auto;
    max-width: 50rem;
    width: 70%;
    z-index: 100;
    overflow: hidden;
    border-radius: 1.2rem;
    border:2px solid yellow;
    border:0.3rem solid var(--purple-1);
}
header {
    background: var(--purple-2);
    padding: 1.6rem;
}
header h2 {
    color: var(--white-2);
    text-align: center;
}
section {
    padding: 1.6rem;
    margin:0;
}
menu {
    padding:1.6rem;
    display: flex;
    justify-content: flex-end;
    margin: 0;
}
.dialog-leave-to,
.dialog-enter-from {
    opacity: 0;
    transform: scale(0.8);
}
.dialog-enter-to,
.dialog-leave-from {
    opacity: 1;
    transform: scale(1);
}
.dialog-enter-active {
    transition: all 0.3s ease-out;
}
.dialog-leave-active {
    transition: all 250ms ease-in;
}
</style>