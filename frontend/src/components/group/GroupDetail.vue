<template>
  <div>
    <TheSubMain :key="$route.path" />
    <router-view :key="$route.path"></router-view>
  </div>
</template>

<script setup>
import TheSubMain from "@/components/layouts/TheSubMain";
</script>

<style></style>
