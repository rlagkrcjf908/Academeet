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
            <el-radio label="1">1반</el-radio>
            <el-radio label="2">2반</el-radio>
            <el-radio label="3">3반</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 멤버 선택 -->
        <!-- 선택안함을 누르면 멤버검색 창이 보임 -->
        <el-form-item label="멤버 선택" prop="guest" v-if="!isSelectGroup">
          <el-select-v2
            v-model="ruleForm.guest"
            style="width: 700px"
            multiple
            filterable
            remote
            :remote-method="remoteMethod"
            clearable
            :options="options"
            :loading="loading"
            placeholder="초대하고 싶은 참여자를 검색해주세요"
          />
        </el-form-item>
      </div>
    </el-form>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">미팅생성</el-button>
    </el-form-item>
    
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const startTime = ref('')
const endTime = ref('')
const ruleFormRef = ref()


const states = [
  'Alabama',
  'Alaska',
  'Arizona',
  'Arkansas',
  'California',
  'Colorado',
  'Connecticut',
  'Delaware',
  'Florida',
  'Georgia',
  'Hawaii',
  'Idaho',
  'Illinois',
  'Indiana',
  'Iowa',
  'Kansas',
  'Kentucky',
  'Louisiana',
  'Maine',
  'Maryland',
  'Massachusetts',
  'Michigan',
  'Minnesota',
  'Mississippi',
  'Missouri',
  'Montana',
  'Nebraska',
  'Nevada',
  'New Hampshire',
  'New Jersey',
  'New Mexico',
  'New York',
  'North Carolina',
  'North Dakota',
  'Ohio',
  'Oklahoma',
  'Oregon',
  'Pennsylvania',
  'Rhode Island',
  'South Carolina',
  'South Dakota',
  'Tennessee',
  'Texas',
  'Utah',
  'Vermont',
  'Virginia',
  'Washington',
  'West Virginia',
  'Wisconsin',
  'Wyoming',
]

const  isSelectGroup = ref(true)

const selectGroup = (data) => {
  if (data === '선택안함'){
    isSelectGroup.value = false
    console.log(isSelectGroup.value)
  }else{
    isSelectGroup.value = true
    console.log(isSelectGroup.value)
  }

}
// 그룹이름 유효성 검사
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

// 유효성 검사할 항목 이름
const ruleForm = reactive({
  groupName: '',
  startTime: '',
  endTime: '',
  group: '',
  guest: [],
})

// 회의 참여자 목록
const guestList = ref([])

// 유저 목록
const list = states.map((item) => {
  return { value: `value:${item}`, label: `label:${item}` }
})

// 유저 검색
const options = ref([])
const loading = ref(false)
const remoteMethod = (query) => {
  if (query !== '') {
    loading.value = true
    setTimeout(() => {
      loading.value = false
      options.value = list.filter((item) => {
        return item.label.toLowerCase().includes(query.toLowerCase())
      })
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
})

// 회의 생성
const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid && guestList.value ) {
      console.log('뭐야..:',ruleForm)
      console.log('???',ruleForm.group)
      console.log('참가자:',guestList.value)
      console.log('submit!')
    } else {
      ElMessage({
        showClose: true,
        message: '초대된 사용자가 없습니다!',
        type: 'error',
      })
      console.log('error submit!')
      return false
    }
  })
}



</script>

<style>
</style>
