<template>
    <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
        <span>{{ props.member.name }}</span> |
        <span>{{ props.member.email }}</span>
    </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
    member: Object
})
</script>

<style>
</style>