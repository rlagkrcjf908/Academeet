<template>
  <div class="common-layout">
    <hr>
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">Aside</el-aside>
          <el-container>
            <el-main>Main
              <div>
                <button>
                  강의 이동하기
                </button>
              </div>
                <button>
                  강의 다시보기
                </button>
            </el-main>
          </el-container>
        </el-container>
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>
<script setup>
import GroupList from '@/components/group/GroupList'
import { onMounted } from 'vue';
import { useStore } from 'vuex'

const store = useStore()
onMounted (() => {
  store.dispatch('groupStore/requestGroupListAction', store.state.accountStore.id)
  })


</script>
<style scoped>

</style>

