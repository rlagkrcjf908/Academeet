<template>
  <section>
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
        <!-- 호스트 유저 (수정가능하게 하기) -->
        <tbody v-if="hostId === loginUserId">
          <tr v-for="(item, index) in attdUserList" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.date }}</td>
            <td class="attd-success" v-if="item.attendance >= 80">
              {{ item.attendance }}
            </td>
            <td class="attd-fail" v-else>
              <el-input v-model="item.attendance"></el-input>
            </td>
          </tr>
        </tbody>
        <!-- 일반 유저 -->
        <tbody v-else>
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
    <el-button
      type="success"
      @click="saveAttdList"
      size="small"
      v-if="hostId === loginUserId"
      >저장하기</el-button
    >
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
    console.log("attdUserList route.params :", route.params);
    const groupId = ref(route.params.groupId);
    const userId = ref(route.params.userId); //상세보기 선택된 유저
    const hostId = ref(route.params.hostId);
    const attdUserList = ref([]);

    const userInfo = ref([]); //localstorage에 저장된 로그인된 userId 저장할 공간
    const loginUserId = ref();
    //login된 유저ID 저장
    userInfo.value = JSON.parse(localStorage.getItem("userInfo"));
    console.log("userInfo.value: ", userInfo.value);
    loginUserId.value = userInfo.value.id;

    console.log("attdUserList hostId :", hostId.value);
    console.log("attdUserList loginUserId :", loginUserId.value);

    //함수수정하기
    function saveAttdList() {
      const config = {
        headers: {
          "content-type": "application/json;charset=UTF-8",
          // "Content-Type": "multipart/form-data"
        },
      };

      const updateAttdList = {
        title: item.title,
        date: item.date.substr(0, 10),
        attendance: item.attendance,
      };
    }

    return {
      groupId,
      userId,
      hostId,
      loginUserId,
      attdUserList,
      saveAttdList,
    };
  },
  async mounted() {
    console.log(
      "groupId==",
      this.groupId,
      "/ userId==",
      this.userId,
      "/hostId==",
      this.hostId
    );
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