<template>
  <div class="main">
    <div class="btn-group">
      <el-button type="success" plain circle @click="$router.push('/meetingcreate')">회의생성</el-button>
      <el-button type="success" plain circle @click="$router.push('/group/create')">그룹생성</el-button>
      <el-button type="success" plain circle>리스트</el-button>
    </div>
  </div>
</template>

<script setup>

import { ElButton } from "element-plus";

</script>

<style scoped>

.main {
  height: 88vh;
  /* background: linear-gradient(-60deg, #ffffff 50%, #cde59b 45%); */
  /* background: linear-gradient(-60deg, img(""), #cde59b 45%); */
  /* background: linear-gradient(60deg, #67c23a88, #ffffff); */
  background-image: url("../assets/images/back6.jfif");
  background-size: cover;

  width: 100%;
  /* //height: 100vh;  */
  display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: flex;

  -webkit-box-align: center;
  -moz-box-align: center;
  -ms-flex-align: center;
  align-items: center; /* 수직 정렬 */

  -webkit-box-pack: center;
  -moz-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center; /* 수평 정렬 */
}
.btn-group {
  width: 100%;
  height: 100vh;
  display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: flex;

  -webkit-box-align: center;
  -moz-box-align: center;
  -ms-flex-align: center;
  align-items: center; /* 수직 정렬 */

  -webkit-box-pack: center;
  -moz-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center; /* 수평 정렬 */
}
.el-button {
  width: 9em;
  height: 9em;
  margin: 2em;
  font-family: "LINESeedKR-Rg";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Rg.woff2")
    format("woff2");
  font-weight: 600;
  font-style: normal;
  font-size: 18px;
}
</style>