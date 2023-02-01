<template>
  <the-header></the-header>
  <div class="main"></div>
  
</template>

<script>
// @ is an alias to /src
import TheHeader from "@/components/layouts/TheHeader.vue";

export default {
  name: "MainView",
  components: { TheHeader },
};
</script>

TheHeader
<style scoped>
.el-alert {
  margin: 20px 0 0;
}
.el-alert:first-child {
  margin: 0;
}
</style>
