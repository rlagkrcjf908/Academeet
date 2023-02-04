import { meetingCreate } from "../common/api/meetingAPI";

const state = {
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
    const response = await meetingCreate(JSON.stringify(meetingData));
    commit("SET_MEETINGINFO", response.data);
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
