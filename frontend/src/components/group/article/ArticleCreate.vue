<template>
  <p>공지사항작성</p>
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
      <el-input
          v-model="ruleForm.content"
          type="textarea"
          autocomplete="off"
        />
    </el-form-item>
  <!-- 저장 -->
    <el-form-item>
      <el-button type="success" round @click="submitForm(ruleFormRef)">
        저장
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router';
const route = useRoute();
const store = useStore()
const userId = store.state.accountStore.userId
const groupId = route.params.groupId
const ruleFormRef = ref()

const ruleForm = reactive({
  title: '',
  content: '',
})

const rules = reactive({
  title: [
    { required: true, message: '제목을 입력해 주세요.', trigger: 'blur' },
  ],
  content: [
    { required: true, message: '내용을 입력해 주세요.', trigger: 'blur' },
  ],
})

const createDate = ()=>{
  const today = new Date();
  const dateString = today.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
  });
  
  return dateString
}

const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      const data = {
        title : ruleForm.title,
        content : ruleForm.content,
        date : createDate(),
        groupId :groupId,
        userId :userId
      }
      await store.dispatch('groupStore/articleCreateAction',data)
      }
    else {
      console.log('error submit!')
      return false
    }    
    })
  }

</script>

<style>

</style>