<template>
  <hr />
  <h2 style="color: rgba(97, 178, 153, 1)">- 공지사항 수정 -</h2>
  <el-form
    label-position="top"
    ref="ruleFormRef"
    :model="ruleForm"
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
    status-icon
  >
    <!-- 글 제목 -->
    <el-form-item label="제목" prop="title">
      <el-input
        v-model="ruleForm.title"
        type="text"
        autocomplete="off"
        maxlength="100"
      />
    </el-form-item>
    <!-- 글 내용 -->
    <el-form-item label="글 내용" prop="content">
      <el-input v-model="ruleForm.content" type="textarea" autocomplete="off" />
    </el-form-item>
  </el-form>
  <!-- 저장 -->
  <el-form-item class="article-update-save-btn">
    <el-button type="success" round @click="submitForm(ruleFormRef)">
      저장하기
    </el-button>
  </el-form-item>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { requestArtile } from "@/common/api/groupAPI.js";

const route = useRoute();
const store = useStore();
const groupId = route.params.groupId;
const articleId = route.params.articleId;
const ruleFormRef = ref();
// const title = ref()
// const content = ref()

async function getArticle() {
  const res = await requestArtile(articleId);

  console.log(res);
  console.log(res.data);

  ruleForm.title = res.data.title;
  ruleForm.content = res.data.content;
}

onMounted(() => {
  getArticle();
});

const ruleForm = reactive({
  title: "",
  content: "",
});

const rules = reactive({
  title: [
    { required: true, message: "제목을 입력해 주세요.", trigger: "blur" },
  ],
  content: [
    { required: true, message: "내용을 입력해 주세요.", trigger: "blur" },
  ],
});

function getToday() {
  var date = new Date();
  var year = date.getFullYear();
  var month = ("0" + (1 + date.getMonth())).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);

  return year + "-" + month + "-" + day;
}

const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      const data = {
        title: ruleForm.title,
        content: ruleForm.content,
        date: getToday(),
        groupId: groupId,
        articleId: articleId,
      };
      await store.dispatch("groupStore/articleUpdateAction", data);
    } else {
      console.log("error submit!");
      return false;
    }
  });
};
</script>


<style>
.demo-ruleForm {
  margin: 3em;
}
.article-update-save-btn {
  margin: auto;
  display: inline-block;
  text-align: center;
}

.el-textarea__inner {
  height: 400px;
}
.el-input__inner {
  height: 50px;
}
</style>