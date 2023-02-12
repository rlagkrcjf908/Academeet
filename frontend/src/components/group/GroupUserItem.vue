<template>
    <div>
        <img :src = "'http://i8d108.p.ssafy.io/img/'+props.member.profile"  style="height:100px;">
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