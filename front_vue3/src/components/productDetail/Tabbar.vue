<template>
               <div class="row">
                    <div class="col-12">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb mt-50">
                                <li class="breadcrumb-item"v-show="item.meta.title" v-for="(item, index) in $route.matched" :key="index">
                                    <base-button link :to="{path:`${item.path}`}"> {{item.meta.title}}</base-button>
                                </li>
                                <li class="breadcrumb-item active" aria-current="page">{{$route.query.title}}</li> 
                            </ol>
                        </nav>
                    </div>
                </div>
</template>

<script setup lang="ts">
import { useRoute} from 'vue-router'
let $Route = useRoute()

</script>

<style scoped>

</style>