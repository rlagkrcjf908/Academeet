<template>
  <div class="join">
    <h1>
      <span style="color: #94d82d">A</span>cade<span style="color: #94d82d">M</span>eet
    </h1>

    <el-form
      label-position="top"
      ref="ruleFormRef"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm join-container"
      >
    <div class="join-item">
      <!-- 프로필사진 -->
      <label for="joinProfile">
        <!-- 이미지업로드는 되나 세로가 더 길면 사이드가 짤려 나감 -->
        <el-avatar
          :size = "300"
          :src = "profile"     
        />
        <input ref="image" @change="uploadImg()" type="file" id="joinProfile" accept="image/*" hidden/>
        <img src="profile" alt="">
      </label>

      <!-- 이름 -->
      <el-form-item prop="name" label="이름">
        <el-input v-model.trim="ruleForm.name" type="text" autocomplete="off" placeholder="이름을 입력해 주세요." maxlength="45"/>
      </el-form-item>

      <!-- 연락처 -->
      <el-form-item prop="phone" label="연락처">
        <el-input v-model="ruleForm.phone" placeholder="예) 01012345678" maxlength="12"/>
      </el-form-item>

    </div>

    <div class="join-item">

      <div class="auth-item">
        <!-- 이메일 -->
        <el-form-item prop="email" label="이메일" >
          <el-input v-model.trim="ruleForm.email" type="email" autocomplete="off" placeholder="이메일을 입력해 주세요." maxlength="100" :readonly="isDupli"/>
        </el-form-item>
        <el-button type="info" plain size="small" @click.prevent="dupliCheck()" v-if="!isCheck && !isDupli">인증코드 발급</el-button>
        <el-button type="info" plain @click.prevent="dupliCheck()" v-if = "isDupli">인증코드 재발급</el-button>
      </div>

      <div class="auth-item">
        <!-- 인증코드 -->
        <el-form-item prop="authPin" label="인증코드">
          <el-input v-model.trim="ruleForm.authPin" type="text" autocomplete="off" placeholder="인증코드를 입력해 주세요." maxlength="100" :readonly="isCheck"/>
        </el-form-item>
        <el-button type="info" plain size="small" @click.prevent="authCheck()">인증하기</el-button>
        <h5 v-if = "isCheck">확인완료</h5>
      </div>

      <!-- 닉네임 -->
      <el-form-item prop="nick" label="닉네임">
        <el-input v-model.trim="ruleForm.nick" type="text" autocomplete="off" placeholder="닉네임을 입력해 주세요." maxlength="45"/>
      </el-form-item>

      <!-- 생일 -->
      <el-form-item prop="birth" label="생일">
        <el-date-picker
          v-model="ruleForm.birth"
          type="date"
          placeholder="날짜를 고르세요."
          style="width: 100%"
        />
      </el-form-item>

      <!-- 비밀번호 -->
      <el-form-item  prop="password" label="비밀번호">
        <el-input v-model.trim="ruleForm.password" type="password" autocomplete="off" placeholder="비밀번호를 입력해주세요"/>
      </el-form-item>

      <!-- 비밀번호 확인 -->
      <el-form-item  prop="checkPass" label="비밀번호 확인">
        <el-input v-model.trim="ruleForm.checkPass" type="password" autocomplete="off" placeholder="비밀번호를 다시 입력해주세요"/>
      </el-form-item>
    </div>
    </el-form>
  </div>
  
<!-- 회원가입 -->
<div>
  <el-button type="success"  @click="submitForm(ruleFormRef)">회원가입</el-button>
  <el-button type="success"  @click="$router.push('/')">로그인</el-button>
</div>

</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import axios from "axios"
import { ElMessage } from 'element-plus'

const store = useStore()
const ruleFormRef = ref()
const router = useRouter()

const profile = ref()
const image = ref()

// 이미지 업로드
function uploadImg (){
  let profileImg = image.value.files[0];
  console.log(profileImg)
  const url = URL.createObjectURL(profileImg);
  profile.value = url;
  console.log(image.value.files[0])
  console.log("이미지",profile.value)
}

const ruleForm = reactive({
  name : '',
  phone : '',
  nick : '',
  birth : '',
  email : '',
  authPin : '',
  password : '',
  checkPass : '',
})

// 이름, 닉네임 유효성 검사
const validateName = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('정보를 입력해 주세요.'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    callback()
  }
}

// 연락처 유효성 검사
const checkPhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('연락처를 입력해주세요.'))
  }else{
    ruleForm.phone = value.replace(/[^0-9]/g, '').replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
  }callback()
}

const isDupli = ref(false)
const isCheck = ref(false)

// 이메일 유효성 검사
const validateEmail = (rule, value, callback) => {
  const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
  if (value === '') {
    callback(new Error('이메일을 입력해주세요'))
  } else {
    if (regExp.test(value) === false) {
      callback(new Error('올바른 이메일을 입력해주세요'))
    }
    callback()
  }
}

// 인증코드입력 유효성 검사
const validateAuthPin = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('인증코드를 입력해주세요'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    if (!isCheck){
      callback(new Error('인증코드를 확인 해주세요'))
    }
    }else{
    callback()
    }
  }
}

// 인증코드 확인
const authCheck = () => {
  axios.post("http://192.168.100.191:8080/api/v1/user/emailCheck", ruleForm.authPin)
  .then(res => {
    if (res.status === 200) {
      ElMessage({
        showClose: true,
        message:'인증 되었습니다.',
        type: 'success',
      })
      isCheck.value = true
    } else {
      ElMessage({
        showClose: true,
        message:'일치하지 않는 코드입니다.',
        type: 'error',
      })
    }
  }).catch(err => {
    ElMessage({
        showClose: true,
        message:'인증 실패했습니다.',
        type: 'error',
      })
    console.log(err);
  })
}

// 인증코드 발급
const dupliCheck= () => {
  if (ruleForm.email){
    const param = {
      "email": ruleForm.email,
    }
    axios.post("http://192.168.100.191:8080/api/v1/user/login/idCheck", JSON.stringify(param), {
      headers: { 'content-type': 'application/json' }})
    .then(res => {
      console.log(res);
      if (res.data === 1) {
        axios.put("http://192.168.100.191:8080/api/v1/user/email", ruleForm.email)
        .then(res => {
          ElMessage({
            showClose: true,
            message:'인증번호가 전송되었습니다.',
            type: 'success',
          })
          isDupli.value = true
        })
        .catch(err => {
          console.log(err);
          ElMessage({
            showClose: true,
            message:'올바른 이메일을 입력해주세요',
            type: 'error',
          })
        })
      } else {
        ElMessage({
          showClose: true,
          message:'가입되었거나 유효하지 않은 이메일입니다.',
          type: 'error',
        })
      }
    })
  }else{
    ElMessage({
          showClose: true,
          message:'이메일을 입력해주세요.',
          type: 'warning',
        })
  }
}

// 비밀번호 유효성 검사
const validatePass = (rule, value, callback) => {
  const reg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
  if (value === '') {
    callback(new Error('비밀번호를 입력해주세요'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    if (reg.test(value) === false) {
      callback(new Error('영문,숫자,특수문자를 포함해 8자리를 입력하세요.'))
    } else {
      if (ruleForm.checkPass !== '') {
        if (!ruleFormRef.value) return
        ruleFormRef.value.validateField('checkPass', () => null)
      }
    }
    callback()
  }
}

// 비밀번호 일치 확인
const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('비밀번호 확인을 위해 한번 더 입력해주세요'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    if (value !== ruleForm.password) {
      callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
    } else {
      callback()
    }
  }
}

// 유효성 검사 룰
const rules = reactive({
  name : [{ validator : validateName, trigger : 'blur' }],
  phone : [{ validator : checkPhone, trigger : 'blur' }],
  nick : [{ validator : validateName, trigger : 'blur' }],
  birth : [{ required : true, message : '날짜를 입력해 주세요.', trigger : 'change',  }],
  email : [{ validator : validateEmail, trigger : 'blur' }],
  authPin : [{ validator : validateAuthPin, trigger : 'blur' }],
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }]
})

// 회원가입 제출
const submitForm = (formEl) => {
  if (!formEl) return
    formEl.validate( (valid) => {
    if (valid) {
      const config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };

      const registerInfo = {
        name : ruleForm.name,
        phone : ruleForm.phone,
        nick : ruleForm.nick,
        birth : ruleForm.birth,
        email : ruleForm.email,
        password : ruleForm.password,
      };

      const frm = new FormData();
      frm.append("profile", image.value.files[0]);
      frm.append(
        "registerInfo", 
        new Blob([JSON.stringify(registerInfo)], {type: 'application/json'})
      );

      axios.post("http://192.168.100.191:8080/api/v1/user", frm, config)
      .then(res => {
          if (res.status === 200) {
            alert("가입이 완료되었습니다.\n로그인창으로이동합니다 ");
            router.push("/login");
          } else {
            alert(res.status)   
          }
        }).catch(err => {
          alert("회원가입에 실패했습니다.");
          console.log(err);
        })
    }else {
      console.log('error submit!')
      return false
    }
  })
}
</script>

<style scoped>

.join{
  width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.join-container{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 80vw;
  margin: auto;

  /* height: 100vh; */
}

.join-item{
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 30vw;
  margin: 8px auto;
}

.auth-item{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin-bottom: 0;
}

.auth-item>.el-button {
  padding: 0;
  margin: 0;
}
</style>