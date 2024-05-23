<template>
   <table class="table table-bordered">
    <tr>
      <OrderTd width="5" label="#"/>
      <OrderTd width="15" label="skuImg"/>
      <OrderTd width="20" label="상품명"/>
      <OrderTd width="40" label="skuDec"/>
      <OrderTd width="10" label="qty"/>
      <OrderTd width="10" label="price"/>
    </tr>

    <tr class="order_tbody" v-for="(orderItem, index) in orderList" :key="orderItem.id">
      <OrderTd width="5" :label="index+1"/>
      <OrderTd width="10">
        <img :src="orderItem.skuInfo.skuDefaultImg" width="50px;">
      </OrderTd>
      <OrderTd width="20" :label="orderItem.skuInfo.spuName"/>
      <OrderTd width="40">
          <p>{{orderItem.skuInfo.skuName}}</p>
          <span v-for="item in orderItem.skuInfo.skuSaleAttrValueList" style="margin-right:2px;">{{`${item.saleAttrName}:${item.saleAttrValueName}`}}</span>
      </OrderTd>
      <OrderTd width="10" :label="orderItem.qty" />
      <OrderTd width="10" :label="orderItem.skuInfo.goods_price_min*orderItem.qty"/>
    </tr>

</table>
</template>

<script setup lang="ts">
import OrderTd from './OrderTd.vue'
import { defineProps } from 'vue'
const props = defineProps(['orderList'])


</script>

<style scoped>
.main-content-wrapper .cart-table-area table thead tr th {
  background: none;
}
.orderCencel {
  border:none;
  background:none;
  color:#959595;
}
.orderCencel:hover,
.orderCencel:active {
  color: #fbb710;
}
.order_tbody td {
  color: #959595;
}

table tr td {
  padding: 5px;
}
</style>