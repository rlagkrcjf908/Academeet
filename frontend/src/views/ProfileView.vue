<template>
  <div>
    <nav>
      <el-button type="success" link @click="$router.push('/profile')">기본정보</el-button>
      <el-button type="success" link @click="$router.push('/profile/changepassword')">비밀번호변경</el-button>
    </nav>
    <router-view></router-view>
  </div>
  <!-- <div class="common-layout">
    <el-container>
      <el-header class="header-item">
        <nav>
          <el-button type="success" link >기본정보</el-button>
          <el-button type="success" link >비밀번호변경</el-button>
        </nav>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div> -->
</template>

<script setup>

</script>
<style scoped>
@import url(https://fonts.googleapis.com/css?family=Lato:400,300,700,900);

</style>

