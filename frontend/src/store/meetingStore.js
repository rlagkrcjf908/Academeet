import { meetingCreate } from "../common/api/meetingAPI";
import router from '../router/index'

const state = {
  id:null,
  meetingInfo:null,
};

const getters = {

};

const mutations = {
  // 회의생성 정보 저장
  SET_MEETINGINFO: (state, meetingData) => {
    state.meetingInfo = meetingData;
  }
};

const actions = {
  // 회의 생성
  meetingCreateAction: async ({ commit }, meetingData) => {
    const payload = {
      "title" : meetingData.name,
      "starttime" : meetingData.starttime,
      "endtime" : meetingData.endtime,
      "date" : meetingData.date,
    }
    if (meetingData.users){
      payload.users = meetingData.users 
    }else{
      payload.groupid = meetingData.groupid 
    }
    const response = await meetingCreate(meetingData.userid,JSON.stringify(payload))
    commit("SET_MEETINGINFO", response.data)
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
