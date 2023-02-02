import { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate, requestUserList } from "../common/api/groupAPI";
const state = {
  id: 9,
  groupInfo: null,
  groupList:null,
  groupUserList:null,
};

const getters = {

};

const mutations = {
  SET_GROUP_INFO: (state, payload) => {
    state.groupInfo = payload;
  },
  SET_GROUP_LIST: (state, payload) => {
    state.groupList = payload;
  },
  SET_GROUP_USER_LIST: (state, payload) => {
    state.groupUserList = payload;
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
    const response = await groupCreate(state.id,groupData);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
  requestGroupListAction: async ({ commit }) => {
    const response = await requestGroupList(state.id);
    commit("SET_GROUP_LIST", response.data.groupData);
  },
  requestGroupAction: async ({ commit }) => {
    const response = await requestGroup(groupid);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
    requestUserListAction: async ({ commit }, group_id) => {
    const response = await requestUserList(group_id);
    commit("SET_GROUP_USER_LIST", response.data.groupData);
  },
  groupDeleteAction: async ({ commit }) => {
    const response = await groupDelete(groupid);
    commit("DELETE_GROUP_INFO", response.data.groupData);
  },
  groupUpdateAction: async ({ commit }, groupData) => {
    const response = await groupUpdate(groupid, groupData);
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
