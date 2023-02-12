<!-- 리스트 페이지 -->
<template>
  <div class="common-layout">
    <el-container>
      <TheMeetingSide/>
        <el-container>
          <router-view></router-view>
        </el-container>
    </el-container>
  </div>
</template>

<script setup>
import TheMeetingSide from '@/components/layouts/TheMeetingSide.vue'
</script>

<style>

</style>