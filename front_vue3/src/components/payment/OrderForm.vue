<template>
        <form>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" id="first_name" v-model="user.firstName" placeholder="First Name" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" id="last_name" v-model="user.lastName" placeholder="Last Name" required>
                                    </div>
                        
                                    <div class="col-md-6 mb-3">
                                        <input type="text" class="form-control" id="zipCode" v-model="user.zipCode" placeholder="Zip Code" >
                                    </div>
                                    <div class="col-12 mb-3">
                                        <input type="text" class="form-control mb-3" id="street_address" v-model="user.address" placeholder="Address" >
                                    </div>
                                    <div class="col-12 mb-3">
                                        <input type="text" class="form-control mb-3" id="number" v-model="user.phoneNumber" placeholder="phoneNumber">
                                    </div>
                                    <div class="col-12 mb-3">
                                        <textarea name="comment" class="form-control w-100" id="comment" cols="30" rows="10" placeholder="Leave a comment about your order"></textarea>
                                    </div>

                                    <div class="col-12">
                                        <div class="custom-control custom-checkbox d-block mb-2">
                                            <input type="checkbox" class="custom-control-input" id="customCheck2" checked>
                                            <label class="custom-control-label" for="customCheck2">Create an accout</label>
                                        </div>
                                        <div class="custom-control custom-checkbox d-block">
                                            <input type="checkbox" class="custom-control-input" id="customCheck3" checked>
                                            <label class="custom-control-label" for="customCheck3">Ship to a different address</label>
                                        </div>
                                    </div>
                                </div>
                            </form>
</template>
<script setup lang="ts">
import {ref, reactive, onMounted, watch, defineProps, defineEmits, nextTick} from 'vue'
import emitter from '@/utils/emitter'

let prop = defineProps(['user'])

let inputValid = ref<boolean>(false)

watch([()=>prop.user.firstName, ()=>prop.user.lastName, ()=>prop.user.address], ([newFirstName, newLastName, newAddress])=> {
  if(newFirstName != '' &&  newLastName != '' && newAddress != '') {
        emitter.emit('changeValid', {valid: true})
  }
}, {immediate:true})

</script>

<style scoped>

</style>