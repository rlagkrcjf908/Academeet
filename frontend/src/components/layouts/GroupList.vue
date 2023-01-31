<template>
    <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
        <p>{{ props.group }}</p>
    </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
    group: String
})

</script>
<style lang="">
    
</style>