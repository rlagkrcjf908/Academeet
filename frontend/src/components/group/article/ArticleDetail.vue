<template>
  <hr />
  <h2 style="color: rgba(97, 178, 153, 1)">- 공지사항 상세 -</h2>
  <div class="atritcle-title">
    <h3>제목 : {{ title }}</h3>
  </div>
  <div class="atritcle-content">
    {{ content }}
  </div>
  <div>
    <el-button
      class="article-detail-btn"
      v-if="userId === authId"
      type="success"
      plain
      round
      @click="articleUpdate"
      >수정</el-button
    >
    <el-button
      class="article-detail-btn"
      v-if="userId === authId"
      type="success"
      plain
      round
      @click="articleDelete"
      >삭제</el-button
    >
  </div>

  <div class="article-detail-back">
    <el-icon @click="$router.go(-1)"><Back /> </el-icon> 돌아가기
  </div>
</template>

<script setup>
import { useRouter, useRoute } from "vue-router";
import { useStore } from "vuex";
import { ref, onMounted } from "vue";
import { requestDeleteArtile, requestArtile } from "@/common/api/groupAPI";
import { Back } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

const store = useStore();
const router = useRouter();
const route = useRoute();
const authId = ref();
const userId = store.state.accountStore.userId;
const groupId = route.params.groupId;
const articleId = route.params.articleId;
const title = ref();
const content = ref();

// 게시글 불러오기
onMounted(async () => {
  const res = await requestArtile(articleId);
  const article = res.data;
  authId.value = article.userId;
  title.value = article.title;
  content.value = article.content;
});

// 수정
function articleUpdate() {
  router.push({
    name: "articleUpdate",
    params: { groupId: groupId, articleId: articleId },
  });
}

// 삭제
const articleDelete = () => {
  ElMessageBox.confirm("글이 삭제 됩니다. 삭제하시겠습니까?", "Warning", {
    confirmButtonText: "OK",
    cancelButtonText: "Cancel",
    type: "warning",
  })
    .then(async () => {
      try {
        await requestDeleteArtile(articleId);
        router.push({ name: "articleList", params: { groupId: groupId } });
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
.atritcle-title {
  margin: 3em;
}
.atritcle-content {
  margin: auto;
  padding: auto;
  border: 1px solid;
  width: 1200px;
  height: 400px;
}

.article-detail-btn {
  width: 120px;
  height: 50px;
}
.article-detail-back {
  color: gray;
  cursor: pointer;
}
</style>