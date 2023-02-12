<template>
  <el-icon @click="routeToArticleCreate"><EditPen/></el-icon>
  <section>
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
        <tbody>
          <tr v-for="(item, index) in articleList" :key="index" @click="routeToArticle(item.articeId)">
            <td>{{ index + 1 }}</td>
            <td>{{ item.authName }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.date }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script setup>
import { requestArtileList } from "@/common/api/groupAPI";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { EditPen } from '@element-plus/icons-vue'
const router = useRouter();
const route = useRoute();
const groupId = ref(route.params.groupId);
const articleList = ref();

const routeToArticle = (articeId) => {
  router.push({
    name: "articleDetail",
    params: {
      groupId: groupId.value,
      articleId: articeId
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
  const res = await requestArtileList(groupId.value);
  const datas = res.data;
  const list = datas.map((item) => {
    return {
      articeId : item.articleid,
      authName: item.username,
      title: item.title,
      date : item.date
    };
  });
  articleList.value = list;
});
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

.attd-fail {
  color: #f89898;
}
.attd-success {
  color: #95d475;
}
</style>
