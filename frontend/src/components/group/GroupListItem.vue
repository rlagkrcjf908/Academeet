<template>
  <div
    style="
      border-bottom: 1px solid rgba(217, 217, 217, 1);
      display: flex;
      align-self: start;
    "
  >
    <h3 class="group-list-item-p" @click="getUserList(props.group?.id)">
      {{ props.group?.name }}
    </h3>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  group: Object,
});

function getUserList(groupId) {
  router.push({ name: "groupUser", params: { groupId } });
}
</script>

<style>
.group-list-item-p:hover {
  cursor: pointer;
  color: rgba(97, 178, 153, 1);
  text-shadow: 2px 2px 2px white;
}
</style>
