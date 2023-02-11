<template>
  <section>
    <div>
      <!-- <MeetSide/> -->
    </div>
    <div class="tbl-header">
      <table cellpadding="0" cellspacing="0" border="0">
        <thead>
          <tr>
            <th>날짜</th>
            <th>회의 시간</th>
            <th>회의 이름</th>
            <th>그룹 이름</th>
            <th>회의 참여</th>
          </tr>
        </thead>
      </table>
    </div>
    <div class="tbl-content">
      <table cellpadding="0" cellspacing="0" border="0">
        <tbody>
          <tr v-for="(item, index) in meetList" :key="index">
            <td>{{ item.date }}</td>
            <td>
              {{ item.startTime }} ~ {{ item.endTime }}
            </td>
            <td>{{ item.meetTitle }}</td>
            <td>{{ item.groupTitle }}</td>
            <el-button
              class="meetEnterBtn"
              @click="joinMeet(item)"
              type="success"
              plain
              >회의시작
            </el-button>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { requestMeetingList } from "@/common/api/meetingAPI";
import { sortedLastIndex } from "lodash";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
// import MeetSide from "@/components/layouts/MeetSide.vue";

const router = useRouter();
const route = useRoute();

// const groupId = ref(route.params.groupId);
// const hostId = ref(route.params.hostId);
// const selectUserId = ref(); //상세 출석 볼 유저
const meetList = ref([]);
const meetTitle = ref();
const userId = JSON.parse(localStorage.getItem("userInfo")).id
// const routeToUser = (item) => {
//   console.log("item.userId: ", item.userId);
//   selectUserId.value = item.userId;
//   router.push({
//     name: "attdUser",
//     params: {
//       selectUserId: selectUserId.value,
//       groupId: groupId.value,
//       hostId: hostId.value,
//     },
//   });
// };

onMounted(async () => {
  const res = await requestMeetingList(userId);
  console.log("전체  meet", res);
  const datas = res.data;
  console.log(datas)
  const sortedDate = (datas) => {
      const sorted_date = datas.sort(function (a, b) {
        // return new Date(a.date) - new Date(b.date).getTime();
        if (a.date < b.date) return -1;
        if (a.date > b.date) return 1;

        if (a.startTime < b.startTime) return -1;
        if (a.startTime > b.startTime) return 1;

        if (a.endTime < b.endTime) return -1;
        if (a.endTime > b.endTime) return 1;
      });
    return sorted_date;
  };
  console.log(sortedDate(datas))

  const list = sortedDate(datas).map((item) => {
    return {
      groupTitle: item.groupTitle,
      meetTitle: item.meetTitle,
      date: item.date,
      startTime: item.startTime,
      endTime: item.endTime,
      meetId: item.meetId,
    };
    
  });
  meetList.value = list;
});

const joinMeet = (item) => {
  const meetInfo = {
    meetId: item.meetId,
    meetTitle: item.meetTitle,
    userName: JSON.parse(localStorage.getItem("userInfo")).name,
    userId: JSON.parse(localStorage.getItem("userInfo")).id,
  }
  console.log(meetInfo);
  sessionStorage.setItem("meetInfo", JSON.stringify(meetInfo));
  router.push({ name: "meeting"})
}



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
h1 {
  font-size: 30px;
  /* color: #fff; */
  text-transform: uppercase;
  font-weight: 300;
  text-align: center;
  margin-bottom: 15px;
}
table {
  width: 100%;
  table-layout: fixed;
}
.tbl-header {
  background-color: rgba(255, 255, 255, 0.3);
}
.tbl-content {
  height: 400px;
  overflow-x: auto;
  margin-top: 0px;
  border: 1px solid rgba(255, 255, 255, 0.3);
}
th {
  padding: 20px 15px;
  text-align: center;
  font-size: 15px;
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
  font-size: 12px;
  /* color: #fff; */
  border-bottom: solid 1px rgba(255, 255, 255, 0.1);
}

section {
  margin: 50px;
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
  color: #f89898;
}
.attd-success {
  color: #95d475;
}
</style>