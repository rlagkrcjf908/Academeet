<template>
  <!-- 여기부턴 아진 폼 -->

  
  
  <el-form
  ref="ruleFormRef"
  :model="ruleForm"
  status-icon
  :rules="rules"
  label-width="120px"
  class="demo-ruleForm"
  >

  <!-- 이메일 -->
  <el-form-item prop="email" label="이메일" >
    <el-input v-model.trim="ruleForm.email" type="email" autocomplete="off" placeholder="이메일을 입력해 주세요." maxlength="100" :readonly="isDupli"/>
  </el-form-item>
  <!-- 클래스 삼항? 으로 class readonly 넣기 -->
  <button @click.prevent="dupliCheck()" v-if="!isCheck && !isDupli">인증코드 발급</button>
  <button @click.prevent="reCheck()" v-if = "isDupli">인증코드 재발급</button>
  
  <!-- 인증코드 -->
  <el-form-item prop="authPin" label="인증코드">
    <el-input v-model.trim="ruleForm.authPin" type="text" autocomplete="off" placeholder="인증코드를 입력해 주세요." maxlength="100" :readonly="isCheck"/>
    <button @click.prevent="authCheck()">인증하기</button>
    <h5 v-if = "isCheck">확인완료</h5>
  </el-form-item>

  <!-- 이름 -->
  <el-form-item prop="name" label="이름">
    <el-input v-model.trim="ruleForm.name" type="text" autocomplete="off" placeholder="이름을 입력해 주세요." maxlength="45"/>
  </el-form-item>

    <!-- 연락처 -->
    <el-form-item prop="phone" label="연락처">
      <el-input v-model.number="ruleForm.phone" placeholder="예) 01012345678" maxlength="12"/>
    </el-form-item>

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


    <label for="joinProfile">
        <span>프로필 설정</span>
        <hr>
        <!-- 이미지업로드는 되나 세로가 더 길면 사이드가 짤려 나감 -->
        <el-avatar
          :size = "300"
          :src = "profile"     
        />
        <input ref="image" @change="uploadImg()" type="file" id="joinProfile" accept="image/*"/>
        <img src="profile" alt="">
        
      </label>






    <!-- 회원가입 -->
    <el-button type="success" round @click="submitForm(ruleFormRef)">회원가입</el-button>
  </el-form>
  
</template>

<!-- <script>
import { ref } from "vue";
import router from "../router/index";
import axios from 'axios'

export default {
  name: "JoinView",
  data () {
    return{
      isDupli : false,
      isCheck : false,
      email: "",
      password: "",
      passwordCheck: "",
      name: "",
      phone: "",
      nick: "",
      birth: "",
      profile: "",
      authPin: "",
    }
  },

  methods: {
    uploadImg(){
      var profile = this.$refs["image"].files[0];
      const url = URL.createObjectURL(profile);
      this.profile = url;
      console.log(this.$refs["image"].files[0])
    },

    dupliCheck(){
      const param = {
        "email": this.email,
      }
      console.log(this.email);
      axios.post("http://172.30.1.26:8080/api/v1/user/login/idCheck", JSON.stringify(param), {
        headers: { 'content-type': 'application/json' }})
      .then(res => {
        console.log(res);
        if (res.data === 1) {
          axios.put("http://172.30.1.26:8080/api/v1/user/email", this.email)
          alert("인증번호가 전송되었습니다.")
          this.isDupli = true
        } else {
          alert("이미 가입되거나 유효하지 않은 이메일입니다.")
        }
      })
    },
    reCheck(){
      axios.put("http://172.30.1.26:8080/api/v1/user/email", this.email)
      alert("인증번호가 전송되었습니다.")
    },
    authCheck(){
      // const parauth = {
      //   "authPin": this.authPin
      // }
      console.log(this.authPin);
      axios.post("http://172.30.1.26:8080/api/v1/user/emailCheck", this.authPin)
      .then(res => {
        if (res.status === 200) {
          alert("인증이 성공적으로 완료되었습니다.");
          this.isCheck = true
        } else {
          alert(res.status)
        }
      }).catch(err => {
        console.log(err);
      })
    },
    submit() {
      const config = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };

      const registerInfo = {
        email: this.email,
        password: this.password,
        name: this.name,
        phone: this.phone,
        nick: this.nick,
        birth: this.birth,
      };

      const frm = new FormData();
      frm.append("profile", this.$refs["image"].files[0]);
      frm.append(
        "registerInfo", 
        new Blob([JSON.stringify(registerInfo)], {type: 'application/json'})
      );
      for(let value of frm.values())
        console.log(value)
      if (this.isCheck === true && this.password === this.passwordCheck) {
        axios.post("http://172.30.1.26:8080/api/v1/user", frm, config)
      
        .then(res => {
          if (res.status === 200) {
            alert("가입이 완료되었습니다.\n로그인창으로이동합니다 ");
            router.push("/login");
          } else {
            alert(res.status)   
          }
        }).catch(err => {
          console.log(err);
        })
      } else if (this.isCheck === true && this.password!== this.passwordCheck) {
        alert("비밀번호가 일치하지 않습니다.");
      } else {
        alert("인증이 필요합니다.");
      }
    }
  },
} 
</script> -->

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
// import { inject } from "vue";
import axios from "axios"
// const axios = inject('axios');

const store = useStore()
const ruleFormRef = ref()
const router = useRouter()

const profile = ref()
const image = ref()

// 이미지 업로드
function uploadImg (){
  let profile = image.value.files[0];
  console.log(profile)
  const url = URL.createObjectURL(profile);
  profile.value = url;
  console.log(image.value.files[0])
  console.log(profile.value)
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

const isDupli = ref(false)
const isCheck = ref(false)

// 이메일 유효성 검사
const validateEmail = (rule, value, callback) => {
  // const reg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
  const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
  if (value === '') {
    callback(new Error('이메일을 입력해주세요'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    if (regExp.test(value) === false) {
      callback(new Error('올바른 이메일을 입력해주세요'))
    }
    callback()
  }
}

// 인증코드 확인
const authCheck = () => {
  console.log(ruleForm.authPin);
  axios.post("http://172.30.1.26:8080/api/v1/user/emailCheck", ruleForm.authPin)
  .then(res => {
    if (res.status === 200) {
      alert("인증이 성공적으로 완료되었습니다.");
      isCheck.value = true
    } else {
      alert(res.status)
    }
  }).catch(err => {
    console.log(err);
  })
}

// 인증코드 재발급
const reCheck = () => {
  console.log('ruleForm.email',ruleForm.email)
  axios.put("http://172.30.1.26:8080/api/v1/user/email", ruleForm.email)
  alert("인증번호가 전송되었습니다.")
  }

// 인증코드 발급
const dupliCheck= () => {
  console.log('ruleForm.email',ruleForm.email)
  const param = {
    "email": ruleForm.email,
  }
  axios.post("http://172.30.1.26:8080/api/v1/user/login/idCheck", JSON.stringify(param), {
    headers: { 'content-type': 'application/json' }})
  .then(res => {
    console.log(res);
    if (res.data === 1) {
      axios.put("http://172.30.1.26:8080/api/v1/user/email", ruleForm.email)
      alert("인증번호가 전송되었습니다.")
      isDupli.value = true
    } else {
      alert("이미 가입되거나 유효하지 않은 이메일입니다.")
    }
  })
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
  authPin : [{ required : true, message : '인증번호를 입력해 주세요.', trigger : 'blur' }],
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
      for(let value of frm.values())
        console.log(value)
      axios.post("http://172.30.1.26:8080/api/v1/user", frm, config)
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

<style>

</style>