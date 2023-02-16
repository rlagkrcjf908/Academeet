<template>
  <div>
    <h2 style="color: rgba(97, 178, 153, 1)">- 회의 리스트 -</h2>
  </div>
  <div class="list-view">
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
            <td v-if="!item.isMeetingEnd">{{ item.date }}</td>
            <td v-if="!item.isMeetingEnd">
              {{ item.startTime }} ~ {{ item.endTime }}
            </td>
            <td v-if="!item.isMeetingEnd">{{ item.meetTitle }}</td>
            <td v-if="!item.isMeetingEnd">{{ item.groupTitle }}</td>
            <el-button
              class="meetEnterBtn"
              @click="joinMeet(item)"
              type="success"
              plain
              v-if="!item.isMeetingEnd"
              >회의시작
            </el-button>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <el-button
    class="list-view-home-btn"
    type="success"
    round
    @click="$router.push('/')"
    >HOME</el-button
  >
</template>

<script setup>
import { requestMeetingList } from "@/common/api/meetingAPI";
import { sortedLastIndex } from "lodash";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const meetList = ref([]);
const meetTitle = ref();
const userId = JSON.parse(localStorage.getItem("userInfo")).id;

onMounted(async () => {
  const res = await requestMeetingList(userId);
  const datas = res.data;
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

  const isMeetingEnd = function (timeData) {
    const currDate = new Date();
    const meetDate = new Date(timeData);
    if (meetDate - currDate < 0) {
      return true;
    } else {
      return false;
    }
  };
  const list = sortedDate(datas).map((item) => {
    const meetingEndTime = `${item.date.substr(0, 10)} ${item.endTime}`;
    return {
      groupTitle: item.groupTitle,
      meetTitle: item.meetTitle,
      date: item.date.substr(0, 10),
      startTime: item.startTime,
      endTime: item.endTime,
      meetId: item.meetId,
      ownerId: item.ownerId,
      isMeetingEnd: isMeetingEnd(meetingEndTime),
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
    ownerId: item.ownerId,
  };
  sessionStorage.setItem("meetInfo", JSON.stringify(meetInfo));
  router.push({ name: "meeting" });
};
</script>

<style>
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
  height: 50vh;
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
  background-color: rgba(97, 178, 153, 1);
}
td {
  padding: 15px;
  text-align: center;
  vertical-align: middle;
  font-weight: 700;
  font-size: 18px;
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

.list-view {
  padding: 2vw;
  width: 80vw;
  height: 50vh;
  margin: auto;
  margin-bottom: 2.5vw;
}

.list-view-home-btn {
  margin-top: 5vw;
}
</style>
