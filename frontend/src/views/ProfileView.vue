<template>
  <div>
    <hr>
    <nav style="padding: 0;">
      <el-button type="success" link @click="$router.push('/profile')" style="margin-top: 8px; margin-bottom: 8px; ">기본정보</el-button>
      <el-button type="success" link @click="$router.push('/profile/changepassword')" style="margin-top: 8px; margin-bottom: 8px;">비밀번호변경</el-button>
    </nav>
    <hr>
    <el-main>
    <router-view></router-view>
    </el-main>
  </div>
</template>

<script setup>

</script>
<style >

</style>

