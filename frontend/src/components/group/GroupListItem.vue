<template>
  <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
      <p @click="getGroupInfo(props.group?.id)">
        {{ props.group?.name }}
        {{ props.group?.id }}
      </p>
  </div>
</template>

<script setup>

import { defineProps } from "vue";
import { useStore } from 'vuex'
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useStore()
const props = defineProps({
  group: String
})

const getGroupInfo = async (groupId) => {
  await store.dispatch("groupStore/requestGroupAction", groupId);
  router.push({ name: "groupUser", params: { groupId } });
}

</script>
<style>
  
</style>