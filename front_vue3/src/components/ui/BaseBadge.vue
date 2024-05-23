<template>
    <span :class="['badge', mode]">
        <slot></slot>
    </span>
</template>

<script setup lang="ts">
import { defineProps } from "vue";
defineProps(['title', 'mode'])
</script>

<style scoped>
.badge {
    font-size: 13px;
    color: #959595;
    font-weight: 400;
    cursor: pointer;
}
</style>