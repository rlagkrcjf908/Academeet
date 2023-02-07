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
            <!-- <td>{{ item.userId }}</td> -->
            <td>{{ item.allAtt }}</td>
            <!-- 버튼 안에 속성 @click="handleDetailUser(scope.$index, scope.row, item.userId)"  -->
            <el-button class="detail-btn" type="success" plain
              >{{ item.name }}:{{ item.userId }}</el-button
            >
            <!-- @click="routeToUser(item)" -->
          </tr>
        </tbody>
      </table>
    </div>
    <!-- <el-button @click="remoteMethod">groupId: {{ groupId }}</el-button> -->
  </section>

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
import { requestAttdList } from "@/common/api/groupAPI";
import { ref } from "vue";

export default {
  name: "attdList",
  // data() {
  //   return {
  //     name: "",
  //     userId: "",
  //     allAtt: "",
  //     groupId: -1,
  //     attdUserList: [],
  //   };
  // },
  // computed:{
  //   attdCheck(){
  //     if(this.allAtt >= 80){
  //       return '출석 완료!';
  //     }else{
  //       return '미출석';
  //     }

  //   }
  // },
  async setup() {
    // this.groupId = this.$route.params.groupId;
    // this.groupId = 2;

    const groupId = ref(2);
    const attdUserList = ref([]);

    console.log("groupId==", groupId.value);
    const res = await requestAttdList(groupId.value);
    console.log("전체 출석 res", res);
    const datas = res.data;
    const list = datas.map((item) => {
      return {
        userId: item.userId,
        name: item.name,
        allAtt: item.allAtt,
      };
    });
    attdUserList.value = list;
    console.log("attdUserList value XX", attdUserList);
    console.log("attdUserList ==", attdUserList.value[0].userId);

    const routeToUser = (item) => {
      console.log("item.userId: ", item.userId);
      this.$router.push({
        name: "attdUser",
        params: { userId: item.userId },
      });
    };

    // 요청결과 리스트

    return {
      groupId,
      attdUserList,
    };

    // // const attdUserList = ref([])
    // const attdUseList = reactive([]);
    // onMounted(async () => {
    //   const groupId = Number(this.groupId);
    //   const res = await requestAttdList(groupId);
    //   attdUseList = datas.map((item) => {
    //     return {
    //       userId: item.userId,
    //       name: item.name,
    //       allAtt: item.allAtt,
    //     };
    //   });
    // });
    // //api로 불러와서 response의 list를 attdUseList에 넣어줌
    // //
  },
  // methods: {
  //   // routeToUser(item) {
  //   //   console.log("item.userId: ", item.userId);
  //   //   this.$router.push({
  //   //     name: "attdUser",
  //   //     params: { userId: item.userId },
  //   //   });
  //   // },
  //   handleDetailUser(userId) {
  //     const user = userId.target.getAttribute("userId");
  //     {
  //     }
  //   },
  //   changeColor() {
  //     if (this.attdUserList.allAtt >= 80) {
  //       this.attdUserList.allAtt = "출석!";
  //     } else {
  //       this.attdUserList.allAtt = "미출석!";
  //     }
  //   },

  //   // remoteMethod() {
  //   //   setTimeout(async () => {
  //   //     const groupId = Number(this.groupId);
  //   //     const res = await requestAttdList(groupId);
  //   //     console.log("전체 출석 res", res);
  //   //     const datas = res.data;
  //   //     console.log("전체 출석 datas", datas);
  //   //     let list = datas.map((item) => {
  //   //       return {
  //   //         userId: item.userId,
  //   //         name: item.name,
  //   //         allAtt: item.allAtt,
  //   //       };
  //   //     });
  //   //     console.log("개인 출석list==", list);

  //   //     this.attdUserList = list;

  //   //     // 요청결과 리스트
  //   //   }, 200);
  //   // },

  //   // remoteMethod() {
  //   //   setTimeout(async () => {
  //   //     console.log("res", res);
  //   //     console.log("datas", datas);
  //   //     let list = console.log("list==", list);

  //   //     this.attdUserList = list;

  //   //     // 요청결과 리스트
  //   //   }, 200);
  //   // },
  // },
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
</style>