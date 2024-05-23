<template>
         <base-dialog v-bind="$attrs">
        <table class="table" v-show="skuInfo">
            <thead>
                <tr>
                <th scope="col">#</th>
                <th scope="col">SkuImg</th>
                <th scope="col">SkuName</th>
                <th scope="col">attr</th>
                <th scope="col">수량</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <th scope="row">1</th>
                <td><img :src="skuInfo.skuDefaultImg" width="50px" height="auto"></td>
                <td>{{skuInfo.skuName}}</td>
                <td>
                    <span v-for="item in skuInfo.skuSaleAttrValueList" style="margin-right:2px;">{{`${item.saleAttrName}:${item.saleAttrValueName}`}}</span>
                </td>
                <td>{{qty}}</td>
                </tr>
            </tbody>
        </table>
        <div style="float:right;margin:5px 0;">
            <base-button link :to="path" mode="flat">바로구매하기</base-button>
        </div>
        </base-dialog>
</template>

<script setup lang="ts">
import useUserStore from '@/stores/modules/user'
import { ref, defineProps, computed} from 'vue'
let userStore = useUserStore()
let props = defineProps(['skuInfo', 'qty'])
const path = computed(()=> {
    return {
            name:'payment',
            query: {
                mode: 'buyNow',
                skuId: props.skuInfo.id,
                qty:props.qty
            }
        }
})
</script>

<style scoped>

</style>