<template>
  <hr />
  <h2 style="color: rgba(97, 178, 153, 1)">- 게시글 작성 -</h2>
  <el-form
    label-position="top"
    ref="ruleFormRef"
    :model="ruleForm"
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
    status-icon
  >
    <div>
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
        <el-input
          v-model="ruleForm.content"
          type="textarea"
          autocomplete="off"
        />
      </el-form-item>
    </div>
  </el-form>
  <!-- 저장 -->
  <el-form-item class="article-create-save-btn">
    <el-button type="success" round @click="submitForm(ruleFormRef)">
      저장하기
    </el-button>
  </el-form-item>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
const route = useRoute();
const store = useStore();
const userId = store.state.accountStore.userId;
const groupId = route.params.groupId;
const ruleFormRef = ref();

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

// 작성날짜
function getToday() {
  var date = new Date();
  var year = date.getFullYear();
  var month = ("0" + (1 + date.getMonth())).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);

  return year + "-" + month + "-" + day;
}

// 작성 제출
const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      const data = {
        title: ruleForm.title,
        content: ruleForm.content,
        date: getToday(),
        groupId: groupId,
        userId: userId,
      };
      await store.dispatch("groupStore/articleCreateAction", data);
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
.article-create-save-btn {
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