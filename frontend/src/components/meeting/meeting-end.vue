<template>
  <div class="after-meeting">
    <div>
      <p class="after-meeting-text">회의가 종료 되었습니다.</p>
      <p class="after-meeting-text">버튼을 눌러 원하는 페이지로 이동하세요</p>
    </div>
    <div class="after-meeting-btn">
      <el-button type="success" round @click="$router.push('/')"
        >메인화면</el-button
      >
      <el-button type="success" round @click="$router.push('/group')"
        >그룹화면</el-button
      >
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.after-meeting {
  width: 70vw;
  height: 60vh;
  background-color: rgba(205, 229, 155, 1);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.after-meeting-text > p {
  color: white;
  font-size: 4rem;
  font-weight: bold;
}
.after-meeting-btn {
  margin-top: 20px;
}
</style>
