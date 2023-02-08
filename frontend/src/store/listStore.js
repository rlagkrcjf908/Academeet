import { requestMeetingList } from "@/common/api/meetingAPI";

//변수들의 집합
const state = {
  meetList: [],
  userName: "Hekko",
  meetTitle: "bye",
};

const getters = {
};

//변수를 조작하는 함수들을 선언
const mutations = {
  SET_MEET_LIST: (state, payload) => {
    state.meetList = payload;
  },
  SET_MEET_INFO: (state, payload) => {
    state.userName = payload.userName;
    // state.meetTitle = payload.meetTitle;
    console.log(state.userName);
  }
};

//비동기 처리를 하는 함수들
const actions = {
  // 미팅 리스트 요청
  requestMeetingList : async ({ commit }, id) =>{
    const response = await requestMeetingList(id);
    commit("SET_MEET_LIST", response.data)
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
