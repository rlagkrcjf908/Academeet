import { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate } from "../common/api/groupAPI";

const state = {
  groupInfo: null,
  groupList:null,
};

const getters = {
  getToken: state => {
    return state.token;
  }
};

const mutations = {
  SET_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
  SET_GROUP_LIST: (state, payload) => {
    state.groupList = payload;
  },
  DELETE_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
  UPDATE_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
};

const actions = {
  groupCreateAction: async ({ commit }, groupData) => {
    const response = await groupCreate(groupData);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
  requestGroupList: async ({ commit }, groupData) => {
    const response = await requestGroupList(groupData);
    commit("SET_GROUP_LIST", response.data.groupData);
  },
  requestGroup: async ({ commit }, groupId) => {
    const response = await requestGroup(groupId);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
  groupDelete: async ({ commit }, groupId) => {
    const response = await groupDelete(groupId);
    commit("DELETE_GROUP_INFO", response.data.groupData);
  },
  groupUpdate: async ({ commit }, groupId) => {
    const response = await groupUpdate(groupId);
    commit("UPDATE_GROUP_INFO", response.data.groupData);
  },

};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
