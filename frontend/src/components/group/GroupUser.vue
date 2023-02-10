<template>
    <div>
        <!-- <p style="color: rgba(97, 178, 153, 1);">그룹 멤버 ({{ members.length }})</p> -->
        <div style="border: 1px solid black; padding: 40px;">
            <GroupUserItem v-for="member in members" :key="member" :member="member" style="border-bottom: 1px solid rgba(217, 217, 217, 1);"/>
        </div>
    </div>
</template>
<script setup>
import GroupUserItem from '@/components/group/GroupUserItem'
import { onMounted, ref, watchEffect, watch } from 'vue';
import { useStore } from 'vuex'
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
const route = useRoute();
const router = useRouter();
const store = useStore()

const userId = store.state.accountStore.userId
const groupId = route.params.groupId
console.log('userId',userId, 'groupId',groupId)
const members = ref()

async function getUsers(){
    members.value = store.state.groupStore.groupUserList  
}

onMounted(()=>{
    getUsers()
})

watch(groupId,getUsers)

</script>

<style>

</style>
