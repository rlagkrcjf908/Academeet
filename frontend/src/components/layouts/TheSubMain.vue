<template>
  <!-- Sub main 자리 -->
  <el-header>
    <!-- 링크버튼 -->
    <div class="sub-main the-sub-main-header">
      <el-button type="success" @click="routeToSubMain" link style="margin: 0; margin-bottom: 8px; ">
        {{ groupInfo.name }}
      </el-button>
      <el-button @click="routeToAttdItem" type="success" link style="margin: 0; margin-bottom: 8px; ">
        출석부
      </el-button>
      <el-button type="success" link style="margin: 0; margin-bottom: 8px; ">
        공지사항
      </el-button>
      <!-- 삭제버튼 -->
      <el-button type="success" :icon="Delete" small @click="open" style="margin-top: 8px; margin-bottom: 8px; width: 5px;"/>
    </div>
  </el-header>
</template>

<script setup>
//출석부를 위해서 로컬 스토리지 유저와 그룹 호스트 비교, route.push() 기능 정리
import { useRouter, useRoute } from "vue-router";
import { ref } from "vue";
import { useStore } from "vuex";
import { requestDeleteGroup, requestGroup } from "@/common/api/groupAPI";
import { ElMessage, ElMessageBox } from "element-plus";
import { Delete } from "@element-plus/icons-vue";

const router = useRouter();
const route = useRoute();
const store = useStore();
const groupInfo = ref([]);
const groupId = ref(route.params.groupId);

//그룹 이름, 호스트 유저 저장
const getGroup = async () => {
  await store.dispatch("groupStore/requestGroupAction", groupId.value);
  groupInfo.value = store.state.groupStore.groupInfo;
  // console.log(
  //   "groupInfo.value:",
  //   groupInfo.value,
  //   "/ groupInfo.ownerId: ",
  //   groupInfo.value.ownerid
  // );
};

getGroup();



const userId = store.state.accountStore.userId; //로그인된 유저
console.log("로그인 된 userId: ", userId);

//출석부 이동
function routeToAttdItem() {
  // console.log("routeToAttdView 이동합니다");
  // console.log("hostId(groupInfo.value.ownerid): ", groupInfo.value.ownerid);
  // console.log("userId: ", userId);

  if (groupInfo.value.ownerid === userId) {
    router.push({
      name: "attdList",
      params: {
        hostId: groupInfo.value.ownerid,
      },
    });
  } else {
    router.push({
      name: "attdUser",
      params: {
        hostId: groupInfo.value.ownerid,
        selectUserId: userId,
      },
    });
  }
  // console.log(
  //   "groupInfo.value.ownerid:",
  //   groupInfo.value.ownerid.value,
  //   "/ userId:",
  //   userId,
  //   "/ groupId:",
  //   groupId.value
  // );
  // console.log("++++++++++++++++++++++++++++++++++=");
}

//서브메인 이동
const routeToSubMain = () => {
  console.log("routeToSubMain 이동합니다");
  router.push({
    name: "groupDetail",
    params: { groupId: groupId.value },
  });
};

// 확인 메시지 창
const open = () => {
  ElMessageBox.confirm("그룹이 삭제 됩니다. 삭제하시겠습니까?", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  })
    .then(async () => {
      try {
        await requestDeleteGroup(groupId.value);
        router.push("/group");
        console.log("delete!");
        ElMessage({
          type: "success",
          message: "삭제되었습니다",
        });
      } catch (error) {
        console.log(error);
        ElMessage({
          type: "error",
          message: "삭제 실패했습니다",
        });
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "삭제가 취소되었습니다",
      });
    });
};
</script>

<style>
.sub-main {
  margin: 0;
  padding: 0;
}
.sub-main.the-sub-main-header {
  margin: 0;
  width: 80vw;
  height: 64px;
  max-height: 64px;
  float: left;
  position: re;
}
.button.el-button.el-button--success.is-link {
  max-width: 150px;
  max-height: 50px;
}
</style>