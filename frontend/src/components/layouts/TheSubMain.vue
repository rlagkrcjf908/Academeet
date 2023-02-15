<template>
  <!-- Sub main 자리 -->
  <el-header class="the-sub-main-header">
    <!-- 링크버튼 -->
    <div>
      <el-button
        type="success"
        @click="routeToSubMain"
        link
        style="margin: 0; margin-bottom: 8px"
      >
        {{ groupInfo.name }}
      </el-button>

      <el-button
        @click="routeToAttdItem"
        type="success"
        link
        style="margin: 0; margin-bottom: 8px"
      >
        출석부
      </el-button>

      <el-button
        @click="routeToArticle"
        type="success"
        link
        style="margin: 0; margin-bottom: 8px"
      >
        게시판
      </el-button>

      <el-button
        @click="routeToRefer"
        type="success"
        link
        style="margin: 0; margin-bottom: 8px"
      >
        자료실
      </el-button>

      <el-button
        @click="open"
        type="danger"
        link
        style="margin: 0; margin-bottom: 8px"
        v-if="groupInfo.ownerid === userId"
      >
        그룹 삭제
      </el-button>
    </div>
  </el-header>
</template>

<script setup>
//출석부를 위해서 로컬 스토리지 유저와 그룹 호스트 비교, route.push() 기능 정리
import { useRouter, useRoute } from "vue-router";
import { ref } from "vue";
import { useStore } from "vuex";
import { requestDeleteGroup } from "@/common/api/groupAPI";
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
};

getGroup();

const userId = store.state.accountStore.userId; //로그인된 유저

//서브메인 이동
const routeToSubMain = () => {
  // console.log("routeToSubMain 이동합니다");
  router.push({
    name: "groupUser",
    params: { groupId: groupId.value },
  });
};

//출석부 이동
function routeToAttdItem() {
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
}
//공지사항 이동
const routeToArticle = () => {
  // console.log("routeToArticle 이동합니다");
  router.push({
    name: "articleList",
    params: { groupId: groupId.value },
  });
};

//자료실 이동
const routeToRefer = () => {
  // console.log("routeToRefer 이동합니다");
  router.push({
    name: "groupRefer",
    params: { groupId: groupId.value },
  });
};

// 삭제 확인 메시지 창
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
        // console.log("delete!");
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
.the-sub-main-header {
  margin: 10px;
  width: 80vw;
  height: 64px;
  max-height: 64px;
  float: left;
  position: relative;
}
.button.el-button.el-button--success.is-link {
  max-width: 150px;
  max-height: 50px;
}
</style>
