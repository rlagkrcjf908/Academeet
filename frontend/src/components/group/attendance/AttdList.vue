<template>
  <section>
    <h2 style="color: rgba(97, 178, 153, 1)">
      구미1반 ({{ attdUserList.length }})
    </h2>

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
        <tbody>
          <tr v-for="(item, index) in attdUserList" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.name }}</td>
            <td class="attd-success" v-if="item.allAtt >= 80">
              {{ item.allAtt }}
            </td>
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
      </table>
    </div>
  </section>
</template>

<script>
import { requestAttdList } from "@/common/api/groupAPI";
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";

export default {
  name: "attdList",
  setup() {
    const router = useRouter();
    const route = useRoute();

    const groupId = ref();
    const attdUserList = ref([]);

    groupId.value = route.params.groupId;

    const routeToUser = (item) => {
      console.log("item.userId: ", item.userId);
      router.push({
        name: "attdUser",
        //params: { userId: item.userId, groupId: item.groupId },
        params: { userId: item.userId },
      });
    };

    // 요청결과 리스트

    return {
      groupId,
      attdUserList,
      routeToUser,
    };
  },

  async mounted() {
    console.log("groupId==", this.groupId);
    const res = await requestAttdList(this.groupId);
    console.log("전체 출석 res", res);
    const datas = res.data;
    const list = datas.map((item) => {
      return {
        userId: item.userId,
        name: item.name,
        allAtt: item.allAtt,
      };
    });
    this.attdUserList = list;
    console.log("attdUserList value XX", this.attdUserList);
  },
};
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