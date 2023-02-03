<template>
    <div>
        <p style="color: rgba(97, 178, 153, 1);">그룹 멤버 ({{ members.length }})</p>
        <div style="border: 1px solid black; padding: 40px;">
            <GroupUserItem v-for="member in members" :key="member" :member="member" style="border-bottom: 1px solid rgba(217, 217, 217, 1);"/>
        </div>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import GroupUserItem from '@/components/layouts/GroupUserItem'
const members = ref(['김싸피 kimsamsung999@naver.com', '김싸피 kimsamsung999@naver.com', '김싸피 kimsamsung999@naver.com', '김싸피 kimsamsung999@naver.com', '김싸피 kimsamsung999@naver.com', '김싸피 kimsamsung999@naver.com'])
</script>
<style>

</style>
