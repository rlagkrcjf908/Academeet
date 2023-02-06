<template>
  <div class="joinBox">
    <!-- <div v-if="state.user.id">
      로그인했으면 가입을 못 하지!!!
    </div>
    <div v-else> -->
      <router-link to="/login">AcadeMeet</router-link>
      <hr>
      <!-- 이메일 중복없음이 확인되면 일단 새로 입력하는 건 막는다 -->
      <div id="Email">
        <div v-if = "isDupli">
          <div v-if = "isCheck">
            <label for="joinEmail">
              <span>이메일 </span>
              <input type="text" id="joinEmail" v-model="email" readonly/>
              <h5>확인완료</h5>
            </label>
          </div>
          <div v-else>
            <label for="joinEmail">
              <span>이메일 </span>
              <input type="text" id="joinEmail" v-model="email" readonly/>
              <button @click="reCheck()">인증번호 재발급</button>
            </label>
          </div>
        </div>
        <div v-else>
          <label for="joinEmail">
            <span>이메일 </span>
            <input type="text" id="joinEmail" v-model="email" />
            <button @click="dupliCheck()">인증번호 발급받기</button>
          </label>
        </div>
      </div>
      <hr>
      <div id="Auth">
        <div v-if = "isCheck">
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
      <!-- 엘리먼트 적용시키면서 패스워드 일치 걸어줘야됨 -->
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
        <input type="text" id="joinPhone" placeholder="01234567890" v-model="phone" />
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
          value-format="YYYY-MM-DD"
          type="date"
          placeholder="날짜를 고르세요"
          :size="size"
        />
      </label>
      <hr>
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
      <hr>
      <button @click="submit()">가입하기</button>
    <!-- </div> -->
  </div>
  
</template>

<script>
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
      axios.post("http://192.168.100.191:8080/api/v1/user/login/idCheck", JSON.stringify(param), {
        headers: { 'content-type': 'application/json' }})
      .then(res => {
        console.log(res);
        if (res.data === 1) {
          axios.put("http://192.168.100.191:8080/api/v1/user/email", this.email)
          alert("인증번호가 전송되었습니다.")
          this.isDupli = true
        } else {
          alert("이미 가입되거나 유효하지 않은 이메일입니다.")
        }
      })
    },
    reCheck(){
      axios.put("http://192.168.100.191:8080/api/v1/user/email", this.email)
      alert("인증번호가 전송되었습니다.")
    },
    authCheck(){
      // const parauth = {
      //   "authPin": this.authPin
      // }
      console.log(this.authPin);
      axios.post("http://192.168.100.191:8080/api/v1/user/emailCheck", this.authPin)
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
        axios.post("http://192.168.100.191:8080/api/v1/user", frm, config)
        
        // $.ajax({
        //   url: "http://192.168.0.8:8080/",
        //   data: frm,
        //   contentType: false,
        //   processData: false,
        //   enctype: 'multipart/form-data',
        // })
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
      
      // const joinImage = new FormData();

      // joinData.append("file", data.file);
      // joinData.append(
      //   "key",
      //   new Blob([JSON.stringify(data.info)], { type: "application/json" })
      // );

      // joinData.append("email", this.email);
      // joinData.append("password", this.password);
      // joinData.append("name", this.name);
      // joinData.append("phone", this.phone);
      // joinData.append("nick", this.nick);
      // joinData.append("birth", this.birth);
      // joinData.append("profile", this.profile);
      
      // console.log(joinData)
      // if (this.isAuth = true && this.password === this.passwordCheck) {
      //   // axios.post("http://192.168.100.191:8080/api/v1/user", joinData, joinImage,{
      //   //   headers: {'Content-Type': 'multipart/form-data'},
      //   //   headers: {'content-Type': 'resgister'}

      //   // })
      //   try{
      //     axios.post("http://192.168.100.191:8080/api/v1/user", joinData,{
      //       headers: {'Content-Type': 'multipart/form-data'},
      //     })
      //     .then((res => console.log(res)))
      //   } catch (err) {
      //     console.log(err);
      //   }
        // .then(() => {
        //   alert("성공")
        // })
        // .catch(err => {
        // })
      // }
    }
  },
} 
</script>
<style>

</style>