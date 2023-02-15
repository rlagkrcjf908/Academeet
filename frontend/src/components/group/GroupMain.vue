<template>
  <div class="groupMain-div">
    <div>
      <p class="group-main-p" v-if="groupLen == 0">속한 그룹이 없습니다.</p>
      <p class="group-main-p">그룹을 만들어 보세요.</p>
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
.groupMain-div {
  margin: auto;
  padding-bottom: 180px;
}

.group-main-p {
  font-size: large;
}
</style>

