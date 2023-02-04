<template>
  <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">
      <p @click="getGroupInfo(props.group?.id)">
        {{ props.group?.name }}
      </p>
  </div>
</template>

<script setup>

import { defineProps } from "vue";
import { useStore } from 'vuex'

const store = useStore()
const props = defineProps({
  group: String
})

const getGroupInfo = (group_id) => {
  store.dispatch("requestGroupAction", group_id);
  router.push({ name: "groupUser", params: {  group_id } });
}

</script>
<style>
  
</style>