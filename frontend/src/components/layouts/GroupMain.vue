<template>
    <div>
        <div v-if="groups.length !== 0" class="group-main">
            <p style="color: rgba(97, 178, 153, 1)">내가 속한 그룹 ({{ groups.length }})</p>
            <GroupList v-for="group in groups" :key="group" :group="group"/>
        </div>
        <div v-else>
            <p>속한 그룹이 없습니다.</p>
            <p>그룹을 만들어 보세요.</p>
            <el-button type="success" round @click="$router.push('/group/create')">그룹생성</el-button>
        </div>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import GroupList from '@/components/layouts/GroupList'
const groups = ref(['1반', '2반', '3', '4', '5', '6', '7', '8', '9'])
</script>
<style scoped>

</style>

