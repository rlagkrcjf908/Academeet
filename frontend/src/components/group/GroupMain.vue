<template>
    <div>
        <div v-if="groupLen" class="group-main">
            <p style="color: rgba(97, 178, 153, 1)">내가 속한 그룹 ({{ groupLen }})</p>
            <group-list/>
        </div>
        <div v-else>
            <p>속한 그룹이 없습니다.</p>
            <p>그룹을 만들어 보세요.</p>
            {{ groupLen }}
            <el-button type="success" round @click="$router.push('/group/create')">그룹생성</el-button>
        </div>

    </div>
</template>
<script setup>
import GroupList from '@/components/group/GroupList'
import { useStore } from 'vuex'
import { computed } from "vue"

const store = useStore()
const groupLen = computed(() => store.getters.getGroupListLength)
console.log(store.getters.getGroupList)
console.log(groupLen)
</script>
<style>

</style>

