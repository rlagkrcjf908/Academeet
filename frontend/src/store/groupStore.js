import { groupCreate, requestGroupList, requestGroup, groupDelete, groupUpdate, requestUserList } from "../common/api/groupAPI";

const state = {
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
    const response = await groupCreate(groupData);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
  requestGroupListAction: async ({ commit }, id) => {
    const response = await requestGroupList(id);
    commit("SET_GROUP_LIST", response.data.groupData);
  },
  requestGroupAction: async ({ commit }, group_id) => {
    const response = await requestGroup(group_id);
    commit("SET_GROUP_INFO", response.data.groupData);
  },
  requestUserListAction: async ({ commit }, group_id) => {
    const response = await requestUserList(group_id);
    commit("SET_GROUP_USER_LIST", response.data.groupData);
  },
  groupDeleteAction: async ({ commit }, groupId) => {
    const response = await groupDelete(groupId);
    commit("DELETE_GROUP_INFO", response.data.groupData);
  },
  groupUpdateAction: async ({ commit }, groupId) => {
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
