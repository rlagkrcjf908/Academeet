<template>
    <div>
        <GroupListItem v-for="group in groups" :key="group.id" :group="group"/>
    </div>
</template>

<script setup>
import GroupListItem from '@/components/group/GroupListItem'
import { ref, onMounted, watch } from 'vue';
import { useStore } from 'vuex'

const store = useStore()
const userid = store.state.accountStore.userId
const groups = ref()

async function getGroupList() {
    await store.dispatch('groupStore/requestGroupListAction', userid)
    groups.value = store.state.groupStore.groupList  
}

watch(groups,getGroupList)

onMounted (() => {
    getGroupList()
})


</script>
<style scoped>

</style>+

