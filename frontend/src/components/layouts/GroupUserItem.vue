<template>
    <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
        <p>{{ props.member }}</p>
    </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
    member: String
})

</script>
<style lang="">
    
</style>