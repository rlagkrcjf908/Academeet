<template>
      <!-- 헤더 -->
      <the-header></the-header>
      <!-- 라우터뷰(마이페이지, 그룹관리, 리스트) -->
      <router-view></router-view>

</template>

<script setup>
// @ is an alias to /src
// import { ElButton } from "element-plus";
import TheHeader from '@/components/layouts/TheHeader.vue';

</script>

<style>
.el-alert {
  margin: 20px 0 0;
}
.el-alert:first-child {
  margin: 0;
}
</style>
