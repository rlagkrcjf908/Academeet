<template>
  <div>
    <nav class="profile-view-nav">
      <el-button
        type="success"
        link
        @click="$router.push('/profile')"
        style="margin-top: 8px; margin-bottom: 8px"
        >기본정보</el-button
      >
      <el-button
        type="success"
        link
        @click="$router.push('/profile/changepassword')"
        style="margin-top: 8px; margin-bottom: 8px"
        >비밀번호변경</el-button
      >
    </nav>
    <hr class="profile-view-hr" />
    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<style>
.profile-view-nav {
  padding: 15px;
}
.profile-view-hr {
  background: lightgray;
  height: 1.6px;
  border: 0;
  width: 100%;
  margin: 0px;
}
</style>

