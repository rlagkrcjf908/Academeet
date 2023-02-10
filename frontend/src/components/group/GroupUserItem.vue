<template>
        <span>{{ props.member.name }}</span> |
        <span>{{ props.member.email }}</span>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
    member: Object
})
</script>

<style>
</style>