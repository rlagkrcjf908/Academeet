<template>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>No.</th>
          <th>제목</th>
          <th>날짜</th>
          <th>출석률</th>
          <th v-if="hostId == userId">수정</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <!-- 호스트 유저 (수정가능하게 하기) -->
      <tbody v-if="hostId == userId && attdUserList.length > 0">
        <tr v-for="(item, index) in attdUserList" :key="index">
          <td>{{ item.meetId }}</td>
          <td>{{ item.title }}</td>
          <td>{{ item.date }}</td>
          <td style="color: blue" v-if="item.attendance >= 70">
            <el-input v-model="item.attendance"></el-input>
          </td>
          <td v-else-if="item.attendance == 0">정보없음</td>
          <td style="color: red" v-else>
            <el-input v-model="item.attendance"></el-input>
          </td>
          <td>
            <el-button
              type="success"
              @click="saveAttdList(item)"
              size="small"
              v-if="hostId == userId"
              >저장하기</el-button
            >
          </td>
        </tr>
      </tbody>
      <!-- 일반 유저 -->
      <tbody v-else-if="attdUserListLength > 0">
        <tr v-for="(item, index) in attdUserList" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ item.title }}</td>
          <td>{{ item.date }}</td>
          <td style="color: blue" v-if="item.attendance >= 70">
            {{ item.attendance }}
          </td>
          <td v-else-if="item.attendance == 0">정보없음</td>
          <td style="color: red" v-else>{{ item.attendance }}</td>
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
import { requestAttdUser, attdUserUpdate } from "@/common/api/groupAPI";
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { useStore } from "vuex";

const route = useRoute();
const store = useStore();

const groupId = ref(route.params.groupId);
const selectUserId = ref(route.params.selectUserId); //상세보기 선택된 유저
const userId = store.state.accountStore.userId;
const hostId = ref(route.params.hostId);
const attdUserList = ref([]);
const meetId = ref(); //meetid로 받음
const attendance = ref(); //변하는 출석률 값
var attdUserListLength = 0;

onMounted(async () => {
  const res = await requestAttdUser(groupId.value, selectUserId.value);

  const datas = res.data;

  const list = datas.map((item) => {
    return {
      title: item.title,
      date: item.date.substr(0, 10),
      attendance: item.attendance,
      meetId: item.meetId,
    };
  });
  attdUserList.value = list;
  attdUserListLength = attdUserList.value.length;
});

// 수정요청함수
async function modifyAttdList(payload) {
  try {
    await attdUserUpdate(
      groupId.value,
      selectUserId.value,
      JSON.stringify(payload)
      // console.log("modifyAttdList 성공")
    );
  } catch (error) {
    console.log("modifyAttdList Error", error);
    console.log("modifyAttdList 실패");
  }
}

//저장하기 버튼을 눌렀을 때 실행
const saveAttdList = (item) => {
  if (0 <= item.attendance && item.attendance <= 100) {
    const payload = {
      //미팅 PK를 받고
      meetId: item.meetId,
      attendance: item.attendance,
    };
    modifyAttdList(payload);
  } else {
    alert("0~100까지만 입력이 가능합니다./n 다시 입력해주세요.");
  }
};
</script>

<style scoped>
.detail-btn {
  width: 140px;
  height: 30px;
  font-size: 15px;
}
tr:hover {
  background-color: rgba(97, 178, 153, 0.2);
  font-weight: 700;
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
  font-weight: 700;
  text-transform: uppercase;
  /* background-color: #94d82d; */
  background-color: rgba(97, 178, 153, 1);
}
td {
  padding: 15px;
  text-align: center;
  vertical-align: middle;
  font-weight: 700;
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
</style>
