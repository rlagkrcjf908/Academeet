<template>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>No.</th>
          <th>이름</th>
          <th>전체 출석률</th>
          <th>상세 보기</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody v-if="attdUserListLength > 0">
        <tr v-for="(item, index) in attdUserList" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ item.name }}</td>
          <td class="attd-success" v-if="item.allAtt >= 70">
            {{ item.allAtt }}
          </td>
          <td v-else-if="item.allAtt == 'NaN'">정보없음</td>
          <td class="attd-fail" v-else>{{ item.allAtt }}</td>
          <el-button
            class="detail-btn"
            @click="routeToUser(item)"
            type="success"
            plain
            >{{ item.name }}:{{ item.userId }}</el-button
          >
        </tr>
      </tbody>
      <tbody v-else>
        <div style="font-size: 20px; margin-top: 250px">- 정보가 없습니다-</div>
      </tbody>
    </table>
  </div>
  <p hidden>{{ attdUserList }}</p>
</template>

<script setup>
import { requestAttdList } from "@/common/api/groupAPI";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const groupId = ref(route.params.groupId);
const hostId = ref(route.params.hostId);
const selectUserId = ref(); //상세 출석 볼 유저
const attdUserList = ref([]);
var attdUserListLength = 0;

const routeToUser = (item) => {
  selectUserId.value = item.userId;
  router.push({
    name: "attdUser",
    params: {
      selectUserId: selectUserId.value,
      groupId: groupId.value,
      hostId: hostId.value,
    },
  });
};

onMounted(async () => {
  const res = await requestAttdList(groupId.value);
  const datas = res.data;
  const list = datas.map((item) => {
    return {
      userId: item.userId,
      name: item.name,
      allAtt: item.allAtt,
    };
  });
  attdUserList.value = list;
  attdUserListLength = attdUserList.value.length;
});
</script>

<style>
.detail-btn {
  width: 140px;
  height: 30px;
  font-size: 15px;
}
tr:hover {
  background-color: rgba(97, 178, 153, 0.2);
  font-weight: bolder;
  /* color: #fdce7e; */
  color: rgba(97, 178, 153, 1);
}
table {
  width: 100%;
  table-layout: fixed;
}
.tbl-header {
  background-color: rgba(255, 255, 255, 0.3);
}
.tbl-content {
  height: 75vh;
  overflow-x: auto;
  margin-top: 0px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}
th {
  padding: 20px 15px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  font-weight: bolder;
  text-transform: uppercase;
  /* background-color: #94d82d; */
  background-color: rgba(97, 178, 153, 1);
}
td {
  padding: 15px;
  text-align: center;
  vertical-align: middle;
  font-weight: 300;
  font-size: 18px;
  /* color: #fff; */
  border-bottom: solid 1px rgba(255, 255, 255, 0.1);
}
/* for custom scrollbar for webkit browser  */

::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}

.attd-fail {
  color: red;
}
.attd-success {
  color: blue;
}
</style>
