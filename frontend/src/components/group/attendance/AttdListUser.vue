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
            <td>{{ item.attendance }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <el-button @click="remoteMethod"
      >UserID: {{ $route.params.userId }}</el-button
    >
  </section>
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
      userId: "",
      // userId: this.$route.params.name,

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
  created() {
    this.userId = this.$route.params.userId;
    console.log("created", this.userId);
    console.log("this.$route.params:", this.$route.params);
  },
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
        let list = datas.map((item) => {
          return {
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

.el-table .warning-row {
  color: #f89898;
}
.el-table .success-row {
  color: #95d475;
}
</style>