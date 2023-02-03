<template>
  <div class="joinBox">
    <!-- <div v-if="state.user.id">
      로그인했으면 가입을 못 하지!!!
    </div>
    <div v-else> -->
      <router-link to="/">AcadeMeet</router-link>
      <hr>
      <!-- 이메일 중복없음이 확인되면 일단 새로 입력하는 건 막는다 -->
      <div id="Email">
        <div v-if = "isdupli">
          <div v-if = "isAuth">
            <label for="joinEmail">
              <span>이메일 </span>
              <input type="email" id="joinEmail" v-model="email" readonly/>
              <h5>확인완료</h5>
            </label>
          </div>
          <div v-else>
            <label for="joinEmail">
              <span>이메일 </span>
              <input type="email" id="joinEmail" v-model="email" readonly/>
              <button @click="reCheck()">인증번호 재발급</button>
            </label>
          </div>
        </div>
        <div v-else>
          <label for="joinEmail">
            <span>이메일 </span>
            <input type="email" id="joinEmail" v-model="email" />
            <button @click="dupliCheck()">인증번호 발급받기</button>
          </label>
        </div>
      </div>
      <div id="Auth">
        <div v-if = "isAuth">
          <label for="joinAuth">
            <span>인증번호</span>
            <input type="text" id="joinAuth" v-model="authPin" readonly/>
            <h5>인증완료</h5>
          </label>
        </div>
        <div v-else>
          <label for="joinAuth">
            <span>인증번호</span>
            <input type="text" id="joinAuth" v-model="authPin"/>
            <button @click="authCheck()">인증하기</button>
          </label>
        </div>
      </div>
      <hr>
      <label for="joinPw">
        <span>패스워드 </span>
        <input type="password" id="joinPw" v-model="password" />
      </label>
      <hr>
      <label for="joinCheck">
        <span>패스워드확인 </span>
        <input type="password" id="joinCheck" v-model="passwordCheck" />
      </label>
      <hr>
      <label for="joinName">
        <span>이름 </span>
        <input type="text" id="joinName" v-model="name" />
      </label>
      <hr>
      <label for="joinPhone">
        <span>연락처 </span>
        <input type="text" id="joinPhone" placeholder="012-3456-7890" v-model="phone" />
      </label>
      <hr>
      <label for="joinNick">
        <span>닉네임 </span>
        <input type="text" id="joinNick" v-model="nick" />
      </label>
      <hr>
      <label for="joinBirth">
        <span>생일 </span>
        <el-date-picker
          v-model="birth"
          type="date"
          placeholder="날짜를 고르세요"
          :size="size"
        />
      </label>
      <hr>
      <label for="joinProfile">
        <el-image
          style="width: 100px; height: 100px;"
          :src="onFileSelected()"
          :preview-src-list="srcList"
          :initial-index="1"
          fit="cover"
        />
        <input type="file" id="profile" accept="image/*">
      </label>
      <hr>
      <button @click="submit()">가입하기</button>
    <!-- </div> -->
  </div>
  
</template>

<script>
import { reactive } from "vue";
import router from "../router/index";
import axios from 'axios'
export default {
  name: "JoinView",
  data () {
    return{
      isDupli : false,
      isAuth : false,
      email: "",
      password: "",
      name: "",
      phone: "",
      nick: "",
      birth: "",
      profile: "",
      authPin: "",
    }
  },

  methods: {
    async dupliCheck(){
      let data = await axios.get("http://localhost:8080/api/v1/user/login/idCheck")
      console.log(data)
      .then(res => {
        
      })
      const params = {
        "email": this.email
      }
    },
    reCheck(){
      const params = {
        "email": this.email
      }
    },
    authCheck(){
      const params = {

      }
    },
    onFileSelected(event){
      this.profile
    },
    submit() {
      const params = {
        "email": this.email,
        "password": this.password,
        "name": this.name,
        "phone": this.phone,
        "nick": this.nick,
        "birth": this.birth,
        "profile": this.profile,
      }
      axios.post("http://localhost:8080/signup", JSON.stringify(params), {
          headers: { 'content-type': 'application/json' }
      }).then(() => {
        alert("회원가입이 정상적으로 완료되었습니다.")
        router.push("/")
      }).catch(() =>{
        alert("문제가 있습니다.")
      })
    }
  },
} 
</script>

<style>
</style>