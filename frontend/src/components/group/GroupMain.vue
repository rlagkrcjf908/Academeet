<template>
  <div class="groupMain-div">
    <div v-if="groupLen" class="group-main">
      <h2 style="color: rgba(97, 178, 153, 1)">
        - 내가 속한 그룹 ({{ groupLen }}) -
      </h2>
      <group-list />
    </div>
    <div v-else>
      <p>속한 그룹이 없습니다.</p>
      <p>그룹을 만들어 보세요.</p>
      <el-button type="success" round @click="$router.push('/group/create')"
        >그룹생성</el-button
      >
    </div>
  </div>
</template>

<script setup>
import GroupList from "@/components/group/GroupList";
import { useStore } from "vuex";
import { computed } from "vue";

const store = useStore();
const groupLen = computed(() => store.getters["groupStore/getGroupListLength"]);
</script>

<style>
/* .group-main {
  margin-top: 28px;
  margin-left: 100px;
} */
.groupMain-div {
  margin-top: 5vh;
  margin-left: 40vw;
}
</style>

