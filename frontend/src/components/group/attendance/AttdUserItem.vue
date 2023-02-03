<template>
  <div
    style="
      border-bottom: 1px solid rgba(217, 217, 217, 1);
      display: flex;
      align-self: start;
    "
    :row-class-name="attdColor"
  >
    <p>
      {{ attdUser.title }}, {{ attdUser.date }},
      {{ attdUser.attd }}
    </p>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
  attdUser: String,
});

const attdColor = ({ row }) => {
  if (row.attd >= 80) {
    return "success-row";
  } else if (row.attd < 80) {
    return "warning-row";
  }
  return "";
};
</script>
<style lang=""></style>
