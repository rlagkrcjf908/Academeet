<template>
    <div>
        <GroupListItem v-for="group in groups" :key="group.id" :group="group"/>
    </div>
</template>

<script setup>
import GroupListItem from '@/components/group/GroupListItem'
import { onMounted, ref } from 'vue';
import { useStore } from 'vuex'

const store = useStore()
const groups = ref()
onMounted (async () => {
    await store.dispatch('groupStore/requestGroupListAction', store.state.accountStore.id)
    groups.value = store.state.groupStore.groupList  
})


</script>
<style scoped>

</style>

