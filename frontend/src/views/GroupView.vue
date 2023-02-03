<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" style="background-color: rgba(205, 229, 155, 1);">Aside</el-aside>
      <el-container>
        <el-header style="background-color: rgb(252, 252, 164);">Header</el-header>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>