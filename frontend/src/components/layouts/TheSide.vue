<template>
  <el-aside>
    <el-button
      type="success"
      round
      @click="$router.push({ name: 'groupCreate' })"
    >
      Create Group
    </el-button>
    <group-list />
  </el-aside>
</template>

<script setup>
import GroupList from "@/components/group/GroupList";
</script>

<style>
.el-aside {
  width: 304px;
  /* height: 90vh; */
  background-color: #cde59b;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0;
  padding: 0;
}
/* .el-main{
  width: 65vw;
  height: 90vh;
  background-color: #FFECB9; 
} */
.el-button {
  margin: 1em;
  width: 252px;
  height: 5vh;
  font-size: 20px;
}

.infinite-list {
  height: 300px;
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>
