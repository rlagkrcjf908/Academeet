<template>
  <div class="group-create">
    <p
      style="
        color: rgba(97, 178, 153, 1);
        border-bottom: 1px solid rgba(217, 217, 217, 1);
      "
    >
      그룹생성
    </p>

    <el-form
      label-position="top"
      ref="ruleFormRef"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
    >
      <!-- 그룹 이름 생성 폼  -->
      <el-form-item label="그룹이름" prop="groupName">
        <el-input
          v-model="ruleForm.groupName"
          type="text"
          autocomplete="off"
          placeholder="그룹이름을 입력해 주세요."
          maxlength="15"
        />
      </el-form-item>
      <!-- 유저 검색 -->
      <el-form-item label="멤버" prop="user">
        <el-select
          v-model="ruleForm.user"
          multiple
          filterable
          remote
          reserve-keyword
          placeholder="초대하고 싶은 멤버 이름을 검색해주세요"
          :remote-method="remoteMethod"
          :loading="loading"
          clearable
          style="width: 292px"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            class="searchOption"
          />
        </el-select>
      </el-form-item>
      <!-- 그룹 생성 버튼 -->
      <el-button type="success" round @click="submitForm(ruleFormRef)"
        >그룹생성</el-button
      >
    </el-form>
  </div>
</template>

<script setup>
import { userSearch } from "@/common/api/groupAPI";
import { reactive, ref } from "vue";
import { toRaw } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

const router = useRouter();
const store = useStore();
const userid = store.state.accountStore.userId;
const ruleFormRef = ref();
const groupList = store.state.groupStore.groupList;

// 그룹이름 중복확인


// 그룹이름 유효성 검사
const validategroupName = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("그룹이름을 입력해 주세요."));
  } else {
    function isExistName(item)  {
      if(item.name === value)  {
        return true;
      }
    }
    let existName = groupList.find(isExistName)
    if( existName ){
      console.log('existName////////////',existName)
      callback(new Error("이미 존재하는 그룹 이름 입니다."));
    }else{
      callback();
    }
  }
};

// 유저검색
const options = ref([]);
const user = ref([]);
const loading = ref(false);

// 회의 참여자 목록
let guestList = null;

// 유저 검색
const remoteMethod = (query) => {
  if (query !== "") {
    loading.value = true;
    setTimeout(async () => {
      // 검색요청
      const username = { name: query };
      const res = await userSearch(JSON.stringify(username));

      const searchUserList = res.data;

      let list = searchUserList
        .filter((item) => item.id !== userid)
        .map((item) => {
          return { value: item.id, label: `${item.name}:${item.email}` };
        });

      loading.value = false;
      options.value = list;

      // 요청결과 리스트
    }, 200);
  } else {
    options.value = [];
  }
};

const ruleForm = reactive({
  groupName: "",
  user: [],
});

// 유효성 검사 규칙
const rules = reactive({
  groupName: [
    { required: true, validator: validategroupName, trigger: "blur" },
  ],
  user: [{ required: true, message: "멤버를 선택하세요", trigger: "change" }],
});

// 그룹생성
const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      // 유저정보는 받은 그대로
      const rawArray = toRaw(ruleForm.user);
      guestList = rawArray;

      const groupData = {
        userid: userid,
        name: ruleForm.groupName,
        users: guestList,
      };
      await store.dispatch("groupStore/groupCreateAction", groupData);
      router.push("/group");
      // console.log('submit!')
    } else {
      console.log("error submit!");
      return false;
    }
  });
};
</script>

<style>
.group-create {
  margin: auto;
  padding-bottom: 180px;
}
</style>
