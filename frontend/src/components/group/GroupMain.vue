<template>
    <div>
        <!-- <div v-if="groups.length !== 0" class="group-main">
            <p style="color: rgba(97, 178, 153, 1)">내가 속한 그룹 ({{ groups.length }})</p>
            <group-list/>
        </div>
        <div v-else>
            <p>속한 그룹이 없습니다.</p>
            <p>그룹을 만들어 보세요.</p>
            <el-button type="success" round @click="$router.push('/group/create')">그룹생성</el-button>
        </div> -->
        <group-list/>

    </div>
</template>
<script setup>
import GroupList from '@/components/group/GroupList'

</script>
<style scoped>

</style>

