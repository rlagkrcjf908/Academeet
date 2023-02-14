<template>
  <div class="profile-container">
    <div>
      <!-- 프로필사진 -->
        <img :src = "'https://i8d108.p.ssafy.io/img/'+profile.profile.filename" class='profile-img'>
      
      <!-- 유저이름 -->
      <p>{{profile.name}}</p>
    </div>
  
    <div class="profileInfo-box">
    
      <!-- 이메일 -->
      <div class="profileInfo">
        <el-icon><Message /></el-icon>
        <span>{{profile.email}}</span>
      </div>
      
      <!-- 닉네임 -->
      <div class="profileInfo">
        <el-icon><User /></el-icon>
        <span>{{profile.nick}}</span>
      </div>
      
    
      <!-- 연락처  -->                
      <div class="profileInfo" >
        <el-icon><Phone /></el-icon>
        <span>{{profile.phone}}</span>
      </div>
      
      <!-- 생일 -->
      <div class="profileInfo">
        <el-icon><Present /></el-icon>
        <span>{{profile.birth}}</span>
      </div>
    </div>
  </div>
  
  <!-- 수정버튼 -->
  <el-button type="success" round @click="$router.push('/profile/update')" class="profile-btn">수정하기</el-button>

</template>

<script setup>
import { Message, User, Phone, Present } from '@element-plus/icons-vue'
const profile = JSON.parse(localStorage.getItem('userInfo'))

console.log('받앗다',profile)

</script>

<style>

.profile-container{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding-top: 4em;

  /* max-width: 960px; */
}

.profile-img{
  background-color: rgba(205, 229, 155, 1);
  width: 24vh;
  height: 24vh;
  border-radius: 100%;
  margin: 0 8em;
}

.profileInfo-box {
    border: 1.5px solid rgba(148, 216, 45, 1);
    border-radius: 40px;
    box-shadow: 8px 8px 8px 2px rgba(205, 229, 155, 0.5);
    padding: 1em;
    margin-bottom: 2em;
    height: 300px; 
    width: 500px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.profileInfo {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 1em;
    border-bottom: 1px solid rgba(217, 217, 217, 1);
    margin: 0 2em;
    font-size: 16px;
}

.el-icon{
  margin-right: 2em;
}
</style>