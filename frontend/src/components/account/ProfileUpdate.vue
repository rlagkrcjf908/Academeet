<template>
  <div class="profile-container">
    <!-- 폼 시작 -->
    <div class="profile-img-form">
      <label for="joinProfile">
        <el-avatar class="profile-img" :src="profileImg" />
        <input
          ref="image"
          @change="uploadImg()"
          type="file"
          id="joinProfile"
          accept="image/*"
          hidden
        />
      </label>
      <!-- 유저이름 -->
      <p>{{ profile.name }}</p>
    </div>

    <div class="profileInfo-box">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        status-icon
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
      >
        <!-- 이메일 -->
        <div class="profileInfo">
          <img
            :src="require('@/assets/images/mail.png')"
            alt=""
            style="height: 1em; padding-right: 1em"
          />
          <span>{{ profile.email }}</span>
        </div>

        <!-- 닉네임 -->
        <div class="profileInfo">
          <img
            :src="require('@/assets/images/id-card.png')"
            alt=""
            style="height: 1em; padding-right: 1em"
          />
          <el-form-item prop="nickname">
            <el-input
              v-model.trim="ruleForm.nick"
              type="text"
              autocomplete="off"
              placeholder="닉네임을 입력해 주세요."
              maxlength="45"
            />
          </el-form-item>
        </div>

        <!-- 연락처  -->
        <div class="profileInfo">
          <img
            :src="require('@/assets/images/telephone-call.png')"
            alt=""
            style="height: 1em; padding-right: 1em"
          />
          <el-form-item prop="phone">
            <el-input
              v-model="ruleForm.phone"
              placeholder="예) 01012345678"
              maxlength="12"
            />
          </el-form-item>
        </div>

        <!-- 생일 -->
        <div class="profileInfo">
          <img
            :src="require('@/assets/images/birthday-cake.png')"
            alt=""
            style="height: 1em; padding-right: 1em"
          />
          <span>{{ profile.birth }}</span>
        </div>
      </el-form>
    </div>
  </div>

  <!-- 수정버튼 -->
  <el-button type="success" round @click="submitForm(ruleFormRef)"
    >저장하기</el-button
  >
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import axios from "axios";

const store = useStore();
const ruleFormRef = ref();
const router = useRouter();

const profile = JSON.parse(localStorage.getItem("userInfo"));

const ruleForm = reactive({
  nick: profile.nick,
  phone: profile.phone,
});

// 연락처 유효성 검사
const checkPhone = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("연락처를 입력해주세요."));
  } else {
    ruleForm.phone = value
      .replace(/[^0-9]/g, "")
      .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
  }
  callback();
};

// 닉네임 유효성 검사
const validateNickname = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("닉네임을 입력해 주세요."));
  } else {
    if (value.replace(" ", "") !== value) {
      callback(new Error("공백은 입력할 수 없습니다."));
    }
    callback();
  }
};

// 프로필사진 업로드
const profileImg = ref(
  "http://i8d108.p.ssafy.io/img/" + profile.profile.filename
);
const image = ref();

function uploadImg() {
  let profile = image.value.files[0];
  const url = URL.createObjectURL(profile);
  profileImg.value = url;
}

const rules = reactive({
  nick: [{ validator: validateNickname, trigger: "blur" }],
  phone: [{ validator: checkPhone, trigger: "blur" }],
});

// 회원정보수정 제출
const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      const config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      const updateInfo = {
        name: profile.name,
        email: profile.email,
        nick: ruleForm.nick,
        phone: ruleForm.phone,
        birth: profile.birth,
      };
      const id = profile.id;
      const frm = new FormData();

      if (image.value.files[0] !== undefined) {
        frm.append("profile", image.value.files[0]);
      }

      frm.append(
        "updateInfo",
        new Blob([JSON.stringify(updateInfo)], { type: "application/json" })
      );

      axios
        .put(
          `https://i8d108.p.ssafy.io/api/v1/user/${id}/update`,
          frm,
          config
        )
        .then((res) => {
          localStorage.setItem("userInfo", JSON.stringify(res.data));
          router.push("/profile");
        })
        .catch((err) => {
          ElMessage({
            showClose: true,
            message: "수정 실패했습니다.",
            type: "error",
          });
          console.log(err);
        });
    } else {
      ElMessage({
        showClose: true,
        message: "수정 실패했습니다.",
        type: "error",
      });
      console.log("error submit!");
      return false;
    }
  });
};
</script>

<style>
.profile-img-form {
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  /* align-items: center; */
}
</style>
