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
import { onMounted, ref } from 'vue';
import { useStore } from 'vuex'

const store = useStore()
const members = ref()
onMounted (async () => {
    await store.dispatch('groupStore/requestUserListAction', store.state.accountStore.id)
    members.value = store.state.groupStore.groupUserList  
})
    
</script>

<style>

</style>
