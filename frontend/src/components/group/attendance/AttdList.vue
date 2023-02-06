<template>
  <p style="color: rgba(97, 178, 153, 1)">
    구미1반 ({{ attdUserList.length }})
  </p>
  <el-button @click="remoteMethod">groupId: {{ groupId }}</el-button>
  <div>
    <table>
      <thead>
        <tr>
          <th>No.</th>
          <th>이름</th>
          <!-- <th>userID</th> -->
          <th>AllATTD</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in attdUserList" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ item.name }}</td>
          <!-- <td>{{ item.userId }}</td> -->
          <td>{{ item.allAtt }}</td>
        </tr>
      </tbody>
    </table>
  </div>
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
  components: {},
  data() {
    return {
      name: "",
      // userId: "",
      allAtt: "",
      result: "",
      groupId: 2,
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
    changeColor() {
      if (this.AttdList.allAtt >= 80) {
        this.AttdList.result = "출석!";
      } else {
        this.AttdList.result = "미출석!";
      }
    },

    remoteMethod() {
      setTimeout(async () => {
        const groupId = Number(this.groupId);
        const res = await requestAttdList(groupId);
        console.log("res", res);
        const datas = res.data;
        console.log("datas", datas);
        let list = datas.map((item) => {
          return {
            /* userId: item.userId,*/ name: item.name,
            allAtt: item.allAtt,
          };
        });
        console.log("list==", list);

        this.attdUserList = list;

        // 요청결과 리스트
      }, 200);
    },
  },
};
</script>

<style>
</style>