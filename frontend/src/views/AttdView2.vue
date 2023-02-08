<!-- 출석 페이지 -->
<template>
  <div class="common-layout">
    <TheSide />
  </div>
</template>

<script>
import { ref } from "vue";
import { requestGroup } from "@/common/api/groupAPI";
import { useRouter, useRoute } from "vue-router";

export default {
  name: "attdView2",
  setup() {
    const hostId = ref(); //requestGroup해서 받은 hostId 저장할 공간
    const userInfo = ref([]); //localstorage에 저장된 userId 저장할 공간
    const route = useRoute();
    const router = useRouter();
    const groupId = ref(route.params.groupId);
    const userId = ref();

    userInfo.value = JSON.parse(localStorage.getItem("userInfo"));
    console.log("userInfo.value: ", userInfo.value);
    console.log("userId.value: ", userInfo.value.id);
    userId.value = userInfo.value.id;

    return {
      hostId,
      userId,
      groupId,
      userInfo,
    };
  },
  async mounted() {
    console.log("attdView mounted");
    console.log("attdView groupId: ", this.groupId);
    const res = await requestGroup(this.groupId);
    console.log("그룹호스트 조회", res);
    this.hostId = res.data.ownerid;
    // console.log("그룹호스트 hostDatas: ", hostDatas);
    console.log("hostId: ", this.hostId);

    //페이지 이동 처리
    if (this.hostId === this.userId) {
      router.push({
        name: "attdList",
        params: {
          hostId: this.hostId,
          userId: this.userId,
          groupId: this.groupId,
        },
      });
    } else {
      router.push({
        name: "attdUser",
        params: {
          hostId: this.hostId,
          userId: this.userId,
          groupId: this.groupId,
        },
      });
    }
  },
  //     isHostShoow(){
  //     this.isHost =
  // }
};
</script>

<style>
</style>
