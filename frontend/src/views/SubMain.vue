<template>
<the-header></the-header>
<el-container>
  <the-side></the-side>
  <the-sub-main></the-sub-main>
</el-container>
</template>

<script>
import TheHeader from '@/components/layouts/TheHeader.vue'
import TheSide from '@/components/layouts/TheSide.vue'
import TheSubMain from '@/components/layouts/TheSubMain.vue'

export default {
  components: { TheHeader, TheSide, TheSubMain },

}
</script>

<style>

</style>