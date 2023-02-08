<template>
  <!-- Sub main 자리 -->
  <el-header>
    <!-- 링크버튼 -->
    <div class="sub-main the-sub-main-header">
      <el-button type="success" @click="routeToSubMain" link small>
        {{ groupName }}
      </el-button>
      <el-button @click="routeToAttdView" type="success" link small>
        출석부
      </el-button>
      <el-button type="success" link small>공지사항</el-button>
      <!-- 삭제버튼 -->
      <el-button type="success" :icon="Delete" small @click="open" />
    </div>
  </el-header>
</template>

<script setup>
import { Delete } from "@element-plus/icons-vue";
import { requestDeleteGroup, requestGroup } from "@/common/api/groupAPI";
import { useRouter, useRoute } from "vue-router";
import { useStore } from "vuex";
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

const router = useRouter();
const route = useRoute();
const store = useStore();
const groupId = ref(route.params.groupId);
const groupName = ref();

console.log("groupId", groupId.value);

//출석부 이동
const routeToAttdView = () => {
  console.log("routeToAttdView 이동합니다");
  router.push({
    name: "attdView2",
    params: { groupId: groupId.value },
  });
};

//서브메인 이동
const routeToSubMain = () => {
  console.log("routeToSubMain 이동합니다");
  router.push({
    name: "groupDetail",
    params: { groupId: groupId.value },
  });
};

//그룹이름 조회
onMounted(async () => {
  const res = await requestGroup(groupId.value);
  console.log("groupName:", res.data.name);
  groupName.value = res.data.name;
});

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