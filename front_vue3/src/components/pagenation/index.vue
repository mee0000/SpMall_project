<template>
    <div class="row">
                    <div class="col-12">
                        <!-- Pagination -->
                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-end mt-50">
                                <li class="page-item" v-show="props.pageNo != 1" @click="selectedPage(props.pageNo-1)"><base-button mode="page-link"> < </base-button></li>
                                <li v-for="p in pageTotal" class="page-item" :class="{active: pageNo == p}" @click="selectedPage(p)">
                                    <base-button mode="page-link">{{p}}</base-button>
                                </li>
                                 <li class="page-item"  v-show="props.pageNo != pageTotal && pageTotal > 1" @click="$emit('setPageNo', props.pageNo+1)"><base-button mode="page-link"> > </base-button></li>
                            </ul>
                        </nav>
                    </div>
                </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from "vue";

const props = defineProps(['pageNo', 'pageSize', 'total'])
const $emit = defineEmits(['setPageNo'])

const pageTotal = computed(()=> {
   console.log(props.total)
   return Math.ceil(props.total/props.pageSize)
})

const selectedPage = (p:number) => {
  $emit('setPageNo', p)
}

</script>

<style scoped>

</style>