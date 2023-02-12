<template>
  <div class="common-layout">
    <el-container>
      <TheSide />
      <router-view></router-view>
    </el-container>
  </div>
</template>

<script setup>
import TheSide from "@/components/layouts/TheSide.vue";
</script>

<style>
</style>