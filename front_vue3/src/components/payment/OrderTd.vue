<template>
    <td :style="`width:${width}%;text-align:center`" v-if="label">
        {{label}}
    </td>
    <td v-else>
        <slot></slot>
    </td>
</template>

<script setup lang="ts">
import {ref, defineProps} from 'vue'
defineProps(['width', 'label'])
</script>

<style scoped>

</style>