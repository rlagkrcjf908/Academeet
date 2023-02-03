<template>
  <div>여기는 호스트가 그룹의 전체 출석을 확인하는 리스트 보여주는 곳!</div>
  <!-- <div><label v-for=""</div> -->
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  userId: Number,
  name: String,
  allAtt: Number,
  // img 예정
});
</script>

<style></style>
