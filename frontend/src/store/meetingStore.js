import { meetingCreate } from "../common/api/meetingAPI";
import router from '../router/index'
import axios from 'axios';

const state = {
  id:null,
  meetingInfo:null,

  //attendance
  clientName:null,
  meetingAtt:null
};

const getters = {

};

const mutations = {
  // 회의생성 정보 저장
  SET_MEETINGINFO: (state, meetingData) => {
    state.meetingInfo = meetingData;
    console.log(meetingData);
  },

  // attendance
  SET_ATTENDANCE:(state, attendanceData)=>{
    state.meetingAtt = attendanceData
  },
  SET_CLIENTNAME:(state, clientName)=>{
    state.clientName = clientName
  }
};

const actions = {
  // 회의 생성
  meetingCreateAction: async ({ commit }, meetingData) => {
    const payload = {
      "title": meetingData.name,
      "starttime": meetingData.starttime,
      "endtime": meetingData.endtime,
      "date": meetingData.date,
    }
    if (meetingData.users) {
      payload.users = meetingData.users
    } else {
      if (meetingData.groupid) {
        payload.groupid = meetingData.groupid
      } else {
        payload.groupid = 0
      }
    }
    const response = await meetingCreate(meetingData.userid, JSON.stringify(payload))
    commit("SET_MEETINGINFO", response.data)
  },
  //attendance
  setAttendanceCount: async({commit},payload) =>{
    const meetingAtt = payload.meetingAtt;
    const clientName = payload.name;
    const meetInfo = JSON.parse(sessionStorage.getItem("meetInfo"));
    commit("SET_ATTENDANCE",meetingAtt)
    commit("SET_CLIENTNAME",clientName)
    axios({
      url:`http://i8d108.p.ssafy.io/api/v1/meet/${meetInfo.meetId}/attend`,
      method:'post',
      data:{
          userId:clientName,
          attendcount:meetingAtt
      }
  })
  .then(function a(response){
      console.log(response);
  })
  .catch(function(error){
      console.log(error);
  });
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
