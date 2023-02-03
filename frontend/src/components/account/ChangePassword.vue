<template>
  <el-row justify="center" class="profile-container">
    <el-col :span="10">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        status-icon
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
      >
        <!-- 현재 비밀번호 -->
        <el-form-item label="현재 비밀번호" prop="currentpass">
          <el-input v-model.trim="ruleForm.currentpass" type="password" autocomplete="off" placeholder="현재 비밀번호를 입력해주세요"/>
        </el-form-item>
        <!-- 새 비밀번호 -->
        <el-form-item label="새 비밀번호" prop="pass">
          <el-input v-model.trim="ruleForm.pass" type="password" autocomplete="off" placeholder="새로운 비밀번호를 입력해주세요"/>
        </el-form-item>
        <!-- 새 비밀번호 확인 -->
        <el-form-item label="새 비밀번호 확인" prop="checkPass">
          <el-input
            v-model.trim="ruleForm.checkPass" type="password" autocomplete="off" placeholder="비밀번호 확인을 위해 한번 더 입력해주세요"/>
        </el-form-item>
        <!-- 변경 -->
        <el-button type="success" round @click="submitForm(ruleFormRef)">변경완료</el-button>
      </el-form>
    </el-col>
  </el-row>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { checkPassword, changePassword } from '@/common/api/accountAPI.js'
import { useRouter } from 'vue-router';
import { useStore } from 'vuex'
const store = useStore()
const router = useRouter();
const ruleFormRef = ref()
const isChecked = ref(false)
const id = store.state.accountStore.id
// 현재 비밀번호 확인
const validateCurrentpass = async (rule, value, callback) => {
  if (value === '') {
    callback(new Error('현재 비밀번호를 입력해주세요.'))
  } else {
      const payload = {
        "password": value
      }
      const res = await checkPassword(id, payload)
      isChecked.value = res.data
  }
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    if (!isChecked.value) {
      callback(new Error('현재 비밀번호와 일치하지 않습니다!'))
    }else{
      callback()
    }
  }


const validatePass = (rule, value, callback) => {
  const reg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
  if (value === '') {
    callback(new Error('새로운 비밀번호를 입력해주세요'))
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

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('비밀번호 확인을 위해 한번 더 입력해주세요'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    if (value !== ruleForm.pass) {
      callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
    } else {
      callback()
    }
  }
}

const ruleForm = reactive({
  currentpass: '',
  pass: '',
  checkPass: ''
})

const rules = reactive({
  currentpass: [{ validator: validateCurrentpass, trigger: 'blur' }],
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }]
})

const submitForm = (formEl) => {
  if (!formEl) return
    formEl.validate( async (valid) => {
    if (valid) {
      console.log(valid)
      console.log(ruleForm.pass)
      console.log('submit!')
      const password = {
        "password" : ruleForm.pass
      }
      try {
        await changePassword(id, password)
        router.push({ name: 'profile' })
        console.log('submit!')
      }
      catch (error) {
        console.log(error)
      }
    }
    else {
      console.log('error submit!')
      return false
    }
  })
}


</script>

<style>
.profile-container{
  display: flex;
  flex-direction: column;
}
</style>