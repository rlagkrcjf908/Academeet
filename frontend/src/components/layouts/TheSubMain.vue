<template>
  <!-- Sub main 자리 -->
  <!-- 아진언니가 만든 nav로 바꾸기 -->
  <el-header>
    <router-link to="">구미1반</router-link>
    <router-link to="">출석부</router-link>
    <router-link to="">공지사항</router-link>
    <!-- 링크버튼 -->
    <div class="sub-main">
    <el-button type="success" link>출석부</el-button>
    <el-button type="success" link>출석부</el-button>
    <el-button type="success" link>공지사항</el-button>
    </div>
    <!-- 삭제버튼 -->
    <el-button type="success" :icon="Delete" @click="open" />

  </el-header>
</template>

<script setup>
import { Delete } from '@element-plus/icons-vue'
import { requestDeleteGroup } from '@/common/api/groupAPI'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()
const store = useStore()
const groupId = ref(route.params.groupId)
console.log('groupId',groupId.value)

// 확인 메시지 창
const open = () => {
  ElMessageBox.confirm(
    '그룹이 삭제 됩니다. 삭제하시겠습니까?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
    .then( async () => {
      try {
        await requestDeleteGroup(groupId.value)
        router.push("/group")
        console.log('delete!')
        ElMessage({
          type: 'success',
          message: '삭제되었습니다',
        })
      }
      catch (error) {
        console.log(error)
        ElMessage({
          type: 'error',
          message: '삭제 실패했습니다',
        })
      }

    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '삭제가 취소되었습니다',
      })
    })
}

</script>

<style>
.sub-main{
  margin: 0;
  padding: 0;
}
</style>