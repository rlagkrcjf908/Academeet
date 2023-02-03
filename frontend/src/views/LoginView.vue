<template>
  <div class="loginBox">
    <!-- <div v-if="state.token">
      <h1>잘못된접근입니다.</h1>
      <button @click="goMain()">메인으로돌아가기</button>
    </div>
    <div v-else> -->
      <label for="loginEmail">
        <span>아이디</span>
        <input type="text" id="loginEmail" v-model="email" />
      </label>
      <label for="loginPw">
        <span>패스워드</span>
        <input type="password" id="loginPw" v-model="password" />
      </label>
      <hr />
      <button @click="submit()">로그인</button>
      <hr>
      <h5 @click="goJoin()">회원가입</h5>
    <!-- </div> -->
  </div>
</template>

<script>
import axios from "axios";
import { reactive } from "vue";
import router from "../router/index";
export default {
  name: "LoginView",
  data () {
    return {
      email: "",
      password: "",
    }
  },
  methods: {
    submit(){
      if (this.email && this.password){
        const email = this.email
        const password = this.password
        this.$store.dispatch('login', {email, password})
      } else {
        alert("이메일 또는 비밀번호가 입력되지 않았습니다.")
        return false
      }
    },
    goMain(){
      router.push('/')
    },
    goJoin(){
      router.push('/join')
    },
  }
}
</script>

<style>

</style>
</style>