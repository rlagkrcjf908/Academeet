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
  // import TheHeader from '@/components/layouts/TheHeader.vue'
import TheSide from '@/components/layouts/TheSide.vue'

</script>

<style>

</style>