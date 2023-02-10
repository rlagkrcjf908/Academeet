<template>
    <div>
        <p style="color: rgba(97, 178, 153, 1);">그룹 멤버 ({{ members.length }})</p>
        <div style="border: 1px solid black; padding: 40px;">
            <GroupUserItem v-for="member in members" :key="member" :member="member" style="border-bottom: 1px solid rgba(217, 217, 217, 1);"/>
        </div>
    </div>
</template>
<script setup>
import GroupUserItem from '@/components/group/GroupUserItem'
import { onMounted, ref, watch } from "vue"
import { useStore } from 'vuex'
import { useRoute } from "vue-router";
import { requestUserList } from '@/common/api/groupAPI.js'

const route = useRoute();
const store = useStore()
const members = ref()
const userId = store.state.accountStore.userId
const groupId = ref(route.params.groupId)
console.log('userId',userId, 'groupId',groupId.value)

async function getUserList() {
    const res = await requestUserList(groupId.value)
    console.log('resdata',res.data)
    members.value = res.data
}

onMounted (() => {
    getUserList()
})

</script>

<style>

</style>
