<template>
  <!-- Sub main 자리 -->
  <!-- 아진언니가 만든 nav로 바꾸기 -->
  <el-header style="background-color: rgb(252, 252, 164);">Header</el-header>

  <el-main>
    <el-tabs type="border-card">
        <el-tab-pane label="구미1반">구미1반</el-tab-pane>
        <el-tab-pane label="출석부">출석부</el-tab-pane>
        <el-tab-pane label="공지사항">공지사항</el-tab-pane>
        <el-tab-pane label="관리">관리</el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
export default {

}
</script>

<style>
.el-main{
  width: 65vw;
  height: 90vh;
  /* background-color: #FFECB9; */
}
</style>