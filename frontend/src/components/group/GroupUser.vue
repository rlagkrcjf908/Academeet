<template>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>No.</th>
          <th>프로필</th>
          <th>이름</th>
          <th>이메일</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody>
        <tr v-for="(item, index) in members" :key="index">
          <td>{{ index + 1 }}</td>
          <td>
            <img
              :src="'http://i8d108.p.ssafy.io/img/' + item.profile"
              class="user-img"
            />
          </td>
          <td>{{ item.name }}</td>
          <td>{{ item.email }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { onMounted, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { requestUserList } from "@/common/api/groupAPI.js";

const route = useRoute();
const store = useStore();
const members = ref();
const userId = store.state.accountStore.userId;
const groupId = ref(route.params.groupId);

async function getUserList() {
  const res = await requestUserList(groupId.value);
  members.value = res.data;
}

onMounted(() => {
  getUserList();
});
</script>

<style>
h1 {
  font-size: 30px;
  /* color: #fff; */
  text-transform: uppercase;
  font-weight: 400;
  text-align: center;
  margin-bottom: 15px;
}

.user-img {
  width: 50px;
  height: 50px;
  border-radius: 100%;
}
</style>
