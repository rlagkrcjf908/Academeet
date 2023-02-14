<template>
  <el-container>
    <TheSide />
    <router-view class="gruop-view"></router-view>
  </el-container>
</template>

<script setup>
import TheSide from "@/components/layouts/TheSide.vue";
</script>

<style>
.el-container {
  width: auto;
  height: calc(100vh - 86px);
  margin: 0;
  padding: 0;
}
</style>