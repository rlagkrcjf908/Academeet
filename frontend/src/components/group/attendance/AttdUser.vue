<template>
  <p>attdUser 공간</p>
  <div>
    <!-- 그룹의 호스트아이디와 현재 유저가 같으면 수정버튼 보이게하기 -->
    <el-main>
      <div v-if="그룹호스트아이디 == 현재유저아이디">
        <!-- ===으로 고쳐야한다 -->
        <!-- <GroupList v-for="group in groups" :key="group" :group="group"/> -->
        <el-button
          type="success"
          @click="$router.push(`/group/attdModify/:{userId}`)"
          >수정하기</el-button
        >
      </div>
      <div v-else>
        <!-- 호스트아이디와 현재유저가 같지않으면 유저가 참여한 회의 목록만 불러오기 -->

        <!-- <el-table
          :data="attdInfos"
          style="width: 100%"
          :row-class-name="attdColor"
        >
          <el-table-column prop="number" label="No." width="60" />
          <el-table-column prop="title" label="제목" width="200" />
          <el-table-column prop="date" label="날짜" width="150" />
          <el-table-column prop="attd" label="출석율" width="100" />
        </el-table> -->

        <div>
          <p style="color: rgba(97, 178, 153, 1)">
            출 석 ({{ attdUsers.length }})
          </p>
          <div style="border: 1px solid black; padding: 40px">
            <AttdUserItem
              v-for="(attdUser, i) in attdUsers"
              :key="i + 1"
              :attdUser="attdUser"
            ></AttdUserItem>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script setup>
import { ref } from "vue";
// import { onMounted } from "vue";
import AttdUserItem from "@/components/group/attendance/AttdUserItem.vue";

//스토어부분 주석처리하기
import { groupStore } from "vuex";
const store = groupStore();

onMounted(() => {
  store.dispatch("groupStore/requestAttdUser", store.state.groupStore.attdUser);
});

const { ...attdUser } = store.state.groupStore.attdUser;
// const props = defineProps({
//   title: String,
//   label: String,
//   attd: Number,
// });

// export default {{
//   data(){
//     return{
//       attdUsers
//     };
//   },
//   methods: {}
//   ,
// }
// }

const attdColor = ({ row }) => {
  if (row.attd >= 80) {
    return "success-row";
  } else if (row.attd < 80) {
    return "warning-row";
  }
  return "";
};

const attdUsers = ref([
  {
    // number: 1,
    title: "title",
    date: "2016-05-03",
    attd: 78,
  },
  {
    // number: 2,
    title: "title",
    date: "2016-05-02",
    attd: 88,
  },
  {
    // number: 3,
    title: "title",
    date: "2016-05-04",
    attd: 100,
  },
  {
    // number: 4,
    title: "title",
    date: "2016-05-01",
    attd: 55,
  },
  {
    // number: 5,
    title: "title",
    date: "2016-05-01",
    attd: 88,
  },
]);
</script>

<style>
.el-table .warning-row {
  color: #f89898;
}
.el-table .success-row {
  color: #95d475;
}
.el-main {
  width: 70vw;
  height: 90vh;
  overflow: hidden;
}
/* .el-main:hover {
  overflow-y: scroll;
} */
</style>
