<template>
  <p>공지사항수정</p>
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
import { reactive, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { requestArtile } from '@/common/api/groupAPI.js'

const route = useRoute();
const store = useStore()
const groupId = route.params.groupId
const articleId = route.params.articleId
const ruleFormRef = ref()
// const title = ref()
// const content = ref()


async function getArticle() {
  const res = await requestArtile(articleId)

    console.log(res)  
    console.log(res.data)

    ruleForm.title = res.data.title
    ruleForm.content = res.data.content
  }

  onMounted (() => {
    getArticle()
  })
  
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

function getToday(){
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
        title : ruleForm.title,
        content : ruleForm.content,
        date : getToday(),
        groupId :groupId,
        articleId :articleId
      }
      await store.dispatch('groupStore/articleUpdateAction',data)
      }
    else {
      console.log('error submit!')
      return false
    }    
    })
  }
  
  // 일단 작성 후 라우터 가드 걸어서 작성자만 가능하고 아니면 접급권한이 없습니다.
</script>


<style>

</style>