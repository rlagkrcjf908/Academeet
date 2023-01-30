<template>
  <!-- 폼 시작 -->
  <el-row :span="24" justify="center">
    <el-col>
          <el-row :span="24" justify="center">

            <el-col :span="4">
              <!-- 프로필사진 -->
              <img :src="profile.profileImg"  alt="프로필사진" style="height:5em;">

              <!-- 유저이름 -->
              <p>{{profile.username}}</p>

            </el-col>
            
            <el-col span="8">
              <div class="profileInfo-box">
  
                <!-- 이메일 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/mail.png')" alt="" style="height:1em; padding-right: 1em;">
                  <span>{{profile.email}}</span>
                </div>
                
                <!-- 닉네임 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/id-card.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.nickname}}</span>
                </div>
                

                <!-- 연락처  -->                
                <div class="profileInfo" >
                  <img :src="require('@/assets/images/telephone-call.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.phone}}</span>
                </div>
                
                <!-- 생일 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/birthday-cake.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.birthday}}</span>
                </div>
              </div>

            </el-col>
            
          </el-row>

          
        </el-col>
      </el-row>
      <!-- 수정버튼 -->
      <el-button type="success" round @click="$router.push('/profile/update')" class="profile-btn">수정하기</el-button>
</template>

<script setup>
import { ref } from 'vue'

const profile = ref(
  {
    profileImg: require("@/assets/images/user.png"),
    username: '김하니',
    email: 'ssafy8@gmail.com',
    nickname: '뉴진스하니',
    phone: '010-1234-5678',
    birthday: '2004.10.06'
  }
)
</script>

<style>
.profileInfo-box {
    border: 4px solid rgba(148, 216, 45, 1);
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

</style>
