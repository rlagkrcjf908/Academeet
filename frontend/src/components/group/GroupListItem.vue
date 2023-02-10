<template>
  <div style="border-bottom: 1px solid rgba(217, 217, 217, 1); display: flex; align-self: start;">

      <p @click="getUserList(props.group?.id)">
        {{ props.group?.name }}
      </p>
  </div>
</template>

<script setup>

import { defineProps, onMounted, watch } from "vue";
import { useStore } from 'vuex'
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useStore();

const props = defineProps({
  group: Object
})

async function getUserList(groupId){
  await store.dispatch("groupStore/requestUserListAction", groupId);
  router.push({ name: "groupUser", params: { groupId } });
}

</script>

<style>
  
</style>