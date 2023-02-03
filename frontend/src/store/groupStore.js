import {
  groupCreate,
  requestGroupList,
  requestGroup,
  groupDelete,
  groupUpdate,
} from "../common/api/groupAPI";

//변수들의 집합
const state = {
  groupInfo: null,
  groupList: null,
  attdList: null, //그룹의 전체 출석
  attdUser: null, //유저 출석
};

//state변수들을 get 호출
const getters = {
  getToken: (state) => {
    return state.token;
  },
};

//변수를 조작하는 함수들을 선언
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
  //출석관련
  SET_ATTD_LIST: (state, payload) => {
    state.attdList = payload; //그룹 전체 리스트
  },
  SET_ATTD_USER: (state, payload) => {
    state.attdUser = payload; //유저 한명의 출석 리스트
  },
  UPDATE_ATTD_USER: (state, payload) => {
    state.attdUser = payload; //유저 한명 수정
  },
};

//비동기 처리를 하는 함수들
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
  //출석관련
  requestAttdList: async ({ commit }, groupId) => {
    const response = await requestAttdList(groupId);
    commit("SET_ATTD_LIST", response.data.groupData);
  },
  //유저만 부르게 어떻게 하지..?
  requestAttdUser: async ({ commit }, groupId) => {
    const response = await requestAttdUser(groupId);
    commit("SET_ATTD_USER", response.data.groupData);
  },
  attdUserUpdate: async ({ commit }, groupId) => {
    const response = await attdUserUpdate(groupId);
    commit("UPDATE_ATTD_USER", response.data.groupData);
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
