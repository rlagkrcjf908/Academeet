<template>
  <div class="common-layout">
    <el-container>
      <p>
        여기는 그룹의 호스트아이디와 유저아이디를 비교하여 '수정'버튼 띄우고
        수정하게하기
      </p>
      <el-header>
        <p style="color: rgba(97, 178, 153, 1)">
          구미1반 ({{ attdUserList.length }})
          <el-button @click="remoteMethod">groupId: {{ groupId }}</el-button>
        </p>
      </el-header>
      <el-main
        ><!-- 테이블 테스트 -->
        <el-table
          :data="attdUserList"
          style="width: 100%"
          :row-class-name="attdColor"
        >
          <el-table-column prop="no" label="No." width="100" />
          <el-table-column prop="title" label="제목" width="180" />
          <el-table-column prop="date" label="날짜" width="180" />
          <el-table-column prop="attendance" label="출석률" />
        </el-table>
      </el-main>
    </el-container>
  </div>

  <!-- <div class="tabel2">
      <table>
        <thead>
          <tr>
            <th>No.</th>
            <th>제목</th>
            <th>날짜</th>
            <th>출석률</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in attdUserList" :key="index">
            <td>{{ item.no }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.date }}</td>
            <td>{{ item.attendance }}</td>
          </tr>
        </tbody>
      </table>
    </div> -->
  <!-- <div>
            <p style="color: rgba(97, 178, 153, 1)">
              출 석 ({{ attdUserList.length }})
            </p>
            <div style="border: 1px solid black; padding: 40px">
              <tr
                v-for="(attdUser, i) in attdUserList"
                :key="i + 1"
                :attdUser="attdUser"
              >
              {{ attdUser.title }}, {{ attdUser.date }},
        {{ attdUser.attd }}
            </tr>
            </div>
      </div> -->
</template>
  
  <script>
import { requestAttdUser } from "@/common/api/groupAPI";
import { ref } from "vue";

export default {
  name: "attdList",
  components: {},
  data() {
    return {
      name: "",
      // userId: "",
      allAtt: "",
      result: "",
      groupId: 2,
      userId: 4,
      attdUserList: [],
    };
  },
  // computed:{
  //   attdCheck(){
  //     if(this.allAtt >= 80){
  //       return '출석 완료!';
  //     }else{
  //       return '미출석';
  //     }

  //   }
  // },
  setup() {
    // const attdUserList = ref([])
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    // changeColor() {
    //   if (this.AttdList.allAtt >= 80) {
    //     this.AttdList.result = "출석!";
    //   } else {
    //     this.AttdList.result = "미출석!";
    //   }
    // },
    // tableRowClassName(attendance) {
    //   if (attendance < 80) {
    //     return "warning-row";
    //   } else if (attendance >= 80) {
    //     return "success-row";
    //   }
    //   return "";
    // },

    attdColor({ row }) {
      if (row.attendance >= 80) {
        return "success-row";
      } else if (row.attendance < 80) {
        return "warning-row";
      }
      return "";
    },

    remoteMethod() {
      setTimeout(async () => {
        const groupId = Number(this.groupId);
        const userId = Number(this.userId);
        const res = await requestAttdUser(groupId, userId);
        console.log("개인 출석 res", res);
        const datas = res.data;
        console.log("개인 출석 datas", datas);
        let no = 1;
        let list = datas.map((item) => {
          return {
            no: no++,
            title: item.title,
            date: item.date.substr(0, 10),
            attendance: item.attendance,
          };
        });
        console.log("개인 출석list==", list);

        this.attdUserList = list;

        // 요청결과 리스트
      }, 200);
    },
  },
};
</script>
  
<style lang="scss" scoped>
.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 99%;
  height: 64px;
  padding: 10px;
  position: relative;
}
.common-layout {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100vw;
  height: 88vh;
  padding: 10px;
  position: relative;
  background-color: beige;
}

.el-table .warning-row {
  color: #f89898;
}
.el-table .success-row {
  color: #95d475;
}
</style>