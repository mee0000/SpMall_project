<template>
    <div class="col-md-6 mb-3">
        <input type="text" class="form-control" v-model="user.userId" placeholder="User ID">
        <span class="error-msg">错误提示信息</span>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";

defineProps(['mode', 'item', 'type'])
</script>

<style scoped>

</style>