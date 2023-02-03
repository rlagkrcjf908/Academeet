<template>
      <!-- 헤더 -->
      <the-header></the-header>
      <!-- 라우터뷰(마이페이지, 그룹관리, 리스트) -->
      <router-view></router-view>

  <!-- 버튼을 눌렀을 때, 해당 위치로 이동 -->
  <!-- <router-link :to="{name: '#'}" >
    <el-button type="success" plain circle>회의생성</el-button>
  </router-link>
  <router-link :to="{name: '#'}" >
    <el-button type="success" plain circle>그룹생성</el-button>
  </router-link>
  <router-link :to="{name: '#'}" >
    <el-button type="success" plain circle>리스트</el-button>
  </router-link> -->
<!--   
  <the-header></the-header>
  <div class="main">
    <div class="btn-group">
      <el-button type="success" plain circle>회의생성</el-button>
      <el-button type="success" plain circle>그룹생성</el-button>
      <el-button type="success" plain circle>리스트</el-button>
    </div>
  </div> -->

</template>

<script>
// @ is an alias to /src
// import { ElButton } from "element-plus";
import TheHeader from '@/components/layouts/TheHeader.vue';

export default {
  name: "MainView",
  components: { TheHeader },
};
</script>

<style>
.el-alert {
  margin: 20px 0 0;
}
.el-alert:first-child {
  margin: 0;
}
</style>
