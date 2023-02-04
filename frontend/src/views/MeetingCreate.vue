<template>
    <el-form
    ref="ruleFormRef"
    :model="ruleForm"
        status-icon
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
        >
      <div style="border: 1px solid black; padding: 40px;">
        <!-- 회의이름 -->
        <el-form-item label="회의이름" prop="groupName" required>
          <el-input v-model="ruleForm.groupName" type="text" autocomplete="off" placeholder="회의이름을 입력해 주세요." maxlength="45"/>
        </el-form-item>

        <!-- 시간 -->
        <div class="demo-time-range">
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
            {{ startTime }}
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
            {{ endTime }}
          </el-form-item>
        </div>
    </div>

    <div style="border: 1px solid black; padding: 40px;">
        <!-- 그룹선택 -->
        <!-- 내가 호스트인 그룹만 데려옴 -->
        <el-form-item label="그룹 선택" prop="group">
          <el-radio-group v-model="ruleForm.group" @change="selectGroup">
            <el-radio label="선택안함" name="no"/>
            <div v-for="(item, index) in groupList" :key="index">
              <el-radio :label="item.id">{{ item.name }}</el-radio>
            </div>
          </el-radio-group>
          
        </el-form-item>
        <!-- 그룹 선택 안할 시 유저 검색 -->
        <el-form-item label="user 선택" prop="user" v-if="!isSelectGroup">
          <el-select
            v-model="ruleForm.user"
            multiple
            filterable
            remote
            reserve-keyword
            placeholder="Please enter a keyword"
            :remote-method="remoteMethod"
            :loading="loading"
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

    <el-form-item>
      <el-button type="success" round @click="submitForm(ruleFormRef)">미팅생성</el-button>
    </el-form-item>
    <user-search/>
</template>

<script setup>

import { hostGroup, userSearch } from '@/common/api/meetingAPI'
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { toRaw } from 'vue';
import { useStore } from 'vuex'
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useStore()
const ruleFormRef = ref()

// 유효성 검사할 항목 이름
const ruleForm = reactive({
  groupName: '',
  startTime: '',
  endTime: '',
  group: '',
  guest: [],
  user:[]
})

// 회의이름 유효성 검사
const validategroupName = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('그룹이름을 입력해 주세요.'))
  } else {
    if (value.replace(' ','') !== value){
      callback(new Error('공백은 입력할 수 없습니다.'))
    }
    callback()
  }
}

// 시간 입력
const startTime = ref('')
const endTime = ref('')
const groupList = ref()
// 내가 호스트인 그룹 목록 불러오는 엑시오스 
onMounted(async() => {
  const res = await hostGroup(4)
  groupList.value = res.data
})

// 그룹선택
const  isSelectGroup = ref(true)
  
const selectGroup = (data) => {
  if (data === '선택안함'){
    isSelectGroup.value = false
  }else{
    isSelectGroup.value = true
  }
}

// 유저검색
// const list = ref([])
const options = ref([])
const user = ref([])
const loading = ref(false)

// 회의 참여자 목록
let guestList = null

// 유저 검색
const remoteMethod = (query) => {
  if (query !== '') {
    loading.value = true
    setTimeout( async () => {

      // 검색요청
      const username ={ "name" : query }
      const res = await userSearch(JSON.stringify(username))

      const searchUserList = res.data
      console.log('저장해서 불러온 검색결과',searchUserList)
      let list = searchUserList.map((item) => {
        return { value: item.id, label: `${item.name}:${item.email}` }
      })

      loading.value = false
      options.value = list
      
      // 요청결과 리스트
    }, 200)
  } else {
    options.value = []
  }
}

// 유효성 검사 규칙
const rules = reactive({
  groupName: [{ validator: validategroupName, trigger: 'blur' }],
  group: [{ required: true, message: '그룹을 선택하세요', trigger: 'change' }],
  startTime: [{ required: true, message: '시작 시간을 선택하세요', trigger: 'change' }],
  endTime: [{ required: true, message: '마치는 시간을 선택하세요', trigger: 'change' }],
  user: [{ required: true, message: '참여자를 선택하세요', trigger: 'change' }]
})

function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

// 회의 생성
const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      // 보낼 회의 정보
      const meetingData = {
        "name" : ruleForm.groupName,
        "starttime" : ruleForm.startTime,
        "endtime" : ruleForm.endTime,
        "date" : getToday(),
      }
      if (isSelectGroup.value === false){
        const rawArray = toRaw(ruleForm.user)
        guestList = rawArray
        meetingData.users = guestList
        
      }else{
        guestList = ruleForm.group
        meetingData.groupid = guestList
      }
      console.log(meetingData)
      await store.dispatch('meetingStore/meetingCreateAction',meetingData)
      router.push({ name: 'main' })
      console.log('submit!')
    } else {
      console.log('error submit!')
      return false
    }
  })
}

</script>

<style>
</style>
