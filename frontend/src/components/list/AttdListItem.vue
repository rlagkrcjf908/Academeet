<template>
    <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
        <p>{{ props.memberAttd }}</p>
        <!-- <div>
            {{ props.memberAttd.profile }}
        </div> -->
        <span>{{ props.memberAttd.userId }}</span>
        <span>{{ props.memberAttd.name }}</span>
        <span>{{ props.memberAttd.allAtt }}</span>

    </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
    memberAttd: String
})

</script>
<style lang="">
    
</style>