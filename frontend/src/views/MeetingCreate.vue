<template>
  <el-form
    label-position="top"
    ref="ruleFormRef"
    :model="ruleForm"
    status-icon
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm meeting-create"
  >

    <div class="meeting-create-form">
      <!-- 회의이름 -->
      <el-form-item label="회의이름" prop="groupName" required>
        <el-input
          v-model="ruleForm.groupName"
          type="text"
          autocomplete="off"
          placeholder="회의이름을 입력해 주세요."
          maxlength="45"
        />
      </el-form-item>
      <!-- 시간 -->
      <div>

        <!-- 회의날짜 -->
        <el-form-item prop="date" label="회의 날짜">
          <el-date-picker
            v-model="ruleForm.date"
            type="date"
            style="width: 100%"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
            min="2000-01-01" 
            max="2500-12-31"
            placeholder="Pick a Date"
          />
        </el-form-item>

        <!-- 시작시간 -->
        <el-form-item label="회의 시작 시간" required prop="startTime">
          <el-time-select
            v-model="ruleForm.startTime"
            class="mr-4"
            start="00:00"
            step="00:15"
            end="24:00"
            id="startTime"
          />
        </el-form-item>
        <!-- 마치는 시간 -->
        <el-form-item label="회의 마치는 시간" required prop="endTime">
          <el-time-select
            v-model="ruleForm.endTime"
            :min-time="startTime"
            start="00:00"
            step="00:15"
            end="24:00"
            id="endTime"
          />
        </el-form-item>
      </div>
    </div>

    <div class="meeting-create-form">
      <!-- 그룹선택 -->
      <!-- 내가 호스트인 그룹만 데려옴 -->
      <el-form-item label="그룹 선택" prop="group">
        <el-radio-group v-model="ruleForm.group" @change="selectGroup">
          <el-radio label="선택안함" name="no" />
          <div v-for="(item, index) in groupList" :key="index">
            <el-radio :label="item?.id" style="margin-right:2em">{{ item?.name }}</el-radio>
          </div>
        </el-radio-group>
      </el-form-item>
      <!-- 그룹 선택 안할 시 유저 검색 -->
      <el-form-item label="멤버 선택" prop="user" v-if="!isSelectGroup">
        <el-select
          v-model="ruleForm.user"
          multiple
          filterable
          remote
          reserve-keyword
          placeholder="Please enter a keyword"
          :remote-method="remoteMethod"
          :loading="loading"
          clearable
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </div>
  </el-form>
  <!-- 회의 생성 버튼 -->
    <el-button type="success" round @click="submitForm(ruleFormRef)">미팅생성</el-button>
</template>

<script setup>
import { hostGroup, userSearch } from "@/common/api/meetingAPI";
import { ref, reactive, onMounted } from "vue";
import { toRaw } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

const router = useRouter();
const store = useStore();
const ruleFormRef = ref();

const userid = store.state.accountStore.userId;
// 유효성 검사할 항목 이름
const ruleForm = reactive({
  groupName: "",
  date: "",
  startTime: "",
  endTime: "",
  group: "",
  guest: [],
  user: [],
});

// 회의이름 유효성 검사
const validategroupName = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("그룹이름을 입력해 주세요."));
  } else {
    // if (value.replace(' ','') !== value){
    //   callback(new Error('공백은 입력할 수 없습니다.'))
    // }
    callback();
  }
};

// 시간 입력
const startTime = ref("");
const endTime = ref("");
const groupList = ref();
// 내가 호스트인 그룹 목록 불러오는 엑시오스
onMounted(async () => {
  const res = await hostGroup(userid);
  groupList.value = res.data;
});

// 그룹선택
const isSelectGroup = ref(true);

const selectGroup = (data) => {
  if (data === "선택안함") {
    isSelectGroup.value = false;
  } else {
    isSelectGroup.value = true;
  }
};

// 유저검색
// const list = ref([])
const options = ref([]);
const user = ref([]);
const loading = ref(false);

// 유저 검색
const remoteMethod = (query) => {
  if (query !== "") {
    loading.value = true;
    setTimeout(async () => {
      // 검색요청
      const username = { "name" : query };
      const res = await userSearch(JSON.stringify(username));

      const searchUserList = res.data;

      let list = searchUserList.filter(item =>item.id !== userid).map((item) => {
        return { value: item.id, label: `${item.name}:${item.email}` }
      })

      loading.value = false;
      options.value = list;

      // 요청결과 리스트
    }, 200);
  } else {
    options.value = [];
  }
};

// 유효성 검사 규칙
const rules = reactive({
  groupName: [{ validator: validategroupName, trigger: "blur" }],
  group: [{ required: true, message: "그룹을 선택하세요", trigger: "change" }],
  date: [
    { required: true, message: "회의 날짜를 선택하세요", trigger: "change" },
  ],
  startTime: [
    { required: true, message: "시작 시간을 선택하세요", trigger: "change" },
  ],
  endTime: [
    { required: true, message: "마치는 시간을 선택하세요", trigger: "change" },
  ],
  user: [{ required: true, message: "참여자를 선택하세요", trigger: "change" }],
});

// 회의 생성
const submitForm = (formEl) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      // 보낼 회의 정보
      const meetingData = {
        userid: userid,
        name: ruleForm.groupName,
        starttime: `${ruleForm.startTime}:00`,
        endtime: `${ruleForm.endTime}:00`,
        date: ruleForm.date,
      };
      if (isSelectGroup.value === false) {
        const rawArray = toRaw(ruleForm.user);
        const guestList = rawArray;
        meetingData.users = guestList;
      } else {
        rules.user[0].required = false;
        const guestList = ruleForm.group;
        meetingData.groupid = guestList;
      }
      console.log(meetingData);
      try {
        await store.dispatch("meetingStore/meetingCreateAction", meetingData);
        router.push("/");
        console.log("submit!");
      } catch (error) {
        console.log(error);
      }
    } else {
      console.log("error submit!");
      return false;
    }
  });
};
</script>

<style>
.meeting-create{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-content: center;
  flex-wrap: wrap;
}

.meeting-create-form{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  /* border: 1px solid green; */
  padding: 8vw;
}
</style>