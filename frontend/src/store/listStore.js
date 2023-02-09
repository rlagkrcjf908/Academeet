import { requestMeetingList } from "@/common/api/meetingAPI";
import { requestMeetingList } from "@/common/api/meetingAPI";

//변수들의 집합
const state = {
  meetList: [],
  meetInfo: null
};

const getters = {
  getMeetInfo (state) {
    return state.meetInfo
  }
};

//변수를 조작하는 함수들을 선언
const mutations = {
  SET_MEET_LIST: (state, payload) => {
    state.meetList = payload;
  SET_MEET_LIST: (state, payload) => {
    state.meetList = payload;
  },
  SET_MEET_INFO: (state, meetInfo) => {
    state.meetInfo = meetInfo;
  }
};

//비동기 처리를 하는 함수들
const actions = {
  // 미팅 리스트 요청
  requestMeetingList : async ({ commit }, id) =>{
    const response = await requestMeetingList(id);
    commit("SET_MEET_LIST", response.data)
  },
  requestMeetInfo({ commit, dispatch }, meetInfo) {
    commit("SET_MEET_INFO", meetInfo);
    console.log(meetInfo)
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
