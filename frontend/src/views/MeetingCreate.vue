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
        <el-form-item label="groupName" prop="groupName">
          <el-input v-model="ruleForm.groupName" type="text" autocomplete="off" placeholder="그룹이름을 입력해 주세요." maxlength="45"/>
        </el-form-item>

        <div class="demo-time-range">
            <div>
                <label for="startTime">회의 시작 시간</label>
                <el-time-select
                  v-model="startTime"
                  class="mr-4"
                  placeholder="회의 시작 시간"
                  start="00:00"
                  step="00:15"
                  end="24:00"
                  id="startTime"
                />
                {{ startTime }}
            </div>
            <div>
                <label for="endTime">회의 마치는 시간</label>
                <el-time-select
                  v-model="endTime"
                  :min-time="startTime"
                  placeholder="회의 마치는 시간"
                  start="00:00"
                  step="00:15"
                  end="24:00"
                  id="endTime"
                />
                {{ endTime }}
            </div>
        </div>
    </div>

    <div style="border: 1px solid black; padding: 40px;">
        <div>
            <p>그룹 선택</p>
            <SelectGroup/>
        </div>
        <div>
          <p>멤버 선택</p>
          <el-select-v2
            v-model="value"
            style="width: 700px"
            multiple
            filterable
            remote
            :remote-method="remoteMethod"
            clearable
            :options="options"
            :loading="loading"
            placeholder="초대하고 싶은 멤버 이름을 검색해주세요"
          />
        </div>
      </div>

    </el-form>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)"
        >그룹생성</el-button
      >
    </el-form-item>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import SelectGroup from '@/components/layouts/SelectGroup'


const ruleFormRef = ref()

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

const ruleForm = reactive({
  groupName: '',
})

const rules = reactive({
  groupName: [{ validator: validategroupName, trigger: 'blur' }],
})

const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid && value.value.length ) {
      console.log('참가자:',value.value)
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
const list = states.map((item) => {
  return { value: `value:${item}`, label: `label:${item}` }
})

const value = ref([])
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

console.log(value.value)

// 시간
const startTime = ref('')
const endTime = ref('')
</script>

<style>
.demo-ruleForm{
  display: flex;
  flex-direction: row;
}
</style>
