<template>
  <!-- 폼 시작 -->
  
  <el-col :span="4">
    <label for="joinProfile">
      <el-avatar :size = "300" :src="profileImg" />
      <input ref="image" @change="uploadImg()" type="file" id="joinProfile" accept="image/*"/>
    </label>
    <!-- 유저이름 -->
    <p>{{profile.name}}</p>
  </el-col>

  <el-row :span="24" justify="center">
    <el-col>
        <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        status-icon
        :rules="rules"
        label-width=auto
        class="demo-ruleForm">
          
          <el-row :span="24" justify="center">

            <el-col :span="8">
              <div class="profileInfo-box">
  
                <!-- 이메일 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/mail.png')" alt="" style="height:1em; padding-right: 1em;">
                  <span>{{profile.email}}</span>
                </div>
                
                <!-- 닉네임 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/id-card.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <el-form-item prop="nickname">
                    <el-input v-model.trim="ruleForm.nick" type="text" autocomplete="off" placeholder="닉네임을 입력해 주세요." maxlength="45"/>
                  </el-form-item>
                </div>
                

                <!-- 연락처  -->                
                <div class="profileInfo" >
                  <img :src="require('@/assets/images/telephone-call.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <el-form-item prop="phone">
                    <el-input v-model="ruleForm.phone" placeholder="예) 01012345678" maxlength="12"/>
                  </el-form-item>
                </div>
                
                <!-- 생일 -->
                <div class="profileInfo">
                  <img :src="require('@/assets/images/birthday-cake.png')" alt="" style="height: 1em; padding-right: 1em;">
                  <span>{{profile.birth}}</span>
                </div>
              </div>

            </el-col>
            
          </el-row>

        </el-form>
      </el-col>
      <!-- 수정버튼 -->
  
      <el-form-item>
        <el-button type="success" round @click="submitForm(ruleFormRef)">저장하기</el-button>
      </el-form-item>
    </el-row>
</template>

<script setup>
import { reactive, ref, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'
import { profileUpdate } from '@/common/api/accountAPI.js'
import axios from "axios"

const store = useStore()
const ruleFormRef = ref()
const router = useRouter()

const profile = JSON.parse(localStorage.getItem('userInfo'))
console.log('받음',profile)

const ruleForm = reactive({
  nick: profile.nick,
  phone: profile.phone,
})

// 연락처 유효성 검사
const checkPhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('연락처를 입력해주세요.'))
  }else{
    ruleForm.phone = value.replace(/[^0-9]/g, '').replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
  }callback()
}

// 닉네임 유효성 검사
const validateNickname = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('닉네임을 입력해 주세요.'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    callback()
  }
}

// 프로필사진 업로드
const profileImg = ref('http://192.168.219.112:8080/image/'+profile.profile.filename)
const image = ref()

function uploadImg (){
  let profile = image.value.files[0];
  const url = URL.createObjectURL(profile);
  profileImg.value = url;
  // console.log(image.value.files[0])
}

const rules = reactive({
  nick: [{ validator: validateNickname, trigger: 'blur' }],
  phone: [{ validator: checkPhone, trigger: 'blur' }]
})

// 회원정보수정 제출
const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate( (valid) => {
    if (valid) {
      const config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };

      const updateInfo = {
        name: profile.name,
        email: profile.email,
        nick: ruleForm.nick,
        phone: ruleForm.phone,
        birth: profile.birth
      }
      const id = profile.id
      const frm = new FormData();
      frm.append("profile", image.value.files[0]);
      frm.append(
        "updateInfo", 
        new Blob([JSON.stringify(updateInfo)], {type: 'application/json'})
      );
      console.log('보내는 폼',frm);
      axios.put(`http://192.168.219.112:8080/api/v1/user/${id}/update`, frm, config)
      .then(res => {
        console.log('답',res.data)
        localStorage.setItem('userInfo', JSON.stringify(res.data));
        
        router.push("/");
        }).catch(err => {
          alert("실패");
          console.log(err);
        })

    } else {
      console.log('error submit!')
      return false
    }
  })
}

</script>

<style>

</style>
