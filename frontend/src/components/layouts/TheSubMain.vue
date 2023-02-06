<template>
  <!-- Sub main 자리 -->
  <!-- 아진언니가 만든 nav로 바꾸기 -->
  <el-header>
    <router-link to="">구미1반</router-link>
    <router-link to="">출석부</router-link>
    <router-link to="">공지사항</router-link>
    <Delete @click="deleteGroup" style="width: 1em; height: 1em; margin-right: 8px" />
  </el-header>
</template>

<script setup>
import { Delete } from '@element-plus/icons-vue'
import { requestDeleteGroup } from '@/common/api/groupAPI'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ref } from 'vue'

const router = useRouter()
const store = useStore()

// const groupId = store.state.groupStore.groupId
// const deleteGroup = async ()=>{
//   // 확인 알림창 추가하기
//   try {
//     await requestDeleteGroup(groupId)
//     router.push({ name: 'groupMain' })
//     console.log('delete!')
//   }
//   catch (error) {
//     console.log(error)
//   }
// }
</script>

<style>
.el-main{
  width: 65vw;
  height: 90vh;
  /* background-color: #FFECB9; */
}
</style>