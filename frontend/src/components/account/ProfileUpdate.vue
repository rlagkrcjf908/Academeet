<template>
  <!-- 폼 시작 -->
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

            <el-col :span="4">
              <!-- 프로필사진 -->
              <el-upload
              class="avatar-uploader"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <p v-else class="avatar-uploader-icon">+</p>
              </el-upload>
              <!-- 유저이름 -->
              <p>{{profile.name}}</p>

            </el-col>
            
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
                    <el-input v-model.number="ruleForm.phone" placeholder="예) 01012345678" maxlength="12"/>
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
// import { profileUpdate } from '@/common/api/accountAPI.js'
// import { onMounted } from 'vue'
import { useStore } from 'vuex'

const store = useStore()
const ruleFormRef = ref()
const router = useRouter()

const { ...profile } = toRefs(store.state.accountStore.profile)

const ruleForm = reactive({
  nick: profile.nick,
  phone: profile.phone,
})

// onMounted (() => {
//       store.dispatch('accountStore/requestProfileAction', store.state.userid)
      
//     })


// 연락처 유효성 검사
const checkPhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('연락처를 입력해주세요.'))
  }
  setTimeout(() => {
    if (!Number.isInteger(value)) {
      callback(new Error('숫자만 입력해주세요.'))
    } else {
      if (String(value).length < 7) {
        callback(new Error('번호를 확인해주세요.'))
      } else {
        callback()
      }
    }
  }, 1000)
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


const rules = reactive({
  nick: [{ validator: validateNickname, trigger: 'blur' }],
  phone: [{ validator: checkPhone, trigger: 'blur' }]
})
// 회원정보수정 제출
const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      const profileData = {
        "img": profile.img.value,
        "name": profile.name.value,
        "email": profile.email.value,
        "nick": ruleForm.nick,
        "phone": ruleForm.phone,
        "birth": profile.birth.value
      }
      await store.dispatch('accountStore/profileUpdateAction', profileData)
        router.push({ name: 'profile' })
        // try {
          //   profileUpdate(data)
      //   router.push({ name: 'profile' })
      //   console.log('submit!')
      // }
      // catch (error) {
      //   console.log(error)
      // }
      // console.log(ruleForm.nickname)
      // console.log(ruleForm.phone)
      console.log('submit!')
    } else {
      console.log('error submit!')
      return false
    }
  })
}

// 프로필사진 업로드
const imageUrl = ref('profile.value.img')
const handleAvatarSuccess = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
  console.log('uploadFile:',uploadFile)
  console.log('업로드된이미지url1번:',imageUrl.value)

  console.log('imageUrl1번:',imageUrl)
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  console.log('업로드된이미지url:',imageUrl.value)
  console.log('imageUrl:',imageUrl)
  console.log('rawFile:',rawFile)

  return true
}
</script>


<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
