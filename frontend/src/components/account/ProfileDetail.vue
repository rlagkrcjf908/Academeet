<template>
  <el-row :span="24" justify="center">
    <el-col>
          <el-row :span="24" justify="center">

            <el-col :span="4">
              <!-- 프로필사진 -->
              <img  :src = "'http://192.168.100.191:8080/image/'+profile.profile.filename"  style="height:5em;">
              

              <!-- 유저이름 -->
              <p>{{profile.name}}</p>

            </el-col>
            
            <el-col :span="8">
              <div class="profileInfo-box">
  
                <!-- 이메일 -->
                <div class="profileInfo">
                  <img :src="profile" alt="" style="height:1em; padding-right: 1em;">
                  <span>{{profile.email}}</span>
                </div>
                
                <!-- 닉네임 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/id-card.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.nick}}</span>
                </div>
                

                <!-- 연락처  -->                
                <div class="profileInfo" >
                  <img :src="require('@/assets/images/telephone-call.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.phone}}</span>
                </div>
                
                <!-- 생일 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/birthday-cake.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.birth}}</span>
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

const profile = JSON.parse(localStorage.getItem('userInfo'))

console.log('받앗다',profile)

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