import { meetingCreate, meetingEnd } from "../common/api/meetingAPI";

const state = {
  meetingInfo:null,
};

const getters = {

};

const mutations = {
  SET_MEETINGINFO: (state, meetingData) => {
    state.meetingInfo = meetingData;
  }
};

const actions = {
  meetingCreateAction: async ({ commit }, meetingData) => {
    const response = await meetingCreate(meetingData);
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
