<template>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>No.</th>
          <th>글제목</th>
          <th>작성자</th>
          <th>작성시간</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody v-if="articleListLength > 0">
        <tr
          v-for="(item, index) in articleList"
          :key="index"
          @click="routeToArticle(item.articleId)"
          style="cursor: pointer"
        >
          <td>{{ index + 1 }}</td>
          <td>{{ item.title }}</td>
          <td>{{ item.authName }}</td>
          <td>{{ item.date }}</td>
        </tr>
      </tbody>
      <tbody v-else>
        <div style="font-size: 20px; margin-top: 250px">
          - 게시글이 없습니다-
        </div>
      </tbody>
    </table>
  </div>
  <el-button
    class="article-list-create-btn"
    round
    @click="routeToArticleCreate"
  >
    <el-icon><EditPen /></el-icon>작성하기
  </el-button>
  <p hidden>{{ articleList }}</p>
</template>

<script setup>
import { requestArtileList } from "@/common/api/groupAPI";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { EditPen } from "@element-plus/icons-vue";
import { useStore } from "vuex";

const router = useRouter();
const route = useRoute();
const store = useStore();

const groupId = ref(route.params.groupId);
const userId = store.state.accountStore.userId;

const articleList = ref();
var articleListLength = 0;

const routeToArticle = (articleId) => {
  router.push({
    name: "articleDetail",
    params: {
      groupId: groupId.value,
      articleId: articleId,
    },
  });
};

const routeToArticleCreate = () => {
  router.push({
    name: "articleCreate",
    params: {
      groupId: groupId.value,
    },
  });
};

onMounted(async () => {
  const res = await requestArtileList(groupId.value, userId);
  const datas = res.data.articleList;
  const list = datas.map((item) => {
    return {
      articleId: item.id,
      authName: item.userid.name,
      title: item.title,
      date: item.date,
    };
  });
  articleList.value = list;
  articleListLength = articleList.value.length;
});
</script>

<style scoped></style>
