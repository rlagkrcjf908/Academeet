<template>
  <!-- <nav>
  </nav> -->
  <div class="common-layout">
    <el-container>
      <el-header class="header-item">
        <nav>
          <router-link to="/profile">기본정보</router-link>
          <router-link to="/profile/changepassword">비밀번호변경</router-link>
        </nav>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>

</script>
<style scoped>
.header-item{
  border-bottom: 1px solid rgba(134, 134, 134, 1);
  margin-bottom: 4em;
}
</style>

