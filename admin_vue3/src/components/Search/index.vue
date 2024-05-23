<template>
  <div class="container">
      <el-form :inline="true" :model="searchParams" class="demo-form-inline" label-width="80px">
        <el-form-item label="price by">
          <el-col :span="11">
            <el-input
              v-model="searchParams.min"
              type="number"
              placeholder="0"
              style="width: 100%"
            />
          </el-col>
          <el-col :span="2" class="text-center">
            <span class="text-gray-500">-</span>
          </el-col>
          <el-col :span="11">
            <el-input
              v-model="searchParams.max"
              type="number"
              placeholder="100"
              style="width: 100%"
            />
          </el-col>
        </el-form-item>
        <el-form-item>
           <el-input v-model="searchParams.keyword" placeholder="Please input">
              <template #append><el-button icon="Search" @click="onSubmit"></el-button></template>
            </el-input>
        </el-form-item>
      </el-form>
  </div>
</template>

<script setup lang="ts">
let $emit = defineEmits(['update:searchParams'])
let props = defineProps(['searchParams'])

const onSubmit = () => {
  $emit('update:searchParams', props.searchParams)
}
</script>

<style scoped>
.container {
  display: flex;
  justify-content: flex-end;
}
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
</style>
