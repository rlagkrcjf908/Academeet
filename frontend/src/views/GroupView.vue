<template>
  <div class="common-layout">
    <el-container>
      <TheSide/>
      <el-container>
        <router-view></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import TheSide from "@/components/layouts/TheSide.vue";
// import { onMounted, ref } from 'vue';
// import { useStore } from 'vuex'

// const store = useStore()
// const userid = store.state.accountStore.userId

// onMounted (async () => {
//     await store.dispatch('groupStore/requestGroupListAction', userid)
// })
</script>

<style>
</style>