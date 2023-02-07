<template>
  <section>
    <h2 style="color: rgba(97, 178, 153, 1)">
      참여 회의 ({{ attdUserList.length }})
    </h2>

    <div class="tbl-header">
      <table cellpadding="0" cellspacing="0" border="0">
        <thead>
          <tr>
            <th>No.</th>
            <th>제목</th>
            <th>날짜</th>
            <th>출석률</th>
          </tr>
        </thead>
      </table>
    </div>
    <div class="tbl-content">
      <table cellpadding="0" cellspacing="0" border="0">
        <tbody>
          <tr v-for="(item, index) in attdUserList" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.date }}</td>
            <td class="attd-success" v-if="item.attendance >= 80">
              {{ item.attendance }}
            </td>
            <td class="attd-fail" v-else>{{ item.attendance }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>
  
<script>
import { requestAttdUser } from "@/common/api/groupAPI";
import { useRoute } from "vue-router";
import { ref } from "vue";

export default {
  name: "attdUser",

  setup() {
    const route = useRoute();
    const groupId = ref();
    const userId = ref();
    const attdUserList = ref([]);

    groupId.value = route.params.groupId;
    userId.value = route.params.userId;

    console.log("route.params:", route.params);

    return {
      groupId,
      userId,
      attdUserList,
    };
  },
  async mounted() {
    console.log("userId==", this.userId);
    console.log("groupId==", this.groupId);
    const res = await requestAttdUser(this.groupId, this.userId);
    console.log("개인 출석 res", res);
    const datas = res.data;
    const list = datas.map((item) => {
      console.log("attdUserList attendance", item.attendance);
      return {
        title: item.title,
        date: item.date.substr(0, 10),
        attendance: item.attendance,
      };
    });
    this.attdUserList = list;
    console.log("개인 attdUserList", this.attdUserList);
  },
};
</script>
  
<style>
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
  font-size: 18px;
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
  font-weight: 400;
  font-size: 15px;
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